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
	
	private static final int SMALL_SIZE = 18; 
	private static final int LARGE_SIZE = 45; 

	public PictogramView(Context context, int columnCount) {
		super(context);
		int size = calculateSize(columnCount);
		init(size);
	}
	
	public PictogramView(Context context, AttributeSet attrs) {
		super(context, attrs);
		int size = calculateSize(2);
		init(size);
	}
	
	public PictogramView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		int size = calculateSize(2);
		init(size);
	}
	
	private int calculateSize(int columnCount){
		int pixel5dp = getPixelDp(5);
		if(columnCount == 2)
			return pixel5dp*SMALL_SIZE;
		return pixel5dp*LARGE_SIZE;	
			
	}
	
	private int getPixelDp(int dps){
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dps, getResources().getDisplayMetrics());
	}
	

	private void init(int size){
		int pixel5dp = getPixelDp(5);
		this.setOrientation(VERTICAL);
		if(pixel5dp*SMALL_SIZE == size)
			prepareSmall(size);
		else
			prepareLarge(size);
	}
	
	private void prepareLarge(int size){
		int pixeldp = getPixelDp(80);
		this.setPadding(pixeldp, pixeldp/10, pixeldp, pixeldp/10);
		imageView = new ImageView(this.getContext());
		imageView.setPadding(0, 0, 0, 0);
		addView(imageView, new LinearLayout.LayoutParams(size,size));
		
		textView = new TextView(this.getContext());
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 22);
		textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
		addView(textView, new LayoutParams(LayoutParams.MATCH_PARENT, (int)(pixeldp/2)));
	}
	
	private void prepareSmall(int size){
		int pixeldp = getPixelDp(30);
		this.setPadding(pixeldp, pixeldp/6, pixeldp, pixeldp/6);
		imageView = new ImageView(this.getContext());
		imageView.setPadding(0, 0, 0, 0);
		addView(imageView, new LinearLayout.LayoutParams(size,size));
		
		textView = new TextView(this.getContext());
		textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
		addView(textView, new LayoutParams(LayoutParams.MATCH_PARENT, (int)(pixeldp*.65)));
	}
	
	public void setPictogram(Pictogram pictogram){
		imageView.setImageDrawable(pictogram.getImage());
		textView.setText(pictogram.getName());
	}

}
