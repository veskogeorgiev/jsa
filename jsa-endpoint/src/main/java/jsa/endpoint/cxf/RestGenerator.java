package jsa.endpoint.cxf;

import java.util.Arrays;
import java.util.List;

import jsa.compiler.SourceCodeGenerator;
import jsa.compiler.SourceFile;
import jsa.compiler.SourceGenerationContext;
import jsa.compiler.meta.rest.RestMethodMeta;
import jsa.compiler.meta.rest.RestPortMeta;
import jsa.compiler.meta.types.ComplexType;
import jsa.compiler.meta.types.Field;
import jsa.compiler.meta.types.Type;

public class RestGenerator implements SourceCodeGenerator {

    private RestPortMeta port;
    private SourceGenerationContext context;

    RestGenerator(RestPortMeta restMeta, SourceGenerationContext context) {
        this.port = restMeta;
        this.context = context;
    }

    @Override
    public List<SourceFile> write() {
        SourceFile sf = new SourceFile(port.getApiMeta().getName(), "  ", "", "");

        for (RestMethodMeta m : port.getMethods()) {
            String path = String.format("%s%s/%s", context.getContext(), port.getFullContext(),
                    m.getDeclaredPath());
            sf.line("%s %s", m.getHttpMethod(), path);

            for (Type t : m.getPostBodyObjectTypes()) {
                printType(t, sf, "  ");
            }
        }
        return Arrays.asList(sf);
    }

    private void printType(Type bodyType, SourceFile sf, String indent) {
        if (bodyType instanceof ComplexType) {
            printType((ComplexType) bodyType, sf, indent);
        }
        else {
            sf.line("%s", bodyType);
        }
    }

    private void printType(ComplexType bodyType, SourceFile sf, String indent) {
        sf.line("{");
        List<Field> fields = bodyType.getFields();
        for (Field field : fields) {
            if (field.getType() instanceof ComplexType) {
                printType(bodyType, sf, indent + "  ");
            }
            else {
                sf.line("%s%s: '%s',", indent, field.getName(), field.getType());
            }
        }
        sf.line("}");
    }
}
