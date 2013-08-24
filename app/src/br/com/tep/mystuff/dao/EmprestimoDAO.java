package br.com.tep.mystuff.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.tep.mystuff.model.Emprestimo;
import br.com.tep.mystuff.model.Usuario;

public class EmprestimoDAO {

	public static final String NOME_TABELA = "emprestimo";
	public static final String COLUNA_ID = "emp_id";
	public static final String COLUNA_OBJETO = "emp_objeto";
	public static final String COLUNA_IMAGEM = "emp_imagem";
	public static final String COLUNA_COMENTARIO = "emp_comentario";
	public static final String COLUNA_CATEGORIA_ID = "cat_id";
	public static final String COLUNA_CONTATO = "emp_contato";
	public static final String COLUNA_DATA_ENTREGA = "emp_dtentrega";
	public static final String COLUNA_NOTIFICAR = "emp_notificar";
	public static final String COLUNA_USU_ID = "usu_id";

	public static final String SCRIPT_CRIACAO_TABELA_EMPRESTIMO = "CREATE TABLE [emprestimo] "
			+ "( [emp_id] INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ " [emp_imagem] [VARCHAR(500)], "
			+ "[emp_objeto] [varcHAR(120)] NOT NULL,"
			+ " [emp_comentario] [varchAR(200)], "
			+ "[cat_id] INTEGER NOT NULL CONSTRAINT [FK_cat_id] REFERENCES [categoria]([cat_id]) ON DELETE RESTRICT ON UPDATE CASCADE, "
			+ "[emp_contato] [VARCHAR(120)] NOT NULL, "
			+ "[emp_dtentrega] VARCHAR NOT NULL, "
			+ "[emp_notificar] INTEGER NOT NULL, "
			+ "[usu_id] INTEGER NOT NULL CONSTRAINT [FK_usu_id] REFERENCES [usuario]([usu_id]) ON DELETE CASCADE ON UPDATE CASCADE)";

	public static final String SCRIPT_DELECAO_TABELA = "DROP TABLE IF EXISTS "
			+ NOME_TABELA;

	private SQLiteDatabase dataBase = null;

	private static EmprestimoDAO instance;

	public static EmprestimoDAO getInstance(Context context) {
		if (instance == null)
			instance = new EmprestimoDAO(context);
		return instance;
	}

	private EmprestimoDAO(Context context) {
		OpenHelper persistenceHelper = OpenHelper.getInstance(context);
		dataBase = persistenceHelper.getWritableDatabase();
	}

	public void insert(Emprestimo emprestimo) {
		ContentValues values = gerarContentValeuesEmprestimo(emprestimo);
		dataBase.insert(NOME_TABELA, null, values);
	}

	public List<Emprestimo> getAll() {
		String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
		Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
		List<Emprestimo> list = construirEmprestimoPorValue(cursor);

		return list;
	}

	public List<Emprestimo> getByUsu_id(int usu_id) {
		String queryReturnAll = "SELECT * FROM " + NOME_TABELA
				+ " WHERE usu_id = " + usu_id;
		Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
		List<Emprestimo> list = construirEmprestimoPorValue(cursor);

		return list;
	}

	public Emprestimo get(int id) {
		Cursor c = dataBase.query(NOME_TABELA, null, "emp_id=" + id + "", null,
				null, null, null);
		return construirEmprestimoPorValue(c).get(0);
	}

	public void delete(Emprestimo emprestimo) {

		String[] valoresParaSubstituir = { String.valueOf(emprestimo.getId()) };

		dataBase.delete(NOME_TABELA, COLUNA_ID + " =  ?", valoresParaSubstituir);
	}

	public void update(Emprestimo emprestimo) {
		ContentValues valores = gerarContentValeuesEmprestimo(emprestimo);

		String[] valoresParaSubstituir = { String.valueOf(emprestimo.getId()) };

		dataBase.update(NOME_TABELA, valores, COLUNA_ID + " = ?",
				valoresParaSubstituir);
	}

	public void close() {
		if (dataBase != null && dataBase.isOpen())
			dataBase.close();
		instance = null;
	}

	private List<Emprestimo> construirEmprestimoPorValue(Cursor cursor) {
		List<Emprestimo> list = new ArrayList<Emprestimo>();
		if (cursor == null)
			return list;

		try {

			if (cursor.moveToFirst()) {
				do {

					int indexID = cursor.getColumnIndex(COLUNA_ID);
					int indexImagem = cursor.getColumnIndex(COLUNA_IMAGEM);
					int indexObjeto = cursor.getColumnIndex(COLUNA_OBJETO);
					int indexComentario = cursor.getColumnIndex(COLUNA_COMENTARIO);
					int indexCategoria_id = cursor.getColumnIndex(COLUNA_CATEGORIA_ID);
					int indexConatato = cursor.getColumnIndex(COLUNA_CONTATO);
					int indexDtEntrega = cursor.getColumnIndex(COLUNA_DATA_ENTREGA);
					int indexNoficicar = cursor.getColumnIndex(COLUNA_NOTIFICAR);
					int indexUsuId = cursor.getColumnIndex(COLUNA_USU_ID);

					int id = cursor.getInt(indexID);
					String imagem = cursor.getString(indexImagem);
					String objeto = cursor.getString(indexObjeto);
					String comentario = cursor.getString(indexComentario);
					int categoria_id = cursor.getInt(indexCategoria_id);
					String contato = cursor.getString(indexConatato);
					Date dtEntrega = null;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					try {
						dtEntrega = dateFormat.parse(cursor.getString(indexDtEntrega));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					int notificar = cursor.getInt(indexNoficicar);
					int usu_id = cursor.getInt(indexUsuId);
					
					Emprestimo emprestimo = new Emprestimo(id, imagem, objeto, comentario, categoria_id, contato, dtEntrega, notificar, usu_id);
					list.add(emprestimo);
				} while (cursor.moveToNext());
			}

		}catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			cursor.close();
		}
		return list;
	}

	private ContentValues gerarContentValeuesEmprestimo(Emprestimo emprestimo) {
		ContentValues values = new ContentValues();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		values.put(COLUNA_CATEGORIA_ID, emprestimo.getCategoria_id());
		values.put(COLUNA_COMENTARIO, emprestimo.getComentario());
		values.put(COLUNA_CONTATO, emprestimo.getContato());
		values.put(COLUNA_DATA_ENTREGA,
				dateFormat.format(emprestimo.getDtEntrega()));
		// values.put(COLUNA_ID, emprestimo.getId());
		values.put(COLUNA_IMAGEM, emprestimo.getImagem());
		values.put(COLUNA_NOTIFICAR, emprestimo.getNotificar());
		values.put(COLUNA_OBJETO, emprestimo.getObjeto());
		values.put(COLUNA_USU_ID, emprestimo.getUsu_id());

		return values;
	}

}
