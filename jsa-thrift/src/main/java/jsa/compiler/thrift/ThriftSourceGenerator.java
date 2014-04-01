/**
 * 
 */
package jsa.compiler.thrift;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
import jsa.compiler.meta.types.Type.TypeCollection;
import jsa.compiler.meta.types.Type.TypeDouble;
import jsa.compiler.meta.types.Type.TypeInteger;
import jsa.compiler.meta.types.Type.TypeMap;
import jsa.compiler.meta.types.Type.TypeString;
import jsa.compiler.meta.types.Type.VoidType;
import jsa.compiler.meta.types.TypeFactory;
import jsa.compiler.meta.types.TypeStringBuilder;
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

	private TypeStringBuilder typeStringBuilder = new TypeStringBuilder()
		.withTypeMapping(VoidType.class, "void")
		.withTypeMapping(TypeBool.class, "bool")
		.withTypeMapping(TypeByte.class, "byte")
		.withTypeMapping(TypeInteger.class, "i32")
		.withTypeMapping(TypeDouble.class, "double")
		.withTypeMapping(TypeString.class, "string")
		.withTypeMapping(TypeBinary.class, "binary");

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
		sf.line("namespace java %s.thrift", api.getPackage().getName());
		sf.line("namespace cocoa %s%s", context.getNamespace(), api.getVersion().getTag());
	}

	private void writeService() {
		sf.blockOpen("service %s", api.getName());
		for (ThriftMethodMeta mm : methods) {
			writeMethod(mm);
		}
		sf.blockClose();
	}

	private void writeMethod(ThriftMethodMeta mm) {
		sf.line("%s %s(%s)", typeStringBuilder.toString(mm.getReturnType()),
		        mm.getName(), buildParameters(mm));
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
		    String t = typeStringBuilder.toString(f.getType());
			sf.line("%s: %s %s", idx++, t, f.getName());
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
			for (Type type : mm.getParameters()) {
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
		else if (type instanceof TypeCollection) {
		    addDtoType(((TypeCollection) type).getInnerType());
		}
		else if (type instanceof TypeMap) {
		    addDtoType(((TypeMap) type).getKeyType());
		    addDtoType(((TypeMap) type).getValueType());
		}
	}

	private String buildParameters(ThriftMethodMeta mm) {
		List<String> functionalParams = new LinkedList<String>();
		int idx = 1;
		for (Type arg : mm.getParameters()) {
			functionalParams.add(idx + ": " + typeStringBuilder.toString(arg) + " " + "arg" + (idx++));
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
			builder.returnType(typeFactory.createType(method.getGenericReturnType()));

			for (Class<?> paramType : method.getParameterTypes()) {
				builder.argument(typeFactory.createType(paramType));
			}
			res.add(builder.build());
		}
		return res;
	}

}
