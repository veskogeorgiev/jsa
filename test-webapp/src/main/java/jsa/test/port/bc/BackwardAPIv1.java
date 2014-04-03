package jsa.test.port.bc;

import jsa.annotations.API;
import jsa.annotations.API.Version;
import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.thrift.annotations.ExposeThrift;
import jsa.test.backward.v1.BackwardCompAPI;
import jsa.test.backward.v1.BackwardCompAPI.Iface;

import org.apache.thrift.TException;

@API(name = "tdemo", version = @Version(number = 1, tag = "v1"))
@ExposeThrift(module = BackwardCompAPI.class)
@APIPort(api = BackwardAPIv1.class, context = "t", type = APIPortType.DECORATOR)
public class BackwardAPIv1 implements Iface {

    @Override
    public void save(String name1, String name2) throws TException {
        System.out.println("v2:" + name1 + " " + name2);
    }

}
