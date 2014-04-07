package jsa.endpoint.thrift.proxy;

import java.lang.reflect.Method;

class DirectServerProxy extends AbstractServerProxy {

	DirectServerProxy(Object apiInstance) {
		super(apiInstance);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] thriftArgs) throws Throwable {
		Method apiMethod = methodRepo.singleMethod(method.getName(), thriftArgs);
		Object res = apiMethod.invoke(apiInstance, thriftArgs);

		return res;
	}
}
