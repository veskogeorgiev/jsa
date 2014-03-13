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
package jsa.compiler.meta;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.compiler.MetaDataException;
import lombok.Data;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Data
public abstract class AbstractAPIPortMeta {

	protected Class<?> apiPortClass;
	protected APIMeta apiMeta;

	protected AbstractAPIPortMeta(Class<?> apiPortClass) {
		this.apiPortClass = apiPortClass;
		APIPort meta = apiPortClass.getAnnotation(APIPort.class);
		if (meta == null) {
			throw new MetaDataException("%s is used as an API port but is not annotated with @APIPort", apiPortClass.getName());
		}
		this.apiMeta = APIMeta.create(getAPIClass());
	}

	public String getFullContext() {
		// TODO
		return apiMeta.getUrl() + "/" + getContext();
	}

	public String getFullClassName() {
		return apiPortClass.getName();
	}

	public String getSimpleClassName() {
		return apiPortClass.getSimpleName();
	}

	public Package getPackage() {
		return apiPortClass.getPackage();
	}

	public String getContext() {
		return apiPortClass.getAnnotation(APIPort.class).context();
	}

	public Class<?> getAPIClass() {
		return apiPortClass.getAnnotation(APIPort.class).api();
	}

	public APIPortType getPortType() {
		return apiPortClass.getAnnotation(APIPort.class).type();
	}

}
