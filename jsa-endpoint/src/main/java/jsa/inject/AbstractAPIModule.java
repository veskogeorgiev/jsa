/**
 * 
 */
package jsa.inject;

import java.util.Collection;

import jsa.endpoint.cxf.JaxRsConfig;
import jsa.inject.registry.APIRegistry;
import jsa.inject.registry.APIWithPorts;
import jsa.inject.web.JSAServletModule;
import lombok.extern.slf4j.Slf4j;

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
public abstract class AbstractAPIModule extends AbstractModule {

    protected String context;
    protected Bus bus;
    protected JaxRsConfig jaxRsConfig;
    protected APIRegistry apiRegistry = new APIRegistry();

    // init Bus
    {
        bus = BusFactory.newInstance().createBus();
        bus.setExtension(new DestinationRegistryImpl(), DestinationRegistry.class);
    }

    public AbstractAPIModule(String context, String packageNamePrefix) {
        this.context = context;
        apiRegistry.scan(packageNamePrefix);
    }

    public AbstractAPIModule(String context) {
        this.context = context;
    }

    public AbstractAPIModule withJaxRsConfig(JaxRsConfig jaxRsConfig) {
        this.jaxRsConfig = jaxRsConfig;
        return this;
    }

    public AbstractAPIModule withPort(Class<?> apiPort) {
        apiRegistry.addPort(apiPort);
        return this;
    }

    @Override
    protected void configure() {
        JSAServletModule servletModule = createServletModule();
        InternalAPIModule apiModule = new InternalAPIModule(context, bus)
                .withJaxRsConfig(jaxRsConfig);

        Collection<APIWithPorts<?>> ports = getApiPorts();

        log.info(toString(ports));

        for (APIWithPorts<?> a : ports) {
            for (Class<?> apiPort : a.getPorts()) {
                try {
                    apiModule.automaticExpose(apiPort);
                }
                catch (Exception e) {
                    log.warn("Error exposing " + apiPort, e);
                }
            }
        }
        bind(InstanceLocator.class).to(InstanceLocatorImpl.class);

        install(servletModule);
        install(apiModule);
    }

    protected Collection<APIWithPorts<?>> getApiPorts() {
        return apiRegistry.getAPIs();
    }

    protected abstract JSAServletModule createServletModule();

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
