/**
 *
 */
package jsa.test.inject;

import java.util.List;

import jsa.endpoint.cxf.JaxRsConfig;
import jsa.inject.APIModule;
import jsa.test.port.api.TestExcetionMapper;

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
//			    APIProtector p = new BasicHttpAuthProtector("Internal API", "api1", "123qweasd");

			    injector = Guice.createInjector(
                      new APIModule("/api", "jsa.test.port.api").withJaxRsConfig(new JaxRsConfig() {
                        @Override
                        public void addProviders(List<Object> providers) {
                            providers.add(new TestExcetionMapper());
                        }
                    }),
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
}
