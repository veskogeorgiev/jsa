package jsa.test.port.api.v4;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.thrift.annotations.ExposeThrift;
import jsa.test.api.v4.ItemsAPI;

@APIPort(api = ItemsAPI.class, context = "pt", type = APIPortType.DECORATOR)
@ExposeThrift(module = jsa.test.api.v1.thrift.ItemsAPI.class)
public interface ItemsAPIThrift extends ItemsAPI {

}
