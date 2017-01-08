package com.tarea1.mallk.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tarea1.mallk.R;
import com.tarea1.mallk.data.Store;
import com.tarea1.mallk.utils.Alog;

import java.util.List;

public class ImagenTiendaActivity extends FragmentActivity {
    ImageView imageView;
    Store store;
    public static int ID_FOTO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Alog.pon("ImagenTiendaActivity/onCreate INICIO");//TRAZA
        super.onCreate(savedInstanceState);
        //Parse
        Parse.initialize(this, "tyGrci26j64kCdgfRIQeUJRepYd5JPL9gEen57aI", "vMzhsd2i7M2hHs7FNnTjbZvS2A5FNWCaCzS6F5zS");

        setContentView(R.layout.activity_imagen_local);

        //asociar vista(obj) - code
        this.imageView = (ImageView) findViewById(R.id.imgTienda);

        //recuperar valores
        Bundle bun = getIntent().getExtras();
        this.store = (Store) bun.getSerializable("Store");

        //Id para comentarios
        this.ID_FOTO = this.store.getIdStore();

        //dar valor
        int id = getResources().getIdentifier(this.store.getImgStore(), "drawable", getPackageName());
        this.imageView.setImageResource(id);

    }


    /*ACTION BAR*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.imagen_local, menu);
        return true;
    }

    /*ACTION BAR-OPCIONES*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/" + this.store.getImgStore());
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/jpg");
                //Compartir
                //da opcion q app usar compatibles al compartir (intent,text titulo compartir)
                startActivity(Intent.createChooser(intent, "Compartir imagen"));
                return true;
            case R.id.action_fav:
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Favoritos");
                query.whereEqualTo("Nombre", store.getNombre());
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> storeList, ParseException e) {
                        if (e == null) {
                            Alog.pon("Encontrada en Parse");
                            if (storeList.size() > 0) {
                                storeList.get(0).increment("Clicks");
                                storeList.get(0).saveInBackground();
                                Toast.makeText(getApplicationContext(), "Clicks: " + storeList.get(0).getInt("Clicks"), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Alog.pon("Tienda no encontrada en Parse");
                        }
                    }
                });

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
