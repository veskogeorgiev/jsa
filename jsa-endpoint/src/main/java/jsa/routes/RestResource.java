
package jsa.routes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public interface RestResource {

	@GET
	@Path("__js")
	@Produces(MediaType.TEXT_PLAIN)
	Response __js();
}
