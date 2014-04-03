package jsa.test.inject;

import jsa.inject.APIModule;
import jsa.test.backward.BackwardCompAPIv1;
import jsa.test.backward.BackwardCompAPIv2;
import jsa.test.impl.BackwardCompAPIv1Impl;
import jsa.test.impl.BackwardCompAPIv2Impl;

public class BackwardAPIModule extends APIModule {

    public BackwardAPIModule() {
        super("/d", "jsa.test.port.bc");
    }

    @Override
    protected void configure() {
        super.configure();
        
        bind(BackwardCompAPIv1.class).to(BackwardCompAPIv1Impl.class);
        bind(BackwardCompAPIv2.class).to(BackwardCompAPIv2Impl.class);
    }
}
