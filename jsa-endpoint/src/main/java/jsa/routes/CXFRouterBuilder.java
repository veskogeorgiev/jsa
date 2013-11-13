package jsa.routes;

import org.apache.camel.Endpoint;
import org.apache.camel.NoSuchEndpointException;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.cxf.BusFactory;

/**
 *
 * @author vesko
 */
public class CXFRouterBuilder extends AbstractRouterBuilder {

	public CXFRouterBuilder(Class<?> apiInterface) {
		super(apiInterface);
	}

	@Override
	public void configure() throws Exception {
		fromCxfEndpoint().process(createProcessor()).to("log:output");
	}

	@Override
	public Endpoint endpoint(String uri) throws NoSuchEndpointException {
		Endpoint endpoint = super.endpoint(uri);
		if (endpoint instanceof CxfEndpoint) {
			((CxfEndpoint) endpoint).setBus(BusFactory.getDefaultBus());
		}
		return endpoint;
	}

	protected CxfEndpoint cxfEndpoint() throws Exception {
		return (CxfEndpoint) endpoint(defaultEndpointAddress());
	}

	protected RouteDefinition fromCxfEndpoint() throws Exception {
		return from(cxfEndpoint());
	}

	protected String defaultEndpointAddress(String params) {
		return String.format("cxf:/%s/v1/soap?%s", apiInterface.getSimpleName(), params);
	}

	protected String defaultEndpointAddress() {
		return defaultEndpointAddress(getDefaultParamQuery());
	}


}
