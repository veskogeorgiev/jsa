package jsa.test.api.v3;

import java.util.List;

import javax.validation.constraints.NotNull;

import jsa.annotations.API;
import jsa.annotations.API.Version;
import jsa.test.api.APIException;

@API(version = @Version(number = 3, tag = "v3"))
public interface ItemsAPI {

    List<Item> availableItems();

    void saveItem(String name, int count, @NotNull String description) throws APIException;

}
