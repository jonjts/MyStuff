package br.com.tep.mystuff.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import br.com.tep.mystuff.dto.Message;
import br.com.tep.mystuff.dto.Response;
import br.com.tep.mystuff.dto.ResponseStatus;
import br.com.tep.mystuff.model.Usuario;

public class UsuarioConverter {

	public static String toJSON(String telefone, String senha, String email)
			throws JSONException {
		JSONStringer js = new JSONStringer();

		js.object().key("numeroTelefone").value(telefone).key("senha").value(senha).key("email").value(email)
				.endObject();

		return js.toString();
	}
	
}
