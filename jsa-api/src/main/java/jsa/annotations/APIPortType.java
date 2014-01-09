/**
 * 
 */
package jsa.annotations;

/**
 * There are 2 types of port types: 
 * <ul>
 * 
 * 	<li>Decorator - the decorator</li>
 * 
 *  <li>Adapter - </li>
 * 
 * </ul>
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public enum APIPortType {

	/**
	 * A decorator port extends the API interface and just adds annotations. 
	 */
	DECORATOR,
	
	/**
	 * An adapter port defines its own interface, injects the API interface 
	 * and delegates the methods to the API
	 */
	ADAPTER
}
