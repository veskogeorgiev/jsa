/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsa.compiler.js;

import jsa.compiler.meta.rest.PathParamPart;
import jsa.compiler.meta.rest.PathQueryPart;
import jsa.compiler.meta.rest.PathVisitor;
import jsa.compiler.meta.rest.PlainPathPart;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class JavaScriptPathVisitor implements PathVisitor {

	@Override
	public String visit(PlainPathPart part) {
		return part.getPart();
	}

	@Override
	public String visit(PathParamPart part) {
		return String.format("' + encodeURIComponent(%s) + '", part.getPart());
	}

	@Override
	public String visit(PathQueryPart part) {
		return String.format("&%s=' + encodeURIComponent(%s) + '", part.getPart(), part.getPart());
	}

}
