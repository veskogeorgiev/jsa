package jsa.compiler.test;


public interface TestAPI {

    void operation1(Request<String> req);

    void operation2(RequestDD<String, Request<Integer>> req);
}
