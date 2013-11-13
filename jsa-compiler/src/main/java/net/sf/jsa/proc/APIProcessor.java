package net.sf.jsa.proc;

import net.sf.jsa.annotations.API;
import net.sf.jsa.proc.meta.ServiceAPI;
import net.sf.jsa.proc.meta.ServiceMethod;
import net.sf.jsa.proc.meta.types.TypeFactory;
import java.lang.reflect.Method;
import javax.inject.Singleton;
import net.sf.jsa.proc.meta.ServiceVersion;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Singleton
public class APIProcessor {

	public ServiceAPI process(Class<?> apiInterface, Class<?> endpointClass) {
		ServiceAPI.Builder builder = ServiceAPI.newBuilder();
		builder.withName(apiInterface.getSimpleName());
		builder.withResourceClass(endpointClass);

		API api = apiInterface.getAnnotation(API.class);
		API.Version version = api.version();
		builder.withVersion(new ServiceVersion(version.number(), version.tag()));

		for (Method m : apiInterface.getDeclaredMethods()) {
			builder.withMethod(createMethod(m));
		}
		return builder.build();
	}

	private ServiceMethod createMethod(Method method) {
		ServiceMethod ret = new ServiceMethod();
		ret.setName(method.getName());
		ret.setMethod(method);

		for (Class<?> paramType : method.getParameterTypes()) {
			ret.getArguments().add(TypeFactory.createType(paramType));
		}
		ret.setReturnType(TypeFactory.createType(method.getReturnType()));
		return ret;
	}
}
