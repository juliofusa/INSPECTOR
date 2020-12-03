package com.example.julio.inspector;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Environment;
import android.provider.Settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;



public class MainActivity extends AppCompatActivity {
    private String ID_DISPOSITIVO,VERSION;
    private TextView T_VERSION;
    private final static String CARPETA_EXPORTACION = "/fotospuesto/EXPORTACIONES";
    private final static String CARPETA_FIRMAS = "/fotospuesto/EXPORTACIONES/firmas";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ID_DISPOSITIVO= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM VS ", null);
        if (cursor.getCount()==0){
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("nombre", "");
            nuevoRegistro.put("apellido1","");
            nuevoRegistro.put("apellido2", "");
            nuevoRegistro.put("tip", "");
            nuevoRegistro.put("dni", "");
            nuevoRegistro.put("id_scaner", "");
            nuevoRegistro.put("password_scaner", "");
            nuevoRegistro.put("telefono", "");
            nuevoRegistro.put("IDSUSPENDIDO", "");
            nuevoRegistro.put("ANTIGUEDAD","");
            nuevoRegistro.put("ANTIGUEDAD_AEROPUERTO", "");
            nuevoRegistro.put("C1", "");
            nuevoRegistro.put("C2", "");
            nuevoRegistro.put("REFRESCOC2", "");
            nuevoRegistro.put("CADUCAC1", "");
            nuevoRegistro.put("CADUCAC2", "");
            nuevoRegistro.put("CADUCA_REFRESCOC2", "");
            nuevoRegistro.put("DOMICILIO","");
            nuevoRegistro.put("NUMERO", "");
            nuevoRegistro.put("CODIGO_POSTAL", "");
            nuevoRegistro.put("MUNICIPIO", "");
            nuevoRegistro.put("TALLA_CAMISA", "");
            nuevoRegistro.put("TALLA_PANTALON", "");
            nuevoRegistro.put("TALLA_CAZADORA", "");
            nuevoRegistro.put("TALLA_ANORAK", "");
            nuevoRegistro.put("TALLA_ZAPATOS","");
            nuevoRegistro.put("SINUSO1", "");
            nuevoRegistro.put("SINUSO2", "");

            //Insertamos el registro en la base de datos
            db.insert("VS", null, nuevoRegistro);
            db.close();
            requestMultiplePermissions();
            correccion();
            CREAR_CARPETAS();
        }
        CREAR_CARPETAS();
        EXPORTACION_INSPECCIONVS();
        //borar_nulos();
        EXPORTACION_PUESTOS();
        EXPORTACION_INSPECCION_MANUAL();
        getVersionInfo();
        //EXPORTACION_CALIBRADO();
    }
    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "Todos permisos han sido otorgados por el Usuario!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error al dar permisos! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
    private void CREAR_CARPETAS(){
        // listado de carpetas a crear
        File DIR = new File(Environment.getExternalStorageDirectory().getPath()+TipdbAdapter.R_CARPETA_EXPORTACION);
        File DIR1_EXPORTARCIONES = new File(Environment.getExternalStorageDirectory().getPath()+TipdbAdapter.R_RUTA_EXPORTACIONES);
        File DIR2_PDF = new File(Environment.getExternalStorageDirectory().getPath()+TipdbAdapter.R_RUTA_PDF);
        File DIR3_FIRMAS = new File(Environment.getExternalStorageDirectory().getPath()+TipdbAdapter.R_RUTA_FIRMAS);
        File DIR4_ORDEN_PUESTO = new File(Environment.getExternalStorageDirectory().getPath()+TipdbAdapter.R_RUTA_ORDENES);
        File DIR5_FORMACION = new File(Environment.getExternalStorageDirectory().getPath()+TipdbAdapter.R_RUTA_FORMACION);
        File DIR6_ORDENVS = new File(Environment.getExternalStorageDirectory().getPath()+TipdbAdapter.R_RUTA_ORDENVS);
        File DIR7_ORDENAUX = new File(Environment.getExternalStorageDirectory().getPath()+TipdbAdapter.R_RUTA_ORDENAUX);
        File DIR8_PROCEDIMIENTOS = new File(Environment.getExternalStorageDirectory().getPath()+TipdbAdapter.R_RUTA_PROCEDIMIENTOS);
        File DIR9_FOTOSTEST = new File(Environment.getExternalStorageDirectory().getPath()+TipdbAdapter.R_RUTA_fototest);
        File DIR10_FOTOS = new File(Environment.getExternalStorageDirectory().getPath()+TipdbAdapter.R_RUTA_FOTOS);


        // comprobamoms si existen los directorios "fotopuesto" y "ORDEN DE PUESTO" y creamos carpetas y subcarpetas
        if (!DIR.exists()){
            DIR.mkdirs();
            DIR1_EXPORTARCIONES.mkdirs();
            DIR2_PDF.mkdirs();
            DIR3_FIRMAS.mkdirs();
        }
        if (!DIR4_ORDEN_PUESTO.exists()){
            DIR4_ORDEN_PUESTO.mkdirs();
            DIR5_FORMACION.mkdirs();
            DIR6_ORDENVS.mkdirs();
            DIR7_ORDENAUX.mkdirs();
            DIR8_PROCEDIMIENTOS.mkdirs();
            DIR9_FOTOSTEST.mkdirs();

        }
        if (!DIR4_ORDEN_PUESTO.exists()){DIR4_ORDEN_PUESTO.mkdirs();}
        if (!DIR5_FORMACION.exists()){DIR5_FORMACION.mkdirs();}
        if (!DIR6_ORDENVS.exists()){DIR6_ORDENVS.mkdirs();}
        if (!DIR7_ORDENAUX.exists()){DIR7_ORDENAUX.mkdirs();}
        if (!DIR8_PROCEDIMIENTOS.exists()){DIR8_PROCEDIMIENTOS.mkdirs();}
        if (!DIR9_FOTOSTEST.exists()){DIR9_FOTOSTEST.mkdirs();}
        if (!DIR10_FOTOS.exists()){DIR10_FOTOS.mkdirs();}
    }
    public void borar_nulos(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if(db != null){
            db.execSQL("DELETE FROM inspeccionvs WHERE fecha =''");
            db.close();
        }
    }
    public void EXPORTACION_INSPECCIONVS(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM inspeccionvs", null);
        if (c.getCount()!=0){
            if (FECHAconformato().equals(FECHA_EXPORTACION())){}else{
                if (Fecha_exportacion_ultimo_registro().equals(FECHA_EXPORTACION())){
                   //Toast.makeText(getBaseContext(), "NO_echa_exportacion_ultimo_registro =" + Fecha_exportacion_ultimo_registro(), Toast.LENGTH_LONG).show();
                }else {

                    EXPORTAR_DRIVE();
                    //Toast.makeText(getBaseContext(), "echa_exportacion_ultimo_registro " + Fecha_exportacion_ultimo_registro(), Toast.LENGTH_LONG).show();
                }
            }
        }
        db.close();
    }
    public void EXPORTACION_PUESTOS(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM VISITAPUESTO", null);
        if (c.getCount()!=0){
            if (FECHAconformato().equals(FECHA_EXPORTACION2())){}else{
                if (Fecha2_exportacion_ultimo_registro().equals(FECHA_EXPORTACION2())){
                    //Toast.makeText(getBaseContext(), "NO_echa_exportacion_ultimo_registro =" + Fecha_exportacion_ultimo_registro(), Toast.LENGTH_LONG).show();
                }else {

                    EXPORTAR_DRIVE2();
                    //Toast.makeText(getBaseContext(), "echa_exportacion_ultimo_registro " + Fecha_exportacion_ultimo_registro(), Toast.LENGTH_LONG).show();
                }
            }
        }
        db.close();
    }
    public void EXPORTACION_INSPECCION_MANUAL(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM inspeccionmanual", null);
        if (c.getCount()!=0){
            if (FECHAconformato().equals(FECHA_EXPORTACION4())){}else{
                if (Fecha4_exportacion_ultimo_registro().equals(FECHA_EXPORTACION4())){

                }else {

                    EXPORTAR_DRIVE4();

                }
            }
        }
        db.close();
    }
    public void EXPORTACION_CALIBRADO(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ADM", null);
        if (c.getCount()!=0){
            if (FECHAconformato().equals(FECHA_EXPORTACION3())){}else{
                if (Fecha3_exportacion_ultimo_registro().equals(FECHA_EXPORTACION3())){

                }else {

                    EXPORTAR_DRIVE3();

                }
            }
        }
        db.close();
    }
    public void EXPORTAR_DRIVE(){
        //EXPORTAR ARCHIVO DE TEXTO A CARPETA COMPARTIDA EN GOOGLE DRIVE
        String NOMBREFICHERO="CERO";

        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();



        NOMBREFICHERO=("INSPECCIONVS"+FECHA_FICHERO(FECHA_EXPORTACION())+ID_DISPOSITIVO+".txt");


        Cursor c = db.rawQuery("SELECT * FROM inspeccionvs WHERE fecha='" + FECHA_EXPORTACION() + "'", null);



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
                    fout.write("ID"+ ";" + "INSPECTOR" + ";" + "FECHA" + ";" + "HORA" + ";" + "PUESTO" + ";" + "TIP" + ";" + "NOMBRE" + ";" + "APELLIDO1" + ";" + "APELLIDO2" + ";" + "DNI" + ";" + "ZAPATOS" + ";" + "PANTALON" + ";" + "CAZADORA" + ";" + "CAMISA" + ";" + "ABRIGO" + ";" + "EPIS" + ";" + "DEFENSA" + ";" + "GRILLETES" + ";" + "CORREAJES"+ ";" + "OTROS" + ";" + "ORDENES PUESTO"+ ";" + "DESMP"+ ";" + "CORRECCIONES"+ ";" + "OBSERVACIONES"+ ";" + "TIP EN VIGOR"+ ";" + "CORRECTO"+ linea);
                    do {
                        String registro=c.getString(0)+ ";" +c.getString(1)+ ";" +c.getString(2)+ ";" +c.getString(3)+ ";" +c.getString(4)+ ";" +c.getString(5)+ ";" +c.getString(6)+ ";" +c.getString(7)+ ";" +c.getString(8)+ ";" +c.getString(9)+ ";" +c.getString(10)+ ";" +c.getString(11)+ ";" +c.getString(12)+ ";" +c.getString(13)+ ";" +c.getString(14)+ ";" +c.getString(15)+ ";" +c.getString(16)+ ";" +c.getString(17)+ ";" +c.getString(18)+ ";" +c.getString(19)+ ";" +c.getString(20)+ ";" +c.getString(21)+ ";" +c.getString(22)+ ";" +c.getString(23)+";" +c.getString(24)+ ";" +c.getString(25);


                        fout.write(registro+linea);
                    } while (c.moveToNext());
                    fout.close();
                    c.close();
                    db.close();
                    guardar_registro_exportacion();
                    Toast.makeText(getBaseContext(), "DATOS EXPORTADOS "+FECHA_EXPORTACION(), Toast.LENGTH_LONG).show();

                    //inicio();
                } catch (Exception ex) {
                    Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
                }

            }
        }
    }
    public void EXPORTAR_DRIVE2(){
        //EXPORTAR ARCHIVO DE TEXTO A CARPETA COMPARTIDA EN GOOGLE DRIVE
        String NOMBREFICHERO="CERO";

        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();



        NOMBREFICHERO=("INSPECCION_PUESTOS"+FECHA_FICHERO(FECHA_EXPORTACION2())+ID_DISPOSITIVO+".txt");


        Cursor c = db.rawQuery("SELECT * FROM VISITAPUESTO WHERE fecha='" + FECHA_EXPORTACION2() + "'", null);



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
                    fout.write("ID"+ ";" + "INSPECTOR" + ";" + "FECHA" + ";" + "HORA" + ";" + "PUESTO" + ";" + "ADM" + ";" + "CALIBRADO_ADM" + ";" + "MAQUINARX" + ";" + "DETECTOR_LIQUIDOS" + ";" + "DETECTOR_ZAPATOS" + ";" + "OTROS" + ";" + "INCIDENCIA" + ";" + "RIESGOS" + ";" + "RUTAFOTO" + ";" + "INCIDENCIA_ANTERIOR" + ";" + "INCIDENCIA_CORREGIDA" + ";" + "RUTAFOTO_ANTERIOR" + ";" + "OBSERVACIONES_ANTERIOR" + ";" + "OBSERVACION"+  ";" + "PROCEDIMIENTO"+ ";" + "ORDENES"+ ";" + "FOTO2"+ ";" + "FOTO3"+";" + "FOTO4"+linea);
                    do {
                        String registro=c.getString(0)+ ";" +c.getString(1)+ ";" +c.getString(2)+ ";" +c.getString(3)+ ";" +c.getString(4)+ ";" +c.getString(5)+ ";" +c.getString(6)+ ";" +c.getString(7)+ ";" +c.getString(8)+ ";" +c.getString(9)+ ";" +c.getString(10)+ ";" +c.getString(11)+ ";" +c.getString(12)+ ";" +c.getString(13)+ ";" +c.getString(14)+ ";" +c.getString(15)+ ";" +c.getString(16)+ ";" +c.getString(17)+ ";" +c.getString(18)+ ";"+c.getString(19)+ ";"+c.getString(20)+ ";"+c.getString(21)+ ";"+c.getString(22)+ ";"+c.getString(23)+ ";"+c.getString(24)+ ";"+c.getString(25)+ ";"+c.getString(26);
                        fout.write(registro+linea);
                    } while (c.moveToNext());
                    fout.close();
                    c.close();
                    db.close();
                    guardar2_registro_exportacion();
                    Toast.makeText(getBaseContext(), "DATOS EXPORTADOS "+FECHA_EXPORTACION2(), Toast.LENGTH_LONG).show();

                    //inicio();
                } catch (Exception ex) {
                    Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
                }

            }
        }
    }
    public void EXPORTAR_DRIVE4(){
        //EXPORTAR ARCHIVO DE TEXTO A CARPETA COMPARTIDA EN GOOGLE DRIVE
        String NOMBREFICHERO="CERO";

        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();



        NOMBREFICHERO=("INSPECCIONES_MANUALES"+FECHA_FICHERO(FECHA_EXPORTACION4())+ID_DISPOSITIVO+".txt");


        Cursor c = db.rawQuery("SELECT * FROM inspeccionmanual WHERE fecha='" + FECHA_EXPORTACION4() + "'", null);



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
                    fout.write("TIP" + ";" + "PERSONA" + ";" + "FECHA" + ";" + "CINTURILLA" + ";" + "CUELLO" + ";" + "HOMBROS_BRAZOS" + ";" + "TORSO_ESPALDA" + ";" + "MUSLO_ENTREPIERNA" + ";" + "empeinezapato" + ";" + "INSPECTOR" + ";" + "CORRECTO" + ";" + "HORA" + ";" + "PUESTO" + linea);
                    do {
                        String registro=c.getString(1)+ ";" +c.getString(2)+ ";" +c.getString(3)+ ";" +c.getString(4)+ ";" +c.getString(5)+ ";"+c.getString(6)+ ";" +c.getString(7)+ ";" +c.getString(8)+ ";"+c.getString(9)+ ";" +c.getString(10)+ ";"+c.getString(11)+ ";" +c.getString(12)+ ";"+c.getString(13);
                        fout.write(registro+linea);
                    } while (c.moveToNext());
                    fout.close();
                    c.close();
                    db.close();
                    guardar4_registro_exportacion();
                    //Toast.makeText(getBaseContext(), "DATOS EXPORTADOS "+FECHA_EXPORTACION3(), Toast.LENGTH_LONG).show();

                    //inicio();
                } catch (Exception ex) {
                    Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
                }

            }
        }
    }
    public void EXPORTAR_DRIVE3(){
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
                    fout.write("FECHA" + ";" + "PERSONA" + ";" + "FILTRO" + ";" + "HORA" + ";" + "OBSERVACIONES" + ";" + "REVISION_DETECTOR" + ";" + "ETA" + linea);
                    do {
                        String registro=c.getString(1)+ ";" +c.getString(2)+ ";" +c.getString(3)+ ";" +c.getString(4)+ ";" +c.getString(5)+ ";"+c.getString(6)+ ";" +c.getString(7)+ ";" ;
                        fout.write(registro+linea);
                    } while (c.moveToNext());
                    fout.close();
                    c.close();
                    db.close();
                    guardar3_registro_exportacion();
                    Toast.makeText(getBaseContext(), "DATOS EXPORTADOS "+FECHA_EXPORTACION3(), Toast.LENGTH_LONG).show();

                    //inicio();
                } catch (Exception ex) {
                    Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
                }

            }
        }
    }
    private void guardar_registro_exportacion(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("fecha_ultima", FECHA_EXPORTACION());

            //Insertamos el registro en la base de datos
            db.insert("REGISTRO", null, nuevoRegistro);
            //Toast.makeText(getBaseContext(), "registo guardado "+FECHA_EXPORTACION(), Toast.LENGTH_LONG).show();
            db.close();

        }
    }
    private void guardar2_registro_exportacion(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("fecha_ultima", FECHA_EXPORTACION2());

            //Insertamos el registro en la base de datos
            db.insert("REGISTROPUESTO", null, nuevoRegistro);
            //Toast.makeText(getBaseContext(), "registo guardado "+FECHA_EXPORTACION(), Toast.LENGTH_LONG).show();
            db.close();

        }
    }
    private void guardar3_registro_exportacion(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("fecha_ultima", FECHA_EXPORTACION3());

            //Insertamos el registro en la base de datos
            db.insert("REGISTROADM", null, nuevoRegistro);
            //Toast.makeText(getBaseContext(), "registo guardado "+FECHA_EXPORTACION(), Toast.LENGTH_LONG).show();
            db.close();

        }
    }
    private void guardar4_registro_exportacion(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("fecha_ultima", FECHA_EXPORTACION4());

            //Insertamos el registro en la base de datos
            db.insert("REGISTROINSMANUAL", null, nuevoRegistro);
            //Toast.makeText(getBaseContext(), "registo guardado "+FECHA_EXPORTACION(), Toast.LENGTH_LONG).show();
            db.close();

        }
    }
    public String FECHA_FICHERO(String f) {
        String fecha_reunion=f;
        String F_REUNION_FORMAT="_"+fecha_reunion.substring(6,10)+"_"+fecha_reunion.substring(3,5)+"_"+fecha_reunion.substring(0,2)+"_";
        return F_REUNION_FORMAT;}
    public String FECHAconformato() {
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        return dateString;
    }
    public String FECHA_EXPORTACION(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM inspeccionvs", null);
        String dateString ="";
        if (c != null) {
            c.moveToLast();
            //Toast.makeText(getBaseContext(), "FECHAS: "+c.getString(2), Toast.LENGTH_SHORT).show();

            dateString = c.getString(2);}

        return dateString;
    }
    public String FECHA_EXPORTACION2(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM VISITAPUESTO", null);
        String dateString ="";
        if (c != null) {
            c.moveToLast();
            //Toast.makeText(getBaseContext(), "FECHAS: "+c.getString(2), Toast.LENGTH_SHORT).show();

            dateString = c.getString(2);}

        return dateString;
    }
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
    public String FECHA_EXPORTACION4(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM inspeccionmanual", null);
        String dateString ="";
        if (c != null) {
            c.moveToLast();
            dateString = c.getString(3);}

        return dateString;
    }
    public String Fecha_exportacion_ultimo_registro(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM REGISTRO", null);
        String dateString="1";
        if (c.getCount()!=0) {
            c.moveToLast();
            //Toast.makeText(getBaseContext(), "FECHAS: "+c.getString(1), Toast.LENGTH_LONG).show();

            dateString = c.getString(1);}

        return dateString;
    }
    public String Fecha2_exportacion_ultimo_registro(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM REGISTROPUESTO", null);
        String dateString="1";
        if (c.getCount()!=0) {
            c.moveToLast();
            //Toast.makeText(getBaseContext(), "FECHAS: "+c.getString(1), Toast.LENGTH_LONG).show();

            dateString = c.getString(1);}

        return dateString;
    }
    public String Fecha3_exportacion_ultimo_registro(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM REGISTROADM", null);
        String dateString="1";
        if (c.getCount()!=0) {
            c.moveToLast();
            //Toast.makeText(getBaseContext(), "FECHAS: "+c.getString(1), Toast.LENGTH_LONG).show();

            dateString = c.getString(1);}

        return dateString;
    }
    public String Fecha4_exportacion_ultimo_registro(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM REGISTROINSMANUAL", null);
        String dateString="1";
        if (c.getCount()!=0) {
            c.moveToLast();
            //Toast.makeText(getBaseContext(), "FECHAS: "+c.getString(1), Toast.LENGTH_LONG).show();

            dateString = c.getString(1);}

        return dateString;
    }
    public void correccion(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if(db != null){
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("noconformidad", "CINTURILLA");
            db.insert("AENA", null, nuevoRegistro);
            nuevoRegistro.put("noconformidad", "CUELLO");
            db.insert("AENA", null, nuevoRegistro);
            //Insertamos el registro en la base de datos

            nuevoRegistro.put("noconformidad", "HOMBROS Y BRAZOS");
            db.insert("AENA", null, nuevoRegistro);
            nuevoRegistro.put("noconformidad", "TORSO Y ESPALDA");
            db.insert("AENA", null, nuevoRegistro);
            nuevoRegistro.put("noconformidad", "MUSLO ENTREPIERNAS Y PIERNA");
            db.insert("AENA", null, nuevoRegistro);
            nuevoRegistro.put("noconformidad", "EMPEINE-ZAPATO");
            db.insert("AENA", null, nuevoRegistro);
            nuevoRegistro.put("noconformidad", "NINGUNA");
            db.insert("AENA", null, nuevoRegistro);
            // Cerramos la base de datos
            db.close();}
    }
// botones
    public void Terminar(View view){
        finish();

    }
    public void inspeccionVS(View view){
        Intent i= new Intent(this, InspeccionesVS.class);
        startActivity(i);
    }
    public void inspeccionPuesto(View view){
        Intent i= new Intent(this,InspeccionPUESTO.class);
        //Intent i= new Intent(this,ej1.class);
        startActivity(i);
    }
    public void Configuracion(View view){
        final AlertDialog.Builder login= new AlertDialog.Builder(this);
        final EditText input= new EditText(this);
        input.setTextColor(Color.MAGENTA);
        login.setView(input);
        login.setTitle("EULEN SEGURIDAD");
        login.setMessage("CLAVE DE ACCESO:");
        login.setIcon(R.drawable.log_icon);
        login.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                Toast.makeText(MainActivity.this, "LOGIN CANCELADO", Toast.LENGTH_SHORT).show();

            }
        });
        login.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String pass = input.getText().toString().trim();
                if (pass.equals("barajas6")) {
                    Toast.makeText(MainActivity.this, "LOGIN CORRECTO", Toast.LENGTH_SHORT).show();
                    acceso_configuracion();
                }
            }
        });

        AlertDialog alertDialog= login.create();
        alertDialog.show();

    }
    public void EXPORTAR(View view){
        Intent i= new Intent(this,EXPORTAR.class);
        startActivity(i);
    }
    public void ENSAYO(View view){
        Intent i= new Intent(this,ENSAYO.class);
        startActivity(i);
    }
    public void ROPA(View view){
        Intent i= new Intent(this,ROPA.class);
        startActivity(i);
    }
    public void getVersionInfo() {

        T_VERSION=(TextView)findViewById(R.id.version);

        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        T_VERSION.setText("Code: "+versionName+" Version: "+versionCode);


    }
    public void CALIBRADO_ADM(View view){
       // Intent i= new Intent(this,pdfview.class);
       // startActivity(i);
        Toast.makeText(getBaseContext(), "NO DISPONIBLE" , Toast.LENGTH_SHORT).show();
    }
    public void Inspecciones_manuales(View view){
            Intent i= new Intent(this,Inspecciones_manuales.class);
        startActivity(i);
    }
    public void test(View view){
        Intent i= new Intent(this,MENU_TEST.class);
        startActivity(i);
    }
    public void FICHA(View view){
        final AlertDialog.Builder login2= new AlertDialog.Builder(this);
        final EditText input= new EditText(this);
        login2.setView(input);
        login2.setTitle("EULEN SEGURIDAD");
        login2.setMessage("CLAVE DE ACCESO:");
        login2.setIcon(R.drawable.log_icon);

        login2.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                Toast.makeText(MainActivity.this, "LOGIN CANCELADO", Toast.LENGTH_SHORT).show();

            }
        });
        login2.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String pass = input.getText().toString().trim();
                if (pass.equals("barajas6")) {
                    Toast.makeText(MainActivity.this, "LOGIN CORRECTO", Toast.LENGTH_SHORT).show();
                    acceso_fichas();
                }
            }
        });

        AlertDialog alertDialog= login2.create();
        alertDialog.show();

    }
    public void ORDENES_PUESTOS_VS(View view){
        Intent i = new Intent(this, listado_ordenes.class);
        i.putExtra("TITULO", "LISTADO ORDENES PUESTO VS");
        i.putExtra("tit", 1);
        startActivity(i);
    }
    public void ORDENES_PUESTOS_AUX(View view){
        Intent i = new Intent(this, listado_ordenes.class);
        i.putExtra("TITULO", "LISTADO ORDENES PUESTO AUXILIARES");
        i.putExtra("tit", 2);
        startActivity(i);
    }
    public void LISTADO_FORMACION(View view){
        Intent i = new Intent(this, listado_ordenes.class);
        i.putExtra("TITULO", "LISTADO FORMACION");
        i.putExtra("tit", 4);
        startActivity(i);
    }
    public void LISTADO_PROCEDIMIENTOS(View view){
        Intent i = new Intent(this, listado_ordenes.class);
        i.putExtra("TITULO", "LISTADO PROCEDIMIENTOS");
        i.putExtra("tit", 3);
        startActivity(i);
    }
    private void acceso_configuracion(){
        Intent i = new Intent(this, Configuracion.class);
        startActivity(i);
    }
    private void acceso_fichas(){
        Intent i= new Intent(this,Fichas_personal.class);
        startActivity(i);
    }
}
