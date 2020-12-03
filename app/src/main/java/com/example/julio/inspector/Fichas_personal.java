package com.example.julio.inspector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fichas_personal extends AppCompatActivity implements OnItemSelectedListener{
private EditText F_NOMBRE,F_APELLIDOS1,F_APELLIDOS2,F_DNI,F_TIP,F_TELEFONOS,F_ID_SCANER,F_PASSWORD,F_CARNET_CONDUCIR,F_CADUCA_PERMISO;
    private EditText F_CADUCA_C1,F_CADUCA_C2,F_CADUCA_REFRESCO_C2,F_ALTA_EULEN,F_BAJA_EULEN,F_ALTA_SERVICIO,F_BAJA_SERVICIO,F_ANTIGUEDAD,F_PISO_PORTAL,F_NUMERO,F_DOMICILIO,F_CODIGO_POSTAL,F_MUNICIPIO;
    private EditText F_TALLA_CAMISA,F_TALLA_ABRIGO,F_TALLA_PANTALON,F_TALLA_CAZADORA,F_TALLA_ZAPATOS;
    private EditText F_C1,F_C2,F_reciclaje_c2;
    private ImageView FOTO_FICHA;
    private CheckBox id_suspendida;
    private int countVS,ed,REG_GUAR;
    private String stip,snombre,sapellido1,sapellido2,sdni,aenanoconforme,ID_VS,SID,SPASS,STEL;
    private TextView vspanel,ETIQUIETA_spin_BUSCAR,ETIQUIETA_spin_TIP;
    private ImageButton NEXT,PREV,BFIN,BINICIO;
    private Spinner SPINER_POR_NOMBRE,SPINER_POR_TIP;
    private Cursor cursorListaSituacion,CURSOR_TIP;
    private TipdbAdapter dbAdapterSituacion,dbADAPTER_TIP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichas_personal);

        // CONTROLES EDITEXT
        F_NOMBRE=(EditText)findViewById(R.id.FICHA_NOMBRE);//1
        F_APELLIDOS1=(EditText)findViewById(R.id.FICHA_APELLIDO1);//2
        F_APELLIDOS2=(EditText)findViewById(R.id.FICHA_APELLIDO2);//3
        F_DNI=(EditText)findViewById(R.id.FICHA_DNI);//4
        F_TIP=(EditText)findViewById(R.id.FICHA_TIP);//5
        F_TELEFONOS=(EditText)findViewById(R.id.FICHA_TELEFONO);//6
        F_ID_SCANER=(EditText)findViewById(R.id.FICHA_ID_SCANER);//7
        F_PASSWORD=(EditText)findViewById(R.id.FICHA_PASSWORD);//8
        F_ALTA_EULEN=(EditText)findViewById(R.id.editText87);//10
        F_ALTA_SERVICIO=(EditText)findViewById(R.id.editText88);//11
        F_C1=(EditText)findViewById(R.id.FICHA_c1);//12
        F_C2=(EditText)findViewById(R.id.FICHA_c2);//13
        F_reciclaje_c2=(EditText)findViewById(R.id.FICHA_RECICLAJE_c2);//14

        F_CADUCA_C1=(EditText)findViewById(R.id.editText6);//15
        F_CADUCA_C2=(EditText)findViewById(R.id.editText7);//16
        F_CADUCA_REFRESCO_C2=(EditText)findViewById(R.id.FICHA_CADUCA_RECICLAJE_C2);//17
        F_DOMICILIO=(EditText)findViewById(R.id.FICHA_DOMICILIO);//18
        F_NUMERO=(EditText)findViewById(R.id.FICHA_NUMERO);//19
        F_PISO_PORTAL=(EditText)findViewById(R.id.FICHA_PISO_PORTAL);//20
        F_CODIGO_POSTAL=(EditText)findViewById(R.id.FICHA_CODIGO_POSTAL);//21
        F_MUNICIPIO=(EditText)findViewById(R.id.FICHA_MUNICIPIO);//22
        F_TALLA_CAMISA=(EditText)findViewById(R.id.FICHA_TALLA_CAMISA);//23
        F_TALLA_PANTALON=(EditText)findViewById(R.id.FICHA_TALLA_PANTALON);//24
        F_TALLA_CAZADORA=(EditText)findViewById(R.id.FICHA_TALLA_CAZADORA);//25
        F_TALLA_ABRIGO=(EditText)findViewById(R.id.FICHA_TALLA_ANORAK);//26
        F_TALLA_ZAPATOS=(EditText)findViewById(R.id.FICHA_TALLA_ZAPATOS);//27
        // CONTROLES CHECKBOX
        id_suspendida=(CheckBox)findViewById(R.id.FICHA_ID_EN_SUSPENSO);
        //CONTROLES TEXVIEW
        vspanel=(TextView)findViewById(R.id.FICHA_PANEL);
        // CONTROLES NAVEGACION
        PREV=(ImageButton)findViewById(R.id.FICHA_PREVIO);
        NEXT=(ImageButton)findViewById(R.id.FICHA_SIGUIENTE);
        BFIN=(ImageButton)findViewById(R.id.FICHA_ULTIMO);
        BINICIO=(ImageButton)findViewById(R.id.FICHA_PRIMERO);
        //CONTROLES SPINER
        SPINER_POR_NOMBRE=(Spinner)findViewById(R.id.FICHA_SPIN_NOMBRE);
        SPINER_POR_TIP=(Spinner)findViewById(R.id.FICHA_SPIN_TIP);
        //IMAGEN
       FOTO_FICHA=(ImageView) findViewById(R.id.imageView19);

        dbAdapterSituacion = new TipdbAdapter(this) ;
        dbAdapterSituacion.abrir();
        cursorListaSituacion = dbAdapterSituacion.apellidosNOMBRE();
        CURSOR_TIP=dbAdapterSituacion.getTIP();

        SPINER_POR_NOMBRE.setOnItemSelectedListener(this);

        SPINER_POR_TIP.setOnItemSelectedListener(this);
        totalvs(1);
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

            setext(cursor);

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
            setext(cursor);
        }
        if (n==4){
            countVS+=1;
            cursor.moveToPosition(countVS);//al siguiente registro

            if (countVS+1==(cursor.getCount())){

                NEXT.setClickable(false);
                NEXT.setEnabled(false);}
            vspanel.setText("REGISTRO: " + String.valueOf(countVS+1)+"/" + String.valueOf(cursor.getCount()));
            ID_VS=cursor.getString(0);
            setext(cursor);
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
            vspanel.setText("REGISTRO: " + String.valueOf(countVS+1)+ "/" + String.valueOf(cursor.getCount()));
            ID_VS=cursor.getString(0);
            setext(cursor);
            NEXT.setClickable(true);
            NEXT.setEnabled(true);
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
    public void VER_REG(View V){
        totalvs(2);
        PREV.setVisibility(View.VISIBLE);
        NEXT.setVisibility(View.VISIBLE);
        BFIN.setVisibility(View.VISIBLE);
        BINICIO.setVisibility(View.VISIBLE);
    }
    public void buscar(View v){
        SimpleCursorAdapter adapterSituacion = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorListaSituacion,(new String[] {"nombrecompleto"}), new int[] {android.R.id.text1});

        adapterSituacion.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        SPINER_POR_NOMBRE.setAdapter(adapterSituacion);
        SimpleCursorAdapter adapterSituacion2 = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,CURSOR_TIP,(new String[] {TipdbAdapter.C_COLUMNA_TIP}), new int[] {android.R.id.text1});

        adapterSituacion2.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        SPINER_POR_TIP.setAdapter(adapterSituacion2);
    }
    public String FECHAconformato() {
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        return dateString;
    }
    public Date convertirfecha(String fecha){
        Date fecha_convertida=null;
        SimpleDateFormat formatateador= new SimpleDateFormat("dd/MM/yyyy");
        try {

           fecha_convertida=formatateador.parse(fecha);

        }catch (ParseException e){}
        return fecha_convertida;

    }
    private void setext(Cursor cursor){
        ID_VS=cursor.getString(0);
        F_NOMBRE.setText(cursor.getString(1));
        F_APELLIDOS1.setText(cursor.getString(2));
        F_APELLIDOS2.setText(cursor.getString(3));
        F_TIP.setText(cursor.getString(4));
        F_DNI.setText(cursor.getString(5));
        F_ID_SCANER.setText(cursor.getString(6));
        F_PASSWORD.setText(cursor.getString(7));
        F_TELEFONOS.setText(cursor.getString(8));

       if (cursor.getInt(9)==1){
           id_suspendida.setChecked(true);
           Toast.makeText(getBaseContext(), "ATENCION: ID SUSPENDIDA,SI ESTA EN UN PUESTO CON RX,SUSTITUIR POR VS CON ID ACTIVA." , Toast.LENGTH_LONG).show();
       }else {id_suspendida.setChecked(false);}
        F_ALTA_EULEN.setText(cursor.getString(10));
        F_ALTA_SERVICIO.setText(cursor.getString(11));

        F_C1.setText(cursor.getString(12));
        F_C2.setText(cursor.getString(13));
        F_reciclaje_c2.setText(cursor.getString(14));
        F_CADUCA_C1.setText(cursor.getString(15));
        F_CADUCA_C2.setText(cursor.getString(16));
        F_CADUCA_REFRESCO_C2.setText(cursor.getString(17));
        F_DOMICILIO.setText(cursor.getString(18));
        F_NUMERO.setText(cursor.getString(19));
        F_PISO_PORTAL.setText(cursor.getString(20));
        F_CODIGO_POSTAL.setText(cursor.getString(21));
        F_MUNICIPIO.setText(cursor.getString(22));
        F_TALLA_CAMISA.setText(cursor.getString(23));
        F_TALLA_PANTALON.setText(cursor.getString(24));
        F_TALLA_CAZADORA.setText(cursor.getString(25));
        F_TALLA_ABRIGO.setText(cursor.getString(26));
        F_TALLA_ZAPATOS.setText(cursor.getString(27));

        Bitmap bMap2 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + TipdbAdapter.R_RUTA_FOTOS + cursor.getString(28));

        FOTO_FICHA.setImageBitmap(bMap2);

        vspanel.setText("REGISTRO: " + ID_VS + "/" + String.valueOf(cursor.getCount()));
    }
    private void llenar_con_ID (String _id){
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM VS WHERE _id='"+_id+"'", null);
        cursor.moveToFirst();
        ID_VS=cursor.getString(0);
        F_NOMBRE.setText(cursor.getString(1));
        F_APELLIDOS1.setText(cursor.getString(2));
        F_APELLIDOS2.setText(cursor.getString(3));
        F_TIP.setText(cursor.getString(4));
        F_DNI.setText(cursor.getString(5));
        F_ID_SCANER.setText(cursor.getString(6));
        F_PASSWORD.setText(cursor.getString(7));
        F_TELEFONOS.setText(cursor.getString(8));
        if (cursor.getInt(9)==1){
            id_suspendida.setChecked(true);
            Toast.makeText(getBaseContext(), "ATENCION: ID SUSPENDIDA,SI ESTA EN UN PUESTO CON RX,SUSTITUIR POR VS CON ID ACTIVA." , Toast.LENGTH_LONG).show();
        }else {id_suspendida.setChecked(false);}
        F_ALTA_EULEN.setText(cursor.getString(10));
        F_ALTA_SERVICIO.setText(cursor.getString(11));
       
        F_C1.setText(cursor.getString(12));
        F_C2.setText(cursor.getString(13));
        F_reciclaje_c2.setText(cursor.getString(14));
        F_CADUCA_C1.setText(cursor.getString(15));
        F_CADUCA_C2.setText(cursor.getString(16));
        F_CADUCA_REFRESCO_C2.setText(cursor.getString(17));
        F_DOMICILIO.setText(cursor.getString(18));
        F_NUMERO.setText(cursor.getString(19));
        F_PISO_PORTAL.setText(cursor.getString(20));
        F_CODIGO_POSTAL.setText(cursor.getString(21));
        F_MUNICIPIO.setText(cursor.getString(22));
        F_TALLA_CAMISA.setText(cursor.getString(23));
        F_TALLA_PANTALON.setText(cursor.getString(24));
        F_TALLA_CAZADORA.setText(cursor.getString(25));
        F_TALLA_ABRIGO.setText(cursor.getString(26));
        F_TALLA_ZAPATOS.setText(cursor.getString(27));

        Bitmap bMap2 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + TipdbAdapter.R_RUTA_FOTOS + cursor.getString(28));

        FOTO_FICHA.setImageBitmap(bMap2);

        vspanel.setText("REGISTRO: " + ID_VS);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fichas_personal, menu);
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
            case R.id.FICHA_SPIN_NOMBRE:
                Cursor c1=(Cursor)parent.getItemAtPosition(position);
                String _id=c1.getString(c1.getColumnIndex(TipdbAdapter.C_COLUMNA_ID));
                llenar_con_ID(_id);
                //SPINER_POR_TIP.setSelection(0);
                break;
            case R.id.FICHA_SPIN_TIP:
                Cursor c2=(Cursor)parent.getItemAtPosition(position);
                String _ID=c2.getString(c2.getColumnIndex(TipdbAdapter.C_COLUMNA_ID));
                llenar_con_ID(_ID);
                //SPINER_POR_NOMBRE.setSelection(0);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
