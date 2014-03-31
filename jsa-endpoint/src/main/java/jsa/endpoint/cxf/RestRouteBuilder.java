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

import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.jaxrs.CxfRsEndpoint;
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
    protected Processor createProcessor() {
        return new RestProcessor();
    }

    @Override
    protected Endpoint createEndpoint(Class<?> apiPort) {
        CxfRsEndpoint endpoint = createEndpoint("cxfrs", CxfRsEndpoint.class);
        endpoint.setBus(bus);
        endpoint.addResourceClass(apiPort);
        endpoint.setProviders(getAdditionalProviders(apiPort));

        return endpoint;
    }

    private List<Object> getAdditionalProviders(Class<?> restDecorator) {
        List<Object> providers = new LinkedList<Object>();
        
        // add global API module config
        if (jaxRsConfig != null) {
            jaxRsConfig.addProviders(providers);
        }

        // add local API config
        ExposeRest rest = restDecorator.getAnnotation(ExposeRest.class);
        Class<?>[] localProviders = rest.providers();
        for (Class<?> providerClass : localProviders) {
            try {
                providers.add(providerClass.newInstance());
            }
            catch (Exception e) {
                log.warn("Could not instantiate provider : " + providerClass, e);
            }
        }
        
        // if nothing so far, put JSON Provider
        if (providers.isEmpty()) {
            providers.add(new JacksonJsonProvider());
        }

        // add default ones
        providers.add(new SourceGenerator(context));
        providers.add(new WadlGeneratorExt());

        return providers;
    }

}
