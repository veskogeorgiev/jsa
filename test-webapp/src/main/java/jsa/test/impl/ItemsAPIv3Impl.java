package jsa.test.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import jsa.test.api.APIException;
import jsa.test.api.v3.Item;
import jsa.test.api.v3.ItemsAPI;

@Singleton
public class ItemsAPIv3Impl implements ItemsAPI {

	private List<Item> list = new ArrayList<Item>();

	public ItemsAPIv3Impl() {
        list.add(new Item("Guitar", 5, "Parker"));
        list.add(new Item("Pick", 3, "Dunlop"));
        list.add(new Item("Drum Kit", 2, "Tama"));
	}

    @Override
    public List<Item> availableItems() {
        return list;
    }

    @Override
    public void saveItem(String name, int count, @NotNull String description) throws APIException {
        Item item = new Item(name, count, description);
        list.add(item);
    }

}
