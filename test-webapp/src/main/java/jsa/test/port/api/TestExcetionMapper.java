package jsa.test.port.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestExcetionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
	    log.info("Mapping excepion");
		return Response.ok("Ooooooooops...").build();
	}
}
