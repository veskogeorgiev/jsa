/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsa.compiler.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceVersion {

	private int number;
	private String tag;

	public String getSingleString() {
		return String.format("[%s:%s]", tag, number);
	}
}
