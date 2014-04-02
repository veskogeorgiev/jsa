package jsa.test.api.v1;

import jsa.annotations.API;
import jsa.annotations.API.Version;

@API(version = @Version(number = 1, tag = "v1"))
public interface DemoAPI {

    String getMe();
}
