package com.example.julio.inspector;

import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ROPA extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner SPINER_POR_NOMBRE,SPINER_POR_DESCRIPCION,SPINER_POR_DESCRIPCION1,SPINER_POR_DESCRIPCION2,SPINER_POR_DESCRIPCION3,SPINER_POR_DESCRIPCION4,SPINER_POR_DESCRIPCION5;
    private Spinner CANTIDAD1,CANTIDAD2,CANTIDAD3,CANTIDAD4,CANTIDAD5;
    private Spinner TALLA1,TALLA2,TALLA3,TALLA4,TALLA5,SPIN_MOVIMIENTO;
    private String RNOMBRE,RCANTIDAD,RTALLLA,ID_PRODUCTO,RFECHA,ROTROS,RMOVIMIENTO,RDESCRIPCION,RDARCHIVO_FIRMA,ID_DISPOSITIVO;
    private String[] ID_PRODUCTO1= new String[5];
    private String[] RDESCRIPCION1= new String[5];
    private String[] RCANTIDAD1= new String[5];
    private String[] RTALLLA1= new String[5];
    private Button boton_guardar;
    private ImageButton boton_firmar;
    private Integer numero_registros=0;
    private EditText ROPA_OTROS,ROPA_TALLA,ROPA_CANTIDAD,ROPA_FECHA,ROPA_ID;
    private TextView Text_prenda2,Text_cantidad2,Text_prenda3,Text_cantidad3,Text_prenda4,Text_cantidad4,Text_prenda5,Text_cantidad5;
    private TipdbAdapter dbAdapterSituacion;
    private Cursor cursorListaSituacion;
    private String[] ID_ARRAY;
    private Integer firm=0;
    private final static String CARPETA_EXPORTACION = "/fotospuesto/EXPORTACIONES";
    private String MOVIMIENTO = "SALIDA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rop);
        REFERENCIA_CONTROLES();
        final String[] DES=getResources().getStringArray(R.array.DESCRIPCION1);
        final String[] ANORAK=getResources().getStringArray(R.array.ANORAK);
        final String[] MOV=getResources().getStringArray(R.array.movimiento);
        String[] NUMEROS={"0","1","2","3","4","5"};
        Resources res=getResources();
        ID_ARRAY=res.getStringArray(R.array.IDPRODUCTO);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, DES);
        ArrayAdapter<String> adaptador_numero = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, NUMEROS);
        ArrayAdapter<String> adaptador_movimiento = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, MOV);
        SPINER_POR_DESCRIPCION.setAdapter(adaptador);
        SPINER_POR_DESCRIPCION1.setAdapter(adaptador);
        SPINER_POR_DESCRIPCION2.setAdapter(adaptador);
        SPINER_POR_DESCRIPCION3.setAdapter(adaptador);
        SPINER_POR_DESCRIPCION4.setAdapter(adaptador);
        SPINER_POR_DESCRIPCION5.setAdapter(adaptador);
        SPIN_MOVIMIENTO.setAdapter(adaptador_movimiento);

        CANTIDAD1.setAdapter(adaptador_numero);
        CANTIDAD2.setAdapter(adaptador_numero);
        CANTIDAD3.setAdapter(adaptador_numero);
        CANTIDAD4.setAdapter(adaptador_numero);
        CANTIDAD5.setAdapter(adaptador_numero);

        SPINER_POR_DESCRIPCION1.setOnItemSelectedListener(this);
        SPINER_POR_DESCRIPCION2.setOnItemSelectedListener(this);
        SPINER_POR_DESCRIPCION3.setOnItemSelectedListener(this);
        SPINER_POR_DESCRIPCION4.setOnItemSelectedListener(this);
        SPINER_POR_DESCRIPCION5.setOnItemSelectedListener(this);
        SPIN_MOVIMIENTO.setOnItemSelectedListener(this);

        CANTIDAD1.setOnItemSelectedListener(this);
        CANTIDAD2.setOnItemSelectedListener(this);
        CANTIDAD3.setOnItemSelectedListener(this);
        CANTIDAD4.setOnItemSelectedListener(this);
        CANTIDAD5.setOnItemSelectedListener(this);

        TALLA1.setOnItemSelectedListener(this);
        TALLA2.setOnItemSelectedListener(this);
        TALLA3.setOnItemSelectedListener(this);
        TALLA4.setOnItemSelectedListener(this);
        TALLA5.setOnItemSelectedListener(this);

       dbAdapterSituacion = new TipdbAdapter(this) ;
       dbAdapterSituacion.abrir();
       cursorListaSituacion = dbAdapterSituacion.NOMBRE();
        SimpleCursorAdapter adapterSituacion = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorListaSituacion,(new String[] {"nombrecompleto"}), new int[] {android.R.id.text1});

        adapterSituacion.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        SPINER_POR_NOMBRE.setAdapter(adapterSituacion);
        SPINER_POR_NOMBRE.setOnItemSelectedListener(this);
        ID_DISPOSITIVO= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    private void REFERENCIA_CONTROLES(){
        SPINER_POR_NOMBRE=(Spinner)findViewById(R.id.ROPA_NOMBRE);
        SPINER_POR_DESCRIPCION=(Spinner)findViewById(R.id.ROPA_DESCRIPCION);
        SPINER_POR_DESCRIPCION1=(Spinner)findViewById(R.id.ROPA_DESCRIPCION_1);
        SPINER_POR_DESCRIPCION2=(Spinner)findViewById(R.id.ROPA_DESCRIPCION_2);
        SPINER_POR_DESCRIPCION3=(Spinner)findViewById(R.id.ROPA_DESCRIPCION_3);
       SPINER_POR_DESCRIPCION4=(Spinner)findViewById(R.id.ROPA_DESCRIPCION_4);
       SPINER_POR_DESCRIPCION5=(Spinner)findViewById(R.id.ROPA_DESCRIPCION_5);
        CANTIDAD1=(Spinner)findViewById(R.id.CANTIDAD1);
        CANTIDAD2=(Spinner)findViewById(R.id.CANTIDAD2);
        CANTIDAD3=(Spinner)findViewById(R.id.CANTIDAD3);
        CANTIDAD4=(Spinner)findViewById(R.id.CANTIDAD4);
        CANTIDAD5=(Spinner)findViewById(R.id.CANTIDAD5);
        SPIN_MOVIMIENTO=(Spinner)findViewById(R.id.spinner);

        TALLA1=(Spinner)findViewById(R.id.TALLAS1);
        TALLA2=(Spinner)findViewById(R.id.TALLAS2);
        TALLA3=(Spinner)findViewById(R.id.TALLAS3);
        TALLA4=(Spinner)findViewById(R.id.TALLAS4);
        TALLA5=(Spinner)findViewById(R.id.TALLAS5);

        ROPA_OTROS=(EditText)findViewById(R.id.ROPA_OTROS);
        ROPA_TALLA=(EditText)findViewById(R.id.ROPA_TALLA);
        ROPA_CANTIDAD=(EditText)findViewById(R.id.ROPA_CANTIDAD);
        ROPA_FECHA=(EditText)findViewById(R.id.ROPA_FECHA);
        ROPA_ID=(EditText)findViewById(R.id.ROPA_IDPRODUCTO);

        Text_prenda2=(TextView)findViewById(R.id.textView115);
        Text_cantidad2=(TextView)findViewById(R.id.textView114);
        Text_prenda3=(TextView)findViewById(R.id.textView117);
        Text_cantidad3=(TextView)findViewById(R.id.textView116);
        Text_prenda4=(TextView)findViewById(R.id.textView119);
        Text_cantidad4=(TextView)findViewById(R.id.textView118);
        Text_prenda5=(TextView)findViewById(R.id.textView121);
        Text_cantidad5=(TextView)findViewById(R.id.textView120);

        boton_guardar=(Button)findViewById(R.id.B_GUARDAR_ROPA);
        boton_firmar=(ImageButton)findViewById(R.id.B_FIRMAR_ROPA);
    }
    public String FECHA_FICHERO(String f) {
        String fecha_reunion=f;
        String F_REUNION_FORMAT="_"+fecha_reunion.substring(6,10)+"_"+fecha_reunion.substring(3,5)+"_"+fecha_reunion.substring(0,2)+"_";
        return F_REUNION_FORMAT;}
    private String getCode()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hhmmss");
        String date = dateFormat.format(new Date() );
        String photoCode =  date+"_";
        return photoCode;
    }

    public void GUARDAR_ROPA(View v){

        if (!RNOMBRE.equals("") && (numero_registros!=0)){ex_todoslosregistros();
            limpiar();}else{Toast.makeText(getBaseContext(), "CAMPOS NOMBRE Y/O DESCRIPCION VACIOS", Toast.LENGTH_SHORT).show();}

    }
    public void firmar(View view){
        Intent i = new Intent(this, firmas.class);

        i.putExtra("FECHA", RFECHA);
        i.putExtra("NOMBRECOMPLETO", RNOMBRE);
        i.putExtra("ACTIVITY", "1");
        startActivity(i);
        firm=1;
    }
    private void limpiar(){
        boton_guardar.setVisibility(View.INVISIBLE);
        boton_firmar.setVisibility(View.INVISIBLE);
        SPINER_POR_DESCRIPCION1.setSelection(0);
        SPINER_POR_NOMBRE.setSelection(0);
        SPIN_MOVIMIENTO.setSelection(0);
        String[] ADAPTER = {};
        ADAPTER = new String[]{""};
        ArrayAdapter<String> adaptador_TALLA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, ADAPTER);
        TALLA1.setAdapter(adaptador_TALLA);
        ROPA_OTROS.setText("");
        CANTIDAD1.setSelection(0);
        RNOMBRE="";
        firm=0;
    }
    private void ex_todoslosregistros(){
        String sqlPUESTOS;
        BasedbHelper usdbVS = new BasedbHelper(this);

        SQLiteDatabase dbDV = usdbVS.getReadableDatabase();


        String NOMBREFICHERO="ROPA_EXPORTACION"+FECHA_FICHERO(RFECHA)+getCode()+ID_DISPOSITIVO+".txt";





        try {
            //File ruta_sd = Environment.getExternalStorageDirectory().getPath();
            File DIR = new File(Environment.getExternalStorageDirectory().getPath()+CARPETA_EXPORTACION);
            if (!DIR.exists()){DIR.mkdir();}
            File file= new File(DIR,NOMBREFICHERO);

            OutputStreamWriter fout =
                    new OutputStreamWriter(new FileOutputStream(file));
            String linea=System.getProperty("line.separator");
            fout.write("ID" + ";" + "FECHA" + ";" + "DESCRIPCION" + ";" + "CANTIDAD" + ";" + "TALLA" + ";" + "MOVIMIENTO" + ";" + "OTROS" + ";" + "NOMBRE" + linea);
            int n=0;
            while(n<numero_registros){
                if (firm==1){RDARCHIVO_FIRMA=RNOMBRE+getfecha()+"_VESTUARIO.PNG";}else{RDARCHIVO_FIRMA="";}

            //Toast.makeText(getBaseContext(), "DATOS:"+ID_PRODUCTO1[n]+ ";" +RFECHA+ ";" +RDESCRIPCION1[n]+ ";" +RCANTIDAD1[n]+ ";" +RTALLLA1[n]+ ";" +RMOVIMIENTO+ ";" +ROTROS+ ";" +RNOMBRE, Toast.LENGTH_LONG).show();
                String d="";
                String id_arr="";

                switch (RDESCRIPCION1[n]){
                    case "ANORAK":
                        d=RDESCRIPCION1[n]+" TALLA " +RTALLLA1[n];
                        id_arr="50"+RTALLLA1[n];
                        break;
                    case "BOTAS":
                        d=RDESCRIPCION1[n]+" TALLA " +RTALLLA1[n];
                        id_arr="80"+RTALLLA1[n];
                        break;
                    case "BRAZALETE":
                        d=RDESCRIPCION1[n];
                        id_arr="10001";
                        break;
                    case "CAMISA VERANO":
                        d=RDESCRIPCION1[n]+" TALLA " +RTALLLA1[n];
                        id_arr="10"+RTALLLA1[n];
                        break;
                    case "CAMISA INVIERNO":
                        d=RDESCRIPCION1[n]+" TALLA " +RTALLLA1[n];
                        id_arr="20"+RTALLLA1[n];
                        break;
                    case "CHALECO":
                        d=RDESCRIPCION1[n];
                        id_arr="10002";
                        break;
                    case "CAZADORA":
                        d=RDESCRIPCION1[n]+" TALLA " +RTALLLA1[n];
                        id_arr="40"+RTALLLA1[n];
                        break;
                    case "CORBATA":
                        d=RDESCRIPCION1[n];
                        id_arr="10003";
                        break;
                    case "PANTALON INVIERNO":
                        d=RDESCRIPCION1[n]+" TALLA " +RTALLLA1[n];
                        id_arr="70"+RTALLLA1[n];
                        break;
                    case "PANTALON VERANO":
                        d=RDESCRIPCION1[n]+" TALLA " +RTALLLA1[n];
                        id_arr="30"+RTALLLA1[n];
                        break;
                    case "TRAJE FRIO":
                        d=RDESCRIPCION1[n]+" " +RTALLLA1[n];
                        id_arr="90"+RTALLLA1[n];
                        break;
                    case "ZAPATOS":
                        d=RDESCRIPCION1[n]+" TALLA " +RTALLLA1[n];
                        id_arr="60"+RTALLLA1[n];
                        break;
                    case "CINTURON":
                        d=RDESCRIPCION1[n];
                        id_arr="10004";
                        break;
                    case "POLO VERANO HOMBRE":
                        d=RDESCRIPCION1[n]+" TALLA " +RTALLLA1[n];
                        id_arr="21"+RTALLLA1[n];
                        break;
                    case "POLO VERANO MUJER":
                        d=RDESCRIPCION1[n]+" TALLA " +RTALLLA1[n];
                        id_arr="22"+RTALLLA1[n];
                        break;
                    case "DEFENSA":
                        d=RDESCRIPCION1[n];
                        id_arr="10005";
                        break;

                }
                String registro=id_arr+ ";" +RFECHA+ ";" + d + ";" +RCANTIDAD1[n]+ ";" +RTALLLA1[n]+ ";" +RMOVIMIENTO+ ";" +ROTROS+ ";" +RNOMBRE + ";" +RDARCHIVO_FIRMA;
            //String registro=RFECHA+ ";" +RMOVIMIENTO+ ";" +ROTROS+ ";" +RNRTALLLA1[n]OMBRE;
                fout.write(registro + linea);
               n=n+1;
            }

            fout.close();

            dbDV.close();
            Toast.makeText(getBaseContext(), "DATOS EXPORTADOS ", Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
        }
    }
    public String FECHAconformato() {
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        return dateString;
    }
    private void TALLAJE(int p,int position) {
        final String[] ANORAK = getResources().getStringArray(R.array.ANORAK);
        final String[] CAMISA = getResources().getStringArray(R.array.CAMISA);
        final String[] PANTALON = getResources().getStringArray(R.array.PANTALON);
        final String[] BOTAS = getResources().getStringArray(R.array.BOTAS);
        final String[] TRAJEFRIO = getResources().getStringArray(R.array.TRAJEFRIO);
        final String[] CAZADORA = getResources().getStringArray(R.array.CAZADORA);
        final String[] ZAPATOS = getResources().getStringArray(R.array.ZAPATOS);
        String[] ADAPTER = {};
        switch (p) {
            case 0:
                ADAPTER = new String[]{""};
                break;
            case 1:
                ADAPTER = ANORAK;
                break;
            case 2:
                ADAPTER = CAMISA;
                break;
            case 3:
                ADAPTER = PANTALON;
                break;
            case 4:
                ADAPTER = BOTAS;
                break;
            case 5:
                ADAPTER = TRAJEFRIO;
                break;
            case 6:
                ADAPTER = CAZADORA;
                break;
            case 7:
                ADAPTER = ZAPATOS;
                break;
            case 8:
                ADAPTER = CAMISA;
                break;
        }
        ArrayAdapter<String> adaptador_TALLA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, ADAPTER);
        switch (position) {
            case 1:
                TALLA1.setAdapter(adaptador_TALLA);
                break;
            case 2:
                TALLA2.setAdapter(adaptador_TALLA);
                break;
            case 3:
                TALLA3.setAdapter(adaptador_TALLA);
                break;
            case 4:
                TALLA4.setAdapter(adaptador_TALLA);
                break;
            case 5:
                TALLA5.setAdapter(adaptador_TALLA);
                break;
            default:
               // TALLAJE(0,p);
                break;
        }
    }
    private void ROPA_DESCRIPCION(String descripcion,int p){
        switch (descripcion) {
            case "ANORAK":
                TALLAJE(1,p);
                break;
            case "CAMISA VERANO":
                TALLAJE(2,p);
                break;
            case "CAMISA INVIERNO":
                TALLAJE(2,p);
                break;
            case "PANTALON INVIERNO":
                TALLAJE(3,p);
                break;
            case "PANTALON VERANO":
                TALLAJE(3,p);
                break;
            case "BOTAS":
                TALLAJE(4,p);
                break;
            case "TRAJE FRIO":
                TALLAJE(5,p);
                break;
            case "CAZADORA":
                TALLAJE(6,p);
                break;
            case "ZAPATOS":
                TALLAJE(7,p);
                break;
            case "POLO VERANO HOMBRE":
                TALLAJE(8,p);
                break;
            case "POLO VERANO MUJER":
                TALLAJE(8,p);
                break;
            default:
                TALLAJE(0,p);
                break;
        }
    }
    private String getfecha() {
        Long date = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        String sdf = dateFormat.format(date);
        String photoCode =  "_"+sdf ;
        return photoCode;
    }

    private void SELECION_SPINER(int p,int position){
        String d;

        if (RDESCRIPCION1[p].length()==0){
            ROPA_TALLA.setText("");
            visible_spiner(p);

        }else{
            visible_spiner2(p);

            if (RDESCRIPCION1[p].equals("CORBATA") || RDESCRIPCION1[p].equals("CHALECO")|| RDESCRIPCION1[p].equals("BRAZALETE")|| RDESCRIPCION1[p].equals("CINTURON")|| RDESCRIPCION1[p].equals("DEFENSA")){d=RDESCRIPCION1[p];RTALLLA1[p]="";ROPA_CANTIDAD.setText("1");}else{

                ROPA_TALLA.setText(RTALLLA1[p]);

                RCANTIDAD1[p]="1";
                ROPA_CANTIDAD.setText("1");
               // Toast.makeText(getBaseContext(), "talla "+RTALLLA1[p], Toast.LENGTH_SHORT).show();
                }}
        ROPA_FECHA.setText(FECHAconformato());
        RMOVIMIENTO=MOVIMIENTO;
        RFECHA=FECHAconformato();

        //ID_PRODUCTO1[p]=id_arr;
        ROPA_ID.setText(ID_PRODUCTO1[p]);
        RCANTIDAD=ROPA_CANTIDAD.getText().toString();
        ROTROS=ROPA_OTROS.getText().toString();
        ROPA_OTROS.setText("ANOTADO CON TABLET");
        RTALLLA=ROPA_TALLA.getText().toString();
    }
    private void visible_spiner(int p){
        switch (p){
            case 0:
                Text_prenda2.setVisibility(View.INVISIBLE);
                Text_cantidad2.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION2.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION2.setSelection(0);
                CANTIDAD2.setVisibility(View.INVISIBLE);
                CANTIDAD2.setSelection(0);
                TALLA2.setVisibility(View.INVISIBLE);

                Text_prenda3.setVisibility(View.INVISIBLE);
                Text_cantidad3.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION3.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION3.setSelection(0);
                CANTIDAD3.setVisibility(View.INVISIBLE);
                CANTIDAD3.setSelection(0);
                TALLA3.setVisibility(View.INVISIBLE);

                Text_prenda4.setVisibility(View.INVISIBLE);
                Text_cantidad4.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION4.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION4.setSelection(0);
                CANTIDAD4.setVisibility(View.INVISIBLE);
                CANTIDAD4.setSelection(0);
                TALLA4.setVisibility(View.INVISIBLE);

                Text_prenda5.setVisibility(View.INVISIBLE);
                Text_cantidad5.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION5.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION5.setSelection(0);
                CANTIDAD5.setVisibility(View.INVISIBLE);
                CANTIDAD5.setSelection(0);
                TALLA5.setVisibility(View.INVISIBLE);
                break;
            case 1:

                SPINER_POR_DESCRIPCION3.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION3.setSelection(0);
                CANTIDAD3.setVisibility(View.INVISIBLE);
                CANTIDAD3.setSelection(0);
                TALLA3.setVisibility(View.INVISIBLE);

                SPINER_POR_DESCRIPCION4.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION4.setSelection(0);
                CANTIDAD4.setVisibility(View.INVISIBLE);
                CANTIDAD4.setSelection(0);
                TALLA4.setVisibility(View.INVISIBLE);

                SPINER_POR_DESCRIPCION5.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION5.setSelection(0);
                CANTIDAD5.setVisibility(View.INVISIBLE);
                CANTIDAD5.setSelection(0);
                TALLA5.setVisibility(View.INVISIBLE);
                break;
            case 2:

                SPINER_POR_DESCRIPCION4.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION4.setSelection(0);
                CANTIDAD4.setVisibility(View.INVISIBLE);
                CANTIDAD4.setSelection(0);
                TALLA4.setVisibility(View.INVISIBLE);

                SPINER_POR_DESCRIPCION5.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION5.setSelection(0);
                CANTIDAD5.setVisibility(View.INVISIBLE);
                CANTIDAD5.setSelection(0);
                TALLA5.setVisibility(View.INVISIBLE);
                break;
            case 3:

                SPINER_POR_DESCRIPCION5.setVisibility(View.INVISIBLE);
                SPINER_POR_DESCRIPCION5.setSelection(0);
                CANTIDAD5.setVisibility(View.INVISIBLE);
                CANTIDAD5.setSelection(0);
                TALLA5.setVisibility(View.INVISIBLE);
                break;
            case 4:
                TALLA5.setVisibility(View.INVISIBLE);
                break;
        }
    }
    private void visible_spiner2(int p){
        switch (p){
            case 0:
                Text_prenda2.setVisibility(View.VISIBLE);
                Text_cantidad2.setVisibility(View.VISIBLE);
                SPINER_POR_DESCRIPCION2.setVisibility(View.VISIBLE);
                CANTIDAD2.setVisibility(View.VISIBLE);
                CANTIDAD1.setSelection(1);
                TALLA1.setVisibility(View.VISIBLE);
                break;
            case 1:
                Text_prenda3.setVisibility(View.VISIBLE);
                Text_cantidad3.setVisibility(View.VISIBLE);
                SPINER_POR_DESCRIPCION3.setVisibility(View.VISIBLE);
                CANTIDAD3.setVisibility(View.VISIBLE);
                CANTIDAD2.setSelection(1);
                TALLA2.setVisibility(View.VISIBLE);
                break;
            case 2:
                Text_prenda4.setVisibility(View.VISIBLE);
                Text_cantidad4.setVisibility(View.VISIBLE);
                SPINER_POR_DESCRIPCION4.setVisibility(View.VISIBLE);
                CANTIDAD4.setVisibility(View.VISIBLE);
                CANTIDAD3.setSelection(1);
                TALLA3.setVisibility(View.VISIBLE);
                break;
            case 3:
                Text_prenda5.setVisibility(View.VISIBLE);
                Text_cantidad5.setVisibility(View.VISIBLE);
                SPINER_POR_DESCRIPCION5.setVisibility(View.VISIBLE);
                CANTIDAD5.setVisibility(View.VISIBLE);
                CANTIDAD4.setSelection(1);
                TALLA4.setVisibility(View.VISIBLE);
                TALLA5.setVisibility(View.VISIBLE);
                break;
            case 4:
                CANTIDAD5.setSelection(1);

                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ro, menu);
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
            case R.id.spinner:
                RMOVIMIENTO=parent.getItemAtPosition(position).toString();

                //Toast.makeText(getBaseContext(), "mov: "+RMOVIMIENTO , Toast.LENGTH_SHORT).show();

                break;

            case R.id.ROPA_NOMBRE:
                Cursor c1 = (Cursor) parent.getItemAtPosition(position);

                //if (position==0){visible_spiner(0);limpiar();}else{
                RNOMBRE = c1.getString(c1.getColumnIndex(TipdbAdapter.C_COLUMNA_NOMBRE));
                Toast.makeText(getBaseContext(), "NOMBRE "+RNOMBRE , Toast.LENGTH_SHORT).show();
                ROPA_FECHA.setText(FECHAconformato());
                RMOVIMIENTO=MOVIMIENTO;
                RFECHA=FECHAconformato();
                //}


                break;
            case R.id.ROPA_DESCRIPCION_1:
                RDESCRIPCION1[0]=parent.getItemAtPosition(position).toString();
                if (RDESCRIPCION1[0].equals("")){ numero_registros=0;limpiar();}else
                {
                    numero_registros=1;
                    ROPA_DESCRIPCION(RDESCRIPCION1[0],1);
                    boton_guardar.setVisibility(View.VISIBLE);
                    boton_firmar.setVisibility(View.VISIBLE);

                }


                SELECION_SPINER(0,position);
                break;
            case R.id.ROPA_DESCRIPCION_2:
                RDESCRIPCION1[1]=parent.getItemAtPosition(position).toString();
                if (RDESCRIPCION1[1].equals("")){if (numero_registros==0){ numero_registros=0;}else{
                    numero_registros=1;
                    ROPA_DESCRIPCION(RDESCRIPCION1[1],2);
                }}else{numero_registros=2;
                       ROPA_DESCRIPCION(RDESCRIPCION1[1],2);
                }
                SELECION_SPINER(1,position);
                break;
            case R.id.ROPA_DESCRIPCION_3:
                RDESCRIPCION1[2]=parent.getItemAtPosition(position).toString();
                if (RDESCRIPCION1[2].equals("")){if (numero_registros==0){ numero_registros=0;}else{ numero_registros=2;}}else{
                    numero_registros=3;
                    ROPA_DESCRIPCION(RDESCRIPCION1[2],3);
                }
                SELECION_SPINER(2,position);
                break;
            case R.id.ROPA_DESCRIPCION_4:
                RDESCRIPCION1[3]=parent.getItemAtPosition(position).toString();
                if (RDESCRIPCION1[3].equals("")){if (numero_registros==0){ numero_registros=0;}else{ numero_registros=3;}}else{
                    numero_registros=4;
                    ROPA_DESCRIPCION(RDESCRIPCION1[3],4);
                }
                SELECION_SPINER(3,position);
                break;
            case R.id.ROPA_DESCRIPCION_5:
                RDESCRIPCION1[4]=parent.getItemAtPosition(position).toString();
                if (RDESCRIPCION1[4].equals("")){if (numero_registros==0){ numero_registros=0;}else{ numero_registros=4;}}else{
                    numero_registros=5;
                    ROPA_DESCRIPCION(RDESCRIPCION1[4],5);
                }
                SELECION_SPINER(4,position);
                break;
            case R.id.CANTIDAD1:
                RCANTIDAD1[0]=parent.getItemAtPosition(position).toString();
               // if (position!=0){ Toast.makeText(getBaseContext(), "cantidad: "+RCANTIDAD1[0], Toast.LENGTH_SHORT).show();}

                break;
            case R.id.CANTIDAD2:
                RCANTIDAD1[1]=parent.getItemAtPosition(position).toString();
                //if (position!=0){ Toast.makeText(getBaseContext(), "cantidad: "+RCANTIDAD1[1], Toast.LENGTH_SHORT).show();}
                break;
            case R.id.CANTIDAD3:
                RCANTIDAD1[2]=parent.getItemAtPosition(position).toString();
                //if (position!=0){ Toast.makeText(getBaseContext(), "cantidad: "+RCANTIDAD1[2], Toast.LENGTH_SHORT).show();}
                break;
            case R.id.CANTIDAD4:
                RCANTIDAD1[3]=parent.getItemAtPosition(position).toString();
                //if (position!=0){ Toast.makeText(getBaseContext(), "cantidad: "+RCANTIDAD1[3], Toast.LENGTH_SHORT).show();}
                break;
            case R.id.CANTIDAD5:
                RCANTIDAD1[4]=parent.getItemAtPosition(position).toString();
                //if (position!=0){ Toast.makeText(getBaseContext(), "cantidad: "+RCANTIDAD1[4], Toast.LENGTH_SHORT).show();}
                break;
            case R.id.TALLAS1:
                RTALLLA1[0]=parent.getItemAtPosition(position).toString();
                //if (position!=0){ Toast.makeText(getBaseContext(), "cantidad: "+RCANTIDAD1[4], Toast.LENGTH_SHORT).show();}
                break;
            case R.id.TALLAS2:
                RTALLLA1[1]=parent.getItemAtPosition(position).toString();
                //if (position!=0){ Toast.makeText(getBaseContext(), "cantidad: "+RCANTIDAD1[4], Toast.LENGTH_SHORT).show();}
                break;
            case R.id.TALLAS3:
                RTALLLA1[2]=parent.getItemAtPosition(position).toString();
                //if (position!=0){ Toast.makeText(getBaseContext(), "cantidad: "+RCANTIDAD1[4], Toast.LENGTH_SHORT).show();}
                break;
            case R.id.TALLAS4:
                RTALLLA1[3]=parent.getItemAtPosition(position).toString();
                //if (position!=0){ Toast.makeText(getBaseContext(), "cantidad: "+RCANTIDAD1[4], Toast.LENGTH_SHORT).show();}
                break;
            case R.id.TALLAS5:
                RTALLLA1[4]=parent.getItemAtPosition(position).toString();
                //if (position!=0){ Toast.makeText(getBaseContext(), "cantidad: "+RCANTIDAD1[4], Toast.LENGTH_SHORT).show();}
                break;
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
