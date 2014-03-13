/**
 * 
 */
package jsa.compiler;

import lombok.NoArgsConstructor;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@NoArgsConstructor
public class MetaDataException extends RuntimeException {

	private static final long serialVersionUID = -5602858223631136657L;

	public MetaDataException(String format, Object... args) {
		super(String.format(format, args));
	}
}
