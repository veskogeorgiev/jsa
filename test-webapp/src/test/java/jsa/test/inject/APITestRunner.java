package jsa.test.inject;

import org.junit.runners.model.InitializationError;

public class APITestRunner extends GuiceTestRunner {

	public APITestRunner(Class<?> classToRun) throws InitializationError {
	   super(classToRun, new APITestModule());
   }

}
