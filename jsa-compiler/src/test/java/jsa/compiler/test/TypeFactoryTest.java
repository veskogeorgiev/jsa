package jsa.compiler.test;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import jsa.compiler.meta.types.TypeFactory;
import jsa.compiler.meta.types.TypeStringBuilder;

public class TypeFactoryTest {
    TypeFactory tf = new TypeFactory(Package.getPackage("jsa.compiler.test"));
    TypeStringBuilder typeStringBuilder = new TypeStringBuilder();

    void inspect(Class<?> api) {
        for (Method m : api.getDeclaredMethods()) {
            inspect(m);
        }
    }

    void inspect(Method m) {
        Type[] genericParameterTypes = m.getGenericParameterTypes();
        for (Type type : genericParameterTypes) {
            jsa.compiler.meta.types.Type t = tf.createType(type);
            System.out.println(typeStringBuilder.toString(t));
        }
    }

    public static void main(String[] args) {
        TypeFactoryTest tft = new TypeFactoryTest();
        tft.inspect(TestAPI.class);
    }
}
