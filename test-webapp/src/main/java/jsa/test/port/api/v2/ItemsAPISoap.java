package jsa.test.port.api.v2;

import javax.jws.WebMethod;
import javax.jws.WebService;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.cxf.ExposeSoap;
import jsa.test.api.v1.Item;
import jsa.test.api.v2.ItemsAPI;

@APIPort(api = ItemsAPI.class, context = "soap", type = APIPortType.DECORATOR)
@ExposeSoap
@WebService
public interface ItemsAPISoap extends ItemsAPI {
	
	@Override
    @WebMethod
	public void save(Item item);

	@Override
    @WebMethod
	public void saveBoth(Item item1, Item item2);
}
