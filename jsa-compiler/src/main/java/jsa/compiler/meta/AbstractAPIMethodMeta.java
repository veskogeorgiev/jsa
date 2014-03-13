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

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import jsa.compiler.meta.types.Type;
import lombok.Getter;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Getter
public abstract class AbstractAPIMethodMeta {

	protected Method method;
	protected Type returnType;
	protected List<Type> arguments = new LinkedList<Type>();

	public String getName() {
		return method.getName();
	}

	public Package getPackage() {
		return method.getDeclaringClass().getPackage();
	}

	public abstract static class Builder<T extends AbstractAPIMethodMeta> extends AbstractBuilder<T> {

		public Builder(T instance) {
			super(instance);
		}

		public Builder<T> returnType(Type returnType) {
			instance.returnType = returnType;
			return this;
		}

		public Builder<T> argument(Type arg) {
			instance.arguments.add(arg);
			return this;
		}

		public Builder<T> method(Method method) {
			instance.method = method;
			return this;
		}
	}
}
