package br.com.tep.mystuff.helper;



import java.io.Serializable;

import br.com.tep.mystuff.model.Usuario;


public class ContextHelper implements Serializable {

	private static final long serialVersionUID = -4566038936134796052L;
	private Usuario usuario;
	private String sessionId;

	public ContextHelper(Usuario usuario, String sessionId) {
		this.usuario = usuario;
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
