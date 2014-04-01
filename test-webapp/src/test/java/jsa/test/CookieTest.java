//package jsa.test;
//
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.ws.rs.core.Cookie;
//
//import jsa.client.APIRestClientFactory;
//import jsa.client.APIRestClientFactory.ClientConfigurator;
//import jsa.test.api.v1.Item;
//import jsa.test.inject.APITestRunner;
//import jsa.test.port.api.v1.ItemsAPIRest;
//import lombok.extern.slf4j.Slf4j;
//
//import org.apache.cxf.jaxrs.client.Client;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@Slf4j
//@RunWith(APITestRunner.class)
//public class CookieTest {
//	@Inject private APIRestClientFactory rest;
//
//	static ItemsAPIRest restApi;
//
//	@Inject
//	public void setup() throws Exception {
//		restApi = rest.get(ItemsAPIRest.class, new ClientConfigurator() {
//			@Override
//         public void config(Client client) {
//	         client.cookie(new Cookie("userId", "fu324978fh2o43cuj"));
//         }
//		});
//	}
//
//	@Test
//	public void testCookies() {
//		List<Item> res = restApi.getItems();
//		log.info("Received response: {}", res);
//	}
//}
