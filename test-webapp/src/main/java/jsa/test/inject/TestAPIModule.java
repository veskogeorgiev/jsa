package jsa.test.inject;

import jsa.test.impl.ItemsAPIv1Impl;
import jsa.test.impl.ItemsAPIv2Impl;
import jsa.test.impl.ItemsAPIv3Impl;

import com.google.inject.AbstractModule;

public class TestAPIModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(jsa.test.api.v1.ItemsAPI.class).to(ItemsAPIv1Impl.class);
		bind(jsa.test.api.v2.ItemsAPI.class).to(ItemsAPIv2Impl.class);
		bind(jsa.test.api.v3.ItemsAPI.class).to(ItemsAPIv3Impl.class);
	}
}
