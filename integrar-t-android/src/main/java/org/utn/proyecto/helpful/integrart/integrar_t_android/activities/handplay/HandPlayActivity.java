package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

import java.util.Date;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.ActivityMetric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.Metric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.MetricsService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.GiftCount;
import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.GiftPopup;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.inject.Inject;

@ContentView(R.layout.hand_play)
public class HandPlayActivity extends RoboActivity implements OnTouchListener{
	private static final String HELP_MESSAGE_KEY = ".handPlay.helpMessage"; 
	private static final String LEVEL_KEY = ".handPlay.level";
	private static final String METRIC_CATEGORY = "category";
	
	@InjectView(R.id.view)
	private FrameLayout view;
	
	@InjectView(R.id.ok)
	private ImageView ok;
	
	@Inject
	private DataStorageService db;
	
	@Inject
	private User user;
	
	@Inject
	private MetricsService metricService;
	
	private HandManager manager;
	
	private int level;
	
	private MediaPlayer successSound;
	
	private long initTime;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(ok==null){
			showUnsupportMessage();
			return;
		}
		if(!db.contain(user.getUserName() + HELP_MESSAGE_KEY)){
			db.put(user.getUserName() + HELP_MESSAGE_KEY, true);
		}
		if(!db.contain(user.getUserName() + LEVEL_KEY)){
			db.put(user.getUserName() + LEVEL_KEY, 1);
		}
		boolean showHelpMessage = db.get(user.getUserName() + HELP_MESSAGE_KEY, Boolean.class);
		level = db.get(user.getUserName() + LEVEL_KEY, Integer.class);
		if(showHelpMessage){
			showHelpMessage();
		}
		manager = new InitialHandManager(this, level);
		view.setOnTouchListener(this);
		
		successSound = MediaPlayer.create(this, R.raw.tada);
		
		initTime = new Date().getTime();
	}
	
	private void showUnsupportMessage() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.handPlayUnsupportTitle);
		builder.setMessage(R.string.handPlayUnsupportMessage);
		builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		Dialog dialog = builder.create();
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
			@Override
			public void onDismiss(DialogInterface dialog) {
				finish();			
			}
			
		});
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.hand_play_menu, menu);
		return true;			
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId() == R.id.showHelp){
			db.put(user.getUserName() + HELP_MESSAGE_KEY, true);
			showHelpMessage();
			return true;
		}
		if(item.getItemId() == R.id.setLevel){
			showLevel();
			return true;
		}
		return false;
	}
	
	public void setManager(HandManager manager){
		this.manager = manager;
	}
	
	private void showLevel(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.handPlaySetLevel);
		builder.setItems(new CharSequence[]{
				getString(R.string.handPlayLevel) + " 1",
				getString(R.string.handPlayLevel) + " 2",
				getString(R.string.handPlayLevel) + " 3",
				getString(R.string.handPlayLevel) + " 4"
				}, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						level = which + 1;
						db.put(user.getUserName() + LEVEL_KEY, level);
						manager.setLevel(level);
					}
				});
		builder.create().show();
	}

	private void showHelpMessage(){
		Dialog dialog = new Dialog(this);
		dialog.setTitle(R.string.handPlayHelpTitle);
		dialog.setContentView(R.layout.hand_play_help_dialog);
		final CheckBox check = (CheckBox)dialog.findViewById(R.id.check);
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				db.put(user.getUserName() + HELP_MESSAGE_KEY, !check.isChecked());
			}
		});
		dialog.show();
	}
	
	public ImageView[] putMarks(FingerPoint[] fingers){
		return putMarks(fingers, null);
	}

	public ImageView[] putMarks(FingerPoint[] fingers, ObjectAnimator[] animators){
		ImageView[] images = new ImageView[fingers.length];
		for(int i=0;i<fingers.length;i++){
			ImageView image = new ImageView(this);
			image.setLayoutParams(new FrameLayout.LayoutParams(100,100));
			image.setImageResource(R.drawable.green_circle);
			image.setX(fingers[i].getX() - 50);
			image.setY(fingers[i].getY() - 50);
			view.addView(image);
			images[i] = image;
			if(animators!=null && animators[i]!=null){
				animators[i].setTarget(image);
				animators[i].start();
			}
		}
		return images;
	}
	
	public void putFingers(FingerPoint[] fingers){
		putFingers(fingers, null);
	}
	
	public void clean(){
		view.removeAllViews();
		ok.setVisibility(View.INVISIBLE);
		view.addView(ok);
	}

	public void putFingers(FingerPoint[] fingers, ObjectAnimator[] animators){
		//view.removeAllViews();
		for(int i=0;i<fingers.length;i++){
			ImageView image = new ImageView(this);
			image.setLayoutParams(new FrameLayout.LayoutParams(100,100));
			image.setImageResource(R.drawable.dedo);
			image.setX(fingers[i].getX() - 50);
			image.setY(fingers[i].getY() - 50);
			view.addView(image);
			
			if(animators!=null && animators[i]!=null){
				animators[i].setTarget(image);
				animators[i].start();
			}
		}
	}
	
	public void success(){
		clean();
		long endTime = new Date().getTime();
		successSound.start();
		user.addGifts(3);
		db.put("currentUser", user);
		new GiftPopup(this, user.getGifts(), GiftCount.THREE).show();
//		Animation anim = AnimationUtils.loadAnimation(this, R.anim.success_animarion);
//		ok.startAnimation(anim);
//		ok.setVisibility(View.VISIBLE);
		metricService.sendMetric(new Metric(user, ActivityMetric.JUGANDO_CON_LA_MANO, METRIC_CATEGORY, "level " + level, (int)(endTime - initTime)));
		initTime = endTime;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(manager == null) return false;
		boolean res = manager.onTouch(v, event);
		manager = manager.getManager();
		return res;
	}
}
