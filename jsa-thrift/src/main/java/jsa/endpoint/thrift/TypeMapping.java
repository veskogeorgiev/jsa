package jsa.endpoint.thrift;

import java.util.HashMap;
import java.util.Map;

public class TypeMapping {

	private Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();
	
	public void addMapping(Class<?> from, Class<?> to) {
		map.put(from, to);
		map.put(to, from);
	}

	public Class<?> getMapping(Class<?> cls) {
		return map.get(cls);
	}
}
