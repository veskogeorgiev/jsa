package jsa.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author vesko
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface APIPort {

	Class<?> api();
	
	String context();
	
	APIPortType type();
}
