package com.example.kh.threadpractice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by KH on 2016-08-04.
 */
public class FileManager {
    public static final FileManager INSTANCE = new FileManager();

    private FileManager() {
    }

    public synchronized String readOriginalFile(String filePath){
        StringBuilder stringBuilder = null;
        BufferedReader bufferedReader = null;
        File file = null;

        try {
            file = new File(filePath);
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

            stringBuilder = new StringBuilder();

            while(true){
                String data = bufferedReader.readLine();

                if(data == null) break;

                stringBuilder.append(data);
                stringBuilder.append("\n");
            }

        } catch (FileNotFoundException e) {
            //write the file if it isn't exist.
            writeFile(filePath, null);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Close stream.
            if(bufferedReader != null) try { bufferedReader.close(); } catch (IOException e) { e.printStackTrace(); }
        }

        return stringBuilder.toString();
    }

    public synchronized void writeFile(String filePath, String content){
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        StringBuilder stringBuilder = null;
        File file = new File(filePath);
        //Log.d(TAG, "file.getName(): " + file.getName());

        try {
            stringBuilder = new StringBuilder(content);
            stringBuilder.append("\n");

            if( file.exists() ){
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));

                while(true){
                    String data = bufferedReader.readLine();

                    if(data == null) break;

                    stringBuilder.append(data);
                    stringBuilder.append("\n");
                }
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Close stream.
            if(bufferedReader != null){  try { bufferedReader.close();  } catch (IOException e) { e.printStackTrace(); } }
            if(bufferedWriter != null){  try { bufferedWriter.close();  } catch (IOException e) { e.printStackTrace(); } }
        }
    }
}
