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
package jsa.endpoint.cxf.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import jsa.compiler.MetaDataException;
import jsa.compiler.SourceCodeGenerator;
import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.compiler.SourceFile;
import jsa.compiler.SourceGenerationContext;
import jsa.endpoint.PluginService;
import lombok.extern.slf4j.Slf4j;

import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;

/**
 * Intercepts the request for '?_js' query path and returns the generated Java
 * Script client for API.
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Slf4j
public class SourceCodeHandler implements RequestHandler {

    public static final String JS_TYPE = "application/javascript";

    private Map<String, SourceCodeGeneratorFactory> factories = new HashMap<String, SourceCodeGeneratorFactory>();
    private String moduleContext;

    public SourceCodeHandler(String moduleContext) {
        this.moduleContext = moduleContext;

        factories = PluginService.getInstance().loadSourceGenerators();
        log.info("Available source generators: " + factories);
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
        SourceCodeGenerator gen = factory.create(apiPort, new SourceGenerationContext(
                moduleContext, namespace));
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
