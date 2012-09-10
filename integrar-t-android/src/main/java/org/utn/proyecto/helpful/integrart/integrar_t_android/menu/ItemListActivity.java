package org.utn.proyecto.helpful.integrart.integrar_t_android.menu;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;

import com.google.inject.Inject;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import android.content.Intent;
import android.os.Bundle;

@ContentView(R.layout.mam_activity_item_list)
public class ItemListActivity extends RoboFragmentActivity
        implements ItemListFragment.Callbacks {

    private boolean mTwoPane;
    private ItemListFragment fragment;
    
    @Inject
    private EventBus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
            fragment = ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list));
            fragment.setActivateOnItemClick(true);
            //fragment.setBus(bus);
        }
    }

    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setBus(bus);
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
