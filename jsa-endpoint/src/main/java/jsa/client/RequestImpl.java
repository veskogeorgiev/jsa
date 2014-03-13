//package jsa.client;
//
//import java.lang.reflect.Method;
//import java.util.concurrent.Executor;
//
//import javax.ws.rs.client.ClientException;
//import javax.ws.rs.client.InvocationCallback;
//
//import lombok.AllArgsConstructor;
//
//@AllArgsConstructor
//class RequestImpl<T> implements Request<T> {
//
//	private Executor executor;
//
//	private Object stub;
//	private Method method;
//	private Object[] args;
//
//	@Override
//	public void fire(final InvocationCallback<T> handler) {
//		executor.execute(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Object res = method.invoke(stub, args);
//					handler.completed((T) res);
//				}
//				catch (Exception e) {
//					handler.failed(new ClientException(e));
//				}
//			}
//		});
//	}
//
// }
