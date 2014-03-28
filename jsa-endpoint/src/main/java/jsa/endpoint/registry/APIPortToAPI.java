/**
 * 
 */
package jsa.endpoint.registry;

import jsa.annotations.APIPort;

import com.google.common.base.Function;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class APIPortToAPI implements Function<Class<?>, Class<?>> {

    public static final APIPortToAPI INSTANCE = new APIPortToAPI();

    private APIPortToAPI() {

    }

    @Override
    public Class<?> apply(Class<?> input) {
        return input.getAnnotation(APIPort.class).api();
    }

}
