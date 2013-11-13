package net.sf.jsa;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.camel.CamelContext;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Singleton
public class APICamelServer {

	@Inject
	private CamelContext camelContext;
	@Inject
	private Logger logger;

	public void start() {
		try {
			camelContext.start();
			logger.info(String.format("Camel Server started on %s", camelContext.getEndpointMap()));
		}
		catch (Exception e) {
			logger.info("Camel Server failed to start");
		}
	}

	public void stop() {
		try {
			camelContext.stop();
			logger.info("Camel Server stopped");
		}
		catch (Exception e) {
			logger.info("Camel Server failed to stop");
		}
	}
}
