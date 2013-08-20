package br.com.tep.mystuff;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import br.com.tep.mystuff.dao.EmprestimoDAO;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class CadastrarEmprestimo extends SherlockActivity{
	
	private EmprestimoDAO emprestimoDAO;
	private ImageView imgCoisa;
	private EditText edtObjeto;
	private EditText edtComentario;
	private Spinner spnCategorias;
	private ImageButton ibtAddContato;
	private EditText edtContato;
	private EditText edtDataEntrega;
	private CheckBox chkNotificar;
	private Button btnCancel;
	private Button btnEmprestar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_emprestimo);
		getSupportActionBar().setTitle("Novo Emprestimo");
		emprestimoDAO = EmprestimoDAO.getInstance(getApplicationContext());
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		 case android.R.id.home:
			 setResult(RESULT_CANCELED);
            finish();
            break;
		}
		return super.onMenuItemSelected(featureId, item);
		
	}

}
