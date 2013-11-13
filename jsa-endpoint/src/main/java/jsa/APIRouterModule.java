package jsa;

import jsa.routes.CXFRouterBuilder;
import jsa.routes.RestRouterBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import jsa.routes.EndpointBrigde;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.camel.RoutesBuilder;

/**
 *
 * @author vesko
 * @param <Ifc>
 */
@AllArgsConstructor
@NoArgsConstructor
public class APIRouterModule<Ifc> extends AbstractModule {

	protected Class<Ifc> apiInterface;

	protected List<Class<RoutesBuilder>> routesClasses = new LinkedList<>();

	protected List<RoutesBuilder> routesInstances = new LinkedList<>();

	public APIRouterModule(Class<Ifc> apiInterface) {
		this.apiInterface = apiInterface;
	}

	public APIRouterModule(Class<Ifc> apiInterface, RoutesBuilder... rbs) {
		this.apiInterface = apiInterface;
		routesInstances.addAll(Arrays.asList(rbs));
	}

	public APIRouterModule<Ifc> addRoute(Class<RoutesBuilder> builder) {
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
		addRoute(new CXFRouterBuilder(apiInterface));
		return this;
	}

	@Override
	protected void configure() {
		Multibinder<RoutesBuilder> uriBinder = Multibinder.newSetBinder(binder(), RoutesBuilder.class);
		for (Class<RoutesBuilder> rbc : routesClasses) {
			uriBinder.addBinding().to(rbc);
		}
		for (RoutesBuilder rb : routesInstances) {
			requestInjection(rb);
			uriBinder.addBinding().toInstance(rb);
		}
//		bind(apiInterface).annotatedWith(EndpointBrigde.class).
	}
}
