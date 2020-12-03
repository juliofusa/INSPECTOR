package com.example.julio.inspector;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.camera2.CameraCharacteristics;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class MENU_TEST extends AppCompatActivity implements OnItemSelectedListener {

    private Spinner TEST_FORMACION,NOMBRE_VS,NOMBRE_AUX,NOMBRE_FORMADOR;
    private Button VER_PDFS,VS,AUX,REINICIO;
    private ImageButton INICIO_TEST,INFORMACION_TEST;
    private TipdbAdapter dbAdapterSituacion;
    private String NOMBRE_TEST="",NOMBRE_PERSONA="",TIP,FORMADOR="";
    private TextView INFO;
    private Boolean ver,tipo_personal=false;
    private TextView TEXT_FORMADOR,TEXT_TIPO_TEXT,TEXT_PERSONAL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__test);

        INICIALIZAR();

    }



    //BOTONES
    public void INICIAR_TEST(View view){

        if (NOMBRE_PERSONA.length()>=3 && exite_aprobado()==false){
            LANZAR_TEST();
        }else{DIALOGO_ALERTA();}

    }
    // SPINNER
    private void LLENAR_SPINNER(){
        dbAdapterSituacion = new TipdbAdapter(this) ;
        dbAdapterSituacion.abrir();
        // tres cursores
        Cursor cursorListaSituacion = dbAdapterSituacion.apellidosNOMBRE();

        Cursor cursorFORMADORES = dbAdapterSituacion.getFORMADORES();

        Cursor cursor_AUX=dbAdapterSituacion.NOMBRE_AUX();
        // spiner vs

        SimpleCursorAdapter adapterSituacion = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorListaSituacion,(new String[] {"nombrecompleto"}), new int[] {android.R.id.text1},0);

        adapterSituacion.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        SimpleCursorAdapter adapterAUX = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursor_AUX,(new String[] {"nombrecompleto"}), new int[] {android.R.id.text1},0);

        adapterAUX.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        // spiner formacion
        //SimpleCursorAdapter TIPOS_FORMACION = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorTIPOS_FORMACION,(new String[] {"NUMERO"}), new int[] {android.R.id.text1},0);

        // TIPOS_FORMACION.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        if (tipo_personal==true){

            NOMBRE_VS.setAdapter(adapterSituacion);

            final String[] LISTADO_TEST_vs = getResources().getStringArray(R.array.TEST_VS);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.custom_spinner_item,LISTADO_TEST_vs);

            //ArrayAdapter<String> adaptador_TEST_vs = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, LISTADO_TEST_vs);

            TEST_FORMACION.setAdapter(adapter);

        }else{

            final String[] NOMBRES_AUX = getResources().getStringArray(R.array.AUXILIARES);

            ArrayAdapter<String> adaptador_AUXILIARES = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, NOMBRES_AUX);

            NOMBRE_VS.setAdapter(adapterAUX);

            final String[] LISTADO_TEST_aux = getResources().getStringArray(R.array.TEST_AUX);

            ArrayAdapter<String> adaptador_TEST_aux = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, LISTADO_TEST_aux);

            TEST_FORMACION.setAdapter(adaptador_TEST_aux);
        }


        // spiner formador

        SimpleCursorAdapter adapterINSPECTOR = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorFORMADORES,(new String[] {"nombre"}), new int[] {android.R.id.text1},0);

        adapterINSPECTOR.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        NOMBRE_FORMADOR.setAdapter(adapterINSPECTOR);

        // Listener
        NOMBRE_FORMADOR.setOnItemSelectedListener(this);

        NOMBRE_VS.setOnItemSelectedListener(this);

        TEST_FORMACION.setOnItemSelectedListener(this);

    }
    // abrir pdf de las modulos de formacion
    public void VER_PDF(View view){
        String Nombre_fichero="";
        String ruta="";
        switch (NOMBRE_TEST){
            case "INSPECCIONES MANUALES":
                Nombre_fichero=TipdbAdapter.N_FICHERO_INSPECION_MANUAL;
                break;
            case "CONTROL PORTON APM":
                Nombre_fichero=TipdbAdapter.N_FICHERO_APM;
                break;
            case "INSPECCIONES CARRITOS LIMPIEZA":
                Nombre_fichero=TipdbAdapter.N_FICHERO_CARROS_LIMPIEZA;
                break;
            case "UTILIZACION EQUIPOS ETD":
                Nombre_fichero=TipdbAdapter.N_FICHERO_ETD;
                break;
            case "REGISTRO DE SEGURIDAD EN AERONAVES":
                Nombre_fichero=TipdbAdapter.N_FICHERO_REGISTRO;
                break;
            case "REGISTRO DE SEGURIDAD EN BOEING 787-900":
                //Nombre_fichero=TipdbAdapter.N_FICHERO_REGISTRO1;
                break;
            case "REGISTRO DE SEGURIDAD EN BOEING 767-300":
               // Nombre_fichero=TipdbAdapter.N_FICHERO_REGISTRO2;
            case "SUMINISTROS AEROPUERTO":
                Nombre_fichero=TipdbAdapter.N_FICHERO_SUMINISTROS;
                break;
            case "ARTEFACTOS EXPLOSIVOS LAMINADOS":
                Nombre_fichero=TipdbAdapter.N_FICHERO_A_E_LAMINADOS;
                break;
        }



        ruta= Environment.getExternalStorageDirectory().getAbsolutePath() +TipdbAdapter.R_RUTA_FORMACION+Nombre_fichero;
        File file = new File(ruta);


        if (file.exists()) {
            Intent i= new Intent(this,pdfview.class);
            i.putExtra("ruta",ruta);
            startActivity(i);
    }}
    // BOTON INFORMACION
    public void INFORMACION_FORMACION(View view){
        if (ver==false){
            INFO.setVisibility(View.VISIBLE);
            INFO.setBackgroundColor(Color.WHITE);
            INFO.setText(R.string.infor_formacion);
            ver=true;
        }else{
            INFO.setVisibility(View.INVISIBLE);
            ver=false;}
    }
    // BOTON VS
    public void VS(View view){

        tipo_personal=true;

        Ver_controles_vs(4);

        ver_ocultar_controles(0);

        LLENAR_SPINNER();
    }

    private void Ver_controles_vs(Integer VISIBILIDAD){

        VS.setVisibility(VISIBILIDAD);

        AUX.setVisibility(VISIBILIDAD);

    }

    private void ver_ocultar_controles(Integer VISIBILIDAD){

        REINICIO.setVisibility(VISIBILIDAD);

        TEST_FORMACION.setVisibility(VISIBILIDAD);

        NOMBRE_VS.setVisibility(VISIBILIDAD);

        NOMBRE_FORMADOR.setVisibility(VISIBILIDAD);

        TEXT_FORMADOR.setVisibility(VISIBILIDAD);

        TEXT_TIPO_TEXT.setVisibility(VISIBILIDAD);

        TEXT_PERSONAL.setVisibility(VISIBILIDAD);

        INFORMACION_TEST.setVisibility(VISIBILIDAD);

        if (tipo_personal==true){

            TEXT_PERSONAL.setText(getResources().getString(R.string.Etiqueta_VS));
            TEXT_PERSONAL.setTextColor(Color.BLUE);

        }else {

            TEXT_PERSONAL.setText(getResources().getString(R.string.Etiqueta_AUX));
            TEXT_PERSONAL.setTextColor(Color.MAGENTA);
        }



        //if (tipo_personal==true){NOMBRE_AUX.setVisibility(View.INVISIBLE);}else{NOMBRE_AUX.setVisibility(VISIBILIDAD);}
    }

    // BOTON AUX
    public void AUX(View view){

        tipo_personal=false;

        Ver_controles_vs(4);

        ver_ocultar_controles(0);

        LLENAR_SPINNER();

    }
    // BOTON REINICIO
    public void REINICIO(View view){

        VER_PDFS.setVisibility(View.INVISIBLE);

        INICIO_TEST.setVisibility(View.INVISIBLE);

        Ver_controles_vs(0);

        ver_ocultar_controles(4);
    }
    public void CERRAR(View view){

      finish();
    }
    // DECLACION DE CONTROLES Y/O INICIALIZACION DE VARIABLES
    private void INICIALIZAR(){

        TEST_FORMACION=(Spinner)findViewById(R.id.spiner_test);

        NOMBRE_VS=(Spinner)findViewById(R.id.spiner_vs);

        NOMBRE_FORMADOR=(Spinner)findViewById(R.id.spinner_formador);

        REINICIO=(Button)findViewById(R.id.Reinicio);

        VER_PDFS=(Button)findViewById(R.id.VER_PDF);

        VS=(Button)findViewById(R.id.VS);

        AUX=(Button)findViewById(R.id.AUX);

        INICIO_TEST=(ImageButton)findViewById(R.id.INICIAR_TEST);

        INFORMACION_TEST=(ImageButton)findViewById(R.id.boton_informacion);

        INFO=(TextView)findViewById(R.id.INFOR_MACION);

        TEXT_FORMADOR=(TextView)findViewById(R.id.textView129);

        TEXT_TIPO_TEXT=(TextView)findViewById(R.id.textView47);

        TEXT_PERSONAL=(TextView)findViewById(R.id.textView125);

        ver=false;

        ver_ocultar_controles(4);

        //LLENAR_SPINNER();

    }
    // cursor para llenar el valor tip de un vs
    private void llenar_con_ID (String _id){

        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();

        Cursor csor = db.rawQuery("SELECT * FROM VS WHERE _id='"+_id+"'", null);

        csor.moveToFirst();

        TIP=csor.getString(4);

    }
    // cursor para llenar el valor DNI de un aux
    private void llenardni_con_ID (String _id){

        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();

        Cursor csor = db.rawQuery("SELECT * FROM AUX WHERE _id='"+_id+"'", null);

        csor.moveToFirst();

        TIP=csor.getString(5);

    }
    // comprobamos que existe un test aprobado para determinada persona
    private Boolean exite_aprobado(){
        dbAdapterSituacion = new TipdbAdapter(this) ;
        dbAdapterSituacion.abrir();

        Cursor C_EXISTE = dbAdapterSituacion.getnombreCOMPLETOII(NOMBRE_PERSONA,NOMBRE_TEST);

        if (C_EXISTE.getCount()>0){return true;}else return false;


    }
    // RUTINA DE LANZAMIENTO DE TEST
    private void LANZAR_TEST(){
        Intent i= new Intent(this,TEST.class);Intent j= new Intent(this,TEST_VARIABLE.class);
        i.putExtra("NOMBRE_TEST", NOMBRE_TEST);j.putExtra("NOMBRE_TEST", NOMBRE_TEST);
        i.putExtra("NOMBRE_VS", NOMBRE_PERSONA);j.putExtra("NOMBRE_VS", NOMBRE_PERSONA);
        i.putExtra("FORMADOR", FORMADOR);j.putExtra("FORMADOR", FORMADOR);
        i.putExtra("TIP", TIP); j.putExtra("TIP", TIP);
        i.putExtra("tipo_personal",tipo_personal);j.putExtra("tipo_personal",tipo_personal);

        Toast.makeText(getBaseContext(), "Comienza el test...para aprobar debe contestar 70% preguntas correctamente.", Toast.LENGTH_LONG).show();
        MediaPlayer mp5= MediaPlayer.create(this,R.raw.start_test);
        switch (NOMBRE_TEST){
            case "INSPECCIONES MANUALES":

                startActivity(i);//ABRIR ACTIVITY

                MediaPlayer mp= MediaPlayer.create(this,R.raw.ins_manual);

                mp.start();//SONIDO

                break;
            case "CONTROL PORTON APM":

                startActivity(i);//ABRIR ACTIVITY

                MediaPlayer mp1= MediaPlayer.create(this,R.raw.apm);

                mp1.start();//SONIDO

                break;
            case "UTILIZACION EQUIPOS ETD":

                startActivity(i);//ABRIR ACTIVITY

                MediaPlayer mp2= MediaPlayer.create(this,R.raw.etd);

                mp2.start();//SONIDO

                break;
            case "INSPECCIONES CARRITOS LIMPIEZA":

                startActivity(i);//ABRIR ACTIVITY

                MediaPlayer mp3= MediaPlayer.create(this,R.raw.carritos);

                mp3.start();//SONIDO

                break;
            case "REGISTRO DE SEGURIDAD EN AERONAVES":

                startActivity(i);//ABRIR ACTIVITY

                mp5.start();//SONIDO

                break;
            case "ARTEFACTOS EXPLOSIVOS LAMINADOS":

                TipdbAdapter test = new TipdbAdapter(this) ;

                test.abrir();

                Cursor AELaminados=test.TEST(NOMBRE_TEST);

                j.putExtra("NUMERO_PREGUNTAS",AELaminados.getCount());

                //Toast.makeText(getBaseContext(), "nº: "+AELaminados.getCount(), Toast.LENGTH_LONG).show();

                startActivity(j);//ABRIR ACTIVITY

                mp5.start();//SONIDO

                break;
            case "SUMINISTROS AEROPUERTO":

                TipdbAdapter test_suministros = new TipdbAdapter(this) ;

                test_suministros.abrir();

                Cursor Suministros=test_suministros.TEST(NOMBRE_TEST);

                j.putExtra("NUMERO_PREGUNTAS",Suministros.getCount());

                Toast.makeText(getBaseContext(), "nº: "+Suministros.getCount(), Toast.LENGTH_LONG).show();

                startActivity(j);//ABRIR ACTIVITY

                mp5.start();//SONIDO

                break;
            default:

                startActivity(i);//ABRIR ACTIVITY

                mp5.start();//SONIDO

                break;
        }
    }
    // dialogo de alerta de test ya realizado
    private void DIALOGO_ALERTA(){
        new AlertDialog.Builder(this)

                .setIcon(R.drawable.infor_macion)
                .setTitle("TEST YA APROBADO")
                .setMessage(Html.fromHtml(getResources().getString(R.string.ALERTA_TEST))+" " +NOMBRE_TEST + "?")

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getBaseContext(), "TEST "+ NOMBRE_TEST +" CANCELADO", Toast.LENGTH_LONG).show();
                    }
                })

                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
// Salir
                        LANZAR_TEST();
                    }
                })
                .show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        switch (parent.getId()) {
            case R.id.spiner_test:

                NOMBRE_TEST = parent.getItemAtPosition(position).toString();

                if (!NOMBRE_TEST.equals("")) {
                    VER_PDFS.setVisibility(View.VISIBLE);
                } else {
                    VER_PDFS.setVisibility(View.INVISIBLE);
                }
                if (!NOMBRE_TEST.equals("") && (NOMBRE_PERSONA.length() >= 3) && (!FORMADOR.equals(""))) {

                    INICIO_TEST.setVisibility(View.VISIBLE);

                } else {

                    INICIO_TEST.setVisibility(View.INVISIBLE);
                }

                break;
            case R.id.spiner_vs:
                Cursor c1 = (Cursor) parent.getItemAtPosition(position);
                if (tipo_personal==true){
                    //Cursor c1 = (Cursor) parent.getItemAtPosition(position);

                    NOMBRE_PERSONA = c1.getString(c1.getColumnIndex(TipdbAdapter.C_COLUMNA_NOMBRE));

                    String _id = c1.getString(c1.getColumnIndex(TipdbAdapter.C_COLUMNA_ID));

                    llenar_con_ID(_id);

                }else{

                    NOMBRE_PERSONA = c1.getString(c1.getColumnIndex(TipdbAdapter.C_COLUMNA_NOMBRE));

                    String _id = c1.getString(c1.getColumnIndex(TipdbAdapter.C_COLUMNA_ID));

                    llenardni_con_ID(_id);
                    //NOMBRE_PERSONA=parent.getItemAtPosition(position).toString();
                }

                if (!NOMBRE_TEST.equals("") && (NOMBRE_PERSONA.length() >= 3) && (!FORMADOR.equals(""))) {

                    INICIO_TEST.setVisibility(View.VISIBLE);

                } else {

                    INICIO_TEST.setVisibility(View.INVISIBLE);
                }

                //Toast.makeText(getBaseContext(), "VS: "+NOMBRE_PERSONA, Toast.LENGTH_LONG).show();
                //if (!NOMBRE_PERSONA.equals("")){INICIO_TEST.setVisibility(View.VISIBLE);}else{INICIO_TEST.setVisibility(View.INVISIBLE);}

                break;
            case R.id.spinner_formador:

                Cursor c2=(Cursor)parent.getItemAtPosition(position);

                FORMADOR=c2.getString(c2.getColumnIndex("nombre"));

                if (!NOMBRE_TEST.equals("") && (NOMBRE_PERSONA.length() >= 3) && (!FORMADOR.equals(""))) {

                    INICIO_TEST.setVisibility(View.VISIBLE);

                } else {

                    INICIO_TEST.setVisibility(View.INVISIBLE);
                }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
