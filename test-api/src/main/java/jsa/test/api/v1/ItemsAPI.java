package jsa.test.api.v1;

import java.util.List;

import jsa.annotations.API;
import jsa.annotations.API.Version;
import jsa.test.api.APIException;

@API(version = @Version(number = 1, tag = "v1"))
public interface ItemsAPI {

	List<Item> availableItems();

	void saveItem(String name, int count) throws APIException;
}
