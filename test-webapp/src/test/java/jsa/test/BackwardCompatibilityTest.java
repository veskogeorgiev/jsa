package jsa.test;

import jsa.test.backward.v1.thrift.BackwardCompAPI.Client;
import jsa.test.inject.APITestRunner;
import jsa.test.port.bc.BackwardAPIv1Rest;
import lombok.extern.slf4j.Slf4j;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(APITestRunner.class)
@Slf4j
public class BackwardCompatibilityTest extends Assert {

    @Test
    public void testSameVersion() throws Exception {
        String endpoint = "http://localhost:8080/d/backward/v1";
        testThrift(endpoint);
        testRest(endpoint);
    }

    @Test
    public void testBackwardVersion() throws Exception {
        String endpoint = "http://localhost:8080/d/backward/v2";
        testThrift(endpoint);
        try {
            testRest(endpoint);
            fail("");
        }
        catch (Exception e) {
            log.info("Expected failure", e);
        }
    }

    public void testThrift(String endpoint) throws Exception {
        String url = endpoint + "/t";
        TTransport clientTransport = new THttpClient(url);
        clientTransport.open();
        TProtocol protocol = new TBinaryProtocol(clientTransport);
        Client client = new Client(protocol);
        
        client.save("n1", "n2");
    }

    public void testRest(String endpoint) throws Exception {
        String url = endpoint + "/rest";

        BackwardAPIv1Rest client = JAXRSClientFactory.create(url, BackwardAPIv1Rest.class);
        client.save("n1", "n2");
    }

}
