package jsa.endpoint.sg;

import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.endpoint.cxf.RestGeneratorFactory;
import jsa.endpoint.spi.SourceGenerationPlugin;

public class RestDocPlugin implements SourceGenerationPlugin {

    @Override
    public String context() {
        return "_restapi";
    }

    @Override
    public Class<? extends SourceCodeGeneratorFactory> factory() {
        return RestGeneratorFactory.class;
    }
}
