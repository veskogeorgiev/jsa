/**
 * 
 */
package jsa.inject;

import java.util.LinkedList;
import java.util.List;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class DefaultFactoryBeanDecorator implements FactoryBeanDecorator {

	@Override
	public void decorate(JAXRSServerFactoryBean factoryBean) {
		List<Object> providers = new LinkedList<>();
		// json
		providers.add(new JacksonJsonProvider());
		
		// swagger
		providers.add(new ResourceListingProvider());
		providers.add(new ApiDeclarationProvider());

		factoryBean.setProviders(providers);
	}

}
