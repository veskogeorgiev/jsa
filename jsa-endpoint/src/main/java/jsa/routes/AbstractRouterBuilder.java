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
package jsa.routes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import jsa.NotImplementedException;
import jsa.annotations.APIPort;
import jsa.compiler.APIInspector;
import jsa.compiler.meta.ServiceAPIMetaData;
import jsa.inject.PortImplementationLocator;
import jsa.proc.DefaultJSAProcessor;
import lombok.Getter;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.google.common.base.Preconditions;
import com.google.inject.Injector;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public abstract class AbstractRouterBuilder extends RouteBuilder implements RouteWithProcessor {

	@Inject protected Injector injector;
	@Inject protected APIInspector inspector;

	protected final Class<?> apiInterface;
	protected final Class<?> apiPort;
	protected final APIPort apiPortMeta;

	protected @Getter Processor processor;

	protected ServiceAPIMetaData serviceMeta;

	public AbstractRouterBuilder(Class<?> apiInterface, Class<?> apiPort, Processor processor) {
		this.apiInterface = apiInterface;
		this.apiPort = apiPort;
		this.apiPortMeta = apiPort.getAnnotation(APIPort.class);

		Preconditions.checkNotNull(apiPortMeta,
				"If class %s is to be used as a port it must be annotated with @APIPort.");

		this.processor = processor;
	}

	@Inject
	private void init(PortImplementationLocator locator) throws NotImplementedException {
		if (processor == null) {
			processor = new DefaultJSAProcessor(apiPort, locator);
		}
	}

	public ServiceAPIMetaData getServiceAPI() {
		if (serviceMeta == null) {
			serviceMeta = inspector.process(apiInterface, apiPort);
		}
		return serviceMeta;
	}

	protected Map<String, String> getDefaultParams() {
		Map<String, String> params = new HashMap<>();
		params.put("serviceClass", apiPort.getName());
		params.put("setDefaultBus", "true");

		return params;
	}

	protected String defaultEndpointAddress(String protocol, String params) {
		return String.format("%s://%s/%s?%s", protocol, getServiceAPI().getUrl(),
				apiPortMeta.context(), params);
	}

	protected String defaultEndpointAddress(String protocol) {
		return defaultEndpointAddress(protocol, getDefaultParamQuery());
	}

	protected String getDefaultParamQuery() {
		return buildParamQuery(getDefaultParams());
	}

	protected String buildParamQuery(Map<String, String> params) {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, String> e : params.entrySet()) {
			builder.append(e.getKey()).append("=").append(e.getValue()).append("&");
		}
		return builder.substring(0, builder.length() - 1).toString();
	}

}
