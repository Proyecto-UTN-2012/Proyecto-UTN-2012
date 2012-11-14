package org.utn.proyecto.helpful.integrart.web.metrics;

import org.codehaus.jackson.annotate.JsonProperty;
import org.utn.proyecto.helpful.integrart.core.percistence.Entity;

@Entity("users")
public class User {
	@JsonProperty("_id")
	private String id;
	private String userName;
	private String email;
	private String accountType;
	transient private String token;
	private String device;
	private int gifts;
	
	public User(){}
	
	public User(String userName, String email, String accountType){
		this.userName = userName;
		this.email = email;
		this.accountType = accountType;
	}

	public User(String userName, String email, String accountType, String token){
		this.userName = userName;
		this.email = email;
		this.token = token;
		this.accountType = accountType;
	}

	public String getUserName() {
		return userName;
	}

	public String getToken() {
		return token;
	}

	public String getAccountType() {
		return accountType;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof User)) return false;
		User u = (User)o;
		return this.userName.equals((u).getUserName()) 
				&& this.accountType.equals(u.getAccountType())
				&& this.device.equals(u.device);
	}
	
	@Override
	public int hashCode(){
		return this.userName.hashCode() * this.accountType.hashCode() * this.device.hashCode() - 1;
	}
	
	@Override
	public String toString(){
		return this.userName + " - " + this.accountType;
	}

	public String getEmail() {
		return email;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public int getGifts() {
		return gifts;
	}

	public void setGifts(int gifts) {
		this.gifts = gifts;
	}
}
