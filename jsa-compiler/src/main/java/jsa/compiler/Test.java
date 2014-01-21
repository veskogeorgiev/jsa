/**
 * 
 */
package jsa.compiler;

import javax.inject.Inject;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class Test {

	private static class Demo {
	
		@Inject
		private Injector inj;
		
		@Inject
		private void init() {
			System.out.println("method " + inj);
		}
	}

	public static void main(String[] args) {
		Injector inj = Guice.createInjector();
		Demo demo = inj.getInstance(Demo.class);
		
	}
}
