
package net.sf.jsa.proc.meta;

import net.sf.jsa.proc.meta.types.Type;
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
