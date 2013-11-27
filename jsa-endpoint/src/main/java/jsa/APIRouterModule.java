package jsa;

import jsa.annotations.ImplementorBrigde;
import jsa.routes.SOAPRouterBuilder;
import jsa.routes.RestRouterBuilder;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;

import org.apache.camel.RoutesBuilder;

/**
 *
 * @author vesko
 * @param <Ifc>
 */
@AllArgsConstructor
public class APIRouterModule<Ifc> extends AbstractModule {

	protected Class<Ifc> apiInterface;

	protected List<Class<? extends RoutesBuilder>> routesClasses = new LinkedList<>();

	protected List<RoutesBuilder> routesInstances = new LinkedList<>();

	public APIRouterModule(Class<Ifc> apiInterface) {
		this.apiInterface = apiInterface;
	}

	public APIRouterModule(Class<Ifc> apiInterface, RoutesBuilder... rbs) {
		this.apiInterface = apiInterface;
		routesInstances.addAll(Arrays.asList(rbs));
	}

	public APIRouterModule<Ifc> addRoute(Class<? extends RoutesBuilder> builder) {
		routesClasses.add(builder);
		return this;
	}

	public APIRouterModule<Ifc> addRoute(RoutesBuilder builder) {
		routesInstances.add(builder);
		return this;
	}

	public APIRouterModule<Ifc> exposeRest(Class<?> restResource) {
		addRoute(new RestRouterBuilder(apiInterface, restResource));
		return this;
	}

	public APIRouterModule<Ifc> exposeSoap() {
		addRoute(new SOAPRouterBuilder(apiInterface));
		return this;
	}

	protected void bindBridge(Class<? extends Ifc> cls) {
		bind(apiInterface).annotatedWith(ImplementorBrigde.class).to(cls);
	}

	@Override
	protected final void configure() {
		Multibinder<RoutesBuilder> uriBinder = Multibinder.newSetBinder(binder(), RoutesBuilder.class);
		for (Class<? extends RoutesBuilder> rbc : routesClasses) {
			uriBinder.addBinding().to(rbc);
		}
		for (RoutesBuilder rb : routesInstances) {
			requestInjection(rb);
			uriBinder.addBinding().toInstance(rb);
		}
		customBindings();
	}
	
	protected void customBindings() {
	}
}