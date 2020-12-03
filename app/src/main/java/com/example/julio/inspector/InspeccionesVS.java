package com.example.julio.inspector;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;

import android.support.v7.app.AppCompatActivity;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.text.SimpleDateFormat;

import android.database.sqlite.SQLiteDatabase;


public class InspeccionesVS extends AppCompatActivity implements OnItemSelectedListener  {
    private Spinner inspector,PUESTO,TIP,ZAPATOS,PANTALON,CAZADORA,CAMISA,ABRIGO,EPIS,GRILLETES,DEFENSA,CORREAJES,DESEMPEO,AENA;
    private ListView Lista_Inspeccion_manual;
    public EditText DNIVS,NOMBREVS,APELLIDO1VS,APELLIDO2VS,hora,fecha,OBSERV,OTROS,vs_INSPECTOR,vs_PUESTO,vs_TIP,vs_ZAPATOS,vs_PANTALOS,vs_CAZADORA,vs_CAMISA,vs_ABRIGO,vs_EPIS,vs_DEFENSA,vs_GRILLETES,vs_CORREAJES,vs_DESMPE,vs_CORRECCION;
    private CheckBox ORDENESFIRMADAS,SI,NO,TIPVIGOR;
    private Button botonGUARDAR,botonVER_REGISTROS;
    private TextView VS_PANEL_INSPECCION,VS_YA_INSPECCIONADO;
    private String vsDNI,vsTIP,vsNOMBRE,vsAPELLIDO1,vsAPELLIDO2,ID_VS;
    public String sINSPECTOR,SFECHA,SHORA,sPUESTO,ID_DISPOSITIVO,sDNI,sNOMBREVS,sAPELLIDO1VS,sAPELLIDO2VS,sZAPATOS,sPANTALON,sCAZADORA,sCAMISA,sABRIGO,sEPIS,sDEFENSA,sGRILLETES,sCORREAJES,sOTROS,sDESEMPE,sCORRECCIONES,sOBSERVACIONES,sCORRECTO,sTIPVALIDO,sORDENES;
    private ImageButton VS_PREV,VS_NEXT,VS_FIRST,VS_LAST;
    private TipdbAdapter dbAdapterSituacion,dbaena,dbInspeccion,DBADAPT;
    private int count_VER,countVS,n_REG,posicion_tip,position_inspector,position_puesto;
    private Cursor cursorListaSituacion,CURSORAENA,CURSOR_INSPECCION,CURSOR_INSPECTORES ;
    private AutoCompleteTextView Auto_OBSERVACIONES;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspecciones_vs);

        final String[] tip = getResources().getStringArray(R.array.dni);

        final String[] puesto = getResources().getStringArray(R.array.puestos);
        final String[] ins=getResources().getStringArray(R.array.INSPECTORES);
        final String[] DES3=getResources().getStringArray(R.array.DESPLEGABLE3);
        final String[] DES4=getResources().getStringArray(R.array.DESPLEGABLE4);
        final String[] OBSERVACIONES=getResources().getStringArray(R.array.OBSERVACIONES);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, ins);
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, puesto);
        ArrayAdapter<String> DESPLEGABLE3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, DES3);
        ArrayAdapter<String> DESPLEGABLE4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, DES4);
        ArrayAdapter<String> DESPLEGABLEdni = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tip);
        ArrayAdapter<String> adapter_obervacion= new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,OBSERVACIONES);

        DBADAPT = new TipdbAdapter(this) ;
        DBADAPT.abrir();

        Cursor cursorINSPECTORES = DBADAPT.getINSPECTOR();

        Cursor cursorPuestos=DBADAPT.Lista_Puestos();

        Cursor cursorTIP = DBADAPT.getnombreCOMPLETOI();

        SimpleCursorAdapter adapterINSPECTORES = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorINSPECTORES,(new String[] {"nombre"}), new int[] {android.R.id.text1},0);
        SimpleCursorAdapter adapterPuestos = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorPuestos,(new String[] {"puesto"}), new int[] {android.R.id.text1},0);
        SimpleCursorAdapter adapterSituacion = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorTIP,(new String[] {TipdbAdapter.C_COLUMNA_NOMBRE}), new int[] {android.R.id.text1},0);

        adapterSituacion.setDropDownViewResource(android.R.layout.simple_list_item_checked);


        INICIALIZAR();

        //NAVEGAR_VS(1);
        count_VER=1;









        //dbaena = new TipdbAdapter(this) ;
        //dbaena.abrir();

        //CURSORAENA = dbAdapterSituacion.getAENA();


        //SimpleCursorAdapter dbaena = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,CURSORAENA,new String[] {TipdbAdapter.C_COLUMNA_AENA}, new int[] {android.R.id.text1},0);

        //adapterSituacion.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        //AENA.setAdapter(dbaena);


        // IDENTIFICADOR DEL DISPOSITIVO
        ID_DISPOSITIVO= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        // ADAPTER Y LISTENER

        TIP.setAdapter(adapterSituacion);
        TIP.setOnItemSelectedListener(this);
        inspector.setAdapter(adapterINSPECTORES);
        inspector.setOnItemSelectedListener(this);
        PUESTO.setAdapter(adapterPuestos);
        PUESTO.setOnItemSelectedListener(this);

        ZAPATOS.setAdapter(DESPLEGABLE3);
        ZAPATOS.setOnItemSelectedListener(this);
        PANTALON.setAdapter(DESPLEGABLE3);
        PANTALON.setOnItemSelectedListener(this);
       CAZADORA.setAdapter(DESPLEGABLE3);
        CAZADORA.setOnItemSelectedListener(this);

        CAMISA.setAdapter(DESPLEGABLE3);
        CAMISA.setOnItemSelectedListener(this);
        ABRIGO.setAdapter(DESPLEGABLE3);
        ABRIGO.setOnItemSelectedListener(this);
        EPIS.setAdapter(DESPLEGABLE3);
        EPIS.setOnItemSelectedListener(this);

        GRILLETES.setAdapter(DESPLEGABLE3);
        GRILLETES.setOnItemSelectedListener(this);
        DEFENSA.setAdapter(DESPLEGABLE3);
        DEFENSA.setOnItemSelectedListener(this);
        CORREAJES.setAdapter(DESPLEGABLE3);
        CORREAJES.setOnItemSelectedListener(this);
        DESEMPEO.setAdapter(DESPLEGABLE4);
        DESEMPEO.setOnItemSelectedListener(this);

       Auto_OBSERVACIONES.setThreshold(2);
       Auto_OBSERVACIONES.setAdapter(adapter_obervacion);

        countVS=0;
        sCORRECCIONES="NO";

        VS_PANEL_INSPECCION.setText("REGISTRO: " + NUMERO_REGISTROS() );
    }

    private void INICIALIZAR(){
        //Lista_Inspeccion_manual= (ListView) findViewById(R.id.list_Inspeccion_manual);
        inspector = (Spinner) findViewById(R.id.S_INSPECTOR);
        PUESTO = (Spinner) findViewById(R.id.S_PUESTO);
        TIP= (Spinner) findViewById(R.id.s_TIP);
        //AENA= (Spinner) findViewById(R.id.spinCORRECION);

        ORDENESFIRMADAS=(CheckBox)findViewById(R.id.checkBox2);
       // TIPVIGOR=(CheckBox)findViewById(R.id.checkTIPVIGOR);
        SI=(CheckBox)findViewById(R.id.ck_invs_CORRECTO_SI);
        NO=(CheckBox)findViewById(R.id.ck_invs_CORRECTO_NO);
        botonGUARDAR=(Button)findViewById(R.id.CMD_INSVS_GUARDAR);
        fecha=(EditText)findViewById(R.id.FECHA);
        hora=(EditText)findViewById(R.id.HORA_inspeccion);
        //OBSERV=(EditText)findViewById(R.id.editOBSERVACION);
        Auto_OBSERVACIONES=(AutoCompleteTextView)findViewById(R.id.editText90);
        OTROS=(EditText)findViewById(R.id.editText89);

        //controles NAVEGACION_b
       /* vs_INSPECTOR=(EditText)findViewById(R.id.Sinspector);
        vs_PUESTO=(EditText)findViewById(R.id.ver_VS_NOMBREPUESTO);
        vs_TIP=(EditText)findViewById(R.id.ver_VS_VIGILANTE);
        vs_ZAPATOS=(EditText)findViewById(R.id.ver_VS_ZAPATOS);
        vs_PANTALOS=(EditText)findViewById(R.id.ver_VS_PANTALON);
        vs_CAZADORA=(EditText)findViewById(R.id.ver_VS_CAZADORA);
        vs_CAMISA=(EditText)findViewById(R.id.ver_VS_CAMISA);
        vs_ABRIGO=(EditText)findViewById(R.id.ver_VS_ABRIGO);
        vs_EPIS=(EditText)findViewById(R.id.ver_VS_EPIS);
        vs_DEFENSA=(EditText)findViewById(R.id.ver_VS_DEFENSA);
        vs_GRILLETES=(EditText)findViewById(R.id.ver_VS_GRILLETES);
        vs_CORREAJES=(EditText)findViewById(R.id.ver_VS_CORREAJES);
        vs_DESMPE=(EditText)findViewById(R.id.ver_VS_DESEMP);
        vs_CORRECCION=(EditText)findViewById(R.id.ver_VS_CORRECCIONES);*/

        //CONTROLES DE NAVEGACION
       /* VS_FIRST=(ImageButton)findViewById(R.id.imageButton12);
        VS_LAST=(ImageButton)findViewById(R.id.imageButton16);
        VS_PREV=(ImageButton)findViewById(R.id.imageButton13);
        VS_NEXT=(ImageButton)findViewById(R.id.imageButton15);*/

        //botonVER_REGISTROS=(Button)findViewById(R.id.BTVERREGISTROS);

        DNIVS=(EditText)findViewById(R.id.e_DNI);
        NOMBREVS=(EditText)findViewById(R.id.e_NOMBRE);
        APELLIDO1VS=(EditText)findViewById(R.id.e_APELLIDO1);
        APELLIDO2VS=(EditText)findViewById(R.id.e_APELLIDO2);
        VS_PANEL_INSPECCION=(TextView)findViewById(R.id.editText91);
        VS_YA_INSPECCIONADO=(TextView)findViewById(R.id.text_insvs_mensaje);

        // Spinner
        ZAPATOS = (Spinner) findViewById(R.id.sp_insvs_zapatos);
        PANTALON = (Spinner) findViewById(R.id.sp_insvs_pantalon);
        CAZADORA = (Spinner) findViewById(R.id.sp_insvs_cazadora);

        CAMISA = (Spinner) findViewById(R.id.sp_insvs_camisa);
        ABRIGO = (Spinner) findViewById(R.id.sp_insvs_abrigo);
        EPIS = (Spinner) findViewById(R.id.sp_insvs_epis);

        GRILLETES = (Spinner) findViewById(R.id.sp_insvs_grilletes);
        DEFENSA = (Spinner) findViewById(R.id.sp_insvs_defensa);
        CORREAJES = (Spinner) findViewById(R.id.sp_insvs_correajes);
        DESEMPEO=(Spinner) findViewById(R.id.sp_insvs_desempeño);
    }
    public void INSERTARINSPECCIONVS(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if(db != null){
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("inspector", sINSPECTOR);//1
            nuevoRegistro.put("fecha",SFECHA);//2
            nuevoRegistro.put("hora", SHORA);
            nuevoRegistro.put("puesto", sPUESTO);
            nuevoRegistro.put("tip", vsTIP);
            nuevoRegistro.put("nombre", sNOMBREVS);
            nuevoRegistro.put("apellidos1",sAPELLIDO1VS);
            nuevoRegistro.put("apellidos2", sAPELLIDO2VS);
            nuevoRegistro.put("dni", vsDNI);
            nuevoRegistro.put("zapatos", sZAPATOS);//10
            nuevoRegistro.put("pantalon", sPANTALON);
            nuevoRegistro.put("cazadora",sCAZADORA);
            nuevoRegistro.put("camisa", sCAMISA);
            nuevoRegistro.put("abrigo", sABRIGO);
            nuevoRegistro.put("epi", sEPIS);//15
            nuevoRegistro.put("defensa", sDEFENSA);
            nuevoRegistro.put("grilletes",sGRILLETES);
            nuevoRegistro.put("correajes", sCORREAJES);
            nuevoRegistro.put("otros", sOTROS);
            nuevoRegistro.put("ordenes", sORDENES);//20
            nuevoRegistro.put("desmpe", sDESEMPE);
           //nuevoRegistro.put("correcciones",sCORRECCIONES);
            nuevoRegistro.put("observacion", sOBSERVACIONES);
            nuevoRegistro.put("tipvalido", sTIPVALIDO);
            nuevoRegistro.put("correcto", sCORRECTO);//25
            nuevoRegistro.put("MESYANO", mesyano(FECHAconformato()));//26
            nuevoRegistro.put("ID_DISPOSITIVO", ID_DISPOSITIVO);
            //Insertamos el registro en la base de datos
            db.insert("inspeccionvs", null, nuevoRegistro);
            Log.i(this.getClass().toString(), "INSERTADO NUEVO REGISTRO");
            // Cerramos la base de datos
            db.close();

            Toast.makeText(getBaseContext(), "DATOS GUARDADOS ", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    public void CONSULTAR() {
        dbInspeccion = new TipdbAdapter(this);
        dbInspeccion.abrir();
        CURSOR_INSPECCION = dbInspeccion.getAENA();
        Inspeccion_manual CursorAdapter = new Inspeccion_manual(this, CURSOR_INSPECCION);
        Lista_Inspeccion_manual.setAdapter(CursorAdapter);
    }

    // string de fecha,hora y mes
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

            anno = FECHA.substring(6);

            mmes = FECHA.substring(3,5);


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
    private void COMPROBAR_INSPECCION(String TIP){
        dbAdapterSituacion = new TipdbAdapter(this) ;
        dbAdapterSituacion.abrir();
        Cursor cursor_puesto=dbAdapterSituacion.vstip(mesyano(FECHAconformato()), TIP);

        if(cursor_puesto.getCount()>0){
            cursor_puesto.moveToFirst();

            if (cursor_puesto.getString(5).equals(TIP)){
                VS_YA_INSPECCIONADO.setVisibility(View.VISIBLE);
                VS_YA_INSPECCIONADO.setText("(EL VS YA FUE INSPECCIONADO EN "+mesyano(FECHAconformato())+")");
                VS_YA_INSPECCIONADO.setTextColor(Color.RED);
                Toast.makeText(getBaseContext(), "EL VS HA SIDO INSPECCIONADO EN "+mesyano(FECHAconformato()), Toast.LENGTH_LONG).show();}
            else{VS_YA_INSPECCIONADO.setVisibility(View.INVISIBLE);}

        }else{VS_YA_INSPECCIONADO.setVisibility(View.INVISIBLE);}
    }
    private void COMPROBAR_ORDENES_FIRMADAS(String TIP){
        String CODIFICACION="";
        String PUESTO_REPLACE="";
        try {


            PUESTO_REPLACE=sPUESTO.replace("*","").toUpperCase().trim();
            CODIFICACION= new String(PUESTO_REPLACE.getBytes("UTF-8"));
        } catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");

        }
        dbAdapterSituacion = new TipdbAdapter(this) ;
        dbAdapterSituacion.abrir();

        if (position_puesto==1){CODIFICACION="A";}
        //Toast.makeText(InspeccionesVS.this, "ORDENES FIRMADAS: "+PUESTO_REPLACE, Toast.LENGTH_SHORT).show();
        Cursor cursor_puesto=dbAdapterSituacion.orden_firmada(CODIFICACION, TIP);


        if(cursor_puesto.getCount()>0){
            cursor_puesto.moveToFirst();
            String ORDEN;
                    if (cursor_puesto.getString(2).equals("A")){ ORDEN="Acceso central (antiguo Alaman)";}else {ORDEN=cursor_puesto.getString(2);}
                    Toast.makeText(InspeccionesVS.this, "ORDENES FIRMADAS: "+ ORDEN, Toast.LENGTH_SHORT).show();
                   ORDENESFIRMADAS.setChecked(true);
                   botonGUARDAR.setVisibility(View.VISIBLE);

            }else{
            ORDENESFIRMADAS.setChecked(false);
            mensajes();
        }

        }
    //messajes de aviso
    private void mensajes(){
        final AlertDialog.Builder mensajes= new AlertDialog.Builder(this);

        mensajes.setTitle("ORDEN DE PUESTO: "+sPUESTO);
        mensajes.setMessage("NO FIRMADA POR EL VS:" + vsNOMBRE + " " + vsAPELLIDO1 + " " + vsAPELLIDO2);
        mensajes.setCancelable(true);
        mensajes.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {


                Toast.makeText(InspeccionesVS.this, "Comprobar si tiene ORDENES firmadas", Toast.LENGTH_SHORT).show();


            }
        });
        AlertDialog alertDialog= mensajes.create();
        alertDialog.show();
    }
    public void INSPECCIONES_MANUALES(View view){
        sCORRECCIONES="SI";
        Intent i= new Intent(this,Inspecciones_manuales.class);
        i.putExtra("position_tip", posicion_tip);
        i.putExtra("position_ins", position_inspector);
        i.putExtra("position_puesto", position_puesto);
        startActivity(i);
    }
    public void limpiar(){
        OBSERV.setText("");
        Auto_OBSERVACIONES.setText("");
        OTROS.setText("");
        vs_TIP.setText("");
        NOMBREVS.setText("");
        DNIVS.setText("");
        APELLIDO1VS.setText("");
        APELLIDO2VS.setText("");
        fecha.setText("");
        hora.setText("");
        ORDENESFIRMADAS.setChecked(false);
        TIPVIGOR.setChecked(false);
    }
    public void VER_REGISTROS(View v){
        if (n_REG==0){Toast.makeText(getBaseContext(), "SIN REGISTROS...", Toast.LENGTH_SHORT).show();
        }else if (count_VER==1){
        VS_FIRST.setVisibility(View.VISIBLE);
        VS_LAST.setVisibility(View.VISIBLE);
        VS_PREV.setVisibility(View.VISIBLE);
        VS_NEXT.setVisibility(View.VISIBLE);
            vs_INSPECTOR.setVisibility(View.VISIBLE);
            vs_PUESTO.setVisibility(View.VISIBLE);
            vs_TIP.setVisibility(View.VISIBLE);
            vs_ZAPATOS.setVisibility(View.VISIBLE);
            vs_PANTALOS.setVisibility(View.VISIBLE);
            vs_CAZADORA.setVisibility(View.VISIBLE);
            vs_CAMISA.setVisibility(View.VISIBLE);
            vs_ABRIGO.setVisibility(View.VISIBLE);
            vs_EPIS.setVisibility(View.VISIBLE);
            vs_DEFENSA.setVisibility(View.VISIBLE);
            vs_GRILLETES.setVisibility(View.VISIBLE);
            vs_CORREAJES.setVisibility(View.VISIBLE);
            vs_DESMPE.setVisibility(View.VISIBLE);
            //vs_CORRECCION.setVisibility(View.VISIBLE);
            botonGUARDAR.setVisibility(View.INVISIBLE);
            inspector.setVisibility(View.INVISIBLE);
            PUESTO.setVisibility(View.INVISIBLE);
            TIP.setVisibility(View.INVISIBLE);
            AENA.setVisibility(View.INVISIBLE);
            ZAPATOS.setVisibility(View.INVISIBLE);
            PANTALON.setVisibility(View.INVISIBLE);
            CAZADORA.setVisibility(View.INVISIBLE);
            CAMISA.setVisibility(View.INVISIBLE);
            ABRIGO.setVisibility(View.INVISIBLE);
            EPIS.setVisibility(View.INVISIBLE);
            GRILLETES.setVisibility(View.INVISIBLE);
            DEFENSA.setVisibility(View.INVISIBLE);
            CORREAJES.setVisibility(View.INVISIBLE);
            DESEMPEO.setVisibility(View.INVISIBLE);
            NAVEGAR_VS(2);
            count_VER=2;
        }else if (count_VER==2){
            count_VER=1;
            VS_FIRST.setVisibility(View.INVISIBLE);
            VS_LAST.setVisibility(View.INVISIBLE);
            VS_PREV.setVisibility(View.INVISIBLE);
            VS_NEXT.setVisibility(View.INVISIBLE);
            vs_INSPECTOR.setVisibility(View.INVISIBLE);
            vs_PUESTO.setVisibility(View.INVISIBLE);
            vs_TIP.setVisibility(View.INVISIBLE);
            vs_ZAPATOS.setVisibility(View.INVISIBLE);
            vs_PANTALOS.setVisibility(View.INVISIBLE);
            vs_CAZADORA.setVisibility(View.INVISIBLE);
            vs_CAMISA.setVisibility(View.INVISIBLE);
            vs_ABRIGO.setVisibility(View.INVISIBLE);
            vs_EPIS.setVisibility(View.INVISIBLE);
            vs_DEFENSA.setVisibility(View.INVISIBLE);
            vs_GRILLETES.setVisibility(View.INVISIBLE);
            vs_CORREAJES.setVisibility(View.INVISIBLE);
            vs_DESMPE.setVisibility(View.INVISIBLE);
            vs_CORRECCION.setVisibility(View.INVISIBLE);

            inspector.setVisibility(View.VISIBLE);
            PUESTO.setVisibility(View.VISIBLE);
            TIP.setVisibility(View.VISIBLE);
            AENA.setVisibility(View.VISIBLE);
            ZAPATOS.setVisibility(View.VISIBLE);
            PANTALON.setVisibility(View.VISIBLE);
            CAZADORA.setVisibility(View.VISIBLE);
            CAMISA.setVisibility(View.VISIBLE);
            ABRIGO.setVisibility(View.VISIBLE);
            EPIS.setVisibility(View.VISIBLE);
            GRILLETES.setVisibility(View.VISIBLE);
            DEFENSA.setVisibility(View.VISIBLE);
            CORREAJES.setVisibility(View.VISIBLE);
            DESEMPEO.setVisibility(View.VISIBLE);
            limpiar();

        }
    }
    public void ORDENESPUESTO(View V){

        if (ORDENESFIRMADAS.isChecked() && count_VER!=2){
            Toast.makeText(getBaseContext(), "HABILITADA FUNCION GUARDAR... ", Toast.LENGTH_SHORT).show();
            botonGUARDAR.setVisibility(View.VISIBLE);

        }else{botonGUARDAR.setVisibility(View.INVISIBLE);}
    }
    public void guardar(View v){
        if (sPUESTO.equals("") ){
            Toast.makeText(getBaseContext(), "CAMPO PUESTO VACIO", Toast.LENGTH_SHORT).show();
        } else if (sINSPECTOR.equals("")){Toast.makeText(getBaseContext(), "CAMPO INPECTOR VACIO", Toast.LENGTH_SHORT).show();}else
        if (NO.isChecked() || SI.isChecked()){
            if (vsTIP.equals("")){Toast.makeText(getBaseContext(), "CAMPOS DEL VS VACIOS", Toast.LENGTH_SHORT).show();}else{
        sOTROS=OTROS.getText().toString();
        sOBSERVACIONES=Auto_OBSERVACIONES.getText().toString();
        SFECHA=fecha.getText().toString();
        SHORA=hora.getText().toString();
        sNOMBREVS=NOMBREVS.getText().toString();
        DNIVS.getText().toString();
        sAPELLIDO1VS=APELLIDO1VS.getText().toString();
        sAPELLIDO2VS=APELLIDO2VS.getText().toString();

        if (ORDENESFIRMADAS.isChecked()){sORDENES="SI";}
        //if (TIPVIGOR.isChecked()){sTIPVALIDO="SI";}
        if (NO.isChecked()){sCORRECTO="NO";}else {sCORRECTO="SI";}

        INSERTARINSPECCIONVS();
                 }
        }else {Toast.makeText(getBaseContext(), "MARQUE INSPECCION CORRECTA (SI/NO)", Toast.LENGTH_SHORT).show();}
    }
    public void CAMBIOCHECK(View v){
        if(SI.isChecked() ){NO.setChecked(false);}
        else {
            if (NO.isChecked()) {
                SI.setChecked(false);
            }

        }
    }
    public void CAMBIOCHECK1(View v) {
        if (NO.isChecked()) {
            SI.setChecked(false);
        } else {
            if (SI.isChecked()) {
                NO.setChecked(false);
            }
        }
    }
    public void VS_PRIMER(View v){
        NAVEGAR_VS(2);
    }
    public void VS_ULTIMO(View v){
        NAVEGAR_VS(3);
    }
    public void VS_ANTERIOR(View v){
        NAVEGAR_VS(5);
    }
    public void VS_SIGUIENTE(View v){
        NAVEGAR_VS(4);
    }
    private void registro(Cursor cursor){
        //VS_PREV.setEnabled(true);

        ID_VS=cursor.getString(0);
        vs_INSPECTOR.setText(cursor.getString(1));

        fecha.setText(cursor.getString(2));
        hora.setText(cursor.getString(3));
        //vs_PUESTO.setText(cursor.getString(4));


        vs_TIP.setText(cursor.getString(5));
        NOMBREVS.setText(cursor.getString(6));
        DNIVS.setText(cursor.getString(9));
        APELLIDO1VS.setText(cursor.getString(7));
        APELLIDO2VS.setText(cursor.getString(8));
        /*vs_ZAPATOS.setText(cursor.getString(10));
        vs_PANTALOS.setText(cursor.getString(11));
        vs_CAZADORA.setText(cursor.getString(12));
        vs_CAMISA.setText(cursor.getString(13));
        vs_ABRIGO.setText(cursor.getString(14));
        vs_EPIS.setText(cursor.getString(15));
        vs_DEFENSA.setText(cursor.getString(16));
        vs_GRILLETES.setText(cursor.getString(17));
        vs_CORREAJES.setText(cursor.getString(18));
        vs_DESMPE.setText(cursor.getString(21));
        vs_CORRECCION.setText(cursor.getString(22));*/

        Auto_OBSERVACIONES.setText(cursor.getString(23));
        OTROS.setText(cursor.getString(19));
        //if ((cursor.getString(20)).equals("SI")){
          //  ORDENESFIRMADAS.setChecked(true);
       // }else {ORDENESFIRMADAS.setChecked(false);}
        if ((cursor.getString(25)).equals("SI")){SI.setChecked(true);NO.setChecked(false);}else {NO.setChecked(true);SI.setChecked(false);}
        //if ((cursor.getString(24)).equals("SI")){
           // TIPVIGOR.setChecked(true);
        //}else{TIPVIGOR.setChecked(false);}
    }
    public int NUMERO_REGISTROS(){

        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM inspeccionvs ", null);

        return cursor.getCount();
    }
    private void NAVEGAR_VS(int N) {
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM inspeccionvs ", null);
        if (N == 1) {
            countVS=0;
            n_REG=cursor.getCount();
            VS_PANEL_INSPECCION.setText("REGISTRO: 0/" + String.valueOf(cursor.getCount()));
        }
        if (N == 2) {
            cursor.moveToFirst();//al primer registro

            countVS=cursor.getPosition();

            VS_PANEL_INSPECCION.setText("REGISTRO: " + (countVS + 1) + "/" + String.valueOf(cursor.getCount()));

            VS_PREV.setEnabled(false);
            if (cursor.getCount()==1){VS_NEXT.setEnabled(false);}else {
            VS_NEXT.setEnabled(true);}
            ID_VS=cursor.getString(0);
            vs_INSPECTOR.setText(cursor.getString(1));

            fecha.setText(cursor.getString(2));
            hora.setText(cursor.getString(3));
            vs_PUESTO.setText(cursor.getString(4));


            vs_TIP.setText(cursor.getString(5));
            NOMBREVS.setText(cursor.getString(6));
            DNIVS.setText(cursor.getString(9));
            APELLIDO1VS.setText(cursor.getString(7));
            APELLIDO2VS.setText(cursor.getString(8));
            vs_ZAPATOS.setText(cursor.getString(10));
            vs_PANTALOS.setText(cursor.getString(11));
            vs_CAZADORA.setText(cursor.getString(12));
            vs_CAMISA.setText(cursor.getString(13));
            vs_ABRIGO.setText(cursor.getString(14));
            vs_EPIS.setText(cursor.getString(15));
            vs_DEFENSA.setText(cursor.getString(16));
            vs_GRILLETES.setText(cursor.getString(17));
            vs_CORREAJES.setText(cursor.getString(18));
            vs_DESMPE.setText(cursor.getString(21));
            vs_CORRECCION.setText(cursor.getString(22));

            Auto_OBSERVACIONES.setText(cursor.getString(23));
            OTROS.setText(cursor.getString(19));
            if ((cursor.getString(20)).equals("SI")){
                ORDENESFIRMADAS.setChecked(true);
            }else {ORDENESFIRMADAS.setChecked(false);}
            if ((cursor.getString(25)).equals("SI")){
                SI.setChecked(true);NO.setChecked(false);
            }else {NO.setChecked(true);SI.setChecked(false);}
            if ((cursor.getString(24)).equals("SI")){
                TIPVIGOR.setChecked(true);
            }else{TIPVIGOR.setChecked(false);}
        }
        if (N == 3) {
            cursor.moveToLast();//al ultimo registro
            countVS=cursor.getPosition();

            VS_PANEL_INSPECCION.setText("REGISTRO: " + (countVS + 1) + "/" + String.valueOf(cursor.getCount()));

            VS_NEXT.setEnabled(false);
            if (cursor.getCount()==1){VS_PREV.setEnabled(false);}else {
                VS_PREV.setEnabled(true);}
            ID_VS=cursor.getString(0);
            vs_INSPECTOR.setText(cursor.getString(1));

            fecha.setText(cursor.getString(2));
            hora.setText(cursor.getString(3));
            vs_PUESTO.setText(cursor.getString(4));


            vs_TIP.setText(cursor.getString(5));
            NOMBREVS.setText(cursor.getString(6));
            DNIVS.setText(cursor.getString(9));
            APELLIDO1VS.setText(cursor.getString(7));
            APELLIDO2VS.setText(cursor.getString(8));
            vs_ZAPATOS.setText(cursor.getString(10));
            vs_PANTALOS.setText(cursor.getString(11));
            vs_CAZADORA.setText(cursor.getString(12));
            vs_CAMISA.setText(cursor.getString(13));
            vs_ABRIGO.setText(cursor.getString(14));
            vs_EPIS.setText(cursor.getString(15));
            vs_DEFENSA.setText(cursor.getString(16));
            vs_GRILLETES.setText(cursor.getString(17));
            vs_CORREAJES.setText(cursor.getString(18));
            vs_DESMPE.setText(cursor.getString(21));
            vs_CORRECCION.setText(cursor.getString(22));

            Auto_OBSERVACIONES.setText(cursor.getString(23));
            OTROS.setText(cursor.getString(19));
            if ((cursor.getString(20)).equals("SI")){
                ORDENESFIRMADAS.setChecked(true);
            }else {ORDENESFIRMADAS.setChecked(false);}
            if ((cursor.getString(25)).equals("SI")){
                SI.setChecked(true);NO.setChecked(false);
            }else {NO.setChecked(true);SI.setChecked(false);}
            if ((cursor.getString(24)).equals("SI")){
                TIPVIGOR.setChecked(true);
            }else{TIPVIGOR.setChecked(false);}
        }
        if (N == 4) {
            countVS+=1;
            //cursor.moveToPosition(1);;//al anterior registro


            cursor.moveToPosition(countVS);//al siguiente registro
            VS_PANEL_INSPECCION.setText("REGISTRO: " + (countVS + 1) + "/" + String.valueOf(cursor.getCount()));
            if (countVS+1==cursor.getCount()){VS_NEXT.setEnabled(false);}else {VS_NEXT.setEnabled(true);}
            registro(cursor);
        }
        if (N == 5) {
            countVS -= 1;

            cursor.moveToPosition(countVS);
            ;//al anterior registro
            // cursor.moveToPosition(countVS);;//al anterior registro

            VS_PANEL_INSPECCION.setText("REGISTRO: " + (countVS + 1) + "/" + String.valueOf(cursor.getCount()));
            if (countVS == 0) { VS_PREV.setEnabled(false);
            } else { VS_PREV.setEnabled(true);}
            if (cursor.getCount() == 1) {VS_NEXT.setEnabled(false);
            } else {VS_NEXT.setEnabled(true);}
            registro(cursor);
        }
    }
    private void llenar_con_ID (String _id){
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor csor = db.rawQuery("SELECT * FROM VS WHERE _id='"+_id+"'", null);
        csor.moveToFirst();

        vsNOMBRE=csor.getString(1);
        vsAPELLIDO2=csor.getString(3);
        vsAPELLIDO1=csor.getString(2);
        vsDNI=csor.getString(5);
        vsTIP=csor.getString(4);
    }
    private Integer position_tip(String tip){
        Integer pos_tip=0,n=0;
        String sql;
        BasedbHelper usdbVS = new BasedbHelper(this);

        SQLiteDatabase dbDV = usdbVS.getReadableDatabase();
        sql="SELECT * FROM VS";
        Cursor c = dbDV.rawQuery(sql, null);
        c.moveToFirst();
        do {
            if (c.getString(4).equals(tip)){pos_tip=n;break;}
            n=n+1;
        } while (c.moveToNext());
        return pos_tip;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inspecciones_v, menu);
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
         case R.id.s_TIP:


             Cursor c1=(Cursor)parent.getItemAtPosition(position);
             String _id=c1.getString(c1.getColumnIndex(TipdbAdapter.C_COLUMNA_ID));
             llenar_con_ID(_id);





             if (position!=0){
                 posicion_tip=position_tip(vsTIP);
                 COMPROBAR_INSPECCION(vsTIP);
                 if(position_puesto!=0){
                     COMPROBAR_ORDENES_FIRMADAS(vsTIP);
                 }
             }

             NOMBREVS.setText(vsNOMBRE);
             DNIVS.setText(vsDNI);

             APELLIDO1VS.setText(vsAPELLIDO1);
             APELLIDO2VS.setText(vsAPELLIDO2);

             NOMBREVS.getText().toString();
             DNIVS.getText().toString();
             APELLIDO1VS.getText().toString();
             APELLIDO2VS.getText().toString();



             break;
         case R.id.sp_insvs_desempeño:

             sDESEMPE=parent.getItemAtPosition(position).toString();

             if(!sDESEMPE.equals("Correcto")){NO.setChecked(true);SI.setChecked(false);}

             break;

         case R.id.sp_insvs_abrigo:

             sABRIGO=parent.getItemAtPosition(position).toString();

             break;
         case R.id.sp_insvs_zapatos:

             sZAPATOS=parent.getItemAtPosition(position).toString();

             if(!sZAPATOS.equals("Correcto")){NO.setChecked(true);SI.setChecked(false);}

             break;

         case R.id.sp_insvs_pantalon:

             sPANTALON=parent.getItemAtPosition(position).toString();

             if(!sPANTALON.equals("Correcto")){NO.setChecked(true);SI.setChecked(false);}

             break;

         case R.id.sp_insvs_cazadora:

             sCAZADORA=parent.getItemAtPosition(position).toString();

             break;

         case R.id.sp_insvs_camisa:

             sCAMISA=parent.getItemAtPosition(position).toString();

             if(!sCAMISA.equals("Correcto")){NO.setChecked(true);SI.setChecked(false);}

             break;

         case R.id.sp_insvs_epis:

             sEPIS=parent.getItemAtPosition(position).toString();

             break;
         case R.id.sp_insvs_grilletes:

             sGRILLETES=parent.getItemAtPosition(position).toString();

             if(!sGRILLETES.equals("Correcto")){NO.setChecked(true);SI.setChecked(false);}

             break;
         case R.id.sp_insvs_correajes:

             sCORREAJES=parent.getItemAtPosition(position).toString();

             if(!sCORREAJES.equals("Correcto")){NO.setChecked(true);SI.setChecked(false);}

             break;
         case R.id.S_PUESTO:
             Cursor c3=(Cursor)parent.getItemAtPosition(position);
             sPUESTO=c3.getString(c3.getColumnIndex(TipdbAdapter.C_COLUMNA_PUESTO));
            // sPUESTO=parent.getItemAtPosition(position).toString();
             position_puesto=position;

             if (position!=0){
                 //Toast.makeText(getBaseContext(), "puesto..: "+sPUESTO+position_puesto, Toast.LENGTH_SHORT).show();
                 if (!vsTIP.equals("")){COMPROBAR_INSPECCION(vsTIP);COMPROBAR_ORDENES_FIRMADAS(vsTIP);}

             }
             break;
         case R.id.sp_insvs_defensa:

             sDEFENSA=parent.getItemAtPosition(position).toString();

             if(!sDEFENSA.equals("Correcto")){NO.setChecked(true);SI.setChecked(false);}

             break;
         case R.id.S_INSPECTOR:

             Cursor c2=(Cursor)parent.getItemAtPosition(position);

             sINSPECTOR=c2.getString(c2.getColumnIndex("nombre"));

             if ((sINSPECTOR.equals(""))){

             break;}else {
                 //Toast.makeText(getBaseContext(), "INSPECTOR..: "+sINSPECTOR, Toast.LENGTH_SHORT).show();
                 fecha.setText(FECHAconformato());
                 SFECHA=fecha.getText().toString();
                 hora.setText(HORAconformato());
                 SHORA=hora.getText().toString();
                 //sINSPECTOR=parent.getItemAtPosition(position).toString();
                 position_inspector=position;
                 break;
             }
     }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
