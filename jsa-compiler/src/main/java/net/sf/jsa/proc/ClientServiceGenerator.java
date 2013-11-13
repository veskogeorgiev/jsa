/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jsa.proc;

import net.sf.jsa.proc.meta.ServiceAPI;
import java.util.List;

/**
 *
 * @author vesko
 */
public interface ClientServiceGenerator {
	
	List<SourceFile> write(ServiceAPI serviceAPI);
}
