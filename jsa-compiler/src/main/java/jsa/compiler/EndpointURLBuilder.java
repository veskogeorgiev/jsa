/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsa.compiler;

import jsa.compiler.meta.ServiceAPIMetaData;
import javax.inject.Singleton;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Singleton
public class EndpointURLBuilder {
	
	public String buildRest(ServiceAPIMetaData sreviceAPI) {
		return String.format("/%s/%s/rest", sreviceAPI.getName(), sreviceAPI.getVersion().getNumber());
	}
}
