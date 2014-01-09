
package jsa.dto;

import javax.activation.DataHandler;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public interface Attachment {

	DataHandler getData();

	Integer getLength();

	String getName();

}
