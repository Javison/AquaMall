package com.tarea1.mallk.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tarea1.mallk.AdaptadorComentario;
import com.tarea1.mallk.R;
import com.tarea1.mallk.activities.DetallesTiendaActivity;
import com.tarea1.mallk.activities.ImagenTiendaActivity;
import com.tarea1.mallk.data.Comment;
import com.tarea1.mallk.data.Store;
import com.tarea1.mallk.utils.Alog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by javigm on 21/11/13.
 * Fragment recupera y mapea comentarios
 * lvComentarios
 * etComentario
 * btnEnviar
 * arrayListCom
 *
 */
public class ComentariosFragment extends Fragment {
    private ListView lvComentarios;
    private EditText etComentario;
    private Button btnEnviar;
    ArrayList<Comment> arrayListCom = new ArrayList<Comment>();
    AdaptadorComentario adaptadorComentario;
    private Comment[] commentArray;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comentario, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Leer de assets
        String lectura = leeFromAssets("comentarios.json");
        fromJson(lectura);
        //Nombre para el ListView
        for (Comment comentario : commentArray) {
            if ("DetallesTiendaActivity".equals(getActivity().getClass().getSimpleName())) {
                if ("Store".equals(comentario.getOrigen())) {
                    if (comentario.getIdOrigen() == DetallesTiendaActivity.ID_TIENDA) {
                        Alog.pon("Comentario:"+comentario);
                        arrayListCom.add(comentario);
                    }
                }
            }else if("ImagenTiendaActivity".equals(getActivity().getClass().getSimpleName())){
                if ("Photo".equals(comentario.getOrigen())) {
                    if (comentario.getIdOrigen() == ImagenTiendaActivity.ID_FOTO) {
                        arrayListCom.add(comentario);
                        Alog.pon("Comentario:"+comentario);
                    }
                }
            }
        }

        //anyade comentarios
        //arrayListCom.add(new Comment("Un sitio excelente para el finde"));
        //arrayListCom.add(new Comment("Nos gusto mucho!"));

        //adapter
        adaptadorComentario = new AdaptadorComentario(this, arrayListCom);

        //view-code
        lvComentarios = (ListView) getView().findViewById(R.id.lvComentarios);
        etComentario = (EditText) getView().findViewById(R.id.etComentario);
        btnEnviar = (Button) getView().findViewById(R.id.btnComentario);

        lvComentarios.setAdapter(adaptadorComentario);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comentario = etComentario.getText().toString();
                if (!"".equals(comentario)) {
                    arrayListCom.add(new Comment(comentario));
                    adaptadorComentario.notifyDataSetChanged();
                    etComentario.setText("");
                    Toast.makeText(getActivity(), "Comment enviado!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Escribe algo!", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
            this.commentArray = gson.fromJson(cadena, Comment[].class);
            Alog.pon("GSON:OK Loaded:"+this.commentArray.length);
            //Log.e("GSON - CLASE:", storesArray[0].toString());//TRAZA
        } catch (Exception e) {
            Alog.pon("EXCEPTION:" + e.toString());
            e.printStackTrace();
        }
    }

}
