package br.com.tep.mystuff;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class Main extends SherlockActivity{

	
	private final int ADD = 1;
	private final int CATEGORIAS = 2;
	private final int CASCAR_FORA = 3;
	
	private ListView listEmprestado;
	private ListView listEmprestei;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		listEmprestado = (ListView) findViewById(R.id.listEmprstado);
		listEmprestei = (ListView) findViewById(R.id.listEmprestei);
		
		
		listEmprestei.setEmptyView(findViewById(R.id.vazio));
		listEmprestado.setEmptyView(findViewById(R.id.TextVazio));
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

		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}
