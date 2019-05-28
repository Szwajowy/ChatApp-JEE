package com.jsf.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsf.entities.User;

//DAO - Data Access Object for User entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class UserDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(User user) {
		em.persist(user);
		//em.flush();
	}

	public User merge(User user) {
		return em.merge(user);
	}

	public void remove(User user) {
		em.remove(em.merge(user));
	}

	public User find(Object id) {
		return em.find(User.class, id);
	}
	
	public User getUser(String login, String password) {
		List<User> list = null;
		
		// 1. Build query string with parameters
		String select = "select user ";
		String from = "from User user ";
		String where = "where user.login = :login and user.password = :password ";
		String orderby = "order by user.surname asc, user.forename";
		
		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);
		
		// 3. Set configured parameters
		query.setParameter("login", login);
		query.setParameter("password", password);
		
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

	public List<User> getFullList() {
		List<User> list = null;

		Query query = em.createQuery("select user from User user");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
