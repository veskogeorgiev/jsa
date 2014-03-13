/**
 * 
 */
package jsa.inject.registry;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import jsa.annotations.APIPort;

import org.reflections.Reflections;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class APIRegistry {
	private Multimap<Class<?>, Class<?>> apis = HashMultimap.create();
	private APIPortToAPI apiPortToAPI = APIPortToAPI.INSTANCE;

	public void scan(String packagePrefix) {
		Reflections reflections = new Reflections(packagePrefix);
		Set<Class<?>> apiPorts = reflections.getTypesAnnotatedWith(APIPort.class);

		for (Class<?> port : apiPorts) {
			addPort(port);
		}
	}

	public void addPort(Class<?> port) {
		apis.put(apiPortToAPI.apply(port), port);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public Collection<APIWithPorts<?>> getAPIs() {
		Collection<APIWithPorts<?>> ret = new LinkedList<APIWithPorts<?>>();
		for (Entry<Class<?>, Collection<Class<?>>> e : apis.asMap().entrySet()) {
			ret.add(new APIWithPorts(e.getKey(), e.getValue()));
		}
		return ret;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<Class<?>, Collection<Class<?>>> e : apis.asMap().entrySet()) {
			sb.append(e.getKey().getName()).append(": ").append(e.getValue()).append("\n");
		}
		return sb.toString();
	}
}
