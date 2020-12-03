package com.example.julio.inspector;
//Inspeccion_manual
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JULIO on 12/01/2016.
 */
public class Inspeccion_manual extends CursorAdapter {
    private Integer n=0;
    private String numero;



    public Inspeccion_manual(Context context, Cursor c) {
        super(context, c);
        // TODO Auto-generated constructor stub


    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {


        return LayoutInflater.from(context).inflate(R.layout.lista_inspeccion,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //controles
        TextView txtInspeccion = (TextView) view.findViewById(R.id.lista_texto_manual);



        ImageView Imagefoto = (ImageView) view.findViewById(R.id.lista_foto_manual);

        //setTEXT


        txtInspeccion.setText(cursor.getString(cursor.getColumnIndexOrThrow("noconformidad")));


    Integer RutaFoto;

        //contador convertir a String
        switch (cursor.getString(cursor.getColumnIndexOrThrow("noconformidad"))){

            case "BAJOS":
                RutaFoto=R.drawable.ba_jos;
                break;
            case "CINTURILLA":
                RutaFoto=R.drawable.cin_turilla;
                break;

            default:
                RutaFoto=R.drawable.inf_o;
                break;

        }
        Imagefoto.setImageResource(RutaFoto);





    }


}

