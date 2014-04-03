package jsa.test.port.bc;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.thrift.annotations.ExposeThrift;
import jsa.test.backward.BackwardCompAPIv2;
import jsa.test.backward.v2.thrift.BackwardCompAPI;
import jsa.test.backward.v2.thrift.BackwardCompAPI.Iface;

@APIPort(api = BackwardCompAPIv2.class, context = "t", type = APIPortType.DECORATOR)
@ExposeThrift(module = BackwardCompAPI.class)
public interface BackwardAPIv2Thrift extends Iface {

}
