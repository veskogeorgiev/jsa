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
package jsa.compiler.js;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jsa.compiler.AbstractSourceGenerator;
import jsa.compiler.SourceFile;
import jsa.compiler.SourceGenerationContext;
import jsa.compiler.meta.rest.RestMethodMeta;
import jsa.compiler.meta.rest.RestPortMeta;
import jsa.compiler.meta.types.ComplexType;
import jsa.compiler.meta.types.CustomType;
import jsa.compiler.meta.types.EnumType;
import jsa.compiler.meta.types.Field;
import jsa.compiler.meta.types.Type;

import com.google.common.base.Joiner;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
class JSSourceGenerator extends AbstractSourceGenerator {

    private static final String HTTP_INTERFACE = "HttpInterface";

    private String apiName;
    private RestPortMeta restMeta;
    private SourceFile sf;

    private final Set<CustomType> dtos = new HashSet<CustomType>();

    JSSourceGenerator(RestPortMeta restMeta, SourceGenerationContext context) {
        super(restMeta.getApiMeta(), context);
        this.restMeta = restMeta;

        this.apiName = String.format("%s.%s", context.getNamespace(), api.getName());

        collectDtos();
    }

    @Override
    public List<SourceFile> write() {
        sf = newSourceFile(api.getName(), "  ", "{", "}");

        writeHeader(sf);
        writeNamespace();
        writeCustomTypes();
        writeService();
        writeMethods();

        return finalizeSourceFiles();
    }

    private void writeNamespace() {
        sf.line("// HTTP url context");
        sf.blockOpen("if (typeof %s === 'undefined')", context.getNamespace());
        sf.blockOpen("%s =", context.getNamespace());
        sf.line("url: '%s'", context.getContext());
        sf.blockClose();
        sf.blockClose();
    }

    private void writeService() {
        sf.line("// %s service API", restMeta.getApiMeta().getName());
        sf.blockOpen("%s = function(%s)", apiName, HTTP_INTERFACE);
        sf.line("this.%s = %s", HTTP_INTERFACE, HTTP_INTERFACE);
        sf.line("this.ctx = %s.url + '%s'", context.getNamespace(), restMeta.getFullContext());
        sf.blockClose();
        sf.line("%s.prototype = {}", apiName);
    }

    private void writeMethods() {
        for (RestMethodMeta sm : restMeta.getMethods()) {
            writeMethod(sm);
        }
    }

    private void writeMethod(RestMethodMeta rmm) {
        String url = rmm.getPath(new JavaScriptPathVisitor());
        String name = rmm.getName();
        String payload = buildPayload(rmm);
        String httpMethod = rmm.getHttpMethod();
        String parameters = buildParameters(rmm);
        String contentType = rmm.getConsumesContentType();

        sf.blockOpen("%s.prototype.%s = function(%s)", apiName, name, parameters);

        sf.blockOpen("var req =");
        if (contentType != null) {
      	  sf.line("headers: { 'Content-Type': '%s'},", contentType);
        }
        else {
      	  sf.line("headers: { },");
        }
        sf.line("url: this.ctx + %s,", url);
        sf.line("method: '%s',", httpMethod);
        sf.line("data: %s", payload);
        sf.blockClose();

        sf.line("return this.%s.send(req)", HTTP_INTERFACE);

        sf.blockClose();
    }

    private void writeCustomTypes() {
        for (CustomType type : dtos) {
            if (type instanceof ComplexType) {
                writeComplexType((ComplexType) type);
            }
            if (type instanceof EnumType) {
                writeEnumType((EnumType) type);
            }
        }
    }

    private void writeComplexType(ComplexType type) {
        sf.blockOpen("%s.%s = function(%s)", context.getNamespace(), type.getName(),
                getFieldList(type));
        for (Field f : type.getFields()) {
            sf.line("this.%s = %s", f.getName(), f.getName());
        }
        sf.blockClose();
    }

    private void writeEnumType(EnumType type) {
        sf.blockOpen("%s.%s =", context.getNamespace(), type.getName());

        int idx = 0;
        for (String s : type.getValues()) {
            sf.line("'%s': %s,", s, idx++);
        }
        sf.blockClose();
    }

    private void collectDtos() {
        for (RestMethodMeta sm : restMeta.getMethods()) {
            // addDtoType(sm.getReturnType());
            for (Type type : sm.getParameters()) {
                addDtoType(type);
            }
        }
    }

    private void addDtoType(Type type) {
        if (type instanceof CustomType) {
            dtos.add((CustomType) type);

            if (type instanceof ComplexType) {
                for (Field f : ((ComplexType) type).getFields()) {
                    addDtoType(f.getType());
                }
            }
        }
    }

    private String buildParameters(RestMethodMeta rmm) {
        List<String> functionalParams = rmm.getFunctionalParameters();

//        if (rmm.isJSONEndcoded()) {
//            functionalParams.add("_payload");
//        }
        return Joiner.on(", ").join(functionalParams);
    }

    private String buildPayload(RestMethodMeta rmm) {
        if (rmm.isFormEndcoded()) {
            Map<String, String> jsonObj = new HashMap<String, String>();
            for (String formParam : rmm.getFormParameters()) {
                jsonObj.put(formParam, formParam);
            }
            return buildURLEncodedString(jsonObj);
        }
        if (rmm.isJSONEndcoded()) {
            List<String> payloadParams = rmm.getNonAnnotatedParameters();
            if (payloadParams.size() == 1) {
                return payloadParams.get(0);
            }
            return "[" + Joiner.on(", ").join(rmm.getNonAnnotatedParameters()) + "]";
//            return "_payload";
        }
        return "null";
    }

    private String buildJSONString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder().append("{");
        for (Map.Entry<String, String> e : map.entrySet()) {
            sb.append("'").append(e.getKey()).append("': ").append(e.getValue()).append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), "}");
        return sb.toString();
    }

    private String buildURLEncodedString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : map.entrySet()) {
            String key = urlEncode(e.getKey());
            
            sb.append("'&").append(key).append("='+");
            sb.append("encodeURIComponent(").append(e.getValue()).append(")+");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private String getFieldList(ComplexType type) {
        List<String> l = new LinkedList<String>();
        for (Field d : type.getFields()) {
            l.add(d.getName());
        }
        return Joiner.on(", ").join(l);
    }

    private String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
