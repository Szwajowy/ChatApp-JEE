package com.jsf.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsf.entities.Chat;
import com.jsf.entities.Friend;
import com.jsf.entities.User;

//DAO - Data Access Object for Chat entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class ChatDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Chat chat) {
		em.persist(chat);
		//em.flush();
	}

	public Chat merge(Chat chat) {
		return em.merge(chat);
	}

	public void remove(Chat chat) {
		em.remove(em.merge(chat));
	}

	public Chat find(Object id) {
		return em.find(Chat.class, id);
	}
	
	public Chat getChat(Integer chatID) {
		List<Chat> list = null;
		
		// 1. Build query string with parameters
		String select = "select chat ";
		String from = "from Chat chat ";
		String where = "where chat.id = :chatID ";
		
		// 2. Create query object
		Query query = em.createQuery(select + from + where);
		
		// 3. Set configured parameters
		query.setParameter("chatID", chatID);
		
		// 4. Execute query and retrieve list of User objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(list.isEmpty()) {
			return null;
		}
			
		return list.get(0);
	}
	
	public List<Chat> getChats(Integer userID) {
		List<Chat> list = null;
		
		User user = new User();
		user.setId(userID);
		
		// 1. Build query string with parameters
		String select = "select chat ";
		String from = "from Chat chat ";
//		String join = "join fetch chat.userByFriendId friendUser ";
		String where = "where :user member of chat.users";
		String orderby = "";
		
		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);
		
		// 3. Set configured parameters
		query.setParameter("user", user);
		
		// 4. Execute query and retrieve list of User objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(list.isEmpty()) {
			return null;
		}
			
		return list;
	}
	
	public List<Chat> getFullList() {
		List<Chat> list = null;

		Query query = em.createQuery("select chat from Chat chat");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
