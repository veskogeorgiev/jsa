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
package jsa.endpoint.processors;

import java.lang.annotation.Annotation;

import jsa.annotations.DecorateInvocation;
import jsa.annotations.DelegateInvocation;
import jsa.annotations.Router;
import jsa.compiler.meta.AbstractAPIPortMeta;

import org.apache.camel.Processor;
import org.apache.camel.RoutesBuilder;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class DefaultAPIPortMeta extends AbstractAPIPortMeta {

	protected DefaultAPIPortMeta(Class<?> apiPortClass) {
		super(apiPortClass);
	}

	public boolean hasProcessorDecorator() {
		return has(DecorateInvocation.class);
	}

	public Class<? extends ProcessorDecorator> getProcessorDecorator() {
		return get(DecorateInvocation.class).value();
	}

	public boolean hasProcessorDelegate() {
		return has(DelegateInvocation.class);
	}

	public Class<? extends Processor> getProcessorDelegate() {
		return get(DelegateInvocation.class).value();
	}

	public boolean hasRouter() {
		return has(Router.class);
	}

	public Class<? extends RoutesBuilder> getRouter() {
		return get(Router.class).value();
	}

	private <T extends Annotation> boolean has(Class<T> a) {
		return apiPortClass.isAnnotationPresent(a);
	}

	private <T extends Annotation> T get(Class<T> a) {
		return apiPortClass.getAnnotation(a);
	}

	// //////////////////////////////////////////////////////////////////////////////
	//
	// //////////////////////////////////////////////////////////////////////////////

	public static DefaultAPIPortMeta create(Class<?> apiPort) {
		return new DefaultAPIPortMeta(apiPort);
	}
}
