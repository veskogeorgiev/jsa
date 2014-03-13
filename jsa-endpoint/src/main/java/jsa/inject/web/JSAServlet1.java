package jsa.inject.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.cxf.Bus;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

@Slf4j
public class JSAServlet1 extends CXFNonSpringServlet {
	private static final long serialVersionUID = -8504554374979363076L;

	private Bus bus;
	private String context;

	public JSAServlet1(Bus bus, String context) {
		this.bus = bus;
		this.context = context;

		log.info("Setting CamelServlet's name {}", this.context);
	}

	@Override
	protected void loadBus(ServletConfig sc) {
		setBus(bus);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
		System.out.println("IN 1");
		super.service(req, resp);
		System.out.println("OUT 1");
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException,
	      IOException {
		System.out.println("IN 2");
		super.service(req, res);
		System.out.println("flush");
		res.flushBuffer();
		System.out.println("close");
		res.getOutputStream().close();
		System.out.println("OUT 2");
	}

}
