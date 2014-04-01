/**
 * 
 */
package jsa.test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import jsa.endpoint.processors.MethodRepository;
import jsa.test.api.v1.ItemsAPI;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class Quick {

	public static void main(String[] args) throws NoSuchMethodException {
	    MethodRepository mr = new MethodRepository(ItemsAPI.class);
	    Method m = mr.singleMethod("saveList", new Object[] { "" });
	    Type t = m.getGenericParameterTypes()[0];
	    if (t instanceof ParameterizedType) {
	        ParameterizedType pt = (ParameterizedType) t;
	        System.out.println(Arrays.toString(pt.getActualTypeArguments()));
	    }
	    System.out.println(t.getClass());
	}
	
}
