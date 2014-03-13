package jsa.test.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import jsa.test.api.items.thrift.Item;
import jsa.test.api.items.thrift.ItemResult;
import jsa.test.api.v3.ItemsAPI;

import org.apache.thrift.TException;

@Singleton
public class ItemsAPIv3Impl implements ItemsAPI {

	private List<Item> list = new ArrayList<Item>();

	public ItemsAPIv3Impl() {
		list.add(new Item("1", "asdf", 5));
		list.add(new Item("2", "25g24g", 5));
		list.add(new Item("3", "259guh", 5));
	}

	@Override
	public List<Item> getItems() throws TException {
		return list;
	}

	@Override
	public ItemResult getItemResult() throws TException {
		ItemResult res = new ItemResult();
		res.setName("asdfasdf");
		res.setItems(list);
		return res;
	}

	@Override
	public Map<String, Item> getMapResult() throws TException {
		Map<String, Item> res = new HashMap<String, Item>();
		res.put("something", new Item("1", "aefg", 4));
		res.put("anything", new Item("2", "asdf34", 4));
		return res;
	}

	@Override
	public void save(Item item) throws TException {
		System.out.println(item);
		list.add(item);
	}

}
