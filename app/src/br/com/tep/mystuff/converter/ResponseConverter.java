package br.com.tep.mystuff.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.tep.mystuff.dto.Message;
import br.com.tep.mystuff.dto.Response;
import br.com.tep.mystuff.dto.ResponseStatus;


public class ResponseConverter {
	
	public static Response toObject(String json) {
		Response result = null;

		try {
			JSONObject obj = new JSONObject(json);

			ResponseStatus status = ResponseStatus.valueOf((String)obj.get("status"));
			result = new Response(status);
			result.setData(obj.get("data"));
			
			JSONArray messages = obj.getJSONArray("messages");
			Message message = null;
			
			for (int i = 0; i < messages.length(); i++) {
				JSONObject mobj = messages.getJSONObject(i);
				message = new Message(mobj.getString("value"));
				
				result.addMessage(message);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}


}
