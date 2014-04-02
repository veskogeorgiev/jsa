package jsa.test.impl;

import jsa.test.api.v1.DemoAPI;

public class DemoAPIImpl implements DemoAPI {

    @Override
    public String getMe() {
        return "Nikodim";
    }

}
