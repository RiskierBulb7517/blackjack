package com.seba.blackjack.bc.model;

import java.io.Serializable;
import java.util.Objects;

public class Immagine implements Serializable{

	private static final long serialVersionUID = 82287245082699313L;

	private long id;
	private long cartaid;
	private String url;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCartaid() {
		return cartaid;
	}
	public void setCartaid(long cartaid) {
		this.cartaid = cartaid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cartaid, id, url);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Immagine other = (Immagine) obj;
		return cartaid == other.cartaid && id == other.id && Objects.equals(url, other.url);
	}
	@Override
	public String toString() {
		return "Immagine [id=" + id + ", cartaid=" + cartaid + ", url=" + url + "]";
	}
	
	
	
}
