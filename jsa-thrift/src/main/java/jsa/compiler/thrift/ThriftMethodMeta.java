/**
 * 
 */
package jsa.compiler.thrift;

import jsa.compiler.meta.AbstractAPIMethodMeta;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class ThriftMethodMeta extends AbstractAPIMethodMeta {
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends AbstractAPIMethodMeta.Builder<ThriftMethodMeta> {

		public Builder() {
			super(new ThriftMethodMeta());
		}
	}
}
