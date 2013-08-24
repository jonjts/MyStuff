package br.com.tep.mystuff;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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
import android.widget.Toast;
import br.com.tep.mystuff.dao.CategoriaDAO;
import br.com.tep.mystuff.dao.EmprestimoDAO;
import br.com.tep.mystuff.model.Categoria;
import br.com.tep.mystuff.model.Emprestimo;
import br.com.tep.mystuff.util.CameraUtil;
import br.com.tep.mystuff.util.Constant;

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
	private int GET_CONTACT = 2;

	private Uri uriFoto;

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

		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		btnEmprestar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Emprestimo emp = isValide();
					if (emp == null) {
						Toast.makeText(getApplicationContext(),
								"Dados invalidos", Toast.LENGTH_LONG).show();
					}else{
						emprestimoDAO.insert(emp);
						Toast.makeText(CadastrarEmprestimo.this, "Inserido com sucesso!",
								Toast.LENGTH_LONG).show();
						finish();
					}
				} catch (Exception e) {
					Toast.makeText(CadastrarEmprestimo.this, "Dados invalidos",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		ibtAddContato.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(CadastrarEmprestimo.this,
						ContatosAgenda.class), GET_CONTACT);
			}
		});

		imgCoisa.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				takeThePicture();

			}
		});
	}

	private void takeThePicture() {
		try {
			uriFoto = Uri.fromFile(createImageFile());
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,uriFoto);
			startActivityForResult(cameraIntent, TAKE_THE_PICTURE);
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TAKE_THE_PICTURE) {
			if (resultCode == RESULT_OK) {
				takePicture(uriFoto);
			} else if (resultCode == RESULT_CANCELED) {

			}
		} else if (requestCode == GET_CONTACT && resultCode == RESULT_OK) {
			String tel = data.getStringExtra("tel");
			edtContato.setText(tel);
		}
	}

	protected void takePicture(Uri uri) {
		try {
			Bitmap foto = BitmapFactory.decodeFile(uri.getPath());
			foto = Bitmap.createScaledBitmap(foto, 150, 150, true);

			ExifInterface ei = null;
			try {
				ei = new ExifInterface(uri.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}

			int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				foto = CameraUtil.rotateImage(90, foto);
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				foto = CameraUtil.rotateImage(180, foto);
				break;
			}

			imgCoisa.setImageBitmap(foto);
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * Cria uma arquivo para a foto a ser capturada
	 * 
	 * @return
	 * @throws IOException
	 */
	private File createImageFile() throws IOException {
		// Nome da foto a ser salva
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = "img_" + timeStamp;

		// Cria a imagem temporário com tamanho padrão da camera
		File image = File.createTempFile(imageFileName, ".temp", getAlbumDir());

		return image;
	}

	/**
	 * Retorna o diretório onde as fotos serão salvas
	 * 
	 * @return
	 */
	private File getAlbumDir() {
		File result = new File(getExternalFilesDir(
				Environment.DIRECTORY_PICTURES).toURI());
		Log.i("Diretorio", result.toURI().toString());

		if (!result.exists()) {
			result.mkdir();
		}

		return result;
	}

	// valida os campos pegando os seus valores e setando em um Emprestimo
	private Emprestimo isValide() throws Exception {
		Emprestimo emprestimo = new Emprestimo();
		emprestimo.setComentario(edtComentario.getText().toString());
		String objeto = edtObjeto.getText().toString();
		emprestimo.setObjeto(objeto);
		if (emprestimo.getObjeto().length() < 0) {
			return null;
		}
		if (spnCategorias.getSelectedItem() != null) {
			emprestimo.setCategoria_id(((Categoria) spnCategorias
					.getSelectedItem()).getId());
		} else {
			return null;
		}
		emprestimo.setContato(edtContato.getText().toString());
		if (emprestimo.getContato().length() < 0) {
			return null;
		}

		try {
			String data = edtDataEntrega.getText().toString();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date dt = format.parse(data);
			emprestimo.setDtEntrega(dt);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if(uriFoto!= null){
			emprestimo.setImagem(uriFoto.getPath());
		}else{
			emprestimo.setImagem("");
		}
		emprestimo.setNotificar(chkNotificar.isChecked() ? 1 : 0);
		SharedPreferences settings = getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);
		emprestimo.setUsu_id(settings.getInt("usu_id", 0));
		return emprestimo;

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		emprestimoDAO.close();
		categoriaDAO.close();
	}

}
