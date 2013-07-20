package br.com.tep.mystuff;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.tep.mystuff.service.PeformLoginTask;
import br.com.tep.mystuff.service.PefromSignupTask;

import com.actionbarsherlock.app.SherlockActivity;

public class Cadastrar extends SherlockActivity{

	private EditText edtNumero;
	private EditText edtSenha;
	private EditText edtEmail;
	private Button btnCadastrar;
	private Button btnCancelar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastrar_usuario);
		edtEmail = (EditText) findViewById(R.id.edtCadEmail);
		edtNumero = (EditText) findViewById(R.id.edtCadtelefone);
		edtSenha = (EditText) findViewById(R.id.edtCadSenha);
		btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
		btnCancelar = (Button) findViewById(R.id.btnCancelar);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			edtNumero.setText(bundle.getString("tel"));
		}
		
		btnCadastrar.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String numero = edtNumero.getText().toString();
				String senha = edtSenha.getText().toString();
				String email = edtEmail.getText().toString();
				try{
				PefromSignupTask signupTask = new PefromSignupTask(Cadastrar.this, numero, senha,email);
				signupTask.execute();
				}catch(Exception e){
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
		
		btnCancelar.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
}
