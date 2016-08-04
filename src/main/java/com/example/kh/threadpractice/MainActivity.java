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
    private Handler threadHandler,fileHandler;
    private Thread thread;
    private Random random;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        random = new Random();
        threadHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int a = msg.getData().getInt(getResources().getString(R.string.random_int));
                Toast.makeText(getApplicationContext(),
                        Integer.toString(a),
                        Toast.LENGTH_SHORT).show();
            }
        };

        fileHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(getApplicationContext(), "파일을 생성합니다.", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void buttonClick(View view){
        switch (view.getId()){
            case R.id.bt_thread :
                createRandomInt(); break;
            case R.id.bt_print :
                printOriginalFile(); break;
            default: break;
        }
    }

    public void createRandomInt(){
        thread = new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                Bundle bundle = new Bundle();
                int intRandom = random.nextInt();
                bundle.putInt(getResources().getString(R.string.random_int),intRandom);
                msg.setData(bundle);
                threadHandler.sendMessage(msg);

                writeFile(Integer.toString(intRandom));
            }
        };

        thread.start();
    }

    public synchronized void printOriginalFile(){
        BufferedReader bufferedReader = null;
        File file = null;

        try {
            file = new File(filePath);
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            txt_content.setText("");

            while(true){
                String data = bufferedReader.readLine();

                if(data == null) break;

                txt_content.append(data);
                txt_content.append("\n");
            }

        } catch (FileNotFoundException e) {
            //write the file if it isn't exist.
            writeFile(null);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Close stream.
            if(bufferedReader != null) try { bufferedReader.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public synchronized void writeFile(String content){
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
