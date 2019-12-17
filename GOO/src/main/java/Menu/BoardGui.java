package Menu;

import Game.GameBoard;
import Server.Enums.MessagesClient;
import Server.Enums.MessagesServer;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class BoardGui extends JFrame {
    private Scanner in;
    private PrintWriter out;
    private CountDownLatch sync = new CountDownLatch(1); //for 'sendToServer' to wait for 'out' to be inicjalized;
    private JButton surrenderButton, skipButton;
    private JLabel backGroundLabel, stateLabel;
    GameBoard board;

    BoardGui(UserSettings uSet) {

        //połączenie z serwerem
        connectToServer(uSet);

        //stworzenie wyglądu
        createGUI(uSet.getSize());

        //wysłanie wiadomości
        sendToServer(MessagesClient.WAITING_FOR_GAME);
        //sendToServer(MessagesClient.MADE_MOVE);
        //sendToServer(MessagesClient.GIVE_UP_MOVE);
        //sendToServer(MessagesClient.SURRENDER);

        //powiadomienie serwera przed zamknieciem
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                sendToServer(MessagesClient.CLOSE);
            }
        });
    }

    private void createGUI(int size) {
        setSize(1366, 768);
        setTitle("Go game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //skip
        skipButton = new JButton("Skip Move");
        skipButton.setBounds(1000, 560, 180, 30);
        getContentPane().add(skipButton);
        skipButton.setForeground(Color.white);
        skipButton.setContentAreaFilled(false);
        skipButton.setToolTipText("Click here to leave session");
        skipButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sendToServer(MessagesClient.GIVE_UP_MOVE);
            }
        });

        //surrender
        surrenderButton = new JButton("Surrender");
        surrenderButton.setBounds(1000, 660, 180, 30);
        getContentPane().add(surrenderButton);
        surrenderButton.setForeground(Color.white);
        surrenderButton.setContentAreaFilled(false);
        surrenderButton.setToolTipText("Click here to go session");
        surrenderButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        surrenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sendToServer(MessagesClient.SURRENDER);
            }
        });

        //board to show moves
        board = new GameBoard(size);
        board.setBounds(150,40,650,650);
        board.setVisible(false);
        getContentPane().add(board);
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(!stateLabel.isVisible()){
                    int[] position = board.makeMove(e);
                    notification("Verification",1000);
                }
            }
        });

        //background
        backGroundLabel = new JLabel(new ImageIcon("images/loading.jpg"));
        backGroundLabel.setOpaque(true);
        backGroundLabel.setBounds(0, 0, 1366, 768);
        getContentPane().add(backGroundLabel);

        //popup
        stateLabel = new JLabel("Waiting for opponent...") {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
            @Override public boolean isOpaque() {
                return false;
            }
        };
        stateLabel.setBackground(new Color(0x88888800, true));
        stateLabel.setForeground(Color.WHITE);
        stateLabel.setFont(stateLabel.getFont().deriveFont(30f));
        stateLabel.setHorizontalAlignment(JLabel.CENTER);
        stateLabel.setVerticalAlignment(JLabel.CENTER);
        stateLabel.setBounds(0, 0, 1366, 768);
        getLayeredPane().add(stateLabel, JLayeredPane.POPUP_LAYER);
        stateLabel.setVisible(true);
    }

    private void connectToServer(final UserSettings uSet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (Socket socket = new Socket(InetAddress.getLocalHost(), 59898)) {
                    in = new Scanner(socket.getInputStream());
                    out = new PrintWriter(socket.getOutputStream(), true);

                    ObjectMapper objectMapper = new ObjectMapper();
                    String settings = objectMapper.writeValueAsString(uSet);
                    out.println(settings);

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
                    // TODO co zrobic gdy serwer sie nagle rozłaczy
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

    private synchronized void notification(final String info, final int time){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(info);
                    stateLabel.setText(info);
                    stateLabel.setVisible(true);
					Thread.sleep(time);
					stateLabel.setVisible(false);
					board.setVisible(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
