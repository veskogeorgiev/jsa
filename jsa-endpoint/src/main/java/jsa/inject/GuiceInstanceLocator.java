package jsa.inject;

import javax.inject.Inject;
import javax.inject.Singleton;

import jsa.endpoint.InstanceLocator;

import com.google.inject.Injector;

@Singleton
class GuiceInstanceLocator implements InstanceLocator {

    private @Inject Injector injector;

    @Override
    public <T> T locate(Class<T> type) {
        return injector.getInstance(type);
    }

}
