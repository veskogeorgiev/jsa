/**
 * 
 */
package jsa.inject;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

/**
 * Used to additionally configure the REST factory bean  
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public interface JaxRsFactoryBeanDecorator {

	void decorate(JAXRSServerFactoryBean factoryBean);

}
