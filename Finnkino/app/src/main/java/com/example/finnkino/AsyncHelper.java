package com.example.finnkino;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public class AsyncHelper {
    private Context context;

    public AsyncHelper(Context context) {
        if (!(context instanceof AsyncInterface) || !(context instanceof AppCompatActivity)) {
            System.out.println("Listener class must implement Async interface and be type of ui class.");
            System.exit(1023);
        }
        this.context = context;
    }

    public void sendResponse(AsyncInterface.AsyncType type, AsyncInterface.AsyncStatus status, String args) {
        // https://stackoverflow.com/questions/12850143/android-basics-running-code-in-the-ui-thread
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((AsyncInterface)context).onAsyncComplete(type, status, args);
            }
        });
    }
}
