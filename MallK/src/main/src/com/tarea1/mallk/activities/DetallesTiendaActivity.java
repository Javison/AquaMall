package com.tarea1.mallk.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import java.io.Serializable;
import java.util.List;

public class DetallesTiendaActivity extends FragmentActivity {
    public static int ID_TIENDA = 0;
    Button llamarBtn;
    Button vistaImgBtn;
    Store store;
    TextView nombreTienda;
    TextView direccionTienda;
    TextView horarioTienda;
    TextView emailTienda;
    TextView descripcionTienda;
    TextView telefonoTienda;
    TextView webTienda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Alog.pon("MainActivity/onCreate INICIO");//TRAZA
        super.onCreate(savedInstanceState);
        //Parse
        Parse.initialize(this, "tyGrci26j64kCdgfRIQeUJRepYd5JPL9gEen57aI", "vMzhsd2i7M2hHs7FNnTjbZvS2A5FNWCaCzS6F5zS");

        setContentView(R.layout.activity_detalle_tienda);

        //JGM
        //Asociar variables vista(obj) - code
        this.llamarBtn = (Button) findViewById(R.id.llamarBtn);
        this.vistaImgBtn = (Button) findViewById(R.id.verLocalBtn);
        this.nombreTienda = (TextView) findViewById(R.id.nombreTienda);
        this.direccionTienda = (TextView) findViewById(R.id.direccionTienda);
        this.horarioTienda = (TextView) findViewById(R.id.horarioTienda);
        this.emailTienda = (TextView) findViewById(R.id.emailTienda);
        this.descripcionTienda = (TextView) findViewById(R.id.descripcionTienda);
        this.telefonoTienda = (TextView) findViewById(R.id.telefonoTienda);
        this.webTienda = (TextView) findViewById(R.id.webTienda);

        //recuperar valores
        Bundle bun = getIntent().getExtras();
        this.store = (Store) bun.getSerializable("Store");

        //Id para comentarios
        this.ID_TIENDA = this.store.getIdStore();

        //asignar campos code - vista
        this.nombreTienda.setText(store.getNombre());
        this.direccionTienda.setText("Lugar: " + store.getDireccion());
        this.horarioTienda.setText("Horario: " + store.getHorario());
        this.emailTienda.setText(store.getEmail());
        this.descripcionTienda.setText("Detalles: " + store.getDescripcion());
        this.telefonoTienda.setText("Telefono: " + store.getTelefono());
        this.webTienda.setText(store.getWebSite());

        //Linkify
        Linkify.addLinks(this.webTienda, Linkify.ALL);
        Linkify.addLinks(this.emailTienda, Linkify.ALL);

        //Boton llamar
        llamarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + store.getTelefono()));
                startActivity(i);
            }
        });

        //Boton Ver Foto
        vistaImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(DetallesTiendaActivity.this, ImagenTiendaActivity.class);
                in.putExtra("Store", (Serializable) store);
                //in.putExtra("imgTienda", store.getImgStore());
                startActivity(in);
            }
        });


    }

    /*ACTION BAR*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalle_tienda, menu);
        return true;
    }

    /*ACTION BAR-OPCIONES*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (store != null) {
                    //Crea texto a compartir
                    String txtCompartir = creaTextoCompartir();

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, txtCompartir);
                    intent.setType("text/plain");
                    //da opcion q app usar compatibles al compartir (intent,text titulo compartir)
                    startActivity(Intent.createChooser(intent, getResources().getText(R.string.action_share)));
                }
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
                                Toast.makeText(getApplicationContext(), "Clicks: "+ storeList.get(0).getInt("Clicks"), Toast.LENGTH_SHORT).show();
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

    /*COMPARTIR-Crea String a partir de clase store*/
    public String creaTextoCompartir(){
        StringBuilder sb = new StringBuilder();
        sb.append("Store: ");sb.append(this.store.getNombre());sb.append(" ");
        sb.append("Descripcion: ");sb.append(this.store.getDescripcion());sb.append(" ");
        sb.append("email: ");sb.append(this.store.getEmail());sb.append(" ");
        sb.append("Horario: ");sb.append(this.store.getHorario());sb.append(" ");
        return sb.toString();
    }
}
