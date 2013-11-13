/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsa.compiler;

import jsa.compiler.meta.ServiceAPI;
import java.util.List;

/**
 *
 * @author vesko
 */
public interface ClientServiceGenerator {
	
	List<SourceFile> write(ServiceAPI serviceAPI);
}
