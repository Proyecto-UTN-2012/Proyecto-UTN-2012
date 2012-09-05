package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.pictogramas)
public class PictogramActivity extends RoboActivity implements EventListener<Void>, OnItemClickListener{
	@Inject
	private PictogramLoader loader;
	@Inject
	private PictogramUpdateService updateService;
	@Inject
	private EventBus bus;
	
	@InjectView(R.id.pictogramGrid)
	private GridView grid;
	
	@InjectView(R.id.pictogramList)
	private LinearLayout listView;
	
	@InjectView(R.id.pictogramText)
	private TextView textView;
	
	@InjectView(R.id.pictogramDeleteButton)
	private Button deleteButton;
	
	@InjectView(R.id.pictogramTalkButton)
	private Button talkButton;
	
	private boolean ready;
	
	private Stack<Pictogram> currentPictogrmas = new Stack<Pictogram>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bus.addEventListener(UpdatePictogramsCompleteEvent.class, this);
		bus.addEventListener(ChangeLevelEvent.class, new ChangeLevelListener());
		updateService.findUpdate();
		do{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}while(!ready);
		List<Pictogram> pictograms = loader.getPictograms();
		grid.setAdapter(new PictogramAdapter(this, pictograms));
		grid.setOnItemClickListener(this);
		
		deleteButton.setOnClickListener(new DeletePictogramListener());
		talkButton.setOnClickListener(new TalkListener());
	}
	
	/**
	 * return false to allow normal menu processing to proceed, true to consume it here.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		LevelActionProvider provider = new LevelActionProvider(this, bus);
		provider.onPerformDefaultAction();
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.pictogram_menu, menu);
		//View view = menu.findItem(R.id.pictogramLevel).getActionView();
		return true;
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		bus.removeEventListener(UpdatePictogramsCompleteEvent.class, this);
	}
	 

	@Override
	public void onEvent(Event<Void> event) {
		ready = true;
	}
	
	private void changeLevel(int level){
		while(!currentPictogrmas.isEmpty()){
			removePictogram();
		}
		List<Pictogram> pictograms = loader.getPictograms(level);
		grid.setAdapter(new PictogramAdapter(this, pictograms));
	}
	
	private void talk(){
		Queue<Pictogram> queue = new ArrayBlockingQueue<Pictogram>(currentPictogrmas.capacity());
		queue.addAll(currentPictogrmas);
		talkNext(queue);
	}
	
	private void talkNext(final Queue<Pictogram> queue){
		if(queue.isEmpty()) return;
		Pictogram pictogram = queue.poll();
		pictogram.getSound().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.setOnCompletionListener(null);
				talkNext(queue);
			}
		});
		pictogram.getSound().start();
	}
	
	private void removePictogram(){
		if(currentPictogrmas.isEmpty()) return;
		currentPictogrmas.pop();
		removeFromListView();
		changeText();
	}
	
	private void removeFromListView(){
		listView.removeViewAt(listView.getChildCount()-1);
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		Pictogram pictogram = (Pictogram) adapter.getItemAtPosition(position);
		currentPictogrmas.push(pictogram);
		runSound(pictogram);
		addToListView(pictogram);
		changeText();
	}
	
	private void changeText(){
		StringBuffer text = new StringBuffer();
		for(Pictogram pictogram : currentPictogrmas){
			text.append(pictogram.getName() + " ");
		}
		textView.setText(text);
	}
	
	private void addToListView(Pictogram pictogram){
		ImageView view = new ImageView(this);
		view.setImageDrawable(pictogram.getImage());
		listView.addView(view);
	}
	
	private void runSound(final Pictogram pictogram){
		MediaPlayer sound = pictogram.getSound();
		if(pictogram.isReady()){
			sound.start();
			return;
		}
		sound.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			public void onPrepared(MediaPlayer mp) {
				mp.start();
				pictogram.setReady();
			}
		});
		try {
			sound.prepare();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private class ChangeLevelListener implements EventListener<Integer>{
		@Override
		public void onEvent(Event<Integer> event) {
			changeLevel(event.getData());
		}
		
	}
	
	private class PictogramAdapter extends BaseAdapter{
		private final Context context;
		private final List<Pictogram> pictograms;
		
		public PictogramAdapter(Context context, List<Pictogram> pictograms){
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
	
	private class DeletePictogramListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			removePictogram();
		}
	}
	
	private class TalkListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			talk();
		}
		
	}
}
