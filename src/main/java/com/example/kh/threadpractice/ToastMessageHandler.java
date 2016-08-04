package com.example.kh.threadpractice;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by KH on 2016-08-04.
 */
public class ToastMessageHandler extends Handler {
    private Context context;

    public ToastMessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        Bundle bundle = msg.getData();

        String strMessage = bundle.getString("msg");

        Toast.makeText(context, strMessage,Toast.LENGTH_SHORT).show();
    }
}
