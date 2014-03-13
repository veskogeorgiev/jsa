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
import jsa.annotations.APIContext;
import jsa.endpoint.HasPorcessor;
import jsa.endpoint.JSARouteBuilder;
import jsa.endpoint.PortExposerService;
import jsa.endpoint.cxf.JaxRsConfig;
import jsa.endpoint.processors.DefaultAPIPortMeta;
import jsa.ext.CxfRsComponentExt;
import jsa.ext.SourceGenerator;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.cxf.jaxrs.CxfRsComponent;
import org.apache.camel.guice.CamelModule;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.DestinationRegistryImpl;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Injector;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
class InternalAPIModule extends PrivateModule {

	protected Set<Object> toInject = new HashSet<Object>();
	protected List<RoutesBuilder> routesInstances = new LinkedList<RoutesBuilder>();

	protected Bus bus;
	protected String context;

	protected JaxRsConfig jaxRsConfig;

	public InternalAPIModule(String context, Bus bus) {
		this.context = context;
		this.bus = bus;
		bus.setExtension(new DestinationRegistryImpl(), DestinationRegistry.class);
	}

	public InternalAPIModule withJaxRsConfig(JaxRsConfig jaxRsConfig) {
		this.jaxRsConfig = jaxRsConfig;
		return this;
	}

	public InternalAPIModule addRoute(RoutesBuilder builder) {
		routesInstances.add(builder);
		addToInject(builder);
		if (builder instanceof HasPorcessor) {
			addToInject(((HasPorcessor) builder).getProcessor());
		}
		return this;
	}

	public <PortType> InternalAPIModule automaticExpose(Class<PortType> apiPort) throws InstantiationException, IllegalAccessException, InvalidConfigurationException {
		DefaultAPIPortMeta meta = DefaultAPIPortMeta.create(apiPort);

		RoutesBuilder routeBuilder = null;

		if (meta.hasRouter()) {
			routeBuilder = meta.getRouter().newInstance();
		}
		else {
			routeBuilder = PortExposerService.getInstance().expose(apiPort);

			if (routeBuilder == null) {
				throw new InvalidConfigurationException("%s is defined as an APIPort but is not specified with expose mechanism.", apiPort);
			}
		}

		if (routeBuilder instanceof JSARouteBuilder) {

			((JSARouteBuilder) routeBuilder).init(apiPort);
		}
		addRoute(routeBuilder);
		return this;
	}

	private void addToInject(Object instance) {
		if (instance != null) {
			toInject.add(instance);
		}
	}

	@Override
	protected final void configure() {
		install(new CamelModule());

		bind(Bus.class).toInstance(bus);
		bind(String.class).annotatedWith(APIContext.class).toInstance(context);

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
	}

	@Provides
	CxfRsComponent cxfRsComponent(CamelContext camelContext, Injector injector) {
		CxfRsComponent cmp = new CxfRsComponentExt(camelContext);
		injector.injectMembers(cmp);

		return cmp;
	}

	@Provides
	JAXRSServerFactoryBean factoryBean(Bus bus, SourceGenerator sourceGenerator) {
		JAXRSServerFactoryBean bean = new JAXRSServerFactoryBean();
		bean.setBus(bus);

		bean.setProvider(sourceGenerator);

		if (jaxRsConfig != null) {
			jaxRsConfig.config(bean);
		}
		else {
			bean.setProvider(new JacksonJsonProvider());
		}
		return bean;
	}

}
