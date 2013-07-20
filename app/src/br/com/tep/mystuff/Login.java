package br.com.tep.mystuff;

import br.com.tep.mystuff.service.PeformLoginTask;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends SherlockActivity {

	private EditText edtNumero;
	private EditText edtSenha;
	private TextView txtNaoCadastrado;
	private Button btnLogin;
	
	private int CADASTRAR = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		edtNumero = (EditText) findViewById(R.id.edtTelefone);
		edtSenha = (EditText) findViewById(R.id.edtSenha);
		txtNaoCadastrado = (TextView) findViewById(R.id.txtNaoCadastrado);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		
		btnLogin.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String numero = edtNumero.getText().toString();
				String senha = edtSenha.getText().toString();
				try{
					if(numero.length() > 0 && senha.length() > 0){
						PeformLoginTask loginTask = new PeformLoginTask(Login.this, numero, senha);
						loginTask.execute();
					}else{
						Toast.makeText(Login.this, "Dados invalidos!", Toast.LENGTH_LONG).show();
					}
				}catch(Exception e){
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		txtNaoCadastrado.setOnClickListener(new TextView.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("tel", edtNumero.getText().toString());
				Intent intent = new Intent(getApplicationContext(), Cadastrar.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, CADASTRAR);
			}
		});
		
	}
	

}
