package com.example.julio.inspector;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;


public class pdfview extends AppCompatActivity {
   // public static final String SAMPLE_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + TipdbAdapter.R_RUTA_FORMACION + TipdbAdapter.N_FICHERO_APM; //your file path
    PDFView pdfView;
    Integer pageNumber = 1;
    String pdfFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        pdfFileName=getIntent().getStringExtra("ruta");
        pdfView= (PDFView)findViewById(R.id.pdfView);


        Uri path = Uri.fromFile(new File(pdfFileName));
        pdfView.fromUri(path)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .load();

    }




}
