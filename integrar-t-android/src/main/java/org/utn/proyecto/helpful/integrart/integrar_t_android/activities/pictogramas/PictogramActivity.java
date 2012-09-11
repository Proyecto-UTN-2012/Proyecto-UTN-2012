package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

import org.utn.proyecto.helpful.integrart.integrar_t_android.MenuActionProvider;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.ChangeColorsEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.pictogramas)
public class PictogramActivity extends RoboFragmentActivity implements EventListener<Void>, OnItemClickListener{
	private static final String START_COLOR = ".pictogramActivity.startColor";
	private static final String END_COLOR = ".pictogramActivity.endColor";
	
	@Inject
	private PictogramLoader loader;
	@Inject
	private PictogramUpdateService updateService;
	@Inject
	private EventBus bus;
	
	@Inject
	private User user;
	
	@Inject
	private DataStorageService db;
	
	@InjectView(R.id.pictogramViewPager)
	private ViewPager pager;
	
	@InjectView(R.id.pictogramList)
	private LinearLayout listView;
	
	@InjectView(R.id.pictogramText)
	private TextView textView;
	
	@InjectView(R.id.pictogramDeleteButton)
	private Button deleteButton;
	
	@InjectView(R.id.pictogramTalkButton)
	private Button talkButton;
	
	private boolean ready;
	
	private int currentLevel;
	
	private Stack<Pictogram> currentPictogrmas = new Stack<Pictogram>();
	
	private int startColor = 0xfff0f6fb;
	private int endColor = 0xffb7dcf4;
	
	private static SparseArray<Class<? extends MenuActionProvider>> menuMap = new SparseArray<Class<? extends MenuActionProvider>>();
	
	{
		menuMap.put(R.id.pictogramLevel, LevelMenuActionProvider.class);
		menuMap.put(R.id.pictogramColor, ColorMenuActionProvider.class);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bus.addEventListener(UpdatePictogramsCompleteEvent.class, this);
		bus.addEventListener(ChangeLevelEvent.class, new ChangeLevelListener());
		bus.addEventListener(ChangeColorsEvent.class, new ChangeColorListener());
		if(db.contain(user.getUserName() + START_COLOR)){
			startColor = db.get(user.getUserName() + START_COLOR, Integer.class);
		}
		if(db.contain(user.getUserName() + END_COLOR)){
			endColor = db.get(user.getUserName() + END_COLOR, Integer.class);
		}
		
		changeBackground(new int[]{startColor, endColor});
		
		updateService.findUpdate();
		do{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}while(!ready);
		List<Pictogram> pictograms = loader.getPictograms();
		pager.setAdapter(new PictogramPageAdapter(getSupportFragmentManager(), pictograms));
		currentLevel = loader.getLevel();
		
		deleteButton.setOnClickListener(new DeletePictogramListener());
		talkButton.setOnClickListener(new TalkListener());
	}
	
	private void changeBackground(int[] colors){
		startColor = colors[0];
		endColor = colors[1];
		GradientDrawable newDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
		newDrawable.setShape(GradientDrawable.RECTANGLE);
		newDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
		pager.setBackgroundDrawable(newDrawable);
		db.put(user.getUserName() + START_COLOR, startColor);
		db.put(user.getUserName() + END_COLOR, endColor);
	}
	
	/**
	 * return false to allow normal menu processing to proceed, true to consume it here.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentLevel", currentLevel);
		MenuActionProvider provider = MenuActionProvider.newInstance(
				menuMap.get(item.getItemId()),
				this, 
				bus, 
				map);
		return provider.execute();
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
		bus.removeEventListener(ChangeLevelEvent.class, new ChangeLevelListener());
		bus.removeEventListener(ChangeColorsEvent.class, new ChangeColorListener());
	}
	 

	@Override
	public void onEvent(Event<Void> event) {
		ready = true;
	}
	
	private void changeLevel(int level){
		currentLevel = level;
		while(!currentPictogrmas.isEmpty()){
			removePictogram();
		}
		List<Pictogram> pictograms = loader.getPictograms(level);
		pager.setAdapter(new PictogramPageAdapter(getSupportFragmentManager(), pictograms));
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
		
		@Override
		public boolean equals(Object o){
			return o instanceof ChangeLevelListener;
		}
		
		@Override
		public int hashCode(){
			return ChangeLevelListener.class.hashCode();
		}
		
	}

	private class ChangeColorListener implements EventListener<int[]>{
		@Override
		public void onEvent(Event<int[]> event) {
			changeBackground(event.getData());
		}
		
		@Override
		public boolean equals(Object o){
			return o instanceof ChangeColorListener;
		}
		
		@Override
		public int hashCode(){
			return ChangeColorListener.class.hashCode();
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
