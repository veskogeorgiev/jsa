/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jsa.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author vesko
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
