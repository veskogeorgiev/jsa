
package net.sf.jsa.routes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public interface AbstractResetResource {

	@GET
	@Path("__js")
	Response __js();
}
