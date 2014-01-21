package jsa.compiler.meta;

import jsa.compiler.meta.types.Type;
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

	private ServiceAPIMetaData api;
	private String name;
	private Type returnType;
	private List<Type> arguments = new LinkedList<>();
	private Method method;

}