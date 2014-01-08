package jsa.routes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import jsa.JSAProcessor;
import jsa.annotations.APIPort;
import jsa.compiler.APIProcessor;
import jsa.compiler.meta.ServiceAPI;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.google.common.base.Preconditions;
import com.google.inject.Injector;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public abstract class AbstractRouterBuilder extends RouteBuilder {

	@Inject protected Injector injector;

	@Inject protected APIProcessor processor;

	protected final Class<?> apiInterface;
	protected final Class<?> apiPortClass;
	protected final APIPort apiPort;

	private ServiceAPI serviceAPI;

	public AbstractRouterBuilder(Class<?> apiInterface, Class<?> apiPortClass) {
		this.apiInterface = apiInterface;
		this.apiPortClass = apiPortClass;
		this.apiPort = apiPortClass.getAnnotation(APIPort.class);

		Preconditions.checkNotNull(apiPort,
				"If class %s is to be used as a port it must be annotated with @APIPort.");
	}

	public ServiceAPI getServiceAPI() {
		if (serviceAPI == null) {
			serviceAPI = processor.process(apiInterface, apiPortClass);
		}
		return serviceAPI;
	}

	protected Map<String, String> getDefaultParams() {
		Map<String, String> params = new HashMap<>();
		params.put("serviceClass", apiPortClass.getName());
		params.put("setDefaultBus", "true");

		return params;
	}

	protected String defaultEndpointAddress(String protocol, String params) {
		return String.format("%s://%s/%s?%s", protocol, getServiceAPI().getUrl(), apiPort.context(), params);
	}

	protected String defaultEndpointAddress(String protocol) {
		return defaultEndpointAddress(protocol, getDefaultParamQuery());
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
		return new JSAProcessor(apiPortClass, injector);
	}
}
