package br.com.tep.mystuff.model;

public class Usuario {

	
	private int id;
	private String numero;
	private String email;
	
	public Usuario(int id, String numero, String emial) {
		super();
		this.id = id;
		this.numero = numero;
		this.email = emial;
	}
	
	
	public Usuario() {
		super();
	}
	
	public Usuario(int id) {
		this.id = id;
	}

	public int getId() {
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
