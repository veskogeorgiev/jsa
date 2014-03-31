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
import jsa.endpoint.APIPortAware;
import lombok.extern.slf4j.Slf4j;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Slf4j
public class APIProcessor implements Processor, APIPortAware {

    protected Class<?> apiPort;
    protected APIPortMeta apiPortMeta;

    protected MethodRepository methodRepository;
    protected Object serviceInstance;

    @Override
    public void setAPIPort(Class<?> apiPort) {
        this.apiPort = apiPort;
        this.apiPortMeta = APIPortMeta.create(apiPort);
    }

    @Inject
    private void postConstruct(PortImplementationLocator locator)
            throws NotImplementedException {
        this.serviceInstance = locator.locate(apiPort);
        this.methodRepository = new MethodRepository(serviceInstance.getClass());
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        MethodInvocationContext invCtx = createInvocationContext(exchange);

        if (invCtx == null) {
            log.info("Cannot create invocation context for operation : " + exchange);
            return;
        }

        Object response = null;
        try {
            log.info("Execute API method : " + invCtx.getMethod());

            response = invoke(invCtx);
        }
        catch (InvocationTargetException e) {
            if (e.getCause() instanceof Exception) {
                response = toResponse((Exception) e.getCause());
            }
            else {
                throw new RuntimeException(e.getCause());
            }
        }
        
        setOutputResult(exchange, response);
    }

    protected MethodInvocationContext createInvocationContext(Exchange exchange)
            throws SecurityException, NoSuchMethodException {
        String operationName = getOperationName(exchange);

        if (operationName == null) {
            return null;
        }
        Object[] body = exchange.getIn().getBody(Object[].class);

        Method method = findMethod(operationName, body);
        return new MethodInvocationContext(serviceInstance, method, body);
    }

    protected Object invoke(MethodInvocationContext invCtx)
            throws InvocationTargetException, Exception {
        return invCtx.invoke();
    }

    protected void setOutputResult(Exchange exchange, Object result) throws IOException {
        exchange.getOut().setBody(result);
    }

    /**
     * Should either return a response or rethrow the exception
     * @param e
     * @return
     * @throws Exception
     */
    protected Object toResponse(Exception e) throws Exception {
        throw e;
    }

    protected Method findMethod(String operationName, Object[] parameters)
            throws SecurityException,
            NoSuchMethodException {
        return methodRepository.singleMethod(operationName, parameters);
    }

    protected String getOperationName(Exchange exchange) {
        return exchange.getIn().getHeader(CxfConstants.OPERATION_NAME, String.class);
    }

}
