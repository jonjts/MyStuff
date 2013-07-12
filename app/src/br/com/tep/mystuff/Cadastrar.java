package br.com.tep.mystuff;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
		setContentView(R.layout.cadastrar);
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
				// TODO Auto-generated method stub
				
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
