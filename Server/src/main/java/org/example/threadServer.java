package org.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class threadServer extends Thread {
    private Socket sck, sck2;
    private InputStreamReader isr1, isr2;
    private BufferedReader buff1, buff2;
    private DataOutputStream out, out2;

    public threadServer (Socket sck, Socket sck2) {
        this.sck = sck;
        this.sck2 = sck2;
        try {
            this.isr1 = new InputStreamReader(sck.getInputStream());
            this.buff1 = new BufferedReader(isr1);
            this.isr2 = new InputStreamReader(sck2.getInputStream());
            this.buff2 = new BufferedReader(isr2);

            out = new DataOutputStream(sck.getOutputStream());
            out.writeBytes("X" + "\n");

            out2 = new DataOutputStream(sck2.getOutputStream());
            out2.writeBytes("O" + "\n");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String a = buff1.readLine();
            String b = buff2.readLine();
            out.writeBytes(b + "\n");
            out2.writeBytes(a + "\n");
            System.out.println(a + " @" + sck.getInetAddress() + " connected");
            System.out.println(b + " @" + sck2.getInetAddress() + " connected");
            while (true) {
                String msg1 = buff1.readLine();
                out2.writeBytes(msg1 + "\n");
                String msg2 = buff2.readLine();
                out.writeBytes(msg2 + "\n");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
