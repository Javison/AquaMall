package com.tarea1.mallk.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tarea1.mallk.R;
import com.tarea1.mallk.fragments.ComunidadFragment;
import com.tarea1.mallk.fragments.ImagePagerFragment;
import com.tarea1.mallk.fragments.PhotoDialogFragment;
import com.tarea1.mallk.fragments.SplashScreenFragment;
import com.tarea1.mallk.fragments.TiendasContentFragment;
import com.tarea1.mallk.utils.Alog;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity {
    private String[] drawerOptions;
    private ListView drawerlist;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Fragment[] fragments = new Fragment[]{
           new ImagePagerFragment(), new TiendasContentFragment()
    };
    private static final long SPLASH_SCREEN_DELAY = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Alog.pon("MainActivity/onCreate INICIO");//TRAZA
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //CODE - VIEW
        drawerlist = (ListView) findViewById(R.id.leftDrawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerOptions = getResources().getStringArray(R.array.drawer_options);

        //ADAPTADOR LISTVIEW
        drawerlist.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawerOptions));

        //ITEM MARCADO
        drawerlist.setItemChecked(0, true);

        //
        drawerlist.setOnItemClickListener(new DrawerItemClicklistener());

        //ACTIONBAR TOGGLE
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_drawer,R.string.drawer_open,R.string.drawer_close){
            public void onDrawerClosed(View view){
                ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            }
            public void onDrawerOpened(View view){
                ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            }
        };
        //indicar a drawer layout mediante listener
        drawerLayout.setDrawerListener(drawerToggle);

        //ACTION BAR
        ActionBar actionBar = getSupportActionBar();
        //Titulo Fragment
        actionBar.setTitle(drawerOptions[0]);
        //Indicar que use el boton
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        //PANTALLA INICIAL
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.contentFrame, fragments[0])
                .add(R.id.contentFrame, fragments[1])
                .hide(fragments[1])
                .commit();
    }

    //GESTION CONTENIDO TABS
    public void setContent(int index) {
        Fragment toHide = null;
        Fragment toShow = null;
        ActionBar actionBar = getSupportActionBar();

        switch (index) {
            case 0:
                toHide = fragments[1];
                toShow = fragments[0];
                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                break;
            case 1:
                toHide = fragments[0];
                toShow = fragments[1];
                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
                break;
        }

        actionBar.setTitle(drawerOptions[index]);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .hide(toHide)
                .show(toShow)
                .commit();

        drawerlist.setItemChecked(index,true);
        drawerLayout.closeDrawer(drawerlist);
    }

    class DrawerItemClicklistener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if("Comunidad".equals(drawerOptions[position])){
                Intent in = new Intent(MainActivity.this, ComunidadActivity.class);
                startActivity(in);
            }else{
                setContent(position);
            }
        }
    }

    /*DRAWER TOGGLE*/
    //Activar drawerToggle
    public boolean onOptionsItemSelected(MenuItem item){
        //validar si item es home
        if(item.getItemId() == android.R.id.home){
            if(drawerLayout.isDrawerOpen(drawerlist)){
                drawerLayout.closeDrawer(drawerlist);
            }else{
                drawerLayout.openDrawer(drawerlist);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration config){
        super.onConfigurationChanged(config);
        drawerToggle.onConfigurationChanged(config);
    }


    /*MENU CABECERA PARA FAV Y SHARE*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
