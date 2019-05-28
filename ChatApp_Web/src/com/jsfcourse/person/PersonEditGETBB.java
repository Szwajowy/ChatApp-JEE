package com.jsfcourse.person;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.jsf.dao.PersonDAO;
import com.jsf.entities.Person;

@Named
@ViewScoped
public class PersonEditGETBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PERSON_LIST = "personList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@EJB
	PersonDAO personDAO;
	private Person person = new Person();
	private Person loaded = null;

	public Person getPerson() {
		return person;
	}

	public void onLoad() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (!context.isPostback()) {
			if (!context.isValidationFailed() && person.getIdperson() != null) {
				loaded = personDAO.find(person.getIdperson());
			}
			if (loaded != null) {
				person = loaded;
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "B³êdne u¿ycie systemu", null));
				//if (!context.isPostback()) { // possible redirect
				//	context.getExternalContext().redirect("personList.xhtml");
				//	context.responseComplete();
				//}
			}
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "wyst¹pi³ b³¹d podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_PERSON_LIST;
	}
}
