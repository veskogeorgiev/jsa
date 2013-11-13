/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jsa.web;

import com.google.inject.Provides;
import com.google.inject.servlet.ServletModule;
import net.sf.jsa.ext.CxfRsComponentExt;
import lombok.AllArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.component.cxf.jaxrs.CxfRsComponent;
import org.apache.camel.guice.CamelModule;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@AllArgsConstructor
public class JSAServletModule extends ServletModule {
	
	private String context;
	
	public JSAServletModule() {
		this("/api");
	}
	
	@Override
	protected void configureServlets() {
		install(new CamelModule());
		serve(context + "/*").with(JSAServlet.class);
		serve(context).with(JSAServlet.class);
	}
	
	@Provides
	CxfRsComponent cxfRsComponent(CamelContext camelContext) {
		CxfRsComponent cmp = new CxfRsComponentExt(camelContext);
		return cmp;
	}
}
