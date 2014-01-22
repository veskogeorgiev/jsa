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
package jsa.ext;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import jsa.annotations.APIPort;
import jsa.compiler.APIInspector;
import jsa.compiler.ClientServiceGenerator;
import jsa.compiler.SourceFile;
import jsa.compiler.js.JavaScriptClientGenerator;
import jsa.compiler.meta.ServiceAPIMetaData;

import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;

/**
 * Intercepts the request for '?_js' query path and returns the generated Java Script client for API.
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class JsGenerator implements RequestHandler {

	public static final String JS_QUERY = "_js";
	public static final MediaType JS_TYPE = JAXRSUtils.toMediaType(MediaType.TEXT_PLAIN);

	@Inject private APIInspector apiProcessor;

	@Override
	public Response handleRequest(Message m, ClassResourceInfo resource) {
		if (!"GET".equals(m.get(Message.HTTP_REQUEST_METHOD))) {
			return null;
		}

		UriInfo ui = new UriInfoImpl(m);
		if (!ui.getQueryParameters().containsKey(JS_QUERY)) {
			return null;
		}

		Class<?> apiPortClass = resource.getResourceClass();
		Class<?> apiClass = apiPortClass.getAnnotation(APIPort.class).api();

		m.getExchange().put(JAXRSUtils.IGNORE_MESSAGE_WRITERS, true);
		return Response.ok().type(JS_TYPE).entity(getContent(apiClass, apiPortClass)).build();
	}

	private String getContent(Class<?> apiInterface, Class<?> apiPort) {
		StringBuilder sb = new StringBuilder();
		for (SourceFile sf : generateJavaScriptSources(apiInterface, apiPort)) {
			sb.append(sf).append("\n");
		}
		return sb.toString();
	}

	private List<SourceFile> generateJavaScriptSources(Class<?> apiInterface, Class<?> apiPort) {
		ServiceAPIMetaData api = apiProcessor.process(apiInterface, apiPort);
		ClientServiceGenerator csg = new JavaScriptClientGenerator();
		List<SourceFile> res = csg.write(api);
		return res;
	}

}
