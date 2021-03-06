package br.com.tep.mystuff.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.provider.CalendarContract.CalendarAlerts;

public class OpenHelper extends android.database.sqlite.SQLiteOpenHelper{

	public static final String NOME_BANCO =  "my_stuff";
    public static final int VERSAO =  5;
     
    private static OpenHelper instance;
     
    public OpenHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }
     
    public static OpenHelper getInstance(Context context) {
        if(instance == null)
            instance = new OpenHelper(context);
         
        return instance;
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoriaDAO.SCRIPT_CRIACAO_TABELA_Categoria);
        db.execSQL(DevolucaoDAO.SCRIPT_CRIACAO_TABELA_Devolucao);
        db.execSQL(UsuarioDAO.SCRIPT_CRIACAO_TABELA_USUARIO);
        db.execSQL(EmprestimoDAO.SCRIPT_CRIACAO_TABELA_EMPRESTIMO);
        String[] s = CategoriaDAO.ss;
        for(int i = 0; i < s.length; i++ ){
        	db.execSQL(s[i]);
        }
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	db.execSQL(EmprestimoDAO.SCRIPT_DELECAO_TABELA);
        db.execSQL(CategoriaDAO.SCRIPT_DELECAO_TABELA);
        db.execSQL(UsuarioDAO.SCRIPT_DELECAO_TABELA);
        db.execSQL(DevolucaoDAO.SCRIPT_DELECAO_TABELA);
        onCreate(db);
    }
 

}
