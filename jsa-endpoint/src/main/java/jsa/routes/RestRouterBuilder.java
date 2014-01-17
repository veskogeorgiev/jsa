package jsa.routes;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.NoSuchEndpointException;
import org.apache.camel.component.cxf.jaxrs.CxfRsEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.cxf.BusFactory;

/**
 *
 * @author vesko
 */
public class RestRouterBuilder extends AbstractRouterBuilder {

	public RestRouterBuilder(Class<?> apiInterface, Class<?> restDecorator) {
		super(apiInterface, restDecorator);
	}

	@Override
	public void configure() throws Exception {
		CxfRsEndpoint endpoint = restEndpoint(apiPortClass);
		
		from(endpoint).process(createProcessor()).to("log:output");

//		from(restEndpoint(ApiListingResourceJSON.class))
//			.process(new SwaggerProcessor(injector))
//			.to("log:output");
	}

	@Override
	public Endpoint endpoint(String uri) throws NoSuchEndpointException {
		Endpoint endpoint = super.endpoint(uri);
		if (endpoint instanceof CxfRsEndpoint) {
			((CxfRsEndpoint) endpoint).setBus(BusFactory.getDefaultBus());
		}
		return endpoint;
	}

	protected CxfRsEndpoint restEndpoint(Class<?> restDecorator) throws Exception {
		Map<String, String> params = getDefaultParams();
		params.put("setDefaultBus", "true");

		String paramString = buildParamQuery(params);
		String endpointAddress = defaultEndpointAddress("cxfrs", paramString);

		CxfRsEndpoint endpoint = (CxfRsEndpoint) endpoint(endpointAddress);
		endpoint.addResourceClass(restDecorator);
		return endpoint;
	}

	protected RouteDefinition fromRestEndpoint(Class<?> restDecorator) throws Exception {
		return from(restEndpoint(restDecorator));
	}
}
