package jsa.compiler.meta.types;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Getter
@Setter
public class CustomType extends Type {

	private Class<?> javaType;
	private String name;

	@Override
	public int hashCode() {
		return javaType.hashCode();
	}
}