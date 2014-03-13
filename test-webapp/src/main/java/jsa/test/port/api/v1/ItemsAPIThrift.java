package jsa.test.port.api.v1;

import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.thrift.annotations.DtoTypeMapping;
import jsa.endpoint.thrift.annotations.ExposeThrift;
import jsa.test.api.items.thrift.Item;
import jsa.test.api.items.thrift.ItemResult;
import jsa.test.api.items.thrift.ItemsAPI.Iface;
import jsa.test.api.v1.ItemsAPI;

import org.apache.thrift.TException;

@APIPort(api = ItemsAPI.class, context = "thrift", type = APIPortType.DECORATOR)
@Singleton
@ExposeThrift(
      module = jsa.test.api.items.thrift.ItemsAPI.class,
      useDtoMapping = true,
      dtoMapping = {
            @DtoTypeMapping(from = jsa.test.api.v1.Item.class, to = Item.class),
            @DtoTypeMapping(from = jsa.test.api.v1.ItemResult.class, to = ItemResult.class),
      })
public interface ItemsAPIThrift extends Iface {

	@Override
	public List<Item> getItems() throws TException;

	@Override
	public ItemResult getItemResult() throws TException;

	@Override
	public Map<String, Item> getMapResult() throws TException;

	@Override
	public void save(Item item) throws TException;

}
