package jsa.test.port.api.v2;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.cxf.ExposeRest;
import jsa.test.api.APIException;
import jsa.test.api.v2.Item;
import jsa.test.api.v2.ItemsAPI;

@APIPort(api = ItemsAPI.class, context = "rest", type = APIPortType.DECORATOR)
@ExposeRest
public interface ItemsAPIRest extends ItemsAPI {

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<Item> availableItems();

    @Override
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @POST
    public void saveItem(
            @FormParam("name") String name,
            @FormParam("count") int count,
            @FormParam("desc") String description)
            throws APIException;
}
