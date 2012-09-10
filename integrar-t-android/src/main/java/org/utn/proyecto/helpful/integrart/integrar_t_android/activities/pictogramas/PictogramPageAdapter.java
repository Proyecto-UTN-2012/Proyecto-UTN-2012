package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import java.util.ArrayList;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class PictogramPageAdapter extends FragmentPagerAdapter {
	private final List<List<Pictogram>> pagesList;
	public PictogramPageAdapter(FragmentManager fm, List<Pictogram> pictograms) {
		super(fm);
		this.pagesList = buildPagesList(pictograms);
	}
	
	private List<List<Pictogram>> buildPagesList(List<Pictogram> pictograms){
		int i=0;
		List<List<Pictogram>> pages = new ArrayList<List<Pictogram>>();
		List<Pictogram> list = null;
		for(Pictogram pictogram : pictograms){
			if((i%6) == 0){
				list = new ArrayList<Pictogram>();
				pages.add(list);
			}
			list.add(pictogram);
			i++;
		}
		return pages;
	}

	@Override
	public Fragment getItem(int position) {
		return PictogramFragment.newInstance(pagesList.get(position));
	}

	@Override
	public int getCount() {
		return pagesList.size();
	}
	
	public static class PictogramFragment extends Fragment{
		private List<Pictogram> pictograms;
        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static PictogramFragment newInstance(List<Pictogram> pictograms) {
            PictogramFragment f = new PictogramFragment();
            f.pictograms = pictograms;
            return f;
        }
	
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.pictogram_page, container, false);
            GridView grid = (GridView) view.findViewById(R.id.pictogramGrid);
            PictogramActivity activity = (PictogramActivity) getActivity();
            grid.setAdapter(new PictogramGridAdapter(activity, pictograms));
            grid.setOnItemClickListener(activity);
            return view;
        }
	}
}
