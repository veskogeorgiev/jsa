package jsa.test.port.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class TestExcetionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception e) {
	    e.printStackTrace();
	    if (e instanceof WebApplicationException) {
	        return toResponse((Exception) e.getCause());
	    }
		return Response.ok(e.getClass().getName()).build();
	}
}
