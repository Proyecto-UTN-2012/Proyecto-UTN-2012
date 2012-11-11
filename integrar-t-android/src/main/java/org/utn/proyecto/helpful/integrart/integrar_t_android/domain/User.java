package org.utn.proyecto.helpful.integrart.integrar_t_android.domain;

public class User {
	private final static String GIFT_KEY = ".gift";
	
	private String userName;
	private String email;
	private String accountType;
	private String token;
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
		return this.userName.equals((u).getUserName()) && this.accountType.equals(u.getAccountType());
	}
	
	@Override
	public int hashCode(){
		return this.userName.hashCode() * this.accountType.hashCode() - 1;
	}
	
	@Override
	public String toString(){
		return this.userName + " - " + this.accountType;
	}

	public String getEmail() {
		return email;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}
	
	public void addGifts(int value){
		gifts+=value;
	}
	
	public int getGifts(){
		return gifts;
	}
	
	public String getGiftKey(){
		return this.userName + GIFT_KEY;
	}
}
