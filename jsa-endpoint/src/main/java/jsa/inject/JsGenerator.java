/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package jsa.inject;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import jsa.annotations.APIPort;
import jsa.compiler.APIProcessor;
import jsa.compiler.ClientServiceGenerator;
import jsa.compiler.SourceFile;
import jsa.compiler.js.JavaScriptClientGenerator;
import jsa.compiler.meta.ServiceAPI;

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

	@Inject private APIProcessor apiProcessor;

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
		ServiceAPI api = apiProcessor.process(apiInterface, apiPort);
		ClientServiceGenerator csg = new JavaScriptClientGenerator();
		List<SourceFile> res = csg.write(api);
		return res;
	}

}
