package com.igaitapp.virtualmd.igait;

import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by jesus on 9/12/15.
 */
public class ClientThread implements Runnable {
    private Socket socket;
    private static final int SERVERPORT = 8080;
    private static final String SERVER_IP = "10.192.0.132";

    @Override
    public void run() {
        try {
            InetAddress serverAddress = InetAddress.getByName(SERVER_IP);

            socket = new Socket(serverAddress, SERVERPORT);
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void sendMessage(View view, String message) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.println(message);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}