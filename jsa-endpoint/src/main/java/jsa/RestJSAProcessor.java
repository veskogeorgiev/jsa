package jsa;

import java.util.List;

import jsa.compiler.APIProcessor;
import jsa.compiler.ClientServiceGenerator;
import jsa.compiler.SourceFile;
import jsa.compiler.js.JavaScriptClientGenerator;
import jsa.compiler.meta.ServiceAPI;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

import com.google.inject.Injector;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class RestJSAProcessor implements Processor {

	private static final String JS_OPERATION_NAME = "__js";

	private final Processor processor;
	private final APIProcessor apiProcessor;
	private final Class<?> apiInterface;
	private final Class<?> apiPort;

	public RestJSAProcessor(Class<?> apiInterface, Class<?> apiPort, Injector injector, Processor processor) {
		this.apiInterface = apiInterface;
		this.apiPort = apiPort;

//		Class<?> stubClass = getImplementorStubClass();
		this.processor = processor;//new JSAProcessor(stubClass, injector);
		apiProcessor = injector.getInstance(APIProcessor.class);
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		String operationName = getOperationName(exchange);

		if (operationName.equals(JS_OPERATION_NAME)) {
			StringBuilder sb = new StringBuilder();
			for (SourceFile sf : generateJavaScriptSources()) {
				sb.append(sf).append("\n");
			}
			exchange.getOut().setBody(sb.toString());
		}
		else {
			processor.process(exchange);
		}
	}

//	private Class<?> getImplementorStubClass() {
//		return apiPort.isAnnotationPresent(ImplementorBrigde.class) 
//				? apiPort
//				: apiInterface;
//	}

	private String getOperationName(Exchange exchange) {
		return exchange.getIn().getHeader(
				CxfConstants.OPERATION_NAME, String.class);
	}

	private List<SourceFile> generateJavaScriptSources() {
		ServiceAPI api = apiProcessor.process(apiInterface, apiPort);
		ClientServiceGenerator csg = new JavaScriptClientGenerator();
		List<SourceFile> res = csg.write(api);
		return res;
	}

}
