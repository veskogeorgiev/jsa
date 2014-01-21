package jsa.inject;

import javax.inject.Inject;
import javax.inject.Singleton;

import jsa.NotImplementedException;
import jsa.annotations.APIPort;
import lombok.extern.java.Log;

import com.google.inject.Injector;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 * @param <T> API interface
 */
@Log
@Singleton
public class PortImplementationLocator {

	@Inject private Injector injector;

	public Object locateServiceImplementor(Class<?> apiPort) throws NotImplementedException {
		Class<?> apiInterface = findServiceClassFromAPIPort(apiPort);
		try {
			return injector.getInstance(apiInterface);
		}
		catch (Exception e) {
			log.warning(String.format("No implementation for %s specified", apiInterface));
			throw new NotImplementedException(e);
		}
	}

	private Class<?> findServiceClassFromAPIPort(Class<?> apiPort) {
		APIPort port = apiPort.getAnnotation(APIPort.class);
		if (port != null) {
			switch (port.type()) {
			case ADAPTER:
				return apiPort;
			case DECORATOR:
				return port.api();
			default:
				break;
			}
		}
		return apiPort;
		//		throw new RuntimeException("");
	}

}
