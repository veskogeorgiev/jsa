package jsa.test.port.api.v2;

import java.lang.reflect.Method;

import org.apache.camel.Exchange;

import jsa.endpoint.cxf.RestProcessor;
import jsa.endpoint.processors.MethodInvocationContext;

public class JSONProcessor extends RestProcessor {

    @Override
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
}
