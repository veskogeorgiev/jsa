package jsa.test.port.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class TestExcetionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		return Response.ok(exception.getClass().getName()).build();
	}
}
