package jsa.proc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import jsa.NotImplementedException;
import jsa.dto.HasAttachments;
import jsa.inject.PortImplementationLocator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class DefaultJSAProcessor implements Processor {

	protected final Class<?> apiPort;

	protected volatile MethodRepository methodRepository;
	protected volatile Object serviceInstance;

	public DefaultJSAProcessor(@NotNull Class<?> apiPort) {
		this.apiPort = apiPort;
	}

	public DefaultJSAProcessor(@NotNull Class<?> apiPort, @NotNull PortImplementationLocator locator) throws NotImplementedException {
		this(apiPort);
		init(locator);
	}

	@Inject
	private void init(PortImplementationLocator locator) throws NotImplementedException {
		if (methodRepository == null) {
			this.serviceInstance = locator.locateServiceImplementor(apiPort);
			methodRepository = new MethodRepository(serviceInstance.getClass());
		}
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		String operationName = getOperationName(exchange);
		if (operationName == null) {
			return;
		}
		// init method repository

		Object[] body = exchange.getIn().getBody(Object[].class);
		Method method = findMethod(operationName, body);

		try {
			Object response = method.invoke(serviceInstance, body);

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

	private Method findMethod(String operationName, Object[] parameters) throws SecurityException,
			NoSuchMethodException {
		return methodRepository.singleMethod(operationName, parameters);
	}

	private String getOperationName(Exchange exchange) {
		return exchange.getIn().getHeader(CxfConstants.OPERATION_NAME, String.class);
	}

	private static class MethodRepository {
		private Map<String, List<Method>> nameToMethod = new HashMap<>();

		MethodRepository(Class<?> cls) {
			if (cls != null) {
				for (Method m : cls.getDeclaredMethods()) {
					methodsByName(m.getName()).add(m);
				}
				for (Method m : cls.getMethods()) {
					methodsByName(m.getName()).add(m);
				}

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

		public Method singleMethod(String methodName, Object[] parameters)
				throws NoSuchMethodException {
			List<Method> possibleMethods = methodsByName(methodName);
			for (Method method : possibleMethods) {
				if (method.getParameterTypes().length == parameters.length) {
					return method;
				}
			}
			throw new NoSuchMethodException();
		}
	}
}
