package jsa.test.impl;

import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import jsa.test.api.v2.Item;
import jsa.test.api.v2.ItemsAPI;

import org.apache.bval.constraints.NotEmpty;
import org.apache.bval.guice.Validate;

@Singleton
public class ItemsAPIv2Impl implements ItemsAPI {

	@Override
	@Validate
	public void save(@Valid Item item, @NotNull @NotEmpty String userId) {
		System.out.println(item + " saved");
	}

}
