package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;

public class MainClient {

    protected Socket sck;
    protected final JFrame frmClient;
    protected final JButton[] btn = new JButton[9]; //array di bottoni
    protected JPanel t_panel = new JPanel(); //panel per il login
    protected JPanel opp_panel = new JPanel();
    protected JPanel bt_panel = new JPanel() {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (finito) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(7));
                g2.setColor(Color.BLACK);
                g2.drawLine(x, y, x1, y1);
                g2.dispose();
            }
        }

        @Override
        public boolean isOptimizedDrawingEnabled() {
            return false;
        }
    }; //panel per i bottoni
    protected JLabel ip = new JLabel("IP Address");
    protected JLabel port = new JLabel("Port");
    protected JLabel nickname = new JLabel("Nickname");
    protected JTextField IPAddress = new JTextField();
    protected JTextField Port = new JTextField();
    protected JTextField name = new JTextField();
    protected JButton btnConnect = new JButton("Connect");
    protected char character;
    protected threadClient thread;
    protected boolean finito = false;
    protected int x, y, x1, y1;
    protected JLabel opp = new JLabel();

    public MainClient() {
        frmClient = new JFrame();
        frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ferma tutto solo alla chiusura dell'applicazione
        frmClient.setSize(600, 600); //risoluzione
        frmClient.setLocationRelativeTo(null); //si avvia al centro dello schermo
        frmClient.setResizable(false); //non si può ridimensionare la finestra
        frmClient.setTitle("TicTacToe"); //nome applicazione

        t_panel.setLayout(new GridLayout(2, 4, 3, 3));
        t_panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        opp_panel.setLayout(new GridLayout(1, 1));
        opp_panel.add(opp);
        opp.setHorizontalAlignment(SwingConstants.CENTER);
        opp.setVerticalAlignment(SwingConstants.CENTER);

        t_panel.add(ip);
        t_panel.add(port); //aggiungo i label per il login nel panel sopra
        t_panel.add(nickname);
        t_panel.add(new JLabel(""));
        t_panel.add(IPAddress);
        t_panel.add(Port); //aggiungo i textfield per il login nel panel sopra
        t_panel.add(name);
        t_panel.add(btnConnect);

        btnConnect.addActionListener(e -> {
            connect();
        });

        bt_panel.setLayout(new GridLayout(3, 3, 3, 3));
        bt_panel.setBackground(Color.BLACK);
        for (int i = 0; i < 9; i++) {
            btn[i] = new JButton();
            btn[i].setBackground(Color.WHITE);
            btn[i].setFont(new Font("Arial", Font.CENTER_BASELINE, 120));

            int finalI = i;
            btn[i].addActionListener(e -> {
                btn[finalI].setText(character + "");
                btn[finalI].setEnabled(false);
                thread.send(String.valueOf(finalI));
                for (JButton buttons : btn) {
                    buttons.setEnabled(false);
                }
                thread.isTris();
            });
            int finalI1 = i;
            btn[i].setUI(new MetalButtonUI() {
                protected Color getDisabledTextColor() {
                    return btn[finalI1].getText().equals(String.valueOf(character)) ? Color.GREEN : Color.RED;
                }
            });
            btn[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!btn[finalI].isEnabled())
                        return;
                    btn[finalI].setText(character + "");
                    btn[finalI].setForeground(Color.gray);
                    bt_panel.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!btn[finalI].isEnabled())
                        return;
                    btn[finalI].setText("");
                    bt_panel.repaint();
                }
            });
            btn[i].setEnabled(false);
            bt_panel.add(btn[i]);
        }

        frmClient.add(t_panel, BorderLayout.NORTH);
        frmClient.add(bt_panel);
        frmClient.add(opp_panel, BorderLayout.SOUTH);

        frmClient.setVisible(true); //rendo visibile il frame
    }

    public static void main(String[] args) {
        MainClient frame = new MainClient();
    }

    public void connect() {
        String ip = IPAddress.getText();
        int port = Integer.parseInt(Port.getText());
        String n = name.getText();
        IPAddress.setEnabled(false);
        Port.setEnabled(false);
        name.setEnabled(false);
        btnConnect.setEnabled(false);
        if (sck != null)
            return;
        try {
            sck = new Socket(ip, port);
            thread = new threadClient(sck, this);
            thread.start();
            thread.send(n);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void isPlayer(String a) {
        if (a.equals("X")) {
            JOptionPane.showMessageDialog(frmClient, "Player 1: " + a);
            for (JButton button : btn) {
                button.setEnabled(true);
            }
            character = 'X';
        } else if (a.equals("O")) {
            for (JButton buttons : btn) {
                buttons.setEnabled(false);
            }
            JOptionPane.showMessageDialog(frmClient, "Player 2: " + a);
            character = 'O';
        }
    }
}
