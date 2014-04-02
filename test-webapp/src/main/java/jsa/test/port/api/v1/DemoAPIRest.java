package jsa.test.port.api.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.cxf.ExposeRest;
import jsa.test.api.v1.DemoAPI;

@APIPort(api = DemoAPI.class, context = "rest", type = APIPortType.DECORATOR)
@ExposeRest
public interface DemoAPIRest extends DemoAPI {

    @Override
    @GET
    @Path("gm")
    String getMe();
}
