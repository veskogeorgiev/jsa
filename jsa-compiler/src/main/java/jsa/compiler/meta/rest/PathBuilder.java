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
package jsa.compiler.meta.rest;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class PathBuilder {

	private final List<PathPart> parts = new LinkedList<>();

	public PathBuilder addRawString(String strPart) {
		return addParts(Arrays.asList(strPart.split("/")));
	}

	public PathBuilder addPart(String strPart) {
		PathPart part = createPart(strPart);
		return addPart(part);
	}

	public PathBuilder addParts(Collection<String> strParts) {
		for (String strPart : strParts) {
			addPart(strPart);
		}
		return this;
	}

	public PathBuilder addPart(PathPart part) {
		parts.add(part);
		return this;
	}

	public String buildPath(PathVisitor visitor) {
		StringBuilder sb = new StringBuilder("'");
		boolean queryParamAppended = false;
		
		for (PathPart part : parts) {
			if (part instanceof PathQueryPart) {
				if (!queryParamAppended) {
					sb.append("?");
					queryParamAppended = true;
				}
			}
			else {
				sb.append("/");
			}
			sb.append(part.accept(visitor));
		}
		sb.append("'");
		return sb.toString();
	}

	private PathPart createPart(String part) {
		if (part.startsWith("{") && part.endsWith("}")) {
			return new PathParamPart(part.substring(1, part.length() - 1));
		}
		return new PlainPathPart(part);
	}
}
