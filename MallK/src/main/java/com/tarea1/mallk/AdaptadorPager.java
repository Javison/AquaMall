package com.tarea1.mallk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tarea1.mallk.fragments.ImageFragment;

/**
 * Created by javigm on 24/11/13.
 * Adaptador del ViewPager. Muestra imagenes al inicio de aplicacion
 */
public class AdaptadorPager extends FragmentPagerAdapter {

    private int[] imagesMall = new int[]{
            R.drawable.aqua_4,
            R.drawable.aqua_12,
            R.drawable.aqua_3,
            R.drawable.aqua_11,
            R.drawable.aqua_6,
            R.drawable.aqua_plano_0,
            R.drawable.aqua_plano_1,
            R.drawable.aqua_plano_2,
            R.drawable.aqua_plano_3,
            R.drawable.aqua_plano_4
    };

    public AdaptadorPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();

        //BANDERA EN ARGUMENTO
        bundle.putInt(ImageFragment.RESOURCE, imagesMall[position]);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return imagesMall.length;
    }
}
