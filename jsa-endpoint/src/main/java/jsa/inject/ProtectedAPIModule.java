/**
 * 
 */
package jsa.inject;

import jsa.endpoint.APIProtector;
import jsa.inject.web.JSAProtectedServletModule;
import jsa.inject.web.JSAServletModule;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class ProtectedAPIModule extends AbstractAPIModule {

    private APIProtector protector;

    public ProtectedAPIModule(String context, APIProtector protector) {
        this(context, null, protector);
    }

    public ProtectedAPIModule(String context, String packageNamePrefix, APIProtector protector) {
        super(context, packageNamePrefix);
        this.protector = protector;
    }

    @Override
    protected JSAServletModule createServletModule() {
        return new JSAProtectedServletModule(context, bus, protector);
    }
}
