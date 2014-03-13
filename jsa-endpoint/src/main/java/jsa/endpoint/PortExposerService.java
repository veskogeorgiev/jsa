package jsa.endpoint;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

import jsa.endpoint.spi.PortExposer;
import jsa.endpoint.spi.PortExposer.SourceGenerationConfig;

import org.apache.camel.RoutesBuilder;

public class PortExposerService {

	private static PortExposerService service;

	private ServiceLoader<PortExposer> loader;

	private PortExposerService() {
		loader = ServiceLoader.load(PortExposer.class);
	}

	public static synchronized PortExposerService getInstance() {
		if (service == null) {
			service = new PortExposerService();
		}
		return service;
	}

	public RoutesBuilder expose(Class<?> apiPort) throws InstantiationException,
	      IllegalAccessException {
		RoutesBuilder routeBuilder = null;

		for (PortExposer exposer : loader) {
			Class<? extends Annotation> a = exposer.annotation();
			if (apiPort.isAnnotationPresent(a)) {
				Class<? extends RoutesBuilder> cls = exposer.routBuilder();
				routeBuilder = cls.newInstance();
			}
		}
		return routeBuilder;
	}

	public Set<SourceGenerationConfig> sourceGenerators() {
		Set<SourceGenerationConfig> res = new HashSet<SourceGenerationConfig>();

		for (PortExposer exposer : loader) {
			SourceGenerationConfig sg = exposer.sourceGenerationConfig();
			if (sg != null) {
				res.add(sg);
			}
		}
		return res;
	}
}
