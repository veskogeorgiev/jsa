package jsa.test.port.api.v1;

import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.cxf.ExposeSoap;
import jsa.test.api.v1.Item;
import jsa.test.api.v1.ItemResult;
import jsa.test.api.v1.ItemsAPI;

@APIPort(api = ItemsAPI.class, context = "soap", type = APIPortType.DECORATOR)
@ExposeSoap
@WebService
public interface ItemsAPISoap extends ItemsAPI {

	@WebMethod
	@WebResult
	@Override
	List<Item> getItems();

	@Override
	@WebMethod
	ItemResult getItemResult();

	@Override
	@WebMethod
	Map<String, Item> getMapResult();

	@WebMethod
	@Override
	void save(Item item);
}
