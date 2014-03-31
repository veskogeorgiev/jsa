package jsa.endpoint;

import jsa.InvalidConfigurationException;
import jsa.endpoint.processors.APIPortMeta;

import org.apache.camel.RoutesBuilder;

/**
 * 
 * @author <a href="mailto:vesko.m.georgiev@gmail.com">Vesko Georgiev<a>
 */
public class RouteBuilderFactory {

    public static final RouteBuilderFactory INSTANCE = new RouteBuilderFactory();

    private RouteBuilderFactory() {
        // no instantiation
    }

    /**
     * 
     * @param apiPort
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvalidConfigurationException
     */
    public RoutesBuilder createRouteBuilder(Class<?> apiPort)
            throws InstantiationException, IllegalAccessException, InvalidConfigurationException {
        APIPortMeta meta = APIPortMeta.create(apiPort);

        RoutesBuilder routeBuilder = null;

        if (meta.hasCustomRouter()) {
            routeBuilder = meta.getCustomRouter().newInstance();
        }
        else {
            routeBuilder = PortExposerService.getInstance().matchRouteBuilder(apiPort);

            if (routeBuilder == null) {
                throw new InvalidConfigurationException(
                        "%s is defined as an APIPort but is not specified with expose mechanism.",
                        apiPort);
            }
        }

        if (routeBuilder instanceof APIPortAware) {
            ((APIPortAware) routeBuilder).setAPIPort(apiPort);
        }

        return routeBuilder;
    }

}
