/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jsa.proc.meta.rest;

/**
 *
 * @author vesko
 */
public interface PathVisitor {
	
	String visit(PlainPathPart part);
	
	String visit(PathParamPart part);

	String visit(PathQueryPart part);
}
