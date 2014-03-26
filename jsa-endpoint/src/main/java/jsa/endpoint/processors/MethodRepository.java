package jsa.endpoint.processors;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class MethodRepository {

    private Multimap<String, Method> nameToMethod = HashMultimap.create();

    public MethodRepository(Class<?> cls) {
        if (cls != null) {
            for (Method m : cls.getDeclaredMethods()) {
                nameToMethod.put(m.getName(), m);
            }
            for (Method m : cls.getMethods()) {
                nameToMethod.put(m.getName(), m);
            }
        }
    }

    public Method singleMethod(String methodName, Object[] args)
            throws NoSuchMethodException {
        if (args == null) {
            args = new Object[0];
        }
        for (Method method : nameToMethod.get(methodName)) {
            Class<?>[] mp = method.getParameterTypes();
            if (mp.length == args.length) {
                return method;
            }
        }
        throw new NoSuchMethodException("No method for name " + methodName +
                " and arguments " + Arrays.toString(args));
    }
}
