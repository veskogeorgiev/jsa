package jsa.compiler.meta.types;

import java.util.HashMap;
import java.util.Map;

import jsa.compiler.meta.types.Type.TypeBinary;
import jsa.compiler.meta.types.Type.TypeBool;
import jsa.compiler.meta.types.Type.TypeByte;
import jsa.compiler.meta.types.Type.TypeDouble;
import jsa.compiler.meta.types.Type.TypeInteger;
import jsa.compiler.meta.types.Type.TypeList;
import jsa.compiler.meta.types.Type.TypeMap;
import jsa.compiler.meta.types.Type.TypeSet;
import jsa.compiler.meta.types.Type.TypeString;
import jsa.compiler.meta.types.Type.VoidType;

public class TypeStringBuilder {

    private Map<Class<? extends Type>, String> typeMapping = new HashMap<Class<? extends Type>, String>();
    {
        typeMapping.put(VoidType.class, "void");
        typeMapping.put(TypeBool.class, "bool");
        typeMapping.put(TypeByte.class, "byte");
        typeMapping.put(TypeInteger.class, "i32");
        typeMapping.put(TypeDouble.class, "double");
        typeMapping.put(TypeString.class, "string");
        typeMapping.put(TypeBinary.class, "binary");
        typeMapping.put(TypeList.class, "list");
        typeMapping.put(TypeSet.class, "set");
        typeMapping.put(TypeMap.class, "map");
    }

    public TypeStringBuilder withTypeMapping(Class<? extends Type> type, String str) {
        typeMapping.put(type, str);
        return this;
    }

    public String toString(Type type) {
        if (type == null) {
            return "unknown";
        }
        if (type instanceof ComplexType) {
            return ((ComplexType) type).getName();
        }
        
        String str = typeMapping.get(type.getClass());
        if (str == null) {
            return "unknown";
        }
        if (type instanceof TypeMap) {
            return String.format("%s<%s, %s>",
                    str,
                    toString(((TypeMap) type).getKeyType()),
                    toString(((TypeMap) type).getValueType()));
        }
        if (type instanceof TypeList) {
            return String.format("%s<%s>", str, toString(((TypeList) type).getInnerType()));
        }
        if (type instanceof TypeSet) {
            return String.format("%s<%s>", str, toString(((TypeSet) type).getInnerType()));
        }

        return str != null ? str : "unknown";
    }

}
