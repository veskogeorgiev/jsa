package jsa.endpoint.thrift;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsa.endpoint.thrift.proxy.ServerProxyFactory;
import jsa.inject.InstanceLocator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.http.HttpMessage;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;

class ThriftProcessor implements Processor {

	private TProtocolFactory inProtocolFactory = new TBinaryProtocol.Factory(true, true);
	private TProtocolFactory outProtocolFactory = new TBinaryProtocol.Factory(true, true);

	private TProcessor thriftProcessor;
	private ThriftPortMeta apiPortMeta;

	public ThriftProcessor(ThriftPortMeta apiPortMeta) {
		this.apiPortMeta = apiPortMeta;
	}

	@Inject
	private void init(InstanceLocator locator) {
		try {
			Class<? extends TProcessor> pc = apiPortMeta.getProcessorClass();
			Constructor<? extends TProcessor> c = pc.getConstructor(apiPortMeta.getIfcClass());

			// the instance we need is the port itself
			Object instance = null;

			switch (apiPortMeta.getPortType()) {
			case ADAPTER:
				// if it is an adapter
				instance = locator.locate(apiPortMeta.getApiPortClass());
				break;
			case DECORATOR:
				Object apiInstance = locator.locate(apiPortMeta.getAPIClass());
				if (apiPortMeta.useDtoMapping()) {
					instance = ServerProxyFactory.create(apiInstance, apiPortMeta.getApiPortClass(),
					      apiPortMeta.getTypeMapping());
				}
				else {
					instance = ServerProxyFactory.create(apiInstance, apiPortMeta.getApiPortClass());
				}
				break;
			}
			thriftProcessor = c.newInstance(instance);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void doPost(HttpServletRequest request,
	      HttpServletResponse response, InputStream is)
	      throws ServletException, IOException {

		try {
			response.setContentType("application/x-thrift");
			OutputStream out = response.getOutputStream();

			TTransport transport = new TIOStreamTransport(is, out);
			TTransport inTransport = transport;
			TTransport outTransport = transport;

			TProtocol inProtocol = inProtocolFactory.getProtocol(inTransport);
			TProtocol outProtocol = outProtocolFactory
			      .getProtocol(outTransport);

			thriftProcessor.process(inProtocol, outProtocol);
			out.flush();
		}
		catch (TException te) {
			throw new ServletException(te);
		}
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		HttpMessage httpMessage = exchange.getIn(HttpMessage.class);
		InputStream inputBody = (InputStream) exchange.getIn().getBody();

		doPost(httpMessage.getRequest(), httpMessage.getResponse(), inputBody);
	}

}
