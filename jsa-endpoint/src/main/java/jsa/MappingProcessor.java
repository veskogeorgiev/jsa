package jsa;

import com.google.inject.Injector;
import jsa.dto.HasAttachments;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.AllArgsConstructor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

/**
 * 
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@AllArgsConstructor
public class MappingProcessor implements Processor {

	protected final Class<?> beanClass;
	protected final Injector injector;

	@Override
	public void process(Exchange exchange) throws Exception {
		String operationName = exchange.getIn().getHeader(
				CxfConstants.OPERATION_NAME, String.class);
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

	private void prepareExchangeWithAttachments(Exchange exchange,
			String headerKey) {
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
		return beanClass.getMethod(operationName,
				getParameterTypes(parameters));
	}

	private Class<?>[] getParameterTypes(Object[] parameters) {
		if (parameters == null) {
			return new Class[0];
		}
		Class<?>[] answer = new Class[parameters.length];
		int i = 0;
		for (Object object : parameters) {
			answer[i] = object.getClass();
			i++;
		}
		return answer;
	}

	private Object getObjectInstance() {
		try {
			return injector.getInstance(beanClass);
		}
		catch (Exception e) {
			Object instance = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { beanClass }, new InvocationHandler() {

				@Override
				public Object invoke(Object o, Method method, Object[] os) throws Throwable {
					//
					return null;
				}
			});
			return instance;
		}
	}

}
