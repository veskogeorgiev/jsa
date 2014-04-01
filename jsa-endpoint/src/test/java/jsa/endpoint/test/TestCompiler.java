package jsa.endpoint.test;

import java.util.List;

import jsa.compiler.SourceCodeGenerator;
import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.compiler.SourceFile;
import jsa.compiler.SourceGenerationContext;
import jsa.compiler.js.JSGeneratorFactory;
import jsa.endpoint.cxf.RestGeneratorFactory;
import jsa.endpoint.test.mock.api.ItemsAPIRest;

import org.junit.Ignore;
import org.junit.Test;

public class TestCompiler {

    private SourceCodeGeneratorFactory jsFactory = new JSGeneratorFactory();
    private SourceCodeGeneratorFactory restFactory = new RestGeneratorFactory();

    private SourceGenerationContext context = new SourceGenerationContext("/api", "idg");

    @Test
    @Ignore
    public void testJS() {
        SourceCodeGenerator gen = jsFactory.create(ItemsAPIRest.class, context);
        List<SourceFile> files = gen.write();
        StringBuilder sb = new StringBuilder();

        for (SourceFile sf : files) {
            sb.append(sf).append("\n");
        }
        System.out.println(sb);
    }

    @Test
    public void testRest() {
        SourceCodeGenerator gen = restFactory.create(ItemsAPIRest.class, context);
        List<SourceFile> files = gen.write();
        StringBuilder sb = new StringBuilder();

        for (SourceFile sf : files) {
            sb.append(sf).append("\n");
        }
        System.out.println(sb);
    }

}
