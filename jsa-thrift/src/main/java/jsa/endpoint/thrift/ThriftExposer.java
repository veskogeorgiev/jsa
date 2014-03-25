package jsa.endpoint.thrift;

import jsa.compiler.thrift.ThriftGeneratorFactory;
import jsa.endpoint.spi.PortExposer;
import jsa.endpoint.thrift.annotations.ExposeThrift;

public class ThriftExposer implements PortExposer {

	@Override
	public Class<ExposeThrift> annotation() {
		return ExposeThrift.class;
	}

	@Override
	public Class<ThriftRouteBuilder> routBuilder() {
		return ThriftRouteBuilder.class;
	}

	@Override
	public SourceGenerationConfig[] sourceGenerationConfig() {
		return new SourceGenerationConfig[] {
			new SourceGenerationConfig("_t", ThriftGeneratorFactory.class)
		};
	}
}
