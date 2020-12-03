package com.example.julio.inspector;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by JULIO on 17/05/2019.
 */

public class COPIAR_ARCHIVOS {

        private static final String TAG = "logcat";
        public COPIAR_ARCHIVOS(String sourceFile, String destinationFile) {

            try{

                File inFile = new File(sourceFile);
                File outFile = new File(destinationFile);

                FileInputStream in = new FileInputStream(inFile);
                FileOutputStream out =new FileOutputStream(outFile);

                byte[] buffer = new byte[1024];
                int c;


                while( (c = in.read(buffer) ) != -1)
                    out.write(buffer, 0, c);

                out.flush();
                in.close();
                out.close();

            } catch(IOException e) {

                Log.e(TAG, "Hubo un error de entrada/salida!!!");

            }

}}
