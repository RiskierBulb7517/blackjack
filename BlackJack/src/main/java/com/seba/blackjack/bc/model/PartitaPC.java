package com.seba.blackjack.bc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PartitaPC implements Serializable{
	
	private static final long serialVersionUID = 8474392414367951501L;
	
	private long id;
	private String u_username;
	private String stato;
	private long puntibanco;
	private long puntiutente;
	private Date dataInserimento;
	private Date dataModifica;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getU_username() {
		return u_username;
	}
	public void setU_username(String u_username) {
		this.u_username = u_username;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public long getPuntibanco() {
		return puntibanco;
	}
	public void setPuntibanco(long puntibanco) {
		this.puntibanco = puntibanco;
	}
	public long getPuntiutente() {
		return puntiutente;
	}
	public void setPuntiutente(long puntiutente) {
		this.puntiutente = puntiutente;
	}
	
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public Date getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, puntibanco, puntiutente, stato, u_username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartitaPC other = (PartitaPC) obj;
		return id == other.id && puntibanco == other.puntibanco && puntiutente == other.puntiutente
				&& Objects.equals(stato, other.stato) && Objects.equals(u_username, other.u_username);
	}
	@Override
	public String toString() {
		return "PartitaPC [id=" + id + ", u_username=" + u_username + ", stato=" + stato + ", puntibanco=" + puntibanco
				+ ", puntiutente=" + puntiutente + ", dataInserimento=" + dataInserimento + ", dataModifica="
				+ dataModifica + "]";
	}
	
	
}
