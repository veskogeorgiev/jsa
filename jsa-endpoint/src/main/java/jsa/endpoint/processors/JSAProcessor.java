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
package jsa.endpoint.processors;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import jsa.NotImplementedException;
import lombok.extern.slf4j.Slf4j;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

import com.google.inject.Injector;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Slf4j
public abstract class JSAProcessor implements Processor {

	protected final Class<?> apiPort;
	private DefaultAPIPortMeta apiPortMeta;

	@Inject protected PortImplementationLocator locator;
	@Inject protected Injector injector;

	protected MethodRepository methodRepository;
	protected Object serviceInstance;

	protected Processor delegate;
	protected ProcessorDecorator decorator = EmptyDecorator.INSTANCE;

	public JSAProcessor(Class<?> apiPort) {
		this.apiPort = apiPort;
		this.apiPortMeta = DefaultAPIPortMeta.create(apiPort);
	}

	@Inject
	private void postConstruct(PortImplementationLocator locator, Injector injector) throws NotImplementedException {
		this.serviceInstance = locator.locateServiceImplementor(apiPort);
		this.methodRepository = new MethodRepository(serviceInstance.getClass());

		if (apiPortMeta.hasProcessorDelegate()) {
			delegate = injector.getInstance(apiPortMeta.getProcessorDelegate());
		}
		else if (apiPortMeta.hasProcessorDecorator()) {
			decorator = injector.getInstance(apiPortMeta.getProcessorDecorator());
		}
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		if (delegate != null) {
			log.info("Delegating API method: ");
			delegate.process(exchange);
			return;
		}
		String operationName = getOperationName(exchange);

		if (operationName == null) {
			log.info("Unknown operation name: " + operationName);
			return;
		}
		Object[] body = exchange.getIn().getBody(Object[].class);

		Method method = findMethod(operationName, body);
		MethodInvocationContext invCtx = new MethodInvocationContext(serviceInstance, method, body);

		try {
			log.info("Execute API method : " + method);

			decorator.beforeInvocation(exchange);

			Object response = invoke(invCtx);

			setOutputResult(exchange, response);

			decorator.afterInvocation(exchange);
		}
		catch (InvocationTargetException e) {
			if (e.getCause() instanceof Exception) {
				throw (Exception) e.getCause();
			}
			throw new RuntimeException(e.getCause());
		}
	}

	protected abstract void setOutputResult(Exchange exchange, Object result) throws IOException;

	protected Object invoke(MethodInvocationContext invCtx) throws Exception {
		return invCtx.invoke();
	}

	protected void handleException(InvocationTargetException e) throws Exception {
		if (e.getCause() instanceof Exception) {
			throw (Exception) e.getCause();
		}
		throw new RuntimeException(e.getCause());		
	}

	protected Method findMethod(String operationName, Object[] parameters) throws SecurityException,
	      NoSuchMethodException {
		return methodRepository.singleMethod(operationName, parameters);
	}

	protected String getOperationName(Exchange exchange) {
		return exchange.getIn().getHeader(CxfConstants.OPERATION_NAME, String.class);
	}

	protected static class EmptyDecorator implements ProcessorDecorator {
		private static final EmptyDecorator INSTANCE = new EmptyDecorator();

		private EmptyDecorator() {
			// no allocation, please
		}

		@Override
		public void beforeInvocation(Exchange exchange) {
			// empty
		}

		@Override
		public void afterInvocation(Exchange exchange) {
			// empty
		}
	}
}
