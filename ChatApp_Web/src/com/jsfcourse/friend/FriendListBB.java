package com.jsfcourse.friend;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;
import javax.servlet.http.HttpSession;

import com.jsf.dao.FriendDAO;
import com.jsf.entities.Friend;
import com.jsf.entities.User;

@Named
@RequestScoped
public class FriendListBB {
	private static final String PAGE_PERSON_EDIT = "personEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	FriendDAO friendDAO;

	public List<Friend> getFriends(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
		RemoteClient<User> remoteClient = new RemoteClient<User>();
		remoteClient = remoteClient.load(session);
		
		User loggedUser = remoteClient.getDetails();
		
		return friendDAO.getFriends(loggedUser.getId());
	}

	public List<Friend> getFulList(){
		return friendDAO.getFullList();
	}

	public String newFriend(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Friend friend = new Friend();
		session.setAttribute("friend", friend);
		return PAGE_PERSON_EDIT;
	}

	public String editFriend(Friend friend){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("friend", friend);
		return PAGE_PERSON_EDIT;
	}

	public String deleteFriend(Friend friend){
		friendDAO.remove(friend);
		return PAGE_STAY_AT_THE_SAME;
	}
}