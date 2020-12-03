package com.example.julio.inspector;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TEST_VARIABLE extends AppCompatActivity {
    private Integer[] r=new Integer[20];
    private Integer[] c=new Integer[20];
    private Integer TOTAL_RESPUESTAS=0,TOTAL_CORRECTAS=0,TOTAL_PREGUNTAS=20;
    private Double PORCENTAJE;
    private CheckBox[][] CK=new CheckBox[20][4];
    private TextView[] PREGUNTA=new TextView[20];
    LinearLayout layout,contenedorBoton;
    private List<Integer> Preguntas2,RESPUESTAS;
    private TipdbAdapter test;
    private String Nombre_vs,Nombre_test,Nombre_formador,HORAINICIO,TIP,ID_DISPOSITIVO;
    private Boolean tipo_personal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test__custom);

         RESPUESTAS=new ArrayList<>(20);

         Nombre_vs=getIntent().getStringExtra("NOMBRE_VS");

         Nombre_test=getIntent().getStringExtra("NOMBRE_TEST");

         Nombre_formador=getIntent().getStringExtra("FORMADOR");

         TOTAL_PREGUNTAS=getIntent().getIntExtra("NUMERO_PREGUNTAS", 20);

         TIP=getIntent().getStringExtra("TIP");

         tipo_personal=getIntent().getBooleanExtra("tipo_personal", true);

         setTitle("FORMACION EN " + Nombre_test + ": " + Nombre_vs + "\n con TIP " + TIP);

         CK=new CheckBox[TOTAL_PREGUNTAS][4];

         PREGUNTA=new TextView[TOTAL_PREGUNTAS];

         r=new Integer[TOTAL_PREGUNTAS];

         c=new Integer[TOTAL_PREGUNTAS];

          LLENAR_TEST();

         ID_DISPOSITIVO= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    //LLenamos el test con preguntas y respuestas
    private void LLENAR_TEST(){

        LinearLayout row2 = (LinearLayout) findViewById(R.id.lyv);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        LinearLayout.LayoutParams layoutParams = new  LinearLayout.LayoutParams(width-15,165);
        layoutParams.setMargins(10, 10, 10, 10); // left, top, right, bottom
        final TypedArray testArrayIcon = getResources().obtainTypedArray(R.array.preguntas_imagen);

        test = new TipdbAdapter(this) ;

        test.abrir();

        Cursor C_test=test.TEST(Nombre_test);
        C_test.moveToFirst();
        int ancho = 80;
        int alto = 80;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
        params.setMargins(10, 7, 5, 7);
        LinearLayout.LayoutParams paramsck = new LinearLayout.LayoutParams(width-15, 85);
        paramsck.setMargins(10, 5, 5, 5);
        LinearLayout.LayoutParams params_boton = new LinearLayout.LayoutParams(280, 90);
        params_boton.setMargins(10, 5, 5, 5);
        Button RESULTADOS=new Button(this);
        for (int i = 0; i <  TOTAL_PREGUNTAS; ++i){
            Integer respuestas=C_test.getInt(7);RESPUESTAS.add(respuestas);

            ImageView NUMERO=new ImageView(this);
            ImageView IMAGEN_TEST=new ImageView(this);

            TextView p=new TextView(this);

            NUMERO.setImageResource(testArrayIcon.getResourceId(i, -1));
            NUMERO.setScaleType(ImageView.ScaleType.FIT_START);
            Bitmap bMap1 = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            TipdbAdapter.R_RUTA_fototest + C_test.getString(10));

            IMAGEN_TEST.setImageBitmap(bMap1);
             //IMAGEN_TEST.setImageResource(testArrayIcon.getResourceId(i, -1));
            //IMAGEN_TEST.setImageResource(bMap1);
            PREGUNTA[i]=p;
            PREGUNTA[i].setText("     "+(i + 1) + ".-" + C_test.getString(2));
            PREGUNTA[i].setTextSize(30);
            PREGUNTA[i].setTextColor(Color.WHITE);
            PREGUNTA[i].setBackgroundColor(Color.BLUE);
            PREGUNTA[i].setAlpha(0.85f);

            CK[i][0]=new CheckBox(this);
            CK[i][0].setButtonDrawable(R.drawable.checbox_background);
            CK[i][0].setTextSize(22);
            CK[i][0].setPadding(5, 0, 0, 0);
            CK[i][0].setGravity(Gravity.TOP);
            CK[i][0].setAlpha(0.85f);

            CK[i][1]=new CheckBox(this);
            CK[i][1].setButtonDrawable(R.drawable.checbox_background);
            CK[i][1].setTextSize(22);
            CK[i][1].setPadding(5, 0, 0, 0);
            CK[i][1].setGravity(Gravity.TOP);
            CK[i][1].setAlpha(0.85f);

            CK[i][2]=new CheckBox(this);
            CK[i][2].setButtonDrawable(R.drawable.checbox_background);
            CK[i][2].setTextSize(22);
            CK[i][2].setPadding(5, 0, 0, 0);
            CK[i][2].setGravity(Gravity.TOP);
            CK[i][2].setAlpha(0.85f);

            CK[i][3]=new CheckBox(this);
            CK[i][3].setButtonDrawable(R.drawable.checbox_background);
            CK[i][3].setTextSize(22);
            CK[i][3].setPadding(5, 0, 0, 0);
            CK[i][3].setGravity(Gravity.TOP);
            CK[i][3].setAlpha(0.85f);
            //llenamos las posibles respuestas
            CK[i][0].setText(C_test.getString(3));
            CK[i][1].setText(C_test.getString(4));
            CK[i][2].setText(C_test.getString(5));
            CK[i][3].setText(C_test.getString(6));

            CK[i][0].setTag(i);
            CK[i][1].setTag(i);
            CK[i][2].setTag(i);
            CK[i][3].setTag(i);

            final Integer finalTemp = i;
            r[i]=0;c[i]=0;
            HORAINICIO=HORAconformato();
            CK[i][0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String s = (String) String.valueOf(v.getTag());
                    Integer ss = Integer.parseInt(s);

                    if(CK[ss][0].isChecked()==true){
                        r[ss]=1;CK[ss][0].setBackgroundColor(Color.YELLOW);total(1,finalTemp);
                    }else{
                        r[ss]=0;CK[ss][0].setBackgroundColor(Color.WHITE);total(5, finalTemp);}

                    CK[ss][1].setChecked(false);
                    CK[ss][1].setBackgroundColor(Color.WHITE);
                    CK[ss][2].setChecked(false);
                    CK[ss][2].setBackgroundColor(Color.WHITE);
                    CK[ss][3].setChecked(false);
                    CK[ss][3].setBackgroundColor(Color.WHITE);
                }
            });
            CK[i][1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = (String) String.valueOf(v.getTag());
                    Integer ss = Integer.parseInt(s);
                    if(CK[ss][1].isChecked()==true){
                        r[ss]=1;CK[ss][1].setBackgroundColor(Color.YELLOW);total(2,finalTemp);
                    }else{
                        r[ss]=0;CK[ss][1].setBackgroundColor(Color.WHITE);total(5,finalTemp);}

                    CK[ss][0].setChecked(false);CK[ss][0].setBackgroundColor(Color.WHITE);
                    //CK[ss][1].setBackgroundColor(Color.YELLOW);
                    CK[ss][2].setChecked(false);CK[ss][2].setBackgroundColor(Color.WHITE);
                    CK[ss][3].setChecked(false);CK[ss][3].setBackgroundColor(Color.WHITE);
                }
            });
            CK[i][2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = (String) String.valueOf(v.getTag());
                    Integer ss = Integer.parseInt(s);
                    if(CK[ss][2].isChecked()==true){
                        r[ss]=1;CK[ss][2].setBackgroundColor(Color.YELLOW);total(3,finalTemp);;
                    }else{
                        r[ss]=0;CK[ss][2].setBackgroundColor(Color.WHITE);total(5,finalTemp);}

                    CK[ss][0].setChecked(false);CK[ss][0].setBackgroundColor(Color.WHITE);
                    CK[ss][1].setChecked(false);CK[ss][1].setBackgroundColor(Color.WHITE);
                    //CK[ss][2].setBackgroundColor(Color.YELLOW);
                    CK[ss][3].setChecked(false);CK[ss][3].setBackgroundColor(Color.WHITE);
                }
            });
            CK[i][3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = (String) String.valueOf(v.getTag());
                    Integer ss = Integer.parseInt(s);
                    if(CK[ss][3].isChecked()==true){
                        r[ss]=1;CK[ss][3].setBackgroundColor(Color.YELLOW);total(4,finalTemp);
                    }else{
                        r[ss]=0;CK[ss][3].setBackgroundColor(Color.WHITE);total(5,finalTemp);}

                    CK[ss][0].setChecked(false);CK[ss][0].setBackgroundColor(Color.WHITE);
                    CK[ss][1].setChecked(false);CK[ss][1].setBackgroundColor(Color.WHITE);
                    CK[ss][2].setChecked(false);CK[ss][2].setBackgroundColor(Color.WHITE);
                    //CK[ss][3].setBackgroundColor(Color.YELLOW);
                }
            });

            row2.addView(NUMERO);
            row2.addView(PREGUNTA[i]);
            row2.addView(IMAGEN_TEST);

            row2.addView(CK[i][0]);
            row2.addView(CK[i][1]);
            row2.addView(CK[i][2]);
            row2.addView(CK[i][3]);

            CK[i][0].setLayoutParams(paramsck);
            CK[i][1].setLayoutParams(paramsck);
            CK[i][2].setLayoutParams(paramsck);
            CK[i][3].setLayoutParams(paramsck);
            NUMERO.setLayoutParams(params);
            PREGUNTA[i].setLayoutParams(layoutParams);
            C_test.moveToNext();
        }
        RESULTADOS.setLayoutParams(params_boton);
        RESULTADOS.setText("RESULTADO");
        RESULTADOS.setTextSize(30);

        row2.addView(RESULTADOS);
        RESULTADOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double respuestas=(double)TOTAL_RESPUESTAS/(double) TOTAL_PREGUNTAS;;
                if (respuestas<0.7){
                    Toast.makeText(getBaseContext(),"Ha respondido menos del 70%", Toast.LENGTH_SHORT).show();
                }else{
                    FINAL_TEST();
                }
            }
        });


    }
    private void total(int respuesta,int pregunta){

        TOTAL_RESPUESTAS=0; TOTAL_CORRECTAS=0;PORCENTAJE=0.0;

        if (respuesta==RESPUESTAS.get(pregunta)){c[pregunta]=1;}else{c[pregunta]=0;}

        for (int i = 0; i < PREGUNTA.length; ++i){TOTAL_CORRECTAS+=c[i];TOTAL_RESPUESTAS+=r[i];}

        if (TOTAL_RESPUESTAS==0){PORCENTAJE=0.0;}else{PORCENTAJE= (double)TOTAL_CORRECTAS/(double) TOTAL_PREGUNTAS;}

        //Toast.makeText(getBaseContext(),"% porcentaje: "+PORCENTAJE, Toast.LENGTH_SHORT).show();

        setTitle("FORMACION EN " + Nombre_test + ": " + Nombre_vs + "\n con TIP " + TIP + " - REPUESTAS: " + (TOTAL_RESPUESTAS)+"/"+PREGUNTA.length+"");
    }
    public void FINAL_TEST(){

        // SI LOS ACIERTOS SON MAS O IGUAL AL 70% -APROBADO-
        if (PORCENTAJE>=0.7){

            MediaPlayer mp= MediaPlayer.create(this,R.raw.aprobado);

            mp.start();

            RESULTADOS(TOTAL_CORRECTAS);


        }else{

            MediaPlayer mp= MediaPlayer.create(this,R.raw.suspenso);

            mp.start();

           // GUARDAR_REGISTRO();

            RESULTADOS(TOTAL_CORRECTAS);

        }
    }
    private void RESULTADOS(final Integer RESULT){

        final Intent i = new Intent(this, firmas.class);
        final ImageView nota= new ImageView(this);
        final String APROBADO;
        final TypedArray testArrayIcon = getResources().obtainTypedArray(R.array.preguntas_imagen);

        if (PORCENTAJE>=0.7) {APROBADO="APROBADO";}else{APROBADO="SUSPENSO";}

        nota.setImageResource(testArrayIcon.getResourceId(RESULT-1, -1));

        new AlertDialog.Builder(this)

                .setIcon(R.drawable.infor_macion)
                .setTitle("RESULTADO DEL TEST: " + APROBADO)
                .setMessage("RESPUESTAS CORRECTAS...")
                .setView(nota)

                .setPositiveButton(R.string.CONTINUAR, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
// Salir
                        if (PORCENTAJE>=0.7) {

                            i.putExtra("FECHA", FECHAconformato());
                            i.putExtra("NOMBRECOMPLETO", Nombre_vs);
                            i.putExtra("FORMADOR", Nombre_formador);
                            i.putExtra("ACTIVITY", "2");
                            i.putExtra("FORMACION", Nombre_test);
                            i.putExtra("HORA", HORAINICIO);
                            i.putExtra("TIP", TIP);
                            i.putExtra("RESULTADO", TOTAL_CORRECTAS);
                            i.putExtra("tipo_personal", tipo_personal);

                            startActivity(i);
                            finish();
                        } else {

                            VER_PDF();

                            finish();
                        }
                    }
                })
                .show();
    }
    // GUARDAMOS REGISTRO SI ESTA SUSPENSO
    public void GUARDAR_REGISTRO(){

        BasedbHelper  usdbh = new  BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if(db != null){
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put("FECHA", FECHAconformato());//1
            nuevoRegistro.put("horaINICIO", HORAINICIO);//2
            nuevoRegistro.put("horaFIN", HORAconformato());//3
            nuevoRegistro.put("FORMACION", Nombre_test);//4
            nuevoRegistro.put("PERSONA",Nombre_vs);//5
            nuevoRegistro.put("TIP", TIP);//6
            nuevoRegistro.put("RESULTADO",  TOTAL_CORRECTAS);//7
            nuevoRegistro.put("APROBADO", 0);//8
            nuevoRegistro.put("FIRMA", "SIN FIRMAR");//9
            nuevoRegistro.put("FORMADOR", Nombre_formador);//10
            //Insertamos el registro en la base de datos
            db.insert("FORMACIONFIRMADAS", null, nuevoRegistro);
            Log.i(this.getClass().toString(), "INSERTADO NUEVO REGISTRO");
            // Cerramos la base de datos
            db.close();

            EXPORTAR();
        }
    }
    // EXPORTAMOS ARCHIVO A EFECTOS ESTADISTICOS
    private void EXPORTAR(){

        String sqlORDENES;

        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();

        sqlORDENES="SELECT * FROM FORMACIONFIRMADAS";

        Cursor c = db.rawQuery(sqlORDENES, null);

        if (c.getCount()!=0){

            c.moveToLast();

            String NOMBREFICHERO="FORMACION_FIRMADA_"+getCode()+"_"+ID_DISPOSITIVO+".txt";

            try {

                File DIR = new File(Environment.getExternalStorageDirectory().getPath()+TipdbAdapter.R_RUTA_EXPORTACIONES);

                if (!DIR.exists()){DIR.mkdir();}

                File file= new File(DIR,NOMBREFICHERO);

                OutputStreamWriter fout =new OutputStreamWriter(new FileOutputStream(file));

                String linea=System.getProperty("line.separator");

                fout.write("ID" + ";" + "FECHA" + ";" + "HORAINICIO" + ";" + "HORAFIN" + ";" + "FORMACION" + ";" + "PERSONA" + ";" + "TIP" + ";" + "RESULTADO" + ";" + "APROBADO" + ";" + "FIRMA"+ ";" + "FORMADOR" + linea);

                do {
                    String registro=c.getString(0)+ ";" +c.getString(1)+ ";" +c.getString(2)+ ";" +c.getString(3)+ ";" +c.getString(4)+ ";" +c.getString(5)+ ";" +c.getString(6)+ ";"+c.getString(7)+ ";" +c.getString(8)+ ";" +c.getString(9)+ ";"+c.getString(10)+ ";" ;

                    fout.write(registro);

                } while (c.moveToNext());

                fout.close();

                c.close();

                db.close();


            } catch (Exception ex) {
                Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
            }
        }else{Toast.makeText(getBaseContext(), "DATOS no EXPORTADOS ", Toast.LENGTH_LONG).show();}
    }
    // COLA DE ARCHIVO CON FECHA Y HORA SIN GUIONES
    private String getCode() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String date = dateFormat.format(new Date());
        String photoCode =  "_"+date ;
        return photoCode;
    }
    // sTRING CON LA FECHA ACTUAL
    public String FECHAconformato() {
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        return dateString;}
    // sTRING CON LA HORA ACTUAL
    public String HORAconformato() {
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String dateString = sdf.format(date);
        return dateString;
    }
    public void salir_menu_test(View view){
        finish();
    }
    // ver pdf
    public void VER_PDF(){
        String Nombre_fichero="";
        String ruta="";
        switch (Nombre_test){
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
            startActivity(i);}
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {

            MediaPlayer mp= MediaPlayer.create(this,R.raw.aten_cion);

            mp.start();

            new AlertDialog.Builder(this)

                    .setIcon(R.drawable.infor_macion)
                    .setTitle("SALIR DE TEST")
                    .setMessage("ESTA SEGURO DE SALIR DEL TEST?")

                    .setNegativeButton(android.R.string.cancel, null)// sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
// Salir
                            finish();
                        }
                    })
                    .show();

// Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
// para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }

}
