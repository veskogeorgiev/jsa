package jsa.test.port.api.v1;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.cxf.ExposeSoap;
import jsa.test.api.APIException;
import jsa.test.api.v1.Item;
import jsa.test.api.v1.ItemsAPI;

@APIPort(api = ItemsAPI.class, context = "soap", type = APIPortType.DECORATOR)
@ExposeSoap
@WebService
public interface ItemsAPISoap extends ItemsAPI {

    @Override
    @WebMethod
    List<Item> availableItems();

    @Override
    @WebMethod
    void saveItem(String name, int count) throws APIException;
}
