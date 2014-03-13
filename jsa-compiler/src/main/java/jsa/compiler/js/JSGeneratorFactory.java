/**
 * 
 */
package jsa.compiler.js;

import jsa.compiler.SourceCodeGenerator;
import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.compiler.SourceGenerationContext;
import jsa.compiler.meta.rest.RestAPIPortInspector;
import jsa.compiler.meta.rest.RestPortMeta;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class JSGeneratorFactory implements SourceCodeGeneratorFactory {

	private RestAPIPortInspector restInspector = RestAPIPortInspector.getInstance();

	@Override
	public SourceCodeGenerator create(final Class<?> apiPort, final SourceGenerationContext context) {
		RestPortMeta restMeta = restInspector.inspect(apiPort);
		return new JSSourceGenerator(restMeta, context);
	}
}
