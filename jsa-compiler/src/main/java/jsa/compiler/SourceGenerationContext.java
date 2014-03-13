/**
 * 
 */
package jsa.compiler;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Data
@AllArgsConstructor
public class SourceGenerationContext {

	private String context;
	private String namespace;
}
