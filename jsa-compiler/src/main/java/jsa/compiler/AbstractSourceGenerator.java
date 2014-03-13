/*******************************************************************************
 * Copyright (C) 2013 <a href="mailto:vesko.georgiev@icloud.com">Vesko Georgiev</a>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *******************************************************************************/
package jsa.compiler;

import java.util.LinkedList;
import java.util.List;

import jsa.compiler.meta.APIMeta;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public abstract class AbstractSourceGenerator implements SourceCodeGenerator {

	protected List<SourceFile> sourceFiles = new LinkedList<SourceFile>();
	protected final APIMeta api;
	protected final SourceGenerationContext context;

	public AbstractSourceGenerator(APIMeta api, SourceGenerationContext context) {
		this.api = api;
		this.context = context;
	}

	protected SourceFile newSourceFile(String name, String indentBase, String blockOpen, String blockClose) {
		SourceFile sf = new SourceFile(name, indentBase, blockOpen, blockClose);
		sourceFiles.add(sf);
		return sf;
	}

	protected List<SourceFile> finalizeSourceFiles() {
		for (SourceFile sourceFile : sourceFiles) {
			sourceFile.close();
		}
		return sourceFiles;
	}

	protected void writeHeader(SourceFile sf) {
		sf.line("////////////////////////////////////////////////////////////");
		sf.line("// %s version: %s:%s", api.getName(), api.getVersion().getTag(), api.getVersion()
		      .getNumber());
		sf.line("////////////////////////////////////////////////////////////");
		sf.line("");
	}

	/**
	 * @return
	 */
	public List<SourceFile> write() {
		return null;
	}
}
