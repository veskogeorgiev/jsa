package jsa.compiler;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public abstract class AbstractSourceGenerator implements ClientServiceGenerator {

	protected List<SourceFile> sourceFiles = new LinkedList<>();
	
	protected SourceFile newSourceFile(String name, String blockOpen, String blockClose) {
		SourceFile sf = new SourceFile(name, blockOpen, blockClose);
		sourceFiles.add(sf);
		return sf;
	}
	
	protected List<SourceFile> finalizeSourceFiles() {
		for (SourceFile sourceFile : sourceFiles) {
			sourceFile.close();
		}
		return sourceFiles;
	}
}
