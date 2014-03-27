/**
 * 
 */
package jsa.compiler.thrift;

import jsa.annotations.API;
import jsa.annotations.APIPort;
import jsa.compiler.SourceCodeGenerator;
import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.compiler.SourceGenerationContext;
import jsa.compiler.meta.APIMeta;
import jsa.endpoint.processors.APIPortMeta;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class ThriftGeneratorFactory implements SourceCodeGeneratorFactory {

	@Override
	public SourceCodeGenerator create(final Class<?> apiPort, final SourceGenerationContext context) {
		Class<?> apiClass = null;
		if (apiPort.isAnnotationPresent(APIPort.class)) {
			APIPortMeta portMeta = APIPortMeta.create(apiPort);
			apiClass = portMeta.getAPIClass();
		}
		else if (apiPort.isAnnotationPresent(API.class)) {
			apiClass = apiPort;
		}
		return new ThriftSourceGenerator(APIMeta.create(apiClass), context);
	}
}
