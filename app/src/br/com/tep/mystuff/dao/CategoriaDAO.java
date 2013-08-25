package br.com.tep.mystuff.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.tep.mystuff.model.Categoria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CategoriaDAO {
	

	 public static final String NOME_TABELA = "categoria";
	    public static final String COLUNA_ID = "cat_id";
	    public static final String COLUNA_NOME = "cat_nome";
	 
	 
	    public static final String SCRIPT_CRIACAO_TABELA_Categoria = "CREATE TABLE [categoria] " +
	    		"( [cat_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
	    		"[cat_nome] [VARCHAR(200)] NOT NULL)";
	 
	    public static final String SCRIPT_DELECAO_TABELA =  "DROP TABLE IF EXISTS " + NOME_TABELA;
	    public static final String[] ss = new String[]{"INSERT INTO categoria (cat_nome)VALUES ('Geral')"};
	 
	 
	    private SQLiteDatabase dataBase = null;
	 
	 
	    private static CategoriaDAO instance;
	     
	    public static CategoriaDAO getInstance(Context context) {
	        if(instance == null)
	            instance = new CategoriaDAO(context);
	        return instance;
	    }
	 
	    private CategoriaDAO(Context context) {
	        OpenHelper persistenceHelper = OpenHelper.getInstance(context);
	        dataBase = persistenceHelper.getWritableDatabase();
	    }
	    
	    
	    public void insert(Categoria categoria) {
	        ContentValues values = gerarContentValeuesCategoria(categoria);
	        dataBase.insert(NOME_TABELA, null, values);
	    }
	 
	    public List<Categoria> getAll() {
	        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
	        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
	        List<Categoria> list = construirCategoriaPorCursor(cursor);
	 
	        return list;
	    }
	 
	    public void delete(Categoria categoria) {
	 
	        String[] valoresParaSubstituir = {
	                String.valueOf(categoria.getId())
	        };
	 
	        dataBase.delete(NOME_TABELA, COLUNA_ID + " =  ?", valoresParaSubstituir);
	    }
	 
	    public void update(Categoria categoria) {
	        ContentValues valores = gerarContentValeuesCategoria(categoria);
	 
	        String[] valoresParaSubstituir = {
	                String.valueOf(categoria.getId())
	        };
	 
	        dataBase.update(NOME_TABELA, valores, COLUNA_ID + " = ?", valoresParaSubstituir);
	    }
	 
	    public void close() {
	        if(dataBase != null && dataBase.isOpen())
	            dataBase.close();
	        instance = null;
	    }
	 
	 
	    private List<Categoria> construirCategoriaPorCursor(Cursor cursor) {
	        List<Categoria> listOperadoras = new ArrayList<Categoria>();
	        if(cursor == null)
	            return listOperadoras;
	         
	        try {
	 
	            if (cursor.moveToFirst()) {
	                do {
	 
	                    int indexID = cursor.getColumnIndex(COLUNA_ID);
	                    int indexNome = cursor.getColumnIndex(COLUNA_NOME);
	 
	                    long id = cursor.getLong(indexID);
	                    String nome = cursor.getString(indexNome);
	 
	                    Categoria categoria = new Categoria(id, nome);
	 
	                    listOperadoras.add(categoria);
	 
	                } while (cursor.moveToNext());
	            }
	             
	        } finally {
	            cursor.close();
	        }
	        return listOperadoras;
	    }
	 
	    private ContentValues gerarContentValeuesCategoria(Categoria categoria) {
	        ContentValues values = new ContentValues();
	        values.put(COLUNA_NOME, categoria.getNome());
	 
	        return values;
	    }

}
