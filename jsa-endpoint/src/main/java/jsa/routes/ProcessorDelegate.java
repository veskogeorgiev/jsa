package jsa.routes;

import org.apache.camel.Exchange;

public interface ProcessorDelegate {

	void process(Exchange exchange, APIInvocationContext invocationContext) throws Exception;
}
