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
package jsa.routes;

import org.apache.camel.Endpoint;
import org.apache.camel.NoSuchEndpointException;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.cxf.BusFactory;

/**
 *
 * @author vesko
 */
public class SOAPRouterBuilder extends AbstractRouterBuilder {

	public SOAPRouterBuilder(Class<?> apiPort, Processor processor) {
		super(apiPort, processor);
	}

	@Override
	public void configure() throws Exception {
		fromSoapEndpoint().process(processor).to("log:output");
	}

	@Override
	public Endpoint endpoint(String uri) throws NoSuchEndpointException {
		Endpoint endpoint = super.endpoint(uri);
		if (endpoint instanceof CxfEndpoint) {
			((CxfEndpoint) endpoint).setBus(BusFactory.getDefaultBus());
		}
		return endpoint;
	}

	protected CxfEndpoint soapEndpoint() throws Exception {
		return (CxfEndpoint) endpoint(defaultEndpointAddress("cxf"));
	}

	protected RouteDefinition fromSoapEndpoint() throws Exception {
		return from(soapEndpoint());
	}

}
