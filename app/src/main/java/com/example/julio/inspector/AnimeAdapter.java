package com.example.julio.inspector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JULIO on 14/11/2017.
 */

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.ViewHolder> {

    private List<Anime> userModelList;

    public AnimeAdapter(List<Anime> userModelList) {
        this.userModelList = userModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = userModelList.get(position).getPuesto();
        String s_inspector = userModelList.get(position).getInspector();
        String s_observacion = userModelList.get(position).getObservaciones();
        String s_deperfectos = userModelList.get(position).getDesperfectos();
        String s_riesgos = userModelList.get(position).getRiesgos();
        String s_fecha = userModelList.get(position).getFecha();
        String s_hora = userModelList.get(position).getHora();
        String foto1 = userModelList.get(position).photoId();
        String S_mes=userModelList.get(position).getMes();
        holder.puesto.setText(name);
        holder.inspector.setText(s_inspector);
        holder.observacion.setText(s_observacion);
        holder.fecha.setText(s_fecha);
        holder.hora.setText(s_hora);
        holder.desperfectos.setText(s_deperfectos);
        holder.riesgos.setText(s_riesgos);
        holder.MES.setText(S_mes);

        Bitmap bMap1 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() +TipdbAdapter.R_RUTA_FOTOS +foto1);

        holder.personPhoto.setImageBitmap(bMap1);
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView puesto,inspector,fecha,hora,observacion,riesgos,desperfectos,MES;
        ImageView personPhoto,personPhoto2personPhoto3,personPhoto4;
        public ViewHolder(View v) {
            super(v);
            puesto = (TextView) v.findViewById(R.id.textUserName);
            inspector = (TextView) v.findViewById(R.id.card_puesto_inspector);
            riesgos = (TextView) v.findViewById(R.id.card_puesto_riesgos);
            desperfectos = (TextView) v.findViewById(R.id.card_puesto_deperfectos);
            fecha = (TextView) v.findViewById(R.id.card_puesto_fecha);
            hora = (TextView) v.findViewById(R.id.card_puesto_hora);
            MES= (TextView) v.findViewById(R.id.card_puesto_MES);
            observacion = (TextView) v.findViewById(R.id.card_puesto_observacion);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

}
