package jsa;


import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.apachecommons.CommonsLog;

import org.apache.camel.CamelContext;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Singleton
@CommonsLog
public class APICamelServer {

	@Inject
	private CamelContext camelContext;

	public void start() {
		try {
			camelContext.start();
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
