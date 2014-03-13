package jsa.endpoint.cxf;

import jsa.endpoint.spi.PortExposer;

public class SoapExposer implements PortExposer {

	@Override
	public Class<ExposeSoap> annotation() {
		return ExposeSoap.class;
	}

	@Override
	public Class<SOAPRouteBuilder> routBuilder() {
		return SOAPRouteBuilder.class;
	}

	@Override
	public SourceGenerationConfig sourceGenerationConfig() {
		return null;
	}

}
