package org.jagan.rsws.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.jagan.rsws.messenger.model.ErrorMessage;

public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException e) {
		ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), 404, "http://www.google.com");
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	}	
}
