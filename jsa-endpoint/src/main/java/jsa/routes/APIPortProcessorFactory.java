package jsa.routes;


public interface APIPortProcessorFactory {
	
	APIPortProcessor create (Class<?> apiPort, CustomProcessor customProcessor);
}
