package jsa.endpoint.cxf;

import jsa.endpoint.spi.PortExposerPlugin;

public class RestPlugin implements PortExposerPlugin {

    @Override
    public Class<ExposeRest> annotation() {
        return ExposeRest.class;
    }

    @Override
    public Class<RestRouteBuilder> routeBuilder() {
        return RestRouteBuilder.class;
    }
}
