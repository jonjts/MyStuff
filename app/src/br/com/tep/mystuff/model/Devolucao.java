package br.com.tep.mystuff.model;


public class Devolucao implements Comparable<Devolucao> {
	
	private long id;
	private String contato;
	private String objeto;
	private String dtEntrega;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public String getDtEntrega() {
		return dtEntrega;
	}

	public void setDtEntrega(String dtEntrega) {
		this.dtEntrega = dtEntrega;
	}

	@Override
	public String toString() {
		return objeto;
	}

	@Override
	public int compareTo(Devolucao another) {
		if(this.getId() == another.getId()){
			return 1;
		}
		return 0;
	}
}
