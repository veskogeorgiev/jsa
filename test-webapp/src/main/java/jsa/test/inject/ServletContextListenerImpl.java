/**
 *
 */
package jsa.test.inject;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import jsa.endpoint.cxf.JaxRsConfig;
import jsa.ext.BasicHttpAuthProtector;
import jsa.inject.APIModule;
import jsa.inject.ProtectedAPIModule;
import jsa.test.api.v1.Item;
import jsa.test.api.v1.ItemsAPI;
import jsa.test.port.api.v1.ItemsAPIRest;

import org.apache.bval.guice.ValidationModule;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class ServletContextListenerImpl extends GuiceServletContextListener {

	private Injector injector;

	@Override
	protected Injector getInjector() {
		if (injector == null) {
			try {
				injector = Guice.createInjector(
				      new ProtectedAPIModule("/secretapi", "jsa.test.port.secapi",
				            new BasicHttpAuthProtector("/secretapi", "Internal API", "api", "123qweasd")),
				      new ProtectedAPIModule("/api", "jsa.test.port.api",
				            new BasicHttpAuthProtector("/api", "Internal API", "api", "123qweasd"))
				            .withJaxRsConfig(new JaxRsConfig() {
					            @Override
					            public void config(JAXRSServerFactoryBean factoryBean) {
						            factoryBean.setProvider(new JacksonJsonProvider());
					            }
				            }),
				      new APIModule("/test").withPort(ItemsAPIRest.class),
				      new TestAPIModule(), 
				      new ValidationModule(),
				      new ServletModule() {
				      	@Override
				      	protected void configureServlets() {
				      	   filter("/*").through(new Filter() {
									@Override
									public void init(FilterConfig filterConfig) throws ServletException {
									}
									
									@Override
									public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
									      throws IOException, ServletException {
										Cookie[] cookies = ((HttpServletRequest)request).getCookies();
										if (cookies != null) {
											for(Cookie cookie: cookies) {
												System.out.println(cookie.getName() + " " + cookie.getValue());
											}
										}
										chain.doFilter(request, response);
									}
									
									@Override
									public void destroy() {
									}
								});
				      	   super.configureServlets();
				      	}
				      });
			}
			catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		return injector;
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		getInjector().injectMembers(this);
	}

	public static void main(String[] args) {
		ServletContextListenerImpl ctx = new  ServletContextListenerImpl();
		Injector inj = ctx.getInjector();
		ItemsAPI api = inj.getInstance(ItemsAPI.class);
		Item item = new Item();
		api.save(item);

//		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
//		Validator v = vf.getValidator();
//		Set<ConstraintViolation<Item>> res = v.validate(i);
//		System.out.println(res);
   }
}
