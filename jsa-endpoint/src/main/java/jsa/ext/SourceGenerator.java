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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import jsa.annotations.APIContext;
import jsa.compiler.MetaDataException;
import jsa.compiler.SourceCodeGenerator;
import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.compiler.SourceFile;
import jsa.compiler.SourceGenerationContext;
import jsa.endpoint.PortExposerService;
import jsa.endpoint.spi.PortExposer.SourceGenerationConfig;

import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;

import com.google.inject.Injector;

/**
 * Intercepts the request for '?_js' query path and returns the generated Java
 * Script client for API.
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */

public class SourceGenerator implements RequestHandler {

	public static final String JS_TYPE = "application/javascript";

	private Map<String, SourceCodeGeneratorFactory> factories = new HashMap<String, SourceCodeGeneratorFactory>();

	@Inject private Injector injector;
	@Inject @APIContext private String apiContext;

	@Inject
	public void init() {
		for (SourceGenerationConfig sg : PortExposerService.getInstance().sourceGenerators()) {
			SourceCodeGeneratorFactory factoryInstance = injector.getInstance(sg.getFactory());
			factories.put(sg.getContext(), factoryInstance);
		}
	}

	@Override
	public Response handleRequest(Message m, ClassResourceInfo resource) {
		if (!"GET".equals(m.get(Message.HTTP_REQUEST_METHOD))) {
			return null;
		}

		UriInfo ui = new UriInfoImpl(m);

		MultivaluedMap<String, String> params = ui.getQueryParameters();

		SourceCodeGeneratorFactory factory = getFactory(params);
		if (factory == null) {
			return null;
		}

		String namespace = params.getFirst("ns");
		if (namespace == null) {
			namespace = "ns";
		}
		Class<?> apiPortClass = resource.getResourceClass();

		m.getExchange().put(JAXRSUtils.IGNORE_MESSAGE_WRITERS, true);

		try {
			String content = getContent(apiPortClass, factory, namespace);
			return Response.ok().type(JS_TYPE).entity(content).build();
		}
		catch (MetaDataException e) {
			e.printStackTrace();
			return Response.serverError().type(MediaType.TEXT_PLAIN)
			      .entity("Ooops... That's emarassing").build();
		}
	}

	private String getContent(Class<?> apiPort, SourceCodeGeneratorFactory factory, String namespace) {
		StringBuilder sb = new StringBuilder();
		for (SourceFile sf : generateJavaScriptSources(apiPort, factory, namespace)) {
			sb.append(sf).append("\n");
		}
		return sb.toString();
	}

	private List<SourceFile> generateJavaScriptSources(Class<?> apiPort,
	      SourceCodeGeneratorFactory factory, String namespace) {
		SourceCodeGenerator gen = factory.create(apiPort, new SourceGenerationContext(apiContext,
		      namespace));
		return gen.write();
	}

	private SourceCodeGeneratorFactory getFactory(MultivaluedMap<String, String> params) {
		for (Entry<String, SourceCodeGeneratorFactory> e : factories.entrySet()) {
			if (params.containsKey(e.getKey())) {
				return e.getValue();
			}
		}
		return null;
	}
}
