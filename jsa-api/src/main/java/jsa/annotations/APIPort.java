package jsa.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation that this is a port to an API. 
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface APIPort {

	/**
	 * The API interface which the annotated class is a port to
	 * @return
	 */
	Class<?> api();

	/**
	 * The URL context of the annotated port
	 * @return
	 */
	String context();

	/**
	 * The type of the port
	 * @return
	 */
	APIPortType type();
}
