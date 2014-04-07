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
import jsa.compiler.meta.types.TypeStringBuilder;

public class RestGenerator implements SourceCodeGenerator {

    private RestPortMeta port;
    private SourceGenerationContext context;
    private TypeStringBuilder tsb = new TypeStringBuilder();

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

            // print request
            List<Type> request = m.getPostBodyObjectTypes();
            if (!request.isEmpty()) {
                sf.blockOpen("Request: {");
                for (Type t : request) {
                    printType(t, sf);
                }
                sf.blockClose("}");
            }

            sf.blockOpen("Result: {");
            printType(m.getReturnType(), sf);
            sf.blockClose("}");
            sf.line("");
        }
        return Arrays.asList(sf);
    }

    private void printType(Type bodyType, SourceFile sf) {
        if (bodyType instanceof ComplexType) {
            printType((ComplexType) bodyType, sf);
        }
        else {
            sf.line("%s", tsb.toString(bodyType));
        }
    }

    private void printType(ComplexType bodyType, SourceFile sf) {
        List<Field> fields = bodyType.getFields();
        for (Field field : fields) {
            if (field.getType() instanceof ComplexType) {
                if (field.getType() == bodyType) {
                    sf.line("%s: %s,", field.getName(), bodyType.getName());
                }
                else {
                    ComplexType ct = (ComplexType) field.getType();
                    sf.blockOpen("%s: {", field.getName());
                    printType(ct, sf);
                    sf.blockClose("}");
                }
            }
            else {
                sf.line("%s: %s,", field.getName(), tsb.toString(field.getType()));
            }
        }
    }
}
