package jsa.test;

import java.util.List;

import javax.inject.Inject;

import jsa.client.APIRestClientFactory;
import jsa.client.APISoapClientFactory;
import jsa.endpoint.thrift.APIThriftClientFactory;
import jsa.test.api.APIException;
import jsa.test.api.v1.thrift.Item;
import jsa.test.api.v1.thrift.ItemsAPI;
import jsa.test.inject.APITestRunner;
import jsa.test.port.api.v1.ItemsAPIRest;
import jsa.test.port.api.v1.ItemsAPISoap;
import jsa.test.port.api.v1.ItemsAPIThrift;
import lombok.extern.slf4j.Slf4j;

import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@Slf4j
@RunWith(APITestRunner.class)
public class StressTestTest extends Assert {
	
	@Inject private APIRestClientFactory rest;
	@Inject private APISoapClientFactory soap;
	@Inject private APIThriftClientFactory thrift;

	static ItemsAPIRest restApi;
	static ItemsAPISoap soapApi;
	static ItemsAPI.Client thriftApi;
	static ItemsAPI.Client pureThriftApi;

	static Boolean LOG = true;

	static int tests = 1000;

	@Inject
	public void setup() throws Exception {
		restApi = rest.get(ItemsAPIRest.class);
		soapApi = soap.get(ItemsAPISoap.class);
		thriftApi = thrift.get(ItemsAPIThrift.class, ItemsAPI.Client.class);
		pureThriftApi = thrift.get(jsa.test.port.api.v4.ItemsAPIThrift.class, ItemsAPI.Client.class);
	}

	@Test
	@Ignore
	public void rest() throws Exception {
		for (int i = 0; i < tests; i++) {
			test("REST", restApi);
      }
	}

	@Test
	@Ignore
	public void soap() throws Exception {
		for (int i = 0; i < tests; i++) {
			test("SOAP", soapApi);
		}
	}

	@Test
	public void thrift() throws Exception {
		for (int i = 0; i < tests; i++) {
			test("Thrift", thriftApi);
		}
	}

	@Test
	public void pureThrift() throws Exception {
		for (int i = 0; i < tests; i++) {
			test("Pure Thrift", pureThriftApi);
		}
	}

	private void test(String type, jsa.test.api.v1.ItemsAPI api) throws APIException {
		Object res = api.availableItems();
		log(type + "Result: %s", res);
	}

	private void test(String type, ItemsAPI.Client api) throws APIException, TException {
        List<Item> res = api.availableItems();
        
		log(type + "Result: %s", res);
	}

	private static void log(String format, Object... args) {
		if (LOG) {
			log.info(String.format(format, args));
		}
	}

	public static void main(String[] args) throws Exception {
		StressTestTest test = new StressTestTest();

		test.rest();
		test.soap();
		test.thrift();
	}

}
