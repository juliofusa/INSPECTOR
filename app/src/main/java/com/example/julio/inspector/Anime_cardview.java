package com.example.julio.inspector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Anime_cardview extends AppCompatActivity {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_cardview);

        // Inicializar Animes


// Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

// Crear un nuevo adaptador
        List listaItemsCursos = getCursorList();
        //adapter = new CartaCursoAdapter(listaItemsCursos, this);
        adapter = new AnimeAdapter(listaItemsCursos);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());

    }
    public  List<Anime> getCursorList(){

        List<Anime>  list= new ArrayList<>();

        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor c= db.rawQuery("SELECT * FROM VISITAPUESTO ", null);


        while (c.moveToNext()){
            Anime Anime = new Anime();

            Anime.setPuesto(c.getString(4));
            Anime.setPhotoId(c.getString(13));
            Anime.setPhotoId2(c.getString(13));
            Anime.setPhotoId3(c.getString(13));
            Anime.setPhotoId4(c.getString(13));
            Anime.setInspector(c.getString(1));
            Anime.setObservaciones(c.getString(18));
            Anime.setFecha(c.getString(2));
            Anime.setHora(c.getString(3));
            Anime.setDesperfectos(c.getString(11));
            Anime.setRiesgos(c.getString(12));
            Anime.setMes(c.getString(28));
            list.add(Anime);
        }

        return list;

    }
}
