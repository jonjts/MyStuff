package br.com.tep.mystuff;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import br.com.tep.mystuff.dao.CategoriaDAO;
import br.com.tep.mystuff.model.Categoria;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class ListarCategoria extends SherlockActivity{
	private AlertDialog dialog;
	private ListView lisCategoria;
	private CategoriaDAO categoriaDAO;
	private final int ADD = 2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_categoria);
		lisCategoria = (ListView) findViewById(R.id.lisCategorias);
		categoriaDAO = CategoriaDAO.getInstance(getApplicationContext());
		loadListView();
		
		lisCategoria.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Categoria categoria = (Categoria) lisCategoria.getItemAtPosition(arg2);
				startActionMode(new AnActionModeOfEpicProportions(categoria));
				
			}
		});
		
		
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
            menu.add(0, ADD, 0, R.string.adicionar)
                .setIcon(R.drawable.ic_add)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            
        return super.onCreateOptionsMenu(menu);
    }
	
	 @Override
		public boolean onMenuItemSelected(int featureId, MenuItem item) {
			switch (item.getItemId()) {
			case ADD:
				 
				LayoutInflater li = getLayoutInflater();
			    final View view = li.inflate(R.layout.add_categoria, null);
			    view.findViewById(R.id.btnSalvarCategoria).setOnClickListener(new View.OnClickListener() {
			        public void onClick(View arg0) {
			        	try{
			        		String nome = ((EditText) view.findViewById(R.id.edtNomeCategoria)).getText().toString();
			        		Categoria cat = new Categoria();
			        		cat.setNome(nome);
			        		categoriaDAO.insert(cat);
			        		loadListView();
			        		dialog.dismiss();
			        	}catch(Exception e){
			        		Toast.makeText(ListarCategoria.this, e.getMessage(), Toast.LENGTH_LONG).show();
			        	}
			        }
			    });
			     
			    AlertDialog.Builder builder2 = new AlertDialog.Builder(ListarCategoria.this);
			    builder2.setTitle("Adicionar");
			    builder2.setView(view);
			    dialog = builder2.create();
			    dialog.show();
				break;
			default:
				break;
			}
			return super.onMenuItemSelected(featureId, item);
		}
	
	private void loadListView(){
		List<Categoria> d = categoriaDAO.getAll();
		ArrayAdapter<Categoria> list = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1);
		for(Categoria c : d){
			list.add(c);
		}
		lisCategoria.setAdapter(list);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		categoriaDAO.close();
	}
	
	
	private final class AnActionModeOfEpicProportions implements ActionMode.Callback {
		
		private final int DELETE = 1;
		private final int UPDATE = 3;
		private Categoria categoria;
		
		public AnActionModeOfEpicProportions(Categoria categoria) {
			this.categoria = categoria;
		}
		
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            //Used to put dark icons on light action bar

            menu.add(0, DELETE, 0, "Delete")
                .setIcon(R.drawable.abs__ic_clear)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

            menu.add(0, UPDATE, 0, "Update")
            .setIcon(R.drawable.ic_update)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
        

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
			case DELETE:
			    AlertDialog.Builder builder = new AlertDialog.Builder(ListarCategoria.this);
			    builder.setTitle("Excluir");
			    builder.setMessage("Deseja remover "+ categoria.getNome()+"?");
			    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface arg0, int arg1) {
			            try{
			            	categoriaDAO.delete(categoria);
			            	loadListView();
			            	Toast.makeText(getApplicationContext(), "Excluido!", Toast.LENGTH_LONG).show();
			            }catch(Exception e){
			            	Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			            }
			        }
			    });
			    builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface arg0, int arg1) {
			            
			        }
			    });
			    builder.create().show();
				break;
			case UPDATE:
				LayoutInflater li = getLayoutInflater();
			    final View view = li.inflate(R.layout.add_categoria, null);
			    ((EditText) view.findViewById(R.id.edtNomeCategoria)).setHint(categoria.getNome());
			    view.findViewById(R.id.btnSalvarCategoria).setOnClickListener(new View.OnClickListener() {
			        public void onClick(View arg0) {
			        	try{
			        		String nome = ((EditText) view.findViewById(R.id.edtNomeCategoria)).getText().toString();
			        		categoria.setNome(nome);
			        		categoriaDAO.update(categoria);
			        		loadListView();
			        		dialog.dismiss();
			        	}catch(Exception e){
			        		Toast.makeText(ListarCategoria.this, e.getMessage(), Toast.LENGTH_LONG).show();
			        	}
			        }
			    });
			     
			    AlertDialog.Builder builder2 = new AlertDialog.Builder(ListarCategoria.this);
			    builder2.setTitle("Atualizar");
			    builder2.setView(view);
			    dialog = builder2.create();
			    dialog.show();
				break;
			default:
				break;
			}
            mode.finish();
            return true;
        }

        
        
        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    }
}
