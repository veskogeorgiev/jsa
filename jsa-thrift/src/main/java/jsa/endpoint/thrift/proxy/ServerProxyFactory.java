package jsa.endpoint.thrift.proxy;

import java.lang.reflect.Proxy;

import jsa.endpoint.thrift.TypeMapping;

public class ServerProxyFactory {

	public static Object create(Object apiInstance, Class<?> thriftPortInterface,
	      TypeMapping typeMapping) {
		AbstractServerProxy proxy = new MappingServerProxy(apiInstance, typeMapping);

		return Proxy.newProxyInstance(DirectServerProxy.class.getClassLoader(),
		      new Class[] {thriftPortInterface}, proxy);
	}

	public static Object create(Object apiInstance, Class<?> thriftPortInterface) {
		AbstractServerProxy proxy = new DirectServerProxy(apiInstance);

		return Proxy.newProxyInstance(DirectServerProxy.class.getClassLoader(),
		      new Class[] {thriftPortInterface}, proxy);
	}


}
