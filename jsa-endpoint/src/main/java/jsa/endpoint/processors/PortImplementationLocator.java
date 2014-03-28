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
package jsa.endpoint.processors;

import javax.inject.Inject;
import javax.inject.Singleton;

import jsa.NotImplementedException;
import jsa.annotations.APIPort;
import jsa.endpoint.InstanceLocator;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 * @param <T>
 *            API interface
 */
@Slf4j
@Singleton
public class PortImplementationLocator {

    @Inject private InstanceLocator locator;

    public Object locate(Class<?> apiPort) throws NotImplementedException {
        Class<?> apiInterface = findServiceClassFromAPIPort(apiPort);
        try {
            return locator.locate(apiInterface);
        }
        catch (Exception e) {
            log.warn(String.format("No implementation for %s specified", apiInterface));
            throw new NotImplementedException(e);
        }
    }

    private Class<?> findServiceClassFromAPIPort(Class<?> apiPort) {
        APIPort port = apiPort.getAnnotation(APIPort.class);
        if (port != null) {
            switch (port.type()) {
            case ADAPTER:
                return apiPort;
            case DECORATOR:
                return port.api();
            default:
                break;
            }
        }
        return apiPort;
        // throw new RuntimeException("");
    }

}
