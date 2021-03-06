package com.jsfcourse.person;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.jsf.dao.PersonDAO;
import com.jsf.entities.Person;

@Named
@ViewScoped
public class PersonEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PERSON_LIST = "personList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Person person = new Person();
	private Person loaded = null;

	public Person getPerson() {
		return person;
	}

	@EJB
	PersonDAO personDAO;
	
	public void onLoad() throws IOException {
	    FacesContext context = FacesContext.getCurrentInstance();

		// load person passed through session
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);

		loaded = (Person) session.getAttribute("person");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			person = loaded;
			session.removeAttribute("person");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B��dne u�ycie systemu", null));
			//if (!context.isPostback()) { //possible redirect 
			//	context.getExternalContext().redirect("personList.xhtml");
			//	context.responseComplete();
			//}
		}

	 }

	public String saveData() {
		// no Person object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (person.getIdperson() == null) {
				// new record
				personDAO.create(person);
			} else {
				// existing record
				personDAO.merge(person);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "wyst�pi� b��d podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_PERSON_LIST;
	}
}
