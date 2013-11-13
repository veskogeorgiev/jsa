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
import javax.ws.rs.QueryParam;

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
