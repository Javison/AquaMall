package com.tarea1.mallk.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.tarea1.mallk.activities.DetallesTiendaActivity;
import com.tarea1.mallk.R;
import com.tarea1.mallk.data.Store;
import com.tarea1.mallk.utils.Alog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by javigm on 20/11/13.
 * Listado de las tiendas del Mall
 *
 *
 */
public class TiendasListFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView lvTiendas;
    ArrayList<String> alNombreTiendas;
    Store[] storesArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflar el xml
        View view = inflater.inflate(R.layout.fragment_tiendas_list, container, false);
        //asignar code-vista
        lvTiendas = (ListView) view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Alog.pon("TiendasListFragment/onActivityCreated");//traza

        //Leer de assets
        String lectura = leeFromAssets("tiendas.json");
        fromJson(lectura);
        //Nombre para el ListView
        alNombreTiendas = new ArrayList<String>();
        for (Store tienda : storesArray) {
            alNombreTiendas.add(tienda.getNombre());
        }
        //Adaptador sencillo
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                this.alNombreTiendas);
        lvTiendas.setAdapter(adapter);

        //listener
        lvTiendas.setOnItemClickListener(this);
        registerForContextMenu(lvTiendas);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Store store = storesArray[position];
        Intent in = new Intent(getActivity().getApplicationContext(), DetallesTiendaActivity.class);
        in.putExtra("Store", (Serializable) store);
        startActivity(in);
    }

    /*LECTURA FILE ASSETS*/
    public String leeFromAssets(String fileName) {
        String lectura = "";
        StringBuilder buf = new StringBuilder();
        BufferedReader in;

        InputStream json = null;
        try {
            json = getActivity().getAssets().open(fileName);

            in = new BufferedReader(new InputStreamReader(json));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
            Alog.pon("FILE:" + buf.toString());//TRAZA
            lectura = buf.toString();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lectura;
    }

    /*CONVERSOR JSON-CLASE*/
    public void fromJson(String cadena) {
        Gson gson = new Gson();
        try {
            this.storesArray = gson.fromJson(cadena, Store[].class);
            //Log.e("GSON - CLASE:", storesArray[0].toString());//TRAZA

        } catch (Exception e) {
            Alog.pon("EXCEPTION:" + e.toString());
            e.printStackTrace();
        }
    }
}
