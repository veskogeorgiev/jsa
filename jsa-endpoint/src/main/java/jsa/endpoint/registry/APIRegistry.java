/**
 * 
 */
package jsa.endpoint.registry;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import jsa.annotations.APIPort;
import jsa.endpoint.RouteBuilderFactory;
import lombok.extern.slf4j.Slf4j;

import org.apache.camel.RoutesBuilder;
import org.reflections.Reflections;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Slf4j
public class APIRegistry {
    private Multimap<Class<?>, Class<?>> apis = HashMultimap.create();
    private APIPortToAPI apiPortToAPI = APIPortToAPI.INSTANCE;
    private RouteBuilderFactory factory = RouteBuilderFactory.INSTANCE;

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

    public Collection<RoutesBuilder> getRouteBuilders() {
        Collection<RoutesBuilder> res = new LinkedList<RoutesBuilder>();

        for (APIWithPorts<?> a : getAPIs()) {
            for (Class<?> apiPort : a.getPorts()) {
                try {
                    RoutesBuilder routeBuilder = factory.createRouteBuilder(apiPort);
                    res.add(routeBuilder);
                }
                catch (Exception e) {
                    log.warn("Error exposing " + apiPort, e);
                }
            }
        }
        return res;
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
