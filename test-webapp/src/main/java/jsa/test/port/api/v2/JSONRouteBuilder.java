package jsa.test.port.api.v2;

import jsa.endpoint.cxf.RestRouteBuilder;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class JSONRouteBuilder extends RestRouteBuilder {

    @Override
    protected Processor createProcessor() {
        return new JSONProcessor();
    }

    @Override
    public void configure() throws Exception {
        Endpoint endpoint = createEndpoint(apiPortMeta.getApiPortClass());
        from(endpoint).multicast().process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                System.out.println("do smth with exchange " + exchange);
            }
        }).process(processor).end();
    }
}
