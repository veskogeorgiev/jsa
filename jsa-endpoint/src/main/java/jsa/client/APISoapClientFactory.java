/**
 * 
 */
package jsa.client;

import jsa.endpoint.APIEndpoint;
import jsa.endpoint.cxf.ExposeSoap;
import jsa.endpoint.processors.APIPortMeta;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class APISoapClientFactory extends APIClientFactory {

    public APISoapClientFactory(APIEndpoint endpoint) {
        super(endpoint);
    }

    public APISoapClientFactory(String apiEndpoint) {
        super(apiEndpoint);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> apiPort) {
        if (!apiPort.isAnnotationPresent(ExposeSoap.class)) {
            throw new RuntimeException(
                    "The port " + apiPort.getName() + "is not annotated with ExposeSoap");
        }
        APIPortMeta api = APIPortMeta.create(apiPort);
        String soapUrl = endpoint.context(api.getFullContext());

        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(apiPort);
        factory.setAddress(soapUrl);

        return (T) factory.create();
    }

}
