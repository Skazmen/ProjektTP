package Menu;

import Game.Start;
import Server.Enums.MessagesClient;
import Server.Enums.MessagesServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class BoardGui extends JFrame implements ActionListener {
    private Scanner in;
    private PrintWriter out;
    private CountDownLatch sync = new CountDownLatch(1); //for 'sendToServer' to wait for 'out' to be inicjalized;
    private JButton surrenderButton, goButton;
    private JLabel backGroundLabel, stateLabel;
    private boolean first = true;
    private String boardSize;
    public static int SIZE = 9;
    int test = 0;

    BoardGui() {
        //połączenie z serwerem
        //TODO wysłac rozmiar planszy - String "9x9" "13x13" albo "19x19"
        // tymczasowo "9x9"
        boardSize = "9x9";

        if (boardSize == "9x9") {
            SIZE = 9;
        }
        connectToServer(boardSize);
        this.setBackground(Color.ORANGE);

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
        goButton = new JButton("go GO");
        goButton.setBounds(1180, 560, 180, 30);
        add(goButton);
        goButton.setForeground(Color.white);
        goButton.setContentAreaFilled(false);
        goButton.setToolTipText("Click here to leave session");
        goButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        goButton.addActionListener(this);
        setResizable(false);


        surrenderButton = new JButton("Leave");
        surrenderButton.setBounds(1180, 660, 180, 30);
        add(surrenderButton);
        surrenderButton.setForeground(Color.white);
        surrenderButton.setContentAreaFilled(false);
        surrenderButton.setToolTipText("Click here to go session");
        surrenderButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        surrenderButton.addActionListener(this);
        setResizable(false);

        //background
        backGroundLabel = new JLabel(new ImageIcon("images/loading.jpg"));
        backGroundLabel.setOpaque(true);
        backGroundLabel.setBounds(0, 0, 1366, 768);
        add(backGroundLabel);


        /*stateLabel = new JLabel("INFO", SwingConstants.CENTER);
        stateLabel.setSize(1366, 768);
        stateLabel.setFont(new Font("Sans-Serif", Font.BOLD, 30));
        stateLabel.setForeground(Color.red);

        add(stateLabel);

        setComponentZOrder(surrenderButton, 0);
		setComponentZOrder(stateLabel, 1);
		setComponentZOrder(backGroundLabel, 2);
	    */

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
            backGroundLabel = new JLabel(new ImageIcon("images/tlo.jpg"));
            backGroundLabel.setOpaque(true);
            backGroundLabel.setBounds(0, 0, 1366, 768);
        } else {
            backGroundLabel = new JLabel(new ImageIcon("images/tlo.jpg"));
            backGroundLabel.setOpaque(true);
            backGroundLabel.setBounds(0, 0, 1366, 768);
        }

        add(backGroundLabel);
        first = !first;
        repaint();

        if (source == goButton) {
            new Start().init();
            this.setVisible(false);
        }

        if (source == surrenderButton) {
            sendToServer(MessagesClient.SURRENDER);
            MenuGui menu = new MenuGui();
            menu.setLocation(this.getX(), this.getY());
            menu.setVisible(true);
            this.setVisible(false);
        }
    }

    private void connectToServer(final String boardSize) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (Socket socket = new Socket(InetAddress.getLocalHost(), 59898)) {
                    in = new Scanner(socket.getInputStream());
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(boardSize);
                    sync.countDown(); //sygnalize 'out' is initialized

                    while (true) {
                        MessagesServer serverAnswer = MessagesServer.valueOf(in.nextLine());
                        switch (serverAnswer) {
                            case SET_COLOR_BLACK:
                                notification("Your color is Black", 4000);
                                break;
                            case SET_COLOR_WHITE:
                                notification("Your color is White", 4000);
                                break;
                            case WRONG_MOVE:
                                notification("The move you tried to make is not allowed", 2000);
                                break;
                            case UPDATE_BOARD:
                                notification("Your move was good, here is updated board", 1000);
                                //TODO zczytac nową planszę i ją narysować
                                break;
                            case END_GAME:
                                notification("The game has ended and PlayerX won", 15000);
								//TODO zakonczyc grę
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

    private void notification(final String info, final int time){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(info);
                    stateLabel.setText(info);
                    //TODO ustawic label zeby był na wieszchu i wrzystko zasłaniał, a potem się schował do tyłu
                    // https://stackoverflow.com/questions/4229638/z-order-on-java-swing-components/4229661
                    // setOpacity nie działa bo potem nie chce znikać owiadomienie,
                    // próbowałem setComponentZOrder działa jak na razie całkiem ładnie ale nwm czy zasłania wszystko i zapobiega klikaniu,
                    // ewentualnie możesz spróbować z JLayeredPane, tak jak w linku powyżej

//					setComponentZOrder(surrenderButton, 0);
//					setComponentZOrder(stateLabel, 1);
//					setComponentZOrder(backGroundLabel, 2);
//                    Thread.sleep(time);
//					setComponentZOrder(surrenderButton, 0);
//					setComponentZOrder(backGroundLabel, 1);
//					setComponentZOrder(stateLabel, 2);

					setComponentZOrder(stateLabel, 0);
					setComponentZOrder(surrenderButton, 1);
					setComponentZOrder(backGroundLabel, 2);
					Thread.sleep(time);
					setComponentZOrder(surrenderButton, 0);
					setComponentZOrder(backGroundLabel, 1);
					setComponentZOrder(stateLabel, 2);

					stateLabel.setText("end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
