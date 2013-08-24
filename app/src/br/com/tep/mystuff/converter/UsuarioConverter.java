package br.com.tep.mystuff.converter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import br.com.tep.mystuff.model.Usuario;

public class UsuarioConverter {

	public static String toJSON(String telefone, String senha, String email)
			throws JSONException {
		JSONStringer js = new JSONStringer();

		js.object().key("numeroTelefone").value(telefone).key("email").value(email).key("senha").value(senha)
				.endObject();

		return js.toString();
	}
	
	public static Usuario fromJSON(JSONObject jsonObject) throws JSONException {
		Usuario result = new Usuario();
		
		String telefone = jsonObject.getString("numeroTelefone");
		String email = jsonObject.getString("email");
		long id = jsonObject.getJSONObject("key").getLong("value");
		
		result.setId(Integer.parseInt(String.valueOf(id)));
		result.setNumero(telefone);
		result.setEmail(email);
		
		return result;
	}
	
}
