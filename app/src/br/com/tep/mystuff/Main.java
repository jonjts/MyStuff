package br.com.tep.mystuff;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.com.tep.mystuff.dao.EmprestimoDAO;
import br.com.tep.mystuff.model.Emprestimo;
import br.com.tep.mystuff.util.Constant;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class Main extends SherlockActivity{

	
	private final int ADD = 1;
	private final int CATEGORIAS = 2;
	private final int CASCAR_FORA = 3;
	
	private ListView listEmprestado;
	private ListView listEmprestei;
	
	private EmprestimoDAO emprestimoDAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		if(getIntent().getExtras()!= null && getIntent().getExtras().containsKey("email")){
			getSupportActionBar().setSubtitle(getIntent().getExtras().getString("email"));
		}
		SharedPreferences settings = getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);
		getSupportActionBar().setSubtitle(settings.getString("numeroTelefone", ""));
		
		listEmprestado = (ListView) findViewById(R.id.listEmprstado);
		listEmprestei = (ListView) findViewById(R.id.listEmprestei);
		
		
		listEmprestei.setEmptyView(findViewById(R.id.vazio));
		listEmprestado.setEmptyView(findViewById(R.id.TextVazio));
		
		emprestimoDAO = EmprestimoDAO.getInstance(getApplicationContext());
		
	}
	
	 private void loadListEmpretado() {
		 SharedPreferences settings = getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);
		 int usu_id = settings.getInt("usu_id", 0);
		ArrayAdapter<Emprestimo> list = new ArrayAdapter<Emprestimo>(this, android.R.layout.simple_list_item_1, emprestimoDAO.getByUsu_id(usu_id));
		listEmprestado.setAdapter(list);
		
	}

	 
	 @Override
	protected void onResume() {
		super.onResume();
		loadListEmpretado();
	}
	 
	@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        
	            menu.add(0, ADD, 0, R.string.adicionar)
	                .setIcon(R.drawable.ic_add)
	                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	            
	            menu.add(0, CATEGORIAS, 0, R.string.categorias)
                .setIcon(R.drawable.ic_action_name)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	            
	            menu.add(0, CASCAR_FORA, 0, R.string.sair)
                .setIcon(R.drawable.abs__ic_clear_normal)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

	        return super.onCreateOptionsMenu(menu);
	    }
	 
	 
	 @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case CATEGORIAS:
			startActivity(new Intent(this, ListarCategoria.class));
			break;
		case ADD:
			startActivityForResult(new Intent(this, CadastrarEmprestimo.class), ADD);
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	 
	 @Override
	protected void onDestroy() {
		super.onDestroy();
		emprestimoDAO.close();
	}
}
