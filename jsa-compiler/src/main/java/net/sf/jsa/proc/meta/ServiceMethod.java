package net.sf.jsa.proc.meta;

import net.sf.jsa.proc.meta.types.Type;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Data
public class ServiceMethod {

	private ServiceAPI api;
	private String name;
	private Type returnType;
	private List<Type> arguments = new LinkedList<>();
	private Method method;

}