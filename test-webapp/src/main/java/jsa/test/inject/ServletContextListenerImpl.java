/**
 *
 */
package jsa.test.inject;

import javax.servlet.ServletContextEvent;

import jsa.endpoint.APIProtector;
import jsa.endpoint.BasicHttpAuthProtector;
import jsa.inject.APIModule;
import jsa.test.api.v1.Item;
import jsa.test.api.v1.ItemsAPI;

import org.apache.bval.guice.ValidationModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

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
			    APIProtector p = new BasicHttpAuthProtector("Internal API", "api1", "123qweasd");

			    injector = Guice.createInjector(
				      new APIModule("/sec", "jsa.test.port.api")
				          .withProtector("/", p),
                      new APIModule("/api", "jsa.test.port.api.v2"),
				      new TestAPIModule(), 
				      new ValidationModule()
				);
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
