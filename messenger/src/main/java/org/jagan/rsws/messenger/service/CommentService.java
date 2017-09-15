package org.jagan.rsws.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jagan.rsws.messenger.database.DatabaseClass;
import org.jagan.rsws.messenger.model.Comment;
import org.jagan.rsws.messenger.model.ErrorMessage;
import org.jagan.rsws.messenger.model.Message;

public class CommentService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId) {
		Message message = messages.get(messageId);
		Map<Long, Comment> comments = message.getComments();
		return new ArrayList<>(comments.values());				
	}

	public Comment getComment(long messageId, long commentId) {
		ErrorMessage errorMessage = new ErrorMessage("Data not found", 404, "http://www.google.com");
		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();

		Message message = messages.get(messageId);
		if(message == null) throw new WebApplicationException(response);
		
		Map<Long, Comment> comments = message.getComments();
		
		Comment comment = comments.get(commentId);
		if(comment == null) throw new NotFoundException(response);
		
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Message message = messages.get(messageId);
		Map<Long, Comment> comments = message.getComments();
		comment.setId(comments.size() + 1);		
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		Message message = messages.get(messageId);
		Map<Long, Comment> comments = message.getComments();
		if(comment.getId() <= 0) return null;
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public void removeComment(long messageId, long commentId) {
		Message message = messages.get(messageId);
		Map<Long, Comment> comments = message.getComments();
		comments.remove(commentId);
	}
}
