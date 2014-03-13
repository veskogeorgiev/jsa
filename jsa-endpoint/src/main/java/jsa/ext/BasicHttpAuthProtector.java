/**
 * 
 */
package jsa.ext;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;

import org.apache.commons.codec.binary.Base64;

import com.google.common.base.Charsets;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@AllArgsConstructor
public class BasicHttpAuthProtector implements APIProtector {

	private String context;
	private String realm;
	private String username;
	private String password;

	@Override
	public boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String reqUrl = request.getRequestURI().substring(context.length());
		if (!reqUrl.isEmpty() && !reqUrl.equals("/")) {
			return true;
		}
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;

		final String auth = httpRequest.getHeader("Authorization");

		if (auth != null) {
			final int index = auth.indexOf(' ');
			if (index > 0) {
				String base64Encoded = auth.substring(index);
				String decoded = new String(Base64.decodeBase64(base64Encoded), Charsets.UTF_8);
				final String[] credentials = decoded.split(":");

				if (credentials.length == 2 && username.equals(credentials[0])
				      && password.equals(credentials[1])) {
					return true;
				}
			}
		}

		httpResponse.setHeader("WWW-Authenticate", "Basic realm=\"" + realm + "\"");
		httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);

		return false;
	}
}
