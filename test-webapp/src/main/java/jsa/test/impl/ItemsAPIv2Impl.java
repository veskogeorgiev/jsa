package jsa.test.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import jsa.test.api.APIException;
import jsa.test.api.v2.Item;
import jsa.test.api.v2.ItemsAPI;

@Singleton
public class ItemsAPIv2Impl implements ItemsAPI {

    private List<Item> list = new ArrayList<Item>();

    public ItemsAPIv2Impl() {
        list.add(new Item("Guitar", 5));
        list.add(new Item("Pick", 3));
        list.add(new Item("Drum Kit", 2));
    }

    @Override
    public List<Item> availableItems() {
        return list;
    }

    @Override
    public void saveItem(String name, int count, String description) throws APIException {
        Item item = new Item(name, count, description);
        list.add(item);
    }

}
