package jsa.endpoint.cxf;

import jsa.endpoint.spi.PortPlugin;

public class SoapPlugin implements PortPlugin {

    @Override
    public Class<ExposeSoap> annotation() {
        return ExposeSoap.class;
    }

    @Override
    public Class<SoapRouteBuilder> routeBuilder() {
        return SoapRouteBuilder.class;
    }

}
