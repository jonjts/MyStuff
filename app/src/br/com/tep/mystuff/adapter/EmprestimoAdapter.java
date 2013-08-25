package br.com.tep.mystuff.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.tep.mystuff.model.Emprestimo;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EmprestimoAdapter extends BaseAdapter{
	
	private List<Emprestimo> list;
	private Context context;
	private LayoutInflater inflater;	
	
	public EmprestimoAdapter(List<Emprestimo> list, Context context) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Emprestimo getItem(int poisition) {
		return list.get(poisition);
	}

	@Override
	public long getItemId(int position){
		return list.get(position).getId();
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		view = inflater.inflate(br.com.tep.mystuff.R.layout.item_emprestimo, null);
		TextView txtObjeto = (TextView) view.findViewById(br.com.tep.mystuff.R.id.txtObjeto);
		TextView txtData = (TextView) view.findViewById(br.com.tep.mystuff.R.id.txtData);
		TextView txtNotificacao = (TextView) view.findViewById(br.com.tep.mystuff.R.id.txtNotificacao);
		
		Emprestimo emp = list.get(arg0);
		txtObjeto.setText(emp.getObjeto());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		txtData.setText(sdf.format(emp.getDtEntrega()));
		if(emp.getNotificar() == 1){
			txtNotificacao.setText("Notificar");
			txtNotificacao.setTextColor(Color.parseColor("#4682b4"));
		}else{
			txtNotificacao.setText("Não Notificar");
			txtNotificacao.setTextColor(Color.parseColor("#fa8072"));
		}
		return view;
	}

}
