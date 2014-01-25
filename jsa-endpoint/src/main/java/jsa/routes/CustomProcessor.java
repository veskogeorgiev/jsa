package jsa.routes;

import org.apache.camel.Exchange;

public interface CustomProcessor {

	void process(Exchange exchange, APIInvocationContext invocationContext) throws Exception;
}
