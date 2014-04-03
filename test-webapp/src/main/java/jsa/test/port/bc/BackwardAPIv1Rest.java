package jsa.test.port.bc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.cxf.ExposeRest;
import jsa.test.backward.BackwardCompAPIv1;

@ExposeRest
@APIPort(api = BackwardCompAPIv1.class, context = "rest", type = APIPortType.DECORATOR)
public interface BackwardAPIv1Rest extends BackwardCompAPIv1 {

    @Override
    @GET
    @Path("/{n1}/{n2}")
    void save(@PathParam("n1") String name1, @PathParam("n2") String name2);
}
