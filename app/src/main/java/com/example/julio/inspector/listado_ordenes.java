package com.example.julio.inspector;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class listado_ordenes extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView lista;
    private TextView titul;
    private List<String> list= new ArrayList<String>();
    private String[] procedimientos;
    private final static String CARPETA_PROCEDIMIENTOS = "/ORDENES DE PUESTO/PROCEDIMIENTOS/";
    private final static String CARPETA_ORDENES_VS = "/ORDENES DE PUESTO/VS/";
    private final static String CARPETA_ORDENES_AUX = "/ORDENES DE PUESTO/AUX/";
    private final static String CARPETA_FORMACION = "/ORDENES DE PUESTO/FORMACION/";
    private String TITULO;
    private Integer t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_ordenes);
        inicio();
    }

    private void inicio(){
        //control lista
        TITULO=getIntent().getStringExtra("TITULO");
        t=getIntent().getIntExtra("tit",1);
        //Toast.makeText(listado_ordenes.this, "TITULO: "+TITULO+" "+t.toString(), Toast.LENGTH_SHORT).show();
        lista=(ListView)findViewById(R.id.LIST_ORDENES);
        titul=(TextView)findViewById(R.id.t_titulo);
        titul.setText(TITULO);
        llenar_array();
    }
    private void llenar_array(){
        //

        String PATH_PROCEDIMIENTOS= Environment.getExternalStorageDirectory().getAbsolutePath() +CARPETA_PROCEDIMIENTOS;
        String PATH_ORDEN_VS= Environment.getExternalStorageDirectory().getAbsolutePath() +CARPETA_ORDENES_VS;
        String PATH_ORDEN_AUX= Environment.getExternalStorageDirectory().getAbsolutePath() +CARPETA_ORDENES_AUX;
        String PATH_FORMACION= Environment.getExternalStorageDirectory().getAbsolutePath() +CARPETA_FORMACION;
        String ruta="";
        switch (t){
            case 1:
                ruta=PATH_ORDEN_VS;
                Toast.makeText(listado_ordenes.this, "TITULO: "+TITULO+" ", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                ruta=PATH_ORDEN_AUX;
                Toast.makeText(listado_ordenes.this, "TITULO: "+TITULO+" ", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                ruta=PATH_PROCEDIMIENTOS;
                Toast.makeText(listado_ordenes.this, "TITULO: "+TITULO+" ", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                ruta=PATH_FORMACION;
                Toast.makeText(listado_ordenes.this, "TITULO: "+TITULO+" ", Toast.LENGTH_SHORT).show();
                break;
        }
        File file = new File(ruta);
        File f[]=file.listFiles();
        int l=f.length;
        for (int i=0;i<l;i++){
            String nombre_procedimiento=f[i].getName();
            String Nom=nombre_procedimiento.replace(".pdf","");
            list.add(Nom);}
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lista.setAdapter(arrayAdapter);
        lista.setOnItemClickListener(this);
    }
    public void FIN_PROCEDIMIENTOS(View v){

        finish();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        switch (parent.getId()) {
            case R.id.LIST_ORDENES:
                String Nombre_fichero=list.get(position)+".pdf";
                String ruta2="";
                switch (t){
                    case 1:
                        ruta2= Environment.getExternalStorageDirectory().getAbsolutePath() +CARPETA_ORDENES_VS+Nombre_fichero;
                        break;
                    case 2:
                        ruta2=Environment.getExternalStorageDirectory().getAbsolutePath() +CARPETA_ORDENES_AUX+Nombre_fichero;
                        break;
                    case 3:
                        ruta2=Environment.getExternalStorageDirectory().getAbsolutePath() +CARPETA_PROCEDIMIENTOS+Nombre_fichero;
                        break;
                    case 4:
                        ruta2=Environment.getExternalStorageDirectory().getAbsolutePath() +CARPETA_FORMACION+Nombre_fichero;
                        break;
                }


                File file = new File(ruta2);


                if (file.exists()) {
                    Intent i= new Intent(this,pdfview.class);
                    i.putExtra("ruta",ruta2);
                    startActivity(i);
                   /** Intent target = new Intent(Intent.ACTION_VIEW);
                    target.setDataAndType(Uri.fromFile(file), "application/pdf");
                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    Intent intent = Intent.createChooser(target, "Open File");
*/
                    //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.pdf");
                    /**Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    //startActivity(intent);
                    try {
                        startActivity(intent);
                        //Toast.makeText(getBaseContext(), "AYUDA....", Toast.LENGTH_LONG).show();
                    } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here, or something
                    }*/
                }else {
                    Toast.makeText(getBaseContext(), "NO ENCUENTRO ARCHIVO", Toast.LENGTH_LONG).show();}

                break;
        }
    }

}
