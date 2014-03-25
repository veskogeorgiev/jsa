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
package jsa.endpoint.cxf;

import java.util.LinkedList;
import java.util.List;

import jsa.endpoint.APIModuleContextAware;
import jsa.endpoint.AbstractRouteBuilder;
import jsa.endpoint.CxfBusAware;
import jsa.endpoint.cxf.ext.SourceGenerator;
import jsa.endpoint.cxf.ext.WadlGeneratorExt;
import lombok.Setter;

import org.apache.camel.Processor;
import org.apache.camel.component.cxf.jaxrs.CxfRsEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.cxf.Bus;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * 
 * @author vesko
 */
public class RestRouteBuilder extends AbstractRouteBuilder implements CxfBusAware,
        APIModuleContextAware, JaxRsConfigAware {

    private @Setter Bus bus;
    private @Setter JaxRsConfig jaxRsConfig;
    private String context;

    @Override
    public void setAPIModuleContext(String context) {
        this.context = context;
    }

    @Override
    public void configure() throws Exception {
        CxfRsEndpoint endpoint = restEndpoint(apiPortMeta.getApiPortClass());
        from(endpoint).process(processor).end();
    }

    @Override
    protected Processor createProcessor() {
        return new RestProcessor();
    }

    protected CxfRsEndpoint restEndpoint(Class<?> restDecorator) throws Exception {
        CxfRsEndpoint endpoint = createEndpoint("cxfrs", CxfRsEndpoint.class);
        endpoint.setBus(bus);
        endpoint.addResourceClass(restDecorator);
        endpoint.setProviders(getAdditionalProviders(restDecorator));

        return endpoint;
    }

    protected RouteDefinition fromRestEndpoint(Class<?> restDecorator) throws Exception {
        return from(restEndpoint(restDecorator));
    }

    private List<Object> getAdditionalProviders(Class<?> restDecorator) {
        List<Object> res = new LinkedList<Object>();
        List<Object> providers = getProvidersFromConfig();

        if (providers != null) {
            res.addAll(jaxRsConfig.providers());
        }
        else {
            res.add(new JacksonJsonProvider());
        }

        SourceGenerator sourceGenerator = new SourceGenerator(context);
        WadlGeneratorExt wg = new WadlGeneratorExt();

        res.add(sourceGenerator);
        res.add(wg);

        ExposeRest rest = restDecorator.getAnnotation(ExposeRest.class);
        Class<?>[] localProviders = rest.providers();
        for (Class<?> providerClass : localProviders) {
            try {
                res.add(providerClass.newInstance());
            }
            catch (Exception e) {
                log.warn("Could not instantiate provider : " + providerClass, e);
            }
        }
        return res;
    }

    private List<Object> getProvidersFromConfig() {
        if (jaxRsConfig != null) {
            List<Object> providers = jaxRsConfig.providers();
            if (providers != null) {
                return jaxRsConfig.providers();
            }
        }
        return null;
    }
}
