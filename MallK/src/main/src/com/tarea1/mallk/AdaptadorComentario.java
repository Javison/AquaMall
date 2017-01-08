package com.tarea1.mallk;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.tarea1.mallk.data.Comment;
import java.util.ArrayList;

/**
 * Created by javigm on 22/11/13.
 * Adaptador listado comentarios
 */
public class AdaptadorComentario extends ArrayAdapter {
    Activity activity;
    ArrayList<Comment> arrayListCom;

    public AdaptadorComentario(Fragment context, ArrayList<Comment> comments) {
        super(context.getActivity(),R.layout.listitem_comentario, comments);
        this.activity = context.getActivity();
        this.arrayListCom = comments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = this.activity.getLayoutInflater();
        View itemView = layoutInflater.inflate(R.layout.listitem_comentario,null);

        TextView textView = (TextView) itemView.findViewById(R.id.tvComentario);
        textView.setText(arrayListCom.get(position).getComentario());

        return itemView;
    }
}
