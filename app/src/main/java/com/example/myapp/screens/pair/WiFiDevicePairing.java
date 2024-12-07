package com.example.myapp.screens.pair;


import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class WiFiDevicePairing extends Pair {
    private static final String DEFAULT_DEVICE_IP = "192.168.4.1";
    private static final int DEFAULT_PORT = 45678;

    private final String gateway;
    private final int port;  // Define your port

    public WiFiDevicePairing(Message message, PairCallback callback) {
        this(DEFAULT_DEVICE_IP, DEFAULT_PORT, message, callback);
    }

    public WiFiDevicePairing(String gateway, int port, Message message, PairCallback callback) {
        super(message, callback);
        this.gateway = gateway;
        this.port = port;
    }

    public String getDeviceInfo() {
        return super.deviceInfo;
    }

    @Override
    public boolean pair() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(gateway, port), 500);
            if (devicePair(socket.getOutputStream(), socket.getInputStream())) {
                return true;
            }
        } catch (IOException e) {
            e.fillInStackTrace();
            String errorMessage = e.getMessage();
            if (errorMessage != null) {
                Log.e(this.getClass().getName(), errorMessage);
            }
        } catch (PairingError e) {
            Log.e(this.getClass().getName(), e.message + " " + e.status);
            return false;
        }
        return false;
    }
}