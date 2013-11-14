package jsa;

import com.google.inject.Injector;

import java.util.List;
import jsa.compiler.APIProcessor;
import jsa.compiler.ClientServiceGenerator;
import jsa.compiler.SourceFile;
import jsa.compiler.js.JavaScriptClientGenerator;
import jsa.compiler.meta.ServiceAPI;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class RestJSAProcessor implements Processor {

	private static final String JS_OPERATION_NAME = "__js";

	private final JSAProcessor processor;
	private final APIProcessor apiProcessor;
	private final Class<?> apiInterface;
	private final Class<?> resourceClass;

	public RestJSAProcessor(Class<?> apiInterface, Class<?> resourceClass, Injector injector) {
		this.apiInterface = apiInterface;
		this.resourceClass = resourceClass;
		processor = new JSAProcessor(apiInterface, injector);
		apiProcessor = injector.getInstance(APIProcessor.class);
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		String operationName = getOperationName(exchange);

		if (operationName.equals(JS_OPERATION_NAME)) {
			StringBuilder sb = new StringBuilder();
			for (SourceFile sf : generateSources()) {
				sb.append(sf).append("\n");
			}
			exchange.getOut().setBody(sb.toString());
		}
		else {
			processor.process(exchange);
		}
	}

	private String getOperationName(Exchange exchange) {
		return exchange.getIn().getHeader(
				CxfConstants.OPERATION_NAME, String.class);
	}

	private List<SourceFile> generateSources() {
		ServiceAPI api = apiProcessor.process(apiInterface, resourceClass);
		ClientServiceGenerator csg = new JavaScriptClientGenerator();
		List<SourceFile> res = csg.write(api);
		return res;
	}

}
