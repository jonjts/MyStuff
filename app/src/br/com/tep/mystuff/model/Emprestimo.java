package br.com.tep.mystuff.model;

import java.util.Date;

import android.R.bool;

public class Emprestimo {

	private int id;
	private String imagem;
	private String objeto;
	private String comentario;
	private int categoria_id;
	private String contato;
	private Date dtEntrega;
	private int notificar;
	private int usu_id;

	public Emprestimo() {
		super();
	}

	public Emprestimo(int id,String imagem, String objeto, String comentario,
			int categoria_id, String contato, Date dtEntrega, int notificar, int usu_id) {
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
	
	public int getUsu_id() {
		return usu_id;
	}
	
	public void setUsu_id(int usu_id) {
		this.usu_id = usu_id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
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

	public int getCategoria_id() {
		return categoria_id;
	}

	public void setCategoria_id(int categoria_id) {
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

}
