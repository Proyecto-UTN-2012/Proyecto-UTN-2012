package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class PictogramGridAdapter extends BaseAdapter {
	private final Context context;
	private final List<Pictogram> pictograms;
	
	public PictogramGridAdapter(Context context, List<Pictogram> pictograms){
		this.pictograms = pictograms;
		this.context = context;
	}

	@Override
	public int getCount() {
		return pictograms.size();
	}

	@Override
	public Object getItem(int position) {
		return pictograms.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int numCollumns = ((GridView)parent).getNumColumns();
		PictogramView view = new PictogramView(context, numCollumns);
		view.setPictogram(this.pictograms.get(position));
		return view;
	}

}
