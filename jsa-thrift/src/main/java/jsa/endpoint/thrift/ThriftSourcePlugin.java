package jsa.endpoint.thrift;

import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.compiler.thrift.ThriftGeneratorFactory;
import jsa.endpoint.spi.SourceGenerationPlugin;

public class ThriftSourcePlugin implements SourceGenerationPlugin {

    @Override
    public String context() {
        return "_t";
    }

    @Override
    public Class<? extends SourceCodeGeneratorFactory> factory() {
        return ThriftGeneratorFactory.class;
    }
}
