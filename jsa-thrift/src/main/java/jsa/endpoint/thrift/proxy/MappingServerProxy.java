package jsa.endpoint.thrift.proxy;

import java.lang.reflect.Method;

import jsa.endpoint.thrift.DtoConverter;
import jsa.endpoint.thrift.TypeMapping;

import org.apache.thrift.TException;

class MappingServerProxy extends AbstractServerProxy {

    private DtoConverter converter;

    MappingServerProxy(Object apiInstance, TypeMapping typeMapping) {
        super(apiInstance);
        this.converter = new DtoConverter(typeMapping);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] thriftArgs) throws Throwable {
        Object[] apiArgs = null;

        // the client calls the API with thrift DTOs
        // We have to convert the input parameters from Thrift to API types

        Method apiMethod = methodRepo.singleMethod(method.getName(), thriftArgs);

        if (thriftArgs != null) {
            // map the arguments
            Class<?>[] apiArgTypes = apiMethod.getParameterTypes();

            apiArgs = new Object[thriftArgs.length];

            for (int i = 0; i < thriftArgs.length; i++) {
                apiArgs[i] = converter.convert(thriftArgs[i], apiArgTypes[i]);
            }
        }
        try {
            Object apiRes = apiMethod.invoke(apiInstance, apiArgs);
            // map the result
            Object res = converter.convert(apiRes, method.getReturnType());

            return res;
        }
        catch (Exception e) {
            throw new TException(e);
        }
    }

}
