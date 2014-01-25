package jsa.routes;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MethodRepository {

	private Map<String, List<Method>> nameToMethod = new HashMap<>();

	public MethodRepository(Class<?> cls) {
		if (cls != null) {
			for (Method m : cls.getDeclaredMethods()) {
				methodsByName(m.getName()).add(m);
			}
			for (Method m : cls.getMethods()) {
				methodsByName(m.getName()).add(m);
			}
		}
	}

	public List<Method> methodsByName(String methodName) {
		List<Method> res = nameToMethod.get(methodName);
		if (res == null) {
			res = new LinkedList<>();
			nameToMethod.put(methodName, res);
		}
		return res;
	}

	public Method singleMethod(String methodName, Object[] parameters)
			throws NoSuchMethodException {
		List<Method> possibleMethods = methodsByName(methodName);
		for (Method method : possibleMethods) {
			if (method.getParameterTypes().length == parameters.length) {
				return method;
			}
		}
		throw new NoSuchMethodException();
	}
}
