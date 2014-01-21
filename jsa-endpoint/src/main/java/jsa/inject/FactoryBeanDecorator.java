/**
 * 
 */
package jsa.inject;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public interface FactoryBeanDecorator {

	void decorate(JAXRSServerFactoryBean factoryBean);

}
