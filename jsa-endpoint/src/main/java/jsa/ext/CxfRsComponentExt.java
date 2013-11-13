/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsa.ext;

import java.util.Iterator;
import java.util.Map;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.component.cxf.jaxrs.CxfRsComponent;
import org.apache.camel.component.cxf.jaxrs.CxfRsEndpoint;
import org.apache.camel.util.CastUtils;
import org.apache.camel.util.ObjectHelper;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class CxfRsComponentExt extends CxfRsComponent {

	public CxfRsComponentExt() {
	}

	public CxfRsComponentExt(CamelContext context) {
		super(context);
	}

	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		CxfRsEndpoint answer = new CxfRsEndpointExt(remaining, this);

		String resourceClass = getAndRemoveParameter(parameters, "resourceClass", String.class);
		if (resourceClass != null) {
			Class<?> clazz = getCamelContext().getClassResolver().resolveMandatoryClass(resourceClass);
			answer.addResourceClass(clazz);
		}

		String resourceClasses = getAndRemoveParameter(parameters, "resourceClasses", String.class);
		Iterator<?> it = ObjectHelper.createIterator(resourceClasses);
		while (it.hasNext()) {
			String name = (String) it.next();
			Class<?> clazz = getCamelContext().getClassResolver().resolveMandatoryClass(name);
			answer.addResourceClass(clazz);
		}

		setProperties(answer, parameters);
		Map<String, String> params = CastUtils.cast(parameters);
		answer.setParameters(params);
		setEndpointHeaderFilterStrategy(answer);
		return answer;
	}
}
