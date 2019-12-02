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
    private JButton surrenderButton, giveUpTurn, confrimMove;
    private JLabel backGroundLabel, stateLabel, backGroundLabel1;
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
        setTitle("Go game");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //Back button
        surrenderButton = new JButton("surrender");
        surrenderButton.setBounds(1180, 660, 180, 30);
        add(surrenderButton);
        surrenderButton.setForeground(Color.white);
        surrenderButton.setContentAreaFilled(false);
        surrenderButton.setToolTipText("Click here to surrender session");
        surrenderButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        surrenderButton.addActionListener(this);

        giveUpTurn = new JButton("Give up turn");
        giveUpTurn.setBounds(950, 400, 200, 30);
        add(giveUpTurn);
        giveUpTurn.setForeground(Color.white);
        giveUpTurn.setContentAreaFilled(false);
        giveUpTurn.setToolTipText("Click here to give up move");
        giveUpTurn.setFont(new Font("SansSerif", Font.BOLD, 20));
        giveUpTurn.addActionListener(this);

        confrimMove = new JButton("Confrim move");
        confrimMove.setBounds(950, 300, 200, 30);
        add(confrimMove);
        confrimMove.setForeground(Color.white);
        confrimMove.setContentAreaFilled(false);
        confrimMove.setToolTipText("Click here to confrim move");
        confrimMove.setFont(new Font("SansSerif", Font.BOLD, 20));
        confrimMove.addActionListener(this);

        backGroundLabel1 = new JLabel(new ImageIcon("images/tlo.jpg"));
        backGroundLabel1.setOpaque(true);
        backGroundLabel1.setBounds(0, 0, 1366, 768);
        add(backGroundLabel1);

        stateLabel = new JLabel();
        stateLabel.setBounds(200, 30, 500, 500);
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
        remove(backGroundLabel1);

        if (first) {
            backGroundLabel1 = new JLabel(new ImageIcon("images/tlo.jpg"));
            backGroundLabel1.setOpaque(true);
            backGroundLabel1.setBounds(0, 0, 1366, 768);
        } else {
            backGroundLabel1 = new JLabel(new ImageIcon("images/tlo.jpg"));
            backGroundLabel1.setOpaque(true);
            backGroundLabel1.setBounds(0, 0, 1366, 768);
        }

        add(backGroundLabel1);
        first = !first;
        repaint();

        if (source == surrenderButton) {
            sendToServer(MessagesClient.SURRENDER);
            MenuGui menu = new MenuGui();
            menu.setLocation(this.getX(), this.getY());
            menu.setVisible(true);
            this.setVisible(false);
        }

        if (source == giveUpTurn) {
            sendToServer(MessagesClient.GIVE_UP_MOVE);
        }

        if (source == confrimMove) {
            sendToServer(MessagesClient.MADE_MOVE);
        }


    }

    private void connectToServer() {
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
                                Thread.sleep(4000);
								stateLabel.setOpaque(false);

                                break;
                            case SET_COLOR_WHITE:
								stateLabel.setText("Your color is White");
								stateLabel.setOpaque(true);
                                Thread.sleep(4000);
								stateLabel.setOpaque(false);

                                System.out.println("Your color is White");
                                break;
                            case WRONG_MOVE:
                            	stateLabel.setText("The move you tride to make is not allowed");
                            	stateLabel.setOpaque(true);
                                Thread.sleep(4000);
								stateLabel.setOpaque(false);

                                System.out.println("The move you tride to make is not allowed");
                                break;
                            case UPDATE_BOARD:
								stateLabel.setText("Your move was good, here is updated board");
								stateLabel.setOpaque(true);
                                Thread.sleep(4000);
								stateLabel.setOpaque(false);

                                System.out.println("Your move was good, here is updated board");
                                break;
                            case END_GAME:
								stateLabel.setText("The game has ended and PlayerX won");
								stateLabel.setOpaque(true);
                                Thread.sleep(4000);
								stateLabel.setOpaque(false);

                                System.out.println("The game has ended and PlayerX won");
                                break;
                        }
                    }
                } catch (ConnectException | UnknownHostException e) {
                    System.out.println("Cannot connect to  server - run server first");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
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
