///**
// * 
// */
//package jsa.inject;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//import jsa.NotImplementedException;
//
///**
// *
// * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
// */
//public class ServiceImplementationProxy implements InvocationHandler {
//
//	private final Lock lazyImplLocationLock = new ReentrantLock(true);
//
//	private final PortImplementationLocator locator;
//	private final Class<?> apiPort;
//
//	private volatile Object serviceInstance;
//
//	public ServiceImplementationProxy(Class<?> apiPort, PortImplementationLocator locator) {
//		this.apiPort = apiPort;
//		this.locator = locator;
//	}
//
//	@Override
//	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		Object impl = getServiceInstance();
//		return method.invoke(impl, args);
//	}
//
//	public Object getServiceInstance() throws NotImplementedException {
//		if (serviceInstance == null) {
//			try {
//				lazyImplLocationLock.lock();
//				if (serviceInstance == null) {
//					try {
//						this.serviceInstance = locator.locateServiceImplementor(apiPort);
//					}
//					catch (NotImplementedException e) {
//						throw e;
//						// methodRepository = new MethodRepository(null);
//					}
//				}
//			}
//			finally {
//				lazyImplLocationLock.unlock();
//			}
//		}
//		return serviceInstance;
//	}
//
//}
