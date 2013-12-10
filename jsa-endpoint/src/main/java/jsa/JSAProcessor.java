package jsa;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jsa.dto.HasAttachments;
import jsa.routes.ImplementorBridgeProxy;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

import com.google.inject.Injector;

/**
 * 
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class JSAProcessor implements Processor {

	protected final Class<?> apiInterface;

	protected final Injector injector;

	protected final MethodRepository methodRepository;

	public JSAProcessor(Class<?> apiInterface, Injector injector) {
		this.apiInterface = apiInterface;
		this.injector = injector;
		methodRepository = new MethodRepository(apiInterface);
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		String operationName = getOperationName(exchange);
		if (operationName == null) {
			return;
		}
		Object[] body = exchange.getIn().getBody(Object[].class);
		Method method = findMethod(operationName, body);

		try {
			Object response = method.invoke(getObjectInstance(), body);

			exchange.getOut().setBody(response);

			// In order for the multicast to work we shallow copy the attachment
			// streams in the header so the appropriate route can process them
			prepareExchangeWithAttachments(exchange, "attachments");
		}
		catch (InvocationTargetException e) {
			throw (Exception) e.getCause();
		}
	}

	private void prepareExchangeWithAttachments(Exchange exchange, String headerKey) {
		// Get arguments from exchange
		Object[] args = exchange.getIn().getBody(Object[].class);
		for (Object obj : args) {
			if (obj instanceof HasAttachments) {
				HasAttachments hasAttachments = (HasAttachments) obj;
				exchange.getOut().setHeader(headerKey, hasAttachments.getAttachments());
			}
		}
	}

	private Method findMethod(String operationName, Object[] parameters)
			throws SecurityException, NoSuchMethodException {
		return methodRepository.singleMethod(operationName, parameters);
	}

	private String getOperationName(Exchange exchange) {
		return exchange.getIn().getHeader(CxfConstants.OPERATION_NAME, String.class);
	}

	private Object getObjectInstance() {
		return ImplementorBridgeProxy.create(apiInterface, injector);
	}

	private static class MethodRepository {
		private Map<String, List<Method>> nameToMethod = new HashMap<>();

		 MethodRepository(Class<?> cls) {
			for (Method m : cls.getDeclaredMethods()) {
				methodsByName(m.getName()).add(m);
			}
		}
		
		public List<Method> methodsByName(String methodName) {
			List<Method> res = nameToMethod.get(methodName);
			if (res == null) {
				res = new LinkedList<>();
				nameToMethod.put(methodName, res);
			}
			return res;
		}
		
		public Method singleMethod(String methodName, Object[] parameters) throws NoSuchMethodException {
			List<Method> possibleMethods = methodsByName(methodName);
			for (Method method : possibleMethods) {
				if (method.getParameterTypes().length == parameters.length) {
					return method;
				}
			}
			throw new NoSuchMethodException();
		}
		
		private Class<?>[] getParameterTypes(Object[] parameters) {
			if (parameters == null) {
				return new Class[0];
			}
			Class<?>[] answer = new Class[parameters.length];
			int i = 0;
			for (Object object : parameters) {
				answer[i] = object != null ? object.getClass() : Object.class;
				i++;
			}
			return answer;
		}
	}
}
