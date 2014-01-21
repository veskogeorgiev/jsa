package jsa.compiler.meta;

import java.util.LinkedList;
import java.util.List;

import jsa.annotations.APIPort;
import lombok.Data;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Data
public class ServiceAPIMetaData {

	private String name;
	private ServiceVersion version;
	private List<ServiceMethod> methods = new LinkedList<>();
	private Class<?> apiPort;

	public String getUrl() {
		return String.format("/%s/%s", getName(), getVersion().getTag());
	}

	public static Builder builder() {
		return new Builder();
	}

	public String getApiPortContext() {
		return apiPort.getAnnotation(APIPort.class).context();
	}

	public static class Builder {

		public Builder() {
			this.item = new ServiceAPIMetaData();
		}

		private final ServiceAPIMetaData item;

		public Builder name(final String name) {
			this.item.name = name;
			return this;
		}

		public Builder version(final ServiceVersion version) {
			this.item.version = version;
			return this;
		}

		public Builder methods(final List<ServiceMethod> methods) {
			this.item.methods = methods;
			return this;
		}

		public Builder method(final ServiceMethod method) {
			this.item.methods.add(method);
			method.setApi(this.item);
			return this;
		}

		public Builder apiPort(final Class<?> apiPort) {
			this.item.setApiPort(apiPort);
			return this;
		}

		public ServiceAPIMetaData build() {
			return this.item;
		}
	}
}
