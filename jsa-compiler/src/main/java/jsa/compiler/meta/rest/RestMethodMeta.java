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

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jsa.compiler.meta.AbstractAPIMethodMeta;
import jsa.compiler.meta.refl.AnnotatedParameter;
import jsa.compiler.meta.refl.ReflectionUtils;
import jsa.compiler.meta.types.Type;

import com.google.common.base.Joiner;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class RestMethodMeta extends AbstractAPIMethodMeta {

    @SuppressWarnings("unchecked") private static final Class<? extends Annotation>[] annotations = new Class[] {
            GET.class, PUT.class, POST.class, DELETE.class};

    private RestPortMeta restMeta;

    private List<AnnotatedParameter<QueryParam>> queryParameters;
    private List<AnnotatedParameter<PathParam>> pathParameters;
    private List<AnnotatedParameter<FormParam>> formParameters;

    public String getDeclaredPath() {
        return getPath(method.getAnnotation(Path.class));
    }

    public String getPath(PathVisitor pathVisitor) {
        PathBuilder builder = new PathBuilder();
        builder.addRawString(restMeta.getPath());
        builder.addRawString(getDeclaredPath());

        for (AnnotatedParameter<QueryParam> ap : getQueryAnnotatedParameters()) {
            builder.addPart(new PathQueryPart(ap.getAnnotation().value()));
        }
        return builder.buildPath(pathVisitor);
    }

    public List<String> getFunctionalParameters() {
        List<String> ret = new LinkedList<String>();
        for (AnnotatedParameter<PathParam> ap : getPathAnnotatedParameters()) {
            ret.add(ap.getAnnotation().value());
        }
        for (AnnotatedParameter<QueryParam> ap : getQueryAnnotatedParameters()) {
            ret.add(ap.getAnnotation().value());
        }
        for (AnnotatedParameter<FormParam> ap : getFormAnnotatedParameters()) {
            ret.add(ap.getAnnotation().value());
        }
        ret.addAll(getNonAnnotatedParameters());

        return ret;
    }

    public List<Type> getPostBodyObjectTypes() {
        List<Type> res = new LinkedList<Type>();
        if (!arguments.isEmpty()) {
            List<Integer> idxs = ReflectionUtils.getUnannotatedArguments(method);
            for (Integer idx : idxs) {
                res.add(arguments.get(idx));
            }
        }
        return res;
    }

    public List<String> getNonAnnotatedParameters() {
        List<String> res = new LinkedList<String>();

        if (!arguments.isEmpty()) {
            int arg = 0;
            for (@SuppressWarnings("unused") Integer i : ReflectionUtils.getUnannotatedArguments(method)) {
                res.add("payloadArg" + arg++);
            }
        }
        return res;
    }

    public List<String> getFormParameters() {
        List<String> ret = new LinkedList<String>();
        for (AnnotatedParameter<FormParam> ap : getFormAnnotatedParameters()) {
            ret.add(ap.getAnnotation().value());
        }
        return ret;
    }

    public boolean isFormEndcoded() {
        return !getFormAnnotatedParameters().isEmpty();
    }

    public boolean isJSONEndcoded() {
        String contentType = getConsumesContentType();

        return contentType != null && contentType.contains(MediaType.APPLICATION_JSON);
    }

    public String getConsumesContentType() {
        Consumes consumes = null;
        if (method.isAnnotationPresent(Consumes.class)) {
            consumes = method.getAnnotation(Consumes.class);
        }
        else if (method.getDeclaringClass().isAnnotationPresent(Consumes.class)) {
            consumes = method.getDeclaringClass().getAnnotation(Consumes.class);
        }

        if (consumes != null) {
            return Joiner.on(",").join(consumes.value());
        }
        return null;
    }

    public String getProducesContentType() {
        Produces produces = null;
        if (method.isAnnotationPresent(Produces.class)) {
            produces = method.getAnnotation(Produces.class);
        }
        else if (method.getDeclaringClass().isAnnotationPresent(Produces.class)) {
            produces = method.getDeclaringClass().getAnnotation(Produces.class);
        }

        if (produces != null) {
            return Joiner.on(",").join(produces.value());
        }
        return "*/*";
    }

    public String getHttpMethod() {
        for (Class<? extends Annotation> a : annotations) {
            if (method.isAnnotationPresent(a)) {
                return a.getSimpleName();
            }
        }
        for (Class<? extends Annotation> a : annotations) {
            if (method.getDeclaringClass().isAnnotationPresent(a)) {
                return a.getSimpleName();
            }
        }
        throw new RuntimeException(String.format("Cannot determine HTTP method for API method %s ",
                method));
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractAPIMethodMeta.Builder<RestMethodMeta> {

        public Builder() {
            super(new RestMethodMeta());
        }

        public Builder restMeta(RestPortMeta restMeta) {
            instance.restMeta = restMeta;
            return this;
        }
    }
}
