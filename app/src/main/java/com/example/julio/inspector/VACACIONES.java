package com.example.julio.inspector;

import android.app.DatePickerDialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class VACACIONES extends AppCompatActivity implements View.OnClickListener {
private EditText enero1q_1I,enero1q_1F,enero1q_1T,enero2q_I,enero2q_F,enero2q_T;

    private DatePickerDialog fecha__picker;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacaciones);
        dateFormatter= new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT);
        CONTROLES();

    }
public void CONTROLES(){
            enero1q_1I=(EditText)findViewById(R.id.ENERO_1Q_I);
            enero1q_1F=(EditText)findViewById(R.id.ENERO_1Q_F);
            enero1q_1T=(EditText)findViewById(R.id.ENERO_1Q_T);
            enero1q_1I.setOnClickListener(this);
    enero2q_I=(EditText)findViewById(R.id.ENERO_2Q_I);
    enero2q_F=(EditText)findViewById(R.id.ENERO_2Q_F);
    enero2q_T=(EditText)findViewById(R.id.ENERO_2Q_T);
    enero2q_I.setOnClickListener(this);
}
    private void setDatetimeField(int mes,String Titulo){

        Calendar newCalendar= Calendar.getInstance();
        final String Titulo2=Titulo;

        fecha__picker=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int mes, int dayOfMonth) {
                fecha__picker.setTitle("SELECCIONAR FECHA");

                Calendar newDate = Calendar.getInstance();
                Calendar newDate2 = Calendar.getInstance();
                newDate.set(year,mes, dayOfMonth);
                newDate2.set(year,mes, dayOfMonth+14);
                if (mes==0 && Titulo2.equals("ENERO 1")){
                enero1q_1I.setText(dateFormatter.format(newDate.getTime()));
                enero1q_1F.setText(dateFormatter.format(newDate2.getTime()));
                enero1q_1T.setText("VACACIONES");}
                if (mes==0 && Titulo2.equals("ENERO 2")){
                    enero2q_I.setText(dateFormatter.format(newDate.getTime()));
                    enero2q_F.setText(dateFormatter.format(newDate2.getTime()));
                    enero2q_T.setText("VACACIONES");}
                //Toast.makeText(getBaseContext(), dateFormatter.format(mes), Toast.LENGTH_SHORT).show();
                //dateFormatter.format(newDate.getTime()) monthOfYear
            }
        },newCalendar.get(Calendar.YEAR),mes,newCalendar.get(Calendar.DAY_OF_MONTH));
        fecha__picker.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vacacione, menu);
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
    public void onClick(View v) {

        if (v==enero1q_1I){setDatetimeField(0,"ENERO 1");}
        if (v==enero2q_I) {setDatetimeField(0,"ENERO 2");}
    }
}
