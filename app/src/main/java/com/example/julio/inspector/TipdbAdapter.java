package com.example.julio.inspector;

/**
 * Created by JULIO on 17/07/2015.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TipdbAdapter {

    //
    // Definimos constante con el nombre de la tabla
    //
    public static final String C_TABLAvs = "VS" ;
    public static final String C_TABLAinspector = "INSPECTORES" ;
    public static final String C_TABLAENA = "AENA" ;
    public static final String C_TABLAINSPECCIONVS = "inspeccionvs" ;
    public static final String C_TABLAINSPECCIONPUESTO = "VISITAPUESTO" ;
    public static final String C_TABLAENSAYO = "ENSAYOS" ;
    //
    // Definimos constantes con el nombre de las columnas de la tabla
    //
    public static final String C_COLUMNA_ID  = "_id";
    public static final String C_COLUMNA_TIP = "tip";
    public static final String C_COLUMNA_DNI = "dni";
    public static final String C_COLUMNA_NOMBREVS = "nombre";
    public static final String C_COLUMNA_APELLIDO1 = "tip";
    public static final String C_COLUMNA_APELLIDO2 = "tip";
    public static final String C_COLUMNA_NOMBRE = "nombrecompleto";
    public static final String C_COLUMNA_AENA = "noconformidad";
    public static final String C_COLUMNA_FECHA = "fecha";
    public static final String C_COLUMNA_PUESTO = "puesto";
    public static final String C_COLUMNA_FILTRO = "FILTRO";
    //RUTAS
    public static final String R_RUTA_PDF="/fotospuesto/PDF/";
    public static final String R_RUTA_EXPORTACIONES="/fotospuesto/EXPORTACIONES/";
    public static final String R_RUTA_FIRMAS="/fotospuesto/firmas/";
    public static final String R_RUTA_ORDENES="/ORDENES DE PUESTO/";
    public static final String R_RUTA_FORMACION="/ORDENES DE PUESTO/FORMACION/";
    public static final String R_RUTA_ORDENVS="/ORDENES DE PUESTO/VS/";
    public static final String R_RUTA_ORDENAUX="/ORDENES DE PUESTO/AUX/";
    public static final String R_RUTA_PROCEDIMIENTOS="/ORDENES DE PUESTO/PROCEDIMIENTOS/";
    public static final String R_CARPETA_EXPORTACION = "/fotospuesto/";
    public static final String R_RUTA_fototest = "/ORDENES DE PUESTO/FOTOSTEST/";
    public static final String R_RUTA_FOTOS = "/ORDENES DE PUESTO/FOTOS/";


    public static final String N_FICHERO_INSPECION_MANUAL="M\u00f3dulo Formaci\u00f3n Inspecciones Manuales.pdf";
    public static final String N_FICHERO_APM="M\u00f3dulo Formaci\u00f3n Control Port\u00f3n APM.pdf";
    public static final String N_FICHERO_CARROS_LIMPIEZA="M\u00f3dulo Formaci\u00f3n Inspecciones Carritos Limpieza v3.pdf";
    public static final String N_FICHERO_ETD="M\u00f3dulo Formaci\u00f3n Utilizaci\u00f3n Equipos ETD.pdf";
    public static final String N_FICHERO_REGISTRO="M\u00f3dulo Formaci\u00f3n registro de seguridad en aeronaves_v1.pdf";
    public static final String N_FICHERO_REGISTRO1="M\u00f3dulo Formaci\u00f3n registro de seguridad en aeronaves Boeing 787-900.pdf";
    public static final String N_FICHERO_REGISTRO2="M\u00f3dulo Formaci\u00f3n registro de seguridad en aeronaves Boeing 767-300.pdf";
    public static final String N_FICHERO_SUMINISTROS="M\u00f3dulo Formaci\u00f3n Suministros Aeropuerto.pdf";
    public static final String N_FICHERO_A_E_LAMINADOS="Modulo Formaci\u00f3n Artefactos Explosivos Laminados.pdf";
    // NOMBRE ARCHIVOS DE IMPORTACION DE DATOS
    public static final String A_FICHAS_VS="INSPECTOR_VS.TXT";
    public static final String A_FICHAS_AUX="INSPECTOR_AUX.TXT";
    public static final String A_ORDENES_FIRMADAS="INSPECTOR_ORDENESFIRMADAS.TXT";
    public static final String A_TEST_FORMACION="INSPECTOR_TEST.TXT";
    public static final String A_INSPECTORES="INSPECTOR_INSPECTORES.TXT";
    public static final String A_FORMADORES="FORMADORES.TXT";


    private Context contexto;
    private BasedbHelper dbHelper;
    private SQLiteDatabase db;

    //
    // Definimos columnas para lista
    //
    private String[] listaTip = new String[]{C_COLUMNA_ID, C_COLUMNA_TIP,C_COLUMNA_DNI,C_COLUMNA_NOMBREVS,C_COLUMNA_APELLIDO1,C_COLUMNA_APELLIDO2} ;
    private String[] listainspector = new String[]{C_COLUMNA_ID, C_COLUMNA_NOMBRE} ;
    private String[] listaAENA = new String[]{C_COLUMNA_ID, C_COLUMNA_AENA} ;
    private String[] listaFECHA = new String[]{C_COLUMNA_ID, C_COLUMNA_FECHA} ;

    public TipdbAdapter(Context context)
    {
        this.contexto = context;
    }

    public BasedbHelper abrir() throws SQLException
    {
        dbHelper = new BasedbHelper(contexto);
        db = dbHelper.getWritableDatabase();
        return dbHelper;
    }

    public void cerrar()
    {
        dbHelper.close();
    }


    //
    // Devuelve una lista (_id, nombre) con todos los registros
    //
    public Cursor getTIP() throws SQLException
    {
        Cursor c = db.query( true, C_TABLAvs, listaTip, null, null, null, null,null, null);

        return c;
    }
    public Cursor getnombreCOMPLETO() throws SQLException
    {
        Cursor c=db.rawQuery("SELECT _id,nombre,apellido1,apellido2,(nombre||' '|| apellido1 ||' '|| apellido2) AS nombrecompleto FROM VS ORDER BY nombrecompleto", null);


        //Cursor c = db.query( true, C_TABLAvs, listaTip, null, null, null, null,null, null);

        return c;
    }
    public Cursor getnombreCOMPLETOI() throws SQLException
    {
        Cursor c=db.rawQuery("SELECT _id,nombre,apellido1,apellido2, (apellido1 || ' ' || apellido2 || ' , ' || nombre ) AS nombrecompleto FROM VS ORDER BY nombrecompleto", null);

        //Cursor c = db.query( true, C_TABLAvs, listaTip, null, null, null, null,null, null);

        return c;
    }
    public Cursor getnombreCOMPLETOII(String NOMBRECOMPLETO,String FORMACION) throws SQLException
    {
        Cursor c=db.rawQuery("SELECT _id,FORMACION,PERSONA FROM FORMACIONFIRMADAS WHERE APROBADO='-1' AND FORMACION='"+FORMACION+"' AND PERSONA='"+NOMBRECOMPLETO+"'", null);

        return c;
    }
    public Cursor getINSPECTOR() throws SQLException
    {
        // Cursor c = db.query(true, C_TABLAinspector, listainspector, null, null, null, null, null, null);
        Cursor c = db.rawQuery("SELECT * FROM INSPECTORES",null);

        return c;
    }
    public Cursor getFORMADORES() throws SQLException
    {
        // Cursor c = db.query(true, C_TABLAinspector, listainspector, null, null, null, null, null, null);
        Cursor c = db.rawQuery("SELECT * FROM FORMADORES",null);

        return c;
    }

    public Cursor getAENA() throws SQLException
    {
        //Cursor c = db.query( true, C_TABLAENA, listaAENA, null, null, null, null, null, null);
        Cursor c1=db.rawQuery("SELECT * FROM AENA ORDER BY noconformidad ASC", null);
        return c1;
    }
    public Cursor getFECHA_DESDE_VS() throws SQLException
    {
        Cursor c=db.rawQuery("SELECT _id, fecha FROM inspeccionvs GROUP BY fecha ORDER BY _id DESC", null);
       //Cursor c = db.query( true, C_TABLAINSPECCIONVS, listaFECHA, null, null, C_COLUMNA_FECHA, null, null, null);

        return c;
    }
    public Cursor getFECHA_DESDE_PUESTO() throws SQLException
    {
        Cursor c=db.rawQuery("SELECT _id, fecha FROM VISITAPUESTO GROUP BY fecha ORDER BY _id DESC", null);
        //Cursor c = db.query( true, C_TABLAINSPECCIONPUESTO, listaFECHA, null, null, C_COLUMNA_FECHA, null, null, null);

        return c;
    }
    public Cursor getFECHA_DESDE_ENSAYO() throws SQLException
    {
        Cursor c=db.rawQuery("SELECT _id, fecha FROM ENSAYOS GROUP BY fecha ORDER BY _id DESC", null);
        //Cursor c = db.query( true, C_TABLAENSAYO, listaFECHA, null, null, C_COLUMNA_FECHA, null, null, null);

        return c;
    }
    public Cursor getFECHA_HASTA_VS() throws SQLException
    {
        Cursor c = db.query(true, C_TABLAINSPECCIONVS, listaFECHA, null, null, C_COLUMNA_FECHA, null, null, null);

        return c;
    }
    public Cursor getFECHA_HASTA_PUESTO() throws SQLException
    {
        Cursor c = db.query( true,C_TABLAINSPECCIONPUESTO, listaFECHA, null, null, C_COLUMNA_FECHA, null, null, null);

        return c;
    }
    public Cursor getFECHA_HASTA_ENSAYO() throws SQLException
    {
        Cursor c = db.query(C_TABLAENSAYO, listaFECHA, null, null, C_COLUMNA_FECHA, null, null, null);

        return c;
    }
    public Cursor apellidosNOMBRE() throws SQLException
    {
        Cursor c=db.rawQuery("SELECT _id,nombre,apellido1,apellido2,(apellido1 ||' '|| apellido2 ||' '|| nombre) AS nombrecompleto FROM VS ORDER BY nombrecompleto", null);
        //Cursor c = db.query( true, C_TABLAvs, listaTip, null, null, null, null,null, null);

        return c;
    }
    public Cursor getFILTRO(String FECHA) throws SQLException
    {
        Cursor c=db.rawQuery("SELECT * FROM ADM WHERE fecha='" + FECHA+ "'", null);

        return c;
    }
    public Cursor getFILTRO_MES(String MES,String YEAR) throws SQLException

    {
        Cursor c=db.rawQuery("SELECT * FROM ADM WHERE OBSERVACIONES='"+MES+"' AND REVISION_DETECTOR='"+ YEAR +"' ORDER BY fecha DESC", null);

        return c;
    }
    public Cursor PUESTO(String FECHA) throws SQLException
    {
        Cursor c=db.rawQuery("SELECT * FROM VISITAPUESTO WHERE MESYANO='" + FECHA+ "'", null);

        return c;
    }
    public Cursor vstip(String FECHA,String TIP) throws SQLException
    {
        Cursor c=db.rawQuery("SELECT * FROM inspeccionvs WHERE MESYANO='" + FECHA+ "'"+"AND tip='"+TIP+"'", null);

        return c;
    }
    public Cursor orden_firmada(String PUESTO,String TIP) throws SQLException
    {
       Cursor c=db.rawQuery("SELECT * FROM ORDENESFIRMADAS WHERE TIP='" + TIP + "'"+"AND PUESTO='"+PUESTO+"'", null);
       // Cursor c=db.rawQuery("SELECT * FROM ORDENESFIRMADAS WHERE TIP='"+TIP+"'", null);
       // Cursor c=db.rawQuery("SELECT * FROM ORDENESFIRMADAS", null);
        return c;
    }
    public Cursor NOMBRE() throws SQLException
    {
        Cursor c=db.rawQuery("SELECT _id,nombre,apellido1,apellido2,(apellido1 ||' '|| apellido2 ||', '|| nombre ) AS nombrecompleto FROM VS  ORDER BY nombrecompleto", null);

        //Cursor c=db.rawQuery("SELECT * FROM inspeccionvs WHERE MESYANO='" + FECHA+ "'"+"AND tip='"+TIP+"'", null);

        return c;
    }
    public Cursor TEST(String nombre_test) throws SQLException
    {
        Cursor c=db.rawQuery("SELECT * FROM PREGUNTASTEST WHERE TEST='"+nombre_test+"'", null);

        //Cursor c=db.rawQuery("SELECT * FROM inspeccionvs WHERE MESYANO='" + FECHA+ "'"+"AND tip='"+TIP+"'", null);

        return c;
    }
    public Cursor TEST_numero(int NUMERO) throws SQLException
    {
        Cursor c=db.rawQuery("SELECT * FROM PREGUNTASTEST WHERE NUMERO=" + NUMERO, null);

        //Cursor c=db.rawQuery("SELECT * FROM inspeccionvs WHERE MESYANO='" + FECHA+ "'"+"AND tip='"+TIP+"'", null);

        return c;
    }
    public Cursor NOMBRE_AUX() throws SQLException
    {
        Cursor c=db.rawQuery("SELECT _id,nombre,apellido1,apellido2,SINUSO1,(apellido1 ||' '|| apellido2 ||' '|| nombre ) AS nombrecompleto FROM AUX WHERE SINUSO1='LATAM' ORDER BY nombrecompleto", null);

        return c;
    }
    public Cursor Lista_Puestos() throws SQLException
    {
        Cursor c=db.rawQuery("SELECT * FROM PUESTOS ORDER BY puesto ASC", null);
        return c;
    }

    public Cursor VER_Puestos() throws SQLException
    {
        Cursor c=db.rawQuery("SELECT * FROM VISITAPUESTO ", null);

        return c;
    }
    public Cursor VER_Puestos_x_PUESTO(String Puesto) throws SQLException
    {
        Cursor c=db.rawQuery("SELECT * FROM VISITAPUESTO WHERE puesto='"+Puesto+"'", null);

        return c;
    }
    public Cursor VER_inspector_x_inspector(String Inspector) throws SQLException
    {
        Cursor c=db.rawQuery("SELECT * FROM VISITAPUESTO WHERE inspector='"+Inspector+"'", null);

        return c;
    }
    public Cursor VER_MES_x_MES() throws SQLException
    {
        Cursor c=db.rawQuery("SELECT * FROM VISITAPUESTO GROUP BY MESYANO", null);

        return c;
    }

    public Cursor VER_LISTA_x_MES(String mes) throws SQLException
    {
        Cursor c=db.rawQuery("SELECT * FROM VISITAPUESTO WHERE MESYANO='"+mes+"'", null);

        return c;
    }
    public Cursor GET_cctv(String FECHA) throws SQLException
    {
        Cursor c=db.rawQuery("SELECT * FROM CCTV WHERE FECHA='"+FECHA+"'", null);
        //Cursor c=db.rawQuery("SELECT * FROM CCTV ", null);
        return c;
    }

}
