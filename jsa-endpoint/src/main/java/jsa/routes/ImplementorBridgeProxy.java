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
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import jsa.NotImplementedException;
import jsa.annotations.ImplementorBrigde;
import lombok.extern.java.Log;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 * @param <T> API interface
 */
@Log
public class ImplementorBridgeProxy<T> implements InvocationHandler {

	private static final Object mutex = new Object();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T create(Class<T> apiInterface, Injector injector) {
		ClassLoader classLoader = ImplementorBridgeProxy.class.getClassLoader();
		Class[] interfaces = new Class[]{apiInterface};
		return (T) Proxy.newProxyInstance(classLoader, interfaces, new ImplementorBridgeProxy<T>(apiInterface, injector));
	}

	private final Class<T> apiInterface;

	private final Injector injector;

	private final Validator validator;

	private T apiImplementor;

	public ImplementorBridgeProxy(Class<T> apiInterface, Injector injector) {
		this.apiInterface = apiInterface;
		this.injector = injector;

		this.apiImplementor = locateApiImplementor();
		this.validator = injector.getInstance(Validator.class);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (apiImplementor != null) {
			if (args != null) {
				for (Object object : args) {
					Set<ConstraintViolation<Object>> res = validator.validate(object);
					if (res.isEmpty()) {
						ConstraintViolation<Object> constraint = res.iterator().next();
						throw new ValidationException(constraint.getMessage());
					}
				}
			}
			return method.invoke(apiImplementor, args);
		}
		throw new NotImplementedException();
	}

	private synchronized T locateApiImplementor() {
		if (apiImplementor == null) {
			synchronized (mutex) {
				if (apiImplementor == null) { // check again
					try {
						Key<T> key = Key.get(apiInterface, ImplementorBrigde.class);
						apiImplementor = injector.getInstance(key);
						log.info(String.format("%s implementation found through a bridge", apiImplementor));
					}
					catch (Exception e) {
						log.info(String.format("No implementation for %s found through a bridge. %s",
											   apiInterface, e));
					}

					if (apiImplementor == null) {
						try {
							apiImplementor = injector.getInstance(apiInterface);
							log.info(String.format("%s implementation found directly", apiImplementor));
						}
						catch (Exception e) {
							log.warning(String.format("No implementation for %s specified", apiInterface));
						}
					}
				}
			}
		}
		return apiImplementor;
	}

}
