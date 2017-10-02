package br.com.cleberson.salutation.ejb;

import javax.ejb.Stateless;

@Stateless(mappedName = "salutationWithJNDIOnClientBean")
public class SalutationWithJNDIOnClient {

	public String getFormalSalutation(String name) {
		return "Dear " + name;
	}
	
	public String getInformalSalutation(String name) {
		return "Hello " + name;
	}
	
}
