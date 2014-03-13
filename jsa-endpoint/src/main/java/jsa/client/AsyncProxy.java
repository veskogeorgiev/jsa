//package jsa.client;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.util.concurrent.Executor;
//
//import lombok.AllArgsConstructor;
//
//@AllArgsConstructor
//class AsyncProxy implements InvocationHandler {
//
//	private Executor executor;
//	private Object stub;
//
//	@Override
//	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		return new RequestImpl<Object>(executor, stub, method, args);
//	}
//
// }
