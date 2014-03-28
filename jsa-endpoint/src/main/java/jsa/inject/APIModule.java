/**
 * 
 */
package jsa.inject;

import java.util.Collection;

import javax.servlet.Filter;

import jsa.endpoint.APIProtector;
import jsa.endpoint.cxf.JaxRsConfig;
import jsa.endpoint.registry.APIRegistry;
import jsa.endpoint.registry.APIWithPorts;
import jsa.inject.web.APIProtectionFilter;
import jsa.inject.web.JSAServletModule;
import lombok.extern.slf4j.Slf4j;

import org.apache.camel.RoutesBuilder;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.DestinationRegistryImpl;

import com.google.inject.AbstractModule;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Slf4j
public class APIModule extends AbstractModule {

    protected String context;
    protected Bus bus;
    protected JSAServletModule servletModule;
    protected JSACamelModule camelModule;
    protected APIRegistry apiRegistry = new APIRegistry();

    public APIModule(String context) {
        this.context = context;

        bus = BusFactory.newInstance().createBus();
        bus.setExtension(new DestinationRegistryImpl(), DestinationRegistry.class);

        this.servletModule = new JSAServletModule(context, bus);
        this.camelModule = new JSACamelModule(context, bus);
    }

    public APIModule(String context, String packageNamePrefix) {
        this(context);
        apiRegistry.scan(packageNamePrefix);
    }

    public APIModule withJaxRsConfig(JaxRsConfig jaxRsConfig) {
        camelModule.withJaxRsConfig(jaxRsConfig);
        return this;
    }

    public APIModule withPort(Class<?> apiPort) {
        apiRegistry.addPort(apiPort);
        return this;
    }

    public APIModule withFilter(String path, Filter filter) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        servletModule.addFilter(context + path, filter);
        return this;
    }

    public APIModule withProtector(String path, APIProtector protector) {
        APIProtectionFilter filter = new APIProtectionFilter(protector);
        return withFilter(path, filter);
    }

    @Override
    protected void configure() {
        Collection<APIWithPorts<?>> ports = getApiPorts();

        log.info(toString(ports));

        for (RoutesBuilder routeBuilder : apiRegistry.getRouteBuilders()) {
            camelModule.addRoute(routeBuilder);
        }
        bind(InstanceLocator.class).to(GuiceInstanceLocator.class);

        install(servletModule);
        install(camelModule);
    }

    protected Collection<APIWithPorts<?>> getApiPorts() {
        return apiRegistry.getAPIs();
    }

    private String toString(Collection<APIWithPorts<?>> ports) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s on context [%s]\n", getClass().getSimpleName(), context));

        for (APIWithPorts<?> a : ports) {
            for (Class<?> apiPort : a.getPorts()) {
                sb.append(String.format("  API[%s]: Port[%s]\n",
                        a.getApi().getName(), apiPort.getName()));
            }
        }
        return sb.toString();
    }
}
