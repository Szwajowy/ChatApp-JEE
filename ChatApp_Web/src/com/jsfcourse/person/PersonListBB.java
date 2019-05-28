package com.jsfcourse.person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.jsf.dao.PersonDAO;
import com.jsf.entities.Person;

@Named
@RequestScoped
public class PersonListBB {
	private static final String PAGE_PERSON_EDIT = "personEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String surname;
		
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@EJB
	PersonDAO personDAO;
		
	public List<Person> getFullList(){
		return personDAO.getFullList();
	}

	public List<Person> getList(){
		List<Person> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (surname != null && surname.length() > 0){
			searchParams.put("surname", surname);
		}
		
		//2. Get list
		list = personDAO.getList(searchParams);
		
		return list;
	}

	public String newPerson(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Person person = new Person();
		session.setAttribute("person", person);
		return PAGE_PERSON_EDIT;
	}

	public String editPerson(Person person){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("person", person);
		return PAGE_PERSON_EDIT;
	}

	public String deletePerson(Person person){
		personDAO.remove(person);
		return PAGE_STAY_AT_THE_SAME;
	}
}
