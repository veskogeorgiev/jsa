package jsa.test.backward;

import jsa.annotations.API;
import jsa.annotations.API.Version;

@API(name = "backward", version = @Version(number = 2, tag = "v2"))
public interface BackwardCompAPIv2 {

    void save(String name1);
}
