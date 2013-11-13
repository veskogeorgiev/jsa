/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsa.compiler.meta.rest;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class PathParamPart extends PathPart {

	public PathParamPart(String part) {
		super(part);
	}

	@Override
	public String accept(PathVisitor visitor) {
		return visitor.visit(this);
	}

}
