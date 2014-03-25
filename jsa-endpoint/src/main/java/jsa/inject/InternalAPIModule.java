/*******************************************************************************
 * Copyright (C) 2013 <a href="mailto:vesko.georgiev@icloud.com">Vesko Georgiev</a>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *******************************************************************************/
package jsa.inject;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jsa.InvalidConfigurationException;
import jsa.endpoint.APIModuleContextAware;
import jsa.endpoint.APIPortAware;
import jsa.endpoint.CxfBusAware;
import jsa.endpoint.HasProcessor;
import jsa.endpoint.PortExposerService;
import jsa.endpoint.cxf.JaxRsConfig;
import jsa.endpoint.cxf.JaxRsConfigAware;
import jsa.endpoint.cxf.ext.CxfRsComponentExt;
import jsa.endpoint.processors.DefaultAPIPortMeta;

import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.cxf.jaxrs.CxfRsComponent;
import org.apache.camel.guice.CamelModule;
import org.apache.cxf.Bus;

import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
class InternalAPIModule extends PrivateModule {

    protected Set<Processor> toInject = new HashSet<Processor>();
    protected List<RoutesBuilder> routesInstances = new LinkedList<RoutesBuilder>();

    protected Bus bus;
    protected String context;
    protected JaxRsConfig jaxRsConfig;

    public InternalAPIModule(String context, Bus bus) {
        this.context = context;
        this.bus = bus;
    }

    public InternalAPIModule withJaxRsConfig(JaxRsConfig jaxRsConfig) {
        this.jaxRsConfig = jaxRsConfig;
        return this;
    }

    public InternalAPIModule addRoute(RoutesBuilder routeBuilder) {
        routesInstances.add(routeBuilder);

        if (routeBuilder instanceof HasProcessor) {
            addToInject(((HasProcessor) routeBuilder).getProcessor());
        }
        if (routeBuilder instanceof CxfBusAware) {
            // avoid private module
            ((CxfBusAware) routeBuilder).setBus(bus);;
        }
        if (routeBuilder instanceof APIModuleContextAware) {
            ((APIModuleContextAware) routeBuilder).setAPIModuleContext(context);
        }
        if (routeBuilder instanceof JaxRsConfigAware) {
            ((JaxRsConfigAware) routeBuilder).setJaxRsConfig(jaxRsConfig);
        }
        // No check for APIPortAware. We assume that if you add a router
        // manually, it should be aware of the port it exposes

        return this;
    }

    public <PortType> InternalAPIModule automaticExpose(Class<PortType> apiPort)
            throws InstantiationException, IllegalAccessException, InvalidConfigurationException {
        DefaultAPIPortMeta meta = DefaultAPIPortMeta.create(apiPort);

        RoutesBuilder routeBuilder = null;

        if (meta.hasRouter()) {
            routeBuilder = meta.getRouter().newInstance();
        }
        else {
            routeBuilder = PortExposerService.getInstance().expose(apiPort);

            if (routeBuilder == null) {
                throw new InvalidConfigurationException(
                        "%s is defined as an APIPort but is not specified with expose mechanism.",
                        apiPort);
            }
        }

        if (routeBuilder instanceof APIPortAware) {
            ((APIPortAware) routeBuilder).setAPIPort(apiPort);
        }

        addRoute(routeBuilder);
        return this;
    }

    private void addToInject(Processor instance) {
        if (instance != null) {
            toInject.add(instance);
        }
    }

    @Override
    protected final void configure() {
        // request injection for all registered processors
        for (Object obj : toInject) {
            requestInjection(obj);
        }
        Multibinder<RoutesBuilder> uriBinder = Multibinder.newSetBinder(binder(),
                RoutesBuilder.class);

        // request injection for all registered routes
        for (RoutesBuilder rb : routesInstances) {
            uriBinder.addBinding().toInstance(rb);
        }
        install(new CamelModule());
    }

    @Provides
    CxfRsComponent cxfRsComponent(CamelContext camelContext) {
        CxfRsComponent cmp = new CxfRsComponentExt(camelContext, bus);

        return cmp;
    }

}
