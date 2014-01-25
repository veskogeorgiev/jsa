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

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jsa.annotations.API;
import jsa.compiler.meta.refl.ReflectionUtils;
import jsa.routes.APIPortProcessor;
import jsa.routes.ProcessorDelegate;
import jsa.routes.HasPorcessor;
import jsa.routes.ProcessorDecorator;
import jsa.routes.RestRouteBuilder;
import jsa.routes.SOAPRouteBuilder;

import org.apache.camel.Processor;
import org.apache.camel.RoutesBuilder;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public abstract class APIModule<Ifc> extends AbstractModule {

	protected Class<Ifc> apiInterface;
	protected Set<Processor> processors = new HashSet<>();
	protected List<RoutesBuilder> routesInstances = new LinkedList<>();

	@SuppressWarnings("unchecked")
	protected APIModule(RoutesBuilder... rbs) {
		this.apiInterface = (Class<Ifc>) ReflectionUtils.getTypeArgument(APIModule.class, getClass());
		Preconditions.checkState(apiInterface.isAnnotationPresent(API.class),
				String.format("%s is used to create %s, but it is not annotated with @API", apiInterface, getClass()));
		routesInstances.addAll(Arrays.asList(rbs));
	}

	public APIModule<Ifc> addRoute(RoutesBuilder builder) {
		routesInstances.add(builder);
		if (builder instanceof HasPorcessor) {
			processors.add(((HasPorcessor) builder).getProcessor());
		}
		return this;
	}

	public <PortType> APIModule<Ifc> exposeRest(Class<PortType> restPort, ProcessorDelegate delegate) {
		return addRoute(new RestRouteBuilder(restPort,
				new APIPortProcessor(restPort, delegate)));
	}

	public <PortType> APIModule<Ifc> exposeRest(Class<PortType> restPort, ProcessorDecorator decorator) {
		return addRoute(new RestRouteBuilder(restPort,
				new APIPortProcessor(restPort, decorator)));
	}

	public <PortType> APIModule<Ifc> exposeRest(Class<PortType> restPort) {
		return addRoute(new RestRouteBuilder(restPort,
				new APIPortProcessor(restPort)));
	}

	public <PortType> APIModule<Ifc> exposeSoap(Class<?> soapPort, ProcessorDelegate delegate) {
		return addRoute(new SOAPRouteBuilder(soapPort, 
				new APIPortProcessor(soapPort, delegate)));
	}

	public <PortType> APIModule<Ifc> exposeSoap(Class<?> soapPort, ProcessorDecorator decorator) {
		return addRoute(new SOAPRouteBuilder(soapPort, 
				new APIPortProcessor(soapPort, decorator)));
	}

	public <PortType> APIModule<Ifc> exposeSoap(Class<?> soapPort) {
		return addRoute(new SOAPRouteBuilder(soapPort, 
				new APIPortProcessor(soapPort)));
	}

	@Override
	protected final void configure() {
		// request injection for all registered processors
		for (Processor processor : processors) {
			requestInjection(processor);
		}
		Multibinder<RoutesBuilder> uriBinder = Multibinder.newSetBinder(binder(),
				RoutesBuilder.class);

		// request injection for all registered routes
		for (RoutesBuilder rb : routesInstances) {
			requestInjection(rb);
			uriBinder.addBinding().toInstance(rb);
		}

		customBindings(uriBinder);
	}

	protected void customBindings(Multibinder<RoutesBuilder> uriBinder) {
	}
}
