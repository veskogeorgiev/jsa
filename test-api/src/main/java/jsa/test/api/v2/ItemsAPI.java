package jsa.test.api.v2;

import jsa.annotations.API;
import jsa.annotations.API.Version;
import jsa.test.api.v1.Item;

@API(version = @Version(number = 2, tag = "v2"))
public interface ItemsAPI {

    void saveBoth(Item item1, Item item2);

    void save(Item item);

    void create(String name, int age);

}
