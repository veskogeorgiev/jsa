/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jsa.proc.meta.rest;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import net.sf.jsa.proc.meta.refl.AnnotatedParameter;
import net.sf.jsa.proc.meta.refl.ReflectionUtils;
import org.apache.camel.Consume;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class RestMethodMeta {
	
	private final RestResourceMeta restRource; 
	private final Method method;

	private List<AnnotatedParameter<QueryParam>> queryParameters;
	private List<AnnotatedParameter<PathParam>> pathParameters;
	private List<AnnotatedParameter<FormParam>> formParameters;

	public RestMethodMeta(RestResourceMeta restRource, Method method) {
		this.restRource = restRource;
		this.method = method;
	}

	public String getDeclaredPath() {
		return getPath(method.getAnnotation(Path.class));
	}

	public String getPath(PathVisitor pathVisitor) {
		PathBuilder builder = new PathBuilder();
		builder.addRawString(restRource.getAPIPath());
		builder.addRawString(getDeclaredPath());

		for (AnnotatedParameter<QueryParam> ap : getQueryAnnotatedParameters()) {
			builder.addPart(new PathQueryPart(ap.getAnnotation().value()));
		}
		return builder.buildPath(pathVisitor);
	}

	public List<String> getFunctionalParameters() {
		List<String> ret = new LinkedList<>();
		for (AnnotatedParameter<PathParam> ap : getPathAnnotatedParameters()) {
			ret.add(ap.getAnnotation().value());
		}
		for (AnnotatedParameter<QueryParam> ap : getQueryAnnotatedParameters()) {
			ret.add(ap.getAnnotation().value());
		}
		for (AnnotatedParameter<FormParam> ap : getFormAnnotatedParameters()) {
			ret.add(ap.getAnnotation().value());
		}
		return ret;
	}

	public List<String> getFormParameters() {
		List<String> ret = new LinkedList<>();
		for (AnnotatedParameter<FormParam> ap : getFormAnnotatedParameters()) {
			ret.add(ap.getAnnotation().value());
		}
		return ret;
	}

	public boolean isFormEndcoded() {
		return !getFormAnnotatedParameters().isEmpty();
	}

	public boolean isJSONEndcoded() {
		if (method.isAnnotationPresent(Consume.class)) {
			for (String mediaType : method.getAnnotation(Consumes.class).value()) {
				if (MediaType.APPLICATION_JSON.equals(mediaType)) {
					return true;
				}
			}
		}
		return false;
	}

	private String getPath(Path path) {
		return path != null ? path.value() : "";
	}

	private List<AnnotatedParameter<FormParam>> getFormAnnotatedParameters() {
		if (formParameters == null) {
			formParameters = ReflectionUtils.getArgumentsWithAnnotation(method, FormParam.class);
		}
		return formParameters;
	}

	private List<AnnotatedParameter<PathParam>> getPathAnnotatedParameters() {
		if (pathParameters == null) {
			pathParameters = ReflectionUtils.getArgumentsWithAnnotation(method, PathParam.class);
		}
		return pathParameters;
	}

	private List<AnnotatedParameter<QueryParam>> getQueryAnnotatedParameters() {
		if (queryParameters == null) {
			queryParameters = ReflectionUtils.getArgumentsWithAnnotation(method, QueryParam.class);
		}
		return queryParameters;
	}

	@Override
	public String toString() {
		return method.toString();
	}

}
