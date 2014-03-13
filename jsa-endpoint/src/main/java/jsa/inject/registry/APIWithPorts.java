/**
 * 
 */
package jsa.inject.registry;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Getter
@AllArgsConstructor
public class APIWithPorts<Ifc> {

	private Class<Ifc> api;
	private Collection<Class<?>> ports;
}
