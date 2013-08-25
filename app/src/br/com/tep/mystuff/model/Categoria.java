package br.com.tep.mystuff.model;

public class Categoria {

	private long id;
	private String nome;

	public Categoria(long id) {
		this.id= id;
	}
	
	public Categoria(long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	

	public Categoria() {
		super();
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
