package jsa.compiler.meta.types;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import jsa.compiler.meta.Field;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Getter
@Setter
public class ComplexType extends CustomType {

	private List<Field> fields = new LinkedList<>();

}