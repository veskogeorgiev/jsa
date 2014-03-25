//package jsa.test;
//
//import javax.inject.Inject;
//
//import jsa.client.APISoapClientFactory;
//import jsa.test.api.v1.Item;
//import jsa.test.inject.APITestRunner;
//import lombok.extern.slf4j.Slf4j;
//
//import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@Slf4j
//@RunWith(APITestRunner.class)
//public class BackwardTest extends Assert {
//
//    @Test
//    public void test() throws Exception {
////        jsa.test.port.api.v1.ItemsAPISoap v1 = create("http://localhost:8080/api/ItemsAPI/v1/soap", jsa.test.port.api.v1.ItemsAPISoap.class);
//        jsa.test.port.api.v2.ItemsAPISoap v2 = create("http://localhost:8080/api/ItemsAPI/v1/soap", jsa.test.port.api.v2.ItemsAPISoap.class);
//
//        v2.save(new Item("123", "pesho", 1), "asf");
//    }
//
//    private <T> T create(String url, Class<T> type) {
//        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//        factory.setServiceClass(type);
//        factory.setAddress(url);
//
//        return (T) factory.create();
//    }
//
//    public static void main(String[] args) throws Exception {
//        BackwardTest test = new BackwardTest();
//
//        test.test();
//    }
//
//}
