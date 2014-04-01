package jsa.endpoint.sg;

import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.compiler.js.JSGeneratorFactory;
import jsa.endpoint.spi.SourceGenerationPlugin;

public class JSSourcePlugin implements SourceGenerationPlugin {

    @Override
    public String context() {
        return "_js";
    }

    @Override
    public Class<? extends SourceCodeGeneratorFactory> factory() {
        return JSGeneratorFactory.class;
    }

}
