package com.example.julio.inspector;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JULIO on 10/11/2017.
 */

public class inspeccion_puesto_lista extends CursorAdapter {

    public inspeccion_puesto_lista(Context context, Cursor c) {
        super(context, c);
        // TODO Auto-generated constructor stub


    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(R.layout.ver_listado_de_puestos,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView txtInspecctor = (TextView) view.findViewById(R.id.t_ver_inspector);

        TextView txtpuesto = (TextView) view.findViewById(R.id.t_ver_puesto);
        TextView txtfecha = (TextView) view.findViewById(R.id.t_ver_fecha);
        TextView txthora = (TextView) view.findViewById(R.id.t_ver_hora);
        TextView txtriesgos = (TextView) view.findViewById(R.id.t_ver_riesgos);
        TextView txtdespefectos = (TextView) view.findViewById(R.id.t_ver_desperfectos);
        TextView txtobservaciones = (TextView) view.findViewById(R.id.t_ver_observaciones);

        TextView txtrx  = (TextView) view.findViewById(R.id.t_ver_rx);
        TextView txttrazas = (TextView) view.findViewById(R.id.t_ver_trazas);
        TextView txtbarreras = (TextView) view.findViewById(R.id.t_ver_barreras);
        TextView txtadm = (TextView) view.findViewById(R.id.t_ver_adm);

        TextView txttornos = (TextView) view.findViewById(R.id.t_ver_tornos);
        TextView txtliquidos = (TextView) view.findViewById(R.id.t_ver_liquidos);
        TextView txtzapatos = (TextView) view.findViewById(R.id.t_ver_zapatos);

        ImageView foto1=view.findViewById(R.id.foto1_ver);
        ImageView foto2=view.findViewById(R.id.foto2_ver);
        ImageView foto3=view.findViewById(R.id.foto3_ver);
        ImageView foto4=view.findViewById(R.id.foto4_ver);

        //setTEXT


        txtInspecctor.setText(cursor.getString(cursor.getColumnIndexOrThrow("inspector")));
        txtpuesto.setText(cursor.getString(cursor.getColumnIndexOrThrow("puesto")));
        txtfecha.setText(cursor.getString(cursor.getColumnIndexOrThrow("fecha")));
        txthora.setText(cursor.getString(cursor.getColumnIndexOrThrow("hora")));
        txtdespefectos.setText(cursor.getString(cursor.getColumnIndexOrThrow("incidencia")));
        txtriesgos.setText(cursor.getString(cursor.getColumnIndexOrThrow("riesgos")));
        txtobservaciones.setText(cursor.getString(cursor.getColumnIndexOrThrow("observacion")));

        txtrx.setText(cursor.getString(cursor.getColumnIndexOrThrow("maquinaRX")));
        txttrazas.setText(cursor.getString(cursor.getColumnIndexOrThrow("TORNOS")));
        txtbarreras.setText(cursor.getString(cursor.getColumnIndexOrThrow("BARRERAS")));
        txtadm.setText(cursor.getString(cursor.getColumnIndexOrThrow("adm")));

         txttornos.setText(cursor.getString(cursor.getColumnIndexOrThrow("TORNOS")));
         txtliquidos.setText(cursor.getString(cursor.getColumnIndexOrThrow("detectorLIQUIDOS")));
         txtzapatos.setText(cursor.getString(cursor.getColumnIndexOrThrow("detectoZAPATOS")));

         Bitmap bMap1 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() +TipdbAdapter.R_RUTA_FOTOS + cursor.getString(cursor.getColumnIndexOrThrow("rutaFOTO")));
         Bitmap bMap2 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() +TipdbAdapter.R_RUTA_FOTOS + cursor.getString(cursor.getColumnIndexOrThrow("rutaFOTO2")));
         Bitmap bMap3 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() +TipdbAdapter.R_RUTA_FOTOS + cursor.getString(cursor.getColumnIndexOrThrow("rutaFOTO3")));
         Bitmap bMap4 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() +TipdbAdapter.R_RUTA_FOTOS + cursor.getString(cursor.getColumnIndexOrThrow("rutaFOTO4")));

         foto1.setImageBitmap(bMap1);
         foto2.setImageBitmap(bMap2);
         foto3.setImageBitmap(bMap3);
         foto4.setImageBitmap(bMap4);

    }
}
