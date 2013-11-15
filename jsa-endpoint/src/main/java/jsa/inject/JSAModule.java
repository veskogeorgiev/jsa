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
package jsa.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import jsa.ext.CxfRsComponentExt;
import org.apache.bval.guice.ValidationModule;
import org.apache.camel.CamelContext;
import org.apache.camel.component.cxf.jaxrs.CxfRsComponent;
import org.apache.camel.guice.CamelModule;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class JSAModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new CamelModule());
		install(new ValidationModule());
	}

	@Provides
	CxfRsComponent cxfRsComponent(CamelContext camelContext) {
		CxfRsComponent cmp = new CxfRsComponentExt(camelContext);
		return cmp;
	}
}
