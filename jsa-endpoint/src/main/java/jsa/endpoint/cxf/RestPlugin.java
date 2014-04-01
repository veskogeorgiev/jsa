package jsa.endpoint.cxf;

import jsa.endpoint.spi.PortPlugin;

public class RestPlugin implements PortPlugin {

    @Override
    public Class<ExposeRest> annotation() {
        return ExposeRest.class;
    }

    @Override
    public Class<RestRouteBuilder> routeBuilder() {
        return RestRouteBuilder.class;
    }
}
