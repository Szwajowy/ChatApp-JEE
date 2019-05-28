package com.jsfcourse.message;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;
import javax.servlet.http.HttpSession;

import com.jsf.dao.ChatDAO;
import com.jsf.dao.MessageDAO;
import com.jsf.entities.Chat;
import com.jsf.entities.Message;
import com.jsf.entities.User;

@Named
@SessionScoped
public class MessageListBB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PAGE_PERSON_EDIT = "personEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	private Integer chatID;
	private String text;
	private List<Message> messages;
	
	public Integer getChatID() {
		return chatID;
	}

	public void setChatID(Integer chatID) {
		this.chatID = chatID;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	@EJB
	MessageDAO messageDAO;
	@EJB
	ChatDAO chatDAO;

	public void getChatMessages(Integer chatID) {
		this.setChatID(chatID);
		this.setMessages(messageDAO.getChatMessages(chatID));
	}

	public List<Message> getFulList(){
		return messageDAO.getFullList();
	}

	public void sendMessage() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
		RemoteClient<User> remoteClient = new RemoteClient<User>();
		remoteClient = remoteClient.load(session);
		
		User loggedUser = remoteClient.getDetails();
		
		Chat chat = new Chat();
		chat = chatDAO.find(this.chatID);
		
		Message message = new Message();
		message.setUser(loggedUser);
		message.setChat(chat);
		message.setText(this.text);
		message.setSendedAt(timestamp);
		
		messageDAO.create(message);
		
		this.text = "";
		this.setMessages(messageDAO.getChatMessages(this.chatID));
	}

	public String editMessage(Message message){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("message", message);
		return PAGE_PERSON_EDIT;
	}

	public String deleteMessage(Message message){
		messageDAO.remove(message);
		return PAGE_STAY_AT_THE_SAME;
	}
}