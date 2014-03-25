package jsa.endpoint.cxf;

import jsa.compiler.SourceCodeGenerator;
import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.compiler.SourceGenerationContext;
import jsa.compiler.meta.rest.RestAPIPortInspector;
import jsa.compiler.meta.rest.RestPortMeta;

public class RestGeneratorFactory implements SourceCodeGeneratorFactory {

    private RestAPIPortInspector restInspector = RestAPIPortInspector.getInstance();

    @Override
    public SourceCodeGenerator create(Class<?> apiPort, SourceGenerationContext context) {
        RestPortMeta restMeta = restInspector.inspect(apiPort);
        return new RestGenerator(restMeta, context);
    }

}
