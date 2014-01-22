/*******************************************************************************
 * Copyright (C) 2013 <a href="mailto:vesko.georgiev@icloud.com">Vesko Georgiev</a>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *******************************************************************************/
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
