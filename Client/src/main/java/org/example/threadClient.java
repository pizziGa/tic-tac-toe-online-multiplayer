package org.example;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class threadClient extends Thread {
    private Socket sck;
    private MainClient frm;
    private InputStreamReader isr;
    private BufferedReader buff;
    private DataOutputStream out;
    protected int x, y, x1, y1;

    public threadClient(Socket sck, MainClient frm) {
        this.sck = sck;
        this.frm = frm;
        try {
            this.isr = new InputStreamReader(sck.getInputStream());
            this.buff = new BufferedReader(isr);
            this.out = new DataOutputStream(sck.getOutputStream());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String a = buff.readLine();
            if (a.equals("X") || a.equals("O")) {
                this.frm.isPlayer(a);
            }
            String Opp = buff.readLine();
            frm.opp.setText("Your opponent is " + Opp);
            while (!frm.finito) {
                String b = buff.readLine();
                int pos = Integer.parseInt(b);
                for (JButton buttons : frm.btn) {
                    if (buttons.getText().equals(""))
                        buttons.setEnabled(true);
                }
                frm.btn[pos].setEnabled(false);
                frm.btn[pos].setText(String.valueOf(frm.character == 'X' ? 'O' : 'X'));
                isTris();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void send(String x) {
        try {
            out.writeBytes(x + "\n");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void isTris() {
        String carattere = "";
        if ((!frm.btn[0].getText().equals("")) && frm.btn[0].getText().equals(frm.btn[1].getText()) && frm.btn[1].getText().equals(frm.btn[2].getText())) {
            carattere = frm.btn[0].getText();
            x = frm.btn[0].getX();
            y = frm.btn[0].getY() + (frm.btn[0].getHeight() / 2);
            x1 = frm.btn[2].getX() + frm.btn[2].getWidth();
            y1 = frm.btn[2].getY() + (frm.btn[2].getHeight() / 2);
        } else if ((!frm.btn[3].getText().equals("")) && frm.btn[3].getText().equals(frm.btn[4].getText()) && frm.btn[4].getText().equals(frm.btn[5].getText())) {
            carattere = frm.btn[3].getText();
            x = frm.btn[3].getX();
            y = frm.btn[3].getY() + (frm.btn[3].getHeight() / 2);
            x1 = frm.btn[5].getX() + frm.btn[5].getWidth();
            y1 = frm.btn[5].getY() + (frm.btn[5].getHeight() / 2);
        } else if ((!frm.btn[6].getText().equals("")) && frm.btn[6].getText().equals(frm.btn[7].getText()) && frm.btn[7].getText().equals(frm.btn[8].getText())) {
            carattere = frm.btn[6].getText();
            x = frm.btn[6].getX();
            y = frm.btn[6].getY() + (frm.btn[6].getHeight() / 2);
            x1 = frm.btn[8].getX() + frm.btn[8].getWidth();
            y1 = frm.btn[8].getY() + (frm.btn[8].getHeight() / 2);
        } else if ((!frm.btn[0].getText().equals("")) && frm.btn[0].getText().equals(frm.btn[3].getText()) && frm.btn[3].getText().equals(frm.btn[6].getText())) {
            carattere = frm.btn[0].getText();
            x = frm.btn[0].getX() + (frm.btn[0].getWidth() / 2);
            y = frm.btn[0].getY();
            x1 = frm.btn[6].getX() + (frm.btn[6].getWidth() / 2);
            y1 = frm.btn[6].getY() + frm.btn[6].getHeight();
        } else if ((!frm.btn[1].getText().equals("")) && frm.btn[1].getText().equals(frm.btn[4].getText()) && frm.btn[4].getText().equals(frm.btn[7].getText())) {
            carattere = frm.btn[1].getText();
            x = frm.btn[1].getX() + (frm.btn[1].getWidth() / 2);
            y = frm.btn[1].getY();
            x1 = frm.btn[7].getX() + (frm.btn[7].getWidth() / 2);
            y1 = frm.btn[7].getY() + frm.btn[7].getHeight();
        } else if ((!frm.btn[5].getText().equals("")) && frm.btn[2].getText().equals(frm.btn[5].getText()) && frm.btn[5].getText().equals(frm.btn[8].getText())) {
            carattere = frm.btn[2].getText();
            x = frm.btn[2].getX() + (frm.btn[2].getWidth() / 2);
            y = frm.btn[2].getY();
            x1 = frm.btn[8].getX() + (frm.btn[8].getWidth() / 2);
            y1 = frm.btn[8].getY() + frm.btn[8].getHeight();
        } else if ((!frm.btn[0].getText().equals("")) && frm.btn[0].getText().equals(frm.btn[4].getText()) && frm.btn[4].getText().equals(frm.btn[8].getText())) {
            carattere = frm.btn[0].getText();
            x = frm.btn[0].getX();
            y = frm.btn[0].getY();
            x1 = frm.btn[8].getX() + frm.btn[8].getWidth();
            y1 = frm.btn[8].getY() + frm.btn[8].getHeight();
        } else if ((!frm.btn[2].getText().equals("")) && frm.btn[2].getText().equals(frm.btn[4].getText()) && frm.btn[4].getText().equals(frm.btn[6].getText())) {
            carattere = frm.btn[2].getText();
            x = frm.btn[2].getX() + frm.btn[2].getWidth();
            y = frm.btn[2].getY();
            x1 = frm.btn[6].getX();
            y1 = frm.btn[6].getY() + frm.btn[6].getHeight();
        }
        if (carattere.equals(String.valueOf(frm.character))) {
            frm.finito = true;
            frm.x = x;
            frm.x1 = x1;
            frm.y = y;
            frm.y1 = y1;

            try {
                out.writeBytes("#1");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (JButton buttons : frm.btn) {
                buttons.setEnabled(false);
            }
            SwingUtilities.invokeLater(() -> frm.bt_panel.repaint());
            JOptionPane.showMessageDialog(frm.frmClient, "You won");
            SwingUtilities.invokeLater(() -> frm.bt_panel.repaint());
        } else if (carattere.equals(String.valueOf(frm.character == 'X' ? 'O' : 'X'))) {
            frm.finito = true;
            frm.x = x;
            frm.x1 = x1;
            frm.y = y;
            frm.y1 = y1;
            for (JButton buttons : frm.btn) {
                buttons.setEnabled(false);
            }
            SwingUtilities.invokeLater(() -> frm.bt_panel.repaint());
            JOptionPane.showMessageDialog(frm.frmClient, "You lost");
            SwingUtilities.invokeLater(() -> frm.bt_panel.repaint());
        }
    }

}
