/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsa.routes;

import com.google.inject.Injector;
import com.google.inject.Key;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import jsa.NotImplementedException;
import lombok.extern.java.Log;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 * @param <T> API interface
 */
@Log
public class EndpointBridgeProxy<T> implements InvocationHandler {

	private static final Object mutex = new Object();

	public static <T> T create(Class<T> apiInterface, Injector injector) {
		ClassLoader classLoader = EndpointBridgeProxy.class.getClassLoader();
		Class[] interfaces = new Class[]{apiInterface};
		return (T) Proxy.newProxyInstance(classLoader, interfaces, new EndpointBridgeProxy(apiInterface, injector));
	}

	private final Class<T> apiInterface;

	private final Injector injector;

	private T apiInstance;

	public EndpointBridgeProxy(Class<T> apiInterface, Injector injector) {
		this.apiInterface = apiInterface;
		this.injector = injector;

		this.apiInstance = locateApiInstance();
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (apiInstance != null) {
			return method.invoke(apiInstance, args);
		}
		throw new NotImplementedException();
	}

	private synchronized T locateApiInstance() {
		if (apiInstance == null) {
			synchronized (mutex) {
				if (apiInstance == null) { // check again
					try {
						Key<T> key = Key.get(apiInterface, EndpointBrigde.class);
						apiInstance = injector.getInstance(key);
						log.info(String.format("%s implementation found through a bridge", apiInstance));
					}
					catch (Exception e) {
						log.info(String.format("No implementation for %s found through a bridge. %s",
											   apiInterface, e));
					}

					if (apiInstance == null) {
						try {
							apiInstance = injector.getInstance(apiInterface);
							log.info(String.format("%s implementation found directly", apiInstance));
						}
						catch (Exception e) {
							log.info(String.format("No direct implementation for %s specified",
												   apiInterface, e));
						}
					}

					if (apiInstance == null) {
						log.warning(String.format("No implementation for %s specified", apiInterface));
					}
				}
			}
		}
		return apiInstance;
	}

}
