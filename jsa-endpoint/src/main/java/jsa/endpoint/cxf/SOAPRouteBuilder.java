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

import jsa.compiler.meta.AbstractAPIPortMeta;
import jsa.endpoint.AbstractRouteBuilder;
import jsa.endpoint.processors.DefaultAPIPortMeta;

import org.apache.camel.Processor;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.model.RouteDefinition;

/**
 * 
 * @author vesko
 */
public class SOAPRouteBuilder extends AbstractRouteBuilder {

	@Override
	protected Processor createProcessor(Class<?> apiPort) {
		return new SoapProcessor(apiPort);
	}

	@Override
	protected AbstractAPIPortMeta createPortMeta(Class<?> apiPort) {
		return DefaultAPIPortMeta.create(apiPort);
	}

	@Override
	public void configure() throws Exception {
		fromSoapEndpoint().process(processor).end();
	}

	protected CxfEndpoint soapEndpoint() throws Exception {
		CxfEndpoint endpoint = createEndpoint("cxf", CxfEndpoint.class);
		endpoint.setBus(bus);
		endpoint.setServiceClass(apiPortMeta.getApiPortClass());

		return endpoint;
	}

	protected RouteDefinition fromSoapEndpoint() throws Exception {
		return from(soapEndpoint());
	}

}
