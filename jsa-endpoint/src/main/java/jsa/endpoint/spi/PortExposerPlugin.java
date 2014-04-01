package jsa.endpoint.spi;

import java.lang.annotation.Annotation;

import org.apache.camel.RoutesBuilder;

public interface PortExposerPlugin {

    Class<? extends Annotation> annotation();

    Class<? extends RoutesBuilder> routeBuilder();
}
