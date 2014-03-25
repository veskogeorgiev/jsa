package jsa.inject;

public interface InstanceLocator {

    <T> T locate(Class<T> type);
}
