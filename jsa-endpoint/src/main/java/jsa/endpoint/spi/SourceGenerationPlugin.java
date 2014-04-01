package jsa.endpoint.spi;

import jsa.compiler.SourceCodeGeneratorFactory;

public interface SourceGenerationPlugin {

    String context();
    
    Class<? extends SourceCodeGeneratorFactory> factory();
}
