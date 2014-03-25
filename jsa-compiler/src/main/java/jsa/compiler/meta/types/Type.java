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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsa.compiler.meta.types;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class Type {

	public static class VoidType extends Type {

	}

	public static class TypeBool extends Type {

	}

	public static class TypeByte extends Type {

	}

	public static class TypeInteger extends Type {

	}

	public static class TypeLong extends Type {

	}

	public static class TypeDouble extends Type {

	}

	public static class TypeString extends Type {

	}

	public static class TypeBinary extends Type {

	}

	public static class ContainerType extends Type {

	}

	@Getter
	@Setter
	public static class TypeMap extends ContainerType {
		private Type keyType;
		private Type valueType;
	}

	@Getter
	@Setter
	public static class TypeList extends ContainerType {
		private Type innerType;
	}

	@Getter
	@Setter
	public static class TypeSet extends ContainerType {
		private Type innerType;
	}

	@Override
	public String toString() {
	   return getClass().getSimpleName();
	}
}
