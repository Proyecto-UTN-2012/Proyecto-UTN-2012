package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.dibujaconcali;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.ActivityMetric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.Metric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.MetricsService;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

@ContentView(R.layout.dcc_dibujo)
public class DibujoActivity extends RoboActivity {

    @Inject
    private User user;
    
    @Inject
    private MetricsService metricsService;

    @InjectView(R.id.view)
    private FrameLayout view;

    @InjectView(R.id.frame)
    private FrameLayout frame;


    private boolean started;
    private Path path;
    private Paint paint;
    private Bitmap bitmap;
    private List<MyPaint> paths = new ArrayList<DibujoActivity.MyPaint>();
    private List<View> offLights = new ArrayList<View>();
    private List<View> onLights = new ArrayList<View>();
    
    private String drawName;

    @InjectView(R.id.miniature)
    private ImageView miniature;
    private int dibujo;
    private static Map<Integer, Point[]> map = new HashMap<Integer, Point[]>();
    {
        map.put(R.drawable.corazon, new Point[] { new Point(640, 110),
                new Point(420, 20), new Point(200, 200), new Point(460, 490),
                new Point(640, 630), new Point(820, 490), new Point(1080, 200),
                new Point(860, 20)

        });

        map.put(R.drawable.avion, new Point[] { new Point(225, 150),
                new Point(152, 235), new Point(45, 278), new Point(152, 330),
                new Point(291, 313), new Point(380, 373), new Point(172, 513),
                new Point(370, 560), new Point(495, 550), new Point(635, 585),
                new Point(690, 516), new Point(765, 490), new Point(950, 505),
                new Point(1140, 455), new Point(1070, 350),
                new Point(1175, 325), new Point(1138, 290),
                new Point(1070, 185), new Point(710, 270), new Point(450, 260),
                new Point(460, 200), new Point(390, 195) });

        map.put(R.drawable.perro, new Point[] { new Point(515, 205),
                new Point(310, 200), new Point(50, 330), new Point(100, 345),
                new Point(15, 480), new Point(245, 470), new Point(410, 365),
                new Point(460, 375), new Point(520, 455), new Point(470, 500),
                new Point(540, 505), new Point(525, 530), new Point(705, 535),
                new Point(700, 490), new Point(685, 440), new Point(925, 440),
                new Point(910, 465), new Point(825, 505), new Point(900, 515),
                new Point(900, 535), new Point(1075, 535),
                new Point(1045, 440), new Point(1105, 385),
                new Point(1180, 405), new Point(1105, 355),
                new Point(925, 295), new Point(610, 300)

        });

        map.put(R.drawable.auto, new Point[] { new Point(555, 50),
                new Point(235, 260), new Point(85, 490), new Point(85, 585),
                new Point(135, 585), new Point(193, 585), new Point(320, 660),
                new Point(475, 585), new Point(545, 585), new Point(745, 585),
                new Point(800, 585), new Point(955, 660), new Point(1095, 585),
                new Point(1150, 580), new Point(1220, 575),
                new Point(1195, 480), new Point(905, 260)

        });

    }

    private void process(View v) {
        int index = onLights.indexOf(v);
        if (verifyComplete(index))
            return;
        v.setVisibility(View.INVISIBLE);
        View off = offLights.get(index);
        off.setVisibility(View.VISIBLE);

        index = (index + 1) % onLights.size();
        onLights.get(index).setVisibility(View.VISIBLE);
        off = offLights.get(index);
        off.setVisibility(View.INVISIBLE);

    }

    private boolean verifyComplete(int index) {

        if (index == 0 && started) {
            
            Metric metrica = new Metric(user, ActivityMetric.DIBUJA_CON_CALI, getResources().getString(R.string.metric_categoria_dibujaconcali),drawName);
            metricsService.sendMetric(metrica);
            
            finished();
            return true;
        }
        started = true;

        return false;
    }

    private void finished() {
        view.setAlpha(1f);
        onLights.get(0).setVisibility(View.INVISIBLE);
        offLights.get(0).setVisibility(View.VISIBLE);
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final Context context = this;
        Bundle arguments = getIntent().getExtras();
        dibujo = arguments.getInt("dibujo");
        miniature.setImageResource(dibujo);
        
        //TODO: bugfix get names hardcode,
        drawName =  getNameFromDrawable(dibujo);
        
        final MyView canvas = new MyView(this);
        canvas.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        // view.setBackgroundResource(R.drawable.paint);
        canvas.setZOrderOnTop(true);
        canvas.getHolder().setFormat(PixelFormat.TRANSPARENT);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (findViewByPoint((int) event.getRawX(),
                        (int) event.getRawY()) != null) {
                    Log.d("onTouch",
                            "view "
                                    + findViewByPoint((int) event.getRawX(),
                                            (int) event.getRawY()).toString());
                    process(findViewByPoint((int) event.getRawX(),
                            (int) event.getRawY()));

                }
                Log.d("onTouch", "x " + event.getRawX());
                Log.d("onTouch", "y " + event.getY());
                synchronized (canvas.getThread().getHolder()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        path = new Path();
                        path.moveTo(event.getX(), event.getY());
                        path.lineTo(event.getX(), event.getY());
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        // canvas.drawPath(path, paint);
                        // path = new Path();
                        path.lineTo(event.getX(), event.getY());
                        // path.lineTo(event.getX(), event.getY());
                        paths.add(new MyPaint(paint, path));
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        path.lineTo(event.getX(), event.getY());
                        paths.add(new MyPaint(paint, path));
                        // canvas.drawPath(path, paint);
                    }
                    // view.getHolder().unlockCanvasAndPost(canvas);
                    // holder.unlockCanvasAndPost(canvas);
                    return true;

                }
            }
        });

        view.addView(canvas);
        paint = createPaint(0xff000000, 5);
        miniature.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Metric metrica = new Metric(user, ActivityMetric.DIBUJA_CON_CALI, getResources().getString(R.string.metric_categoria_dibujaconcali),"in"+drawName);
                metricsService.sendMetric(metrica);
                
                view.setAlpha(1f);
            }
        });

        view.setBackgroundResource(dibujo);
        ObjectAnimator fade = ObjectAnimator
                .ofFloat(null, "alpha", 1.0f, 0.05f);
        fade.setDuration(2000);
        fade.setStartDelay(2000);
        fade.setInterpolator(new LinearInterpolator());
        fade.setTarget(view);
        fade.start();
        fade.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Point[] points = map.get(dibujo);
                for (Point point : points) {
                    ImageView image = new ImageView(context);
                    image.setLayoutParams(new ViewGroup.LayoutParams(30, 30));
                    image.setBackgroundResource(R.drawable.circle_grey);
                    image.setX((float) point.x);
                    image.setY((float) point.y);
                    // image.setOnTouchListener(listener);
                    frame.addView(image);
                    offLights.add(image);

                    ImageView imageOn = new ImageView(context);
                    imageOn.setLayoutParams(new ViewGroup.LayoutParams(30, 30));
                    imageOn.setBackgroundResource(R.drawable.circle_yellow);
                    imageOn.setX((float) point.x);
                    imageOn.setY((float) point.y);
                    imageOn.setVisibility(View.INVISIBLE);
                    frame.addView(imageOn);
                    onLights.add(imageOn);

                }

                offLights.get(0).setVisibility(View.INVISIBLE);
                onLights.get(0).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

    }

    private String getNameFromDrawable(int drawableKey) {
        switch (drawableKey) {
        case R.drawable.avion:
            return getResources().getString( R.string.metric_categoria_dibujaconcali_avion);
        case R.drawable.auto:
            return getResources().getString(R.string.metric_categoria_dibujaconcali_auto);
        case R.drawable.corazon:
            return getResources().getString(R.string.metric_categoria_dibujaconcali_corazon);
        case R.drawable.perro:
            return getResources().getString(R.string.metric_categoria_dibujaconcali_perro);
        default:
            return "";
        }
    }

    public Paint createPaint(int color, int grosor) {
        paint = new Paint();
        paint.setDither(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(grosor);
        return paint;

    }

    private View findViewByPoint(int x, int y) {

        for (View view : onLights) {
            int locations[] = new int[2];
            view.getLocationOnScreen(locations);
            int vx = locations[0];
            int vy = locations[1];
            int vx2 = vx + view.getWidth();
            int vy2 = vy + view.getHeight();

            if (x >= vx && x <= vx2 && y >= vy && y <= vy2
                    && view.getVisibility() == View.VISIBLE)
                return view;
        }
        return null;
    }

    private class MyView extends SurfaceView implements SurfaceHolder.Callback {
        private MyThread thread;

        public MyView(Context context) {
            super(context);
            this.getHolder().addCallback(this);
            thread = new MyThread(this);
            setFocusable(true);
            thread.start();
        }

        public MyThread getThread() {
            return thread;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d("Starting View", "Iniciando");
            // thread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            thread.end();
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (bitmap == null && view.getWidth() > 0)
                bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                        Config.ARGB_8888);

            Canvas buffer = null;
            if (bitmap != null)
                buffer = new Canvas(bitmap);

            for (MyPaint path : paths) {
                buffer.drawColor(0);
                canvas.drawPath(path.getPath(), path.getPaint());
                if (buffer != null)
                    buffer.drawPath(path.getPath(), path.getPaint());
            }

        }

    }

    private class MyThread extends Thread {
        private boolean stop;
        private final MyView view;

        public MyThread(MyView view) {
            this.view = view;
        }

        public SurfaceHolder getHolder() {
            return view.getHolder();
        }

        public void end() {
            stop = true;
        }

        @Override
        public void run() {
            while (!stop) {
                Canvas canvas = null;
                SurfaceHolder holder = view.getHolder();
                try {
                    canvas = holder.lockCanvas();
                    synchronized (holder) {
                        view.onDraw(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    private class MyPaint {

        final private Paint paint;

        public Paint getPaint() {
            return paint;
        }

        public Path getPath() {
            return path;
        }

        final private Path path;

        public MyPaint(Paint paint, Path path) {
            this.paint = paint;
            this.path = path;

        }

    }

}
