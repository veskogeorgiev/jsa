package jsa.proc;
//package jsa;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
//import jsa.dto.HasAttachments;
//import jsa.routes.ImplementorBridgeProxy;
//import lombok.AllArgsConstructor;
//
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.apache.camel.component.cxf.common.message.CxfConstants;
//
//import com.google.inject.Injector;
//
///**
// * 
// * 
// * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
// */
//@AllArgsConstructor
//public class JSABeanProcessor implements Processor {
//
//	protected final Class<?> apiInterface;
//
//	protected final Injector injector;
//
//	@Override
//	public void process(Exchange exchange) throws Exception {
//		String operationName = getOperationName(exchange);
//		if (operationName == null) {
//			return;
//		}
//		Object[] body = exchange.getIn().getBody(Object[].class);
//		Method method = findMethod(operationName, body);
//
//		try {
//			Object response = method.invoke(getObjectInstance(), body);
//
//			exchange.getOut().setBody(response);
//
//			// In order for the multicast to work we shallow copy the attachment
//			// streams in the header so the appropriate route can process them
//			prepareExchangeWithAttachments(exchange, "attachments");
//		}
//		catch (InvocationTargetException e) {
//			throw (Exception) e.getCause();
//		}
//	}
//
//	private void prepareExchangeWithAttachments(Exchange exchange,
//												String headerKey) {
//		// Get arguments from exchange
//		Object[] args = exchange.getIn().getBody(Object[].class);
//		for (Object obj : args) {
//			if (obj instanceof HasAttachments) {
//				HasAttachments hasAttachments = (HasAttachments) obj;
//				exchange.getOut().setHeader(headerKey, hasAttachments.getAttachments());
//			}
//		}
//	}
//
//	private Method findMethod(String operationName, Object[] parameters)
//			throws SecurityException, NoSuchMethodException {
//		return apiInterface.getMethod(operationName,
//									  getParameterTypes(parameters));
//	}
//
//	private Class<?>[] getParameterTypes(Object[] parameters) {
//		if (parameters == null) {
//			return new Class[0];
//		}
//		Class<?>[] answer = new Class[parameters.length];
//		int i = 0;
//		for (Object object : parameters) {
//			answer[i] = object != null ? object.getClass() : Object.class;
//			i++;
//		}
//		return answer;
//	}
//
//	private String getOperationName(Exchange exchange) {
//		return exchange.getIn().getHeader(
//				CxfConstants.OPERATION_NAME, String.class);
//	}
//
//	private Object getObjectInstance() {
//		return ImplementorBridgeProxy.create(apiInterface, injector);
//	}
//
////	private static class MethodRepository {
////		private Map<String, Collection<Method>> nameToMethod = new HashMap<>();
////
////		public MethodRepository(Class<?> cls) {
////			for (Method m : cls.getDeclaredMethods()) {
////				nameToMethod.put(m.getName(), value)
////			}
////		}
////	}
//}