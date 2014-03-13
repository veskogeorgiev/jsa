/**
 * 
 */
package jsa.compiler.thrift;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jsa.compiler.AbstractSourceGenerator;
import jsa.compiler.SourceFile;
import jsa.compiler.SourceGenerationContext;
import jsa.compiler.meta.APIMeta;
import jsa.compiler.meta.types.ComplexType;
import jsa.compiler.meta.types.CustomType;
import jsa.compiler.meta.types.EnumType;
import jsa.compiler.meta.types.Field;
import jsa.compiler.meta.types.Type;
import jsa.compiler.meta.types.Type.TypeBinary;
import jsa.compiler.meta.types.Type.TypeBool;
import jsa.compiler.meta.types.Type.TypeByte;
import jsa.compiler.meta.types.Type.TypeDouble;
import jsa.compiler.meta.types.Type.TypeInteger;
import jsa.compiler.meta.types.Type.TypeList;
import jsa.compiler.meta.types.Type.TypeMap;
import jsa.compiler.meta.types.Type.TypeSet;
import jsa.compiler.meta.types.Type.TypeString;
import jsa.compiler.meta.types.Type.VoidType;
import jsa.compiler.meta.types.TypeFactory;
import jsa.compiler.thrift.ThriftMethodMeta.Builder;

import org.reflections.ReflectionUtils;

import com.google.common.base.Joiner;

/**
 * 
 * namespace java jsa.test.api.items.thrift namespace cocoa IDG
 * 
 * struct Item { 1: string id 2: string name 3: i32 count }
 * 
 * struct ItemResult { 1: string name 2: list<Item> items }
 * 
 * service ItemsAPI { list<Item> getItems()
 * 
 * ItemResult getItemResult()
 * 
 * map<string, Item> getMapResult()
 * 
 * void save(Item item) }
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class ThriftSourceGenerator extends AbstractSourceGenerator {

	private SourceFile sf;
	private List<ThriftMethodMeta> methods;
	private Map<Class<? extends Type>, String> typeMapping = new HashMap<Class<? extends Type>, String>();
	{
		typeMapping.put(VoidType.class, "void");
		typeMapping.put(TypeBool.class, "bool");
		typeMapping.put(TypeByte.class, "byte");
		typeMapping.put(TypeInteger.class, "i32");
		typeMapping.put(TypeDouble.class, "double");
		typeMapping.put(TypeString.class, "string");
		typeMapping.put(TypeBinary.class, "binary");
		typeMapping.put(TypeMap.class, "map");
		typeMapping.put(TypeList.class, "list");
		typeMapping.put(TypeSet.class, "sets");
	}
	private final Set<CustomType> dtos = new HashSet<CustomType>();

	ThriftSourceGenerator(APIMeta port, SourceGenerationContext context) {
		super(port, context);
		methods = extractMethods(api.getApiClass());

		collectDtos();
	}

	@Override
	public List<SourceFile> write() {
		sf = newSourceFile(api.getName(), "  ", "{", "}");

		writeHeader(sf);
		writeNamespace();
		sf.newLine();
		writeCustomTypes();
		writeService();

		return finalizeSourceFiles();
	}

	private void writeNamespace() {
		sf.line("namespace java %s", api.getPackage());
		sf.line("namespace cocoa IDG");
	}

	private void writeService() {
		sf.blockOpen("service %s", api.getName());
		for (ThriftMethodMeta mm : methods) {
			writeMethod(mm);
		}
		sf.blockClose();
	}

	private void writeMethod(ThriftMethodMeta mm) {
		sf.line("%s %s(%s)", type(mm.getReturnType()), mm.getName(), buildParameters(mm));
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
		sf.blockOpen("struct %s", type.getName());
		int idx = 1;
		for (Field f : type.getFields()) {
			sf.line("%s: %s %s", idx++, type(f.getType()), f.getName());
		}
		sf.blockClose();
		sf.newLine();
	}

	private void writeEnumType(EnumType type) {
		// sf.blockOpen("%s.%s =", context.getNamespace(), type.getName());
		//
		// int idx = 0;
		// for (String s : type.getValues()) {
		// sf.line("'%s': %s,", s, idx++);
		// }
		// sf.blockClose();
	}

	private void collectDtos() {
		for (ThriftMethodMeta mm : methods) {
			addDtoType(mm.getReturnType());
			for (Type type : mm.getArguments()) {
				addDtoType(type);
			}
		}
	}

	private String type(Type type) {
		for (Entry<Class<? extends Type>, String> e : typeMapping.entrySet()) {
			if (e.getKey().isAssignableFrom(type.getClass())) {
				return e.getValue();
			}
		}
		if (type instanceof ComplexType) {
			return ((ComplexType) type).getName();
		}
		throw new RuntimeException("Cannot determine type: " + type);
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

	private String buildParameters(ThriftMethodMeta mm) {
		List<String> functionalParams = new LinkedList<String>();
		int idx = 1;
		for (Type arg : mm.getArguments()) {
			functionalParams.add(type(arg) + " " + "arg" + (idx++));
		}
		return Joiner.on(", ").join(functionalParams);
	}

	private List<ThriftMethodMeta> extractMethods(Class<?> apiCls) {
		List<ThriftMethodMeta> res = new LinkedList<ThriftMethodMeta>();

		TypeFactory typeFactory = new TypeFactory(apiCls.getPackage());

		@SuppressWarnings("unchecked")
		Set<Method> methods = ReflectionUtils.getAllMethods(apiCls,
		      ReflectionUtils.withModifier(Modifier.PUBLIC));

		for (Method method : methods) {
			Builder builder = ThriftMethodMeta.builder();
			builder.method(method);
			builder.returnType(typeFactory.createType(method.getReturnType()));

			for (Class<?> paramType : method.getParameterTypes()) {
				builder.argument(typeFactory.createType(paramType));
			}
			res.add(builder.build());
		}
		return res;
	}

}
