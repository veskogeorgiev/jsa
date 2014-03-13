package jsa.endpoint.test;

import java.util.List;

import jsa.compiler.SourceCodeGenerator;
import jsa.compiler.SourceCodeGeneratorFactory;
import jsa.compiler.SourceFile;
import jsa.compiler.SourceGenerationContext;
import jsa.compiler.js.JSGeneratorFactory;
import jsa.endpoint.test.mock.api.ItemsAPIRest;

import org.junit.Test;

public class TestCompiler {

	private SourceCodeGeneratorFactory factory = new JSGeneratorFactory();

	@Test
	public void compilation() {
		SourceCodeGenerator gen = factory.create(ItemsAPIRest.class,
		      new SourceGenerationContext("/api", "idg"));
		List<SourceFile> files = gen.write();
		StringBuilder sb = new StringBuilder();

		for (SourceFile sf : files) {
			sb.append(sf).append("\n");
		}
		System.out.println(sb);
	}

}
