package jsa.endpoint.test.mock.api;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;

import org.apache.cxf.jaxrs.model.wadl.Description;

@APIPort(api = ItemsAPI.class, context = "rest", type = APIPortType.DECORATOR)
@Description("this is a super cool API for items")
@Produces(MediaType.APPLICATION_JSON)
public interface ItemsAPIRest extends ItemsAPI {

    @Override
    @Path("list")
    @GET
    @Description("awesome getItems")
    List<Item> getItems(Request<Item> req);

    @Override
    @GET
    @Path("ir")
    @Description("awesome getItemResult")
    ItemResult getItemResult();

    @Override
    @GET
    @Path("mr")
    Map<String, Item> getMapResult();

    @Override
    @Path("save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void save(Item item);

}
