package br.com.tep.mystuff;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.tep.mystuff.model.Usuario;

import com.actionbarsherlock.app.SherlockActivity;

public class ContatosAgenda extends SherlockActivity {

	private ListView lstContatos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_contato);
		setTitle("Contatos");
		lstContatos = (ListView) findViewById(R.id.lstContatos);
		ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, getTelefones());
		lstContatos.setAdapter(adapter);
		lstContatos.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle b = new Bundle();
				b.putString("tel", ((Usuario)arg0.getAdapter().getItem(arg2)).getNumero());
				Intent it = new Intent();
				it.putExtras(b);
				setResult(RESULT_OK, it);
				finish();
			}
		});
	}

	public List<Usuario> getTelefones() {
		Cursor C_Telefones = this.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		int IndexTel;
		List<Usuario> Telefones = new ArrayList<Usuario>();
		while (C_Telefones.moveToNext()) {
			Usuario usu = new Usuario();
			IndexTel = C_Telefones
					.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);
			usu.setNumero(C_Telefones.getString(IndexTel));
			Telefones.add(usu);
		}
		C_Telefones.close();
		return Telefones;
	}

}
