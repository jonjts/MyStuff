package br.com.tep.mystuff.task;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.tep.mystuff.Main;
import br.com.tep.mystuff.converter.ResponseConverter;
import br.com.tep.mystuff.converter.UsuarioConverter;
import br.com.tep.mystuff.dao.UsuarioDAO;
import br.com.tep.mystuff.dto.Response;
import br.com.tep.mystuff.dto.ResponseStatus;
import br.com.tep.mystuff.helper.ContextHelper;
import br.com.tep.mystuff.model.Usuario;
import br.com.tep.mystuff.util.ApplicationContext;
import br.com.tep.mystuff.util.Constant;
import br.com.tep.mystuff.util.WebClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


public class PeformLoginTask extends AsyncTask<Object, Object, String> {

	private final Context context;
	private ProgressDialog progressDialog;
	private String telefone;
	private String senha;
	private String cookie;

	public PeformLoginTask(Context context, String telefone, String senha) {
		this.context = context;
		this.telefone = telefone;
		this.senha = senha;
	}

	@Override
	protected String doInBackground(Object... params) {

		try {
			String url = "http://mystuff.michef.com.br/login";
			String json = UsuarioConverter.toJSON(telefone, senha, "");

			WebClient wc = new WebClient(url);
			String result = wc.post(json);
			cookie = wc.getCookies();

			return result;

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	private void insertUsuarioDb(Response response) {
		Usuario usuario = null;

		try {
			usuario = UsuarioConverter.fromJSON((JSONObject) response.getData());
			UsuarioDAO usuarioDAO = UsuarioDAO.getInstance(context);
			usuarioDAO.insert(usuario);
			usuarioDAO.close();
		} catch (JSONException e) {
			Log.e("Parse", e.getMessage());
		}
	}
	
	private void mainAction(Response response) {
		String sessionId = cookie.substring(0, cookie.indexOf(";"));

		Usuario usuario = null;

		try {
			usuario = UsuarioConverter.fromJSON((JSONObject) response.getData());
			ContextHelper helper = new ContextHelper(usuario, sessionId);
			ApplicationContext.getContext().setHelper(helper);
			saveLoginPreference(helper);

			Intent i = new Intent(context, Main.class);
			//i.putExtra("context", helper);

			context.startActivity(i);
			((Activity) context).finish();

		} catch (JSONException e) {
			Log.e("Parse", e.getMessage());
		}
	}
	
	private void saveLoginPreference(ContextHelper helper) {
		SharedPreferences settings = context.getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = settings.edit();
		editor.putString("numeroTelefone", helper.getUsuario().getNumero());
		editor.putInt("usu_id", helper.getUsuario().getId());

		editor.commit();
	}

	
	@Override
	protected void onPostExecute(String result) {
		progressDialog.dismiss();

		if (!result.equals("")) {
			Response response = ResponseConverter.toObject(result);

			if (response.getStatus().equals(ResponseStatus.ERROR)) {
				Toast.makeText(context,
						response.getMessages().get(0).getValue(),
						Toast.LENGTH_SHORT).show();
			} else {
				insertUsuarioDb(response);
				mainAction(response);
			}
			
		} else {
			Toast.makeText(context, Constant.ERROR_SERVIDOR, Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void mainAction() {
		Intent i = new Intent(context, Main.class);
		context.startActivity(i);
		((Activity) context).finish();
	}

	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(context, "Aguarde...",
				"Envio de dados para servidor", true, true);
	}

}
