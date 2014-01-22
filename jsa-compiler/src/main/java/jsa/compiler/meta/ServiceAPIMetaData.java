/*******************************************************************************
 * Copyright (C) 2013 <a href="mailto:vesko.georgiev@icloud.com">Vesko Georgiev</a>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *******************************************************************************/
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
