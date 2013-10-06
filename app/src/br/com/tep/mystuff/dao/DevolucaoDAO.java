package br.com.tep.mystuff.dao;

import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.tep.mystuff.model.Devolucao;

public class DevolucaoDAO {
	
	public static final String NOME_TABELA = "devolucao";
    public static final String COLUNA_ID = "dev_id";
    public static final String COLUNA_OBJETO = "dev_objeto";
    public static final String COLUNA_CONTATO = "dev_contato";
    public static final String COLUNA_DTENTREGA = "dev_dtentrega";
 
 
    public static final String SCRIPT_CRIACAO_TABELA_Devolucao = "CREATE TABLE [devolucao] " +
    		"([dev_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
    		"[dev_objeto] [VARCHAR(200)] NOT NULL,"+
    		"[dev_contato] [VARCHAR(200)] NOT NULL,"+
    		"[dev_dtentrega] VARCHAR NOT NULL)";
 
    public static final String SCRIPT_DELECAO_TABELA =  "DROP TABLE IF EXISTS " + NOME_TABELA;
    public static final String[] ss = new String[]{"INSERT INTO categoria (cat_nome)VALUES ('Geral')"};
 
 
    private SQLiteDatabase dataBase = null;
 
 
    private static DevolucaoDAO instance;
     
    public static DevolucaoDAO getInstance(Context context) {
        if(instance == null)
            instance = new DevolucaoDAO(context);
        return instance;
    }
 
    private DevolucaoDAO(Context context) {
        OpenHelper persistenceHelper = OpenHelper.getInstance(context);
        dataBase = persistenceHelper.getWritableDatabase();
    }
    
    public void insert(Devolucao devolucao) {
		ContentValues values = gerarContentValeuesDevolucao(devolucao);
		dataBase.insert(NOME_TABELA, null, values);
	}
    
    public void deleteAll() {
    	dataBase.delete(NOME_TABELA, "", null);
    }
    
    private ContentValues gerarContentValeuesDevolucao(Devolucao devolucao) {
		ContentValues values = new ContentValues();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		values.put(COLUNA_ID, devolucao.getId());
		values.put(COLUNA_OBJETO, devolucao.getObjeto());
		values.put(COLUNA_DTENTREGA, devolucao.getDtEntrega());
		values.put(COLUNA_CONTATO, devolucao.getContato());

		return values;
	}

}
