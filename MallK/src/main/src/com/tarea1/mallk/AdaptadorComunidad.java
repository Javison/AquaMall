package com.tarea1.mallk;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.tarea1.mallk.activities.ComunidadActivity;
import com.tarea1.mallk.data.ImageInstagram;
import com.tarea1.mallk.fragments.ComunidadFragment;
import com.tarea1.mallk.utils.BitmapLRUCache;

import java.util.ArrayList;

/**
 * Created by javigm on 29/11/13.
 */
public class AdaptadorComunidad extends BaseAdapter {

    /*
    int[] arrayStore = new int[]{R.drawable.aqua_1,
            R.drawable.aqua_2,
            R.drawable.aqua_3,
            R.drawable.aqua_4,
            R.drawable.aqua_5,
            R.drawable.aqua_6,
            R.drawable.aqua_7,
            R.drawable.aqua_8,
            R.drawable.aqua_9};

    String[] arrayStoreNames = new String[]{"Vista Lateral", "Imagenes del centro", "En navidad!", "Vista de la entrada",
            "La entrada de noche", "Navidad!", "Vista frontal", "Vista del interior",
            "Mas vistas del interior"};*/

    private Resources resources;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    private ArrayList<ImageInstagram> dataArray;

    public AdaptadorComunidad(Context context, ArrayList<ImageInstagram> dataArray) {
        this.dataArray = dataArray;
        this.inflater = LayoutInflater.from(context);
        this.imageLoader = new ImageLoader(ComunidadFragment.requestQueue,new BitmapLRUCache());
    }

    @Override
    public int getCount() {
        return dataArray.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ImageInstagram current = dataArray.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_comunidad, null);

            holder = new ViewHolder();
            holder.imgStore = (NetworkImageView) convertView.findViewById(R.id.imgStore);
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Fotos del array
        //holder.imgStore.setImageBitmap(decodeSampledBitmapFromResource(resources, arrayStore[position], 400, 200));
        //holder.txtName.setText(arrayStoreNames[position]);

        //Fotos de Instagram
        holder.imgStore.setImageUrl(current.getImgUrl(), imageLoader);
        holder.txtName.setText(current.getUserName());

        return convertView;
    }

    /*ViewHolder*/
    static class ViewHolder {
        public NetworkImageView imgStore;
        public TextView txtName;
    }

    /*RESIZE IMG DEL ARRAY*/
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


}
