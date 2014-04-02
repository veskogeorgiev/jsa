/**
 *
 */
package jsa.test.inject;

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
                // APIProtector p = new BasicHttpAuthProtector("Internal API",
                // "api1", "123qweasd");

                injector = Guice.createInjector(
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
