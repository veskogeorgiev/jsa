package jsa.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface API {

	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Version {

		int number();

		String tag();
	}

	Version version();
}
