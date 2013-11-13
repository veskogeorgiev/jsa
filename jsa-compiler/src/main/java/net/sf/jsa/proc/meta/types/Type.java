/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jsa.proc.meta.types;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class Type {

	public static class TypeBool extends Type {
		
	}

	public static class TypeByte extends Type {
		
	}

	public static class TypeInteger extends Type {
		
	}

	public static class TypeDouble extends Type {
		
	}

	public static class TypeString extends Type {
		
	}

	public static class TypeBinary extends Type {
		
	}

	public static class ContainerType extends Type {
		
	}

	@Getter @Setter
	public static class TypeMap extends ContainerType {
		private Type keyType;
		private Type valueType;
	}

	@Getter @Setter
	public static class TypeList extends ContainerType {
		private Type innerType;
	}

	@Getter @Setter
	public static class TypeSet extends ContainerType {
		private Type innerType;
	}

}
