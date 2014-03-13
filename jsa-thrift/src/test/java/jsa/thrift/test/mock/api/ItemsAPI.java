package jsa.thrift.test.mock.api;

import java.util.List;
import java.util.Map;

import jsa.annotations.API;
import jsa.annotations.API.Version;

@API(version = @Version(number = 1, tag = "v1"))
public interface ItemsAPI {

	List<Item> getItems() throws APIException;

	ItemResult getItemResult();

	Map<String, Item> getMapResult();

	void save(Item item);
}
