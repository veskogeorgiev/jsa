//package jsa.test;
//
//import java.util.Arrays;
//import java.util.List;
//
//import javax.inject.Inject;
//
//import jsa.client.APIRestClientFactory;
//import jsa.client.APISoapClientFactory;
//import jsa.endpoint.thrift.APIThriftClientFactory;
//import jsa.test.api.APIException;
//import jsa.test.api.items.thrift.ItemsAPI;
//import jsa.test.api.v1.Item;
//import jsa.test.api.v1.Request;
//import jsa.test.inject.APITestRunner;
//import jsa.test.port.api.v1.ItemsAPIRest;
//import jsa.test.port.api.v1.ItemsAPISoap;
//import jsa.test.port.api.v1.ItemsAPIThrift;
//import lombok.extern.slf4j.Slf4j;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@Slf4j
//@RunWith(APITestRunner.class)
//public class ClientTest extends Assert {
//	
//	@Inject private APIRestClientFactory rest;
//	@Inject private APISoapClientFactory soap;
//	@Inject private APIThriftClientFactory thrift;
//
//	static ItemsAPIRest restApi;
//	static ItemsAPISoap soapApi;
//	static ItemsAPI.Client thriftApi;
//
//	static Boolean LOG = false;
//
//	@Inject
//	public void setup() throws Exception {
//		restApi = rest.get(ItemsAPIRest.class);
//		soapApi = soap.get(ItemsAPISoap.class);
//		thriftApi = thrift.get(ItemsAPIThrift.class, ItemsAPI.Client.class);
//	}
//
//	@Test
//	public void rest() throws Exception {
//		test("REST", restApi);
//	}
//
//	@Test
//	public void soap() throws Exception {
//		test("SOAP", soapApi);
//	}
//
//	@Test
//	public void testFault() {
//	    try {
//            List<Item> res = soapApi.getItems();
//            System.out.println("response: " + res);
//        }
//        catch (APIException e) {
//            System.out.println("apiex: " + e);
//        }
//	    catch (Exception e) {
//	        System.out.println("ex: " + e);
//        }
//	}
//
//	@Test
//	public void thrift() throws Exception {
//		Object res = null;
//
//		res = thriftApi.getItemResult();
//		log("Thrift Result: %s", res);
//		res = thriftApi.getItems();
//		log("Thrift Result: %s", res);
//		res = thriftApi.getMapResult();
//		log("Thrift Result: %s", res);
//
//		try {
//			thriftApi.save(new jsa.test.api.items.thrift.Item("smth", "asf", 1));
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//	}
//
//	private void test(String type, jsa.test.api.v1.ItemsAPI api) throws APIException {
//		Object res = null;
//		res = api.getItemResult();
//		log(type + "Result: %s", res);
//		res = api.getItems();
//		log(type + "Thrift Result: %s", res);
//		res = api.getMapResult();
//		log(type + "Thrift Result: %s", res);
//
//		try {
//			api.save(new Item("smth", "asf", 1));
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void demo() {
//		Request<List<Item>> req = new Request<List<Item>>();
//		req.setId("asdfasdf");
//		req.setPayload(Arrays.asList(new Item("asd", "asdf", 4)));
//		restApi.saveList(req);
//	}
//
//	private static void log(String format, Object... args) {
//		if (LOG) {
//			log.info(String.format(format, args));
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//		ClientTest test = new ClientTest();
//
//		test.rest();
//		test.soap();
//		test.thrift();
//	}
//
//}
