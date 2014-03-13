package jsa.thrift.test.mock.api;

import java.util.List;

import lombok.Data;

@Data
public class ItemResult {

	private String name;
	private List<Item> items;
}
