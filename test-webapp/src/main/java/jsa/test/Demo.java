package jsa.test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class Demo<T> {

    T myField;
    
    public static void main(String[] args) {
        TypeVariable<Class<Demo>>[] tp = Demo.class.getTypeParameters();
        TypeVariable<Class<Demo>> tv = tp[0];
//        for (TypeVariable<Class<Demo>> typeVariable : tp) {
//            System.out.println(typeVariable);
//        }
        
        Field[] fields = Demo.class.getDeclaredFields();
        Field field = fields[0];
        Type gt = field.getGenericType();
        System.out.println(gt.equals(tv));
     }
}
