package org.utn.proyecto.helpful.integrart.integrar_t_android.utils;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public abstract class SixItemPaginatorAdapter<T> extends FragmentPagerAdapter {
	protected final List<List<T>> pagesList;
	public SixItemPaginatorAdapter(FragmentManager fm, List<T> items) {
		super(fm);
		this.pagesList = buildPagesList(items);
	}
	private List<List<T>> buildPagesList(List<T> items){
		int i=0;
		List<List<T>> pages = new ArrayList<List<T>>();
		List<T> list = null;
		for(T item : items){
			if((i%6) == 0){
				list = new ArrayList<T>();
				pages.add(list);
			}
			list.add(item);
			i++;
		}
		return pages;
	}


	@Override
	abstract public Fragment getItem(int arg0); 
	
	@Override
	public int getCount() {
		return pagesList.size();
	}

	
}
