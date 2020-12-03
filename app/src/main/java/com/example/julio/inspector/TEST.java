package com.example.julio.inspector;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class TEST extends AppCompatActivity {

    private int respuestas,TOTAL_RESPUESTAS,TOTAL_CORRECTAS,R1,R2,R3,R4,R5,R6,R7,R8,R9,R10,C1,C2,C3,C4,C5,C6,C7,C8,C9,C10;
    private int aleat[];
    private String Nombre_vs,Nombre_test,Nombre_formador,HORAINICIO,TIP,ID_DISPOSITIVO;
    private List<Integer> Preguntas2,RESPUESTAS;
    private TipdbAdapter test;
    private Button Terminar;
    private Integer Numero_preguntas;
    private Boolean tipo_personal;
    private TextView pr1,pr2,pr3,pr4,pr5,pr6,pr7,pr8,pr9,pr10,TOTAL;
    private CheckBox ck1_A,ck1_B,ck1_C,ck1_D;
    private CheckBox ck2_A,ck2_B,ck2_C,ck2_D;
    private CheckBox ck3_A,ck3_B,ck3_C,ck3_D;
    private CheckBox ck4_A,ck4_B,ck4_C,ck4_D;
    private CheckBox ck5_A,ck5_B,ck5_C,ck5_D;
    private CheckBox ck6_A,ck6_B,ck6_C,ck6_D;
    private CheckBox ck7_A,ck7_B,ck7_C,ck7_D;
    private CheckBox ck8_A,ck8_B,ck8_C,ck8_D;
    private CheckBox ck9_A,ck9_B,ck9_C,ck9_D;
    private CheckBox ck10_A,ck10_B,ck10_C,ck10_D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Nombre_vs=getIntent().getStringExtra("NOMBRE_VS");

        Nombre_test=getIntent().getStringExtra("NOMBRE_TEST");

        Nombre_formador=getIntent().getStringExtra("FORMADOR");

        TIP=getIntent().getStringExtra("TIP");

        tipo_personal=getIntent().getBooleanExtra("tipo_personal",true);

        if (tipo_personal==true) {

            setTitle("FORMACION EN " + Nombre_test + ": " + Nombre_vs + "\n con TIP " + TIP);

        }else{

            setTitle("FORMACION EN " + Nombre_test + ": " + Nombre_vs + "\n con DNI " + TIP);

        }

        INICIALIZAR();

        LLENAR_TEST(Nombre_test);

        ID_DISPOSITIVO= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

    }
    // DECLACION DE CONTROLES Y/O INICIALIZACION DE VARIABLES
    private void INICIALIZAR(){


        pr1=(TextView)findViewById(R.id.pregunta1);
        pr2=(TextView)findViewById(R.id.pregunta2);
        pr3=(TextView)findViewById(R.id.pregunta3);
        pr4=(TextView)findViewById(R.id.pregunta4);
        pr5=(TextView)findViewById(R.id.pregunta5);
        pr6=(TextView)findViewById(R.id.pregunta6);
        pr7=(TextView)findViewById(R.id.pregunta7);
        pr8=(TextView)findViewById(R.id.pregunta8);
        pr9=(TextView)findViewById(R.id.pregunta9);
        pr10=(TextView)findViewById(R.id.pregunta10);
        TOTAL=(TextView)findViewById(R.id.TOTAL_RESPUESTAS);


        ck1_A=(CheckBox)findViewById(R.id.ck1_A);
        ck1_B=(CheckBox)findViewById(R.id.ck1_B);
        ck1_C=(CheckBox)findViewById(R.id.ck1_C);
        ck1_D=(CheckBox)findViewById(R.id.ck1_D);

        ck2_A=(CheckBox)findViewById(R.id.ck2_A);
        ck2_B=(CheckBox)findViewById(R.id.ck2_B);
        ck2_C=(CheckBox)findViewById(R.id.ck2_C);
        ck2_D=(CheckBox)findViewById(R.id.ck2_D);

        ck3_A=(CheckBox)findViewById(R.id.ck3_A);
        ck3_B=(CheckBox)findViewById(R.id.ck3_B);
        ck3_C=(CheckBox)findViewById(R.id.ck3_C);
        ck3_D=(CheckBox)findViewById(R.id.ck3_D);

        ck4_A=(CheckBox)findViewById(R.id.ck4_A);
        ck4_B=(CheckBox)findViewById(R.id.ck4_B);
        ck4_C=(CheckBox)findViewById(R.id.ck4_C);
        ck4_D=(CheckBox)findViewById(R.id.ck4_D);

        ck5_A=(CheckBox)findViewById(R.id.ck5_A);
        ck5_B=(CheckBox)findViewById(R.id.ck5_B);
        ck5_C=(CheckBox)findViewById(R.id.ck5_C);
        ck5_D=(CheckBox)findViewById(R.id.ck5_D);

        ck6_A=(CheckBox)findViewById(R.id.ck6_A);
        ck6_B=(CheckBox)findViewById(R.id.ck6_B);
        ck6_C=(CheckBox)findViewById(R.id.ck6_C);
        ck6_D=(CheckBox)findViewById(R.id.ck6_D);

        ck7_A=(CheckBox)findViewById(R.id.ck7_A);
        ck7_B=(CheckBox)findViewById(R.id.ck7_B);
        ck7_C=(CheckBox)findViewById(R.id.ck7_C);
        ck7_D=(CheckBox)findViewById(R.id.ck7_D);

        ck8_A=(CheckBox)findViewById(R.id.ck8_A);
        ck8_B=(CheckBox)findViewById(R.id.ck8_B);
        ck8_C=(CheckBox)findViewById(R.id.ck8_C);
        ck8_D=(CheckBox)findViewById(R.id.ck8_D);

        ck9_A=(CheckBox)findViewById(R.id.ck9_A);
        ck9_B=(CheckBox)findViewById(R.id.ck9_B);
        ck9_C=(CheckBox)findViewById(R.id.ck9_C);
        ck9_D=(CheckBox)findViewById(R.id.ck9_D);

        ck10_A=(CheckBox)findViewById(R.id.ck10_A);
        ck10_B=(CheckBox)findViewById(R.id.ck10_B);
        ck10_C=(CheckBox)findViewById(R.id.ck10_C);
        ck10_D=(CheckBox)findViewById(R.id.ck10_D);

        Terminar=(Button)findViewById(R.id.FINALIZAR_TEST);

        // VALOR INICIAL DE PREGUNTAS CON RESPUESTA
        TOTAL_RESPUESTAS=0;
        TOTAL_CORRECTAS=0;
        RESPUESTAS=new ArrayList<>(10);
        R1=0;R2=0;R3=0;R4=0;R5=0;R6=0;R7=0;R8=0;R9=0;R10=0;
        C1=0;C2=0;C3=0;C4=0;C5=0;C6=0;C7=0;C8=0;C9=0;C10=0;
        HORAINICIO=HORAconformato();

    }

    // ASIGNACION DE PREGUNTAS Y RESPUESTAS A LOS CONTROLES
    private void LLENAR_TEST(String nombre_test){

        test = new TipdbAdapter(this) ;

        test.abrir();

        Cursor C_test=test.TEST(nombre_test);

        Numero_preguntas=C_test.getCount();

        //Toast.makeText(getBaseContext(), "Numero_preguntas "+Numero_preguntas, Toast.LENGTH_LONG).show();

        C_test.moveToFirst();

        respuestas=C_test.getInt(7);RESPUESTAS.add(respuestas);
        pr1.setText("01.- " + C_test.getString(2) );
        ck1_A.setText(C_test.getString(3));ck1_A.setChecked(false);ck1_A.setBackgroundColor(Color.WHITE);
        ck1_B.setText(C_test.getString(4));ck1_B.setChecked(false);ck1_B.setBackgroundColor(Color.WHITE);
        ck1_C.setText(C_test.getString(5));ck1_C.setChecked(false);ck1_C.setBackgroundColor(Color.WHITE);
        ck1_D.setText(C_test.getString(6));ck1_D.setChecked(false);ck1_D.setBackgroundColor(Color.WHITE);
        C_test.moveToNext();
        respuestas=C_test.getInt(7);RESPUESTAS.add(respuestas);
        pr2.setText("02.- " + C_test.getString(2) );
        ck2_A.setText(C_test.getString(3));ck2_A.setChecked(false);ck2_A.setBackgroundColor(Color.WHITE);
        ck2_B.setText(C_test.getString(4));ck2_B.setChecked(false);ck2_B.setBackgroundColor(Color.WHITE);
        ck2_C.setText(C_test.getString(5));ck2_C.setChecked(false);ck2_C.setBackgroundColor(Color.WHITE);
        ck2_D.setText(C_test.getString(6));ck2_D.setChecked(false);ck2_D.setBackgroundColor(Color.WHITE);
        C_test.moveToNext();
        respuestas=C_test.getInt(7);RESPUESTAS.add(respuestas);
        pr3.setText("03.- " + C_test.getString(2) );
        ck3_A.setText(C_test.getString(3));ck3_A.setChecked(false);ck3_A.setBackgroundColor(Color.WHITE);
        ck3_B.setText(C_test.getString(4));ck3_B.setChecked(false);ck3_B.setBackgroundColor(Color.WHITE);
        ck3_C.setText(C_test.getString(5));ck3_C.setChecked(false);ck3_C.setBackgroundColor(Color.WHITE);
        ck3_D.setText(C_test.getString(6));ck3_D.setChecked(false);ck3_D.setBackgroundColor(Color.WHITE);
        C_test.moveToNext();
        respuestas=C_test.getInt(7);RESPUESTAS.add(respuestas);
        pr4.setText("04.- " + C_test.getString(2) );
        ck4_A.setText(C_test.getString(3));ck4_A.setChecked(false);ck4_A.setBackgroundColor(Color.WHITE);
        ck4_B.setText(C_test.getString(4));ck4_B.setChecked(false);ck4_B.setBackgroundColor(Color.WHITE);
        ck4_C.setText(C_test.getString(5));ck4_C.setChecked(false);ck4_C.setBackgroundColor(Color.WHITE);
        ck4_D.setText(C_test.getString(6));ck4_D.setChecked(false);ck4_D.setBackgroundColor(Color.WHITE);
        C_test.moveToNext();
        respuestas=C_test.getInt(7);RESPUESTAS.add(respuestas);
        pr5.setText("05.- " + C_test.getString(2) );
        ck5_A.setText(C_test.getString(3));ck5_A.setChecked(false);ck5_A.setBackgroundColor(Color.WHITE);
        ck5_B.setText(C_test.getString(4));ck5_B.setChecked(false);ck5_B.setBackgroundColor(Color.WHITE);
        ck5_C.setText(C_test.getString(5));ck5_C.setChecked(false);ck5_C.setBackgroundColor(Color.WHITE);
        ck5_D.setText(C_test.getString(6));ck5_D.setChecked(false);ck5_D.setBackgroundColor(Color.WHITE);

        // a partir de aqui preguntas aleatorias
        switch (Nombre_test){
            case "INSPECCIONES MANUALES":
                LLENAR_ALEATORIO();
                break;
            case "CONTROL PORTON APM":
                LLENAR_ALEATORIO();
                break;
            case "INSPECCIONES CARRITOS LIMPIEZA":
                LLENAR_FIJO();
                break;
            case "UTILIZACION EQUIPOS ETD":
                LLENAR_ALEATORIO();
                break;
            case "REGISTRO DE SEGURIDAD EN AERONAVES":
                LLENAR_ALEATORIO_20P();
                break;
             default:
                 LLENAR_ALEATORIO_20P();

             break;}
        //if (Nombre_test.equals("INSPECCIONES CARRITOS LIMPIEZA")){LLENAR_FIJO();}else{LLENAR_ALEATORIO();}

        C_test.moveToPosition(Preguntas2.get(0) - 1);
        respuestas=C_test.getInt(7);RESPUESTAS.add(respuestas);
        pr6.setText("06.- " + C_test.getString(2) );
        ck6_A.setText(C_test.getString(3));ck6_A.setChecked(false);ck6_A.setBackgroundColor(Color.WHITE);
        ck6_B.setText(C_test.getString(4));ck6_B.setChecked(false);ck6_B.setBackgroundColor(Color.WHITE);
        ck6_C.setText(C_test.getString(5));ck6_C.setChecked(false);ck6_C.setBackgroundColor(Color.WHITE);
        ck6_D.setText(C_test.getString(6));ck6_D.setChecked(false);ck6_D.setBackgroundColor(Color.WHITE);

        C_test.moveToPosition(Preguntas2.get(1) - 1);
        respuestas=C_test.getInt(7);RESPUESTAS.add(respuestas);
        pr7.setText("07.- " + C_test.getString(2) );
        ck7_A.setText(C_test.getString(3));ck7_A.setChecked(false);ck7_A.setBackgroundColor(Color.WHITE);
        ck7_B.setText(C_test.getString(4));ck7_B.setChecked(false);ck7_B.setBackgroundColor(Color.WHITE);
        ck7_C.setText(C_test.getString(5));ck7_C.setChecked(false);ck7_C.setBackgroundColor(Color.WHITE);
        ck7_D.setText(C_test.getString(6));ck7_D.setChecked(false);ck7_D.setBackgroundColor(Color.WHITE);

        C_test.moveToPosition(Preguntas2.get(2) - 1);
        respuestas=C_test.getInt(7);RESPUESTAS.add(respuestas);
        pr8.setText("08.- " + C_test.getString(2) );
        ck8_A.setText(C_test.getString(3));ck8_A.setChecked(false);ck8_A.setBackgroundColor(Color.WHITE);
        ck8_B.setText(C_test.getString(4));ck8_B.setChecked(false);ck8_B.setBackgroundColor(Color.WHITE);
        ck8_C.setText(C_test.getString(5));ck8_C.setChecked(false);ck8_C.setBackgroundColor(Color.WHITE);
        ck8_D.setText(C_test.getString(6));ck8_D.setChecked(false);ck8_D.setBackgroundColor(Color.WHITE);

        C_test.moveToPosition(Preguntas2.get(3) - 1);
        respuestas=C_test.getInt(7);RESPUESTAS.add(respuestas);
        pr9.setText("09.- " + C_test.getString(2) );
        ck9_A.setText(C_test.getString(3));ck9_A.setChecked(false);ck9_A.setBackgroundColor(Color.WHITE);
        ck9_B.setText(C_test.getString(4));ck9_B.setChecked(false);ck9_B.setBackgroundColor(Color.WHITE);
        ck9_C.setText(C_test.getString(5));ck9_C.setChecked(false);ck9_C.setBackgroundColor(Color.WHITE);
        ck9_D.setText(C_test.getString(6));ck9_D.setChecked(false);ck9_D.setBackgroundColor(Color.WHITE);

        C_test.moveToPosition(Preguntas2.get(4) - 1);
        respuestas=C_test.getInt(7);RESPUESTAS.add(respuestas);
        pr10.setText("10.- " + C_test.getString(2) );
        ck10_A.setText(C_test.getString(3));ck10_A.setChecked(false);ck10_A.setBackgroundColor(Color.WHITE);
        ck10_B.setText(C_test.getString(4));ck10_B.setChecked(false);ck10_B.setBackgroundColor(Color.WHITE);
        ck10_C.setText(C_test.getString(5));ck10_C.setChecked(false);ck10_C.setBackgroundColor(Color.WHITE);
        ck10_D.setText(C_test.getString(6));ck10_D.setChecked(false);ck10_D.setBackgroundColor(Color.WHITE);
        ck1_A.setFocusableInTouchMode(true);ck1_A.requestFocus();

        total(5, 10);
    }

    // ASIGNACION DE LAS ULTIMAS 5 PREGUNTAS ALEATORIAMENTE
    private void LLENAR_ALEATORIO_20P(){
        Preguntas2=new ArrayList<>(Numero_preguntas);


        for (int i=6;i<21;i++){

            Preguntas2.add(i);
        }
        Random random=new Random();

        while (Preguntas2.size()>5){

            int indicealeatorio=random.nextInt(Preguntas2.size());

            Preguntas2.remove(indicealeatorio);
        }
    }
    private void LLENAR_ALEATORIO(){
        Preguntas2=new ArrayList<>(10);


        for (int i=6;i<16;i++){

            Preguntas2.add(i);
        }
        Random random=new Random();

        while (Preguntas2.size()>5){

            int indicealeatorio=random.nextInt(Preguntas2.size());

            Preguntas2.remove(indicealeatorio);
        }
        //Toast.makeText(getBaseContext(), "numeros: "+Preguntas2.get(0)+"-"+Preguntas2.get(1)+"-"+Preguntas2.get(2)+"-"+Preguntas2.get(3)+"-"+Preguntas2.get(4), Toast.LENGTH_SHORT).show();
    }
    private void LLENAR_FIJO(){
        Preguntas2=new ArrayList<>(5);


        for (int i=6;i<11;i++){

            Preguntas2.add(i);
        }
         }
    private void LIMPIEZA(){

        INICIALIZAR();

        LLENAR_TEST(Nombre_test);
    }

    //botones check comprueba cual se ha pulsado, la cantidad de respuestas y cuales son correctas

    //PREGUNTA 1
    public void boton_ck1_A(View v){if (ck1_A.isChecked()==true){R1=1;total(1,0);ck1_B.setChecked(false);ck1_C.setChecked(false);ck1_D.setChecked(false);ck1_A.setBackgroundColor(Color.YELLOW);ck1_B.setBackgroundColor(Color.WHITE);ck1_C.setBackgroundColor(Color.WHITE);ck1_D.setBackgroundColor(Color.WHITE);}else{ck1_A.setBackgroundColor(Color.WHITE);R1=0;total(5,0);}}
    public void boton_ck1_B(View v){if (ck1_B.isChecked()==true){R1=1;total(2,0);ck1_A.setChecked(false);ck1_C.setChecked(false);ck1_D.setChecked(false);ck1_B.setBackgroundColor(Color.YELLOW);ck1_A.setBackgroundColor(Color.WHITE);ck1_C.setBackgroundColor(Color.WHITE);ck1_D.setBackgroundColor(Color.WHITE);}else{ck1_B.setBackgroundColor(Color.WHITE);R1=0;total(5,0);}}
    public void boton_ck1_C(View v){if (ck1_C.isChecked()==true){R1=1;total(3,0);ck1_B.setChecked(false);ck1_A.setChecked(false);ck1_D.setChecked(false);ck1_C.setBackgroundColor(Color.YELLOW);ck1_A.setBackgroundColor(Color.WHITE);ck1_B.setBackgroundColor(Color.WHITE);ck1_D.setBackgroundColor(Color.WHITE);}else{ck1_C.setBackgroundColor(Color.WHITE);R1=0;total(5,0);}}
    public void boton_ck1_D(View v){if (ck1_D.isChecked()==true){R1=1;total(4,0);ck1_B.setChecked(false);ck1_C.setChecked(false);ck1_A.setChecked(false);ck1_D.setBackgroundColor(Color.YELLOW);ck1_A.setBackgroundColor(Color.WHITE);ck1_B.setBackgroundColor(Color.WHITE);ck1_C.setBackgroundColor(Color.WHITE);}else{ck1_D.setBackgroundColor(Color.WHITE);R1=0;total(5,0);}}
    //PREGUNTA 2
    public void boton_ck2_A(View v){if (ck2_A.isChecked()==true){R2=1;total(1,1);ck2_B.setChecked(false);ck2_C.setChecked(false);ck2_D.setChecked(false);ck2_A.setBackgroundColor(Color.YELLOW);ck2_B.setBackgroundColor(Color.WHITE);ck2_C.setBackgroundColor(Color.WHITE);ck2_D.setBackgroundColor(Color.WHITE);}else{ck2_A.setBackgroundColor(Color.WHITE);R2=0;total(5,1);}}
    public void boton_ck2_B(View v){if (ck2_B.isChecked()==true){R2=1;total(2,1);ck2_A.setChecked(false);ck2_C.setChecked(false);ck2_D.setChecked(false);ck2_B.setBackgroundColor(Color.YELLOW);ck2_A.setBackgroundColor(Color.WHITE);ck2_C.setBackgroundColor(Color.WHITE);ck2_D.setBackgroundColor(Color.WHITE);}else{ck2_B.setBackgroundColor(Color.WHITE);R2=0;total(5,1);}}
    public void boton_ck2_C(View v){if (ck2_C.isChecked()==true){R2=1;total(3,1);ck2_B.setChecked(false);ck2_A.setChecked(false);ck2_D.setChecked(false);ck2_C.setBackgroundColor(Color.YELLOW);ck2_A.setBackgroundColor(Color.WHITE);ck2_B.setBackgroundColor(Color.WHITE);ck2_D.setBackgroundColor(Color.WHITE);}else{ck2_C.setBackgroundColor(Color.WHITE);R2=0;total(5,1);}}
    public void boton_ck2_D(View v){if (ck2_D.isChecked()==true){R2=1;total(4,1);ck2_B.setChecked(false);ck2_C.setChecked(false);ck2_A.setChecked(false);ck2_D.setBackgroundColor(Color.YELLOW);ck2_A.setBackgroundColor(Color.WHITE);ck2_B.setBackgroundColor(Color.WHITE);ck2_C.setBackgroundColor(Color.WHITE);}else{ck2_D.setBackgroundColor(Color.WHITE);R2=0;total(5,1);}}
    //PREGUNTA 3
    public void boton_ck3_A(View v){if (ck3_A.isChecked()==true){R3=1;total(1,2);ck3_B.setChecked(false);ck3_C.setChecked(false);ck3_D.setChecked(false);ck3_A.setBackgroundColor(Color.YELLOW);ck3_B.setBackgroundColor(Color.WHITE);ck3_C.setBackgroundColor(Color.WHITE);ck3_D.setBackgroundColor(Color.WHITE);}else{ck3_A.setBackgroundColor(Color.WHITE);R3=0;total(5,2);}}
    public void boton_ck3_B(View v){if (ck3_B.isChecked()==true){R3=1;total(2,2);ck3_A.setChecked(false);ck3_C.setChecked(false);ck3_D.setChecked(false);ck3_B.setBackgroundColor(Color.YELLOW);ck3_A.setBackgroundColor(Color.WHITE);ck3_C.setBackgroundColor(Color.WHITE);ck3_D.setBackgroundColor(Color.WHITE);}else{ck3_B.setBackgroundColor(Color.WHITE);R3=0;total(5,2);}}
    public void boton_ck3_C(View v){if (ck3_C.isChecked()==true){R3=1;total(3,2);ck3_B.setChecked(false);ck3_A.setChecked(false);ck3_D.setChecked(false);ck3_C.setBackgroundColor(Color.YELLOW);ck3_A.setBackgroundColor(Color.WHITE);ck3_B.setBackgroundColor(Color.WHITE);ck3_D.setBackgroundColor(Color.WHITE);}else{ck3_C.setBackgroundColor(Color.WHITE);R3=0;total(5,2);}}
    public void boton_ck3_D(View v){if (ck3_D.isChecked()==true){R3=1;total(4,2);ck3_B.setChecked(false);ck3_C.setChecked(false);ck3_A.setChecked(false);ck3_D.setBackgroundColor(Color.YELLOW);ck3_A.setBackgroundColor(Color.WHITE);ck3_B.setBackgroundColor(Color.WHITE);ck3_C.setBackgroundColor(Color.WHITE);}else{ck3_D.setBackgroundColor(Color.WHITE);R3=0;total(5,2);}}
    //PREGUNTA 4
    public void boton_ck4_A(View v){if (ck4_A.isChecked()==true){R4=1;total(1,3);ck4_B.setChecked(false);ck4_C.setChecked(false);ck4_D.setChecked(false);ck4_A.setBackgroundColor(Color.YELLOW);ck4_B.setBackgroundColor(Color.WHITE);ck4_C.setBackgroundColor(Color.WHITE);ck4_D.setBackgroundColor(Color.WHITE);}else{ck4_A.setBackgroundColor(Color.WHITE);R4=0;total(5,3);}}
    public void boton_ck4_B(View v){if (ck4_B.isChecked()==true){R4=1;total(2,3);ck4_A.setChecked(false);ck4_C.setChecked(false);ck4_D.setChecked(false);ck4_B.setBackgroundColor(Color.YELLOW);ck4_A.setBackgroundColor(Color.WHITE);ck4_C.setBackgroundColor(Color.WHITE);ck4_D.setBackgroundColor(Color.WHITE);}else{ck4_B.setBackgroundColor(Color.WHITE);R4=0;total(5,3);}}
    public void boton_ck4_C(View v){if (ck4_C.isChecked()==true){R4=1;total(3,3);ck4_B.setChecked(false);ck4_A.setChecked(false);ck4_D.setChecked(false);ck4_C.setBackgroundColor(Color.YELLOW);ck4_A.setBackgroundColor(Color.WHITE);ck4_B.setBackgroundColor(Color.WHITE);ck4_D.setBackgroundColor(Color.WHITE);}else{ck4_C.setBackgroundColor(Color.WHITE);R4=0;total(5,3);}}
    public void boton_ck4_D(View v){if (ck4_D.isChecked()==true){R4=1;total(4,3);ck4_B.setChecked(false);ck4_C.setChecked(false);ck4_A.setChecked(false);ck4_D.setBackgroundColor(Color.YELLOW);ck4_A.setBackgroundColor(Color.WHITE);ck4_B.setBackgroundColor(Color.WHITE);ck4_C.setBackgroundColor(Color.WHITE);}else{ck4_D.setBackgroundColor(Color.WHITE);R4=0;total(5,3);}}
    //PREGUNTA 5
    public void boton_ck5_A(View v){if (ck5_A.isChecked()==true){R5=1;total(1,4);ck5_B.setChecked(false);ck5_C.setChecked(false);ck5_D.setChecked(false);ck5_A.setBackgroundColor(Color.YELLOW);ck5_B.setBackgroundColor(Color.WHITE);ck5_C.setBackgroundColor(Color.WHITE);ck5_D.setBackgroundColor(Color.WHITE);}else{ck5_A.setBackgroundColor(Color.WHITE);R5=0;total(5,4);}}
    public void boton_ck5_B(View v){if (ck5_B.isChecked()==true){R5=1;total(2,4);ck5_A.setChecked(false);ck5_C.setChecked(false);ck5_D.setChecked(false);ck5_B.setBackgroundColor(Color.YELLOW);ck5_A.setBackgroundColor(Color.WHITE);ck5_C.setBackgroundColor(Color.WHITE);ck5_D.setBackgroundColor(Color.WHITE);}else{ck5_B.setBackgroundColor(Color.WHITE);R5=0;total(5,4);}}
    public void boton_ck5_C(View v){if (ck5_C.isChecked()==true){R5=1;total(3,4);ck5_B.setChecked(false);ck5_A.setChecked(false);ck5_D.setChecked(false);ck5_C.setBackgroundColor(Color.YELLOW);ck5_A.setBackgroundColor(Color.WHITE);ck5_B.setBackgroundColor(Color.WHITE);ck5_D.setBackgroundColor(Color.WHITE);}else{ck5_C.setBackgroundColor(Color.WHITE);R5=0;total(5,4);}}
    public void boton_ck5_D(View v){if (ck5_D.isChecked()==true){R5=1;total(4,4);ck5_B.setChecked(false);ck5_C.setChecked(false);ck5_A.setChecked(false);ck5_D.setBackgroundColor(Color.YELLOW);ck5_A.setBackgroundColor(Color.WHITE);ck5_B.setBackgroundColor(Color.WHITE);ck5_C.setBackgroundColor(Color.WHITE);}else{ck5_D.setBackgroundColor(Color.WHITE);R5=0;total(5,4);}}
    //PREGUNTA 6
    public void boton_ck6_A(View v){if (ck6_A.isChecked()==true){R6=1;total(1,5);ck6_B.setChecked(false);ck6_C.setChecked(false);ck6_D.setChecked(false);ck6_A.setBackgroundColor(Color.YELLOW);ck6_B.setBackgroundColor(Color.WHITE);ck6_C.setBackgroundColor(Color.WHITE);ck6_D.setBackgroundColor(Color.WHITE);}else{ck6_A.setBackgroundColor(Color.WHITE);R6=0;total(5,5);}}
    public void boton_ck6_B(View v){if (ck6_B.isChecked()==true){R6=1;total(2,5);ck6_A.setChecked(false);ck6_C.setChecked(false);ck6_D.setChecked(false);ck6_B.setBackgroundColor(Color.YELLOW);ck6_A.setBackgroundColor(Color.WHITE);ck6_C.setBackgroundColor(Color.WHITE);ck6_D.setBackgroundColor(Color.WHITE);}else{ck6_B.setBackgroundColor(Color.WHITE);R6=0;total(5,5);}}
    public void boton_ck6_C(View v){if (ck6_C.isChecked()==true){R6=1;total(3,5);ck6_B.setChecked(false);ck6_A.setChecked(false);ck6_D.setChecked(false);ck6_C.setBackgroundColor(Color.YELLOW);ck6_A.setBackgroundColor(Color.WHITE);ck6_B.setBackgroundColor(Color.WHITE);ck6_D.setBackgroundColor(Color.WHITE);}else{ck6_C.setBackgroundColor(Color.WHITE);R6=0;total(5,5);}}
    public void boton_ck6_D(View v){if (ck6_D.isChecked()==true){R6=1;total(4,5);ck6_B.setChecked(false);ck6_C.setChecked(false);ck6_A.setChecked(false);ck6_D.setBackgroundColor(Color.YELLOW);ck6_A.setBackgroundColor(Color.WHITE);ck6_B.setBackgroundColor(Color.WHITE);ck6_C.setBackgroundColor(Color.WHITE);}else{ck6_D.setBackgroundColor(Color.WHITE);R6=0;total(5,5);}}
    //PREGUNTA 7
    public void boton_ck7_A(View v){if (ck7_A.isChecked()==true){R7=1;total(1,6);ck7_B.setChecked(false);ck7_C.setChecked(false);ck7_D.setChecked(false);ck7_A.setBackgroundColor(Color.YELLOW);ck7_B.setBackgroundColor(Color.WHITE);ck7_C.setBackgroundColor(Color.WHITE);ck7_D.setBackgroundColor(Color.WHITE);}else{ck7_A.setBackgroundColor(Color.WHITE);R7=0;total(5,6);}}
    public void boton_ck7_B(View v){if (ck7_B.isChecked()==true){R7=1;total(2,6);ck7_A.setChecked(false);ck7_C.setChecked(false);ck7_D.setChecked(false);ck7_B.setBackgroundColor(Color.YELLOW);ck7_A.setBackgroundColor(Color.WHITE);ck7_C.setBackgroundColor(Color.WHITE);ck7_D.setBackgroundColor(Color.WHITE);}else{ck7_B.setBackgroundColor(Color.WHITE);R7=0;total(5,6);}}
    public void boton_ck7_C(View v){if (ck7_C.isChecked()==true){R7=1;total(3,6);ck7_B.setChecked(false);ck7_A.setChecked(false);ck7_D.setChecked(false);ck7_C.setBackgroundColor(Color.YELLOW);ck7_A.setBackgroundColor(Color.WHITE);ck7_B.setBackgroundColor(Color.WHITE);ck7_D.setBackgroundColor(Color.WHITE);}else{ck7_C.setBackgroundColor(Color.WHITE);R7=0;total(5,6);}}
    public void boton_ck7_D(View v){if (ck7_D.isChecked()==true){R7=1;total(4,6);ck7_B.setChecked(false);ck7_C.setChecked(false);ck7_A.setChecked(false);ck7_D.setBackgroundColor(Color.YELLOW);ck7_A.setBackgroundColor(Color.WHITE);ck7_B.setBackgroundColor(Color.WHITE);ck7_C.setBackgroundColor(Color.WHITE);}else{ck7_D.setBackgroundColor(Color.WHITE);R7=0;total(5,6);}}
    //PREGUNTA 8
    public void boton_ck8_A(View v){if (ck8_A.isChecked()==true){R8=1;total(1,7);ck8_B.setChecked(false);ck8_C.setChecked(false);ck8_D.setChecked(false);ck8_A.setBackgroundColor(Color.YELLOW);ck8_B.setBackgroundColor(Color.WHITE);ck8_C.setBackgroundColor(Color.WHITE);ck8_D.setBackgroundColor(Color.WHITE);}else{ck8_A.setBackgroundColor(Color.WHITE);R8=0;total(5,7);}}
    public void boton_ck8_B(View v){if (ck8_B.isChecked()==true){R8=1;total(2,7);ck8_A.setChecked(false);ck8_C.setChecked(false);ck8_D.setChecked(false);ck8_B.setBackgroundColor(Color.YELLOW);ck8_A.setBackgroundColor(Color.WHITE);ck8_C.setBackgroundColor(Color.WHITE);ck8_D.setBackgroundColor(Color.WHITE);}else{ck8_B.setBackgroundColor(Color.WHITE);R8=0;total(5,7);}}
    public void boton_ck8_C(View v){if (ck8_C.isChecked()==true){R8=1;total(3,7);ck8_B.setChecked(false);ck8_A.setChecked(false);ck8_D.setChecked(false);ck8_C.setBackgroundColor(Color.YELLOW);ck8_A.setBackgroundColor(Color.WHITE);ck8_B.setBackgroundColor(Color.WHITE);ck8_D.setBackgroundColor(Color.WHITE);}else{ck8_C.setBackgroundColor(Color.WHITE);R8=0;total(5,7);}}
    public void boton_ck8_D(View v){if (ck8_D.isChecked()==true){R8=1;total(4,7);ck8_B.setChecked(false);ck8_C.setChecked(false);ck8_A.setChecked(false);ck8_D.setBackgroundColor(Color.YELLOW);ck8_A.setBackgroundColor(Color.WHITE);ck8_B.setBackgroundColor(Color.WHITE);ck8_C.setBackgroundColor(Color.WHITE);}else{ck8_D.setBackgroundColor(Color.WHITE);R8=0;total(5,7);}}
    //PREGUNTA 9
    public void boton_ck9_A(View v){if (ck9_A.isChecked()==true){R9=1;total(1,8);ck9_B.setChecked(false);ck9_C.setChecked(false);ck9_D.setChecked(false);ck9_A.setBackgroundColor(Color.YELLOW);ck9_B.setBackgroundColor(Color.WHITE);ck9_C.setBackgroundColor(Color.WHITE);ck9_D.setBackgroundColor(Color.WHITE);}else{ck9_A.setBackgroundColor(Color.WHITE);R9=0;total(5,8);}}
    public void boton_ck9_B(View v){if (ck9_B.isChecked()==true){R9=1;total(2,8);ck9_A.setChecked(false);ck9_C.setChecked(false);ck9_D.setChecked(false);ck9_B.setBackgroundColor(Color.YELLOW);ck9_A.setBackgroundColor(Color.WHITE);ck9_C.setBackgroundColor(Color.WHITE);ck9_D.setBackgroundColor(Color.WHITE);}else{ck9_B.setBackgroundColor(Color.WHITE);R9=0;total(5,8);}}
    public void boton_ck9_C(View v){if (ck9_C.isChecked()==true){R9=1;total(3,8);ck9_B.setChecked(false);ck9_A.setChecked(false);ck9_D.setChecked(false);ck9_C.setBackgroundColor(Color.YELLOW);ck9_A.setBackgroundColor(Color.WHITE);ck9_B.setBackgroundColor(Color.WHITE);ck9_D.setBackgroundColor(Color.WHITE);}else{ck9_C.setBackgroundColor(Color.WHITE);R9=0;total(5,8);}}
    public void boton_ck9_D(View v){if (ck9_D.isChecked()==true){R9=1;total(4,8);ck9_B.setChecked(false);ck9_C.setChecked(false);ck9_A.setChecked(false);ck9_D.setBackgroundColor(Color.YELLOW);ck9_A.setBackgroundColor(Color.WHITE);ck9_B.setBackgroundColor(Color.WHITE);ck9_C.setBackgroundColor(Color.WHITE);}else{ck9_D.setBackgroundColor(Color.WHITE);R9=0;total(5,8);}}
    //PREGUNTA 10
    public void boton_ck10_A(View v){if (ck10_A.isChecked()==true){R10=1;total(1,9);ck10_B.setChecked(false);ck10_C.setChecked(false);ck10_D.setChecked(false);ck10_A.setBackgroundColor(Color.YELLOW);ck10_B.setBackgroundColor(Color.WHITE);ck10_C.setBackgroundColor(Color.WHITE);ck10_D.setBackgroundColor(Color.WHITE);}else{ck10_A.setBackgroundColor(Color.WHITE);R10=0;total(5,9);}}
    public void boton_ck10_B(View v){if (ck10_B.isChecked()==true){R10=1;total(2,9);ck10_A.setChecked(false);ck10_C.setChecked(false);ck10_D.setChecked(false);ck10_B.setBackgroundColor(Color.YELLOW);ck10_A.setBackgroundColor(Color.WHITE);ck10_C.setBackgroundColor(Color.WHITE);ck10_D.setBackgroundColor(Color.WHITE);}else{ck10_B.setBackgroundColor(Color.WHITE);R10=0;total(5,9);}}
    public void boton_ck10_C(View v){if (ck10_C.isChecked()==true){R10=1;total(3, 9);ck10_B.setChecked(false);ck10_A.setChecked(false);ck10_D.setChecked(false);ck10_C.setBackgroundColor(Color.YELLOW);ck10_A.setBackgroundColor(Color.WHITE);ck10_B.setBackgroundColor(Color.WHITE);ck10_D.setBackgroundColor(Color.WHITE);}else{ck10_C.setBackgroundColor(Color.WHITE);R10=0;total(5, 9);}}
    public void boton_ck10_D(View v){if (ck10_D.isChecked()==true){R10=1;total(4, 9);ck10_B.setChecked(false);ck10_C.setChecked(false);ck10_A.setChecked(false);ck10_D.setBackgroundColor(Color.YELLOW);ck10_A.setBackgroundColor(Color.WHITE);ck10_B.setBackgroundColor(Color.WHITE);ck10_C.setBackgroundColor(Color.WHITE);}else{ck10_D.setBackgroundColor(Color.WHITE);R10=0;total(5, 9);}}
     // COMPROBACION DE RESULTADOS
    public void FINAL_TEST(View V){

        // SI LOS ACIERTOS SON MAS O IGUAL A 7 -APROBADO-
        if (TOTAL_CORRECTAS>=7){

            MediaPlayer mp= MediaPlayer.create(this,R.raw.aprobado);

            mp.start();



           // Toast.makeText(getBaseContext(), "RESULTADO: " + TOTAL_CORRECTAS+" RESPUESTAS CORRECTAS.\n  ESTA APROBADO, FIRME LA FORMACION", Toast.LENGTH_LONG).show();
          RESULTADOS(TOTAL_CORRECTAS);


        }else{

            MediaPlayer mp= MediaPlayer.create(this,R.raw.suspenso);

            mp.start();

            //GUARDAR_REGISTRO();

            RESULTADOS(TOTAL_CORRECTAS);

            //Toast.makeText(getBaseContext(), "RESULTADO: " + TOTAL_CORRECTAS+" RESPUESTAS CORRECTAS.\n  ESTA SUSPENSO", Toast.LENGTH_LONG).show();

           // VER_PDF();

            //finish();

        }
    }
    public void VER_PDF(){
        String Nombre_fichero="";
        String ruta="";
        switch (Nombre_test){
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
               // Nombre_fichero=TipdbAdapter.N_FICHERO_REGISTRO787;
                break;
        }



        ruta= Environment.getExternalStorageDirectory().getAbsolutePath() +TipdbAdapter.R_RUTA_FORMACION+Nombre_fichero;
        File file = new File(ruta);


        if (file.exists()) {
            Intent i= new Intent(this,pdfview.class);
            i.putExtra("ruta",ruta);
            startActivity(i);}
    }
    //calcula total respuestas/correctas
    private void total(int respuesta,int pregunta){

        switch (pregunta){
            case 0:
                if (respuesta==RESPUESTAS.get(pregunta)){C1=1;}else{C1=0;}
            break;
            case 1:
                if (respuesta==RESPUESTAS.get(pregunta)){C2=1;}else{C2=0;}
                break;
            case 2:
                if (respuesta==RESPUESTAS.get(pregunta)){C3=1;}else{C3=0;}
                break;
            case 3:
                if (respuesta==RESPUESTAS.get(pregunta)){C4=1;}else{C4=0;}
                break;
            case 4:
                if (respuesta==RESPUESTAS.get(pregunta)){C5=1;}else{C5=0;}
                break;
            case 5:
                if (respuesta==RESPUESTAS.get(pregunta)){C6=1;}else{C6=0;}
                break;
            case 6:
                if (respuesta==RESPUESTAS.get(pregunta)){C7=1;}else{C7=0;}
                break;
            case 7:
                if (respuesta==RESPUESTAS.get(pregunta)){C8=1;}else{C8=0;}
                break;
            case 8:
                if (respuesta==RESPUESTAS.get(pregunta)){C9=1;}else{C9=0;}
                break;
            case 9:
                if (respuesta==RESPUESTAS.get(pregunta)){C10=1;}else{C10=0;}
                break;
        }

        TOTAL_RESPUESTAS=R1+R2+R3+R4+R5+R6+R7+R8+R9+R10;

        TOTAL_CORRECTAS=C1+C2+C3+C4+C5+C6+C7+C8+C9+C10;

        TOTAL.setText(String.valueOf("RESPUESTAS: "+TOTAL_RESPUESTAS) + "/10");//INDICADOR DE RESPUESTAS CONTESTADAS

        if ((TOTAL_RESPUESTAS>=10) || (TOTAL_CORRECTAS>=7)){Terminar.setVisibility(View.VISIBLE);}else{Terminar.setVisibility(View.INVISIBLE);}
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
    // GUARDAMOS REGISTRO SI ESTA SUSPENSO
    public void GUARDAR_REGISTRO(){

        BasedbHelper  usdbh = new  BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if(db != null){
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put("FECHA",FECHAconformato());//1
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
    // DIALOGO DE RESULTADOS
    private void RESULTADOS(final Integer RESULT){

        final Intent i = new Intent(this, firmas.class);
        final ImageView nota= new ImageView(this);
        final String APROBADO;

        if (RESULT >= 7) {APROBADO="APROBADO";}else{APROBADO="SUSPENSO";}

        switch (RESULT){
            case 1:
                nota.setImageResource(R.drawable.uno);
                break;
            case 2:
                nota.setImageResource(R.drawable.dos);
                break;
            case 3:
                nota.setImageResource(R.drawable.tres);
                break;
            case 4:
                nota.setImageResource(R.drawable.cuatro);
                break;
            case 5:
                nota.setImageResource(R.drawable.cinco);
                break;
            case 6:
                nota.setImageResource(R.drawable.seis);
                break;
            case 7:
                nota.setImageResource(R.drawable.siete);
                break;
            case 8:
                nota.setImageResource(R.drawable.ocho);
                break;
            case 9:
                nota.setImageResource(R.drawable.nueve);
                break;
            case 10:
                nota.setImageResource(R.drawable.diez);
                break;
        }


        new AlertDialog.Builder(this)

                .setIcon(R.drawable.infor_macion)
                .setTitle("RESULTADO DEL TEST: " + APROBADO)
                .setMessage("RESPUESTAS CORRECTAS...")
                .setView(nota)

                .setPositiveButton(R.string.CONTINUAR, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
// Salir
                        if (RESULT >= 7) {

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
