package jsa.endpoint;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.endpoint.spi.PortPlugin;
import jsa.endpoint.spi.SourceGenerationPlugin;

import org.apache.camel.RoutesBuilder;

public class PluginService {

    private static final PluginService INSTANCE = new PluginService();

    public static PluginService getInstance() {
        return INSTANCE;
    }

    private ServiceLoader<PortPlugin> portLoader;
    private ServiceLoader<SourceGenerationPlugin> sourceLoader;

    private PluginService() {
        portLoader = ServiceLoader.load(PortPlugin.class);
        sourceLoader = ServiceLoader.load(SourceGenerationPlugin.class);
    }

    public RoutesBuilder matchRouteBuilder(Class<?> apiPort) throws InstantiationException,
            IllegalAccessException {
        RoutesBuilder routeBuilder = null;

        for (PortPlugin p : portLoader) {
            Class<? extends Annotation> a = p.annotation();
            if (a != null && apiPort.isAnnotationPresent(a)) {
                Class<? extends RoutesBuilder> cls = p.routeBuilder();
                if (cls != null) {
                    routeBuilder = cls.newInstance();
                }
            }
        }
        return routeBuilder;
    }

    public Map<String, SourceCodeGeneratorFactory> loadSourceGenerators() {
        Map<String, SourceCodeGeneratorFactory> res = new HashMap<String, SourceCodeGeneratorFactory>();

        try {
            for (SourceGenerationPlugin p : sourceLoader) {
                res.put(p.context(), p.factory().newInstance());
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Could not instantiate source generators", e);
        }
        return res;
    }
}
