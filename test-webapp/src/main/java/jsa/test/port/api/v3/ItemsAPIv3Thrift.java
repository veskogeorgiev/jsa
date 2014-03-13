package jsa.test.port.api.v3;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.thrift.annotations.ExposeThrift;
import jsa.test.api.v3.ItemsAPI;

@APIPort(api = ItemsAPI.class, context = "thrift", type = APIPortType.DECORATOR)
@ExposeThrift(module = jsa.test.api.items.thrift.ItemsAPI.class)
public interface ItemsAPIv3Thrift extends ItemsAPI {

}
