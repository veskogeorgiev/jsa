package jsa.test.port.bc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import jsa.annotations.APIPort;
import jsa.annotations.APIPortType;
import jsa.endpoint.cxf.ExposeRest;
import jsa.test.backward.v1.BackwardCompAPI.Iface;

import org.apache.thrift.TException;

@ExposeRest
@APIPort(api = BackwardAPIv1.class, context = "rest", type = APIPortType.DECORATOR)
public interface BackwardAPIv1Rest extends Iface {

    @Override
    @GET
    @Path("/{n1}/{n2}")
    void save(@PathParam("n1") String name1, @PathParam("n2") String name2) throws TException;
}
