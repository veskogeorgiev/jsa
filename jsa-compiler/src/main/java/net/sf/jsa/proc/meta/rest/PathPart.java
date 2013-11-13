/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.jsa.proc.meta.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@AllArgsConstructor
public abstract class PathPart {

	@Getter
	protected final String part;
	
	public abstract String accept(PathVisitor visitor);
}
