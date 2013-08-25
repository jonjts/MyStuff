package br.com.tep.mystuff.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.tep.mystuff.model.Usuario;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO {
	
	 public static final String NOME_TABELA = "usuario";
	    public static final String COLUNA_ID = "usu_id";
	    public static final String COLUNA_TELEFONE = "usu_telefone";
	    public static final String COLUNA_EMAIL = "usu_email";
	 
	 
	    public static final String SCRIPT_CRIACAO_TABELA_USUARIO = "CREATE TABLE [usuario] " +
	    		"([usu_id] INTEGER PRIMARY KEY, " +
	    		"[usu_telefone] [VARCHAR(20)] NOT NULL, [usu_email] [varcHAR(50)] NOT NULL)";
	 
	    public static final String SCRIPT_DELECAO_TABELA =  "DROP TABLE IF EXISTS " + NOME_TABELA;
	 
	    private SQLiteDatabase dataBase = null;

	 
	    private static UsuarioDAO instance;
	     
	    public static UsuarioDAO getInstance(Context context) {
	        if(instance == null)
	            instance = new UsuarioDAO(context);
	        return instance;
	    }
	 
	    private UsuarioDAO(Context context) {
	        OpenHelper persistenceHelper = OpenHelper.getInstance(context);
	        dataBase = persistenceHelper.getWritableDatabase();
	    }
	    
	    public boolean exist(Usuario usuario){
	    	 String queryReturnAll = "SELECT * FROM " + NOME_TABELA+ " WHERE usu_id="+usuario.getId();
		        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
		        return cursor.getCount() > 0;
	    }
	    
	    public void insert(Usuario usuario) {
	    	if(!exist(usuario)){
	        ContentValues values = gerarContentValeuesUsuario(usuario);
	        dataBase.insert(NOME_TABELA, null, values);
	    	}
	    }
	 
	    public List<Usuario> getAll() {
	        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
	        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
	        List<Usuario> list = construirUsuarioPorValue(cursor);
	 
	        return list;
	    }
	 
	    public Usuario get(int id){
	    	Cursor c = dataBase.query(NOME_TABELA, null, "usu_id="+id+"",null, null, null, null);
	    	return construirUsuarioPorValue(c).get(0);
	    }
	    
	    public void delete(Usuario usuario) {
	 
	        String[] valoresParaSubstituir = {
	                String.valueOf(usuario.getId())
	        };
	 
	        dataBase.delete(NOME_TABELA, COLUNA_ID + " =  ?", valoresParaSubstituir);
	    }
	 
	    public void update(Usuario usuario) {
	        ContentValues valores = gerarContentValeuesUsuario(usuario);
	 
	        String[] valoresParaSubstituir = {
	                String.valueOf(usuario.getId())
	        };
	 
	        dataBase.update(NOME_TABELA, valores, COLUNA_ID + " = ?", valoresParaSubstituir);
	    }
	 
	    public void close() {
	        if(dataBase != null && dataBase.isOpen())
	            dataBase.close();
	        instance = null;
	    }
	 
	 
	    private List<Usuario> construirUsuarioPorValue(Cursor cursor) {
	        List<Usuario> listOperadoras = new ArrayList<Usuario>();
	        if(cursor == null)
	            return listOperadoras;
	         
	        try {
	 
	            if (cursor.moveToFirst()) {
	                do {
	 
	                    int indexID = cursor.getColumnIndex(COLUNA_ID);
	                    int indexNumero = cursor.getColumnIndex(COLUNA_TELEFONE);
	                    int indexEmail = cursor.getColumnIndex(COLUNA_EMAIL);
	                    
	 
	                    long id = cursor.getLong(indexID);
	                    String numero = cursor.getString(indexNumero);
	                    String email = cursor.getString(indexEmail);
	                    
	 
	                    Usuario usuario = new Usuario(id,numero , email);
	 
	                    listOperadoras.add(usuario);
	 
	                } while (cursor.moveToNext());
	            }
	             
	        } finally {
	            cursor.close();
	        }
	        return listOperadoras;
	    }
	 
	    private ContentValues gerarContentValeuesUsuario(Usuario usuario) {
	        ContentValues values = new ContentValues();
	        values.put(COLUNA_EMAIL, usuario.getEmail());
	        values.put(COLUNA_TELEFONE, usuario.getNumero());
	        values.put(COLUNA_ID, usuario.getId());
	 
	        return values;
	    }
}
