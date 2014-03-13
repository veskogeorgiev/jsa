/**
 * 
 */
package jsa.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface APIContext {

}
