package jsa.test.port.api.v1;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.cxf.ExposeRest;
import jsa.test.api.v1.Item;
import jsa.test.api.v1.ItemResult;
import jsa.test.api.v1.ItemsAPI;
import jsa.test.api.v1.Request;
import jsa.test.port.api.TestExcetionMapper;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.ElementClass;

@APIPort(api = ItemsAPI.class, context = "rest", type = APIPortType.DECORATOR)
@ExposeRest(exceptionMapper = TestExcetionMapper.class)
@Description("This is a super cool API for items")
@Produces(MediaType.APPLICATION_JSON)
public interface ItemsAPIRest extends ItemsAPI {

    @Override
    @Path("list")
    @GET
    @Description("method:getItems")
    @ElementClass(response = Item.class)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    List<Item> getItems();

    @Override
    @GET
    @Path("ir")
    @Description("method:getItemResult")
    ItemResult getItemResult();

    @Override
    @GET
    @Path("mr")
    @Description("method:getMapResult")
    Map<String, Item> getMapResult();

    @Override
    @Path("save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Description("method:save")
    void save(@Description("this is the new item") Item item);

    @Override
    @Path("savel")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Description("method:saveList")
    @ElementClass(request = Request.class)
    void saveList(Request<List<Item>> item);

    @Path("demo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Description("method:demo")
    @ElementClass(response = Item.class)
    Item demoMethod(
            @QueryParam("name") @Description("the name") String name, 
            @QueryParam("age") @Description("the age") Integer age);

}
