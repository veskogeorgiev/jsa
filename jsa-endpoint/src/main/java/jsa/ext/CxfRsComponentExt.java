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

import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.component.cxf.jaxrs.CxfRsComponent;
import org.apache.camel.component.cxf.jaxrs.CxfRsEndpoint;
import org.apache.camel.util.CastUtils;
import org.apache.camel.util.ObjectHelper;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class CxfRsComponentExt extends CxfRsComponent {

	@Inject private Provider<JAXRSServerFactoryBean> factoryBean;

	public CxfRsComponentExt(CamelContext context) {
		super(context);
	}

	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		CxfRsEndpoint answer = new CxfRsEndpointExt(remaining, this, factoryBean);

		String resourceClass = getAndRemoveParameter(parameters, "resourceClass", String.class);
		if (resourceClass != null) {
			Class<?> clazz = getCamelContext().getClassResolver().resolveMandatoryClass(resourceClass);
			answer.addResourceClass(clazz);
		}

		String resourceClasses = getAndRemoveParameter(parameters, "resourceClasses", String.class);
		Iterator<?> it = ObjectHelper.createIterator(resourceClasses);
		while (it.hasNext()) {
			String name = (String) it.next();
			Class<?> clazz = getCamelContext().getClassResolver().resolveMandatoryClass(name);
			answer.addResourceClass(clazz);
		}

		setProperties(answer, parameters);
		Map<String, String> params = CastUtils.cast(parameters);
		answer.setParameters(params);
		setEndpointHeaderFilterStrategy(answer);
		return answer;
	}

}
