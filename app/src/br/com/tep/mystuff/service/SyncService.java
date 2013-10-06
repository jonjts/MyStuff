package br.com.tep.mystuff.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import br.com.tep.mystuff.converter.DevolucaoConverter;
import br.com.tep.mystuff.converter.EmprestimoConverter;
import br.com.tep.mystuff.dao.DevolucaoDAO;
import br.com.tep.mystuff.model.Devolucao;

public class SyncService extends Service{
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			//String url = "http://mystuff.michef.com.br/app/usuario/12/emprestimo";
			
			String jsonRetorno = "{'registros':[{'objeto':'Objeto Teste 2','categoria':'1','contato':'1','dataEntrega':'20/09/2013','notificar':'0','usuario':'1'}]}";
			JSONArray lista = EmprestimoConverter.toArray(jsonRetorno);
			Boolean atraso = insertDevolucoesDB(lista);
			
			if (atraso) {
						
				NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
				Notification myNotification = new Notification(R.drawable.ic_dialog_alert,
						"My Stuff",
						System.currentTimeMillis());
				
				Context context = getApplicationContext();
				String titulo = "Devoluções atrasadas";
				String texto = "Devoluções atrasadas.";
				PendingIntent pendingIntent = PendingIntent.getActivity(SyncService.this, 0, null, Intent.FLAG_ACTIVITY_NEW_TASK);
				
				myNotification.defaults |= Notification.DEFAULT_SOUND;
				   myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
				   myNotification.setLatestEventInfo(context,
				      titulo,
				      texto,
				      pendingIntent);
				   notificationManager.notify(1, myNotification);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		} 	
		return super.onStartCommand(intent, flags, startId);
	}
	
	public Boolean insertDevolucoesDB(JSONArray array) {
		Boolean atraso = false;
		try {
			DevolucaoDAO devolucaoDAO = DevolucaoDAO.getInstance(getApplicationContext());
			devolucaoDAO.deleteAll();
			
			for (int i=0; i<array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				Devolucao devolucao = DevolucaoConverter.fromJSON((JSONObject) jsonObject);
				devolucaoDAO.insert(devolucao);
				
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date dataEntrega = new Date(df.parse(devolucao.getDtEntrega()).getTime());
				Date dataAtual = new Date();
				
				if (dataEntrega.before(dataAtual) || dataEntrega.equals(dataAtual)) {
					atraso = true;
				} 
			}
			return atraso;
		} catch(Exception e){
		    e.printStackTrace();
	    }
		return atraso;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
