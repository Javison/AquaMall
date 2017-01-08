package com.tarea1.mallk.fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tarea1.mallk.AdaptadorComunidad;
import com.tarea1.mallk.R;
import com.tarea1.mallk.activities.ComunidadActivity;
import com.tarea1.mallk.data.ImageInstagram;
import com.tarea1.mallk.utils.Alog;
import com.tarea1.mallk.utils.InstagramHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by javigm on 24/11/13.
 */
public class ComunidadFragment extends Fragment implements View.OnClickListener {
    ImageButton imageButton;

    //Adaptador imagenInstagram
    ArrayList<ImageInstagram> dataArray;
    AdaptadorComunidad adapter;

    //API INSTAGRAM
    public ProgressBar progressBar;
    public static RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Alog.pon("ComunidadFragment/onCreateView");//traza
        //inflar
        View view = inflater.inflate(R.layout.fragment_comunidad, container, false);

        //Descargar datos de Instagram al abrir ventana
        //VOLLEY
        requestQueue = Volley.newRequestQueue(getActivity());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Alog.pon("ComunidadFragment/onActivityCreated");//traza
        super.onActivityCreated(savedInstanceState);

        //code - view
        ListView listView = (ListView) getActivity().findViewById(R.id.lvComunidadImg);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        imageButton = (ImageButton) getActivity().findViewById(R.id.ibFoto);

        //Adaptador
        dataArray = new ArrayList<ImageInstagram>();
        adapter = new AdaptadorComunidad(getActivity().getApplicationContext(), dataArray);
        listView.setAdapter(adapter);

        //Async call
        APICall();

        imageButton.setOnClickListener(this);
    }

    //Mostrar el dialogo al pulsar el boton
    @Override
    public void onClick(View v) {
        Alog.pon("ComunidadFragment/onClick");//traza
        new PhotoDialogFragment().show(getActivity().getSupportFragmentManager(), "");
    }

    /*Difine peticion volley*/
    private void APICall() {
        Alog.pon("ComunidadFragment/APICall");//traza
        String url = InstagramHelper.getRecentUrl("valencia");
        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //Define listener
        //FIXME
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            /*CALLBACK*/
            @Override
            public void onResponse(JSONObject response) {
                //CUANDO TERMINA
                //Deshabilitar boton actualizar
                Alog.pon("ComunidadFragment/APICall");//traza
                if (getActivity() != null) {
                    getActivity().findViewById(R.id.progressBar).setVisibility(View.GONE);
                    getActivity().findViewById(R.id.lvComunidadImg).setVisibility(View.VISIBLE);
                }

                Alog.pon("ComunidadFragment/APICall/response:" + response.toString());//traza

                JSONArray data;

                try {
                    data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject element = data.getJSONObject(i);
                        String type = element.getString("type");
                        if ("image".equals(type)) {
                            //usuario/imagnees/resolStandard
                            JSONObject user = element.getJSONObject("user");
                            JSONObject images = element.getJSONObject("images");
                            JSONObject standardResolution = images.getJSONObject("standard_resolution");

                            String userName = user.getString("username");
                            String imgUrl = standardResolution.getString("url");

                            ImageInstagram image = new ImageInstagram();
                            image.setImgUrl(imgUrl);
                            image.setUserName(userName);

                            dataArray.add(image);
                        }
                    }

                    adapter.notifyDataSetChanged();
                    //showNotification();

                } catch (JSONException e) {
                    Log.e("ERROR", Log.getStackTraceString(e));
                }
            }
        };

        //PETICION
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, null);
        requestQueue.add(request);

    }

    /*NOTIFICACION BAR-DATOS CARGADOS*/
    public void showNotification() {
        Alog.pon("ComunidadFragment/showNotification");//traza
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getResources().getString(R.string.txt_notification_title))
                        .setContentText(getResources().getString(R.string.txt_notification_subtitle));

        //Intent resultIntent = new Intent(this, CameraActivity.class);
        Intent resultIntent = new Intent(getActivity(), ComunidadActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());

        //stackBuilder.addParentStack(CameraActivity.class);
        stackBuilder.addParentStack(ComunidadActivity.class);

        stackBuilder.addNextIntent(resultIntent);

        //Se ejecuta hasta q haga click
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        //mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        mNotificationManager.notify(1, mBuilder.build());
    }

}
