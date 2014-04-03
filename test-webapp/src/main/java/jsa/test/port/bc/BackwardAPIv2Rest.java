package jsa.test.port.bc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.cxf.ExposeRest;
import jsa.test.backward.BackwardCompAPIv2;

@APIPort(api = BackwardCompAPIv2.class, context = "rest", type = APIPortType.DECORATOR)
@ExposeRest
public interface BackwardAPIv2Rest extends BackwardCompAPIv2 {

    @Override
    @GET
    @Path("/{n1}")
    void save(@PathParam("n1") String name1);
}
