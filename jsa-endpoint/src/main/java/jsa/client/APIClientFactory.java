package jsa.client;

import jsa.endpoint.APIEndpoint;

/**
 * 
 * @author <a href="mailto:vesko.m.georgiev@gmail.com">Vesko Georgiev<a>
 */
public abstract class APIClientFactory {

    protected APIEndpoint endpoint;

    public APIClientFactory(APIEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public APIClientFactory(String apiEndpoint) {
        this(new APIEndpoint(apiEndpoint));
    }
}
