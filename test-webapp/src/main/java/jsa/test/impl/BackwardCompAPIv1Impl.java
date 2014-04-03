package jsa.test.impl;

import jsa.test.backward.BackwardCompAPIv1;

public class BackwardCompAPIv1Impl implements BackwardCompAPIv1 {

    @Override
    public void save(String name1, String name2) {
        System.out.println("v1:" + name1 + " " + name2);
    }

}
