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
package jsa.compiler;

import jsa.annotations.API;
import jsa.compiler.meta.ServiceAPIMetaData;
import jsa.compiler.meta.ServiceMethod;
import jsa.compiler.meta.types.TypeFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.inject.Singleton;

import jsa.compiler.meta.ServiceVersion;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Singleton
public class APIInspector {

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

	public ServiceAPIMetaData process(Class<?> apiInterface, Class<?> portClass) {
		ServiceAPIMetaData.Builder builder = ServiceAPIMetaData.builder();
		builder.name(apiInterface.getSimpleName());
		builder.apiPort(portClass);

		API api = apiInterface.getAnnotation(API.class);
		API.Version version = api != null ? api.version() : DEFAULT_VERSION;
		builder.version(new ServiceVersion(version.number(), version.tag()));

		TypeFactory typeFactory = new TypeFactory(apiInterface.getPackage(), portClass.getPackage());

		for (Method m : portClass.getDeclaredMethods()) {
			builder.method(createMethod(typeFactory, m));
		}
		return builder.build();
	}

	private ServiceMethod createMethod(TypeFactory typeFactory, Method method) {
		ServiceMethod ret = new ServiceMethod();
		ret.setName(method.getName());
		ret.setMethod(method);

		Package domainPackage = method.getDeclaringClass().getPackage();

		for (Class<?> paramType : method.getParameterTypes()) {
			ret.getArguments().add(typeFactory.createType(domainPackage, paramType));
		}
		ret.setReturnType(typeFactory.createType(domainPackage, method.getReturnType()));
		return ret;
	}
}
