/**
 * 
 */
package jsa.inject;

import jsa.inject.web.JSAServletModule;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class APIModule extends AbstractAPIModule {

	public APIModule(String context) {
		super(context);
	}

	public APIModule(String context, String packageNamePrefix) {
		super(context, packageNamePrefix);
	}

	@Override
	protected JSAServletModule createServletModule() {
		return new JSAServletModule(context, bus);
	}
}
