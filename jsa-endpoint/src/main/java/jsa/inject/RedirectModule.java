/**
 * 
 */
package jsa.inject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import com.google.inject.servlet.ServletModule;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Log
public class RedirectModule extends ServletModule {

	private Map<String, String> sourceDestionMapping = new HashMap<String, String>();

	public RedirectModule() {
		//
	}

	public RedirectModule(String from, String to) {
		fromTo(from, to);
	}

	public RedirectModule fromTo(String from, String to) {
		sourceDestionMapping.put(from, to);
		return this;
	}

	@AllArgsConstructor
	@Log
	private static class RedirectServlet extends HttpServlet {

		private static final long serialVersionUID = 6822084125840281244L;

		private String destinationUrl;

		@Override
		protected void service(HttpServletRequest req, HttpServletResponse resp)
		      throws ServletException, IOException {

			log.info(String.format("Redirecting %s to %s", req.getRequestURL(), destinationUrl));

			req.getRequestDispatcher(destinationUrl).forward(req, resp);
		}
	}

	@Override
	protected void configureServlets() {
		log.info(sourceDestionMapping.toString());
		for (Entry<String, String> e : sourceDestionMapping.entrySet()) {
			RedirectServlet servlet = new RedirectServlet(e.getValue());
			serve(e.getKey()).with(servlet);
		}
	}
}