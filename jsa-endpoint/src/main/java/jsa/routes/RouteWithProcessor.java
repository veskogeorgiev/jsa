/**
 * 
 */
package jsa.routes;

import org.apache.camel.Processor;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public interface RouteWithProcessor {

	Processor getProcessor();
}
