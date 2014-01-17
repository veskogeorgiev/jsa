/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsa.routes;

import jsa.NotImplementedException;
import jsa.annotations.APIPort;
import lombok.extern.java.Log;

import com.google.inject.Injector;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 * @param <T> API interface
 */
@Log
public class APIImplementationLocator {

//	private static final Lock lock = new ReentrantLock(true);

	public static Object locateServiceImplementor(Class<?> apiPort, Injector injector) throws NotImplementedException {
		Class<?> apiInterface = findServiceClassFromAPIPort(apiPort);
		try {
			return injector.getInstance(apiInterface);
		}
		catch (Exception e) {
			log.warning(String.format("No implementation for %s specified", apiInterface));
			throw new NotImplementedException(e);
		}
	}

	public static Class<?> findServiceClassFromAPIPort(Class<?> apiPort) {
		APIPort port = apiPort.getAnnotation(APIPort.class);
		if (port != null) {
			switch (port.type()) {
			case ADAPTER:
				return apiPort;
			case DECORATOR:
				return port.api();
			default:
				break;
			}
		}
		return apiPort;
//		throw new RuntimeException("");
	}

//	private final Class<T> apiInterface;
//
//	private final Injector injector;
//
//	private final Validator validator;
//
//	private T apiImplementor;

//	public ImplementorBridgeProxy(Class<T> apiInterface, Injector injector) {
//		this.apiInterface = apiInterface;
//		this.injector = injector;
//
//		this.apiImplementor = locateApiImplementor();
//		this.validator = injector.getInstance(Validator.class);
//	}
//
//	@Override
//	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		if (apiImplementor != null) {
//			if (args != null) {
//				for (Object object : args) {
//					Set<ConstraintViolation<Object>> res = validator.validate(object);
//					if (!res.isEmpty()) {
//						ConstraintViolation<Object> constraint = res.iterator().next();
//						throw new ValidationException(constraint.getMessage());
//					}
//				}
//			}
//			return method.invoke(apiImplementor, args);
//		}
//		throw new NotImplementedException();
//	}
//
//	private synchronized T locateApiImplementor() {
//		if (apiImplementor == null) {
//			try {
//				lock.lock();
//
////				if (apiImplementor == null) { // check again
////					try {
////						Key<T> key = Key.get(apiInterface, ImplementorBrigde.class);
////						apiImplementor = injector.getInstance(key);
////						log.info(String.format("%s implementation found through a bridge", apiImplementor));
////					}
////					catch (Exception e) {
////						log.info(String.format("No implementation for %s found through a bridge. %s",
////											   apiInterface, e));
////					}
//
//					if (apiImplementor == null) {
//						try {
//							apiImplementor = injector.getInstance(apiInterface);
//							log.info(String.format("%s implementation found directly", apiImplementor));
//						}
//						catch (Exception e) {
//							log.warning(String.format("No implementation for %s specified", apiInterface));
//						}
//					}
////				}
//			}
//			finally {
//				lock.unlock();
//			}
//		}
//		return apiImplementor;
//	}

}
