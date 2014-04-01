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

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import jsa.compiler.meta.types.Type.TypeCollection;
import jsa.compiler.meta.types.Type.TypeMap;

import org.reflections.ReflectionUtils;

import com.google.common.base.Joiner;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class TypeFactory {

    private static final Map<Class<?>, Type> cache = new ConcurrentHashMap<Class<?>, Type>();

    private Set<String> possiblePackages = new HashSet<String>();

    public TypeFactory(List<Package> domainPackages) {
        for (Package pckg : domainPackages) {
            possiblePackages.addAll(getParentPackages(pckg));
        }
    }

    public TypeFactory(Package... domainPackages) {
        for (Package pckg : domainPackages) {
            possiblePackages.addAll(getParentPackages(pckg));
        }
    }

    public Type createType(java.lang.reflect.Type t) {
        if (t instanceof Class) {
            return createType((Class<?>)t);
        }
        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) t;
            Type raw = createType(pt.getRawType());
            if (raw instanceof TypeCollection) {
                Type inner = createType(pt.getActualTypeArguments()[0]);
                ((TypeCollection) raw).setInnerType(inner);
            }
            else if (raw instanceof TypeMap) {
                Type inner1 = createType(pt.getActualTypeArguments()[0]);
                Type inner2 = createType(pt.getActualTypeArguments()[1]);
                ((TypeMap) raw).setKeyType(inner1);
                ((TypeMap) raw).setValueType(inner2);
            }
            return raw;
        }
        System.out.println(t);
        return null;
    }

    public Type createType(Class<?> cls) {
        Type ret = cache.get(cls);

        if (ret != null) {
            return ret;
        }

        if (cls == Boolean.class || cls == boolean.class) {
            ret = new Type.TypeBool();
        }
        else if (cls == Byte.class || cls == byte.class) {
            ret = new Type.TypeByte();
        }
        else if (cls == Integer.class || cls == int.class) {
            ret = new Type.TypeInteger();
        }
        else if (cls == Long.class || cls == long.class) {
            ret = new Type.TypeLong();
        }
        else if (cls == Double.class || cls == double.class || cls == Float.class || cls == float.class) {
            ret = new Type.TypeDouble();
        }
        else if (cls == Double.class || cls == double.class) {
            ret = new Type.TypeDouble();
        }
        else if (cls == String.class) {
            ret = new Type.TypeString();
        }
        else if (List.class.isAssignableFrom(cls)) {
            ret = new Type.TypeList();
        }
        else if (Set.class.isAssignableFrom(cls)) {
            ret = new Type.TypeSet();
        }
        else if (Map.class.isAssignableFrom(cls)) {
            ret = new Type.TypeMap();
        }
        else if (cls.isEnum()) {
            ret = createEnumType(cls);
        }
        else if (cls == Void.class || cls == void.class || !shouldProcess(cls)) {
            ret = new Type.VoidType();
        }

        if (ret == null) {
            ret = createComplexType(cls);
        }

        cache.put(cls, ret);
        return ret;

        // TypeBinary
        // ContainerType
    }

    public EnumType createEnumType(Class<?> cls) {
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

    @SuppressWarnings("unchecked")
    public ComplexType createComplexType(Class<?> cls) {
        ComplexType type = new ComplexType();
        type.setName(cls.getSimpleName());
        type.setJavaType(cls);

        for (java.lang.reflect.Field f : ReflectionUtils.getAllFields(cls)) {
            type.getFields().add(new Field(createType(f.getType()), f.getName()));
        }
        return type;
    }

    private boolean shouldProcess(Class<?> cls) {
        if (cls.getPackage() == null) {
            return false;
        }
        String packageName = cls.getPackage().getName();

        for (String prefix : possiblePackages) {
            if (packageName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getParentPackages(Package pckg) {
        List<String> res = new LinkedList<String>();
        List<String> builder = new LinkedList<String>();

        for (String part : pckg.getName().split("\\.")) {
            builder.add(part);
            res.add(Joiner.on(".").join(builder));
        }
        return res;
    }
}
