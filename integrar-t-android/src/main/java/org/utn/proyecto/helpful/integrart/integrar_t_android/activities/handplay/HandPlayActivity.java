package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.inject.Inject;

@ContentView(R.layout.hand_play)
public class HandPlayActivity extends RoboActivity implements OnTouchListener{
	private static final String HELP_MESSAGE_KEY = ".handPlay.helpMessage"; 
	
	@InjectView(R.id.view)
	private FrameLayout view;
	
	@InjectView(R.id.ok)
	private ImageView ok;
	
	@Inject
	private DataStorageService db;
	
	@Inject
	private User user;
	
	private HandManager manager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!db.contain(user.getUserName() + HELP_MESSAGE_KEY)){
			db.put(user.getUserName() + HELP_MESSAGE_KEY, true);
		}
		boolean showHelpMessage = db.get(user.getUserName() + HELP_MESSAGE_KEY, Boolean.class);
		if(showHelpMessage){
			showHelpMessage();
		}
		manager = new InitialHandManager(this);
		view.setOnTouchListener(this);
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
		return false;
	}
	
	public void setManager(HandManager manager){
		this.manager = manager;
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
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.success_animarion);
		ok.startAnimation(anim);
		ok.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(manager == null) return false;
		boolean res = manager.onTouch(v, event);
		manager = manager.getManager();
		return res;
	}
}
