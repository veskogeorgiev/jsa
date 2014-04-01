package jsa.test.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import jsa.test.api.APIException;
import jsa.test.api.v1.Item;
import jsa.test.api.v1.ItemsAPI;

@Singleton
public class ItemsAPIv1Impl implements ItemsAPI {

	private List<Item> list = new ArrayList<Item>();

	public ItemsAPIv1Impl() {
		list.add(new Item("Guitar", 5));
		list.add(new Item("Pick", 3));
		list.add(new Item("Drum Kit", 2));
	}

    @Override
    public List<Item> availableItems() {
        return list;
    }

    @Override
    public void saveItem(String name, int count) throws APIException {
        Item item = new Item(name, count);
        list.add(item);
    }
}
