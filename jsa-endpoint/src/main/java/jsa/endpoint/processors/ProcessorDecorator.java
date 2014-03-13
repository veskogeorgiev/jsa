package jsa.endpoint.processors;

import org.apache.camel.Exchange;

public interface ProcessorDecorator {

	void beforeInvocation(Exchange exchange);

	void afterInvocation(Exchange exchange);
}
