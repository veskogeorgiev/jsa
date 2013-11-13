/**
 * 
 */
package jsa.web;

import javax.inject.Singleton;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@SuppressWarnings("serial")
@Singleton
public class JSAServlet extends CXFNonSpringServlet {

	@Override
	protected void loadBus(ServletConfig sc) {
        setBus(BusFactory.getDefaultBus());
	}

	@Override
	protected void invoke(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		super.invoke(request, response);
	}
	
}
