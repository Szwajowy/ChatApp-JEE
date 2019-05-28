package com.jsfcourse.chat;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;
import javax.servlet.http.HttpSession;

import com.jsf.dao.ChatDAO;
import com.jsf.entities.Chat;
import com.jsf.entities.User;

@Named
@RequestScoped
public class ChatListBB {
	private static final String PAGE_PERSON_EDIT = "personEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	ChatDAO chatDAO;

	public List<Chat> getChats(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
		RemoteClient<User> remoteClient = new RemoteClient<User>();
		remoteClient = remoteClient.load(session);
		
		User loggedUser = remoteClient.getDetails();
		
		return chatDAO.getChats(loggedUser.getId());
	}

	public List<Chat> getFulList(){
		return chatDAO.getFullList();
	}

	public String newChat(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Chat chat = new Chat();
		session.setAttribute("chat", chat);
		return PAGE_PERSON_EDIT;
	}

	public String editChat(Chat chat){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("chat", chat);
		return PAGE_PERSON_EDIT;
	}

	public String deleteChat(Chat chat){
		chatDAO.remove(chat);
		return PAGE_STAY_AT_THE_SAME;
	}
}