package jsa.test.api.v1;

import java.util.List;

import lombok.Data;

@Data
public class ItemResult {

	private String name;
	private List<Item> items;
}
