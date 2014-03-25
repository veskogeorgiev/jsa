package jsa.endpoint.spi;

import java.lang.annotation.Annotation;

import jsa.compiler.SourceCodeGeneratorFactory;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.apache.camel.RoutesBuilder;

public interface PortExposer {

    Class<? extends Annotation> annotation();

    Class<? extends RoutesBuilder> routBuilder();

    SourceGenerationConfig[] sourceGenerationConfig();

    @Data
    @AllArgsConstructor
    public static class SourceGenerationConfig {
        private final String context;
        private final Class<? extends SourceCodeGeneratorFactory> factory;
    }
}
