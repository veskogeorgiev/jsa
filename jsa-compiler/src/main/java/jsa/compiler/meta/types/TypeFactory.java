package jsa.compiler.meta.types;

import java.util.HashMap;
import java.util.Map;

import jsa.compiler.meta.Field;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class TypeFactory {

	private static final Map<Class<?>, Type> types = new HashMap<>();

	public static Type createType(Class<?> cls) {
		Type ret = types.get(cls);
		if (ret != null) {
			return ret;
		}

		if (cls == Boolean.class || cls == boolean.class) {
			ret = new Type.TypeBool();
		}
		if (cls == Byte.class || cls == byte.class) {
			ret = new Type.TypeByte();
		}
		if (cls == Integer.class || cls == int.class || cls == Long.class || cls == long.class) {
			ret = new Type.TypeInteger();
		}
		if (cls == Double.class || cls == double.class || cls == Float.class || cls == float.class) {
			ret = new Type.TypeDouble();
		}
		if (cls == Double.class || cls == double.class) {
			ret = new Type.TypeDouble();
		}
		if (cls == String.class) {
			ret = new Type.TypeString();
		}
		if (cls.isEnum()) {
			ret = createEnumType(cls);
		}
		if (ret == null) {
			ret = createComplexType(cls);
		}

		types.put(cls, ret);
		return ret;

		// TypeBinary
		// ContainerType
		// TypeMap
		// TypeList
		// TypeSet
	}

	public static EnumType createEnumType(Class<?> cls) {
		EnumType ret = new EnumType();
		ret.setJavaType(cls);
		ret.setName(cls.getSimpleName());
		Object[] enums = cls.getEnumConstants();
		String[] enumValues = new String[enums.length];
		int idx = 0;
		for (Object e : enums) {
			enumValues[idx++] = e.toString();
		}
		ret.setValues(enumValues);
		return ret;
	}

	public static ComplexType createComplexType(Class<?> cls) {
		ComplexType type = new ComplexType();
		type.setName(cls.getSimpleName());
		type.setJavaType(cls);

		for (java.lang.reflect.Field f : cls.getDeclaredFields()) {
			type.getFields().add(new Field(createType(f.getType()), f.getName()));
		}
		return type;
	}
}
