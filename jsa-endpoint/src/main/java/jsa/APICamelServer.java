package jsa;


import java.util.Map;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.java.Log;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Singleton
@Log
public class APICamelServer {

	@Inject
	private CamelContext camelContext;

	public void start() {
		try {
			camelContext.start();
			for (Map.Entry<String, Endpoint> e : camelContext.getEndpointMap().entrySet()) {
				log.info(String.format("%s: %s", e.getKey(), e.getValue()));
			}
			log.info(String.format("Camel Server started on %s", camelContext.getEndpointMap()));
		}
		catch (Exception e) {
			log.info("Camel Server failed to start");
		}
	}

	public void stop() {
		try {
			camelContext.stop();
			log.info("Camel Server stopped");
		}
		catch (Exception e) {
			log.info("Camel Server failed to stop");
		}
	}
}
