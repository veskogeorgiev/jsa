package jsa.endpoint.processors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodInvocationContext {

    private Object serviceInstance;
    private Method method;
    private Object[] arguments;

    public Object invoke() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        method.setAccessible(true);
        return method.invoke(serviceInstance, arguments);
    }
}
