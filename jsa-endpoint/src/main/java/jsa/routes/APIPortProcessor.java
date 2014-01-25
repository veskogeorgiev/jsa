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
package jsa.routes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import jsa.NotImplementedException;
import jsa.inject.PortImplementationLocator;
import lombok.extern.java.Log;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

import com.google.inject.Injector;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Log
public class APIPortProcessor implements Processor {

	protected final Class<?> apiPort;

	protected MethodRepository methodRepository;
	protected Object serviceInstance;

	private CustomProcessor customProcessor;

	public APIPortProcessor(Class<?> apiPort, CustomProcessor customProcessor) {
		this.apiPort = apiPort;
		this.customProcessor = customProcessor;
	}

	public APIPortProcessor(Class<?> apiPort) {
		this(apiPort, null);
	}

	@Inject
	private void postConstruct(PortImplementationLocator locator, Injector injector) throws NotImplementedException {
		this.serviceInstance = locator.locateServiceImplementor(apiPort);
		this.methodRepository = new MethodRepository(serviceInstance.getClass());

		if (customProcessor != null) {
			injector.injectMembers(customProcessor);
		}
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		String operationName = getOperationName(exchange);

		if (operationName == null) {
			log.info("Unknown operation name: " + operationName);
			return;
		}
		Object[] body = exchange.getIn().getBody(Object[].class);

		Method method = findMethod(operationName, body);

		APIInvocationContext invCtx = new APIInvocationContext(serviceInstance, method, body);

		if (customProcessor != null) {
			log.info("Dispaching API method : " + method + " to custom processor");
			customProcessor.process(exchange, invCtx);
		}
		else {
			try {
				log.info("Execute API method : " + method);
				Object response = invCtx.invoke();
				exchange.getOut().setBody(response);
			}
			catch (InvocationTargetException e) {
				throw (Exception) e.getCause();
			}
		}
	}

	protected Method findMethod(String operationName, Object[] parameters) throws SecurityException,
			NoSuchMethodException {
		return methodRepository.singleMethod(operationName, parameters);
	}

	protected String getOperationName(Exchange exchange) {
		return exchange.getIn().getHeader(CxfConstants.OPERATION_NAME, String.class);
	}

}
