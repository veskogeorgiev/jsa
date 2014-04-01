/**
 * 
 */
package jsa.compiler;


/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public interface SourceCodeGeneratorFactory {

	SourceCodeGenerator create(Class<?> apiPort, SourceGenerationContext context);

}
