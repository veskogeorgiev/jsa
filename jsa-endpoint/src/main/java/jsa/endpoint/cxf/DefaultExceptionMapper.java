package jsa.endpoint.cxf;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        return Response.serverError()
                .entity(exception.getClass().getSimpleName())
                .build();
    }

}
