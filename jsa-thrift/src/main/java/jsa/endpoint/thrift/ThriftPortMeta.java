package jsa.endpoint.thrift;

import jsa.endpoint.processors.APIPortMeta;
import jsa.endpoint.thrift.annotations.DtoTypeMapping;
import jsa.endpoint.thrift.annotations.ExposeThrift;

import org.apache.thrift.TProcessor;

class ThriftPortMeta extends APIPortMeta {
	private ExposeThrift et;

	protected ThriftPortMeta(Class<?> apiPortClass) {
		super(apiPortClass);
		et = apiPortClass.getAnnotation(ExposeThrift.class);
	}

	public Class<?> getClientClass() {
		return find("$Client");
	}

	public Class<?> getIfcClass() {
		return find("$Iface");
	}

	@SuppressWarnings("unchecked")
	public <T extends TProcessor> Class<T> getProcessorClass() {
		return (Class<T>) find("$Processor");
	}

	public TypeMapping getTypeMapping() {
		TypeMapping res = new TypeMapping();
		for (DtoTypeMapping m : et.dtoMapping()) {
			res.addMapping(m.from(), m.to());
		}
		return res;
	}

	public boolean useDtoMapping() {
		return et.useDtoMapping();
	}

	private Class<?> find(String suffix) {
		for (Class<?> cls : et.module().getDeclaredClasses()) {
			if (cls.getName().endsWith(suffix)) {
				return cls;
			}
		}
		return null;
	}

	public static ThriftPortMeta create(Class<?> port) {
		return new ThriftPortMeta(port);
	}
}
