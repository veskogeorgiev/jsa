package jsa.test.port.api.v2;

import javax.inject.Singleton;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.thrift.annotations.DtoTypeMapping;
import jsa.endpoint.thrift.annotations.ExposeThrift;
import jsa.test.api.v2.ItemsAPI;
import jsa.test.api.v2.thrift.Item;
import jsa.test.api.v2.thrift.ItemsAPI.Iface;

@APIPort(api = ItemsAPI.class, context = "thrift", type = APIPortType.DECORATOR)
@Singleton
@ExposeThrift(
      module = jsa.test.api.v2.thrift.ItemsAPI.class,
      useDtoMapping = true,
      dtoMapping = {
            @DtoTypeMapping(from = jsa.test.api.v2.Item.class, to = Item.class),
      })
public interface ItemsAPIThrift extends Iface {
}
