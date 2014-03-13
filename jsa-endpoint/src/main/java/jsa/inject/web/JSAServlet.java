package jsa.inject.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.component.servlet.DefaultHttpRegistry;
import org.apache.camel.component.servlet.HttpRegistry;
import org.apache.cxf.Bus;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

@AllArgsConstructor
@Slf4j
public class JSAServlet extends HttpServlet {
	private static final long serialVersionUID = -8504554374979363076L;

	private Bus bus;
	private String context;

	private JSACXFServlet cxfServlet = new JSACXFServlet();
	private JSACamelServlet camelServlet = new JSACamelServlet();

	private HttpServlet[] delegates = new HttpServlet[] {
	      cxfServlet, camelServlet
	};

	private HttpRegistry registry;

	public JSAServlet(Bus bus, String context) {
		this.bus = bus;
		this.context = context;

		this.registry = DefaultHttpRegistry.getHttpRegistry(this.context);

		log.info("Setting CamelServlet's name {}", this.context);
		camelServlet.setServletName(this.context);
		registry.register(camelServlet);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (camelServlet.canHandle(req)) {
			camelServlet.service(req, res);
		}
		else {
			cxfServlet.service(req, res);
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
		for (HttpServlet servlet : delegates) {
			servlet.init();
		}
		camelServlet.setServletName(this.context);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		for (HttpServlet servlet : delegates) {
			servlet.init(config);
		}
		camelServlet.setServletName(this.context);
	}

	@Override
	public void destroy() {
		super.destroy();
		for (HttpServlet servlet : delegates) {
			servlet.destroy();
		}
	}

	/**
	 * 
	 * @author <a href="mailto:vesko.m.georgiev@gmail.com">Vesko Georgiev<a>
	 */
	@AllArgsConstructor
	class JSACXFServlet extends CXFNonSpringServlet {
		private static final long serialVersionUID = -5683222201447667591L;

		@Override
		protected void loadBus(ServletConfig sc) {
			setBus(bus);
		}
	}

	/**
	 * 
	 * @author <a href="mailto:vesko.m.georgiev@gmail.com">Vesko Georgiev<a>
	 */
	class JSACamelServlet extends CamelHttpTransportServlet {
		private static final long serialVersionUID = 2863542950972764850L;

		boolean canHandle(HttpServletRequest request) {
			return resolve(request) != null;
		}
	}

}
