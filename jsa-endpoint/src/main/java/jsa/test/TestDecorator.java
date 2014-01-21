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
package jsa.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;

import javax.inject.Inject;

import lombok.extern.java.Log;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Log
public class TestDecorator {

	@Inject
	private CamelContext camelContext;

	public void decorate(String httpAddress) {
		for (Map.Entry<String, Endpoint> e : camelContext.getEndpointMap().entrySet()) {
			try {
				String uriStr = e.getKey();
				URI uri = new URI(uriStr);
				String scheme = uri.getScheme();

				uriStr = scheme + "://" + httpAddress + uriStr.substring(scheme.length() + 3);

//				log.info(String.format("%s: %s", e.getKey(), e.getValue()));
				
				Endpoint endpoint = camelContext.getEndpoint(uriStr);
				camelContext.addEndpoint(uriStr, endpoint);
			}
			catch (URISyntaxException ex) {
				log.log(Level.SEVERE, null, ex);
			}
			catch (Exception ex) {
				log.log(Level.SEVERE, null, ex);
			}
		}
	}
}
