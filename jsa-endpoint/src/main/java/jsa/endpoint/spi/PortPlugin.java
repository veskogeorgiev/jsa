package jsa.endpoint.spi;

import java.lang.annotation.Annotation;

import org.apache.camel.RoutesBuilder;

public interface PortPlugin {

    Class<? extends Annotation> annotation();

    Class<? extends RoutesBuilder> routeBuilder();
}
