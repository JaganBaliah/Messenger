package org.jagan.rsws.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
	
	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,
			@HeaderParam("header1") String headerParam) {
		
		return "Matrix param : " + matrixParam + " / " + "Header param : " + headerParam;
	}

	@GET
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders) {
		StringBuilder rtnVal = new StringBuilder();
		rtnVal.append("Path : " + uriInfo.getPath());
		rtnVal.append("\n");
		rtnVal.append("Abs Path : " + uriInfo.getAbsolutePath());
		rtnVal.append("\n");
		rtnVal.append("Path Params : " + uriInfo.getPathParameters());
		rtnVal.append("\n");
		rtnVal.append("Query Params : " + uriInfo.getQueryParameters());
		rtnVal.append("\n");
		rtnVal.append("Header Params : " + httpHeaders.getRequestHeaders());
		rtnVal.append("\n");
		rtnVal.append("Cookies : " + httpHeaders.getCookies());
		rtnVal.append("\n");
		rtnVal.append("Headers : " + httpHeaders.getHeaderString("header1"));
		return rtnVal.toString();
	}
}
