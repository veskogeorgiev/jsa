package net.sf.jsa;

import com.google.inject.Injector;

import java.util.List;
import net.sf.jsa.proc.APIProcessor;
import net.sf.jsa.proc.ClientServiceGenerator;
import net.sf.jsa.proc.SourceFile;
import net.sf.jsa.proc.js.JavaScriptClientGenerator;
import net.sf.jsa.proc.meta.ServiceAPI;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class RestMappingProcessor implements Processor {

	private final MappingProcessor processor;
	private final APIProcessor apiProcessor;
	private final Class<?> apiInterface;

	public RestMappingProcessor(Class<?> beanClass, Injector injector) {
		apiInterface = beanClass;
		processor = new MappingProcessor(beanClass, injector);
		apiProcessor = injector.getInstance(APIProcessor.class);
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		String operationName = exchange.getIn().getHeader(
				CxfConstants.OPERATION_NAME, String.class);
		if (operationName.equals("__js")) {
			StringBuilder sb = new StringBuilder();
			for (SourceFile sf : generateSources()) {
				sb.append(sf).append("\n");
			}
			exchange.getOut().setBody(sb.toString());
		}
		else {
			System.out.println(exchange.getIn());
			processor.process(exchange);
		}

	}

	private List<SourceFile> generateSources() {
		ServiceAPI api = apiProcessor.process(apiInterface, apiInterface);
		ClientServiceGenerator csg = new JavaScriptClientGenerator();
		List<SourceFile> res = csg.write(api);
		return res;
	}

}
