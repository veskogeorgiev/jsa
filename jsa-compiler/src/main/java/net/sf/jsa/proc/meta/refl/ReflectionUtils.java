/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jsa.proc.meta.refl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class ReflectionUtils {

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> List<AnnotatedParameter<T>> getArgumentsWithAnnotation(Method method, Class<T> annotationClass) {
		List<AnnotatedParameter<T>> ret = new LinkedList<>();

		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		int argIdx = 0;

		for (Annotation[] argAnnotations : parameterAnnotations) {
			for (Annotation an : argAnnotations) {
				if (an.annotationType().isAssignableFrom(annotationClass)) {
					ret.add(new AnnotatedParameter<>((T) an, argIdx));
				}
			}
			argIdx++;
		}
		return ret;
	}

}
