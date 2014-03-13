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
package jsa.endpoint.cxf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import jsa.endpoint.processors.JSAProcessor;
import jsa.endpoint.processors.MethodInvocationContext;

import org.apache.camel.Exchange;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
class RestProcessor extends JSAProcessor {

	private ExceptionMapper<Exception> exceptionMapper;

	public RestProcessor(Class<?> apiPort) {
		super(apiPort);
	}

	@Override
	protected void setOutputResult(Exchange exchange, Object result) throws IOException {
		exchange.getOut().setBody(result);
	}

	@Override
	protected Object invoke(MethodInvocationContext invCtx) throws Exception {
		try {
			return invCtx.invoke();
		}
		catch (InvocationTargetException e) {
			if (e.getCause() instanceof Exception) {
				return handleInvocationException((Exception) e.getCause());
			}
			throw e;
		}
		catch (Exception e) {
			return handleInvocationException(e);
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private Object handleInvocationException(Exception e) throws Exception {
		Response res = getExceptionMapper().toResponse(e);
		if (res == null) {
			throw e;
		}
		return res;
	}

	public synchronized ExceptionMapper<Exception> getExceptionMapper() {
		if (exceptionMapper == null) {
			ExposeRest rest = apiPort.getAnnotation(ExposeRest.class);
			Class<? extends ExceptionMapper<Exception>> mapper = rest.exceptionMapper();
			exceptionMapper = injector.getInstance(mapper);
		}
		return exceptionMapper;
	}

}
