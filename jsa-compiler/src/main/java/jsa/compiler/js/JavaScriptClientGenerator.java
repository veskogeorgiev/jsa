package jsa.compiler.js;

import com.google.common.base.Joiner;
import java.util.HashMap;
import jsa.compiler.AbstractSourceGenerator;
import jsa.compiler.SourceFile;
import jsa.compiler.meta.Field;
import jsa.compiler.meta.rest.RestResourceMeta;
import jsa.compiler.meta.ServiceAPI;
import jsa.compiler.meta.ServiceMethod;
import jsa.compiler.meta.types.ComplexType;
import jsa.compiler.meta.types.CustomType;
import jsa.compiler.meta.types.EnumType;
import jsa.compiler.meta.types.Type;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jsa.compiler.meta.rest.RestMethodMeta;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class JavaScriptClientGenerator extends AbstractSourceGenerator {

	private static final String HTTP_INTERFACE = "HttpInterface";

	private ServiceAPI api;
	private RestResourceMeta restMeta;

	private String apiName;
	private SourceFile sf;

	private final Set<CustomType> dtos = new HashSet<>();
	private final String namespace;

	public JavaScriptClientGenerator() {
		this.namespace = "idg";
	}

	@Override
	public List<SourceFile> write(ServiceAPI api) {
		init(api);

		writeHeader();
		writeNamespace();
		writeCustomTypes();
		writeService();
		writeMethods();

		return finalizeSourceFiles();
	}

	private void writeHeader() {
		sf.line("////////////////////////////////////////////////////////////");
		sf.line("// %s version: %s", api.getName(), api.getVersion().getSingleString());
		sf.line("////////////////////////////////////////////////////////////");
		sf.line("");
	}

	private void writeNamespace() {
		sf.blockOpen("if (typeof %s === 'undefined')", namespace);
		sf.blockOpen("%s =", namespace);
		sf.line("url: 'http://localhost:8080/api'");
		sf.blockClose();
		sf.blockClose();
	}

	private void writeService() {
		sf.blockOpen("%s = function(%s)", apiName, HTTP_INTERFACE);
		sf.line("this.%s = %s", HTTP_INTERFACE, HTTP_INTERFACE);
		sf.line("this.ctx = idg.url + '%s'", api.getApiPortContext());
		sf.blockClose();
		sf.line("%s.prototype = {}", apiName);
	}

	private void writeMethods() {
		for (ServiceMethod sm : api.getMethods()) {
			writeMethod(sm);
		}
	}

	private void writeMethod(ServiceMethod method) {
		RestMethodMeta rmm = restMeta.getRestMethod(method.getMethod());
		System.out.println(rmm);
		String url = rmm.getPath(new JavaScriptPathVisitor());
		String name = method.getName();

		String parameters = buildParameters(rmm);
		sf.blockOpen("%s.prototype.%s = function(%s)", apiName, name, parameters);

		String payload = buildPayload(rmm);
		sf.line("return new %s(this.%s, this.ctx + %s, %s)",
				getRequestType(rmm), HTTP_INTERFACE, url, payload);
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
		sf.blockOpen("%s.%s = function(args)", namespace, type.getName());
		for (Field f : type.getFields()) {
			sf.line("this.%s = null", f.getName());
		}
		sf.blockOpen("if (args)");
		for (Field f : type.getFields()) {
			sf.line("if (args.%s !== undefined) this.%s = args.%s",
					f.getName(), f.getName(), f.getName());
		}
		sf.blockClose();
		sf.blockClose();
	}

	private void writeEnumType(EnumType type) {
		sf.blockOpen("%s.%s =", namespace, type.getName());

		int idx = 0;
		for (String s : type.getValues()) {
			sf.line("'%s': %s,", s, idx++);
		}
		sf.blockClose();
	}

	private void init(ServiceAPI api) {
		this.api = api;
		this.restMeta = new RestResourceMeta(api.getApiPort());
		this.apiName = String.format("%s.%s", namespace, api.getName());
		sf = newSourceFile(api.getName(), "{", "}");

		collectDtos();
	}

	private void collectDtos() {
		for (ServiceMethod sm : api.getMethods()) {
			addDtoType(sm.getReturnType());
			for (Type type : sm.getArguments()) {
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
	
	private String getRequestType(RestMethodMeta rmm) {
		return rmm.isFormEndcoded() ? "FormRequest" : "JSONRequest";
	}

	private String buildParameters(RestMethodMeta rmm) {
		List<String> functionalParams = rmm.getFunctionalParameters();
		
		if (rmm.isJSONEndcoded()) {
			functionalParams.add("payload");
		}
		return Joiner.on(", ").join(functionalParams);
	}

	private String buildPayload(RestMethodMeta rmm) {
		if (rmm.isFormEndcoded()) {	
			Map<String, String> jsonObj = new HashMap<>();
			for (String formParam : rmm.getFormParameters()) {
				jsonObj.put(formParam, formParam);
			}
			return buildJSONString(jsonObj);
		}
		return "payload";
	}
	
	private String buildJSONString(Map<String, String> map) {
		StringBuilder sb = new StringBuilder().append("{");
		for (Map.Entry<String, String> e : map.entrySet()) {
			sb.append("'").append(e.getKey()).append("': ").append(e.getValue()).append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "}");
		return sb.toString();
	}
}
