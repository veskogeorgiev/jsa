/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsa.compiler.meta.refl;

import java.lang.annotation.Annotation;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
/**
 * Pair of an argument and its annotation
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 * @param <T>
 */
@AllArgsConstructor
public class AnnotatedParameter<T extends Annotation> {
	private @Getter final T annotation;
	private @Getter int index;
}
