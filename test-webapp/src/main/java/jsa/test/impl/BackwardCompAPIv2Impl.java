package jsa.test.impl;

import jsa.test.backward.BackwardCompAPIv2;

public class BackwardCompAPIv2Impl implements BackwardCompAPIv2 {

    @Override
    public void save(String name1) {
        System.out.println("v2:" + name1);
    }

}
