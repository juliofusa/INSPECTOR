package com.example.julio.inspector;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ver_puestos extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener {
    private inspeccion_puesto_lista CursorAdapter;
    private TipdbAdapter dbAdapterSituacion,DBADAPT;
    private ListView VER_LISTADOS_DE_PUESTOS_INSPECCIONADOS;
    private Spinner Spin_PUESTOS,Spin_MES,Spin_INSPECTOR;
    private String PUESTO="",INSPECTOR="",MES="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_puestos);

        INICIALIZAR();

    }

    private void INICIALIZAR(){

        VER_LISTADOS_DE_PUESTOS_INSPECCIONADOS=findViewById(R.id.LISTA_DE_PUESTOS);

        Spin_PUESTOS=findViewById(R.id.s_PUESTOS_VER_REGISTROS);

        Spin_MES=findViewById(R.id.spinner_mes);

        Spin_INSPECTOR=findViewById(R.id.spinner_inspector);

        LLENAR_PUESTOS();

        LLENAR_INSPECTORES();

        LLENAR_MESES();
       // LLENAR_LISTA_VER_PUESTOS(PUESTO);

    }

    private void LLENAR_LISTA_VER_PUESTOS(String s_puesto){

        dbAdapterSituacion = new TipdbAdapter(this) ;

        dbAdapterSituacion.abrir();

        int c;

        if(!s_puesto.equals("")){

            Cursor CURSOR_FILTRO=dbAdapterSituacion.VER_Puestos_x_PUESTO(s_puesto);

            c=CURSOR_FILTRO.getCount();

            Toast.makeText(getBaseContext(), "Nº REGISTROS: "+c , Toast.LENGTH_SHORT).show();

            inspeccion_puesto_lista CursorAdapter=new inspeccion_puesto_lista(this,CURSOR_FILTRO);

            //otra_lista.setAdapter(CursorAdapter);
           VER_LISTADOS_DE_PUESTOS_INSPECCIONADOS.setAdapter(CursorAdapter);

        }else{

            Cursor CURSOR_FILTRO=dbAdapterSituacion.VER_Puestos();

            c=CURSOR_FILTRO.getCount();

            inspeccion_puesto_lista CursorAdapter=new inspeccion_puesto_lista(this,CURSOR_FILTRO);

            VER_LISTADOS_DE_PUESTOS_INSPECCIONADOS.setAdapter(CursorAdapter);

        }


    }

    private void LLENAR_LISTA_VER_INSPECTOR(String s_inspector){

        DBADAPT = new TipdbAdapter(this) ;

        DBADAPT.abrir();

        int c;

        if(!s_inspector.equals("")){

            Cursor CURSOR_FILTRO=DBADAPT.VER_inspector_x_inspector(s_inspector);

            c=CURSOR_FILTRO.getCount();

            Toast.makeText(getBaseContext(), "Nº REGISTROS: "+c , Toast.LENGTH_SHORT).show();

            inspeccion_puesto_lista CursorAdapter=new inspeccion_puesto_lista(this,CURSOR_FILTRO);

            VER_LISTADOS_DE_PUESTOS_INSPECCIONADOS.setAdapter(CursorAdapter);

        }else{

            Cursor CURSOR_FILTRO=DBADAPT.VER_Puestos();

            c=CURSOR_FILTRO.getCount();

            inspeccion_puesto_lista CursorAdapter=new inspeccion_puesto_lista(this,CURSOR_FILTRO);

            VER_LISTADOS_DE_PUESTOS_INSPECCIONADOS.setAdapter(CursorAdapter);

        }


    }

    private void LLENAR_LISTA_VER_MESES(String s_mes){

        DBADAPT = new TipdbAdapter(this) ;

        DBADAPT.abrir();

        int c;

        if(!s_mes.equals("")){

            Cursor CURSOR_FILTRO=DBADAPT.VER_LISTA_x_MES(s_mes);

            c=CURSOR_FILTRO.getCount();

            Toast.makeText(getBaseContext(), "Nº REGISTROS: "+c , Toast.LENGTH_SHORT).show();

            inspeccion_puesto_lista CursorAdapter=new inspeccion_puesto_lista(this,CURSOR_FILTRO);

            VER_LISTADOS_DE_PUESTOS_INSPECCIONADOS.setAdapter(CursorAdapter);

        }else{

            Cursor CURSOR_FILTRO=DBADAPT.VER_Puestos();

            c=CURSOR_FILTRO.getCount();

            inspeccion_puesto_lista CursorAdapter=new inspeccion_puesto_lista(this,CURSOR_FILTRO);

            VER_LISTADOS_DE_PUESTOS_INSPECCIONADOS.setAdapter(CursorAdapter);

        }


    }

    private void LLENAR_PUESTOS(){
        //final String[] puesto = getResources().getStringArray(R.array.puestos);

        DBADAPT = new TipdbAdapter(this) ;

        DBADAPT.abrir();

        Cursor cursorPuestos=DBADAPT.Lista_Puestos();

        SimpleCursorAdapter adapterPuestos = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorPuestos,(new String[] {"puesto"}), new int[] {android.R.id.text1},0);

        //ArrayAdapter<String> adaptadorPUESTO = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, puesto);

         Spin_PUESTOS.setAdapter(adapterPuestos);

         Spin_PUESTOS.setOnItemSelectedListener(this);
    }

    private void LLENAR_INSPECTORES(){

        DBADAPT = new TipdbAdapter(this) ;

        DBADAPT.abrir();

        Cursor cursorINSPECTORES = DBADAPT.getINSPECTOR();

        SimpleCursorAdapter adapterINSPECTORES = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorINSPECTORES,(new String[] {"nombre"}), new int[] {android.R.id.text1},0);

        Spin_INSPECTOR.setAdapter(adapterINSPECTORES);

        Spin_INSPECTOR.setOnItemSelectedListener(this);
    }

    private void LLENAR_MESES(){

        DBADAPT = new TipdbAdapter(this) ;

        DBADAPT.abrir();

        Cursor cursorMESES = DBADAPT.VER_MES_x_MES();

        SimpleCursorAdapter adapterMESES = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_checked,cursorMESES,(new String[] {"MESYANO"}), new int[] {android.R.id.text1},0);

        Spin_MES.setAdapter(adapterMESES);

        Spin_MES.setOnItemSelectedListener(this);
    }

    public void SALIR(View view){finish();}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        parent.getItemAtPosition(i);
        switch (parent.getId()){
            case R.id.s_PUESTOS_VER_REGISTROS:

                Cursor c3=(Cursor)parent.getItemAtPosition(i);

                PUESTO=c3.getString(c3.getColumnIndex(TipdbAdapter.C_COLUMNA_PUESTO));

                //PUESTO=parent.getItemAtPosition(i).toString();

                LLENAR_LISTA_VER_PUESTOS(PUESTO);

                break;

                case R.id.spinner_inspector:

                    Cursor c1= (Cursor) parent.getItemAtPosition(i);

                    INSPECTOR=c1.getString(1);

                    if (!INSPECTOR.equals("")){ LLENAR_LISTA_VER_INSPECTOR(INSPECTOR);}

                break;
            case R.id.spinner_mes:

                Cursor c2= (Cursor) parent.getItemAtPosition(i);

                MES=c2.getString(28);

                if (!MES.equals("")){ LLENAR_LISTA_VER_MESES(MES);}

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
