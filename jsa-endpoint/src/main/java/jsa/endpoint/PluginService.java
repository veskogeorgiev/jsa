package jsa.endpoint;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.endpoint.spi.PortExposerPlugin;
import jsa.endpoint.spi.SourceGenerationPlugin;

public class PluginService {

    private static final PluginService INSTANCE = new PluginService();

    public static PluginService getInstance() {
        return INSTANCE;
    }

    private ServiceLoader<PortExposerPlugin> portLoader;
    private ServiceLoader<SourceGenerationPlugin> sourceLoader;

    private PluginService() {
        portLoader = ServiceLoader.load(PortExposerPlugin.class);
        sourceLoader = ServiceLoader.load(SourceGenerationPlugin.class);
    }

    public Iterable<PortExposerPlugin> loadPortExposers() {
        return portLoader;
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
