package jsa.endpoint.thrift;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.dozer.DozerBeanMapper;

public class DtoConverter {
	private TypeMapping typeMapping;
	private DozerBeanMapper mapper = new DozerBeanMapper();

	public DtoConverter(TypeMapping typeMapping) {
		this.typeMapping = typeMapping;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public Object convert(Object thriftObject, Class<?> apiArgType) {
		if (!shouldConvert(thriftObject)) {
			return thriftObject;
		}
		if (thriftObject instanceof Collection) {
			Collection res = null;
			if (thriftObject instanceof List) {
				res = new LinkedList();
			}
			else if (thriftObject instanceof Set) {
				res = new HashSet();
			}
			List thriftList = (List) thriftObject;

			for (Object tObj : thriftList) {
				Class<?> mappedType = typeMapping.getMapping(tObj.getClass());
				Object apiObj = convert(tObj, mappedType);
				res.add(apiObj);
			}
			return res;
		}
		else if (thriftObject instanceof Map) {
			Map res = new HashMap();

			Map thriftMap = (Map) thriftObject;

			Set<Map.Entry> set = thriftMap.entrySet();
			for (Entry tEntry : set) {
				Object keyObj = tEntry.getKey();
				Object valObj = tEntry.getValue();
				
				keyObj = convert(keyObj, typeMapping.getMapping(keyObj.getClass()));
				valObj = convert(valObj, typeMapping.getMapping(valObj.getClass()));
				
				res.put(keyObj, valObj);
			}
			return res;
		}
		else {
			return map(thriftObject, apiArgType);
		}
	}

	private boolean shouldConvert(Object obj) {
		return obj != null && obj.getClass() != String.class
				&& !ClassUtils.isPrimitiveOrWrapper(obj.getClass());
	}

	protected <T> T map(Object obj, Class<T> destType) {
		return mapper.map(obj, destType);
	}
}
