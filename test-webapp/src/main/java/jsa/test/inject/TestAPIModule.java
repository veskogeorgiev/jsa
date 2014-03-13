package jsa.test.inject;

import jsa.test.api.v1.ItemsAPI;
import jsa.test.impl.ItemsAPIImpl;
import jsa.test.impl.ItemsAPIv2Impl;
import jsa.test.impl.ItemsAPIv3Impl;
import jsa.test.impl.SecretsAPIImpl;
import jsa.test.secapi.SecretsAPI;

import com.google.inject.AbstractModule;

public class TestAPIModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ItemsAPI.class).to(ItemsAPIImpl.class);
		bind(jsa.test.api.v2.ItemsAPI.class).to(ItemsAPIv2Impl.class);
		bind(jsa.test.api.v3.ItemsAPI.class).to(ItemsAPIv3Impl.class);
		bind(SecretsAPI.class).to(SecretsAPIImpl.class);
	}
}
