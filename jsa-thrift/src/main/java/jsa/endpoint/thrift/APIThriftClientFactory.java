package jsa.endpoint.thrift;

import java.lang.reflect.Constructor;

import jsa.client.APIClientFactory;
import jsa.endpoint.APIEndpoint;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;

public class APIThriftClientFactory extends APIClientFactory {

	/**
	 * @param apiEndpoint
	 */
	public APIThriftClientFactory(String apiEndpoint) {
		super(apiEndpoint);
	}

	/**
	 * @param endpoint
	 */
	public APIThriftClientFactory(APIEndpoint endpoint) {
		super(endpoint);
	}

	public <T extends TServiceClient> T get(Class<?> apiPort, Class<T> clientCls) throws Exception {
		ThriftPortMeta api = ThriftPortMeta.create(apiPort);
		String thriftUrl = endpoint.context(api.getFullContext());

		TTransport clientTransport = new THttpClient(thriftUrl);
		clientTransport.open();
		TProtocol protocol = new TBinaryProtocol(clientTransport);

		Constructor<T> c = clientCls.getConstructor(TProtocol.class);

		return c.newInstance(protocol);
	}

}
