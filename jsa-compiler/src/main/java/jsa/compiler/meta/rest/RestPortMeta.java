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

package jsa.compiler.meta.rest;

import java.util.List;

import javax.ws.rs.Path;

import jsa.compiler.meta.AbstractAPIPortMeta;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */

public class RestPortMeta extends AbstractAPIPortMeta {

	private @Getter @Setter List<RestMethodMeta> methods;

	public RestPortMeta(Class<?> apiPort) {
		super(apiPort);
	}

	public String getPath() {
		return getPath(apiPortClass.getAnnotation(Path.class));
	}

	private String getPath(Path path) {
		return path != null ? path.value() : "";
	}

}
