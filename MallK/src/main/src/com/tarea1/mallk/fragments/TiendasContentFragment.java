package com.tarea1.mallk.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarea1.mallk.activities.MainActivity;
import com.tarea1.mallk.R;

/**
 * Created by javigm on 24/11/13.
 */
public class TiendasContentFragment extends Fragment implements ActionBar.TabListener{

    Fragment[] fragments = new Fragment[]{new TiendasListFragment(),new MapaTiendasFragment()};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tiendas_content,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //PESTANAS
        final ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(
                actionBar.newTab()
                        .setText("TIENDAS")
                        .setTabListener(this));

        actionBar.addTab(
                actionBar.newTab()
                        .setText("MAPA")
                        .setTabListener(this));
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().add(R.id.container, fragments[0]).add(R.id.container, fragments[1]).commit();

    }

    /*PESTANAS*/
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        //funcion
        setContent(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    //gestion contenido pestana
    public void setContent(int tab) {
        Fragment toHide = null;
        Fragment toShow = null;
        switch (tab) {
            case 0:
                toHide = fragments[1];
                toShow = fragments[0];
                break;
            case 1:
                toHide = fragments[0];
                toShow = fragments[1];
                break;
        }

        FragmentManager manager = getActivity().getSupportFragmentManager();

        manager.beginTransaction()
                .hide(toHide)
                .show(toShow)
                .commit();
    }

}
