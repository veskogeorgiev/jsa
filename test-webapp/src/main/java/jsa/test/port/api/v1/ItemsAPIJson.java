package jsa.test.port.api.v1;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.cxf.ExposeRest;
import jsa.test.api.APIException;
import jsa.test.api.v1.Item;
import jsa.test.api.v1.ItemsAPI;

@APIPort(api = ItemsAPI.class, context = "rsjs", type = APIPortType.ADAPTER)
@ExposeRest
public class ItemsAPIJson {

    @Inject private ItemsAPI api;

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<Item> availableItems() {
        return api.availableItems();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void saveItem(Item item) throws APIException {
        api.saveItem(item.getName(), item.getCount());
    }

}
