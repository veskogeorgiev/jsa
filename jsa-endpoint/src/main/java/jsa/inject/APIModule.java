package jsa.inject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jsa.annotations.API;
import jsa.compiler.meta.refl.ReflectionUtils;
import jsa.proc.DefaultJSAProcessor;
import jsa.routes.RestRouterBuilder;
import jsa.routes.RouteWithProcessor;
import jsa.routes.SOAPRouterBuilder;

import org.apache.camel.Processor;
import org.apache.camel.RoutesBuilder;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class APIModule<Ifc> extends AbstractModule {

	protected Class<Ifc> apiInterface;
	protected Set<Processor> processors = new HashSet<>();
	protected List<RoutesBuilder> routesInstances = new LinkedList<>();

	@SuppressWarnings("unchecked")
	public APIModule(RoutesBuilder... rbs) {
		this.apiInterface = (Class<Ifc>) ReflectionUtils.getTypeArgument(APIModule.class, getClass());
		Preconditions.checkState(apiInterface.isAnnotationPresent(API.class), 
				String.format("%s is used to create %s, but it is not annotated with @API", apiInterface, getClass()));
		routesInstances.addAll(Arrays.asList(rbs));
	}

	public APIModule<Ifc> addRoute(RoutesBuilder builder) {
		if (builder instanceof RouteWithProcessor) {
			processors.add(((RouteWithProcessor) builder).getProcessor());
		}
		routesInstances.add(builder);
		return this;
	}

	public <PortType> APIModule<Ifc> exposeRest(Class<PortType> restPort, Processor processor) {
		processors.add(processor);
		addRoute(new RestRouterBuilder(apiInterface, restPort, processor));
		return this;
	}

	public <PortType> APIModule<Ifc> exposeRest(Class<PortType> restPort) {
		return exposeRest(restPort, createDefaultProcessor(restPort));
	}

	public <PortType> APIModule<Ifc> exposeSoap(Class<?> soapPort, Processor processor) {
		processors.add(processor);
		addRoute(new SOAPRouterBuilder(apiInterface, soapPort, processor));
		return this;
	}

	public <PortType> APIModule<Ifc> exposeSoap(Class<?> soapPort) {
		return exposeSoap(soapPort, createDefaultProcessor(soapPort));
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

	protected <ApiPort> Processor createDefaultProcessor(Class<ApiPort> apiPort) {
		return new DefaultJSAProcessor(apiPort);
	}
}