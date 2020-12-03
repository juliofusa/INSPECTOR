package com.example.julio.inspector;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;


public class Inspecciones_manuales extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private CheckBox CINTURILLA,CUELLO,HOMBROS,TORSO_ESPALDA,MUSLO_ENTREPIERNAS_PIERNA,NINGUNA;
    private ToggleButton T_CINTURILLA,T_CUELLO,T_HOMBROS,T_TORSO_ESPALDA,T_MUSLO_ENTREPIERNAS_PIERNA,T_empeine_zapato,T_NINGUNA;
    private Spinner INSPECTOR,tip,PUESTO;
    private EditText vs_nombre,fecha,HORA;
    private Integer position_tip,position_ins,position_puesto;
    private String S_PUESTO,S_cinturilla,S_cuello,S_hombros,S_torso_espalda,S_Muslo_entrepierna_pierna,S_ninguna,S_empeine_zapato,S_VS,S_fecha,S_TIP,S_INSPECTOR,S_CORRECTO;
    private TipdbAdapter dbAdapterSituacion,dbADAPTER_TIP,DBADAPT;
    private Cursor cursorListaSituacion,CURSOR_TIP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspecciones_manuales);

        DBADAPT = new TipdbAdapter(this) ;

        DBADAPT.abrir();

        Cursor cursorPuestos=DBADAPT.Lista_Puestos();

        position_tip=getIntent().getIntExtra("position_tip",0);

        position_ins=getIntent().getIntExtra("position_ins",0);

        position_puesto=getIntent().getIntExtra("position_puesto",0);

        final String[] ins=getResources().getStringArray(R.array.INSPECTORES);
        final String[] puesto = getResources().getStringArray(R.array.puestos);
       // ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, ins);
        //ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, puesto);
        SimpleCursorAdapter adapterPuestos = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorPuestos,(new String[] {"puesto"}), new int[] {android.R.id.text1},0);

        CONTROLES();

        dbAdapterSituacion = new TipdbAdapter(this) ;
        dbAdapterSituacion.abrir();
        cursorListaSituacion = dbAdapterSituacion.getnombreCOMPLETO();
        CURSOR_TIP = dbAdapterSituacion.getTIP();

        SimpleCursorAdapter adapterSituacion2 = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,CURSOR_TIP,(new String[] {TipdbAdapter.C_COLUMNA_TIP}), new int[] {android.R.id.text1},0);
        Cursor cursorINSPECTORES = dbAdapterSituacion.getINSPECTOR();

        SimpleCursorAdapter adapterINSPECTORES = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorINSPECTORES,(new String[] {"nombre"}), new int[] {android.R.id.text1},0);



        tip.setAdapter(adapterSituacion2);
        tip.setOnItemSelectedListener(this);
        tip.setSelection(position_tip);

        INSPECTOR.setAdapter(adapterINSPECTORES);
        INSPECTOR.setOnItemSelectedListener(this);
        INSPECTOR.setSelection(position_ins);

        PUESTO.setAdapter(adapterPuestos);
        PUESTO.setOnItemSelectedListener(this);
        PUESTO.setSelection(position_puesto);

        fecha.setText(FECHAconformato());// poner fecha actual
        HORA.setText(HORAconformato());// poner HORA actual
    }

  private void CONTROLES(){


      vs_nombre=(EditText)findViewById(R.id.E_VS);
      fecha=(EditText)findViewById(R.id.fecha);
      HORA=(EditText)findViewById(R.id.HORA_inspeccion);
      T_CINTURILLA=(ToggleButton)findViewById(R.id.t_cinturilla);
      T_CUELLO=(ToggleButton)findViewById(R.id.t_cuello);
      T_HOMBROS=(ToggleButton)findViewById(R.id.t_hombros_brazos);
      T_TORSO_ESPALDA=(ToggleButton)findViewById(R.id.t_torso_espalda);
      T_MUSLO_ENTREPIERNAS_PIERNA=(ToggleButton)findViewById(R.id.t_muslo_entrepierna);
      T_empeine_zapato=(ToggleButton)findViewById(R.id.T_empeine_zapato);
      T_NINGUNA=(ToggleButton)findViewById(R.id.T_NINGUNA);
      tip=(Spinner)findViewById(R.id.spinner_tip);
      INSPECTOR=(Spinner)findViewById(R.id.spinner_INSPECTOR);
      PUESTO=(Spinner)findViewById(R.id.spinner_PUESTO);
      VALORESINICIALES();
  }
  private void VALORESINICIALES(){
      S_cinturilla="BIEN";
      S_cuello="BIEN";
      S_hombros="BIEN";
      S_torso_espalda="BIEN";
      S_Muslo_entrepierna_pierna="BIEN";
      S_empeine_zapato="BIEN";
      S_CORRECTO="SI";
  }
    private void INSERTAR_INSPECCION_MANUAL(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if(db != null){
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("tip", S_TIP);
            nuevoRegistro.put("personalVS",vs_nombre.getText().toString());
            nuevoRegistro.put("fecha", fecha.getText().toString());
            nuevoRegistro.put("CINTURILLA", S_cinturilla);
            nuevoRegistro.put("CUELLO", S_cuello);
            nuevoRegistro.put("HOMBROSYBRAZOS", S_hombros);
            nuevoRegistro.put("TORSOYESPALDA",S_torso_espalda);
            nuevoRegistro.put("MUSLOYENTREPIERNA", S_Muslo_entrepierna_pierna);
            nuevoRegistro.put("empeinezapato", S_empeine_zapato);
            nuevoRegistro.put("INSPECTOR", S_INSPECTOR);
            nuevoRegistro.put("CORRECTO", S_CORRECTO);
            nuevoRegistro.put("HORA", HORA.getText().toString());
            nuevoRegistro.put("PUESTO", S_PUESTO);
            nuevoRegistro.put("ID_DISPOSITIVO", "");
            //Insertamos el registro en la base de datos
            db.insert("inspeccionmanual", null, nuevoRegistro);
            Log.i(this.getClass().toString(), "INSERTADO NUEVO REGISTRO");
            // Cerramos la base de datos
            db.close();

            Toast.makeText(getBaseContext(), "DATOS GUARDADOS ", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    //FECHA Y HORA
    private String FECHAconformato() {
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        return dateString;
    }
    public String HORAconformato() {
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dateString = sdf.format(date);
        return dateString;
    }
    // BOTONES TROOGLEBUTTON
    public void CINTURILLA(View v){
      if(T_CINTURILLA.isChecked() ){S_cinturilla="MAL";S_CORRECTO="NO";T_NINGUNA.setChecked(false);T_NINGUNA.setBackgroundColor(Color.rgb(255, 68, 68));T_CINTURILLA.setBackgroundColor(Color.rgb(255,68,68));}else{VER_CORRECTO();S_cinturilla="BIEN";T_CINTURILLA.setBackgroundColor(Color.rgb(153,204,0));}
  }
    public void CUELLO(View v){
        if(T_CUELLO.isChecked() ){S_CORRECTO="NO";S_cuello="MAL";T_NINGUNA.setChecked(false);T_NINGUNA.setBackgroundColor(Color.rgb(255, 68, 68));T_CUELLO.setBackgroundColor(Color.rgb(255, 68, 68));}else{VER_CORRECTO();S_cuello="BIEN";T_CUELLO.setBackgroundColor(Color.rgb(153,204,0));}
    }
    public void HOMBROS(View v){
        if(T_HOMBROS.isChecked() ){S_CORRECTO="NO";S_hombros="MAL";T_NINGUNA.setChecked(false);T_NINGUNA.setBackgroundColor(Color.rgb(255, 68, 68));T_HOMBROS.setBackgroundColor(Color.rgb(255, 68, 68));}else{VER_CORRECTO();S_hombros="BIEN";T_HOMBROS.setBackgroundColor(Color.rgb(153,204,0));}
    }
    public void TORSO_ESPALDA(View v){
        if(T_TORSO_ESPALDA.isChecked() ){S_CORRECTO="NO";S_torso_espalda="MAL";T_NINGUNA.setChecked(false);T_NINGUNA.setBackgroundColor(Color.rgb(255, 68, 68));T_TORSO_ESPALDA.setBackgroundColor(Color.rgb(255, 68, 68));}else{VER_CORRECTO();S_torso_espalda="BIEN";T_TORSO_ESPALDA.setBackgroundColor(Color.rgb(153,204,0));}
    }
    public void MUSLO_ENTREPIERNAS_PIERNA(View v){
        if(T_MUSLO_ENTREPIERNAS_PIERNA.isChecked() ){S_CORRECTO="NO";S_Muslo_entrepierna_pierna="MAL";T_NINGUNA.setChecked(false);T_NINGUNA.setBackgroundColor(Color.rgb(255, 68, 68));T_MUSLO_ENTREPIERNAS_PIERNA.setBackgroundColor(Color.rgb(255, 68, 68));}else{VER_CORRECTO();S_Muslo_entrepierna_pierna="BIEN";T_MUSLO_ENTREPIERNAS_PIERNA.setBackgroundColor(Color.rgb(153,204,0));}
    }
    public void EMPEINE_ZAPATO(View v){
        if(T_empeine_zapato.isChecked() ){S_CORRECTO="NO";S_empeine_zapato="MAL";T_NINGUNA.setChecked(false);T_NINGUNA.setBackgroundColor(Color.rgb(255, 68, 68));T_empeine_zapato.setBackgroundColor(Color.rgb(255, 68, 68));}else{VER_CORRECTO();S_empeine_zapato="BIEN";T_empeine_zapato.setBackgroundColor(Color.rgb(153,204,0));}
    }
    public void VER_CORRECTO(){
        if(!T_CINTURILLA.isChecked() && !T_CUELLO.isChecked() && !T_HOMBROS.isChecked() && !T_TORSO_ESPALDA.isChecked() && !T_MUSLO_ENTREPIERNAS_PIERNA.isChecked() && !T_empeine_zapato.isChecked()){ S_CORRECTO="SI";}
    }
    public void NINGUNA(View v){
        if(T_NINGUNA.isChecked() ){
            S_CORRECTO="SI";
            T_NINGUNA.setBackgroundColor(Color.rgb(153,204,0));
            T_CINTURILLA.setChecked(false);S_cinturilla="BIEN";T_CINTURILLA.setBackgroundColor(Color.rgb(153,204,0));
            T_CUELLO.setChecked(false);S_cuello="BIEN";T_CUELLO.setBackgroundColor(Color.rgb(153,204,0));
            T_HOMBROS.setChecked(false);S_hombros="BIEN";T_HOMBROS.setBackgroundColor(Color.rgb(153,204,0));
            T_TORSO_ESPALDA.setChecked(false);S_torso_espalda="BIEN";T_TORSO_ESPALDA.setBackgroundColor(Color.rgb(153,204,0));
            T_MUSLO_ENTREPIERNAS_PIERNA.setChecked(false);S_Muslo_entrepierna_pierna="BIEN";T_MUSLO_ENTREPIERNAS_PIERNA.setBackgroundColor(Color.rgb(153,204,0));
            T_empeine_zapato.setChecked(false);S_empeine_zapato="BIEN";T_empeine_zapato.setBackgroundColor(Color.rgb(153,204,0));
        }else{S_CORRECTO="NO";}
    }
    // BOTON VOLVER
    public void volver(View v){
     finish();
 }
    public void GUARDAR_INSPECCION(View v){
        if(!S_TIP.equals("") ){
            INSERTAR_INSPECCION_MANUAL();
            }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inspecciones_manuales, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        switch (parent.getId()) {
            case R.id.spinner_tip:

                BasedbHelper usdbh = new BasedbHelper(this);

                SQLiteDatabase db = usdbh.getReadableDatabase();

                Cursor csor = db.rawQuery("SELECT * FROM VS ", null);

                csor.moveToPosition(position);

                if (csor.getString(2).equals("")){

                    vs_nombre.setText("");
                    S_TIP="";

                     }else{

                    S_TIP=csor.getString(4);//variable texto TIP

                    vs_nombre.setText(csor.getString(2)+" "+csor.getString(3)+", "+csor.getString(1));}
                break;
            case R.id.spinner_INSPECTOR:

                Cursor c2=(Cursor)parent.getItemAtPosition(position);

                S_INSPECTOR=c2.getString(c2.getColumnIndex("nombre"));// VARIABLE TEXTO INSPECTOR


                break;
            case R.id.spinner_PUESTO:
                Cursor c3=(Cursor)parent.getItemAtPosition(position);
                S_PUESTO=c3.getString(c3.getColumnIndex(TipdbAdapter.C_COLUMNA_PUESTO));
                //S_PUESTO=parent.getItemAtPosition(position).toString();// VARIABLE TEXTO puesto

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
