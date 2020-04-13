package com.example.finnkino;

// https://www.geeksforgeeks.org/asynchronous-synchronous-callbacks-java/
public interface AsyncInterface {
    enum AsyncStatus {
        OK,
        ERROR
    }
    enum AsyncType {
        THEATHERS,
        MOVIES,
        DATE,
        TIME
    }
    void onAsyncComplete(AsyncType type, AsyncStatus status, String args);
}
