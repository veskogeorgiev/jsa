package jsa.test.backward;

import jsa.annotations.API;
import jsa.annotations.API.Version;

@API(name = "backward", version = @Version(number = 1, tag = "v1"))
public interface BackwardCompAPIv1 {

    void save(String name1, String name2);
}
