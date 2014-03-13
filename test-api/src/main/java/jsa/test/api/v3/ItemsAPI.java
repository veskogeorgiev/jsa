package jsa.test.api.v3;

import jsa.annotations.API;
import jsa.annotations.API.Version;
import jsa.test.api.items.thrift.ItemsAPI.Iface;

@API(version = @Version(number = 3, tag = "v3"))
public interface ItemsAPI extends Iface {
}
