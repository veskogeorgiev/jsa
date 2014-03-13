package jsa.test.port.api.v2;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.cxf.ExposeRest;
import jsa.test.api.v2.Item;
import jsa.test.api.v2.ItemsAPI;
import jsa.test.port.api.TestExcetionMapper;

import org.apache.cxf.jaxrs.model.wadl.Description;

@APIPort(api = ItemsAPI.class, context = "rest", type = APIPortType.DECORATOR)
@ExposeRest(exceptionMapper = TestExcetionMapper.class)
@Description("this is a super cool API for items")
@Produces(MediaType.APPLICATION_JSON)
public interface ItemsAPIRestV2 extends ItemsAPI {

	@Override
	@Path("save/{userId}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	void save(Item item, @PathParam("userId") String userId);

}
