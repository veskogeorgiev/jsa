package jsa.endpoint.thrift;

import jsa.endpoint.spi.PortExposerPlugin;
import jsa.endpoint.thrift.annotations.ExposeThrift;

public class ThriftPlugin implements PortExposerPlugin {

	@Override
	public Class<ExposeThrift> annotation() {
		return ExposeThrift.class;
	}

	@Override
	public Class<ThriftRouteBuilder> routeBuilder() {
		return ThriftRouteBuilder.class;
	}

//	@Override
//	public SourceGenerationConfig[] sourceGenerationConfig() {
//		return new SourceGenerationConfig[] {
//			new SourceGenerationConfig("_t", ThriftGeneratorFactory.class)
//		};
//	}
}
