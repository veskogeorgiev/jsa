package jsa.test.api.v4;

import jsa.annotations.API;
import jsa.annotations.API.Version;
import jsa.test.api.v1.thrift.ItemsAPI.Iface;

@API(version = @Version(number = 4, tag = "v4"))
public interface ItemsAPI extends Iface {
    
}
