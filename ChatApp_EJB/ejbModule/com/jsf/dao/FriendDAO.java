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
import com.jsf.entities.Message;
import com.jsf.entities.User;

//DAO - Data Access Object for Friend entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class FriendDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Friend friend) {
		em.persist(friend);
		//em.flush();
	}

	public Friend merge(Friend friend) {
		return em.merge(friend);
	}

	public void remove(Friend friend) {
		em.remove(em.merge(friend));
	}

	public Friend find(Object id) {
		return em.find(Friend.class, id);
	}
	
	public List<Friend> getFriends(Integer userID) {
		List<Friend> list = null;
		
		User user = new User();
		user.setId(userID);
		
		// 1. Build query string with parameters
		String select = "select friend ";
		String from = "from Friend friend ";
		String join = "join fetch friend.userByFriendId friendUser ";
		String where = "where friend.userByUserId = :user or friend.userByFriendId = :user ";
		String orderby = "";
		
		// 2. Create query object
		Query query = em.createQuery(select + from + join + where + orderby);
		
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
	
	public List<Friend> getFullList() {
		List<Friend> list = null;

		Query query = em.createQuery("select friend from Friend friend");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
