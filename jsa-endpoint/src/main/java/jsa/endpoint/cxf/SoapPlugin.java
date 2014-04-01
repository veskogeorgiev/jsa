package jsa.endpoint.cxf;

import jsa.endpoint.spi.PortExposerPlugin;

public class SoapPlugin implements PortExposerPlugin {

    @Override
    public Class<ExposeSoap> annotation() {
        return ExposeSoap.class;
    }

    @Override
    public Class<SoapRouteBuilder> routeBuilder() {
        return SoapRouteBuilder.class;
    }

}
