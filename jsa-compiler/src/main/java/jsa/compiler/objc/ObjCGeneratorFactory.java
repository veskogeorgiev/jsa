/**
 * 
 */
package jsa.compiler.objc;

import jsa.compiler.SourceCodeGenerator;
import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.compiler.SourceGenerationContext;
import jsa.compiler.meta.rest.RestAPIPortInspector;
import jsa.compiler.meta.rest.RestPortMeta;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class ObjCGeneratorFactory implements SourceCodeGeneratorFactory {

	private RestAPIPortInspector restInspector = RestAPIPortInspector.getInstance();

	@Override
	public SourceCodeGenerator create(Class<?> apiPort, SourceGenerationContext context) {
		RestPortMeta restMeta = restInspector.inspect(apiPort);

		return new ObjCGenerator(restMeta, context);
	}
}
