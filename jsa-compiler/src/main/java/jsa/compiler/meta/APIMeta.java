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

import java.lang.annotation.Annotation;

import jsa.annotations.API;
import jsa.compiler.MetaDataException;
import lombok.Getter;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Getter
public class APIMeta {

	private String name;
	private APIVersionMeta version;
	private Class<?> apiClass;

	private APIMeta() {
		// no instantiation please
	}

	public Package getPackage() {
		return apiClass.getPackage();
	}

	public String getURI() {
		return String.format("/%s/%s", getName(), getVersion().getTag());
	}

	public static APIMeta create(Class<?> apiInterface) {
		API api = apiInterface.getAnnotation(API.class);

		if (api == null) {
			throw new MetaDataException("%s is used as an API but is not annotated with @API",
			      apiInterface.getName());
		}
		API.Version version = api != null ? api.version() : DEFAULT_VERSION;

		APIMeta res = new APIMeta();
		res.apiClass = apiInterface;
		res.name = api.name().isEmpty() ? apiInterface.getSimpleName() : api.name();
		res.version = new APIVersionMeta(version.number(), version.tag());

		return res;
	}

	/**
     * 
     */
	private static final API.Version DEFAULT_VERSION = new API.Version() {

		@Override
		public Class<? extends Annotation> annotationType() {
			return API.Version.class;
		}

		@Override
		public String tag() {
			return "v1";
		}

		@Override
		public int number() {
			return 1;
		}
	};

}
