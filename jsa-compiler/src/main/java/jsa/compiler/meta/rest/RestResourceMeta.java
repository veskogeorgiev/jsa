
package jsa.compiler.meta.rest;

import java.lang.reflect.Method;

import javax.ws.rs.Path;

import lombok.AllArgsConstructor;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@AllArgsConstructor
public class RestResourceMeta {

	private final Class<?> resourceClass;

	public String getAPIPath() {
		return getPath(resourceClass.getAnnotation(Path.class));
	}

	public RestMethodMeta getRestMethod(Method method) {
		try {
			method = resourceClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new RestMethodMeta(this, method);
	}

	private String getPath(Path path) {
		return path != null ? path.value() : "";
	}

}
