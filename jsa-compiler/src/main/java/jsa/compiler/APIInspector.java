package jsa.compiler;

import jsa.annotations.API;
import jsa.compiler.meta.ServiceAPIMetaData;
import jsa.compiler.meta.ServiceMethod;
import jsa.compiler.meta.types.TypeFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.inject.Singleton;

import jsa.compiler.meta.ServiceVersion;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Singleton
public class APIInspector {

	private static final API.Version DEFAULT_VERSION = new API.Version() {
		
		@Override
		public Class<? extends Annotation> annotationType() {
			return API.Version.class;
		}
		
		@Override
		public String tag() {
			return "v1";
		}
		
		@Override
		public int number() {
			return 1;
		}
	};

//	public ServiceAPI process(Class<?> apiInterface) {
//		return process(apiInterface, apiInterface);
//	}

	public ServiceAPIMetaData process(Class<?> apiInterface, Class<?> portClass) {
		ServiceAPIMetaData.Builder builder = ServiceAPIMetaData.builder();
		builder.name(apiInterface.getSimpleName());
		builder.apiPort(portClass);

		API api = apiInterface.getAnnotation(API.class);
		API.Version version = api != null ? api.version() : DEFAULT_VERSION;
		builder.version(new ServiceVersion(version.number(), version.tag()));

		for (Method m : portClass.getDeclaredMethods()) {
			builder.method(createMethod(m));
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