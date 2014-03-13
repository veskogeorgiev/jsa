package jsa.test.inject;

import jsa.client.APIRestClientFactory;
import jsa.client.APISoapClientFactory;
import jsa.endpoint.APIEndpoint;
import jsa.endpoint.thrift.APIThriftClientFactory;

import com.google.inject.AbstractModule;

public class APITestModule extends AbstractModule {

	@Override
   protected void configure() {
		APIEndpoint endpoint = new APIEndpoint("http://localhost:8080/api");
		APIRestClientFactory rest = new APIRestClientFactory(endpoint);
		APISoapClientFactory soap = new APISoapClientFactory(endpoint);
		APIThriftClientFactory thrift = new APIThriftClientFactory(endpoint);

		bind(APIRestClientFactory.class).toInstance(rest);
		bind(APISoapClientFactory.class).toInstance(soap);
		bind(APIThriftClientFactory.class).toInstance(thrift);
	}

}
