package jsa.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import jsa.test.api.v1.Item;

import org.dozer.DozerBeanMapper;

@Singleton
public class ObjectMapper extends DozerBeanMapper {

	public <From, To> List<To> map(Iterable<From> from, Class<To> to) {
		List<To> res = new LinkedList<To>();
		for (From i : from) {
			res.add(map(i, to));
		}
		return res;
	}
	
	public static void main(String[] args) {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator v = vf.getValidator();
		Item i = new Item();
		Set<ConstraintViolation<Item>> res = v.validate(i);
		System.out.println(res);
   }
}
