
package net.sf.jsa.dto;

import javax.activation.DataHandler;

/**
 *
 * @author vesko
 */
public interface Attachment {

	DataHandler getData();

	Integer getLength();

	String getName();

}
