//package jsa.endpoint.thrift;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//
//import org.codehaus.jackson.map.ObjectMapper;
//
//public class ThriftClientProxy implements InvocationHandler {
//
//	public static Object create(Object thriftClient, Class<?> thriftPort) {
//		ThriftClientProxy proxy = new ThriftClientProxy(apiInstance);
//
//		Proxy.newProxyInstance(ThriftClientProxy.class.getClassLoader(), 
//				new Class[] { thriftPort }, );
//	}
//
//	private ObjectMapper mapper = new ObjectMapper();
//	private Object apiInstance;
//
//	public ThriftClientProxy(Object apiInstance) {
//		this.apiInstance = apiInstance;
//	}
//
//	@Override
//	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		//
//		return null;
//	}
//
// }
