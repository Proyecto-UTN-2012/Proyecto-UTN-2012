package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PictogramView extends LinearLayout {
	private ImageView imageView;
	private TextView textView;
	
	public PictogramView(Context context) {
		super(context);
		init();
	}
	public PictogramView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public PictogramView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		this.setOrientation(VERTICAL);
		int pixel5dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
		this.setPadding(pixel5dp, pixel5dp, pixel5dp, pixel5dp);
		imageView = new ImageView(this.getContext());
		imageView.setPadding(0, 0, 0, 0);
		addView(imageView, new LinearLayout.LayoutParams(pixel5dp*20,pixel5dp*20));
		
		textView = new TextView(this.getContext());
		textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
		addView(textView, new LayoutParams(LayoutParams.MATCH_PARENT, pixel5dp*4));
	}
	
	public void setPictogram(Pictogram pictogram){
		imageView.setImageDrawable(pictogram.getImage());
		textView.setText(pictogram.getName());
	}

}
