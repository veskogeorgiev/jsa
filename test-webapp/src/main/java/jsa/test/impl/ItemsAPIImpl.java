package jsa.test.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.validation.Valid;

import org.apache.bval.guice.Validate;

import jsa.test.api.APIException;
import jsa.test.api.v1.Item;
import jsa.test.api.v1.ItemResult;
import jsa.test.api.v1.ItemsAPI;
import jsa.test.api.v1.Request;

@Singleton
public class ItemsAPIImpl implements ItemsAPI {

	private List<Item> list = new ArrayList<Item>();

	public ItemsAPIImpl() {
		list.add(new Item("1", "asdf", 5));
		list.add(new Item("2", "25g24g", 5));
		list.add(new Item("3", "259guh", 5));
	}

	@Override
	public List<Item> getItems() throws APIException {
		return list;
	}

	@Override
	public ItemResult getItemResult() {
		ItemResult res = new ItemResult();
		res.setName("asdfasdf");
		res.setItems(list);
		return res;
	}

	@Override
	@Validate
	public void save(@Valid Item item) {
		System.out.println(item);
		list.add(item);
	}

	@Override
	public Map<String, Item> getMapResult() {
		Map<String, Item> res = new HashMap<String, Item>();
		res.put("something", new Item("1", "aefg", 4));
		res.put("anything", new Item("2", "asdf34", 4));
		return res;
	}

	@Override
	public void saveList(Request<List<Item>> item) {
		System.out.println(item);
	}
}
