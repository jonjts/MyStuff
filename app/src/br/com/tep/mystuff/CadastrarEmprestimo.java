package br.com.tep.mystuff;

import java.io.File;
import java.net.URI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import br.com.tep.mystuff.dao.CategoriaDAO;
import br.com.tep.mystuff.dao.EmprestimoDAO;
import br.com.tep.mystuff.model.Categoria;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class CadastrarEmprestimo extends SherlockActivity {

	private EmprestimoDAO emprestimoDAO;
	private CategoriaDAO categoriaDAO;

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
	
	private int TAKE_THE_PICTURE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_emprestimo);
		getSupportActionBar().setTitle("Novo Emprestimo");
		imgCoisa = (ImageView) findViewById(R.id.imgCoisa);
		edtObjeto = (EditText) findViewById(R.id.edtObjeto);
		edtComentario = (EditText) findViewById(R.id.edtComentario);
		spnCategorias = (Spinner) findViewById(R.id.spnCategorias);
		ibtAddContato = (ImageButton) findViewById(R.id.ibtAddContato);
		edtContato = (EditText) findViewById(R.id.edtContato);
		edtDataEntrega = (EditText) findViewById(R.id.edtDataEntrega);
		chkNotificar = (CheckBox) findViewById(R.id.cekNotificar);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnEmprestar = (Button) findViewById(R.id.btnEmprestar);

		emprestimoDAO = EmprestimoDAO.getInstance(getApplicationContext());
		categoriaDAO = CategoriaDAO.getInstance(getApplicationContext());

		loadCategorias();

		imgCoisa.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				takeThePicture();

			}
		});
	}

	private void takeThePicture() {
		File storageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyStuff");
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri pictureUri = Uri.fromFile(storageDir);
		camera.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
		startActivityForResult(camera, TAKE_THE_PICTURE);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == TAKE_THE_PICTURE){
	        if (resultCode == RESULT_OK) {
	            takePicture(data);
	        } else if (resultCode == RESULT_CANCELED) {

	        }
	    }
	}

	protected void takePicture(Intent data) {
	    Bundle b = data.getExtras();
	   Bitmap pic = (Bitmap) b.get("data");
	    if (pic != null) {
	    	
	        imgCoisa.setImageBitmap(pic);
	    }
	}

	private void loadCategorias() {
		ArrayAdapter<Categoria> listCat = new ArrayAdapter<Categoria>(this,
				android.R.layout.simple_spinner_item, categoriaDAO.getAll());
		listCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnCategorias.setAdapter(listCat);
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
