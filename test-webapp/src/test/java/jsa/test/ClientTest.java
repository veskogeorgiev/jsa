package jsa.test;

import javax.inject.Inject;

import jsa.client.APIRestClientFactory;
import jsa.client.APISoapClientFactory;
import jsa.endpoint.thrift.APIThriftClientFactory;
import jsa.test.api.APIException;
import jsa.test.api.v1.thrift.ItemsAPI;
import jsa.test.inject.APITestRunner;
import jsa.test.port.api.v1.ItemsAPIRest;
import jsa.test.port.api.v1.ItemsAPISoap;
import jsa.test.port.api.v1.ItemsAPIThrift;
import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@Slf4j
@RunWith(APITestRunner.class)
public class ClientTest extends Assert {
	
	@Inject private APIRestClientFactory rest;
	@Inject private APISoapClientFactory soap;
	@Inject private APIThriftClientFactory thrift;

	static ItemsAPIRest restApi;
	static ItemsAPISoap soapApi;
	static ItemsAPI.Client thriftApi;

	static Boolean LOG = true;

	@Inject
	public void setup() throws Exception {
		restApi = rest.get(ItemsAPIRest.class);
		soapApi = soap.get(ItemsAPISoap.class);
		thriftApi = thrift.get(ItemsAPIThrift.class, ItemsAPI.Client.class);
	}

	@Test
	public void rest() throws Exception {
		test("REST", restApi);
	}

	@Test
	public void soap() throws Exception {
		test("SOAP", soapApi);
	}

	@Test
	public void thrift() throws Exception {
		Object res = null;

		res = thriftApi.availableItems();
		log("Thrift: %s", res);
	}

	private void test(String type, jsa.test.api.v1.ItemsAPI api) throws APIException {
		Object res = null;
		res = api.availableItems();
		log("%s: %s", type, res);
	}

	private static void log(String format, Object... args) {
		if (LOG) {
			log.info(String.format(format, args));
		}
	}

	public static void main(String[] args) throws Exception {
		ClientTest test = new ClientTest();

		test.rest();
		test.soap();
		test.thrift();
	}

}
