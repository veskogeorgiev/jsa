package jsa.endpoint;

public interface InstanceLocator {

    <T> T locate(Class<T> type);
}
