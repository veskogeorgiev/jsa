package jsa.compiler.meta;

import java.util.LinkedList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Data
public class ServiceAPI {

	private String name;
	private ServiceVersion version;
	private List<ServiceMethod> methods = new LinkedList<>();
	private Class<?> resourceClass;

	public String getUrl() {
		return String.format("/%s/%s", getName(), getVersion().getTag());
	}

	public String getRestUrl() {
		return String.format("%s/rest", getUrl());
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {

		public Builder() {
			this.item = new ServiceAPI();
		}

		private final ServiceAPI item;

		public Builder withName(final String name) {
			this.item.name = name;
			return this;
		}

		public Builder withVersion(final ServiceVersion version) {
			this.item.version = version;
			return this;
		}

		public Builder withMethods(final List<ServiceMethod> methods) {
			this.item.methods = methods;
			return this;
		}

		public Builder withMethod(final ServiceMethod method) {
			this.item.methods.add(method);
			method.setApi(this.item);
			return this;
		}

		public Builder withResourceClass(final Class<?> resourceClass) {
			this.item.setResourceClass(resourceClass);
			return this;
		}

		public ServiceAPI build() {
			return this.item;
		}
	}
}
