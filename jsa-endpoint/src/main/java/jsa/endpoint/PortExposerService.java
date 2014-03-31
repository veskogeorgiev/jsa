package jsa.endpoint;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

import jsa.endpoint.spi.PortExposer;
import jsa.endpoint.spi.PortExposer.SourceGenerationConfig;

import org.apache.camel.RoutesBuilder;

public class PortExposerService {

    private static final PortExposerService INSTANCE = new PortExposerService();

    public static PortExposerService getInstance() {
        return INSTANCE;
    }

    private ServiceLoader<PortExposer> loader;

    private PortExposerService() {
        loader = ServiceLoader.load(PortExposer.class);
    }

    public RoutesBuilder matchRouteBuilder(Class<?> apiPort) throws InstantiationException,
            IllegalAccessException {
        RoutesBuilder routeBuilder = null;

        for (PortExposer exposer : loader) {
            Class<? extends Annotation> a = exposer.annotation();
            if (a != null && apiPort.isAnnotationPresent(a)) {
                Class<? extends RoutesBuilder> cls = exposer.routeBuilder();
                if (cls != null) {
                    routeBuilder = cls.newInstance();
                }
            }
        }
        return routeBuilder;
    }

    public Set<SourceGenerationConfig> discoverSourceGenerators() {
        Set<SourceGenerationConfig> res = new HashSet<SourceGenerationConfig>();

        for (PortExposer exposer : loader) {
            SourceGenerationConfig[] sgs = exposer.sourceGenerationConfig();
            if (sgs != null) {
                for (SourceGenerationConfig sg : sgs) {
                    res.add(sg);
                }
            }
        }
        return res;
    }
}
