package com.example.kh.threadpractice;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    private TextView txt_content;
    private Handler toastMessageHandler;
    private Thread randomIntThread;
    private String filePath;
    private FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileManager = FileManager.INSTANCE;
        StringBuilder sb = new StringBuilder();

        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/");
        sb.append("Download");
        sb.append("/");
        sb.append("tmp.txt");
        Log.d(TAG, "Path: " + sb.toString());
        //Log.d(TAG, "this.getFilesDir(): " + this.getFilesDir());

        filePath = sb.toString();
        txt_content = (TextView) findViewById(R.id.txt_content);

        toastMessageHandler = new ToastMessageHandler(getApplicationContext());
    }

    public void buttonClick(View view){
        switch (view.getId()){
            case R.id.bt_thread :
                createRandomInt(); break;
            case R.id.bt_print :
                printFile(); break;
            default: break;
        }
    }

    public void createRandomInt(){
        randomIntThread = new RandomIntThread(this, toastMessageHandler, filePath);

        randomIntThread.start();
    }

    public void printFile(){
        txt_content.setText("");
        String content = fileManager.readOriginalFile(filePath);
        txt_content.setText(content);
    }

}
