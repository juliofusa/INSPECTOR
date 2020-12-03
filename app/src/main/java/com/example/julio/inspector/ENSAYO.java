package com.example.julio.inspector;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;




public class ENSAYO extends AppCompatActivity implements OnItemSelectedListener{
    private Spinner EN_TIPO,EN_RESULTADOS;
    private ImageView EN_VERFOTO;
    private Button ENSAYO_ANTERIOR,ENSAYO_SIGUIENTE,ENSAYO_FIN,ENSAYO_PRIMERO;
    private Button ENSAYO_B_HORA_I,ENSAYO_B_HORA_F,ENSAYO_B_NUEVO_ENSAYO;
    private int count_ENSAYO,count_registro,ID_ENSAYO,campo;
    private CheckBox sEN_EQUIPORX,sEN_ARCO;
    private String n_reg,SRUTAFOTOn,ENSAYO_TIPO,ENSAYO_FECHA,ENSAYO_HORAINICIO,ENSAYO_HORAFIN,ENSAYO_LUGAR,ENSAYO_INSPECTOR,ENSAYO_PERSONAL1,ENSAYO_PERSONAL2,ENSAYO_PERSONAL3,ENSAYO_EQUIPORX,ENSAYO_ARCO,ENSAYO_MEDIDAS,ENSAYO_DESCRIPCION,ENSAYO_RESULTADO;
    private EditText ENSAYO_TIPOENSAYO,EN_FECHA_ACT,EN_HORA_INICIO,EN_HORA_FINALIZACION,sEN_LUGAR,sEN_INSPECTOR,sEN_MEDIDAS,sEN_DESCRIPCION;
    private TextView ENSAYO_PANEL,TIPO_PANEL;
    private TipdbAdapter dbAdapterSituacion;
    private AutoCompleteTextView RESPONSABLE,sEN_PERSONAL1,sEN_PERSONAL2,sEN_PERSONAL3;
    private TimePickerDialog hora_picker;
    private Button ENSAYO_VER,ENSAYO_GUARDAR,ENSAYO_FOTO,ENSAYO_EDITAR,ENSAYO_ACTUALIZAR,ENSAYO_NOPROCEDE;
    final private String RUTA_DRIVE="/fotospuesto/";// CAMBIAR SI CAMBIAMOS DE CARPETA SINCRONIZADA DE DRIVE
    private int FLAG_RESULTADO=0;
    private int FLAG_CERRAR=1;
    private String currentPhotoPath,Nombre_foto;
    static final int REQUEST_TAKE_PHOTO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensayo2);
        //setContentView(R.layout.activity_ensayo);

        //final String[] TIPOENSAYO = getResources().getStringArray(R.array.ENSAYOS);
        final String[] RESULTADO_ENSAYO = getResources().getStringArray(R.array.RESULTADO);
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this, R.layout.custom_spinner_item1, RESULTADO_ENSAYO);
         ArrayAdapter<CharSequence> ADAPTADOR=ArrayAdapter.createFromResource(this,R.array.ENSAYOS,R.layout.custom_spinner_item1);
       // ADAPTADOR.setDropDownViewResource(android.R.layout.simple_list_item_checked);
       // ArrayAdapter<String> ADAPTADOR_TIPOENSAYO = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, TIPOENSAYO);

        count_registro=1;

        //declaracion de controles
        INICIALIZAR();

        EN_TIPO.setAdapter(ADAPTADOR);
        EN_RESULTADOS.setAdapter(adaptador1);
        EN_TIPO.setOnItemSelectedListener(this);
        EN_RESULTADOS.setOnItemSelectedListener(this);

        dbAdapterSituacion = new TipdbAdapter(this) ;
        dbAdapterSituacion.abrir();
        Cursor c=dbAdapterSituacion.getnombreCOMPLETOI();

        if (c.getCount()>1){
        RESPONSABLE.setThreshold(2);
        final String[] s= SelectAllData();
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,s);
            RESPONSABLE.setAdapter(adapter);
            sEN_PERSONAL1.setAdapter(adapter);
            sEN_PERSONAL2.setAdapter(adapter);
            sEN_PERSONAL3.setAdapter(adapter);
            RESPONSABLE.setOnItemSelectedListener(this);
            sEN_PERSONAL1.setOnItemSelectedListener(this);
            sEN_PERSONAL2.setOnItemSelectedListener(this);
            sEN_PERSONAL3.setOnItemSelectedListener(this);
        c.close();}

        setTimeField1();
        REGISTROS_TOTALES();
        VISIBILIDAD_CONTROLES(4);
    }
    private void INICIALIZAR(){


        EN_TIPO = (Spinner) findViewById(R.id.spinner_ensayo);
        EN_RESULTADOS= (Spinner) findViewById(R.id.spinner_resultado);
        EN_VERFOTO= (ImageView) findViewById(R.id.foto_en);
        //
        ENSAYO_ANTERIOR= (Button)findViewById(R.id.EN_IR_ANTERIOR);
        ENSAYO_SIGUIENTE= (Button) findViewById(R.id.EN_IR_SIGUIENTE);
        ENSAYO_FIN= (Button) findViewById(R.id.EN_IR_FIN);
        ENSAYO_PRIMERO=  (Button)findViewById(R.id.EN_IR_PRINCIPIO);


        //ENSAYO_VER=(Button)findViewById(R.id.EN_VER_REG);
        ENSAYO_GUARDAR=(Button)findViewById(R.id.EN_GUARDAR);
        ENSAYO_FOTO=(Button)findViewById(R.id.EN_HACERFOTO);
        ENSAYO_EDITAR=(Button)findViewById(R.id.EN_EDITAR);
        ENSAYO_ACTUALIZAR=(Button)findViewById(R.id.EN_ACTUALIZAR);
        ENSAYO_B_NUEVO_ENSAYO=  (Button)findViewById(R.id.EN_NUEVO_ENSAYO);
        //ENSAYO_NOPROCEDE=(Button)findViewById(R.id.EN_NOPROCEDE);

        EN_FECHA_ACT=(EditText)findViewById(R.id.e_fecha);
        EN_HORA_INICIO=(EditText)findViewById(R.id.hora_inicio);
        EN_HORA_FINALIZACION=(EditText)findViewById(R.id.hora_fin);
        sEN_LUGAR=(EditText)findViewById(R.id.LE);
        //sEN_INSPECTOR=(EditText)findViewById(R.id.je_ins);
        sEN_PERSONAL1=(AutoCompleteTextView)findViewById(R.id.p1);
        sEN_PERSONAL2=(AutoCompleteTextView)findViewById(R.id.p2);
        sEN_PERSONAL3=(AutoCompleteTextView)findViewById(R.id.p3);
        sEN_MEDIDAS=(EditText)findViewById(R.id.mc);
        sEN_DESCRIPCION=(EditText)findViewById(R.id.desc_en);
        sEN_DESCRIPCION.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500) });
        //ENSAYO_TIPOENSAYO=(EditText)findViewById(R.id.EN_TIPOENSAYO);

        sEN_EQUIPORX=(CheckBox)findViewById(R.id.ck_ER);
        sEN_ARCO=(CheckBox)findViewById(R.id.ck_AD);

        ENSAYO_PANEL=(TextView)findViewById(R.id.panel_informativo);
        TIPO_PANEL=(TextView)findViewById(R.id.EN_PANEL_TIPO);

        RESPONSABLE=(AutoCompleteTextView)findViewById(R.id.je_ins);

        ENSAYO_B_HORA_I=(Button)findViewById(R.id.EN_BOTON_HORA_INICIO);

        ENSAYO_B_HORA_F=(Button)findViewById(R.id.EN_BOTON_HORA_FIN);

    }
    private void VISIBILIDAD_CONTROLES(Integer V){
        EN_TIPO.setVisibility(V);
        EN_RESULTADOS.setVisibility(V);
        EN_VERFOTO.setVisibility(V);

        ENSAYO_B_HORA_I.setVisibility(V);

        ENSAYO_B_HORA_F.setVisibility(V);
        TIPO_PANEL.setVisibility(V);




        EN_FECHA_ACT.setVisibility(V);
        EN_HORA_INICIO.setVisibility(V);
        EN_HORA_FINALIZACION.setVisibility(V);
        sEN_LUGAR.setVisibility(V);
        //sEN_INSPECTOR=(EditText)findViewById(R.id.je_ins);
        sEN_PERSONAL1.setVisibility(V);
        sEN_PERSONAL2.setVisibility(V);
        sEN_PERSONAL3.setVisibility(V);
        sEN_MEDIDAS.setVisibility(V);
        sEN_DESCRIPCION.setVisibility(V);
        //ENSAYO_TIPOENSAYO=(EditText)findViewById(R.id.EN_TIPOENSAYO);

        sEN_EQUIPORX.setVisibility(V);
        sEN_ARCO.setVisibility(V);

        RESPONSABLE.setVisibility(V);
    }
    private void VISIBILIDAD_CONTROLES_EDITAR(Integer V){
        int vv;
        if (V==4){
            vv=0;
        ENSAYO_B_NUEVO_ENSAYO.setVisibility(vv);
        ENSAYO_EDITAR.setVisibility(vv);
        ENSAYO_GUARDAR.setVisibility(V);
        ENSAYO_FOTO.setVisibility(V);
        ENSAYO_ACTUALIZAR.setVisibility(V);
        ENSAYO_ANTERIOR.setVisibility(V);
        ENSAYO_SIGUIENTE.setVisibility(V);
        ENSAYO_FIN.setVisibility(V);
        ENSAYO_PRIMERO.setVisibility(V);}
    }
    private void LIMPIAR_CONTROLES(){

        EN_TIPO.setSelection(0);

        count_registro=1;

        EN_RESULTADOS.setSelection(0);

        EN_FECHA_ACT.setText("");
        EN_HORA_INICIO.setText("");
        EN_HORA_FINALIZACION.setText("");
        sEN_LUGAR.setText("");
        RESPONSABLE.setText("");
        sEN_PERSONAL1.setText("");
        sEN_PERSONAL2.setText("");
        sEN_PERSONAL3.setText("");

        sEN_DESCRIPCION.setText("");

        sEN_EQUIPORX.setChecked(false);
        sEN_ARCO.setChecked(false);
        EN_VERFOTO.setImageDrawable(getDrawable(R.drawable.fondo_foto));

    }
    private void CERRAR(){
        if (FLAG_CERRAR==1){finish();}
        if (FLAG_CERRAR==2){

            VISIBILIDAD_CONTROLES(4);
            LIMPIAR_CONTROLES();
            FLAG_CERRAR=1;
            VISIBILIDAD_CONTROLES_EDITAR(4);
        }
        if (FLAG_CERRAR==3){
            VISIBILIDAD_CONTROLES(4);
            FLAG_CERRAR=1;
            VISIBILIDAD_CONTROLES_EDITAR(4);
            LIMPIAR_CONTROLES();
        }
    }
    public void NUEVO_ENSAYO(View v){
        ENSAYO_B_NUEVO_ENSAYO.setVisibility(View.INVISIBLE);
        ENSAYO_EDITAR.setVisibility(View.INVISIBLE);
        ENSAYO_GUARDAR.setVisibility(View.VISIBLE);
        ENSAYO_FOTO.setVisibility(View.VISIBLE);
        VISIBILIDAD_CONTROLES(0);
        FLAG_CERRAR=2;
    }
    public void EN_BOTON_HACERFOTO(View v){
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
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

}

    public void EN_BOTON_CERRAR(View v){
        CERRAR();
    }
    public void PONERHORAFIN(View v){
        campo=2;
        hora_picker.show();
        //EN_HORA_FINALIZACION.setText(HORAconformato());
    }
    public void EN_BOTON_GUARDAR(View v){
        ENSAYO_FECHA=EN_FECHA_ACT.getText().toString();
        ENSAYO_HORAINICIO=EN_HORA_INICIO.getText().toString();
        ENSAYO_HORAFIN=EN_HORA_FINALIZACION.getText().toString();
        ENSAYO_LUGAR=sEN_LUGAR.getText().toString();
        ENSAYO_INSPECTOR=RESPONSABLE.getText().toString();
        ENSAYO_PERSONAL1=sEN_PERSONAL1.getText().toString();
        ENSAYO_PERSONAL2=sEN_PERSONAL2.getText().toString();
        ENSAYO_PERSONAL3=sEN_PERSONAL3.getText().toString();
        ENSAYO_MEDIDAS=sEN_MEDIDAS.getText().toString();
        ENSAYO_DESCRIPCION=sEN_DESCRIPCION.getText().toString();
        if (sEN_EQUIPORX.isChecked()){
            ENSAYO_EQUIPORX="SI";
        }else {ENSAYO_EQUIPORX="NO";}
        if (sEN_ARCO.isChecked()){
            ENSAYO_ARCO="SI";
        }else {ENSAYO_ARCO="NO";}
        if (ENSAYO_FECHA.equals("")){
            Toast.makeText(getBaseContext(), "RELLENAR POR LO MENOS EL CAMPO FECHA..: ", Toast.LENGTH_SHORT).show();
        }else {INSERTARENSAYO();}
    }
    public void ir_PRIMER(View v){
        count_registro=2;
        NAVEGAR(2);

    }
    public void ir_ULTIMO(View v){
        count_registro=2;
        NAVEGAR(3);

    }
    public void ir_SIGUIENTE(View v){
        count_registro=2;
        NAVEGAR(4);

    }
    public void ir_ANTERIOR(View v){
        count_registro=2;
        NAVEGAR(5);

    }
    public void VER_registros_ENSAYOS(View v){
        if (n_reg.equals("0")){
            Toast.makeText(getBaseContext(), "SIN REGISTROS" , Toast.LENGTH_SHORT).show();
        } else {
            ENSAYO_GUARDAR.setVisibility(View.INVISIBLE);
            ENSAYO_FOTO.setVisibility(View.INVISIBLE);
            ENSAYO_ACTUALIZAR.setVisibility(View.INVISIBLE);
            EN_TIPO.setVisibility(View.INVISIBLE);
            //CONTROLES NAVEGAR REGISTROS VISIBLES
            ENSAYO_TIPOENSAYO.setVisibility(View.VISIBLE);
            ENSAYO_ANTERIOR.setVisibility(View.VISIBLE);
            ENSAYO_SIGUIENTE.setVisibility(View.VISIBLE);
            ENSAYO_FIN.setVisibility(View.VISIBLE);
            ENSAYO_PRIMERO.setVisibility(View.VISIBLE);


            count_registro = 2;
            NAVEGAR(3);//ir al ultimo registro
        }
    }

    public void ENSAYO_UPDATE(View v){
        if (n_reg.equals("0")){
            Toast.makeText(getBaseContext(), "SIN REGISTROS" , Toast.LENGTH_SHORT).show();
        }else{
       // ENSAYO_GUARDAR.setVisibility(View.INVISIBLE);
       // ENSAYO_FOTO.setVisibility(View.VISIBLE);
        VISIBILIDAD_CONTROLES(0);
            FLAG_CERRAR=3;
            ENSAYO_B_NUEVO_ENSAYO.setVisibility(View.INVISIBLE);
            ENSAYO_EDITAR.setVisibility(View.INVISIBLE);
            ENSAYO_GUARDAR.setVisibility(View.INVISIBLE);

            ENSAYO_FOTO.setVisibility(View.VISIBLE);
        ENSAYO_ACTUALIZAR.setVisibility(View.VISIBLE);
        ENSAYO_ANTERIOR.setVisibility(View.VISIBLE);
        ENSAYO_SIGUIENTE.setVisibility(View.VISIBLE);
        ENSAYO_FIN.setVisibility(View.VISIBLE);
        ENSAYO_PRIMERO.setVisibility(View.VISIBLE);
            count_registro = 2;
        NAVEGAR(3);}//ir al ultimo registro
    }
    public void ENSAYO_ACTUALIZAR(View V){
        getENSAYO();
        updateENSAYO();

    }
    public void NO_PROCEDE(View v){
        sEN_MEDIDAS.setText("No proceden.");
        EN_RESULTADOS.setSelection(1);
    }
    public void h_COMIENZO(View v){

        campo=1;
        hora_picker.show();
       //EN_HORA_INICIO.setText(HORAconformato());
    }
    public void getENSAYO(){

        ENSAYO_FECHA=EN_FECHA_ACT.getText().toString();
        ENSAYO_HORAINICIO=EN_HORA_INICIO.getText().toString();
        ENSAYO_HORAFIN=EN_HORA_FINALIZACION.getText().toString();
        ENSAYO_LUGAR=sEN_LUGAR.getText().toString();
        ENSAYO_INSPECTOR=RESPONSABLE.getText().toString();
        ENSAYO_PERSONAL1=sEN_PERSONAL1.getText().toString();
        ENSAYO_PERSONAL2=sEN_PERSONAL2.getText().toString();
        ENSAYO_PERSONAL3=sEN_PERSONAL3.getText().toString();
        ENSAYO_MEDIDAS=sEN_MEDIDAS.getText().toString();
        ENSAYO_DESCRIPCION=sEN_DESCRIPCION.getText().toString();
        if (sEN_EQUIPORX.isChecked()){
            ENSAYO_EQUIPORX="SI";
        }else {ENSAYO_EQUIPORX="NO";}
        if (sEN_ARCO.isChecked()){
            ENSAYO_ARCO="SI";
        }else {ENSAYO_ARCO="NO";}
    }
    private  void REGISTROS_TOTALES() {
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ENSAYOS ", null);

        String nREG= String.valueOf(cursor.getCount());
        n_reg=nREG;
        ENSAYO_PANEL.setText("REGISTROS:" + nREG);
        cursor.close();
        db.close();
    }
    private void NAVEGAR(int N) {
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ENSAYOS ", null);
        if (N == 1) {

            ENSAYO_PANEL.setText("REGISTRO: 0/" + String.valueOf(cursor.getCount()));

        }
        if (N == 2) {
            cursor.moveToFirst();//al primer registro

            count_ENSAYO = cursor.getPosition();
            ENSAYO_PANEL.setText("REGISTRO: " + (count_ENSAYO + 1) + "/" + String.valueOf(cursor.getCount()));
            ID_ENSAYO=cursor.getInt(0);
            String TIPOENSAYO1 = cursor.getString(1);
            String TIPORESULTADO1 = cursor.getString(15);
            Seleccion_spinner(TIPOENSAYO1,TIPORESULTADO1);

            RESPONSABLE.setText(cursor.getString(2));
            EN_FECHA_ACT.setText(cursor.getString(3));
            EN_HORA_INICIO.setText(cursor.getString(4));
            EN_HORA_FINALIZACION.setText(cursor.getString(5));
            sEN_LUGAR.setText(cursor.getString(6));
            sEN_PERSONAL1.setText(cursor.getString(7));
            sEN_PERSONAL2.setText(cursor.getString(8));
            sEN_PERSONAL3.setText(cursor.getString(9));
            String temp1 = cursor.getString(10);

            if (temp1.equals("SI")) {
                sEN_EQUIPORX.setChecked(true);
            } else {
                sEN_EQUIPORX.setChecked(false);
            }
            if ((cursor.getString(11)) .equals("SI")) {
                sEN_ARCO.setChecked(true);
            } else {
                sEN_ARCO.setChecked(false);
            }
            sEN_MEDIDAS.setText(cursor.getString(12));
            sEN_DESCRIPCION.setText(cursor.getString(14));
            //poner foto si existe

                Bitmap bMap2 = BitmapFactory.decodeFile(
                        Environment.getExternalStorageDirectory() + TipdbAdapter.R_RUTA_FOTOS + cursor.getString(13));
               EN_VERFOTO.setImageBitmap(bMap2);
            SRUTAFOTOn=cursor.getString(13);


            ENSAYO_ANTERIOR.setClickable(false);
            ENSAYO_ANTERIOR.setEnabled(false);
            ENSAYO_SIGUIENTE.setClickable(true);
            ENSAYO_SIGUIENTE.setEnabled(true);
            FLAG_RESULTADO=1;
            sEN_LUGAR.requestFocus();
        }
        if (N == 3) {
            cursor.moveToLast();//al ultimo registro

            count_ENSAYO = cursor.getPosition();
            ENSAYO_PANEL.setText("REGISTRO: " + (count_ENSAYO + 1) + "/" + String.valueOf(cursor.getCount()));
            ID_ENSAYO=cursor.getInt(0);

            String TIPOENSAYO1 = cursor.getString(1);
            String TIPORESULTADO1 = cursor.getString(15);
            Seleccion_spinner(TIPOENSAYO1,TIPORESULTADO1);

            RESPONSABLE.setText(cursor.getString(2));
            EN_FECHA_ACT.setText(cursor.getString(3));
            EN_HORA_INICIO.setText(cursor.getString(4));
            EN_HORA_FINALIZACION.setText(cursor.getString(5));
            sEN_LUGAR.setText(cursor.getString(6));
            sEN_PERSONAL1.setText(cursor.getString(7));
            sEN_PERSONAL2.setText(cursor.getString(8));
            sEN_PERSONAL3.setText(cursor.getString(9));
            String temp1 = cursor.getString(10);

            if (temp1.equals("SI")) {
                sEN_EQUIPORX.setChecked(true);
            } else {
                sEN_EQUIPORX.setChecked(false);
            }
            if ((cursor.getString(11)).equals("SI")) {
                sEN_ARCO.setChecked(true);
            } else {
                sEN_ARCO.setChecked(false);
            }
            sEN_MEDIDAS.setText(cursor.getString(12));
            sEN_DESCRIPCION.setText(cursor.getString(14));


                Bitmap bMap2 = BitmapFactory.decodeFile(
                        Environment.getExternalStorageDirectory() + TipdbAdapter.R_RUTA_FOTOS + cursor.getString(13));
                EN_VERFOTO.setImageBitmap(bMap2);
            SRUTAFOTOn=cursor.getString(13);

            ENSAYO_ANTERIOR.setClickable(true);
            ENSAYO_ANTERIOR.setEnabled(true);
            ENSAYO_SIGUIENTE.setClickable(false);
            ENSAYO_SIGUIENTE.setEnabled(false);
            FLAG_RESULTADO=1;
            sEN_LUGAR.requestFocus();
        }
        if (N == 4) {
            count_ENSAYO += 1;
            cursor.moveToPosition(count_ENSAYO);//al siguiente registro
            if (count_ENSAYO + 1 == (cursor.getCount())) {

                ENSAYO_SIGUIENTE.setClickable(false);
                ENSAYO_SIGUIENTE.setEnabled(false);
            }

            ENSAYO_PANEL.setText("REGISTRO: " + (count_ENSAYO + 1) + "/" + String.valueOf(cursor.getCount()));
            ID_ENSAYO=cursor.getInt(0);
            ENSAYO_ANTERIOR.setClickable(true);
            ENSAYO_ANTERIOR.setEnabled(true);

            String TIPOENSAYO1 = cursor.getString(1);
            String TIPORESULTADO1 = cursor.getString(15);
            Seleccion_spinner(TIPOENSAYO1,TIPORESULTADO1);


            RESPONSABLE.setText(cursor.getString(2));
            EN_FECHA_ACT.setText(cursor.getString(3));
            EN_HORA_INICIO.setText(cursor.getString(4));
            EN_HORA_FINALIZACION.setText(cursor.getString(5));
            sEN_LUGAR.setText(cursor.getString(6));
            sEN_PERSONAL1.setText(cursor.getString(7));
            sEN_PERSONAL2.setText(cursor.getString(8));
            sEN_PERSONAL3.setText(cursor.getString(9));
            String temp1 = cursor.getString(10);

            if (temp1.equals("SI")) {
                sEN_EQUIPORX.setChecked(true);
            } else {
                sEN_EQUIPORX.setChecked(false);
            }
            if ((cursor.getString(11)).equals("SI")) {
                sEN_ARCO.setChecked(true);
            } else {
                sEN_ARCO.setChecked(false);
            }
            sEN_MEDIDAS.setText(cursor.getString(12));
            sEN_DESCRIPCION.setText(cursor.getString(14));


                Bitmap bMap2 = BitmapFactory.decodeFile(
                        Environment.getExternalStorageDirectory() + TipdbAdapter.R_RUTA_FOTOS + cursor.getString(13));
                EN_VERFOTO.setImageBitmap(bMap2);
            SRUTAFOTOn=cursor.getString(13);
            FLAG_RESULTADO=1;
            sEN_LUGAR.requestFocus();

        }
        if (N == 5) {
            if (count_ENSAYO == 0) {
                ENSAYO_ANTERIOR.setClickable(false);
                ENSAYO_ANTERIOR.setEnabled(false);
            }else {
                count_ENSAYO -= 1;

                cursor.moveToPosition(count_ENSAYO);//al  registro PREVIO

                count_ENSAYO = cursor.getPosition();
                ENSAYO_PANEL.setText("REGISTRO: " + (count_ENSAYO + 1) + "/" + String.valueOf(cursor.getCount()));
                ID_ENSAYO = cursor.getInt(0);
                ENSAYO_SIGUIENTE.setClickable(true);
                ENSAYO_SIGUIENTE.setEnabled(true);

                String TIPOENSAYO1 = cursor.getString(1);
                String TIPORESULTADO1 = cursor.getString(15);
                Seleccion_spinner(TIPOENSAYO1, TIPORESULTADO1);


                RESPONSABLE.setText(cursor.getString(2));
                EN_FECHA_ACT.setText(cursor.getString(3));
                EN_HORA_INICIO.setText(cursor.getString(4));
                EN_HORA_FINALIZACION.setText(cursor.getString(5));
                sEN_LUGAR.setText(cursor.getString(6));
                sEN_PERSONAL1.setText(cursor.getString(7));
                sEN_PERSONAL2.setText(cursor.getString(8));
                sEN_PERSONAL3.setText(cursor.getString(9));
                String temp1 = cursor.getString(10);

                if ((temp1).equals("SI")) {
                    sEN_EQUIPORX.setChecked(true);
                } else {
                    sEN_EQUIPORX.setChecked(false);
                }
                if ((cursor.getString(11)).equals("SI")) {
                    sEN_ARCO.setChecked(true);
                } else {
                    sEN_ARCO.setChecked(false);
                }
                sEN_MEDIDAS.setText(cursor.getString(12));
                sEN_DESCRIPCION.setText(cursor.getString(14));


                Bitmap bMap2 = BitmapFactory.decodeFile(
                        Environment.getExternalStorageDirectory() + TipdbAdapter.R_RUTA_FOTOS + cursor.getString(13));
                EN_VERFOTO.setImageBitmap(bMap2);
                SRUTAFOTOn = cursor.getString(13);
                FLAG_RESULTADO = 1;
                sEN_LUGAR.requestFocus();
            }
            }

    }
    private void Seleccion_spinner(String tipo,String Resultado){

        ArrayAdapter<CharSequence> ADAPTADOR = ArrayAdapter.createFromResource(this, R.array.ENSAYOS, android.R.layout.simple_list_item_checked);

        ADAPTADOR.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        EN_TIPO.setSelection(ADAPTADOR.getPosition(tipo));

        ArrayAdapter<CharSequence> RESULTADO = ArrayAdapter.createFromResource(this, R.array.RESULTADO, android.R.layout.simple_list_item_checked);

        EN_RESULTADOS.setSelection(RESULTADO.getPosition(Resultado));

    }
    private String getCode(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoCode = "ENSAYO_" + date;
        return photoCode;
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
        return dateString;}
    public void INSERTARENSAYO() {
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("tipoensayo", ENSAYO_TIPO);
            nuevoRegistro.put("personalinspector", ENSAYO_INSPECTOR);
            nuevoRegistro.put("fecha", ENSAYO_FECHA);
            nuevoRegistro.put("horaINICIO", ENSAYO_HORAINICIO);
            nuevoRegistro.put("horaFINALIZACION", ENSAYO_HORAFIN);
            nuevoRegistro.put("lugar", ENSAYO_LUGAR);
            nuevoRegistro.put("personal1", ENSAYO_PERSONAL1);
            nuevoRegistro.put("personal2", ENSAYO_PERSONAL2);
            nuevoRegistro.put("personal3", ENSAYO_PERSONAL3);
            nuevoRegistro.put("equiporadioscopia", ENSAYO_EQUIPORX);
            nuevoRegistro.put("arcoadn", ENSAYO_ARCO);
            nuevoRegistro.put("medidascorrectoras", ENSAYO_MEDIDAS);
            nuevoRegistro.put("rutaFOTO", SRUTAFOTOn);
            nuevoRegistro.put("DESCRIPCION", ENSAYO_DESCRIPCION);
            nuevoRegistro.put("RESULTADO", ENSAYO_RESULTADO);


            //Insertamos el registro en la base de datos
            db.insert("ENSAYOS", null, nuevoRegistro);
            Log.i(this.getClass().toString(), "INSERTADO NUEVO REGISTRO");
            // Cerramos la base de datos
            db.close();

            Toast.makeText(getBaseContext(), "DATOS GUARDADOS ", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    public void updateENSAYO(){
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("tipoensayo", ENSAYO_TIPO);
            nuevoRegistro.put("personalinspector", ENSAYO_INSPECTOR);
            nuevoRegistro.put("fecha", ENSAYO_FECHA);
            nuevoRegistro.put("horaINICIO", ENSAYO_HORAINICIO);
            nuevoRegistro.put("horaFINALIZACION", ENSAYO_HORAFIN);
            nuevoRegistro.put("lugar", ENSAYO_LUGAR);
            nuevoRegistro.put("personal1", ENSAYO_PERSONAL1);
            nuevoRegistro.put("personal2", ENSAYO_PERSONAL2);
            nuevoRegistro.put("personal3", ENSAYO_PERSONAL3);
            nuevoRegistro.put("equiporadioscopia", ENSAYO_EQUIPORX);
            nuevoRegistro.put("arcoadn", ENSAYO_ARCO);
            nuevoRegistro.put("medidascorrectoras", ENSAYO_MEDIDAS);
            nuevoRegistro.put("rutaFOTO", SRUTAFOTOn);
            nuevoRegistro.put("DESCRIPCION", ENSAYO_DESCRIPCION);



            //Insertamos el registro en la base de datos
            db.update("ENSAYOS", nuevoRegistro, "_id=" + ID_ENSAYO, null);
            Log.i(this.getClass().toString(), "INSERTADO NUEVO REGISTRO");
            // Cerramos la base de datos
            db.close();

            Toast.makeText(getBaseContext(), "DATOS ACTUALIZADOS ", Toast.LENGTH_SHORT).show();

        }
    }
    public String[] SelectAllData() {
        dbAdapterSituacion = new TipdbAdapter(this) ;
        dbAdapterSituacion.abrir();
        Cursor c=dbAdapterSituacion.getnombreCOMPLETOI();
        String arrData[] = null;
        if(c != null) {

            if (c.moveToFirst()) {
                arrData = new String[c.getCount()];
                int i = 0;
                do {
                    arrData[i] = c.getString(4);
                    i++;
                } while (c.moveToNext());
            }


        }
        c.close();
        return arrData;}
    private void setTimeField(){

        Calendar newCalendar= Calendar.getInstance();
        int hour=newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute=newCalendar.get(Calendar.MINUTE);
        String f_MINUTO;
        hora_picker=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                switch (minute){
                    case 0:
                        EN_HORA_INICIO.setText(hourOfDay + ":00");
                        break;
                    case 1:
                        EN_HORA_INICIO.setText(hourOfDay + ":01");
                        break;
                    case 2:
                        EN_HORA_INICIO.setText(hourOfDay + ":02");
                        break;
                    case 3:
                        EN_HORA_INICIO.setText(hourOfDay + ":03");
                        break;
                    case 4:
                        EN_HORA_INICIO.setText(hourOfDay + ":04");
                        break;
                    case 5:
                        EN_HORA_INICIO.setText(hourOfDay + ":05");
                        break;
                    case 6:
                        EN_HORA_INICIO.setText(hourOfDay + ":06");
                        break;
                    case 7:
                        EN_HORA_INICIO.setText(hourOfDay + ":07");
                        break;
                    case 8:
                        EN_HORA_INICIO.setText(hourOfDay + ":08");
                        break;
                    case 9:
                        EN_HORA_INICIO.setText(hourOfDay + ":09");
                        break;
                    default: EN_HORA_INICIO.setText(hourOfDay + ":" + minute);
                }

            }



        },hour,minute,true);

    }
    private void setTimeField1(){

        Calendar newCalendar= Calendar.getInstance();
        int hour=newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute=newCalendar.get(Calendar.MINUTE);

        hora_picker=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String f_MINUTO;
                switch (minute){
                    case 0:
                        f_MINUTO=":00";

                        break;
                    case 1:
                        f_MINUTO=":01";

                        break;
                    case 2:
                        f_MINUTO=":02";

                        break;
                    case 3:
                        f_MINUTO=":03";

                        break;
                    case 4:
                        f_MINUTO=":04";

                        break;
                    case 5:
                        f_MINUTO=":05";

                        break;
                    case 6:
                        f_MINUTO=":06";

                        break;
                    case 7:
                        f_MINUTO=":07";

                        break;
                    case 8:
                        f_MINUTO=":08";

                        break;
                    case 9:
                        f_MINUTO=":09";

                        break;
                    default:
                        f_MINUTO= ":"+String.valueOf(minute);

                }
                switch (campo){
                    case 1:
                        EN_HORA_INICIO.setText(hourOfDay + f_MINUTO);

                        break;
                    case 2:
                        EN_HORA_FINALIZACION.setText(hourOfDay + f_MINUTO);

                        break;

                }
            }



        },hour,minute,true);

    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "ENSAYO_" + timeStamp + "_";
        Nombre_foto=imageFileName+".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        //Toast.makeText(getBaseContext(), "FOTO : "+currentPhotoPath, Toast.LENGTH_LONG).show();
        return image;
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
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + TipdbAdapter.R_CARPETA_EXPORTACION);
        // have the object build the directory structure, if needed.
        //if (!wallpaperDirectory.exists()) {wallpaperDirectory.mkdirs();}

        try {
            Nombre_foto="ENSAYO_"+ Calendar.getInstance().getTimeInMillis() + ".jpg";
            SRUTAFOTOn=Nombre_foto;
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
        //Comprovamos que la foto se a realizado
        super.onActivityResult(requestCode, resultCode, data);
        //Comprovamos que la foto se a realizado
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);


            //Creamos un bitmap con la imagen recientemente
            //almacenada en la memoria

        //Nombre_foto="ENSAYO_"+ Calendar.getInstance().getTimeInMillis() + ".jpg";
        SRUTAFOTOn=Nombre_foto;
        String path=currentPhotoPath;
        EN_VERFOTO.setImageBitmap(imageBitmap);


           // new COPIAR_ARCHIVOS( Environment.getExternalStorageDirectory()+TipdbAdapter.R_RUTA_FOTOS+SRUTAFOTOn,Environment.getExternalStorageDirectory() + RUTA_DRIVE + SRUTAFOTOn);
        new COPIAR_ARCHIVOS( path,Environment.getExternalStorageDirectory() + TipdbAdapter.R_CARPETA_EXPORTACION + SRUTAFOTOn);
        new COPIAR_ARCHIVOS( path,Environment.getExternalStorageDirectory() + TipdbAdapter.R_RUTA_FOTOS + SRUTAFOTOn);

        File f=new File(path);
        f.delete();

            Toast.makeText(getBaseContext(), "¡FOTO GUARDADA! "+Environment.getExternalStorageDirectory() + TipdbAdapter.R_RUTA_FOTOS + SRUTAFOTOn, Toast.LENGTH_LONG).show();

    }

      @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ensayo, menu);
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
            case R.id.spinner_ensayo:
               if ((parent.getItemAtPosition(position).toString()).equals("")){

                    break;}else {
                    ENSAYO_TIPO=parent.getItemAtPosition(position).toString();
                    if (ENSAYO_TIPO.equals("MAQUINA RX")){
                        sEN_EQUIPORX.setChecked(true);
                        sEN_ARCO.setChecked(false);
                        }else if(ENSAYO_TIPO.equals("ADM")){
                        sEN_ARCO.setChecked(true);
                        sEN_EQUIPORX.setChecked(false);
                    }else{
                        sEN_EQUIPORX.setChecked(false);
                        sEN_ARCO.setChecked(false);
                    }
                    if (count_registro==1){
                        EN_FECHA_ACT.setText(FECHAconformato());
                        EN_HORA_INICIO.setText(HORAconformato());


                    }else if (count_registro==2){
                       // EN_FECHA_ACT.setText("");
                       // EN_HORA_INICIO.setText("");
                    }

                }
                break;
            case R.id.spinner_resultado:
                ENSAYO_RESULTADO=parent.getItemAtPosition(position).toString();
                if (count_registro==1){

                if (ENSAYO_RESULTADO.equals("BIEN")){
                    sEN_MEDIDAS.setText("NO PROCEDEN");
                }else {
                    sEN_MEDIDAS.setText("Se comunica al gestor de servicios y se toman las medidas oportunas.");}
                } else if (FLAG_RESULTADO==1){
                    if (ENSAYO_RESULTADO.equals("BIEN")){
                        sEN_MEDIDAS.setText("NO PROCEDEN");
                    }else {
                        sEN_MEDIDAS.setText("Se comunica al gestor de servicios y se toman las medidas oportunas.");}
                }

                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        count_registro=1;
    }

}
