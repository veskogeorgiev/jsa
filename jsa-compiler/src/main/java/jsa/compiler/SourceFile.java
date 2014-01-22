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

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import lombok.Getter;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class SourceFile {

	private static final String INDENT = "\t";
	private static final String NL = "\n";

	private final PrintWriter writer;
	private final ByteArrayOutputStream out;
	private String indent = "";

	@Getter
	private final String name;
	private final String blockOpen;
	private final String blockClose;

	public SourceFile(String name, String blockOpen, String blockClose) {
		this.name = name;
		this.blockOpen = blockOpen;
		this.blockClose = blockClose;
		this.out = new ByteArrayOutputStream();
		writer = new PrintWriter(out);
	}

	public SourceFile append(String format, Object... args) {
		writer.format(indent + format, args);
		return this;
	}

	public SourceFile line(String format, Object... args) {
		writer.format(indent + format + NL, args);
		return this;
	}

	public SourceFile blockOpen(String format, Object... args) {
		line(format + " " + blockOpen, args);
		indent();
		return this;
	}

	public SourceFile blockClose() {
		deindent().line(blockClose);
		return this;
	}

	public SourceFile indent() {
		indent += INDENT;
		return this;
	}

	public SourceFile deindent() {
		indent = indent.substring(0, indent.length() - INDENT.length());
		return this;
	}

	public void close() {
		try {
			writer.close();
		}
		catch (Exception ex) {
			//
		}
	}

	public byte[] getContent() {
		close();
		return out.toByteArray();
	}

	@Override
	public String toString() {
		return new String(getContent());
	}
}
