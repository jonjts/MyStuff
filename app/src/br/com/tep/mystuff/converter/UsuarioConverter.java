package br.com.tep.mystuff.converter;

import org.json.JSONException;
import org.json.JSONStringer;

public class UsuarioConverter {

	public static String toJSON(String telefone, String senha, String email)
			throws JSONException {
		JSONStringer js = new JSONStringer();

		js.object().key("numeroTelefone").value(telefone).key("email").value(email).key("senha").value(senha)
				.endObject();

		return js.toString();
	}
	
}
