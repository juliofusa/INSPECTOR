package com.example.julio.inspector;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Configuracion extends AppCompatActivity implements OnItemSelectedListener{

    private EditText TIP,NOMBRE,APELLIDO1,APELLIDO2,DNI,NOCONFORME,INSNOMBRE,INSDNI,PUESTO,VSID,VSPASS,VSTEL,NOMBRE_INSPECTOR;
    private String stip,snombre,sapellido1,sapellido2,sdni,aenanoconforme,ID_VS,SID,SPASS,STEL,IDSUSPENDIDO,ANTIGUEDAD,ANTIGUEDAD_AEROPUERTO,C1,C2,REFRESCOC2,CADUCAC1,CADUCAC2,CADUCA_REFRESCOC2,DOMICILIO,NUMERO,PISO,CODIGO_POSTAL,MUNICIPIO,TALLA_CAMISA,TALLA_PANTALON,TALLA_CAZADORA,TALLA_ANORAK,TALLA_ZAPATOS;
    private TextView vspanel,ETIQUIETA_spin_BUSCAR,ETIQUIETA_spin_TIP;
    private AutoCompleteTextView inspectores;
    private int countVS,ed,REG_GUAR;
    private ImageButton NEXT,PREV,BFIN,BINICIO;
    private Button BT_borrar,BT_Actualizar,BT_Guardar,BT_Importar,BT_Buscar,ANADIR_INSPECTOR;
    private Spinner SPINER_POR_NOMBRE,SPINER_POR_TIP;
    private TipdbAdapter dbAdapterSituacion,dbADAPTER_TIP;
    private Cursor cursorListaSituacion,CURSOR_TIP;
    private List<String> list= new ArrayList<String>();
    private final static String CARPETA_IMPORTACION = "/Download/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);


        INICIALIZAR();



        dbAdapterSituacion = new TipdbAdapter(this) ;
        dbAdapterSituacion.abrir();


        CURSOR_TIP=dbAdapterSituacion.getTIP();
        cursorListaSituacion = dbAdapterSituacion.getnombreCOMPLETO();

        int longitudARRAY=cursorListaSituacion.getCount();
        String[] nombre= new String[longitudARRAY];
        String [] apellido1= new String[longitudARRAY];
        String [] apellido2= new String[longitudARRAY];
        String [] nombrecompleto= new String[longitudARRAY];
        //for (int i=0;i<longitudARRAY;i++){
            //nombre[i]=cursorListaSituacion.getString(3);
           // apellido1[i]=cursorListaSituacion.getString(4);
            //apellido2[i]=cursorListaSituacion.getString(5);
           // nombrecompleto[i]=nombre[i]+" "+apellido1[i]+" "+apellido2[i];

        //}



        SPINER_POR_NOMBRE.setOnItemSelectedListener(this);

        SPINER_POR_TIP.setOnItemSelectedListener(this);
        SPINER_POR_NOMBRE.setVisibility(View.INVISIBLE);
        REG_GUAR=1;
        limpiar();
    }
    // DECLACION DE CONTROLES Y/O INICIALIZACION DE VARIABLES
    private void INICIALIZAR(){
        ed=1;
        TIP=(EditText)findViewById(R.id.VS_TIP);
        NOMBRE=(EditText)findViewById(R.id.VS_NOMBRE);
        APELLIDO1=(EditText)findViewById(R.id.VS_APELLIDOS1);
        APELLIDO2=(EditText)findViewById(R.id.VS_APELLIDOS2);
        DNI=(EditText)findViewById(R.id.VS_DNI);
        NOCONFORME=(EditText)findViewById(R.id.AENA_NOCONFORME);
        INSNOMBRE=(EditText)findViewById(R.id.INS_NOMBRE);
        INSDNI=(EditText)findViewById(R.id.INS_DNI);
        VSID=(EditText)findViewById(R.id.VS_ID);
        VSPASS=(EditText)findViewById(R.id.VS_PASSWORD);
        VSTEL=(EditText)findViewById(R.id.VS_TELEFONO);

        PUESTO=(EditText)findViewById(R.id.PUESTO_NOMBRE);
        NOMBRE_INSPECTOR=(EditText)findViewById(R.id.autocompletar);

        vspanel=(TextView)findViewById(R.id.VS_PANEL);
        ETIQUIETA_spin_BUSCAR=(TextView)findViewById(R.id.con_text_nombre_completo);
        ETIQUIETA_spin_TIP=(TextView)findViewById(R.id.con_text_tip);
        PREV=(ImageButton)findViewById(R.id.VS_BTATRAS);

        NEXT=(ImageButton)findViewById(R.id.VS_BTAVANCE);
        BFIN=(ImageButton)findViewById(R.id.VS_BTULTIMO);
        BINICIO=(ImageButton)findViewById(R.id.VS_BTPRIMERO);

        BT_borrar=(Button)findViewById(R.id.VS_BTBORRAR);
        BT_Actualizar=(Button)findViewById(R.id.VS_BTUPDATE);
        BT_Guardar=(Button)findViewById(R.id.VS_BTGUARDAR);
        BT_Importar=(Button)findViewById(R.id.VS_BTIMPORTAR);
        BT_Buscar=(Button)findViewById(R.id.VS_BUSCAR);
        ANADIR_INSPECTOR=(Button)findViewById(R.id.anadir_inspector);

        inspectores=(AutoCompleteTextView) findViewById(R.id.autocompletar);
        dbAdapterSituacion = new TipdbAdapter(this) ;
        dbAdapterSituacion.abrir();
        Cursor c=dbAdapterSituacion.getnombreCOMPLETOI();
        if (c.getCount()>1){
        final String[] s= SelectAllData();

        inspectores.setThreshold(2);

        //final String[] ins=getResources().getStringArray(R.array.INSPECTORESadm);

        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,s);

        inspectores.setAdapter(adapter);
        c.close();}

        SPINER_POR_TIP=(Spinner)findViewById(R.id.spinner_TIP);
        SPINER_POR_NOMBRE=(Spinner)findViewById(R.id.spinner_NOMBRE);
    }
    private void limpiar(){
        TIP.setText("");
        NOMBRE.setText("");
        APELLIDO1.setText("");
        APELLIDO2.setText("");
        DNI.setText("");
        NOCONFORME.setText("");
        INSNOMBRE.setText("");
        INSDNI.setText("");
        PUESTO.setText("");
        VSID.setText("");
        VSPASS.setText("");
        VSTEL.setText("");
        totalvs(1);
    }
    public void BT_VS_BUSCAR(View v){
        limpiar();
        SimpleCursorAdapter adapterSituacion = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorListaSituacion,(new String[] {"nombrecompleto"}), new int[] {android.R.id.text1},0);

        adapterSituacion.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        SPINER_POR_NOMBRE.setAdapter(adapterSituacion);
        SPINER_POR_NOMBRE.setVisibility(View.VISIBLE);
        SPINER_POR_TIP.setVisibility(View.VISIBLE);
        SimpleCursorAdapter adapterSituacion2 = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,CURSOR_TIP,(new String[] {TipdbAdapter.C_COLUMNA_TIP}), new int[] {android.R.id.text1});

        adapterSituacion.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        SPINER_POR_TIP.setAdapter(adapterSituacion2);
        ETIQUIETA_spin_TIP.setVisibility(View.VISIBLE);
        ETIQUIETA_spin_BUSCAR.setVisibility(View.VISIBLE);
        BT_borrar.setVisibility(View.INVISIBLE);
        BT_Actualizar.setVisibility(View.VISIBLE);
        PREV.setVisibility(View.INVISIBLE);
        NEXT.setVisibility(View.INVISIBLE);
        BFIN.setVisibility(View.INVISIBLE);
        BINICIO.setVisibility(View.INVISIBLE);
    }
    public void vsguardar(View v){
        if (REG_GUAR==1){
            BT_Importar.setVisibility(View.VISIBLE);
            BT_Guardar.setText("GUARDAR");
            REG_GUAR=2;
        }else if (REG_GUAR==2){
        getVS();
            insertarVS(snombre, sapellido1, sapellido2, stip, sdni, SID, SPASS, STEL, IDSUSPENDIDO,ANTIGUEDAD,ANTIGUEDAD_AEROPUERTO,C1,C2,"","","","","","","","","","","","","","","");
        Toast.makeText(getBaseContext(), "DATOS DE VS GUARDADOS... ", Toast.LENGTH_SHORT).show();
            BT_Importar.setVisibility(View.INVISIBLE);
            BT_Guardar.setText("VS NUEVO/S");
            limpiar();
            REG_GUAR=1;
        }
    }
    public void AENAguardar(View v){
        aenanoconforme=NOCONFORME.getText().toString();
        insertarAENA(aenanoconforme);
        limpiar();
    }
    public void ACTUALIZAVS(View v){
        actializaVS();

    }
    public void BorrarVS(View v){
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            db.delete("VS", "_id=" + ID_VS, null);
            Toast.makeText(getBaseContext(), "REGISTRO BORRADO", Toast.LENGTH_SHORT).show();
            totalvs(2);
        }
    }
    public void EDITARVS(View v){
        if (ed==1){
        PREV.setVisibility(View.VISIBLE);
        NEXT.setVisibility(View.VISIBLE);
        BFIN.setVisibility(View.VISIBLE);
        BINICIO.setVisibility(View.VISIBLE);
        BT_borrar.setVisibility(View.VISIBLE);
        BT_Actualizar.setVisibility(View.VISIBLE);
        BT_Buscar.setVisibility(View.VISIBLE);
        BT_Guardar.setVisibility(View.INVISIBLE);
        BT_Importar.setVisibility(View.INVISIBLE);

        totalvs(2);
        ed=2;}else if (ed==2){
            PREV.setVisibility(View.INVISIBLE);
            NEXT.setVisibility(View.INVISIBLE);
            BFIN.setVisibility(View.INVISIBLE);
            BINICIO.setVisibility(View.INVISIBLE);
            BT_borrar.setVisibility(View.INVISIBLE);
            BT_Actualizar.setVisibility(View.INVISIBLE);
            BT_Guardar.setVisibility(View.VISIBLE);
            BT_Guardar.setText("VS NUEVO/S");
            BT_Importar.setVisibility(View.INVISIBLE);
            BT_Buscar.setVisibility(View.INVISIBLE);
            SPINER_POR_NOMBRE.setVisibility(View.INVISIBLE);
            ETIQUIETA_spin_BUSCAR.setVisibility(View.INVISIBLE);
            ETIQUIETA_spin_TIP.setVisibility(View.INVISIBLE);
            SPINER_POR_TIP.setVisibility(View.INVISIBLE);
            limpiar();
            ed=1;
        }
    }
    public void reginiciovs(View v) {

        totalvs(2);
    }
    public void regFINvs(View v) {
        totalvs(3);
    }
    public void regNEXTvs(View v) {
        totalvs(4);
    }
    public void regPREVvs(View v) {
        totalvs(5);
    }

    public void importarpuestos(View v){

        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            db.execSQL("DELETE FROM PUESTOS");
            db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+"PUESTOS"+"'");
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("puesto", "");
            db.insert("PUESTOS", null, nuevoRegistro);
            db.close();
        }
        String PATH_ORDEN_VS= Environment.getExternalStorageDirectory().getAbsolutePath() +TipdbAdapter.R_RUTA_ORDENVS;
        String ruta=PATH_ORDEN_VS;
        File file = new File(ruta);
        File f[]=file.listFiles();
        int l=f.length;
        for (int i=0;i<l;i++){
            String nombre_procedimiento=f[i].getName();
            String Nom=nombre_procedimiento.replace(".pdf","");
            Insertar_puesto(Nom);
             }
        Toast.makeText(getBaseContext(), "Añadidos "+l+" Puestos... "+l, Toast.LENGTH_SHORT).show()  ;
    }
    // IMPORTACION DE FICHAS DE VS
    public void importarvs(View v){

        File DIR = new File(Environment.getExternalStorageDirectory().getPath()+CARPETA_IMPORTACION);

        File f = new File(DIR, TipdbAdapter.A_FICHAS_VS);



        if (f.exists()) {

        String texto;
        int N=0;
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            db.execSQL("DELETE FROM VS");
            db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+"VS"+"'");
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("nombre", "");
            nuevoRegistro.put("apellido1","");
            nuevoRegistro.put("apellido2", "");
            nuevoRegistro.put("tip", "");
            nuevoRegistro.put("dni", "");
            nuevoRegistro.put("id_scaner", "");
            nuevoRegistro.put("password_scaner", "");
            nuevoRegistro.put("telefono", "");

            //Insertamos el registro en la base de datos
            db.insert("VS", null, nuevoRegistro);
            db.close();
        }

        try
        {
            Toast.makeText(getBaseContext(), "ACTUALIZANDO TODOS LOS VS... ", Toast.LENGTH_SHORT).show();

            BufferedReader VSFICHA =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(f)));
            while((texto = VSFICHA.readLine())!=null){
            String CODIFICACION= new String(texto.getBytes("UTF-8"),"UTF-8");
            String [] registro=CODIFICACION.split(";");
                if (N==0){N += 1;}else{
                insertarVS(registro[0].toUpperCase(), registro[1].toUpperCase(), registro[2].toUpperCase(), registro[4], registro[3], registro[5], registro[6], registro[7], registro[8], registro[9], registro[10], registro[11], registro[12], registro[13], registro[14], registro[15], registro[16], registro[17], registro[18], registro[19], registro[20], registro[21], registro[22], registro[23], registro[24], registro[25], registro[26], registro[27]);
                N += 1;}
            }
            VSFICHA.close();
            f.delete();
            totalvs(1);

            Toast.makeText(getBaseContext(), "ARCHIVO DE VS IMPORTADOS... "+Integer.toString(N-1)+" Registros", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");

            Toast.makeText(getBaseContext(), "Error al leer fichero", Toast.LENGTH_SHORT).show();
        }
        }else{

            Toast.makeText(getBaseContext(), "NO EXISTE EL ARCHIVO", Toast.LENGTH_SHORT).show();}
    }
    private void Insertar_puesto(String Puesto){
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("puesto", Puesto);
            db.insert("PUESTOS", null, nuevoRegistro);
            db.close();
        }
    }
    public void importaraux(View v){

        File DIR = new File(Environment.getExternalStorageDirectory().getPath()+CARPETA_IMPORTACION);

        File f = new File(DIR, TipdbAdapter.A_FICHAS_AUX);
       // Toast.makeText(getBaseContext(), "RUTA... "+f.toString(), Toast.LENGTH_SHORT).show();
        if (f.exists()) {

            String texto;
            int N=0;
            BasedbHelper usdbh = new BasedbHelper(this);

            SQLiteDatabase db = usdbh.getWritableDatabase();

            if (db != null) {
                db.execSQL("DELETE FROM AUX");
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "AUX" + "'");
                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("nombre", "");
                nuevoRegistro.put("apellido1", "");
                nuevoRegistro.put("apellido2", "");
                nuevoRegistro.put("SINUSO1", "LATAM");
                
                //Insertamos el registro en la base de datos
                db.insert("AUX", null, nuevoRegistro);
                db.close();
            }

            try
            {
                Toast.makeText(getBaseContext(), "ACTUALIZANDO TODOS LOS AUX... ", Toast.LENGTH_SHORT).show();

                BufferedReader VSFICHA =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(f)));
                while((texto = VSFICHA.readLine())!=null){
                    String CODIFICACION= new String(texto.getBytes("UTF-8"),"UTF-8");
                   // Toast.makeText(getBaseContext(), CODIFICACION, Toast.LENGTH_SHORT).show();
                    String [] registro=CODIFICACION.split(";");
                    if (N==0){N += 1;}else{
                        insertarAUX(registro[0].toUpperCase(), registro[1].toUpperCase(), registro[2].toUpperCase(), registro[3], registro[4], registro[5], registro[6], registro[7], registro[8], registro[9], registro[10]);
                        N += 1;}
                }
                VSFICHA.close();
                f.delete();
                totalvs(1);
                Toast.makeText(getBaseContext(), "ARCHIVO DE AUX IMPORTADOS... "+Integer.toString(N-1)+" Registros", Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex)
            {
                Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
                Toast.makeText(getBaseContext(), "Error al leer fichero", Toast.LENGTH_SHORT).show();
            }
        }else{Toast.makeText(getBaseContext(), "NO EXISTE EL ARCHIVO", Toast.LENGTH_SHORT).show();}
    }
    public void actializaVS() {
        getVS();
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("nombre", snombre);
            nuevoRegistro.put("apellido1", sapellido1);
            nuevoRegistro.put("apellido2", sapellido2);
            nuevoRegistro.put("tip", stip);
            nuevoRegistro.put("dni", sdni);
            nuevoRegistro.put("id_scaner", SID);
            nuevoRegistro.put("password_scaner", SPASS);
            nuevoRegistro.put("telefono", STEL);

            //actualizamos el registro en la base de datos
            db.update("VS", nuevoRegistro, "_id=" + ID_VS, null);
            Toast.makeText(getBaseContext(), "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show();
        }
    }
    public void INSPECCIONES_PORDEFECTO(View v){correccion();}
    public void correccion(){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if(db != null){
            db.execSQL("DELETE FROM AENA");
            db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+"AENA"+"'");
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
            db.close();
            Toast.makeText(getBaseContext(), "INSPECCIONES MANUALES POR DEFECTO...", Toast.LENGTH_SHORT).show();
        }
    }
    public void getVS(){
        stip=TIP.getText().toString();
        snombre=NOMBRE.getText().toString();
        sapellido1=APELLIDO1.getText().toString();
        sapellido2=APELLIDO2.getText().toString();
        sdni=DNI.getText().toString();

        SID=VSID.getText().toString();
        SPASS=VSPASS.getText().toString();
        STEL=VSTEL.getText().toString();
    }
    public void insertarVS( String nombre,String apellido1, String apellido2,String tip, String dni,String ID_ESCANER,String PASS_ESCANER,String tel,String ID_ENSUS,String ANTIGUEDAD,String ANTIGUEDAD_AEROPUERTO,String C1,String C2,String reciclajec2,String CADUCA_C1,String CADUCA_C2,String CADUCA_REFRESCO_C2,String DOMI_CILIO,String NUM_ERO,String Piso_portal,String codigo_postal,String municipio,String camisa,String pantalon,String cazadora,String anorak,String zapatos,String fecha_actualizacion) {
        //String pantalon,String cazadora,String anorak,String zapatos,
        Log.i(this.getClass().toString(), "insertar_registro");

        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Log.i(this.getClass().toString(), "abriendo base");
        if(db != null){
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("nombre", nombre);//1
            nuevoRegistro.put("apellido1",apellido1);//2
            nuevoRegistro.put("apellido2", apellido2);//3
            nuevoRegistro.put("tip", tip);//4
            nuevoRegistro.put("dni", dni);//5
            nuevoRegistro.put("id_scaner", ID_ESCANER);//6
            nuevoRegistro.put("password_scaner", PASS_ESCANER);//7
            nuevoRegistro.put("telefono", tel);//8
            nuevoRegistro.put("IDSUSPENDIDO", ID_ENSUS);//9
            nuevoRegistro.put("ANTIGUEDAD", ANTIGUEDAD);//10
            nuevoRegistro.put("ANTIGUEDAD_AEROPUERTO", ANTIGUEDAD_AEROPUERTO);//11
            nuevoRegistro.put("C1", C1);//12
            nuevoRegistro.put("C2", C2);//13
            nuevoRegistro.put("REFRESCOC2", reciclajec2);//14
            nuevoRegistro.put("CADUCAC1", CADUCA_C1);//15
            nuevoRegistro.put("CADUCAC2", CADUCA_C2);//16
            nuevoRegistro.put("CADUCA_REFRESCOC2", CADUCA_REFRESCO_C2);//17
            nuevoRegistro.put("DOMICILIO", DOMI_CILIO);//18
            nuevoRegistro.put("NUMERO", NUM_ERO);//19
            nuevoRegistro.put("PISO", Piso_portal);//20
            nuevoRegistro.put("CODIGO_POSTAL", codigo_postal);//21
            nuevoRegistro.put("MUNICIPIO", municipio);//22
            nuevoRegistro.put("TALLA_CAMISA", camisa);//23
            nuevoRegistro.put("TALLA_PANTALON", pantalon);//24
            nuevoRegistro.put("TALLA_CAZADORA", cazadora);//25
            nuevoRegistro.put("TALLA_ANORAK", anorak);//26
            nuevoRegistro.put("TALLA_ZAPATOS", zapatos);//27

            nuevoRegistro.put("SINUSO1", fecha_actualizacion);//28
            //Insertamos el registro en la base de datos
            db.insert("VS", null, nuevoRegistro);
            Log.i(this.getClass().toString(), "INSERTADO NUEVO REGISTRO");
            // Cerramos la base de datos
            db.close();
            //Toast.makeText(getBaseContext(), "DATOS GUARDADOS ", Toast.LENGTH_LONG).show();

        }
    }
    public void insertarAUX( String nombre,String apellido1, String apellido2, String dni,String tel,String DOMI_CILIO,String NUM_ERO,String Piso_portal,String codigo_postal,String municipio,String Puesto_habitual) {
        //String pantalon,String cazadora,String anorak,String zapatos,
        Log.i(this.getClass().toString(), "insertar_registro");

        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Log.i(this.getClass().toString(), "abriendo base");
        if(db != null){
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("nombre", nombre);
            nuevoRegistro.put("apellido1",apellido1);
            nuevoRegistro.put("apellido2", apellido2);
            nuevoRegistro.put("dni", dni);
            nuevoRegistro.put("telefono", tel);

            nuevoRegistro.put("DOMICILIO", DOMI_CILIO);
            nuevoRegistro.put("NUMERO", NUM_ERO);
            nuevoRegistro.put("PISO", Piso_portal);
            nuevoRegistro.put("CODIGO_POSTAL", codigo_postal);
            nuevoRegistro.put("MUNICIPIO", municipio);
            nuevoRegistro.put("SINUSO1", Puesto_habitual);
            //Insertamos el registro en la base de datos
            db.insert("AUX", null, nuevoRegistro);
            Log.i(this.getClass().toString(), "INSERTADO NUEVO REGISTRO");
            // Cerramos la base de datos
            db.close();
            //Toast.makeText(getBaseContext(), "DATOS GUARDADOS ", Toast.LENGTH_LONG).show();

        }
    }

    public class PROGRESO_DIALOGO extends AsyncTask<Void,Void,Void>{
     ProgressDialog progress;
       MainActivity act;
        public PROGRESO_DIALOGO (ProgressDialog progress,MainActivity act){
            this.progress=progress;
            this.act=act;
        }
        public void  onPreExecute(){
            progress.show();

        }
        public void onPostExecute(){
            progress.dismiss();

        }
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }
    public void insertarAENA(String aena){
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Log.i(this.getClass().toString(), "abriendo base");
        if(db != null){
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("noconformidad", aena);


            //Insertamos el registro en la base de datos
            db.insert("AENA", null, nuevoRegistro);
            Log.i(this.getClass().toString(), "INSERTADO NUEVO REGISTRO");
            // Cerramos la base de datos
            db.close();
            Toast.makeText(getBaseContext(), "DATOS GUARDADOS ", Toast.LENGTH_LONG).show();

        }
    }
    public void totalvs(int n){
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM VS ", null);
        if (n==1){
            countVS=0;
        vspanel.setText("REGISTRO: 0/" + String.valueOf(cursor.getCount()));}
        if (n==2){
            cursor.moveToFirst();//al primer registro

            countVS=cursor.getPosition();
            vspanel.setText("REGISTRO: " + (countVS + 1) + "/" + String.valueOf(cursor.getCount()));
            ID_VS=cursor.getString(0);
            TIP.setText(cursor.getString(4));
            DNI.setText(cursor.getString(5));
            NOMBRE.setText(cursor.getString(1));
            APELLIDO1.setText(cursor.getString(2));
            APELLIDO2.setText(cursor.getString(3));
            VSID.setText(cursor.getString(6));
            VSPASS.setText(cursor.getString(7));
            VSTEL.setText(cursor.getString(8));

            PREV.setClickable(false);
            PREV.setEnabled(false);
            NEXT.setClickable(true);
            NEXT.setEnabled(true);
        }
        if (n==3){
            cursor.moveToLast();//al ultimo registro
            NEXT.setClickable(false);
            NEXT.setEnabled(false);
            PREV.setClickable(true);
            PREV.setEnabled(true);
            countVS=cursor.getPosition();
            vspanel.setText("REGISTRO: " + String.valueOf(countVS + 1) + "/" + String.valueOf(cursor.getCount()));
            ID_VS=cursor.getString(0);
            TIP.setText(cursor.getString(4));
            DNI.setText(cursor.getString(5));
            NOMBRE.setText(cursor.getString(1));
            APELLIDO1.setText(cursor.getString(2));
            APELLIDO2.setText(cursor.getString(3));
            VSID.setText(cursor.getString(6));
            VSPASS.setText(cursor.getString(7));
            VSTEL.setText(cursor.getString(8));
        }
        if (n==4){
            countVS+=1;
            cursor.moveToPosition(countVS);//al siguiente registro

            if (countVS+1==(cursor.getCount())){

              NEXT.setClickable(false);
              NEXT.setEnabled(false);}
            vspanel.setText("REGISTRO: " + String.valueOf(countVS+1)+"/" + String.valueOf(cursor.getCount()));
            ID_VS=cursor.getString(0);
            TIP.setText(cursor.getString(4));
            DNI.setText(cursor.getString(5));
            NOMBRE.setText(cursor.getString(1));
            APELLIDO1.setText(cursor.getString(2));
            APELLIDO2.setText(cursor.getString(3));
            VSID.setText(cursor.getString(6));
            VSPASS.setText(cursor.getString(7));
            VSTEL.setText(cursor.getString(8));
            PREV.setClickable(true);
            PREV.setEnabled(true);
        }
        if (n==5){
            countVS-=1;

            cursor.moveToPosition(countVS);;//al anterior registro
            if (countVS==0){
                NEXT.setClickable(true);
                NEXT.setEnabled(true);
                PREV.setClickable(false);
                PREV.setEnabled(false);}
            vspanel.setText("REGISTRO: " + String.valueOf(countVS+1)+"/" + String.valueOf(cursor.getCount()));
            ID_VS=cursor.getString(0);
            TIP.setText(cursor.getString(4));
            DNI.setText(cursor.getString(5));
            NOMBRE.setText(cursor.getString(1));
            APELLIDO1.setText(cursor.getString(2));
            APELLIDO2.setText(cursor.getString(3));
            VSID.setText(cursor.getString(6));
            VSPASS.setText(cursor.getString(7));
            VSTEL.setText(cursor.getString(8));
            NEXT.setClickable(true);
            NEXT.setEnabled(true);
        }
    }
    // botones

    // IMPORTACION DE ORDENES DE PUESTO FIRMADAS
    public void importar_ORDENES(View v){


        final ProgressDialog Loading_ordenes=ProgressDialog.show(Configuracion.this,"Importando Datos","Espere por favor",false,true);


        File DIR = new File(Environment.getExternalStorageDirectory().getPath()+CARPETA_IMPORTACION);

        File f = new File(DIR, TipdbAdapter.A_ORDENES_FIRMADAS);

         if (f.exists()) {
             String texto;
             int N = 0;
             BasedbHelper usdbh = new BasedbHelper(this);

             SQLiteDatabase db = usdbh.getWritableDatabase();

             if (db != null) {
                 db.execSQL("DELETE FROM ORDENESFIRMADAS");
                 db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "PERSONA" + "'");
                 ContentValues nuevoRegistro = new ContentValues();
                 nuevoRegistro.put("fecha", "");
                 nuevoRegistro.put("PUESTO", "");
                 nuevoRegistro.put("PERSONA", "");
                 nuevoRegistro.put("TIP", "");


                 //Insertamos el registro en la base de datos
                 db.insert("ORDENESFIRMADAS", null, nuevoRegistro);
                 db.close();
             }
             //Toast.makeText(getBaseContext(), "BORRANDO VS... ", Toast.LENGTH_SHORT).show();
             try {
                 Toast.makeText(getBaseContext(), "ACTUALIZANDO ORDENES PUESTO FIRMADAS... ", Toast.LENGTH_SHORT).show();

                 //File ruta_sd = Environment.getExternalStorageDirectory();

                 BufferedReader VSFICHA =
                         new BufferedReader(
                                 new InputStreamReader(
                                         new FileInputStream(f)));
                 while ((texto = VSFICHA.readLine()) != null) {
                     String CODIFICACION = new String(texto.getBytes("UTF-8"), "UTF-8");
                     String[] registro = CODIFICACION.split(";");
                     if (N == 0) {
                         N += 1;
                     } else {

                         String PUESTO_REPLACE = registro[2].replace("*", "");
                         insertar_ORDENES_PUESTO(registro[0].toUpperCase(), registro[1].toUpperCase(), PUESTO_REPLACE.toUpperCase().trim(), registro[3]);
                         N += 1;
                     }
                 }

                 VSFICHA.close();

                 f.delete();

                 totalvs(1);

                 Toast.makeText(getBaseContext(), "ARCHIVO ORDENES PUESTO IMPORTADOS... " + Integer.toString(N - 1) + " Registros", Toast.LENGTH_SHORT).show();

                 Loading_ordenes.dismiss();
             } catch (Exception ex) {
                 Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");

                 Toast.makeText(getBaseContext(), "Error al leer fichero", Toast.LENGTH_SHORT).show();

                 Loading_ordenes.dismiss();

             }
         }else{

             Toast.makeText(getBaseContext(), "NO EXISTE EL ARCHIVO", Toast.LENGTH_SHORT).show();

             Loading_ordenes.dismiss();

         }
    }
    public void insertar_ORDENES_PUESTO( String FECHA,String PUESTO, String PERSONA,String TIP) {


        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Log.i(this.getClass().toString(), "abriendo base");
        if(db != null){
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("fecha", FECHA);
            nuevoRegistro.put("PUESTO", PUESTO.trim());
            nuevoRegistro.put("PERSONA", PERSONA.trim());
            nuevoRegistro.put("TIP", TIP);
            //Insertamos el registro en la base de datos
            db.insert("ORDENESFIRMADAS", null, nuevoRegistro);
            Log.i(this.getClass().toString(), "INSERTADO NUEVO REGISTRO");
            // Cerramos la base de datos
            db.close();
            //Toast.makeText(getBaseContext(), "DATOS GUARDADOS ", Toast.LENGTH_LONG).show();

        }
    }
    // IMPORTACION DE TEST
    public void importar_TEST(View v){

        final ProgressDialog Loading=ProgressDialog.show(Configuracion.this,"Importando Datos","Espere por favor",false,false);


        File DIR = new File(Environment.getExternalStorageDirectory().getPath()+CARPETA_IMPORTACION);

        File f = new File(DIR, TipdbAdapter.A_TEST_FORMACION);



        if (f.exists()) {

        //Loading.show();

        String texto;
        int N=0;
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            db.execSQL("DELETE FROM PREGUNTASTEST");
            db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "NUMERO" + "'");

            db.close();
        }

        try
        {
            Toast.makeText(getBaseContext(), "IMPORTANDO TEST... ", Toast.LENGTH_SHORT).show();

            BufferedReader VSFICHA =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(f)));
            while((texto = VSFICHA.readLine())!=null){
                String CODIFICACION= new String(texto.getBytes("UTF-8"),"UTF-8");

                String [] registro=CODIFICACION.split(";");
                if (N==0){N += 1;}else{

                    insertar_TEST(registro[0], registro[1], registro[2], registro[3], registro[4], registro[5], registro[6], registro[7], registro[8], registro[9]);
                    //Toast.makeText(getBaseContext(), "reg:"+N+" - "+registro[1]+"/"+registro[8], Toast.LENGTH_SHORT).show();
                    N += 1;}
            }
            VSFICHA.close();
            f.delete();

            Toast.makeText(getBaseContext(), "ARCHIVO TEST IMPORTADOS... "+Integer.toString(N-1)+" Registros", Toast.LENGTH_SHORT).show();

            Loading.dismiss();

        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
            Loading.dismiss();
            Toast.makeText(getBaseContext(), "Error al leer fichero", Toast.LENGTH_SHORT).show();
        }
       }else{

            Toast.makeText(getBaseContext(), "NO EXISTE EL ARCHIVO", Toast.LENGTH_SHORT).show();

            Loading.dismiss();

        }
    }
    public void insertar_TEST( String NUMERO,String PREGUNTA, String RESPUESTA1,String RESPUESTA2,String RESPUESTA3,String RESPUESTA4,String CORRECTA,String FIJA,String TEST,String SEXO) {


        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        Log.i(this.getClass().toString(), "abriendo base");
        if(db != null){
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("NUMERO", NUMERO);
            nuevoRegistro.put("PREGUNTA", PREGUNTA);
            nuevoRegistro.put("RESPUESTA1", RESPUESTA1);
            nuevoRegistro.put("RESPUESTA2", RESPUESTA2);
            nuevoRegistro.put("RESPUESTA3", RESPUESTA3);
            nuevoRegistro.put("RESPUESTA4", RESPUESTA4);
            nuevoRegistro.put("CORRECTA", CORRECTA);
            nuevoRegistro.put("FIJA", FIJA);
            nuevoRegistro.put("TEST", TEST);
            nuevoRegistro.put("SEXO", SEXO);

            //Insertamos el registro en la base de datos
            db.insert("PREGUNTASTEST", null, nuevoRegistro);
            Log.i(this.getClass().toString(), "INSERTADO NUEVO REGISTRO");
            // Cerramos la base de datos
            db.close();
            //Toast.makeText(getBaseContext(), "DATOS GUARDADOS ", Toast.LENGTH_LONG).show();

        }
    }
    // IMPORTACION DE INSPECTORES
    public void importar_INSPECTORES(View v){

        final ProgressDialog Loading=ProgressDialog.show(this,"Importando Datos","Espere por favor",false,false);

        Loading.show();

        inspectores.setVisibility(View.INVISIBLE);

        ANADIR_INSPECTOR.setVisibility(View.INVISIBLE);

        File DIR = new File(Environment.getExternalStorageDirectory().getPath()+CARPETA_IMPORTACION);

        File f = new File(DIR, TipdbAdapter.A_INSPECTORES);

        if (f.exists()) {


        String texto;

        int N=0;

        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {
            db.execSQL("DELETE FROM INSPECTORES");
            db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "nombre" + "'");
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("nombre", "");
            db.insert("INSPECTORES", null, nuevoRegistro);

        }

        try
        {
            Toast.makeText(getBaseContext(), "IMPORTANDO INSPECTORES... ", Toast.LENGTH_SHORT).show();

            BufferedReader VSFICHA =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(f)));
            while((texto = VSFICHA.readLine())!=null){
                String CODIFICACION= new String(texto.getBytes("UTF-8"),"UTF-8");
                String [] registro=CODIFICACION.split(";");
                if (N==0){N += 1;}else{

                    ContentValues nuevoRegistro = new ContentValues();

                    nuevoRegistro.put("nombre", registro[0]);

                    db.insert("INSPECTORES", null, nuevoRegistro);

                      N += 1;

                }
                Loading.dismiss();
            }
            VSFICHA.close();

            db.close();

            f.delete();

            Toast.makeText(getBaseContext(), "ARCHIVO INSPECTORES IMPORTADOS... "+Integer.toString(N-1)+" Registros", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
            Loading.dismiss();
            Toast.makeText(getBaseContext(), "Error al leer fichero", Toast.LENGTH_SHORT).show();
        }
        }else{
            Loading.dismiss();
            Toast.makeText(getBaseContext(), "NO EXISTE EL ARCHIVO", Toast.LENGTH_SHORT).show();}
    }
    public void ver_inspectores(View v){

        inspectores.setVisibility(View.VISIBLE);

        ANADIR_INSPECTOR.setVisibility(View.VISIBLE);
    }
    public void add_inspector(View v){

        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        if (db != null) {

        ContentValues nuevoRegistro = new ContentValues();

        nuevoRegistro.put("nombre", inspectores.getText().toString().toUpperCase());

        db.insert("INSPECTORES", null, nuevoRegistro);

        db.close();

        Toast.makeText(getBaseContext(), "INSERTADO NUEVO INSPECTOR... ", Toast.LENGTH_SHORT).show();

            inspectores.setVisibility(View.INVISIBLE);

            ANADIR_INSPECTOR.setVisibility(View.INVISIBLE);}
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
    public void importar_FORMADORES(View v){


        File DIR = new File(Environment.getExternalStorageDirectory().getPath()+CARPETA_IMPORTACION);

        File f = new File(DIR, TipdbAdapter.A_FORMADORES);

        if (f.exists()) {


            String texto;

            int N=0;

            BasedbHelper usdbh = new BasedbHelper(this);

            SQLiteDatabase db = usdbh.getWritableDatabase();

            if (db != null) {
                db.execSQL("DELETE FROM FORMADORES");
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "nombre" + "'");
                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("nombre", "");
                db.insert("FORMADORES", null, nuevoRegistro);

            }

            try
            {
                Toast.makeText(getBaseContext(), "IMPORTANDO FORMADORES... ", Toast.LENGTH_SHORT).show();

                BufferedReader VSFICHA =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(f)));
                while((texto = VSFICHA.readLine())!=null){
                    String CODIFICACION= new String(texto.getBytes("UTF-8"),"UTF-8");
                    String [] registro=CODIFICACION.split(";");
                    if (N==0){N += 1;}else{

                        ContentValues nuevoRegistro = new ContentValues();

                        nuevoRegistro.put("nombre", registro[0]);

                        db.insert("FORMADORES", null, nuevoRegistro);

                        N += 1;

                    }

                }
                VSFICHA.close();

                db.close();

                f.delete();

                Toast.makeText(getBaseContext(), "ARCHIVO FORMADORES IMPORTADOS... "+Integer.toString(N-1)+" Registros", Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex)
            {
                Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");

                Toast.makeText(getBaseContext(), "Error al leer fichero", Toast.LENGTH_SHORT).show();
            }
        }else{

            Toast.makeText(getBaseContext(), "NO EXISTE EL ARCHIVO", Toast.LENGTH_SHORT).show();}
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        switch (parent.getId()) {
            case R.id.spinner_NOMBRE:
                Cursor c1=(Cursor)parent.getItemAtPosition(position);
                String VS_id=c1.getString(c1.getColumnIndex(TipdbAdapter.C_COLUMNA_ID));
                //Toast.makeText(getBaseContext(), "ID: "+VS_id, Toast.LENGTH_SHORT).show();
                BasedbHelper usdbh = new BasedbHelper(this);

                SQLiteDatabase db = usdbh.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT _id,nombre,apellido1,apellido2,tip,dni,id_scaner,password_scaner,telefono,(nombre||' '|| apellido1 ||' '|| apellido2) AS nombrecompleto FROM VS ORDER BY nombrecompleto ", null);

                cursor.moveToPosition(position);
                ID_VS=cursor.getString(0);
                TIP.setText(cursor.getString(4));
                DNI.setText(cursor.getString(5));
                NOMBRE.setText(cursor.getString(1));
                APELLIDO1.setText(cursor.getString(2));
                APELLIDO2.setText(cursor.getString(3));
                VSID.setText(cursor.getString(6));
                VSPASS.setText(cursor.getString(7));
                VSTEL.setText(cursor.getString(8));
                vspanel.setText("REGISTRO: " + ID_VS + "/" + String.valueOf(cursor.getCount()));


                cursor.close();
                db.close();

                break;
            case R.id.spinner_TIP:
                Cursor c2=(Cursor)parent.getItemAtPosition(position);
                String VS_id2=c2.getString(c2.getColumnIndex(TipdbAdapter.C_COLUMNA_ID));
                //Toast.makeText(getBaseContext(), "ID: "+VS_id, Toast.LENGTH_SHORT).show();
                BasedbHelper usdbh2 = new BasedbHelper(this);

                SQLiteDatabase db2 = usdbh2.getReadableDatabase();
                Cursor cursor1 = db2.rawQuery("SELECT * FROM VS", null);

                cursor1.moveToPosition(position);
                ID_VS=cursor1.getString(0);
                TIP.setText(cursor1.getString(4));
                DNI.setText(cursor1.getString(5));
                NOMBRE.setText(cursor1.getString(1));
                APELLIDO1.setText(cursor1.getString(2));
                APELLIDO2.setText(cursor1.getString(3));
                VSID.setText(cursor1.getString(6));
                VSPASS.setText(cursor1.getString(7));
                VSTEL.setText(cursor1.getString(8));
                vspanel.setText("REGISTRO: " + ID_VS + "/" + String.valueOf(cursor1.getCount()));


                cursor1.close();
                db2.close();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
