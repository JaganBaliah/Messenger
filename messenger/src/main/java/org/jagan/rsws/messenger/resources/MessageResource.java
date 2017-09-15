package org.jagan.rsws.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jagan.rsws.messenger.model.Message;
import org.jagan.rsws.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	//http://localhost:8080/messenger/webapi/messages
	MessageService messageService = new MessageService();
		
	@GET
	public List<Message> getMessages(
			@QueryParam("year") int year, 
			@QueryParam("start") int start, 
			@QueryParam("size") int size) {
		if(year > 0) {
			return messageService.getAllMessagesForYear(year);
		}
		if(start >= 0 && size > 0) {
			return messageService.getAllMessagesPaginated(start, size);
		}
		return messageService.getAllMessages();
	}
	
	/*
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
		if(filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear()+1);
		}
		if(filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	*/
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		//return messageService.addMessage(message);
		Message newMessage = messageService.addMessage(message);
		//return Response.status(Status.CREATED).entity(newMessage).build();
		String idStr = String.valueOf(newMessage.getId());
		URI location = uriInfo.getAbsolutePathBuilder().path(idStr).build();
		return Response.created(location).entity(newMessage).build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}

	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);
		String selfUrl = getUriForSelf(uriInfo, message);
		message.addLinks(selfUrl, "self");
		String profileUrl = getUriForProfile(uriInfo, message);
		message.addLinks(profileUrl, "profile");
		String commentsUrl = getUriForComment(uriInfo, message);
		message.addLinks(commentsUrl, "comments");
		return message;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String linkUrl = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Long.toString(message.getId()))
				.build()
				.toString();
		return linkUrl;
	}
	
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String linkUrl = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build()
				.toString();
		return linkUrl;
	}

	private String getUriForComment(UriInfo uriInfo, Message message) {
		String linkUrl = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())
				.build()
				.toString();
		return linkUrl;
	}

	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
