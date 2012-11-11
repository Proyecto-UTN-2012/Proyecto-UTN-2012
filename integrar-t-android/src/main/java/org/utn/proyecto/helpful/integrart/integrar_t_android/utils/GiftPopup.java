package org.utn.proyecto.helpful.integrart.integrar_t_android.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class GiftPopup extends Dialog {
	private final MediaPlayer sound;
	private final FrameLayout view;
	
	private final ImageView star1;
	private final ImageView star2;
	private final ImageView star3;
	
	private final GiftAnimation animation;
	
	private final static Map<GiftCount, GiftAnimation> animations = new HashMap<GiftCount, GiftPopup.GiftAnimation>();
	
	{
		animations.put(GiftCount.ONE, new SingleAnimation());
		animations.put(GiftCount.TREE, new ComplexAnimation());
	}
	
	public GiftPopup(Context context, int totalValue) {
		this(context, totalValue, GiftCount.ONE);
	}
	
	public GiftPopup(Context context, int totalValue, GiftCount count) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		this.setContentView(R.layout.gift_dialog);
		view = (FrameLayout)this.findViewById(R.id.view);
		star1 = (ImageView)this.findViewById(R.id.image1);
		star2 = (ImageView)this.findViewById(R.id.image2);
		star3 = (ImageView)this.findViewById(R.id.image3);
		TextView text = (TextView) this.findViewById(R.id.text);
		text.setText("" + totalValue);
		sound = (count==GiftCount.ONE) 
				? MediaPlayer.create(context, R.raw.gift_one)
				: MediaPlayer.create(context, R.raw.gift_many);
		sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.stop();
				mp.prepareAsync();
			}
			
		});	
		animation = animations.get(count);
	}
	
	@Override
	public void show(){
		super.show();
		animation.animate();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				dismiss();
			}
		}, 2500);
	}
	
	interface GiftAnimation{
		public void animate();
	}

	class SingleAnimation implements GiftAnimation{

		@Override
		public void animate() {
			Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_center_anim);
			anim.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					star1.setVisibility(View.INVISIBLE);
					view.setVisibility(View.VISIBLE);
					Animation scale = AnimationUtils.loadAnimation(getContext(), R.anim.star_animation);
					view.startAnimation(scale);
					sound.start();
				}
			});
			star1.setVisibility(View.VISIBLE);
			star1.startAnimation(anim);
		}
		
	}

	class ComplexAnimation implements GiftAnimation{
		
		@Override
		public void animate() {
			Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.right_center_anim);
			Animation anim2 = AnimationUtils.loadAnimation(getContext(), R.anim.top_center_anim);
			Animation anim3 = AnimationUtils.loadAnimation(getContext(), R.anim.left_center_anim);
			anim1.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					star1.setVisibility(View.INVISIBLE);
					star2.setVisibility(View.INVISIBLE);
					star3.setVisibility(View.INVISIBLE);
					view.setVisibility(View.VISIBLE);
					Animation scale = AnimationUtils.loadAnimation(getContext(), R.anim.star_animation);
					view.startAnimation(scale);
					sound.start();
				}
			});
			star1.setVisibility(View.VISIBLE);
			star2.setVisibility(View.VISIBLE);
			star3.setVisibility(View.VISIBLE);
			star1.startAnimation(anim1);
			star2.startAnimation(anim2);
			star3.startAnimation(anim3);
		}
		
	}
}
