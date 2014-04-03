package jsa.test.inject;

import jsa.inject.APIModule;

public class BackwardAPIModule extends APIModule {

    public BackwardAPIModule() {
        super("/d", "jsa.test.port.bc");
    }

}
