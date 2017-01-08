package com.tarea1.mallk.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tarea1.mallk.R;
import com.tarea1.mallk.utils.Alog;

/**
 * Created by javigm on 24/11/13.
 */
public class ImageFragment extends Fragment {
    public final static String RESOURCE = "resource";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Alog.pon("ImageFragment/onCreateView");//TRAZA
        //Inflar xml
        View view = inflater.inflate(R.layout.fragment_image,null);

        //obtner args
        ImageView imageView = (ImageView) view.findViewById(R.id.ivImage);
        Bundle bundle = getArguments();

        imageView.setImageResource(bundle.getInt(RESOURCE));

        return view;
    }
}
