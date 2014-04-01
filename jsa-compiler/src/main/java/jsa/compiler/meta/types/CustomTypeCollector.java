package jsa.compiler.meta.types;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import jsa.compiler.meta.types.Type.TypeCollection;
import jsa.compiler.meta.types.Type.TypeMap;

public class CustomTypeCollector implements Iterable<CustomType> {

    private Set<CustomType> types = new HashSet<CustomType>();

    public void add(Type type) {
        if (type instanceof CustomType) {
            types.add((CustomType) type);

            if (type instanceof ComplexType) {
                for (Field f : ((ComplexType) type).getFields()) {
                    add(f.getType());
                }
            }
        }
        else if (type instanceof TypeCollection) {
            add(((TypeCollection) type).getInnerType());
        }
        else if (type instanceof TypeMap) {
            add(((TypeMap) type).getKeyType());
            add(((TypeMap) type).getValueType());
        }
    }

    @Override
    public Iterator<CustomType> iterator() {
        return types.iterator();
    }

}
