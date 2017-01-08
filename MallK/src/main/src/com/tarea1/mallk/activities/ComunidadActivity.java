package com.tarea1.mallk.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tarea1.mallk.AdaptadorComunidad;
import com.tarea1.mallk.R;
import com.tarea1.mallk.data.ImageInstagram;
import com.tarea1.mallk.fragments.PhotoDialogFragment;
import com.tarea1.mallk.utils.Alog;
import com.tarea1.mallk.utils.InstagramHelper;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by javigm on 30/11/13.
 */
public class ComunidadActivity extends FragmentActivity implements PhotoDialogFragment.NoticeDialogListener {
    private final static int LOAD_IMAGE = 1;
    private final static int USE_CAMERA = 2;
    String photoPath = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Alog.pon("ComunidadActivity/onCreate INICIO");//TRAZA
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comunidad);

    }


    /*ALBUM FOTO*/
    @Override
    public void onDialogAlbumPhotoClick() {
        Alog.pon("ComunidadActivity/onDialogAlbumPhotoClick");//TRAZA
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, LOAD_IMAGE);
    }

    /*CAMARA FOTO*/
    @Override
    public void onDialogTakePhotoClick() {
        Alog.pon("ComunidadActivity/onDialogTakePhotoClick");//TRAZA
        //Nuevo
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = setUpFile();
        photoPath = photo.getAbsolutePath();
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));

        startActivityForResult(intent, USE_CAMERA);

        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, USE_CAMERA);
    }

    /*RESPUESTA SELECT FOTO DE ALBUM*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Alog.pon("ComunidadActivity/onActivityResult");//TRAZA
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    fromGallery(data);
                }
                break;
            case USE_CAMERA:
                if (resultCode == RESULT_OK) {
                    fromCamera();
                }
                break;
        }
    }


    public void fromCamera() {
        Alog.pon("ComunidadActivity/fromCamera");//TRAZA
        //Buscar foto
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(photoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        //Notificar actualizacion
        this.sendBroadcast(mediaScanIntent);
    }

    public void fromGallery(Intent data) {
        Alog.pon("ComunidadActivity/fromGallery");//TRAZA
        if (data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //ImageView imageView = (ImageView) findViewById(R.id.ivUpload);
            //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }


    /*CAMARA / GALERIA FOTOS*/
    /*REAJUSTAR IMG:ANDROID FUNC*/
    public Bitmap resizeBitmap(int targetW, int targetH) {
        Alog.pon("ComunidadActivity/resizeBitmap");//TRAZA
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }

    /*CONFIGURAR PATH*/
    public File setUpFile() {
        Alog.pon("ComunidadActivity/setUpFile");//TRAZA
        File albumDir;
        String albumName = "Aqua";
        //Dependiendo de version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            //Directorio de las imagenes
            albumDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), albumName);
        } else {
            //dcim = directorio imagenes
            albumDir = new File(Environment.getExternalStorageDirectory()
                    + "/dcim/"
                    + albumName);
        }
        //Crear directorios necesarios en camino
        albumDir.mkdirs();
        //Crear fecha para crear directorio
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(Calendar.getInstance().getTime());
        //Nombre del nuevo archivo
        String imageFileName = "IMG_" + timeStamp + ".jpg";
        //Construir archivo
        File imageF = new File(albumDir + "/" + imageFileName);

        Alog.pon("ComunidadActivity/setUpFile:FilePath:" + imageF.getPath());//TRAZA
        return imageF;
    }


}
