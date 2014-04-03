package jsa.test.api.v1;

import java.util.List;

import jsa.annotations.API;
import jsa.annotations.API.Version;
import jsa.test.api.APIException;

/** Items API version 1 */
@API(version = @Version(number = 1, tag = "v1"))
public interface ItemsAPI {

    /** Retrieves the available items */
	List<Item> availableItems();

	/** Creates a new item with the given name and count */
	void saveItem(String name, int count) throws APIException;
}
