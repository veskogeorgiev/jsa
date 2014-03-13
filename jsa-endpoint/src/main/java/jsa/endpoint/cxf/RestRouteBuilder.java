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
package jsa.endpoint.cxf;

import java.util.Arrays;

import jsa.compiler.meta.AbstractAPIPortMeta;
import jsa.endpoint.AbstractRouteBuilder;
import jsa.endpoint.processors.DefaultAPIPortMeta;

import org.apache.camel.Processor;
import org.apache.camel.component.cxf.jaxrs.CxfRsEndpoint;
import org.apache.camel.model.RouteDefinition;

/**
 * 
 * @author vesko
 */
public class RestRouteBuilder extends AbstractRouteBuilder {

	@Override
	public void configure() throws Exception {
		CxfRsEndpoint endpoint = restEndpoint(apiPortMeta.getApiPortClass());
		from(endpoint).process(processor).end();
	}

	protected CxfRsEndpoint restEndpoint(Class<?> restDecorator) throws Exception {
		CxfRsEndpoint endpoint = createEndpoint("cxfrs", CxfRsEndpoint.class);
		endpoint.setBus(bus);
		endpoint.addResourceClass(restDecorator);
		endpoint.setProviders(Arrays.asList(getAdditionalProviders(restDecorator)));
		return endpoint;
	}

	protected RouteDefinition fromRestEndpoint(Class<?> restDecorator) throws Exception {
		return from(restEndpoint(restDecorator));
	}

	@Override
	protected Processor createProcessor(Class<?> apiPort) {
		return new RestProcessor(apiPort);
	}

	@Override
	protected AbstractAPIPortMeta createPortMeta(Class<?> apiPort) {
		return DefaultAPIPortMeta.create(apiPort);
	}

	private Class<?>[] getAdditionalProviders(Class<?> restDecorator) {
		ExposeRest rest = restDecorator.getAnnotation(ExposeRest.class);
		return rest.providers();
	}
}
