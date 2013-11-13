/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsa.routes;

import com.google.inject.Injector;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import lombok.AllArgsConstructor;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 * @param <T> API interface
 */
@AllArgsConstructor
public class EndpointBridgeProxy<T> implements InvocationHandler {

	public static <T> T create(Class<T> apiInterface, Injector injector) {
		ClassLoader classLoader = EndpointBridgeProxy.class.getClassLoader();
		Class[] interfaces = new Class[] { apiInterface };
		return (T) Proxy.newProxyInstance(classLoader, interfaces, new EndpointBridgeProxy(apiInterface, injector));
	} 

	private final Class<T> apiInterface;
	private final Injector injector;
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(method);
		return null;
	}

}
