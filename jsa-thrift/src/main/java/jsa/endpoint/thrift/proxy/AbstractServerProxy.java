package jsa.endpoint.thrift.proxy;

import java.lang.reflect.InvocationHandler;

import jsa.endpoint.processors.MethodRepository;

abstract class AbstractServerProxy implements InvocationHandler {
	protected Object apiInstance;
	protected MethodRepository methodRepo;

	protected AbstractServerProxy(Object apiInstance) {
		this.apiInstance = apiInstance;
		methodRepo = new MethodRepository(apiInstance.getClass());
	}

}
