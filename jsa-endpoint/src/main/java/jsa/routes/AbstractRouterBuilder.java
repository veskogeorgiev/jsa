package jsa.routes;

import com.google.inject.Injector;
import jsa.MappingProcessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.inject.Inject;
import jsa.compiler.APIProcessor;
import jsa.compiler.meta.ServiceAPI;
import org.apache.camel.Processor;

import org.apache.camel.builder.RouteBuilder;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public abstract class AbstractRouterBuilder extends RouteBuilder {

	@Inject
	protected Injector injector;

	@Inject
	protected APIProcessor processor;

	protected final Class<?> apiInterface;

	private ServiceAPI serviceAPI;

	public AbstractRouterBuilder(Class<?> apiInterface) {
		this.apiInterface = apiInterface;
	}

	public ServiceAPI getServiceAPI() {
		if (serviceAPI == null) {
			serviceAPI = processor.process(apiInterface, null);
		}
		return serviceAPI;
	}

	protected Map<String, String> getDefaultParams() {
		Map<String, String> params = new HashMap<>();
		params.put("serviceClass", apiInterface.getName());
		params.put("setDefaultBus", "true");

		return params;
	}

	protected String getDefaultParamQuery() {
		return buildParamQuery(getDefaultParams());
	}

	protected String buildParamQuery(Map<String, String> params) {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, String> e : params.entrySet()) {
			builder.append(e.getKey()).append("=").append(e.getValue()).append("&");
		}
		return builder.substring(0, builder.length() - 1).toString();
	}

	protected Processor createProcessor() {
		return new MappingProcessor(apiInterface, injector);
	}
}
