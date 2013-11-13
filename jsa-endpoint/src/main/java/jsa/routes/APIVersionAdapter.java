/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsa.routes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 * @param <FromVersion>
 * @param <ToVersion>
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class APIVersionAdapter<FromVersion, ToVersion> /* implements FromVersion */ {

	protected final ToVersion adaptee;
	
}
