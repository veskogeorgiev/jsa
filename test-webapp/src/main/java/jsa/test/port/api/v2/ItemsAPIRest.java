package jsa.test.port.api.v2;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.annotations.Router;
import jsa.endpoint.cxf.ExposeRest;
import jsa.test.api.v1.Item;
import jsa.test.api.v2.ItemsAPI;

import org.apache.cxf.jaxrs.model.wadl.Description;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@APIPort(api = ItemsAPI.class, context = "rest", type = APIPortType.DECORATOR)
@ExposeRest(providers = {JacksonJsonProvider.class})
@Router(JSONRouteBuilder.class)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Description("this is a super cool API for items")
public interface ItemsAPIRest extends ItemsAPI {

    @Override
    @Path("saveBoth")
    @POST
    void saveBoth(Item item1, Item item2);

    @Override
    @Path("save")
    @POST
    void save(Item item);

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void create(@FormParam("name") String name, @FormParam("age") int age);

}
