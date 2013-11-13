
package jsa.compiler.meta;

import jsa.compiler.meta.types.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author vesko
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {
	
	private Type type;
	private String name;
}
