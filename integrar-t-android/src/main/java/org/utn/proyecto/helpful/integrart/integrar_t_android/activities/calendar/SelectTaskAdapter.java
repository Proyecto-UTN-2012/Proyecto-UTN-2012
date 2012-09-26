package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectTaskAdapter extends BaseAdapter {
	private final Context context;
	private final List<TaskType> tasks;
	private final ViewGroup views[];
	public SelectTaskAdapter(Context context, List<TaskType> tasks){
		this.context = context;
		this.tasks = tasks;
		this.views = new ViewGroup[tasks.size()];
	}
	@Override
	public int getCount() {
		return tasks.size();
	}

	@Override
	public Object getItem(int position) {
		return tasks.get(position); 
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View ondView, ViewGroup parent) {
		if(views[position]==null){
			TaskType task = this.tasks.get(position);
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ViewGroup view = (ViewGroup) inflater.inflate(R.layout.image_item_list, null);
			
			ImageView image = (ImageView) view.findViewById(R.id.image);
			image.setImageDrawable(task.getPictogram());			
			
			TextView text = (TextView) view.findViewById(R.id.text);
			text.setText(task.getName());
	
			views[position] = view;
		}
		return views[position];
	}

}
