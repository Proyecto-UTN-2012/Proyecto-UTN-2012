package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;

@ContentView(R.layout.task_transition)
public class TaskTransitionActivity extends RoboActivity {
	private Typeface font;
	@InjectView(R.id.currentImage)
	private ImageView currentView;
	
	@InjectView(R.id.currentText)
	private TextView currentName;
	
	@InjectView(R.id.now)
	private TextView nowText;
	
	@InjectView(R.id.nextImage)
	private ImageView nextView;
	
	@InjectView(R.id.nextText)
	private TextView nextName;
	
	@InjectView(R.id.then)
	private TextView thenText;
	
	@InjectView(R.id.transition)
	private ImageView arrows;
	
	@InjectView(R.id.nextLayout)
	private RelativeLayout nextLayout;

	@InjectView(R.id.currentLayout)
	private RelativeLayout currentLayout;
	
	@Inject
	private CalendarDataLoader loader;
	
	private Task currentTask;
	
	private Task nextTask;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		font = Typeface.createFromAsset(getAssets(), "fonts/WC_RoughTrad.ttf");
		Calendar date = (Calendar) Calendar.getInstance().clone();
		Set<Task> set = new HashSet<Task>();
		set.addAll(loader.loadUnrepeatableTaskFromDay(date));
		set.addAll(loader.loadRepeatableTaskFromDay(date));
		List<Task> tasks = new ArrayList<Task>(set);
		Collections.sort(tasks);
		currentTask = loader.loadCurrentTask();
		try{
			nextTask = tasks.get(tasks.indexOf(currentTask)+1);
		}catch(IndexOutOfBoundsException e){
			showNotNextTaskMessage();
			return;
		}
		currentView.setImageDrawable(currentTask.getSmallImage());
		nextView.setImageDrawable(nextTask.getSmallImage());
		
		currentName.setTypeface(font);
		currentName.setText(currentTask.getName());
		
		nowText.setTypeface(font);
		
		nextName.setTypeface(font);
		nextName.setText(nextTask.getName());
		
		thenText.setTypeface(font);
		
		arrows.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				arrows.setRotation(180f);
				Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.rotate_arrows);
				animation.setAnimationListener(new Animation.AnimationListener(){
					@Override
					public void onAnimationEnd(Animation animation) {
						loader.saveCurrentTask(nextTask);
						finish();
					}
					@Override
					public void onAnimationRepeat(Animation animation) {}
					@Override
					public void onAnimationStart(Animation animation) {}
					
				});
				arrows.startAnimation(animation);
				float yCurrent = currentLayout.getY();
				float yNext = nextLayout.getY();
				ObjectAnimator nextAnim = ObjectAnimator.ofFloat(nextLayout, "y", yCurrent);
				nextAnim.setDuration(700);
				nextAnim.setInterpolator(new AccelerateDecelerateInterpolator());
				nextAnim.start();

				ObjectAnimator currentAnim = ObjectAnimator.ofFloat(currentLayout, "y", yNext);
				currentAnim.setDuration(700);
				currentAnim.setInterpolator(new AccelerateDecelerateInterpolator());
				currentAnim.start();
				
			}
		});
	}
	
	private void showNotNextTaskMessage(){
		Toast.makeText(this, getString(R.string.notNextTask), Toast.LENGTH_LONG).show();
		finish();
	}
}
