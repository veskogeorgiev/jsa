/**
 * 
 */
package jsa.client;

import java.util.Arrays;
import java.util.List;

import jsa.endpoint.APIEndpoint;
import jsa.endpoint.cxf.ExposeRest;
import jsa.endpoint.processors.APIPortMeta;

import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class APIRestClientFactory extends APIClientFactory {
    // protected Executor executor;

    public APIRestClientFactory(APIEndpoint endpoint) {
        super(endpoint);
    }

    public APIRestClientFactory(String apiEndpoint) {
        super(apiEndpoint);
    }

    public <T> T get(Class<T> apiPort) {
       
        JacksonJsonProvider p = new JacksonJsonProvider();
        p.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return get(apiPort, Arrays.asList(p));
    }

    public <T> T get(Class<T> apiPort, ClientConfigurator configurator) {
        T res = get(apiPort, Arrays.asList(new JacksonJsonProvider()));
        Client client = WebClient.client(res);
        configurator.config(client);
        return res;
    }

    public <T> T get(Class<T> apiPort, List<?> providers) {
        if (!apiPort.isAnnotationPresent(ExposeRest.class)) {
            throw new RuntimeException("The port " + apiPort.getName()
                    + "is not annotated with ExposeRest");
        }
        APIPortMeta api = APIPortMeta.create(apiPort);
        String restUrl = endpoint.context(api.getFullContext());
        T proxy = (T) JAXRSClientFactory.create(restUrl, apiPort, providers);
        return proxy;
    }

    public static interface ClientConfigurator {
        void config(Client client);
    }

}
