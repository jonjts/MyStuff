package br.com.tep.mystuff.model;

import java.util.Date;

import android.R.bool;

public class Emprestimo implements Comparable<Emprestimo>{

	private long id;
	private String imagem;
	private String objeto;
	private String comentario;
	private long categoria_id;
	private String contato;
	private Date dtEntrega;
	private int notificar;
	private long usu_id;

	public Emprestimo() {
		super();
	}

	public Emprestimo(long id,String imagem, String objeto, String comentario,
			long categoria_id, String contato, Date dtEntrega, int notificar, long usu_id) {
		super();
		this.id = id;
		this.imagem = imagem;
		this.objeto = objeto;
		this.comentario = comentario;
		this.categoria_id = categoria_id;
		this.contato = contato;
		this.dtEntrega = dtEntrega;
		this.notificar = notificar;
		this.usu_id = usu_id;
	}
	
	public long getUsu_id() {
		return usu_id;
	}
	
	public void setUsu_id(long usu_id) {
		this.usu_id = usu_id;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public long getCategoria_id() {
		return categoria_id;
	}

	public void setCategoria_id(long categoria_id) {
		this.categoria_id = categoria_id;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Date getDtEntrega() {
		return dtEntrega;
	}

	public void setDtEntrega(Date dtEntrega) {
		this.dtEntrega = dtEntrega;
	}

	public int getNotificar() {
		return notificar;
	}

	public void setNotificar(int notificar) {
		this.notificar = notificar;
	}
	
	@Override
	public String toString() {
		return objeto;
	}

	@Override
	public int compareTo(Emprestimo another) {
		if(this.getId() == another.getId()){
			return 1;
		}
		return 0;
	}

}
