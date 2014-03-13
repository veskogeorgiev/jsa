package jsa.endpoint.thrift.annotations;

/**
 * 
 * @author <a href="mailto:vesko.m.georgiev@gmail.com">Vesko Georgiev<a>
 *
 */
public @interface DtoTypeMapping {

	Class<?> from();
	Class<?> to();
}
