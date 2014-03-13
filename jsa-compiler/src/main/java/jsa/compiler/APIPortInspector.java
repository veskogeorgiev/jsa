/**
 * 
 */
package jsa.compiler;

import jsa.compiler.meta.AbstractAPIPortMeta;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public interface APIPortInspector<PortType extends AbstractAPIPortMeta> {

	public PortType inspect(Class<?> portClass);

}
