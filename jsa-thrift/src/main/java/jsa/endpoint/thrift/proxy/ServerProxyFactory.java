package jsa.endpoint.thrift.proxy;

import java.lang.reflect.Proxy;

import jsa.endpoint.thrift.TypeMapping;

public class ServerProxyFactory {

	public static Object create(Object apiInstance, Class<?> thriftPortInterface,
	      TypeMapping typeMapping) {
		AbstractServerProxy proxy = new MappingServerProxy(apiInstance, typeMapping);

		return Proxy.newProxyInstance(classLoader(),
		      new Class[] {thriftPortInterface}, proxy);
	}

	public static Object create(Object apiInstance, Class<?> thriftPortInterface) {
	    if (!thriftPortInterface.isInterface()) {
	        thriftPortInterface = thriftPortInterface.getInterfaces()[0];
	    }
		AbstractServerProxy proxy = new DirectServerProxy(apiInstance);

		return Proxy.newProxyInstance(classLoader(),
		      new Class[] {thriftPortInterface}, proxy);
	}

	private static ClassLoader classLoader() {
	    return ServerProxyFactory.class.getClassLoader();
	}

}
