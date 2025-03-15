package com.seba.blackjack.bc.model;

import java.io.Serializable;
import java.util.Objects;

public class Carta implements Serializable{
	
	private static final long serialVersionUID = -5856556406456189660L;
	private long id;
	private long valore;
	private String seme;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getValore() {
		return valore;
	}
	public void setValore(long valore) {
		this.valore = valore;
	}
	public String getSeme() {
		return seme;
	}
	public void setSeme(String seme) {
		this.seme = seme;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, seme, valore);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carta other = (Carta) obj;
		return id == other.id && Objects.equals(seme, other.seme) && valore == other.valore;
	}
	@Override
	public String toString() {
		return "Carta [id=" + id + ", valore=" + valore + ", seme=" + seme + "]";
	}
	
}
