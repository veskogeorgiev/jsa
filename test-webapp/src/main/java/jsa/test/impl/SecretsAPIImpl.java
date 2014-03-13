/**
 * 
 */
package jsa.test.impl;

import jsa.test.secapi.SecretsAPI;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class SecretsAPIImpl implements SecretsAPI {

	@Override
	public String getName() {
		return "It's a secret";
	}

}
