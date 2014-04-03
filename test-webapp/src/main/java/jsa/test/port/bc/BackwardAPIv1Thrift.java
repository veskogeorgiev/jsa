package jsa.test.port.bc;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.thrift.annotations.ExposeThrift;
import jsa.test.backward.BackwardCompAPIv1;
import jsa.test.backward.v1.thrift.BackwardCompAPI;
import jsa.test.backward.v1.thrift.BackwardCompAPI.Iface;

@APIPort(api = BackwardCompAPIv1.class, context = "t", type = APIPortType.DECORATOR)
@ExposeThrift(module = BackwardCompAPI.class)
public interface BackwardAPIv1Thrift extends Iface {

}
