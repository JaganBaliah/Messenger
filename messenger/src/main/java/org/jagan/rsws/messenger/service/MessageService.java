package org.jagan.rsws.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.jagan.rsws.messenger.database.DatabaseClass;
import org.jagan.rsws.messenger.exception.DataNotFoundException;
import org.jagan.rsws.messenger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message(1, "Hello World !!!", "Jagan"));
		messages.put(2L, new Message(2, "Hello Jersey !!!", "Jagan"));
	}
	
	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());		
	}
	
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messagesForYear = new ArrayList<Message>();
		Calendar calendar = Calendar.getInstance();
		for (Message message : messages.values()) {
			calendar.setTime(message.getCreated());
			if(calendar.get(Calendar.YEAR) == year) messagesForYear.add(message);
		}
		return messagesForYear;		
	}
	
	
	public List<Message> getAllMessagesPaginated(int start, int size) {
		List<Message> list = new ArrayList<Message>(messages.values());
		if(start + size > list.size()) return new ArrayList<Message>();
		return list.subList(start, start + size);
	}
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if(message == null) throw new DataNotFoundException("Message with id " + id + " not found");
		return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if(message.getId() <= 0) return null;
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(long id) {
		return messages.remove(id);
	}
	
	public void removeMessage(long id) {
		messages.remove(id);
	}
}
