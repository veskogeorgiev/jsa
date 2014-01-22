/*******************************************************************************
 * Copyright (C) 2013 <a href="mailto:vesko.georgiev@icloud.com">Vesko Georgiev</a>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *******************************************************************************/
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
