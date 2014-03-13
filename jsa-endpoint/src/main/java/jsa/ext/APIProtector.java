/**
 * 
 */
package jsa.ext;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public interface APIProtector {

	boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
