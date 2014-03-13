/**
 * 
 */
package jsa.compiler.meta;

import lombok.AllArgsConstructor;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@AllArgsConstructor
public abstract class AbstractBuilder<T> {

	protected T instance;

	public T build() {
		return instance;
	}

}
