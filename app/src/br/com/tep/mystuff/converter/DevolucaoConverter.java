package br.com.tep.mystuff.converter;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.tep.mystuff.model.Devolucao;

public class DevolucaoConverter {

	public static Devolucao fromJSON(JSONObject jsonObject) throws JSONException {
		Devolucao devolucao = new Devolucao();
		devolucao.setObjeto((String) jsonObject.get("objeto"));
		devolucao.setContato((String) jsonObject.get("contato"));
		devolucao.setDtEntrega((String) jsonObject.get("dataEntrega"));
		
		return devolucao;
	}
}
