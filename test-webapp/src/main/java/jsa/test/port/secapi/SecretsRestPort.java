/**
 * 
 */
package jsa.test.port.secapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.cxf.ExposeRest;
import jsa.test.secapi.SecretsAPI;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@APIPort(api = SecretsAPI.class, context = "rest", type = APIPortType.DECORATOR)
@ExposeRest
public interface SecretsRestPort extends SecretsAPI {

	@Path("name")
	@GET
	String getName();
}
