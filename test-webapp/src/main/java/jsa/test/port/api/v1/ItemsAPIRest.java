package jsa.test.port.api.v1;

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
@Description("this is a super cool API for items")
@Produces(MediaType.APPLICATION_JSON)
public interface ItemsAPIRest extends ItemsAPI {

	@Override
	@Path("list")
	@GET
	@Description("awesome getItems")
	@ElementClass(response = Item.class)
	List<Item> getItems();

	@Override
	@GET
	@Path("ir")
	@Description("awesome getItemResult")
	@ElementClass(response = ItemResult.class)
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

	@Override
	@Path("savel")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	void saveList(Request<List<Item>> item);
}
