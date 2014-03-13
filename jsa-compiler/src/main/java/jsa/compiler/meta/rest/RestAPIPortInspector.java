/**
 * 
 */
package jsa.compiler.meta.rest;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import jsa.compiler.APIPortInspector;
import jsa.compiler.meta.rest.RestMethodMeta.Builder;
import jsa.compiler.meta.types.TypeFactory;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class RestAPIPortInspector implements APIPortInspector<RestPortMeta> {

	private static final RestAPIPortInspector instance = new RestAPIPortInspector();

	public static RestAPIPortInspector getInstance() {
		return instance;
	}

	private RestAPIPortInspector() {
		// no instantiation
	}

	@Override
	public RestPortMeta inspect(Class<?> portClass) {
		RestPortMeta res = new RestPortMeta(portClass);

		TypeFactory typeFactory = new TypeFactory(res.getApiMeta().getPackage(), res.getPackage());

		List<RestMethodMeta> portMethods = new LinkedList<RestMethodMeta>();
		res.setMethods(portMethods);

		for (Method m : portClass.getDeclaredMethods()) {
			portMethods.add(createMethod(typeFactory, res, m));
		}
		return res;
	}

	private RestMethodMeta createMethod(TypeFactory typeFactory, RestPortMeta restMeta, Method method) {
		Builder builder = RestMethodMeta.builder();
		builder.restMeta(restMeta);
		builder.method(method);
		builder.returnType(typeFactory.createType(method.getReturnType()));

		for (Class<?> paramType : method.getParameterTypes()) {
			builder.argument(typeFactory.createType(paramType));
		}

		return builder.build();
	}

}
