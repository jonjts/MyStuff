package br.com.tep.mystuff.converter;

import org.json.JSONException;
import org.json.JSONStringer;

public class UsuarioConverter {

	public static String toJSON(String telefone, String senha)
			throws JSONException {
		JSONStringer js = new JSONStringer();

		js.object().key("numeroTelefone").value(telefone).key("senha").value(senha)
				.endObject();

		return js.toString();
	}
}
