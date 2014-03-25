package jsa.test.impl;

import javax.inject.Singleton;

import jsa.test.api.v1.Item;
import jsa.test.api.v2.ItemsAPI;

@Singleton
public class ItemsAPIv2Impl implements ItemsAPI {

    @Override
    public void saveBoth(Item item1, Item item2) {
        System.out.println("saved both for user Id" + item1 + "  " + item2);
    }

    @Override
    public void save(Item item) {
        System.out.println(item + " saved");
        throw new RuntimeException();
    }

}
