/*
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
 */
package jsa.inject;

import java.util.Arrays;
import java.util.Set;

import javax.inject.Inject;

import jsa.ext.CxfRsComponentExt;
import jsa.ext.JsGenerator;
import lombok.extern.java.Log;

import org.apache.camel.CamelContext;
import org.apache.camel.component.cxf.jaxrs.CxfRsComponent;
import org.apache.camel.guice.CamelModule;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.ProviderFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Log
public class JSAModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new CamelModule());

		bind(Initializer.class).asEagerSingleton();
	}

	@Provides
	CxfRsComponent cxfRsComponent(CamelContext camelContext, Injector injector) {
		CxfRsComponent cmp = new CxfRsComponentExt(camelContext);
		injector.injectMembers(cmp);
		
		return cmp;
	}

	@Provides
	JAXRSServerFactoryBean factoryBean(Injector injector, Set<JaxRsFactoryBeanDecorator> decorators) {
		JAXRSServerFactoryBean bean = new JAXRSServerFactoryBean();

		// decorate the factory bean if any decorators are defined
		try {
//			Set<JaxRsFactoryBeanDecorator> decorators = injector.getInstance(
//					Key.get(new TypeLiteral<Set<JaxRsFactoryBeanDecorator>>() {}));
			for (JaxRsFactoryBeanDecorator decorator : decorators) {
				decorator.decorate(bean);
			}
		}
		catch (Exception e) {
			log.info("No FactoryBeanDecorator found");
		}

		return bean;
	}

	private static class Initializer {
		@Inject
		public Initializer(JsGenerator jsGenerator) {
			// add custom request processor to intercept "?_js" query path
			ProviderFactory.getSharedInstance().setUserProviders(Arrays.asList(jsGenerator));
		}
	}
}
