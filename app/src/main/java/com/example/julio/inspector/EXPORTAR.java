package com.example.julio.inspector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.Settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;


public class EXPORTAR extends AppCompatActivity implements OnItemSelectedListener{
    private CheckBox inspeccionesvs,EXPORTAR_TODOSLOSREGISTROS;
    private RadioButton Rfecha,Rpuesto,Rtodo,Rinspector,Rvigilante;
    private Button exportINSPECIONVS,exportPUESTO,exportENSAYO;
    private TextView EXPORTAR_PANEL,text_F_DESDE,text_F_HASTA;
    private TipdbAdapter dbAdapterfecha,dbaena;
    private Cursor cursorListaSituacion1,cursorListaSituacion2 ;
    private Spinner EXPORTAR_FECHA_DESDE,EXPORTAR_FECHA_HASTA;
    private EditText FECHA_DESDE,FECHA_HASTA;
    private String ID_DISPOSITIVO;
    private final static String CARPETA_EXPORTACION = "/fotospuesto/EXPORTACIONES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportar);
        //CONTROLES
        exportINSPECIONVS=(Button)findViewById(R.id.BTexportar);
        exportPUESTO=(Button)findViewById(R.id.bt_puesto);
        exportENSAYO=(Button)findViewById(R.id.bt_ensayo);
        inspeccionesvs=(CheckBox)findViewById(R.id.checkINSPECIONESVS);
        EXPORTAR_PANEL=(TextView)findViewById(R.id.EX_PANEL);
        EXPORTAR_TODOSLOSREGISTROS=(CheckBox)findViewById(R.id.EX_TODOSLOSREGISTROS);
        FECHA_DESDE=(EditText)findViewById(R.id.FILTRO_fechadesde);
        FECHA_HASTA=(EditText)findViewById(R.id.FILTRO_fechahasta);
        text_F_DESDE=(TextView)findViewById(R.id.textView_FECHA_DESDE);
        text_F_HASTA=(TextView)findViewById(R.id.textView_FECHA_HASTA);

        EXPORTAR_FECHA_DESDE=(Spinner)findViewById(R.id.EX_FECHA_DESDE);
        EXPORTAR_FECHA_DESDE.setOnItemSelectedListener(this);
        EXPORTAR_FECHA_HASTA=(Spinner)findViewById(R.id.EX_FECHA_HASTA);
        EXPORTAR_FECHA_HASTA.setOnItemSelectedListener(this);
        // IDENTIFICADOR DEL DISPOSITIVO
        ID_DISPOSITIVO= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    public void expotar_ensayos(View v){

            ex_ensayo_todoslosregistros();

    }
    public void exportar_puesto(View v){

            ex_puesto_todoslosregistros();

    }
    public void verINSPECIONESVS(View v){
        if (inspeccionesvs.isChecked()){
            Rvigilante.setVisibility(View.VISIBLE);
            Rfecha.setVisibility(View.VISIBLE);
            Rpuesto.setVisibility(View.VISIBLE);
            Rtodo.setVisibility(View.VISIBLE);
            Rinspector.setVisibility(View.VISIBLE);
            exportINSPECIONVS.setVisibility(View.VISIBLE);
        }else {
            Rvigilante.setVisibility(View.INVISIBLE);
            Rfecha.setVisibility(View.INVISIBLE);
            Rpuesto.setVisibility(View.INVISIBLE);
            Rtodo.setVisibility(View.INVISIBLE);
            Rinspector.setVisibility(View.INVISIBLE);
            exportINSPECIONVS.setVisibility(View.INVISIBLE);
        }
    }
    public void EXPORT_BOTON_INSPECCIONVS(View V){
        EXPORTAR_PANEL.setText("EXPORTAR: INSPECCIONES DE VIGILANTES");
        dbAdapterfecha = new TipdbAdapter(this) ;
        dbAdapterfecha.abrir();

        cursorListaSituacion1 = dbAdapterfecha.getFECHA_DESDE_VS();
        cursorListaSituacion2 = dbAdapterfecha.getFECHA_HASTA_VS();
        SimpleCursorAdapter adapterfechadesde = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorListaSituacion1,new String[] {TipdbAdapter.C_COLUMNA_FECHA}, new int[] {android.R.id.text1});

        adapterfechadesde.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        SimpleCursorAdapter adapterfechahasta = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorListaSituacion1,new String[] {TipdbAdapter.C_COLUMNA_FECHA}, new int[] {android.R.id.text1});

        adapterfechahasta.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        EXPORTAR_FECHA_DESDE.setAdapter(adapterfechadesde);
        EXPORTAR_FECHA_HASTA.setAdapter(adapterfechahasta);
        exportINSPECIONVS.setVisibility(View.VISIBLE);
        exportENSAYO.setVisibility(View.INVISIBLE);
        exportPUESTO.setVisibility(View.INVISIBLE);
        text_F_DESDE.setVisibility(View.VISIBLE);
        text_F_HASTA.setVisibility(View.VISIBLE);
    }
    public void EXPORT_BOTON_INSPECCIONPUESTO(View V){
        EXPORTAR_PANEL.setText("EXPORTAR: INSPECCIONES DE PUESTO");
        dbAdapterfecha = new TipdbAdapter(this) ;
        dbAdapterfecha.abrir();

        cursorListaSituacion1 = dbAdapterfecha.getFECHA_DESDE_PUESTO();
        cursorListaSituacion2 = dbAdapterfecha.getFECHA_HASTA_PUESTO();
        SimpleCursorAdapter adapterfechadesde = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorListaSituacion1,new String[] {TipdbAdapter.C_COLUMNA_FECHA}, new int[] {android.R.id.text1});

        adapterfechadesde.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        SimpleCursorAdapter adapterfechahasta = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorListaSituacion1,new String[] {TipdbAdapter.C_COLUMNA_FECHA}, new int[] {android.R.id.text1});

        adapterfechahasta.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        EXPORTAR_FECHA_DESDE.setAdapter(adapterfechadesde);
        EXPORTAR_FECHA_HASTA.setAdapter(adapterfechahasta);
        exportPUESTO.setVisibility(View.VISIBLE);
        exportINSPECIONVS.setVisibility(View.INVISIBLE);
        exportENSAYO.setVisibility(View.INVISIBLE);
        text_F_DESDE.setVisibility(View.VISIBLE);
        text_F_HASTA.setVisibility(View.VISIBLE);
    }
    public void EXPORT_BOTON_ENSAYO(View V){
        EXPORTAR_PANEL.setText("EXPORTAR: ENSAYOS");
        dbAdapterfecha = new TipdbAdapter(this) ;
        dbAdapterfecha.abrir();

        cursorListaSituacion1 = dbAdapterfecha.getFECHA_DESDE_ENSAYO();
        cursorListaSituacion2 = dbAdapterfecha.getFECHA_HASTA_ENSAYO();
        SimpleCursorAdapter adapterfechadesde = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorListaSituacion1,new String[] {TipdbAdapter.C_COLUMNA_FECHA}, new int[] {android.R.id.text1});

        adapterfechadesde.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        SimpleCursorAdapter adapterfechahasta = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorListaSituacion1,new String[] {TipdbAdapter.C_COLUMNA_FECHA}, new int[] {android.R.id.text1});

        adapterfechahasta.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        EXPORTAR_FECHA_DESDE.setAdapter(adapterfechadesde);
        EXPORTAR_FECHA_HASTA.setAdapter(adapterfechahasta);
        exportENSAYO.setVisibility(View.VISIBLE);
        exportPUESTO.setVisibility(View.INVISIBLE);
        exportINSPECIONVS.setVisibility(View.INVISIBLE);
        text_F_DESDE.setVisibility(View.VISIBLE);
        text_F_HASTA.setVisibility(View.VISIBLE);
    }
    public void exportarvs(View v){
        String sqlENSAYOS;

        BasedbHelper usdbVS = new BasedbHelper(this);

        SQLiteDatabase dbDV = usdbVS.getReadableDatabase();


        String NOMBREFICHERO="INSPECCIONVS"+FECHA_FICHERO(FECHA_DESDE.getText().toString())+ID_DISPOSITIVO+".txt";
        if ( EXPORTAR_TODOSLOSREGISTROS.isChecked()){
            sqlENSAYOS="SELECT * FROM inspeccionvs ";
        }else {sqlENSAYOS="SELECT * FROM inspeccionvs WHERE _id>='" + id_desde(1) + "'"+ " AND "+"_id<='" + id_hasta(1) + "'";}

        Cursor c = dbDV.rawQuery(sqlENSAYOS, null);
        c.moveToFirst();
        Toast.makeText(getBaseContext(), NOMBREFICHERO, Toast.LENGTH_SHORT).show();


        try {
                    //File ruta_sd = Environment.getExternalStorageDirectory().getPath();
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
                    dbDV.close();
                    Toast.makeText(getBaseContext(), "DATOS EXPORTADOS", Toast.LENGTH_LONG).show();

                } catch (Exception ex) {
                    Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
                }



    }
    public void check_todos(View v){
        if (EXPORTAR_TODOSLOSREGISTROS.isChecked()){
            EXPORTAR_FECHA_DESDE.setVisibility(View.INVISIBLE);
            EXPORTAR_FECHA_HASTA.setVisibility(View.INVISIBLE);
            text_F_DESDE.setVisibility(View.INVISIBLE);
            text_F_HASTA.setVisibility(View.INVISIBLE);
        }else {
            EXPORTAR_FECHA_DESDE.setVisibility(View.VISIBLE);
            EXPORTAR_FECHA_HASTA.setVisibility(View.VISIBLE);
            text_F_DESDE.setVisibility(View.VISIBLE);
            text_F_HASTA.setVisibility(View.VISIBLE);
        }
    }
    private void ex_puesto_todoslosregistros(){
        String sqlPUESTOS;

        BasedbHelper usdbVS = new BasedbHelper(this);

        SQLiteDatabase dbDV = usdbVS.getReadableDatabase();


        String NOMBREFICHERO="INSPECCION_PUESTOS"+FECHA_FICHERO(FECHA_DESDE.getText().toString())+ID_DISPOSITIVO+".txt";


        if ( EXPORTAR_TODOSLOSREGISTROS.isChecked()){
            sqlPUESTOS="SELECT * FROM VISITAPUESTO";
        }else {sqlPUESTOS="SELECT * FROM VISITAPUESTO WHERE _id>='" + id_desde(2) + "'"+ " AND "+"_id<='" + id_hasta(2) + "'";}

        Cursor c = dbDV.rawQuery(sqlPUESTOS, null);
        c.moveToFirst();
        Toast.makeText(getBaseContext(), NOMBREFICHERO, Toast.LENGTH_SHORT).show();


        try {
            //File ruta_sd = Environment.getExternalStorageDirectory().getPath();
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
            dbDV.close();
            Toast.makeText(getBaseContext(), "DATOS EXPORTADOS ", Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
        }
    }
    private void ex_ensayo_todoslosregistros(){
    String sqlENSAYOS;
    BasedbHelper usdbVS = new BasedbHelper(this);

    SQLiteDatabase dbDV = usdbVS.getReadableDatabase();


    String NOMBREFICHERO="ENSAYOS"+FECHA_FICHERO(FECHA_DESDE.getText().toString())+ID_DISPOSITIVO+".txt";
    if ( EXPORTAR_TODOSLOSREGISTROS.isChecked()){
        sqlENSAYOS="SELECT * FROM ENSAYOS";
    }else {sqlENSAYOS="SELECT * FROM ENSAYOS WHERE _id>='" + id_desde(3) + "'"+ " AND "+"_id<='" + id_hasta(3) + "'";}
    Cursor c = dbDV.rawQuery(sqlENSAYOS, null);
    c.moveToFirst();
    Toast.makeText(getBaseContext(), NOMBREFICHERO, Toast.LENGTH_SHORT).show();


    try {
        //File ruta_sd = Environment.getExternalStorageDirectory().getPath();
        File DIR = new File(Environment.getExternalStorageDirectory().getPath()+CARPETA_EXPORTACION);
        if (!DIR.exists()){DIR.mkdir();}
        File file= new File(DIR,NOMBREFICHERO);

        OutputStreamWriter fout =
                new OutputStreamWriter(new FileOutputStream(file));
        String linea=System.getProperty("line.separator");
        fout.write("ID"+ ";" + "TIPO_ENSAYO" + ";" + "INSPECTOR" + ";" + "FECHA" + ";" + "HORA_INICIO" + ";" + "HORA_FIN" + ";" + "LUGAR" + ";" + "PERSONAL1" + ";" + "PERSONAL2" + ";" + "PERSONAL3" + ";" + "EQUIPOS_RX" + ";" + "ARCO_ADM" + ";" + "MEDIDAS_CORRECTORAS" + ";" + "RUTA_FOTO" + ";" + "DESCRIPCION"+ ";" + "RESULTADO" + linea);
        do {
            String registro=c.getString(0)+ ";%" +c.getString(1)+ "%;%" +c.getString(2)+ "%;" +c.getString(3)+ ";" +c.getString(4)+ ";" +c.getString(5)+ ";%" +c.getString(6)+ "%;%" +c.getString(7)+ "%;%" +c.getString(8)+ "%;%" +c.getString(9)+ "%;%" +c.getString(10)+ "%;%" +c.getString(11)+ "%;%" +c.getString(12)+ "%;%" +c.getString(13)+ "%;%" +c.getString(14)+ "%;%"+c.getString(15)+ "%";

            fout.write(registro+linea);
        } while (c.moveToNext());
        fout.close();
        c.close();
        dbDV.close();
        Toast.makeText(getBaseContext(), "DATOS EXPORTADOS ", Toast.LENGTH_LONG).show();

    } catch (Exception ex) {
        Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
    }
}
    public int id_desde(int sel) {
        String sql="";
        BasedbHelper usdbVS = new BasedbHelper(this);

        SQLiteDatabase dbDV = usdbVS.getReadableDatabase();

        if (sel == 1) {
            sql = "SELECT * FROM inspeccionvs WHERE fecha='" + FECHA_DESDE.getText() + "'";
        } else if (sel == 2) {
            sql = "SELECT * FROM VISITAPUESTO WHERE fecha='" + FECHA_DESDE.getText() + "'";
        } else if (sel == 3) {
            sql = "SELECT * FROM ENSAYOS WHERE fecha='" + FECHA_DESDE.getText() + "'";
        }
        Cursor c = dbDV.rawQuery(sql, null);
        c.moveToFirst();

        int id = c.getInt(0);
        c.close();
        return id;


    }
    public int id_hasta(int sel){
        String sql="";
        BasedbHelper usdbVS = new BasedbHelper(this);

        SQLiteDatabase dbDV = usdbVS.getReadableDatabase();
        if (sel == 1) {
           sql = "SELECT * FROM inspeccionvs WHERE fecha='" + FECHA_HASTA.getText() + "'";
        } else if (sel == 2) {
           sql = "SELECT * FROM VISITAPUESTO WHERE fecha='" + FECHA_HASTA.getText() + "'";
        } else if (sel == 3) {
           sql = "SELECT * FROM ENSAYOS WHERE fecha='" + FECHA_HASTA.getText() + "'";
        }
        Cursor c = dbDV.rawQuery( sql, null);
        c.moveToLast();

        int id=c.getInt(0);
        c.close();

        return id;
    }
    public String FECHA_FICHERO(String f) {
        String fecha_reunion=f;
        String F_REUNION_FORMAT="_"+fecha_reunion.substring(6,10)+"_"+fecha_reunion.substring(3,5)+"_"+fecha_reunion.substring(0,2)+"_";
        return F_REUNION_FORMAT;}
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);

        switch (parent.getId()) {
            case R.id.EX_FECHA_DESDE:
                //String sDESEMPE3=(String)EXPORTAR_FECHA_DESDE.getSelectedItem();
                Cursor c1=(Cursor)parent.getItemAtPosition(position);
                String fecha_desde=c1.getString(c1.getColumnIndex(TipdbAdapter.C_COLUMNA_FECHA));

                FECHA_DESDE.setText(fecha_desde);


                break;
            case R.id.EX_FECHA_HASTA:
                Cursor c2=(Cursor)parent.getItemAtPosition(position);
                String fecha_hasta=c2.getString(c2.getColumnIndex(TipdbAdapter.C_COLUMNA_FECHA));
                FECHA_HASTA.setText(fecha_hasta);

                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
