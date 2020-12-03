package com.example.julio.inspector;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Environment;
import android.provider.Settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class CALIBRADO_ADM extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener,AdapterView.OnItemClickListener {
    private EditText FECHA,HORA,OBSERVACIONES,TRAZAS,vspanel,FECHA_SEL;
    private ImageButton calendario;
    private Button GUARDAR,EDITAR,ALARMA_ALAMAN;
    private TextView rev,identificador,ATENCION;
    private CheckBox EXISTE_MAQUINA_TRAZAS;
    private Spinner nombre_persona,FILTRO;
    private ListView CALIBRADOS;
    private int countVS,cuenta,BIT,T_ACT,Touch_List;
    private long ID;
    private DatePickerDialog fecha__picker;
    private TimePickerDialog hora_picker;
    private SimpleDateFormat dateFormatter;
    private String s_fecha,s_hora,s_PERSONA ,s_FILTRO ,s_OBSERVACIONES,s_EXISTE,s_REVISION_TRAZAS ;
    private TipdbAdapter dbAdapterSituacion;
    private MainActivity exportar;
    private LenguajeListAdapter CursorAdapter;
    private Cursor cursorListaSituacion,CURSOR_FILTRO;
    private String ID_DISPOSITIVO,numero;
    Timer myTimer;
    private final static String CARPETA_EXPORTACION = "/fotospuesto/EXPORTACIONES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrado__adm);
        CONTROLES();
        FECHA_SEL.setText(FECHAconformato());
        CONSULTAR(FECHAconformato());
        setTimeField();
        ID_DISPOSITIVO= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);


        final String[] DES=getResources().getStringArray(R.array.FILTROSADM);
        final String[] nom=getResources().getStringArray(R.array.INSPECTORESadm);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, DES);
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, nom);
        FILTRO.setAdapter(adaptador);
        FILTRO.setOnItemSelectedListener(this);
        nombre_persona.setAdapter(adaptador1);
        nombre_persona.setOnItemSelectedListener(this);

       // total(1, FECHAconformato());
        dateFormatter= new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT);
        setDatetimeField();
        BIT=0;
        CALIBRADOS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(), "pulsado "+id, Toast.LENGTH_SHORT).show();



                    }


        });
            }
    public void ACTUALIZA_BOTON(View V){
        Actualizar(ID);
        LIMPIEZA();
        CONSULTAR(FECHA_SEL.getText().toString());
    }
    public void Actualizar(long ID){
        get_DATOS();
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("fecha", s_fecha); //1
            nuevoRegistro.put("PERSONA", s_PERSONA);//2
            nuevoRegistro.put("FILTRO", s_FILTRO);//3
            nuevoRegistro.put("HORA", s_hora);//4
            nuevoRegistro.put("OBSERVACIONES", s_OBSERVACIONES);//5
            nuevoRegistro.put("REVISION_DETECTOR", s_REVISION_TRAZAS);//6
            nuevoRegistro.put("ETA", s_EXISTE);

            //actualizamos el registro en la base de datos
            db.update("ADM", nuevoRegistro, "_id="+ID, null);
            Toast.makeText(getBaseContext(), "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();}
    }
    public void CONSULTAR(final String FECHA){
        dbAdapterSituacion = new TipdbAdapter(this) ;
        dbAdapterSituacion.abrir();
       // String diauno=FECHA.substring(3,10).toString();
        String MES=FECHA.substring(3,5).toString();
        String YEAR=FECHA.substring(6,10).toString();

        //String PRIMERDIAMES= "01/"+diauno;

        //Toast.makeText(getBaseContext(), PRIMERDIAMES, Toast.LENGTH_LONG).show();
        CURSOR_FILTRO=dbAdapterSituacion.getFILTRO_MES(MES,YEAR);
        LenguajeListAdapter CursorAdapter=new LenguajeListAdapter(this,CURSOR_FILTRO);
        CALIBRADOS.setAdapter(CursorAdapter);

        vspanel.setBackgroundColor(Color.YELLOW);

        vspanel.setText("REGISTROS : " + String.valueOf(CURSOR_FILTRO.getCount())+"/"+obtenerUltimoDiaMes(Integer.parseInt(YEAR),Integer.parseInt(MES)));

    }

    public int obtenerUltimoDiaMes (int anio, int mes) {

        Calendar calendario=Calendar.getInstance();
        calendario.set(anio, mes-1, 1);
        return calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

    }
    int ultimoDiaMes=obtenerUltimoDiaMes(2014, 2);

    public void CONTROLES(){
        FECHA=(EditText)findViewById(R.id.ADM_FECHA);
        HORA=(EditText)findViewById(R.id.ADM_HORA);
        OBSERVACIONES=(EditText)findViewById(R.id.ADM_OBSERVACIONES);
        TRAZAS=(EditText)findViewById(R.id.ADM_REVISON_TRAZAS);
        rev=(TextView)findViewById(R.id.TEXT_REVISION);
        EXISTE_MAQUINA_TRAZAS=(CheckBox)findViewById(R.id.ADM_TRAZAS_CHECK);
        nombre_persona=(Spinner)findViewById(R.id.ADM_SPIN_PERSONA);
        FILTRO=(Spinner)findViewById(R.id.ADM_SPIN_FILTRO);
        vspanel=(EditText)findViewById(R.id.ADM_PANEL);
        calendario=(ImageButton)findViewById(R.id.adm_CALENDAR);
        CALIBRADOS=(ListView)findViewById(R.id.list_CALIBRADO);
        identificador=(TextView)findViewById(R.id.l_IDENTIFICADOR);
        FECHA_SEL=(EditText)findViewById(R.id.FECHA_SELECCION);
        ATENCION=(TextView)findViewById(R.id.ADM_ATENCION);
        EDITAR=(Button)findViewById(R.id.ADM_ACTUALIZA);
        GUARDAR=(Button)findViewById(R.id.ADM_GUARDAR);
        ALARMA_ALAMAN=(Button)findViewById(R.id.ALARMA_ALAMAN);
    }
    public void HORA_PICK(View view){
        hora_picker.setTitle("SELECCIONE HORA");
        hora_picker.show();
    }
    public void FECHA_PICK(View view){
        fecha__picker.show();
    }
    private void setDatetimeField(){
        FECHA.setOnClickListener(this);
        Calendar newCalendar= Calendar.getInstance();
        fecha__picker=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                FECHA_SEL.setText(dateFormatter.format(newDate.getTime()));
                total(1, FECHA_SEL.getText().toString());
                CONSULTAR(FECHA_SEL.getText().toString());
                LIMPIEZA();
            }
        },newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    private void setTimeField(){

        Calendar newCalendar= Calendar.getInstance();
        int hour=newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute=newCalendar.get(Calendar.MINUTE);
        hora_picker=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                switch (minute){
                    case 0:
                        HORA.setText(hourOfDay + ":00");
                        break;
                    case 1:
                        HORA.setText(hourOfDay + ":01");
                        break;
                    case 2:
                        HORA.setText(hourOfDay + ":02");
                        break;
                    case 3:
                        HORA.setText(hourOfDay + ":03");
                        break;
                    case 4:
                        HORA.setText(hourOfDay + ":04");
                        break;
                    case 5:
                        HORA.setText(hourOfDay + ":05");
                        break;
                    case 6:
                        HORA.setText(hourOfDay + ":06");
                        break;
                    case 7:
                        HORA.setText(hourOfDay + ":07");
                        break;
                    case 8:
                        HORA.setText(hourOfDay + ":08");
                        break;
                    case 9:
                        HORA.setText(hourOfDay + ":09");
                        break;
                    default: HORA.setText(hourOfDay + ":" + minute);
                }

            }



        },hour,minute,true);

    }
    public void GUARDAR (View V){

        if (s_FILTRO.equals("") || s_PERSONA.equals("")){Toast.makeText(getBaseContext(), "ANOTADO POR, VACIO.", Toast.LENGTH_SHORT).show();
        }else if (cuenta<32){

        if (repetido()==0){
            get_DATOS();
            INSERTARCALIBRADO();
            EXPORTAR_alarma_alaman();
        } else{Toast.makeText(getBaseContext(), "VERIFICACION DE ALARMA YA REALIZADA", Toast.LENGTH_SHORT).show();}
        }else {Toast.makeText(getBaseContext(), "ALARMA VERIFICADA", Toast.LENGTH_SHORT).show();}}
    private Integer repetido(){
        Integer R=0;
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ADM WHERE fecha='" + FECHA.getText().toString() + "'"+ " AND " + "FILTRO='"+s_FILTRO+ "'", null);
        R=cursor.getCount();
        return R;
    }
    public void maquina_TRAZAS(View V){
        if (EXISTE_MAQUINA_TRAZAS.isChecked()){
        TRAZAS.setVisibility(View.VISIBLE);
            TRAZAS.setText("ESTADO OK,MAQUINA E.T.A VERIFICADA.");
        rev.setVisibility(View.VISIBLE);}else {
            TRAZAS.setVisibility(View.INVISIBLE);
            rev.setVisibility(View.INVISIBLE);
        }
    }
    public void ALARMA(View V){
        if (s_FILTRO.equals("") || s_PERSONA.equals("")){Toast.makeText(getBaseContext(), "FILTRO y/o ANOTADO POR, VACIOS.", Toast.LENGTH_SHORT).show();}else {
            ALARMA_ALAMAN.setVisibility(View.INVISIBLE);
            EXPORTAR_alarma_alaman();
        }
    }
    private void get_DATOS(){
        s_fecha=FECHA.getText().toString();
        s_hora=HORA.getText().toString();
        s_OBSERVACIONES=OBSERVACIONES.getText().toString();
        if (EXISTE_MAQUINA_TRAZAS.isChecked()){
            s_EXISTE="SI";
            s_REVISION_TRAZAS=TRAZAS.getText().toString();
        }else {
            s_EXISTE="NO";
            s_REVISION_TRAZAS="";
        }

    }
    public String FECHAconformato() {
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
    public void INSERTARCALIBRADO() {
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        String MES=s_fecha.substring(3,5).toString();
        String YEAR=s_fecha.substring(6,10).toString();
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put("fecha", s_fecha); //1
            nuevoRegistro.put("PERSONA", s_PERSONA);//2
            nuevoRegistro.put("FILTRO", s_FILTRO);//3
            nuevoRegistro.put("HORA", s_hora);//4
            nuevoRegistro.put("OBSERVACIONES", MES);//5
            nuevoRegistro.put("REVISION_DETECTOR", YEAR);//6
            nuevoRegistro.put("ETA", s_EXISTE);//7
            //Insertamos el registro en la base de datos
            db.insert("ADM", null, nuevoRegistro);
            Log.i(this.getClass().toString(), "INSERTADO NUEVO REGISTRO");
            // Cerramos la base de datos
            db.close();

            Toast.makeText(getBaseContext(), "DATOS GUARDADOS ", Toast.LENGTH_SHORT).show();
            CONSULTAR(FECHA_SEL.getText().toString());
            //total(1,s_fecha);
            LIMPIEZA();


        }
    }
    public void total(int n,String FECHA){
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ADM WHERE fecha='" + FECHA + "'", null);
        if (n==1){
            countVS=0;
            cuenta=cursor.getCount();
            if (cuenta==14){


                vspanel.setBackgroundColor(Color.MAGENTA);

                vspanel.setText("REGISTROS : " + String.valueOf(cuenta) + "/14 (COMPLETO)");

            } else if (cuenta==0){


                vspanel.setBackgroundColor(Color.LTGRAY);

                vspanel.setText("REGISTROS : " + String.valueOf(cuenta)+"/14");

            }else{


                vspanel.setBackgroundColor(Color.YELLOW);

                vspanel.setText("REGISTROS : " + String.valueOf(cuenta)+"/14");

            }}
        if (n==2){
            cursor.moveToFirst();//al primer registro

            countVS=cursor.getPosition();
            vspanel.setText("REGISTROS HOY: " + (countVS + 1) + "/" + String.valueOf(cursor.getCount()));

        }
        if (n==3){
            cursor.moveToLast();//al ultimo registro

            countVS=cursor.getPosition();
            vspanel.setText("REGISTROS HOY: " + String.valueOf(countVS + 1) + "/" + String.valueOf(cursor.getCount()));

        }
        if (n==4){
            countVS+=1;
            cursor.moveToPosition(countVS);//al siguiente registro

            if (countVS+1==(cursor.getCount())){

                }
            vspanel.setText("REGISTROS HOY: " + String.valueOf(countVS+1)+"/" + String.valueOf(cursor.getCount()));

        }
        if (n==5){
            countVS-=1;

            cursor.moveToPosition(countVS);;//al anterior registro
            if (countVS==0){
                }
            vspanel.setText("REGISTROS HOY: " + String.valueOf(countVS + 1) + "/" + String.valueOf(cursor.getCount()));

        }
        if (n==6){}
    }
    public void EXPORTAR_DRIVE(){
        //EXPORTAR ARCHIVO DE TEXTO A CARPETA COMPARTIDA EN GOOGLE DRIVE
        String NOMBREFICHERO="CERO";

        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();



        NOMBREFICHERO=("CALIBRADO_ADM"+FECHA_FICHERO(FECHA_EXPORTACION3())+ID_DISPOSITIVO+".txt");


        Cursor c = db.rawQuery("SELECT * FROM ADM WHERE fecha='" + FECHA_EXPORTACION3() + "'", null);



        if (c != null) {
            //cursor al la primera columna
            if (c.moveToFirst()) {
                try {
                    //File ruta_sd = Environment.getExternalStorageDirectory();
                    File DIR = new File(Environment.getExternalStorageDirectory().getPath()+CARPETA_EXPORTACION);
                    if (!DIR.exists()){DIR.mkdir();}
                    File file= new File(DIR,NOMBREFICHERO);

                    OutputStreamWriter fout =
                            new OutputStreamWriter(new FileOutputStream(file));
                    String linea=System.getProperty("line.separator");
                    fout.write("FECHA" + ";" + "PERSONA" + ";" + "FILTRO" + ";" + "HORA" + ";" + "OBSERVACIONES" + ";" + "REVISION_DETECTOR" + ";" + "ETA" +linea);
                    do {
                        String registro=c.getString(1)+ ";" +c.getString(2)+ ";" +c.getString(3)+ ";" +c.getString(4)+ ";" +c.getString(5)+ ";"+c.getString(6)+ ";" +c.getString(7)+ ";" ;
                        fout.write(registro+linea);
                    } while (c.moveToNext());
                    fout.close();
                    c.close();
                    db.close();
                    //guardar3_registro_exportacion();
                    Toast.makeText(getBaseContext(), "DATOS CALIBRADO ADM EXPORTADOS "+FECHA_EXPORTACION3(), Toast.LENGTH_LONG).show();

                    //inicio();
                } catch (Exception ex) {
                    Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
                }

            }
        }
    }
    public void EXPORTAR_alarma_alaman(){
        //EXPORTAR ARCHIVO DE TEXTO A CARPETA COMPARTIDA EN GOOGLE DRIVE
        String NOMBREFICHERO="CERO";

       // get_DATOS();

        NOMBREFICHERO=("ALARMA_ALAMAN"+FECHA_FICHERO(s_fecha)+ID_DISPOSITIVO+".txt");

                try {
                    //File ruta_sd = Environment.getExternalStorageDirectory();
                    File DIR = new File(Environment.getExternalStorageDirectory().getPath()+CARPETA_EXPORTACION);
                    if (!DIR.exists()){DIR.mkdir();}
                    File file= new File(DIR,NOMBREFICHERO);

                    OutputStreamWriter fout =
                            new OutputStreamWriter(new FileOutputStream(file));
                    String linea=System.getProperty("line.separator");
                    fout.write("FECHA" + ";" + "PERSONA" + ";" + "FILTRO" + ";" + "ANOTADO" +linea);

                        String registro=s_fecha+ ";" +s_hora+ ";" +s_FILTRO+ ";" +s_PERSONA;
                    fout.write(registro+linea);

                    fout.close();

                    //guardar3_registro_exportacion();
                   // Toast.makeText(getBaseContext(), "VERIFICAR ALARMA EXPORTADOS "+FECHA_EXPORTACION3(), Toast.LENGTH_LONG).show();

                    //inicio();
                } catch (Exception ex) {
                    Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
                }



    }
    public String FECHA_FICHERO(String f) {
        String fecha_reunion=f;
        String F_REUNION_FORMAT="_"+fecha_reunion.substring(6,10).toString()+"_"+fecha_reunion.substring(3,5).toString()+"_"+fecha_reunion.substring(0,2).toString()+"_";
        return F_REUNION_FORMAT;}
    public String FECHA_EXPORTACION3(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ADM", null);
        String dateString ="";
        if (c != null) {
            c.moveToLast();
            dateString = c.getString(1);}

        return dateString;
    }
    private void LIMPIEZA(){

        FECHA.setText("");

        HORA.setText("");

        OBSERVACIONES.setText("");

        TRAZAS.setText("");

        EXISTE_MAQUINA_TRAZAS.setChecked(false);

        nombre_persona.setSelection(0);

        FILTRO.setSelection(0);

        identificador.setVisibility(View.INVISIBLE);

        EDITAR.setVisibility(View.INVISIBLE);

        GUARDAR.setVisibility(View.VISIBLE);

        TRAZAS.setVisibility(View.INVISIBLE);


    }
    private void TIMER(){
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                CALIBRADO_ADM.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        T_ACT=1;
                        if(BIT==1){ATENCION.setVisibility(View.INVISIBLE);BIT=0;} else{ATENCION.setVisibility(View.VISIBLE);BIT=1;}
                    }
                });


            }
        }, 400, 500);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        switch (parent.getId()) {
            case R.id.ADM_SPIN_FILTRO:

                s_FILTRO =parent.getItemAtPosition(position).toString();
                if (s_FILTRO.equals("") || Touch_List==1){}else{

                FECHA.setText(FECHA_SEL.getText());
                s_fecha=FECHA.getText().toString();
               HORA.setText(HORAconformato());
               s_hora=HORA.getText().toString();
                OBSERVACIONES.setText("OK");}
                switch (s_FILTRO){

                    case "OFIC. DIQUE SUR":
                        numero="01";
                        EXISTE_MAQUINA_TRAZAS.setChecked(false);
                        TRAZAS.setText("");
                        TRAZAS.setVisibility(View.INVISIBLE);
                        break;
                    case "OFIC. NORTE":
                        numero="02";
                        EXISTE_MAQUINA_TRAZAS.setChecked(false);
                        TRAZAS.setText("");
                        TRAZAS.setVisibility(View.INVISIBLE);
                        break;
                    case "SALA 1":
                        numero="03";
                        EXISTE_MAQUINA_TRAZAS.setChecked(true);
                        TRAZAS.setText("ESTADO OK,MAQUINA E.T.A VERIFICADA.");
                        TRAZAS.setVisibility(View.VISIBLE);
                        break;
                    case "FILTRO T2 SUR":
                        numero="04";
                        EXISTE_MAQUINA_TRAZAS.setChecked(false);
                        TRAZAS.setText("");
                        TRAZAS.setVisibility(View.INVISIBLE);
                        break;
                    case "VEHICULOS SUR":
                        numero="05";
                        EXISTE_MAQUINA_TRAZAS.setChecked(false);
                        TRAZAS.setText("");
                        TRAZAS.setVisibility(View.INVISIBLE);
                        break;
                    case "MERCANCIAS SUR":
                        numero="06";
                        EXISTE_MAQUINA_TRAZAS.setChecked(false);
                        TRAZAS.setText("");
                        TRAZAS.setVisibility(View.INVISIBLE);
                        break;
                    case "MERCANCIAS NORTE":
                        numero="07";
                        EXISTE_MAQUINA_TRAZAS.setChecked(false);
                        TRAZAS.setText("");
                        TRAZAS.setVisibility(View.INVISIBLE);
                        break;
                    case "CLH":
                        numero="08";
                        EXISTE_MAQUINA_TRAZAS.setChecked(false);
                        TRAZAS.setText("");
                        TRAZAS.setVisibility(View.INVISIBLE);
                        break;
                    case "ALAMAN":
                        numero="09";
                        EXISTE_MAQUINA_TRAZAS.setChecked(false);
                        TRAZAS.setText("");
                        TRAZAS.setVisibility(View.INVISIBLE);
                        ALARMA_ALAMAN.setVisibility(View.INVISIBLE);
                        break;
                    case "MODULAR":
                        numero="10";
                        EXISTE_MAQUINA_TRAZAS.setChecked(false);
                        TRAZAS.setText("");
                        TRAZAS.setVisibility(View.INVISIBLE);
                        break;
                    case "TUNEL CELA":
                        numero="11";
                        EXISTE_MAQUINA_TRAZAS.setChecked(false);
                        TRAZAS.setText("");
                        TRAZAS.setVisibility(View.INVISIBLE);
                        break;
                    case "TORRE ACC. SATELITE":
                        numero="12";
                        EXISTE_MAQUINA_TRAZAS.setChecked(false);
                        TRAZAS.setText("");
                        TRAZAS.setVisibility(View.INVISIBLE);
                        break;
                    case "FILTRO EMPLEADOS T4 P0":
                        numero="13";
                        EXISTE_MAQUINA_TRAZAS.setChecked(false);
                        TRAZAS.setText("");
                        TRAZAS.setVisibility(View.INVISIBLE);
                        break;
                    case "FILTRO VIP":
                        numero="14";
                        EXISTE_MAQUINA_TRAZAS.setChecked(true);
                        TRAZAS.setText("ESTADO OK,MAQUINA E.T.A VERIFICADA.");
                        TRAZAS.setVisibility(View.VISIBLE);

                        break;
                }

                if (!s_FILTRO.equals("")){identificador.setVisibility(View.VISIBLE);}

                identificador.setText(numero);
                Touch_List=0;
                break;

            case R.id.ADM_SPIN_PERSONA:

                s_PERSONA=parent.getItemAtPosition(position).toString();

                if (s_PERSONA.equals("")|| Touch_List==1){}else{

                    FECHA.setText(FECHA_SEL.getText());

                    HORA.setText(HORAconformato());

                    s_hora=HORA.getText().toString();

                     OBSERVACIONES.setText("OK");}

                Touch_List=0;
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);




    }
}
