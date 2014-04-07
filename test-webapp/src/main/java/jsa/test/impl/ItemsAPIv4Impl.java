package jsa.test.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import jsa.test.api.v1.thrift.Item;
import jsa.test.api.v4.ItemsAPI;

import org.apache.thrift.TException;

@Singleton
public class ItemsAPIv4Impl implements ItemsAPI {
 
    private List<Item> list = new ArrayList<Item>();

    public ItemsAPIv4Impl() {
        list.add(new Item("Guitar", 5));
        list.add(new Item("Pick", 3));
        list.add(new Item("Drum Kit", 2));
    }

    @Override
    public List<Item> availableItems() throws TException {
        return list;
    }

    @Override
    public void saveItem(String arg1, int arg2) throws TException {
        // save
    }


}
