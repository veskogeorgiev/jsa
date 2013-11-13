package net.sf.jsa.proc.meta.types;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.sf.jsa.proc.meta.Field;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Getter
@Setter
public class ComplexType extends CustomType {

	private List<Field> fields = new LinkedList<>();

}