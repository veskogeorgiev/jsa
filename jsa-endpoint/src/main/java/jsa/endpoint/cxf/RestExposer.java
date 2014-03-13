package jsa.endpoint.cxf;

import jsa.compiler.js.JSGeneratorFactory;
import jsa.endpoint.spi.PortExposer;

public class RestExposer implements PortExposer {

	@Override
	public Class<ExposeRest> annotation() {
		return ExposeRest.class;
	}

	@Override
	public Class<RestRouteBuilder> routBuilder() {
		return RestRouteBuilder.class;
	}

	@Override
	public SourceGenerationConfig sourceGenerationConfig() {
		return new SourceGenerationConfig("_js", JSGeneratorFactory.class);
	}
}
