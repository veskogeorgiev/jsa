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
package jsa.endpoint;

import jsa.endpoint.processors.DefaultAPIPortMeta;
import lombok.Getter;

import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public abstract class AbstractRouteBuilder extends RouteBuilder implements HasPorcessor, APIPortAware {

    protected @Getter Processor processor;
    protected @Getter DefaultAPIPortMeta apiPortMeta;

    @Override
    public void setAPIPort(Class<?> apiPort) {
        apiPortMeta = createPortMeta(apiPort);
        if (apiPortMeta.hasCustomProcessor()) {
            try {
                processor = apiPortMeta.getCustomProcessor().newInstance();
            }
            catch (Exception e) {
                throw new RuntimeException("Could not create instance of "
                        + "custom processor: " + apiPortMeta.getCustomProcessor(), e);
            }
        }
        else {
            processor = createProcessor();
        }

        if (processor instanceof APIPortAware) {
            ((APIPortAware) processor).setAPIPort(apiPort);
        }
    }

    protected abstract Processor createProcessor();

    protected DefaultAPIPortMeta createPortMeta(Class<?> apiPort) {
        return DefaultAPIPortMeta.create(apiPort);
    }

    protected <T extends Endpoint> T createEndpoint(String protocol, Class<T> expectedType) {
        return endpoint(endpointAddress(protocol), expectedType);
    }

    protected Endpoint createEndpoint(String protocol) {
        return endpoint(endpointAddress(protocol));
    }

    protected String endpointAddress(String protocol) {
        return String.format("%s://%s", protocol, apiPortMeta.getFullContext());
    }

}
