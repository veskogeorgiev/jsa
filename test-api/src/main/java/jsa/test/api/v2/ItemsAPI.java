package jsa.test.api.v2;

import java.util.List;

import jsa.annotations.API;
import jsa.annotations.API.Version;
import jsa.test.api.APIException;

@API(version = @Version(number = 2, tag = "v2"))
public interface ItemsAPI {

    List<Item> availableItems();

    void saveItem(String name, int count, String description) throws APIException;

}
