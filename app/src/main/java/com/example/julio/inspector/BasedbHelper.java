package com.example.julio.inspector;

/**
 * Created by JULIO on 17/07/2015.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by JULIO on 25/06/2015.
 */
public class BasedbHelper extends SQLiteOpenHelper {

    String sqlCreateVS= "CREATE TABLE VS (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido1 TEXT, apellido2 TEXT, tip TEXT, dni TEXT,id_scaner TEXT,password_scaner TEXT,telefono TEXT,IDSUSPENDIDO TEXT,ANTIGUEDAD TEXT,ANTIGUEDAD_AEROPUERTO TEXT,C1 TEXT,C2 TEXT,REFRESCOC2 TEXT,CADUCAC1 TEXT,CADUCAC2 TEXT,CADUCA_REFRESCOC2 TEXT,DOMICILIO TEXT,NUMERO TEXT,PISO TEXT,CODIGO_POSTAL TEXT,MUNICIPIO TEXT,TALLA_CAMISA TEXT,TALLA_PANTALON TEXT,TALLA_CAZADORA TEXT,TALLA_ANORAK TEXT,TALLA_ZAPATOS TEXT,SINUSO1 TEXT,SINUSO2 TEXT,SINUSO3 TEXT,SINUSO4 TEXT,SINUSO5 TEXT,SINUSO6 TEXT,SINUSO7 TEXT,SINUSO8 TEXT,SINUSO9 TEXT,SINUSO10 TEXT,ID_DISPOSITIVO TEXT)";
    String sqlCreateAUX= "CREATE TABLE AUX (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido1 TEXT, apellido2 TEXT, tia TEXT, dni TEXT,telefono TEXT,ANTIGUEDAD TEXT,ANTIGUEDAD_AEROPUERTO TEXT,FORMACION1 TEXT,FORMACION2 TEXT,CADUCA1 TEXT,CADUCA2 TEXT,DOMICILIO TEXT,NUMERO TEXT,PISO TEXT,CODIGO_POSTAL TEXT,MUNICIPIO TEXT,TALLA_CAMISA TEXT,TALLA_PANTALON TEXT,TALLA_CAZADORA TEXT,TALLA_ANORAK TEXT,TALLA_ZAPATOS TEXT,SINUSO1 TEXT,SINUSO2 TEXT,SINUSO3 TEXT,SINUSO4 TEXT,SINUSO5 TEXT,SINUSO6 TEXT,SINUSO7 TEXT,SINUSO8 TEXT,SINUSO9 TEXT,SINUSO10 TEXT,ID_DISPOSITIVO TEXT)";
    String sqlCreateinspeccionvs = "CREATE TABLE inspeccionvs (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,inspector TEXT,fecha TEXT, hora TEXT, puesto TEXT, tip TEXT, nombre TEXT, apellidos1 TEXT, apellidos2 TEXT, dni TEXT, zapatos TEXT, pantalon TEXT, cazadora TEXT, camisa TEXT, abrigo TEXT, epi TEXT, defensa TEXT, grilletes TEXT, correajes TEXT, otros TEXT, ordenes TEXT, desmpe TEXT, correcciones TEXT, observacion TEXT, tipvalido TEXT, correcto TEXT,MESYANO TEXT,ID_DISPOSITIVO TEXT)";
    String sqlCreateinspeccionpuesto = "CREATE TABLE inspeccionpuesto (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombrepuesto TEXT,ID_DISPOSITIVO TEXT)";
    String sqlCreateAENA = "CREATE TABLE AENA (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, noconformidad TEXT,ID_DISPOSITIVO TEXT)";
    String sqlCreateINSPECTORES = "CREATE TABLE INSPECTORES (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre TEXT, dni TEXT,ID_DISPOSITIVO TEXT)";
    String sqlCreateFORMADORES = "CREATE TABLE FORMADORES (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre TEXT, ID_DISPOSITIVO TEXT)";

    String sqlCreatePUESTOS = "CREATE TABLE PUESTOS (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, puesto TEXT,ID_DISPOSITIVO TEXT)";
    String sqlCreateVISITAPUESTO = "CREATE TABLE VISITAPUESTO (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, inspector TEXT,fecha TEXT, hora TEXT, puesto TEXT,adm TEXT,calibradoADM TEXT, maquinaRX TEXT, detectorLIQUIDOS TEXT,detectoZAPATOS TEXT, otros TEXT, incidencia TEXT, riesgos TEXT, rutaFOTO TEXT,incidenciaANTERIOR TEXT, corregidaINCIDENCIA TEXT, rutaFOTOanterior TEXT, observacionesANTERIOR TEXT, observacion TEXT,PROCEDIMIENTO TEXT,ORDENESFIRMADAS TEXT,rutaFOTO2 TEXT,rutaFOTO3 TEXT,rutaFOTO4 TEXT,BARRERAS TEXT,TORNOS TEXT,TRAZAS TEXT,ID_DISPOSITIVO TEXT,MESYANO TEXT)";
    String sqlCreateENSAYO = "CREATE TABLE ENSAYOS (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,tipoensayo TEXT, personalinspector TEXT,fecha TEXT, horaINICIO TEXT, horaFINALIZACION TEXT, lugar TEXT,personal1 TEXT,personal2 TEXT, personal3 TEXT, equiporadioscopia TEXT,arcoadn TEXT, medidascorrectoras TEXT, rutaFOTO TEXT,DESCRIPCION TEXT,RESULTADO TEXT,ID_DISPOSITIVO TEXT)";
    String sqlCreateinspeccion_manual = "CREATE TABLE inspeccionmanual (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,tip TEXT, personalVS TEXT,fecha TEXT, CINTURILLA TEXT, CUELLO TEXT, HOMBROSYBRAZOS TEXT,TORSOYESPALDA TEXT,MUSLOYENTREPIERNA TEXT, empeinezapato TEXT, INSPECTOR TEXT,CORRECTO TEXT,HORA TEXT,PUESTO TEXT,ID_DISPOSITIVO TEXT)";
    String sqlCreatExportaciones = "CREATE TABLE EXPORTACION (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, fecha TEXT,ID_DISPOSITIVO TEXT)";
    String sqlCreateULTIMA_EXPORTACION = "CREATE TABLE REGISTRO (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, fecha_ultima TEXT)";
    String sqlCreateULTIMA2_EXPORTACION = "CREATE TABLE REGISTROPUESTO (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, fecha_ultima TEXT)";
    String sqlCreateULTIMA3_EXPORTACION = "CREATE TABLE REGISTROADM (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, fecha_ultima TEXT)";
    String sqlCreateULTIMA4_EXPORTACION = "CREATE TABLE REGISTROINSMANUAL (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, fecha_ultima TEXT)";
    String sqlCreateROPA = "CREATE TABLE ROPA (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, fecha TEXT, DESCRIPCION TEXT, ID_PRODUCTO TEXT,CANTIDAD TEXT,TALLA TEXT,MOVIMIENTO TEXT,OTROS TEXT,NOMBRE TEXT)";
    String sqlCreateVACACIONES = "CREATE TABLE VACACIONES (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT, SEXO TEXT, ESTADO TEXT, ANO TEXT, ENERO1 TEXT, ENERO2 TEXT,tipoENERO1 TEXT,ENERO1_2 TEXT,ENERO2_2 TEXT,tipoENERO2 TEXT, FEBRERO1 TEXT, FEBRERO2 TEXT,tipoFEBRERO1 TEXT,FEBRERO1_2 TEXT,FEBRERO2_2 TEXT,tipoFEBRERO2 TEXT, MARZO1 TEXT, MARZO2 TEXT,tipoMARZO1 TEXT,MARZO1_2 TEXT,MARZO2_2 TEXT,tipoMARZO2 TEXT, ABRIL1 TEXT, ABRIL2 TEXT,tipoABRIL1 TEXT,ABRIL1_2 TEXT,ABRIL2_2 TEXT,tipoABRIL2 TEXT, MAYO1 TEXT, MAYO2 TEXT,tipoMAYO1 TEXT,MAYO1_2 TEXT,MAYO2_2 TEXT,tipoMAYO2 TEXT, JUNIO1 TEXT, JUNIO2 TEXT,tipoJUNIO1 TEXT,JUNIO1_2 TEXT,JUNIO2_2 TEXT,tipoJUNIO2 TEXT, JULIO1 TEXT, JULIO2 TEXT,tipoJULIO1 TEXT,JULIO1_2 TEXT,JULIO2_2 TEXT,tipoJULIO2 TEXT, AGOSTO1 TEXT, AGOSTO2 TEXT,tipoAGOSTO1 TEXT,AGOSTO1_2 TEXT,AGOSTO2_2 TEXT,tipoAGOSTO2 TEXT, SEPTIEMBRE1 TEXT, SEPTIEMBRE2 TEXT,tipoSEPTIEMBRE1 TEXT,SEPTIEMBRE1_2 TEXT,SEPTIEMBRE2_2 TEXT,tipoSEPTIEMBRE2 TEXT, OCTUBRE1 TEXT, OCTUBRE2 TEXT,tipoOCTUBRE1 TEXT,OCTUBRE1_2 TEXT,OCTUBRE2_2 TEXT,tipoOCTUBRE2 TEXT, NOVIEMBRE1 TEXT, NOVIEMBRE2 TEXT,tipoNOVIEMBRE1 TEXT,NOVIEMBRE1_2 TEXT,NOVIEMBRE2_2 TEXT,tipoNOVIEMBRE2 TEXT, DICIEMBRE1 TEXT, DICIEMBRE2 TEXT,tipoDICIEMBRE1 TEXT,DICIEMBRE1_2 TEXT,DICIEMBRE2_2 TEXT,tipoDICIEMBRE2 TEXT,FIRMA TEXT)";
    String sqlCreateADM = "CREATE TABLE ADM (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,fecha TEXT,PERSONA TEXT,FILTRO TEXT,HORA TEXT,OBSERVACIONES TEXT,REVISION_DETECTOR TEXT,ETA TEXT)";
    String sqlCreatORDENESFIRMADAS = "CREATE TABLE ORDENESFIRMADAS (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, fecha TEXT, PUESTO TEXT, PERSONA TEXT, TIP TEXT)";

    String sqlCreatFORMACIONFIRMADAS = "CREATE TABLE FORMACIONFIRMADAS (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, fecha TEXT, horaINICIO TEXT, horaFIN TEXT, FORMACION TEXT, PERSONA TEXT, TIP TEXT, RESULTADO INTEGER,APROBADO TEXT, FIRMA TEXT,FORMADOR TEXT)";
    String sqlCreatTEST = "CREATE TABLE PREGUNTASTEST (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, NUMERO INTEGER, PREGUNTA TEXT, RESPUESTA1 TEXT,RESPUESTA2 TEXT, RESPUESTA3 TEXT,RESPUESTA4 TEXT,CORRECTA TEXT,FIJA TEXT, TEST TEXT, SEXO TEXT)";
    public BasedbHelper(Context context) {
        super(context, "DBINSPECCIONES", null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreateVS);
        db.execSQL(sqlCreateAUX);
        db.execSQL(sqlCreateinspeccionvs);
        db.execSQL(sqlCreateinspeccionpuesto);
        db.execSQL(sqlCreateAENA);
        db.execSQL(sqlCreateINSPECTORES);
        db.execSQL(sqlCreateFORMADORES);
        db.execSQL(sqlCreatePUESTOS);
        db.execSQL(sqlCreateVISITAPUESTO);
        db.execSQL(sqlCreateENSAYO);
        db.execSQL(sqlCreatExportaciones);
        db.execSQL(sqlCreateULTIMA_EXPORTACION);
        db.execSQL(sqlCreateULTIMA2_EXPORTACION);
        db.execSQL(sqlCreateULTIMA3_EXPORTACION);
        db.execSQL(sqlCreateULTIMA4_EXPORTACION);
        db.execSQL(sqlCreateROPA);
        db.execSQL(sqlCreateVACACIONES);
        db.execSQL(sqlCreateADM);
        db.execSQL(sqlCreateinspeccion_manual);
        db.execSQL(sqlCreatORDENESFIRMADAS);
        db.execSQL(sqlCreatFORMACIONFIRMADAS);
        db.execSQL(sqlCreatTEST);
        Log.i(this.getClass().toString(), "BASE INSPECTOR creada");

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//Se elimina la version anterior de la tabla

        if (newVersion > oldVersion) {
            db.execSQL(sqlCreateFORMADORES);
            //db.execSQL("ALTER TABLE FORMACIONFIRMADAS ADD COLUMN FORMADOR TEXT");
        }
    }
}
