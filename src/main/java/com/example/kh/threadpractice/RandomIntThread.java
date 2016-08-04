package com.example.kh.threadpractice;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Random;

/**
 * Created by KH on 2016-08-04.
 */
public class RandomIntThread extends Thread {
    private Context context;
    private Handler toastMessageHandler;
    private Random random;
    private FileManager fileManager;
    private String filePath;


    public RandomIntThread(Context context,Handler toastMessageHandler, String filePath) {
        this.context = context;
        this.toastMessageHandler = toastMessageHandler;
        this.filePath = filePath;

        random = new Random();
        fileManager = FileManager.INSTANCE;
    }

    @Override
    public void run() {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        String randomInt = Integer.toString(random.nextInt());
        bundle.putString("msg", randomInt);
        msg.setData(bundle);
        toastMessageHandler.sendMessage(msg);

        fileManager.writeFile(filePath, randomInt);
    }
}
