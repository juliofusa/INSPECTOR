package com.example.julio.inspector;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by JULIO on 12/01/2016.
 */
public class LenguajeListAdapter extends CursorAdapter {
    private Integer n=0;
    private String numero;



    public LenguajeListAdapter(Context context, Cursor c) {
        super(context, c);
        // TODO Auto-generated constructor stub


    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {


        return LayoutInflater.from(context).inflate(R.layout.lista,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //controles
        TextView txtfiltro = (TextView) view.findViewById(R.id.lista_filtro);

        TextView txtfecha = (TextView) view.findViewById(R.id.lista_fecha);

        TextView txtanotador = (TextView) view.findViewById(R.id.lista_anotador);

        TextView txthora = (TextView) view.findViewById(R.id.lista_hora);

        CheckBox CHECKTRAZAS= (CheckBox) view.findViewById(R.id.LISTA_TRAZAS);

        TextView txtcontador = (TextView) view.findViewById(R.id.lista_contador);

        //setTEXT
        txtfecha.setText(cursor.getString(cursor.getColumnIndexOrThrow("fecha")));

        txtfiltro.setText(cursor.getString(cursor.getColumnIndexOrThrow("FILTRO")));

        txthora.setText(cursor.getString(cursor.getColumnIndexOrThrow("HORA")));

        txtanotador.setText(cursor.getString(cursor.getColumnIndexOrThrow("PERSONA")));
        //contador convertir a String
        switch (cursor.getString(cursor.getColumnIndexOrThrow("FILTRO"))){

            case "OFIC. DIQUE SUR":
                 numero="01";
                break;
            case "OFIC. NORTE":
                 numero="02";
                break;
            case "SALA 1":
                numero="03";
                break;
            case "FILTRO T2 SUR":
                numero="04";
                break;
            case "VEHICULOS SUR":
                numero="05";
                break;
            case "MERCANCIAS SUR":
                numero="06";
                break;
            case "MERCANCIAS NORTE":
                numero="07";
                break;
            case "CLH":
                numero="08";
                break;
            case "ALAMAN":
                numero="09";
                break;
            case "MODULAR":
                numero="10";
                break;
            case "TUNEL CELA":
                numero="11";
                break;
            case "TORRE ACC. SATELITE":
                numero="12";
                break;
            case "FILTRO EMPLEADOS T4 P0":
                numero="13";
                break;
            case "FILTRO VIP":
                numero="14";
                break;
        }

        txtcontador.setText(numero);

        String existe=cursor.getString(cursor.getColumnIndexOrThrow("ETA"));
        if (existe.equals("SI")){
            CHECKTRAZAS.setVisibility(View.VISIBLE);}else {CHECKTRAZAS.setVisibility(View.INVISIBLE);}
    }


}
