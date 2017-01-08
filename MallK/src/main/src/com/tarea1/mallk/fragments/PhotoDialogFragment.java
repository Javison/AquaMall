package com.tarea1.mallk.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.tarea1.mallk.R;
import com.tarea1.mallk.utils.Alog;

/**
 * Created by javigm on 29/11/13.
 */
public class PhotoDialogFragment extends DialogFragment{
    NoticeDialogListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (NoticeDialogListener) getActivity();
        } catch (ClassCastException e){
            Log.e("ERROR", Log.getStackTraceString(e));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Crea msg alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Compone msg
        builder.setTitle("Tomar foto de...")
                .setItems(R.array.origen_foto, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which){
                            case 0:
                                Alog.pon("LISTENER");
                                listener.onDialogAlbumPhotoClick();
                                break;
                            case 1:listener.onDialogTakePhotoClick();
                                break;
                        }
                    }
                });

        return builder.create();
    }

    /*Interfaz a implementar en Activity*/
    public interface NoticeDialogListener{
        public void onDialogAlbumPhotoClick();
        public void onDialogTakePhotoClick();
    }

}

