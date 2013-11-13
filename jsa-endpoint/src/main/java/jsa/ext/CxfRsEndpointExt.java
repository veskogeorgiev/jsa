/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsa.ext;

import java.util.LinkedList;
import java.util.List;

import org.apache.camel.Component;
import org.apache.camel.component.cxf.jaxrs.CxfRsEndpoint;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class CxfRsEndpointExt extends CxfRsEndpoint {

	public CxfRsEndpointExt(String endpointUri, Component component) {
		super(endpointUri, component);
	}

	@Override
	protected JAXRSServerFactoryBean newJAXRSServerFactoryBean() {
		JAXRSServerFactoryBean bean = new JAXRSServerFactoryBean();
		List<?> providers = bean.getProviders();
		List<Object> newPorivders = new LinkedList<>(providers);
		newPorivders.add(new JacksonJsonProvider());
		bean.setProviders(newPorivders);
		return bean;
	}

	
}
