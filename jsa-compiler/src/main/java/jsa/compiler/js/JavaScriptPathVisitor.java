/*******************************************************************************
 * Copyright (C) 2013 <a href="mailto:vesko.georgiev@icloud.com">Vesko Georgiev</a>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *******************************************************************************/
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
