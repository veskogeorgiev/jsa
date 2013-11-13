package jsa.compiler.meta.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Getter
@Setter
@EqualsAndHashCode(of = "javaType", callSuper = false)
public class CustomType extends Type {

	private Class<?> javaType;
	private String name;
}