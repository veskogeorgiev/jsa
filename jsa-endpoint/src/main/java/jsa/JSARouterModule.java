package jsa;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import jsa.routes.RestRouterBuilder;
import jsa.routes.SOAPRouterBuilder;
import lombok.AllArgsConstructor;

import org.apache.camel.RoutesBuilder;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 *
 * @author vesko
 */
@AllArgsConstructor
public class JSARouterModule extends AbstractModule {

	protected List<Class<? extends RoutesBuilder>> routesClasses = new LinkedList<>();

	protected List<RoutesBuilder> routesInstances = new LinkedList<>();

	public JSARouterModule(RoutesBuilder... rbs) {
		routesInstances.addAll(Arrays.asList(rbs));
	}

	public JSARouterModule addRoute(Class<? extends RoutesBuilder> builder) {
		routesClasses.add(builder);
		return this;
	}

	public JSARouterModule addRoute(RoutesBuilder builder) {
		routesInstances.add(builder);
		return this;
	}

	public JSARouterModule exposeRest(Class<?> apiInterface, Class<?> restResource) {
		addRoute(new RestRouterBuilder(apiInterface, restResource));
		return this;
	}

	public JSARouterModule exposeSoap(Class<?> apiInterface, Class<?> apiPortClass) {
		addRoute(new SOAPRouterBuilder(apiInterface, apiPortClass));
		return this;
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