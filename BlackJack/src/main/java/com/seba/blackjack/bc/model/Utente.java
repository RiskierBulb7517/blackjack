package com.seba.blackjack.bc.model;

import java.io.Serializable;
import java.util.Objects;

public class Utente implements Serializable{

	
	private static final long serialVersionUID = 1711700553499031687L;
	
	
	private String username;
	private String email;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Utente [username=" + username + ", email=" + email + ", password=" + password + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, password, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utente other = (Utente) obj;
		return Objects.equals(email, other.email) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}
	
	

}
