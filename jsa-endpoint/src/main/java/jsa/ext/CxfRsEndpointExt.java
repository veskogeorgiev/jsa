/*
 * Copyright (C) 2013 <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
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
 */

package jsa.ext;

import javax.inject.Provider;

import org.apache.camel.Component;
import org.apache.camel.component.cxf.jaxrs.CxfRsEndpoint;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class CxfRsEndpointExt extends CxfRsEndpoint {

	private Provider<JAXRSServerFactoryBean> factoryBean;

	public CxfRsEndpointExt(String endpointUri, Component component, Provider<JAXRSServerFactoryBean> factoryBean) {
		super(endpointUri, component);
		this.factoryBean = factoryBean;
	}

	@Override
	protected JAXRSServerFactoryBean newJAXRSServerFactoryBean() {
		return factoryBean.get();
	}

}
