package jsa.routes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIInvocationContext {

	private Object serviceInstance;
	private Method method;
	private Object[] arguments;
	
	public Object invoke() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return method.invoke(serviceInstance, arguments);
	}
}
