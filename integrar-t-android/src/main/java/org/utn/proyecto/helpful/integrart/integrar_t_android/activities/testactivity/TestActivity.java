package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.testactivity;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@ContentView(R.layout.test_activity)
public class TestActivity extends RoboFragmentActivity {
	
	@InjectView(R.id.testText)
	private TextView text;
	private Editable span;
	private String s;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 s ="Texto muy largo que tiene muchas cosas, mirá mirá tiene una parte de textos amarillos" +
			 		", la cagada es que no le pude poner la sombra, asi como quedamos que ibamos a hacer pero bueno" +
			 		"la parte amarilla está jojojojojo!!"; 
		 text.setText(s);
		 span = text.getEditableText();
		 text.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int index = s.indexOf("amarillos");
				span.setSpan(new TextAppearanceSpan(v.getContext(), R.style.TestText), index, index + 9, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
				index = s.lastIndexOf("amarilla");
				span.setSpan(new TextAppearanceSpan(v.getContext(), R.style.TestText), index, index + 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
				text.setText(span);
			}
		});
		 
	 }
	 
	 
		
}
