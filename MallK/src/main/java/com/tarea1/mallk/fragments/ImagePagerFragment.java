package com.tarea1.mallk.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarea1.mallk.AdaptadorPager;
import com.tarea1.mallk.R;
import com.tarea1.mallk.ZoomOutPageTransformer;

/**
 * Created by javigm on 24/11/13.
 */
public class ImagePagerFragment extends Fragment {
    ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //INFLAR
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);

        //VISTA - CODE
        viewPager = (ViewPager) view.findViewById(R.id.pager);

        //ANIMACION
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // FlagFragment dentro de countryFlagFragment dentro de actividad
        AdaptadorPager adapter = new AdaptadorPager(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }
}