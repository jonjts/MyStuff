package br.com.tep.mystuff.model;

import java.io.Serializable;

public class Usuario implements Serializable {

	
	private long id;
	private String numero;
	private String email;
	
	public Usuario(long id, String numero, String emial) {
		super();
		this.id = id;
		this.numero = numero;
		this.email = emial;
	}
	
	
	public Usuario() {
		super();
	}
	
	public Usuario(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	};
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return numero;
	}
	
}
