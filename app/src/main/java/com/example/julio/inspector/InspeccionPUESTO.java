package com.example.julio.inspector;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.media.MediaRecorder.VideoSource.CAMERA;


public class InspeccionPUESTO extends AppCompatActivity implements OnItemSelectedListener,View.OnClickListener {
 //private final String RUTA_FOTOS= Environment.getExternalStorageDirectory();
 //private File file=new File(RUTA_FOTOS);
    private Button bt_hacerfoto,boton_GUARDAR_PUESTO,bt_REGISTROS;
    private Button PUESTO_PRIMER,PUESTO_ANTERIOR,PUESTO_SIGUIENTE,PUESTO_FIN;
    private TextView PUESTO_panel,tex_f1,text_f2,text_f3,titulo_puesto;
    private int countPUESTO,REG,COUNT_FOTO,n_REG;
    private ImageView img,IMGANTERIOR,imagen2,imagen3,imagen4;
    private Spinner INSPECTORP,PUESTOP,ADMP,RXP,DETECTORP,DETECTORP1,MZAPATOSP,BARRERAS,TORNOS,TRAZAS;
    private CheckBox Ccalibrado,Cotros,Ccorregido,Cprocedimiento,Cordenes_puesto;
    private String ID_DISPOSITIVO,SINSPECTORP,SHORAP,SFECHAP,SPUESTOP,SADMP,SRXP,sDETECTORP,sMZAPATOSP,sCALIBRADO,sOTROSP,sINCIDENCIAn,sINCIDENCIAa,sRIESGOS,sOBSERVACIONn,SRUTAFOTOn,SRUTAFOTOa,sCORREGIDO,sOBSERVACIONa,sPROCEDIMIENTO,sOREDENES_FIRMADAS,sBARRERAS,sTORNOS,sTRAZAS,sFOTO1,sFOTO2,sFOTO3,sFOTO4,SFOTO_ULTIMA;
    private EditText fechapuesto,horapuesto,INCIDENCIAVISITA,RIESGOS,OBSERVACIONVISITA,INCIDENCIANTERIOR,OBSERVACIONANTERIOR,PUESTO,INSPECTOR,PUESTO_rx,PUESTO_zapatos,PUESTO_liquidos,PUESTO_adm,PUESTO_torno,PUESTO_BARRERAS,PUESTO_TRAZAS;
    final private String RUTA_DRIVE="/fotospuesto/";
    private TipdbAdapter dbAdapterSituacion,DBADAPT;
    private Cursor cursor_puesto;
    public static final int REQUEST_CODE_TAKE_PHOTO = 0 /*1*/;
    private String currentPhotoPath,Nombre_foto;
    private Uri photoURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_puestos);

        INICIALIZAR();

    }
    private void INICIALIZAR(){

        //controles imagen
        img=(ImageView) findViewById(R.id.imageView_foto1);
        imagen2=(ImageView) findViewById(R.id.imageView_foto2);
        imagen3=(ImageView) findViewById(R.id.imageView_foto3);
        imagen4=(ImageView) findViewById(R.id.imageView_foto4);

        //controles EDITTEXT
        fechapuesto=(EditText) findViewById(R.id.PUESTOS_FECHA);
        horapuesto=(EditText) findViewById(R.id.PUESTOS_HORA);

        INCIDENCIAVISITA=(EditText) findViewById(R.id.e_PUESTOS_DESPERFECTOS);
        RIESGOS=(EditText) findViewById(R.id.e_PUESTOS_RIESGOS);
        OBSERVACIONVISITA=(EditText) findViewById(R.id.e_PUESTOS_OBSERVACIONES);

        PUESTO_panel=(TextView) findViewById(R.id.PUESTO_REG);
        titulo_puesto=(TextView) findViewById(R.id.titulo_puesto);


        //controles spiner
        ADMP = (Spinner) findViewById(R.id.s_PUESTOS_ADM);
        RXP = (Spinner) findViewById(R.id.s_PUESTOS_MAQUINARX);

        DETECTORP1= (Spinner) findViewById(R.id.s_PUESTOS_DETECTOR_LIQUIDOS);
        DETECTORP = (Spinner) findViewById(R.id.s_PUESTOS_DETECTOR_LIQUIDOS);
        MZAPATOSP = (Spinner) findViewById(R.id.s_PUESTOS_MAQUINA_ZAPATOS);
        INSPECTORP= (Spinner) findViewById(R.id.s_PUESTOS_INSPECTOR);
        PUESTOP= (Spinner) findViewById(R.id.s_PUESTOS_PUESTO);
        BARRERAS= (Spinner) findViewById(R.id.s_PUESTOS_BARRERAS);
        TORNOS= (Spinner) findViewById(R.id.s_PUESTOS_TORNOS);
        TRAZAS= (Spinner) findViewById(R.id.s_PUESTOS_TRAZAS);
        //controles check
        Ccalibrado = (CheckBox) findViewById(R.id.CK_CALIBRADO);
        Cotros= (CheckBox) findViewById(R.id.CK_OTROS);
        Ccorregido= (CheckBox) findViewById(R.id.CK_CORREGIDO);
        Cprocedimiento= (CheckBox) findViewById(R.id.CK_PROCEDIMIENTOS);
        Cordenes_puesto= (CheckBox) findViewById(R.id.CK_ORDENES);

        // CONTROLES NAVEGAR ENTRE REGISTROS
        PUESTO_PRIMER=(Button)findViewById(R.id.B_PUESTO_PRIMERO);
        PUESTO_ANTERIOR=(Button)findViewById(R.id.B_PUESTO_ANTERIOR);
        PUESTO_SIGUIENTE=(Button)findViewById(R.id.B_PUESTO_SIGUIENTE);
        PUESTO_FIN=(Button)findViewById(R.id.B_PUESTO_ULTIMO);
        //controles boton
        bt_hacerfoto=(Button)findViewById(R.id.btomafotoVISITA);
        boton_GUARDAR_PUESTO=(Button)findViewById(R.id.BOTON_PUESTOS_GUARDAR);
        bt_REGISTROS=(Button)findViewById(R.id.BOTON_PUESTOS_VER);

        listeners();

        NAVEGAR_PUESTO(1);

        ID_DISPOSITIVO= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        REG=1;
        COUNT_FOTO=1;
        SRUTAFOTOn="";

    }
    public void listeners(){
        final String[] DES5=getResources().getStringArray(R.array.DESPLEGABLE5);

        final String[] puesto = getResources().getStringArray(R.array.puestos);

        DBADAPT = new TipdbAdapter(this) ;
        DBADAPT.abrir();

        Cursor cursorINSPECTORES = DBADAPT.getINSPECTOR();
        Cursor cursorPuestos=DBADAPT.Lista_Puestos();

        SimpleCursorAdapter adapterINSPECTORES = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorINSPECTORES,(new String[] {"nombre"}), new int[] {android.R.id.text1},0);

        //final String[] ins=getResources().getStringArray(R.array.INSPECTORES);
        SimpleCursorAdapter adapterPuestos = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorPuestos,(new String[] {"puesto"}), new int[] {android.R.id.text1},0);

        //ArrayAdapter<String> adaptadorPUESTO = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, puesto);

        //ArrayAdapter<String> ADAPTADORINSPECTOR = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, ins);
        ArrayAdapter<String> DESPLEGABLE5 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, DES5);

        INSPECTORP.setAdapter(adapterINSPECTORES);
        INSPECTORP.setOnItemSelectedListener(this);
        ADMP.setAdapter(DESPLEGABLE5);
        ADMP.setOnItemSelectedListener(this);
        RXP.setAdapter(DESPLEGABLE5);
        RXP.setOnItemSelectedListener(this);
        DETECTORP.setAdapter(DESPLEGABLE5);
        //DETECTORP1.setAdapter(DESPLEGABLE5);
        DETECTORP.setOnItemSelectedListener(this);
        MZAPATOSP.setAdapter(DESPLEGABLE5);
        MZAPATOSP.setOnItemSelectedListener(this);
        BARRERAS.setAdapter(DESPLEGABLE5);
        BARRERAS.setOnItemSelectedListener(this);
        TORNOS.setAdapter(DESPLEGABLE5);
        TORNOS.setOnItemSelectedListener(this);
        TRAZAS.setAdapter(DESPLEGABLE5);
        TRAZAS.setOnItemSelectedListener(this);
        PUESTOP.setAdapter(adapterPuestos);

        PUESTOP.setOnItemSelectedListener(this);
    }

    public void botonTOMARFOTOVISITA(View v){
        File carpetaimagenes=new File(Environment.getExternalStorageDirectory(),RUTA_DRIVE);

        carpetaimagenes.mkdirs();
        String file=getCode()+".jpg";
        String ruta=Environment.getExternalStorageDirectory() +
                RUTA_DRIVE + file;
        if (COUNT_FOTO==1){
            SRUTAFOTOn=file.toString();
            SFOTO_ULTIMA=file.toString();
            bt_hacerfoto.setText("HACER FOTO 2/4");
            Toast.makeText(getBaseContext(), "FOTO (1/4) " , Toast.LENGTH_SHORT).show();
        }
        if (COUNT_FOTO==2){
            sFOTO2=file.toString();
            SFOTO_ULTIMA=file.toString();
            bt_hacerfoto.setText("HACER FOTO 3/4");
            Toast.makeText(getBaseContext(), "FOTO(2/4)  " , Toast.LENGTH_SHORT).show();
        }
        if (COUNT_FOTO==3){
            sFOTO3=file.toString();
            SFOTO_ULTIMA=file.toString();
            bt_hacerfoto.setText("HACER FOTO 4/4");
            Toast.makeText(getBaseContext(), "FOTO(3/4):  " + COUNT_FOTO, Toast.LENGTH_SHORT).show();
        }
        if (COUNT_FOTO==4){
            sFOTO4=file.toString();
            SFOTO_ULTIMA=file.toString();
            bt_hacerfoto.setVisibility(View.INVISIBLE);
            Toast.makeText(getBaseContext(), "FOTO(4/4)- ULTIMA FOTOGRAFIA  " + COUNT_FOTO+"", Toast.LENGTH_SHORT).show();
        }

        //SRUTAFOTOn=file.toString();

        Toast.makeText(getBaseContext(), "FOTO: " + file.toString(), Toast.LENGTH_SHORT).show();
        File mi_foto = new File( carpetaimagenes,file );
        try {
                            mi_foto.createNewFile();
                        } catch (IOException ex) {
                          Log.e("ERROR ", "Error:" + ex);
                        }
                    //
                    Uri uri = Uri.fromFile(mi_foto);
                      //Abre la camara para tomar la foto
                   Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   //Guarda imagen

                     cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                   //Retorna a la actividad


                    startActivityForResult(cameraIntent, 1);
                    COUNT_FOTO+=1;

          }
    public void boton_TOMARFOTO1(View v){

        COUNT_FOTO=1;//foto nº1

        TAKEFOTO();

    }
    public void boton_TOMARFOTO2(View v){

        COUNT_FOTO=2;

        TAKEFOTO();


    }
    public void boton_TOMARFOTO3(View v){

        COUNT_FOTO=3;

        TAKEFOTO();
    }
    public void boton_TOMARFOTO4(View v){

        COUNT_FOTO=4;

        TAKEFOTO();


    }
    private void TAKEFOTO(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
            }
        }
    }
    public void foto2(View v){
     if (Ccorregido.isChecked() || !SRUTAFOTOa.equals(null) || !SINSPECTORP.equals("")){
         bt_hacerfoto.setVisibility(View.VISIBLE);
         boton_GUARDAR_PUESTO.setVisibility(View.VISIBLE);
     } else if (!Ccorregido.isChecked()){bt_hacerfoto.setVisibility(View.INVISIBLE);
         boton_GUARDAR_PUESTO.setVisibility(View.INVISIBLE);
     }
    }
    public void SALIR(View v){
        finish();
    }
    public void VER_REGISTROS_PUESTOS(View v){
        Intent i= new Intent(this,ver_puestos.class);
        startActivity(i);
    }
    public void guardarREGISTRO (View V){

        sINCIDENCIAn=INCIDENCIAVISITA.getText().toString();
        sRIESGOS= RIESGOS.getText().toString();
        sOBSERVACIONn= OBSERVACIONVISITA.getText().toString();


        if (Cotros.isChecked()){
            sOTROSP="SI";
        }else {sOTROSP="NO";}
        if (Ccalibrado.isChecked()){
            sCALIBRADO="SI";
        }else {sCALIBRADO="NO";}
        if (Ccalibrado.isChecked()){
            sCALIBRADO="SI";
        }else {sCALIBRADO="NO";}
        if (Ccorregido.isChecked()){
            sCORREGIDO="SI";
        }else {sCORREGIDO="NO";}
        if (Cprocedimiento.isChecked()){
            sPROCEDIMIENTO="SI";
        }else {sPROCEDIMIENTO="NO";}
        if (Cordenes_puesto.isChecked()){
            sOREDENES_FIRMADAS="SI";
        }else {sOREDENES_FIRMADAS="NO";}

        if (SPUESTOP.equals("") || SINSPECTORP.equals("")){

            Toast.makeText(getBaseContext(), "CAMPOS INSPECTOR Y/O PUESTO VACIOS", Toast.LENGTH_SHORT).show();

        } else {

        if (sFOTO1.equals("")){

            Toast.makeText(getBaseContext(), "FALTA TOMAR UNA FOTOGRAFIA", Toast.LENGTH_SHORT).show();

        }else{

            INSERTARVISITAPUESTO(sFOTO1,SRUTAFOTOa);

            }}
    }
    private void limpieza(){
        fechapuesto.setText("");
        horapuesto.setText("");
    }
    private String getCode()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String date = dateFormat.format(new Date() );
        String photoCode = "PUESTO_" + date+"_"+ID_DISPOSITIVO;
        return photoCode;
    }
    public String getAbsolutePath(Uri uri) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
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
    private String mesyano(String FECHA){
        String mmes="",anno="",mesyaNo="";
        try{

            DateFormat sdf1= new SimpleDateFormat(("MM"));

            Date mes= sdf1.parse(FECHA);
             anno = FECHA.substring(6);
             mmes = FECHA.substring(3,5);

        }catch (ParseException e){System.out.println(e.getMessage());}
       switch (mmes){
           case "01":
               mesyaNo="ENERO "+anno;
               break;
           case "02":
               mesyaNo="FEBRERO "+anno;
               break;
           case "03":
               mesyaNo="MARZO "+anno;
               break;
           case "04":
               mesyaNo="ABRIL "+anno;
               break;
           case "05":
               mesyaNo="MAYO "+anno;
               break;
           case "06":
               mesyaNo="JUNIO "+anno;
               break;
           case "07":
               mesyaNo="JULIO "+anno;
               break;
           case "08":
               mesyaNo="AGOSTO "+anno;
               break;
           case "09":
               mesyaNo="SEPTIEMBRE "+anno;
               break;
           case "10":
               mesyaNo="OCTUBRE "+anno;
               break;
           case "11":
               mesyaNo="NOVIEMBRE "+anno;
               break;
           case "12":
               mesyaNo="DICIEMBRE "+anno;
               break;
       }


        return mesyaNo;
    }
    private void PONER_FECHA_y_HORA(){

        horapuesto.setText(HORAconformato());
        fechapuesto.setText(FECHAconformato());

        SHORAP=horapuesto.getText().toString();
        SFECHAP=fechapuesto.getText().toString();
    }
    private void POSICIONES_ELEMENTOS_AUX(String PUESTO){
        switch (PUESTO) {
            case "Acceso Central (antiguo Alaman)":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(0);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(4);

                break;
            case "Acceso Este (antiguo Iberswiss)":
                ADMP.setSelection(4);
                RXP.setSelection(4);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(4);
                BARRERAS.setSelection(0);
                TORNOS.setSelection(4);
                TRAZAS.setSelection(4);

                break;
            case "Acceso Norte (CLH)":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(0);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(4);

                break;

            case "Acceso Porton APM":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(0);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(4);

                break;
            case "Acceso Sur (antiguo Modular)":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(4);
                BARRERAS.setSelection(0);
                TORNOS.setSelection(4);
                TRAZAS.setSelection(4);

                break;

            case "Acceso Túnel Cela":
                ADMP.setSelection(4);
                RXP.setSelection(4);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(4);
                BARRERAS.setSelection(0);
                TORNOS.setSelection(4);
                TRAZAS.setSelection(4);

                break;
            case "Central Eléctrica CELT":
                ADMP.setSelection(4);
                RXP.setSelection(4);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(4);
                BARRERAS.setSelection(0);
                TORNOS.setSelection(4);
                TRAZAS.setSelection(4);

                break;
            case "CLASA. Acceso Norte":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(4);

                break;
            case "CLASA. Acceso Sur":
                ADMP.setSelection(4);
                RXP.setSelection(4);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(4);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(4);

                break;
            case "Dique Sur Planta 1*":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(4);

                break;
            case "Edificio CLASA":
                ADMP.setSelection(4);
                RXP.setSelection(4);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(4);
                BARRERAS.setSelection(0);
                TORNOS.setSelection(4);
                TRAZAS.setSelection(4);

                break;
            case "Empleados T 2":
                ADMP.setSelection(4);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(4);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(4);
                TRAZAS.setSelection(4);

                break;
            case "Estacionamiento Norte":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(0);
                TORNOS.setSelection(4);
                TRAZAS.setSelection(0);

                break;
            case "Incidencias CI (CGA)":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(4);

                break;
            case "Filtro Vip T4":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(4);

                break;
            case "Filtro Empleados T4 P0":
                ADMP.setSelection(4);
                RXP.setSelection(4);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(4);
                BARRERAS.setSelection(0);
                TORNOS.setSelection(4);
                TRAZAS.setSelection(4);

                break;
            case "Filtro T3":
                ADMP.setSelection(4);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(4);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(4);
                TRAZAS.setSelection(4);

                break;
            case "Parking VIP":
                ADMP.setSelection(4);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(4);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(4);
                TRAZAS.setSelection(4);

                break;
            case "APM":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(4);

                break;
            case "PORTON APM":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(0);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(0);

                break;
            case "Patrulla ZR":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(0);

                break;
            case "Patrullas Perimetrales":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(0);

                break;
            case "":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(4);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(4);

                break;
            case "Plan Barajas":
                ADMP.setSelection(0);
                RXP.setSelection(0);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(0);
                BARRERAS.setSelection(0);
                TORNOS.setSelection(0);
                TRAZAS.setSelection(4);

                break;
            default:
                ADMP.setSelection(4);
                RXP.setSelection(4);
                DETECTORP.setSelection(4);
                MZAPATOSP.setSelection(4);
                BARRERAS.setSelection(4);
                TORNOS.setSelection(4);
                TRAZAS.setSelection(4);

                break;
        }
    }
    public void INSERTARVISITAPUESTO(String foto1,String foto2){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if(db != null){
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("inspector", SINSPECTORP);//1
            nuevoRegistro.put("fecha",SFECHAP);//2
            nuevoRegistro.put("hora", SHORAP);//3
            nuevoRegistro.put("puesto", SPUESTOP);//4
            nuevoRegistro.put("adm", SADMP);//5
            nuevoRegistro.put("calibradoADM", sCALIBRADO);//6
            nuevoRegistro.put("maquinaRX",SRXP);//7
            nuevoRegistro.put("detectorLIQUIDOS", sDETECTORP);//8
            nuevoRegistro.put("detectoZAPATOS", sMZAPATOSP);//9
            nuevoRegistro.put("otros", sOTROSP);//10
            nuevoRegistro.put("incidencia", sINCIDENCIAn);//11
            nuevoRegistro.put("riesgos",sRIESGOS);//12
            nuevoRegistro.put("rutaFOTO", foto1);//foto de la visita (13)
            nuevoRegistro.put("incidenciaANTERIOR", sINCIDENCIAa);//14
            nuevoRegistro.put("corregidaINCIDENCIA", sCORREGIDO);//15
            nuevoRegistro.put("rutaFOTOanterior", foto2);// foto de visita anterior (16)
            nuevoRegistro.put("observacionesANTERIOR", sOBSERVACIONa);//17
            nuevoRegistro.put("observacion", sOBSERVACIONn);//18
            nuevoRegistro.put("PROCEDIMIENTO", sPROCEDIMIENTO);//19
            nuevoRegistro.put("ORDENESFIRMADAS", sOREDENES_FIRMADAS);//20
            nuevoRegistro.put("rutaFOTO2",  sFOTO2);// 21
            nuevoRegistro.put("rutaFOTO3",  sFOTO3);//22
            nuevoRegistro.put("rutaFOTO4",  sFOTO4);//23
            nuevoRegistro.put("BARRERAS",sBARRERAS  );// 24 sBARRERAS
            nuevoRegistro.put("TORNOS", sTORNOS );//25 sTORNOS
            nuevoRegistro.put("TRAZAS", sTRAZAS );//26 sTRAZAS
            nuevoRegistro.put("MESYANO", mesyano(SFECHAP) );//28 sTRAZAS

            //Insertamos el registro en la base de datos
            db.insert("VISITAPUESTO", null, nuevoRegistro);
            Log.i(this.getClass().toString(), "INSERTADO NUEVO REGISTRO");
            // Cerramos la base de datos
            db.close();

            Toast.makeText(getBaseContext(), "DATOS GUARDADOS ", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private void FOTODESPERFECTOS_ANTERIORES(String FOTODESDEsql){
        BasedbHelper usdbh = new BasedbHelper(this);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM VISITAPUESTO WHERE puesto='" + FOTODESDEsql + "'", null);
        if (cursor.moveToLast()) {

        String NOMBREdeFOTO1=cursor.getString(13);
        String NOMBREdeFOTO2=cursor.getString(16);
        String CORRECION=cursor.getString(15);
        String INCIDENCIA=cursor.getString(11);
        String OBSERVACION=cursor.getString(18);
            if (NOMBREdeFOTO1==null){NOMBREdeFOTO1=NOMBREdeFOTO2;}
            if (CORRECION.equals("NO")){
        Bitmap foto2 = BitmapFactory.decodeFile(
                Environment.getExternalStorageDirectory() +
                        "/DCIM/Camera/" + NOMBREdeFOTO1);
                IMGANTERIOR.setImageBitmap(foto2);
                INCIDENCIANTERIOR.setText(INCIDENCIA);
                OBSERVACIONANTERIOR.setText(OBSERVACION);
                SRUTAFOTOa= NOMBREdeFOTO1;
                boton_GUARDAR_PUESTO.setVisibility(View.VISIBLE);
                tex_f1.setVisibility(View.INVISIBLE);
                text_f2.setVisibility(View.INVISIBLE);
                text_f3.setVisibility(View.INVISIBLE);
        cursor.close();
        db.close();}else if(CORRECION.equals("SI")){IMGANTERIOR.setImageResource(R.drawable.fondo_foto);
            SRUTAFOTOa="";
                bt_hacerfoto.setVisibility(View.VISIBLE);
                }
            } else {
            IMGANTERIOR.setImageResource(R.drawable.fondo_foto);
            SRUTAFOTOa="";
            bt_hacerfoto.setVisibility(View.VISIBLE);
            boton_GUARDAR_PUESTO.setVisibility(View.VISIBLE);
        }
    }
    public void recycle(View v){
        Intent i= new Intent(this,Anime_cardview.class);
        startActivity(i);
    }
    public void BOTON_PRIMER(View v){
        NAVEGAR_PUESTO(2);
    }
    public void BOTON_ULTIMO(View v){
        NAVEGAR_PUESTO(3);
    }
    public void BOTON_SIGUIENTE(View v){
        NAVEGAR_PUESTO(4);
    }
    public void BOTON_ANTERIOR(View v){
        NAVEGAR_PUESTO(5);
    }
    public void VER_REGISTROS(View v){
        if (n_REG==0){Toast.makeText(getBaseContext(), "SIN REGISTROS...", Toast.LENGTH_SHORT).show();
        }else if (REG==1){
        PUESTO_PRIMER.setVisibility(View.VISIBLE);
        PUESTO_ANTERIOR.setVisibility(View.VISIBLE);
        PUESTO_SIGUIENTE.setVisibility(View.VISIBLE);
        PUESTO_FIN.setVisibility(View.VISIBLE);
            PUESTO_torno.setVisibility(View.VISIBLE);
            PUESTO_BARRERAS.setVisibility(View.VISIBLE);
            PUESTO_TRAZAS.setVisibility(View.VISIBLE);
        boton_GUARDAR_PUESTO.setVisibility(View.INVISIBLE);
            ADMP.setVisibility(View.INVISIBLE);
            RXP.setVisibility(View.INVISIBLE);
            DETECTORP.setVisibility(View.INVISIBLE);
            MZAPATOSP.setVisibility(View.INVISIBLE);
            PUESTO_rx.setVisibility(View.VISIBLE);
            PUESTO_zapatos.setVisibility(View.VISIBLE);
            PUESTO_liquidos.setVisibility(View.VISIBLE);
            PUESTO_adm.setVisibility(View.VISIBLE);

            imagen2.setVisibility(View.VISIBLE);
            imagen3.setVisibility(View.VISIBLE);
            imagen4.setVisibility(View.VISIBLE);

        NAVEGAR_PUESTO(3);
            REG=2;
            bt_REGISTROS.setText("Registro NUEVO");
        }else if (REG==2){
            PUESTO_PRIMER.setVisibility(View.INVISIBLE);
            PUESTO_ANTERIOR.setVisibility(View.INVISIBLE);
            PUESTO_SIGUIENTE.setVisibility(View.INVISIBLE);
            PUESTO_FIN.setVisibility(View.INVISIBLE);
            boton_GUARDAR_PUESTO.setVisibility(View.INVISIBLE);
            bt_REGISTROS.setText("VER REGISTROS");
            ADMP.setVisibility(View.VISIBLE);
            RXP.setVisibility(View.VISIBLE);
            DETECTORP.setVisibility(View.VISIBLE);
            MZAPATOSP.setVisibility(View.VISIBLE);
            PUESTO_rx.setVisibility(View.INVISIBLE);
            PUESTO_zapatos.setVisibility(View.INVISIBLE);
            PUESTO_liquidos.setVisibility(View.INVISIBLE);
            PUESTO_adm.setVisibility(View.INVISIBLE);
            PUESTO_torno.setVisibility(View.INVISIBLE);
            PUESTO_BARRERAS.setVisibility(View.INVISIBLE);
            PUESTO_TRAZAS.setVisibility(View.INVISIBLE);
            tex_f1.setVisibility(View.INVISIBLE);
            text_f2.setVisibility(View.INVISIBLE);
            text_f3.setVisibility(View.INVISIBLE);
            imagen2.setVisibility(View.INVISIBLE);
            imagen3.setVisibility(View.INVISIBLE);
            imagen4.setVisibility(View.INVISIBLE);
            IMGANTERIOR.setImageResource(R.drawable.fondo_foto);
            img.setImageResource(R.drawable.fondo_foto);
            limpieza();
            REG=1;
            NAVEGAR_PUESTO(1);
        }

    }
    private void NAVEGAR_PUESTO(int n){
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM VISITAPUESTO ", null);
        if (n==1){
            countPUESTO=0;
            //INSPECTORP.setVisibility(View.VISIBLE);
           //PUESTOP.setVisibility(View.VISIBLE);
           // INSPECTOR.setVisibility(View.INVISIBLE);
           // PUESTO.setVisibility(View.INVISIBLE);
            INCIDENCIAVISITA.setText("");
            RIESGOS.setText("");
            OBSERVACIONVISITA.setText("");
            //INCIDENCIANTERIOR.setText("");
            //OBSERVACIONANTERIOR.setText("");
            Ccalibrado.setChecked(false);
            Cotros.setChecked(false);
            Ccorregido.setChecked(false);
            n_REG=cursor.getCount();
            PUESTO_panel.setText("REGISTROS: " + String.valueOf(cursor.getCount()));}
        if (n==2){
            cursor.moveToFirst();//al primer registro

            countPUESTO= cursor.getPosition();
            PUESTO_panel.setText("REGISTRO:" + (countPUESTO + 1) + "/" + String.valueOf(cursor.getCount()));

            INSPECTORP.setVisibility(View.INVISIBLE);
            PUESTOP.setVisibility(View.INVISIBLE);
            INSPECTOR.setVisibility(View.VISIBLE);
            PUESTO.setVisibility(View.VISIBLE);


            INSPECTOR.setText(cursor.getString(1));
            PUESTO.setText(cursor.getString(4));
            fechapuesto.setText(cursor.getString(2));
            horapuesto.setText(cursor.getString(3));

            PUESTO_rx.setText(cursor.getString(7));
            PUESTO_zapatos.setText(cursor.getString(9));
            PUESTO_liquidos.setText(cursor.getString(8));
            PUESTO_adm.setText(cursor.getString(5));

            PUESTO_torno.setText(cursor.getString(25));
            PUESTO_BARRERAS.setText(cursor.getString(24));
            PUESTO_TRAZAS.setText(cursor.getString(26));

            if ((cursor.getString(6)).equals("SI")){
                Ccalibrado.setChecked(true);
            }else {Ccalibrado.setChecked(false);}
            if ((cursor.getString(11)).equals("SI")){
                Cotros.setChecked(true);
            }else {Cotros.setChecked(false);}
            if ((cursor.getString(15)).equals("SI")){
                Ccorregido.setChecked(true);
            }else{Ccorregido.setChecked(false);}

            if ((cursor.getString(19)).equals("SI")){
                Cprocedimiento.setChecked(true);
            }else{Cprocedimiento.setChecked(false);}
            if ((cursor.getString(20)).equals("SI")){
                Cordenes_puesto.setChecked(true);
            }else{Cordenes_puesto.setChecked(false);}


            INCIDENCIAVISITA.setText(cursor.getString(11));
            RIESGOS.setText(cursor.getString(12));
            OBSERVACIONVISITA.setText(cursor.getString(18));
            INCIDENCIANTERIOR.setText(cursor.getString(14));
            OBSERVACIONANTERIOR.setText(cursor.getString(17));
            String NOMBREdeFOTO1=cursor.getString(16);
            String NOMBREdeFOTO2=cursor.getString(13);
            String NOMBREdeFOTO3=cursor.getString(21);
            String NOMBREdeFOTO4=cursor.getString(22);
            String NOMBREdeFOTO5=cursor.getString(23);
            Bitmap bMap1 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO1);
            IMGANTERIOR.setImageBitmap(bMap1);
            Bitmap bMap2 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO2);
            img.setImageBitmap(bMap2);
            Bitmap bMap3 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO3);
            imagen2.setImageBitmap(bMap3);
            Bitmap bMap4 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO4);
            imagen3.setImageBitmap(bMap4);
            Bitmap bMap5 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO5);
            imagen4.setImageBitmap(bMap5);
            PUESTO_ANTERIOR.setClickable(false);
            PUESTO_ANTERIOR.setEnabled(false);
            if (countPUESTO+1== (cursor.getCount())) {
                PUESTO_SIGUIENTE.setClickable(false);
                PUESTO_SIGUIENTE.setEnabled(false);
            }else{
            PUESTO_SIGUIENTE.setClickable(true);
            PUESTO_SIGUIENTE.setEnabled(true);}

        }
        if (n==3){
            cursor.moveToLast();//al ultimo registro

            countPUESTO= cursor.getPosition();
            PUESTO_panel.setText("REGISTRO:" + (countPUESTO + 1) + "/" + String.valueOf(cursor.getCount()));

            INSPECTORP.setVisibility(View.INVISIBLE);
            PUESTOP.setVisibility(View.INVISIBLE);
            INSPECTOR.setVisibility(View.VISIBLE);
            PUESTO.setVisibility(View.VISIBLE);
            INSPECTOR.setText(cursor.getString(1));
            PUESTO.setText(cursor.getString(4));
            fechapuesto.setText(cursor.getString(2));
            horapuesto.setText(cursor.getString(3));

            PUESTO_rx.setText(cursor.getString(7));
            PUESTO_zapatos.setText(cursor.getString(9));
            PUESTO_liquidos.setText(cursor.getString(8));
            PUESTO_adm.setText(cursor.getString(5));

            PUESTO_torno.setText(cursor.getString(25));
            PUESTO_BARRERAS.setText(cursor.getString(24));
            PUESTO_TRAZAS.setText(cursor.getString(26));

            if ((cursor.getString(6)).equals("SI")){
                Ccalibrado.setChecked(true);
            }else {Ccalibrado.setChecked(false);}
            if ((cursor.getString(10)).equals("SI")){
                Cotros.setChecked(true);
            }else {Cotros.setChecked(false);}
            if ((cursor.getString(15)).equals("SI")){
                Ccorregido.setChecked(true);
            }else{Ccorregido.setChecked(false);}
            if ((cursor.getString(19)).equals("SI")){
                Cprocedimiento.setChecked(true);
            }else{Cprocedimiento.setChecked(false);}
            if ((cursor.getString(20)).equals("SI")){
                Cordenes_puesto.setChecked(true);
            }else{Cordenes_puesto.setChecked(false);}
            INCIDENCIAVISITA.setText(cursor.getString(11));
            RIESGOS.setText(cursor.getString(12));
            OBSERVACIONVISITA.setText(cursor.getString(18));
            INCIDENCIANTERIOR.setText(cursor.getString(14));
            OBSERVACIONANTERIOR.setText(cursor.getString(17));
            //get fotos
            String NOMBREdeFOTO1=cursor.getString(16);
            String NOMBREdeFOTO2=cursor.getString(13);
            String NOMBREdeFOTO3=cursor.getString(21);
            String NOMBREdeFOTO4=cursor.getString(22);
            String NOMBREdeFOTO5=cursor.getString(23);
            Bitmap bMap1 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO1);
            IMGANTERIOR.setImageBitmap(bMap1);
            Bitmap bMap2 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO2);
            img.setImageBitmap(bMap2);
            Bitmap bMap3 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO3);
            imagen2.setImageBitmap(bMap3);
            Bitmap bMap4 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO4);
            imagen3.setImageBitmap(bMap4);
            Bitmap bMap5 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO5);
            imagen4.setImageBitmap(bMap5);
            //control de margen de registros
            if ((countPUESTO+1)==1 & cursor.getCount()==1){
                PUESTO_ANTERIOR.setClickable(false);
                PUESTO_ANTERIOR.setEnabled(false);
            }else {
            PUESTO_ANTERIOR.setClickable(true);
            PUESTO_ANTERIOR.setEnabled(true);}
            PUESTO_SIGUIENTE.setClickable(false);
            PUESTO_SIGUIENTE.setEnabled(false);

        }
        if (n==4){
            countPUESTO += 1;
            cursor.moveToPosition(countPUESTO);//al siguiente registro

            PUESTO_panel.setText("REGISTRO:" + (countPUESTO + 1) + "/" + String.valueOf(cursor.getCount()));

            INSPECTORP.setVisibility(View.INVISIBLE);
            PUESTOP.setVisibility(View.INVISIBLE);
            INSPECTOR.setVisibility(View.VISIBLE);
            PUESTO.setVisibility(View.VISIBLE);
            INSPECTOR.setText(cursor.getString(1));
            PUESTO.setText(cursor.getString(4));
            fechapuesto.setText(cursor.getString(2));
            horapuesto.setText(cursor.getString(3));

            PUESTO_rx.setText(cursor.getString(7));
            PUESTO_zapatos.setText(cursor.getString(9));
            PUESTO_liquidos.setText(cursor.getString(8));
            PUESTO_adm.setText(cursor.getString(5));

            PUESTO_torno.setText(cursor.getString(25));
            PUESTO_BARRERAS.setText(cursor.getString(24));
            PUESTO_TRAZAS.setText(cursor.getString(26));

            if ((cursor.getString(6)).equals("SI")){
                Ccalibrado.setChecked(true);
            }else {Ccalibrado.setChecked(false);}
            if ((cursor.getString(10)).equals("SI")){
                Cotros.setChecked(true);
            }else {Cotros.setChecked(false);}
            if ((cursor.getString(15)).equals("SI")){
                Ccorregido.setChecked(true);
            }else{Ccorregido.setChecked(false);}
            if ((cursor.getString(19)).equals("SI")){
                Cprocedimiento.setChecked(true);
            }else{Cprocedimiento.setChecked(false);}
            if ((cursor.getString(20)).equals("SI")){
                Cordenes_puesto.setChecked(true);
            }else{Cordenes_puesto.setChecked(false);}
            INCIDENCIAVISITA.setText(cursor.getString(11));
            RIESGOS.setText(cursor.getString(12));
            OBSERVACIONVISITA.setText(cursor.getString(18));
            INCIDENCIANTERIOR.setText(cursor.getString(14));
            OBSERVACIONANTERIOR.setText(cursor.getString(17));
            //get fotos
            String NOMBREdeFOTO1=cursor.getString(16);
            String NOMBREdeFOTO2=cursor.getString(13);
            String NOMBREdeFOTO3=cursor.getString(21);
            String NOMBREdeFOTO4=cursor.getString(22);
            String NOMBREdeFOTO5=cursor.getString(23);
            Bitmap bMap1 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO1);
            IMGANTERIOR.setImageBitmap(bMap1);
            Bitmap bMap2 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO2);
            img.setImageBitmap(bMap2);
            Bitmap bMap3 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO3);
            imagen2.setImageBitmap(bMap3);
            Bitmap bMap4 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO4);
            imagen3.setImageBitmap(bMap4);
            Bitmap bMap5 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO5);
            imagen4.setImageBitmap(bMap5);
            //control de margen de registros
            PUESTO_PRIMER.setClickable(true);
            PUESTO_ANTERIOR.setClickable(true);
            PUESTO_ANTERIOR.setEnabled(true);
            if (countPUESTO+1== (cursor.getCount())) {
                PUESTO_SIGUIENTE.setClickable(false);
                PUESTO_SIGUIENTE.setEnabled(false);
            }
            PUESTO_FIN.setClickable(true);
        }
        if (n==5){
            countPUESTO -= 1;
            cursor.moveToPosition(countPUESTO);//al ANTERIOR registro

            PUESTO_panel.setText("REGISTRO:"+ (countPUESTO + 1) + "/" + String.valueOf(cursor.getCount()));

            INSPECTORP.setVisibility(View.INVISIBLE);
            PUESTOP.setVisibility(View.INVISIBLE);
            INSPECTOR.setVisibility(View.VISIBLE);
            PUESTO.setVisibility(View.VISIBLE);
            INSPECTOR.setText(cursor.getString(1));
            PUESTO.setText(cursor.getString(4));
            fechapuesto.setText(cursor.getString(2));
            horapuesto.setText(cursor.getString(3));

            PUESTO_rx.setText(cursor.getString(7));
            PUESTO_zapatos.setText(cursor.getString(9));
            PUESTO_liquidos.setText(cursor.getString(8));
            PUESTO_adm.setText(cursor.getString(5));

            PUESTO_torno.setText(cursor.getString(25));
            PUESTO_BARRERAS.setText(cursor.getString(24));
            PUESTO_TRAZAS.setText(cursor.getString(26));

            if ((cursor.getString(6)).equals("SI")){
                Ccalibrado.setChecked(true);
            }else {Ccalibrado.setChecked(false);}
            if ((cursor.getString(10)).equals("SI")){
                Cotros.setChecked(true);
            }else {Cotros.setChecked(false);}
            if ((cursor.getString(15)).equals("SI")){
                Ccorregido.setChecked(true);
            }else{Ccorregido.setChecked(false);}
            if ((cursor.getString(19)).equals("SI")){
                Cprocedimiento.setChecked(true);
            }else{Cprocedimiento.setChecked(false);}
            if ((cursor.getString(20)).equals("SI")){
                Cordenes_puesto.setChecked(true);
            }else{Cordenes_puesto.setChecked(false);}
            INCIDENCIAVISITA.setText(cursor.getString(11));
            RIESGOS.setText(cursor.getString(12));
            OBSERVACIONVISITA.setText(cursor.getString(18));
            INCIDENCIANTERIOR.setText(cursor.getString(14));
            OBSERVACIONANTERIOR.setText(cursor.getString(17));
            //get fotos
            String NOMBREdeFOTO1=cursor.getString(16);
            String NOMBREdeFOTO2=cursor.getString(13);
            String NOMBREdeFOTO3=cursor.getString(21);
            String NOMBREdeFOTO4=cursor.getString(22);
            String NOMBREdeFOTO5=cursor.getString(23);
            Bitmap bMap1 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO1);
            IMGANTERIOR.setImageBitmap(bMap1);
            Bitmap bMap2 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO2);
            img.setImageBitmap(bMap2);
            Bitmap bMap3 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO3);
            imagen2.setImageBitmap(bMap3);
            Bitmap bMap4 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO4);
            imagen3.setImageBitmap(bMap4);
            Bitmap bMap5 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/DCIM/Camera/" + NOMBREdeFOTO5);
            imagen4.setImageBitmap(bMap5);
            //control de margen de registros
            PUESTO_PRIMER.setClickable(true);
            PUESTO_SIGUIENTE.setClickable(true);
            PUESTO_SIGUIENTE.setEnabled(true);
            if (countPUESTO==0) {
                PUESTO_ANTERIOR.setClickable(false);
                PUESTO_ANTERIOR.setEnabled(false);
            }
            PUESTO_FIN.setClickable(true);
        }
    }
    private void COMPROBAR_INSPECCION(String puesto){
        dbAdapterSituacion = new TipdbAdapter(this) ;
        dbAdapterSituacion.abrir();
        cursor_puesto=dbAdapterSituacion.PUESTO(mesyano(FECHAconformato()));
         if(cursor_puesto.getCount()>0){
           cursor_puesto.moveToFirst();

            if (cursor_puesto.getString(4).equals(puesto)){
                titulo_puesto.setText("PUESTO (ya ha sido inspeccionado este mes)");
                titulo_puesto.setTextColor(Color.RED);
                mensajes();
                //Toast.makeText(getBaseContext(), "EL PUESTO, "+puesto+" YA HA SIDO INSPECCIONADO EN: "+mesyano(FECHAconformato()), Toast.LENGTH_LONG).show();
            }else{
                titulo_puesto.setText("PUESTO SIN INSPECCIONAR");
                titulo_puesto.setTextColor(Color.GRAY);
            }

       }
    }
    private void mensajes(){
        final AlertDialog.Builder mensajes= new AlertDialog.Builder(this);

        mensajes.setTitle("ATENCION PUESTO YA INSPECCIONADO ");
        //mensajes.setIcon(R.drawable.notification_template_icon_bg);
        mensajes.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
        mensajes.setMessage("EL PUESTO:\n " + SPUESTOP + "\n YA HA SIDO INSPECCIONADO EN " + mesyano(FECHAconformato()) + "\n\n INSPECCIONAR \bSOLO SI HAY UNA INCIDENCIA NUEVA");
        mensajes.setCancelable(true);
        mensajes.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {


                Toast.makeText(getBaseContext(), "INSPECIONAR PUESTO SOLO SI LO CONSIDERA OPORTUNO", Toast.LENGTH_SHORT).show();


            }
        });
        AlertDialog alertDialog= mensajes.create();
        alertDialog.show();
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                photoURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                //Uri photoURI = FileProvider.getUriForFile(AddActivity.this, "com.example.android.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
               // Toast.makeText(getBaseContext(), "foto: "+, Toast.LENGTH_LONG).show();
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "PUESTO_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        Nombre_foto=imageFileName+".jpg";

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents


        currentPhotoPath = image.getAbsolutePath();

        //Toast.makeText(getBaseContext(), "foto: "+currentPhotoPath, Toast.LENGTH_LONG).show();
        return image;
    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + TipdbAdapter.R_CARPETA_EXPORTACION);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
             Nombre_foto="PUESTO_"+ Calendar.getInstance().getTimeInMillis() + ".jpg";

            File f = new File(wallpaperDirectory, Nombre_foto);
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();

            new COPIAR_ARCHIVOS(Environment.getExternalStorageDirectory() + TipdbAdapter.R_CARPETA_EXPORTACION + Nombre_foto, Environment.getExternalStorageDirectory()+TipdbAdapter.R_RUTA_FOTOS + Nombre_foto);

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Comprovamos que la foto se a realizado
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        //String selectedImagePath = getAbsolutePath(data.getData());

        //Nombre_foto="PUESTO_"+ Calendar.getInstance().getTimeInMillis() + ".jpg";

            switch (COUNT_FOTO) {
                case 1:

                    Bitmap thumbnail = BitmapFactory.decodeFile(currentPhotoPath);

                    img.setImageBitmap(thumbnail);

                   //saveImage(thumbnail);

                    sFOTO1=Nombre_foto;

                    new COPIAR_ARCHIVOS( currentPhotoPath,Environment.getExternalStorageDirectory() + TipdbAdapter.R_CARPETA_EXPORTACION + Nombre_foto);
                    new COPIAR_ARCHIVOS( currentPhotoPath,Environment.getExternalStorageDirectory() + TipdbAdapter.R_RUTA_FOTOS + Nombre_foto);

                    Toast.makeText(getBaseContext(), "FOTO GUARDADA!", Toast.LENGTH_LONG).show();

                    break;
                case 2:

                    Bitmap thumbnail2 = BitmapFactory.decodeFile(currentPhotoPath);

                    imagen2.setImageBitmap(thumbnail2);

                    //saveImage(thumbnail2);

                    sFOTO2=Nombre_foto;

                    new COPIAR_ARCHIVOS( currentPhotoPath,Environment.getExternalStorageDirectory() + TipdbAdapter.R_CARPETA_EXPORTACION + Nombre_foto);
                    new COPIAR_ARCHIVOS( currentPhotoPath,Environment.getExternalStorageDirectory() + TipdbAdapter.R_RUTA_FOTOS + Nombre_foto);


                    Toast.makeText(getBaseContext(), "FOTO GUARDADA!", Toast.LENGTH_LONG).show();

                    break;
                case 3:

                    Bitmap thumbnail3 = BitmapFactory.decodeFile(currentPhotoPath);

                    imagen3.setImageBitmap(thumbnail3);

                    //saveImage(thumbnail3);

                    sFOTO3=Nombre_foto;

                    new COPIAR_ARCHIVOS( currentPhotoPath,Environment.getExternalStorageDirectory() + TipdbAdapter.R_CARPETA_EXPORTACION + Nombre_foto);
                    new COPIAR_ARCHIVOS( currentPhotoPath,Environment.getExternalStorageDirectory() + TipdbAdapter.R_RUTA_FOTOS + Nombre_foto);


                    Toast.makeText(getBaseContext(), "FOTO GUARDADA!", Toast.LENGTH_LONG).show();

                       break;
                case 4:

                    Bitmap thumbnail4 = BitmapFactory.decodeFile(currentPhotoPath);

                    imagen4.setImageBitmap(thumbnail4);

                    //saveImage(thumbnail4);

                    sFOTO4=Nombre_foto;

                    new COPIAR_ARCHIVOS( currentPhotoPath,Environment.getExternalStorageDirectory() + TipdbAdapter.R_CARPETA_EXPORTACION + Nombre_foto);
                    new COPIAR_ARCHIVOS( currentPhotoPath,Environment.getExternalStorageDirectory() + TipdbAdapter.R_RUTA_FOTOS + Nombre_foto);


                    Toast.makeText(getBaseContext(), "FOTO GUARDADA!", Toast.LENGTH_LONG).show();

                    break;
            }
        File f=new File(currentPhotoPath);
        f.delete();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inspeccion_puesto, menu);
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
    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id) {
        parent.getItemAtPosition(position);
        switch (parent.getId()) {
            case R.id.s_PUESTOS_PUESTO:
                Cursor c3=(Cursor)parent.getItemAtPosition(position);
                SPUESTOP=c3.getString(c3.getColumnIndex(TipdbAdapter.C_COLUMNA_PUESTO));
                //SPUESTOP = parent.getItemAtPosition(position).toString();

                //Toast.makeText(getBaseContext(), "PUESTO..: " + SPUESTOP, Toast.LENGTH_SHORT).show();

                if(!SPUESTOP.equals("")){
                    PONER_FECHA_y_HORA();
                    COMPROBAR_INSPECCION(SPUESTOP);
                    POSICIONES_ELEMENTOS_AUX(SPUESTOP);
                }


                if (!SINSPECTORP.equals("")){

                   // bt_hacerfoto.setVisibility(View.INVISIBLE);

                    boton_GUARDAR_PUESTO.setVisibility(View.VISIBLE);}

                 if (!SPUESTOP.equals("")){

                    //FOTODESPERFECTOS_ANTERIORES(SPUESTOP);//REVISAR CODIGO


                    }else {
                    //IMGANTERIOR.setImageResource(R.drawable.fondo_foto);
                    SRUTAFOTOa="";
                    boton_GUARDAR_PUESTO.setVisibility(View.INVISIBLE);

                }

                break;
            case R.id.s_PUESTOS_ADM:

                SADMP=parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(), "ADM..: " + SADMP, Toast.LENGTH_SHORT).show();
                break;
            case R.id.s_PUESTOS_MAQUINARX:


                SRXP=parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(), "RX..: " + SRXP, Toast.LENGTH_SHORT).show();
                break;
            case R.id.s_PUESTOS_DETECTOR_LIQUIDOS:

                sDETECTORP=parent.getItemAtPosition(position).toString();
               // Toast.makeText(getBaseContext(), "DETECTOR_LIQUIDOS..: " + sDETECTORP, Toast.LENGTH_SHORT).show();
                break;
            case R.id.s_PUESTOS_MAQUINA_ZAPATOS:

                sMZAPATOSP=parent.getItemAtPosition(position).toString();
               //Toast.makeText(getBaseContext(), "ZAPATOS..: " + sMZAPATOSP, Toast.LENGTH_SHORT).show();
                break;
            case R.id.s_PUESTOS_BARRERAS:

                sBARRERAS=parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(), "ZAPATOS..: " +  sBARRERAS, Toast.LENGTH_SHORT).show();
                break;
            case R.id.s_PUESTOS_TORNOS:

                sTORNOS=parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(), "TORNOS..: " +  sTORNOS, Toast.LENGTH_SHORT).show();
                break;
            case R.id.s_PUESTOS_TRAZAS:

                sTRAZAS=parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(), "TRAZAS..: " +   sTRAZAS, Toast.LENGTH_SHORT).show();
                break;
            case R.id.s_PUESTOS_INSPECTOR:
                Cursor c1=(Cursor)parent.getItemAtPosition(position);

                SINSPECTORP=c1.getString(c1.getColumnIndex("nombre"));

                //SINSPECTORP=parent.getItemAtPosition(position).toString();

                //if (!SPUESTOP.equals("")){bt_hacerfoto.setVisibility(View.VISIBLE);
                //  boton_GUARDAR_PUESTO.setVisibility(View.VISIBLE);}
                if (SINSPECTORP.equals("")){
                    boton_GUARDAR_PUESTO.setVisibility(View.INVISIBLE);
                    //bt_hacerfoto.setVisibility(View.INVISIBLE);
                   // INCIDENCIAVISITA.setText("");
                    break;}else {
                    //Toast.makeText(getBaseContext(), "INSPECTOR..: " + SINSPECTORP, Toast.LENGTH_SHORT).show();
                    PONER_FECHA_y_HORA();

                    INCIDENCIAVISITA.setText("NINGUNA");



                    boton_GUARDAR_PUESTO.setVisibility(View.VISIBLE);
                    break;
                }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (view == bt_hacerfoto) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            225);
                }


                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            226);
                }
            } else {
                dispatchTakePictureIntent();
            }
        }
    }
}
