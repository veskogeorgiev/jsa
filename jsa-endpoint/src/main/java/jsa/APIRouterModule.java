package jsa;

import jsa.routes.CXFRouterBuilder;
import jsa.routes.RestRouterBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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

	protected Class<Ifc> ifc;

	protected List<Class<RoutesBuilder>> routesClasses = new LinkedList<>();

	protected List<RoutesBuilder> routesInstances = new LinkedList<>();

	public APIRouterModule(Class<Ifc> ifc) {
		this.ifc = ifc;
	}

	public APIRouterModule(Class<Ifc> ifc, RoutesBuilder... rbs) {
		this.ifc = ifc;
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
		addRoute(new RestRouterBuilder(ifc, restResource));
		return this;
	}

	public APIRouterModule<Ifc> exposeSoap() {
		addRoute(new CXFRouterBuilder(ifc));
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
	}
}
