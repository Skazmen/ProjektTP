package Menu;

import Server.Enums.MessagesClient;
import Server.Enums.MessagesServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.awt.Color;
import java.awt.Font;

public class BoardGui extends JFrame implements ActionListener {
    private Scanner in;
    private PrintWriter out;
    private CountDownLatch sync = new CountDownLatch(1); //for 'sendToServer' to wait for 'out' to be inicjalized;
    private JButton surrenderButton;
    private JLabel backGroundLabel, stateLabel;
    private boolean first = true;

    BoardGui() {
        //połączenie z serwerem
        connectToServer();

        //wysłanie wiadomości
        sendToServer(MessagesClient.WAITING_FOR_GAME);
        sendToServer(MessagesClient.MADE_MOVE);
        sendToServer(MessagesClient.GIVE_UP_MOVE);
        sendToServer(MessagesClient.SURRENDER);

        setSize(1366, 768);
        setTitle("Go game - Loading");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //Back button
        surrenderButton = new JButton("leave");
        surrenderButton.setBounds(1180, 660, 180, 30);
        add(surrenderButton);
        surrenderButton.setForeground(Color.white);
        surrenderButton.setContentAreaFilled(false);
        surrenderButton.setToolTipText("Click here to leave session");
        surrenderButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        surrenderButton.addActionListener(this);
        setResizable(false);

        //background
        backGroundLabel = new JLabel(new ImageIcon("images/loading.jpg"));
        backGroundLabel.setOpaque(true);
        backGroundLabel.setBounds(0, 0, 1366, 768);
        add(backGroundLabel);

        stateLabel = new JLabel();
        stateLabel.setBounds(0, 0, 1366, 768);
        stateLabel.setOpaque(false);
        stateLabel.setHorizontalAlignment(JLabel.CENTER);
        add(stateLabel);

        //todo zaleznie od state odpowiednio wszytsko narysować

        //powiadomienie serwera przed zamknieciem
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                sendToServer(MessagesClient.CLOSE);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        remove(backGroundLabel);

        if (first) {
            backGroundLabel = new JLabel(new ImageIcon("images/loading.jpg"));
            backGroundLabel.setOpaque(true);
            backGroundLabel.setBounds(0, 0, 1366, 768);
        } else {
            backGroundLabel = new JLabel(new ImageIcon("images/loading.jpg"));
            backGroundLabel.setOpaque(true);
            backGroundLabel.setBounds(0, 0, 1366, 768);
        }

        add(backGroundLabel);
        first = !first;
        repaint();

        if (source == surrenderButton) {
            sendToServer(MessagesClient.SURRENDER);
            MenuGui menu = new MenuGui();
            menu.setLocation(this.getX(), this.getY());
            menu.setVisible(true);
            this.setVisible(false);
        }
    }

    private void connectToServer() { //pokaz mi miejsce  wktórym dostjaesz info z backendu że np gracz ywkonał ruch, albo w ktorym miejscu to sie wyswie
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (Socket socket = new Socket(InetAddress.getLocalHost(), 59898)) {
                    in = new Scanner(socket.getInputStream());
                    out = new PrintWriter(socket.getOutputStream(), true);
                    sync.countDown(); //sygnalize 'out' is initialized

                    while (true) {
                        MessagesServer serverAnswer = MessagesServer.valueOf(in.nextLine());
                        switch (serverAnswer) {
                            case SET_COLOR_BLACK:
                                System.out.println("Your color is Black");
								stateLabel.setText("Your color is Black");
								stateLabel.setOpaque(true);
								//todo -> nie pamiętam jak, zrób sleep na 4s i wtedy odekmentuj poniższą linię
								//stateLabel.setOpaque(false);

                                break;
                            case SET_COLOR_WHITE:
								stateLabel.setText("Your color is White");
								stateLabel.setOpaque(true);
								//todo -> nie pamiętam jak, zrób sleep na 4s i wtedy odekmentuj poniższą linię
								//stateLabel.setOpaque(false);

                                System.out.println("Your color is White");
                                break;
                            case WRONG_MOVE:
                            	stateLabel.setText("The move you tride to make is not allowed");
                            	stateLabel.setOpaque(true);
                            	//todo -> nie pamiętam jak, zrób sleep na 4s i wtedy odekmentuj poniższą linię
								//stateLabel.setOpaque(false);

                                System.out.println("The move you tride to make is not allowed");
                                break;
                            case UPDATE_BOARD:
								stateLabel.setText("Your move was good, here is updated board");
								stateLabel.setOpaque(true);
								//todo -> nie pamiętam jak, zrób sleep na 4s i wtedy odekmentuj poniższą linię
								//stateLabel.setOpaque(false);

                                System.out.println("Your move was good, here is updated board");
                                break;
                            case END_GAME:
								stateLabel.setText("The game has ended and PlayerX won");
								stateLabel.setOpaque(true);
								//todo -> nie pamiętam jak, zrób sleep na 4s i wtedy odekmentuj poniższą linię
								//stateLabel.setOpaque(false);

                                System.out.println("The game has ended and PlayerX won");
                                break;
                        }
                    }
                } catch (ConnectException | UnknownHostException e) {
                    System.out.println("Cannot connect to  server - run server first");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendToServer(final MessagesClient message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sync.await(); //wait for 'out' to be initialized
                    out.println(message.toString());
                } catch (NullPointerException | InterruptedException e) {
                    System.out.println("Didn't connect to server yet");
                }
            }
        }).start();
    }
}
