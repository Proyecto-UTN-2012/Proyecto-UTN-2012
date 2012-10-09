package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.testactivity;

import java.util.ArrayList;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

@ContentView(R.layout.test_activity)
public class TestActivity extends RoboFragmentActivity{
//	@InjectView(R.id.surface)
//	private SurfaceView view;
//	private SurfaceHolder holder;
	
	@InjectView(R.id.view)
	private FrameLayout layout;
	private MyView view;
	private Path path;
	private Paint paint;
	
	private List<Path> paths = new ArrayList<Path>();
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 view = new MyView(this);
		 view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		 //view.setBackgroundResource(R.drawable.paint);
		 view.setZOrderOnTop(true);
		 view.getHolder().setFormat(PixelFormat.TRANSPARENT);
		 layout.addView(view);
		 paint = new Paint();
		 paint.setDither(true);
		 paint.setColor(0xFFFFFF00);
		 paint.setStyle(Paint.Style.STROKE);
		 paint.setStrokeJoin(Paint.Join.ROUND);
		 paint.setStrokeCap(Paint.Cap.ROUND);
		 paint.setStrokeWidth(30);
//		 holder =  view.getHolder();
//		 holder.addCallback(this);
		 view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				synchronized (view.getThread().getHolder()) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						path = new Path();
						path.moveTo(event.getX(), event.getY());
						path.lineTo(event.getX(), event.getY());
					} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
						//canvas.drawPath(path, paint);
						//path = new Path();
						path.lineTo(event.getX(), event.getY());
						//path.lineTo(event.getX(), event.getY());			
						paths.add(path);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						path.lineTo(event.getX(), event.getY());
						paths.add(path);
						//canvas.drawPath(path, paint);
					}	
					//view.getHolder().unlockCanvasAndPost(canvas);
					//holder.unlockCanvasAndPost(canvas);
					return true;
					
				}
			}
		});
	 }
	 
	private class MyThread extends Thread{
		private boolean stop;
		private final MyView view;
		public MyThread(MyView view){
			this.view = view;
		}
		
		public SurfaceHolder getHolder(){
			return view.getHolder();
		}
		public void end(){
			stop=true;
		}
		@Override
		public void run(){
			while(!stop){
				Canvas canvas = null;
				SurfaceHolder holder = view.getHolder();
				try{
					canvas = holder.lockCanvas();
					synchronized (holder) {
						view.onDraw(canvas);
					}
				}finally{
					if(canvas!=null){
						holder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
	}
		
	private class MyView extends SurfaceView  implements SurfaceHolder.Callback{
		private MyThread thread;
		public MyView(Context context) {
			super(context);
			this.getHolder().addCallback(this);
			thread = new MyThread(this);
			setFocusable(true);
			thread.start();
		}
		
		public MyThread getThread(){
			return thread;
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Log.d("Starting View", "Iniciando");
			//thread.start();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			thread.end();
		}		
		
		@Override
		public void onDraw(Canvas canvas){
			super.onDraw(canvas);
			for(Path path : paths){
				canvas.drawPath(path, paint);
			}
		}
		
	}
}
