package com.jsf.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsf.entities.Chat;
import com.jsf.entities.Message;
import com.jsf.entities.User;

//DAO - Data Access Object for Message entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class MessageDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Message message) {
		em.persist(message);
		//em.flush();
	}

	public Message merge(Message message) {
		return em.merge(message);
	}

	public void remove(Message message) {
		em.remove(em.merge(message));
	}

	public Message find(Object id) {
		return em.find(Message.class, id);
	}
	
	public List<Message> getChatMessages(Integer chatID) {
		List<Message> list = null;
		
		Chat chat = new Chat();
		chat.setId(chatID);
		
		// 1. Build query string with parameters
		String select = "select message ";
		String from = "from Message message ";
		String join = "join fetch message.user user ";
		String where = "where message.chat = :chat ";
		String orderby = "order by message.sendedAt asc";
		
		// 2. Create query object
		Query query = em.createQuery(select + from + join + where + orderby);
		
		// 3. Set configured parameters
		query.setParameter("chat", chat);
		
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

	public List<Message> getFullList() {
		List<Message> list = null;

		Query query = em.createQuery("select message from Message message");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
