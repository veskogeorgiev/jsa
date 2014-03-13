/**
 * 
 */
package jsa.test.secapi;

import jsa.annotations.API;
import jsa.annotations.API.Version;

;
/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@API(version = @Version(number = 1, tag = "v1"))
public interface SecretsAPI {

	String getName();
}
