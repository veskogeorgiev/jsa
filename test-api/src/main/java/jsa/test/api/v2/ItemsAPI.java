package jsa.test.api.v2;

import jsa.annotations.API;
import jsa.annotations.API.Version;

@API(version = @Version(number = 2, tag = "v2"))
public interface ItemsAPI {

	void save(Item item, String userId);
}
