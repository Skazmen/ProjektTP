package Menu;

//import GameBoardPanel;

import Server.Enums.MessagesClient;
import Server.Enums.MessagesServer;
import Server.ExtractedGrid;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
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
    private JLabel stateLabel;
    GameBoardPanel board;
    boolean move = false;

    BoardGui(UserSettings uSet) {

        //połączenie z serwerem
        connectToServer(uSet);

        //stworzenie wyglądu
        createGUI(uSet.getSize());

        //wysłanie wiadomości
        sendToServer(MessagesClient.WAITING_FOR_GAME_, "");

        //powiadomienie serwera przed zamknieciem
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                sendToServer(MessagesClient.CLOSE____________, "");
            }
        });
    }

    private void createGUI(int size) {
        setSize(1366, 768);
        setTitle("Go game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //skip
        JButton skipButton = new JButton("Skip Move");
        skipButton.setBounds(1000, 560, 180, 30);
        getContentPane().add(skipButton);
        skipButton.setForeground(Color.white);
        skipButton.setContentAreaFilled(false);
        skipButton.setToolTipText("Click here to skip move");
        skipButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (move) sendToServer(MessagesClient.GIVE_UP_MOVE_____, "");
            }
        });


        //surrender
        JButton surrenderButton = new JButton("Surrender");
        surrenderButton.setBounds(1000, 660, 180, 30);
        getContentPane().add(surrenderButton);
        surrenderButton.setForeground(Color.white);
        surrenderButton.setContentAreaFilled(false);
        surrenderButton.setToolTipText("Click here to surrender session");
        surrenderButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        surrenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sendToServer(MessagesClient.SURRENDER________, "");
            }
        });

        //board to show moves
        board = new GameBoardPanel(size);
        board.setBounds(150, 40, 650, 650);
        board.setVisible(false);
        getContentPane().add(board);
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (!stateLabel.isVisible()) {
                    int[] position = board.makeMove(e);
                    sendToServer(MessagesClient.MADE_MOVE________, (position[0] + "/" + position[1]));
                    untimedNotification("Verification");
                }
            }
        });

        //background
        JLabel backGroundLabel = new JLabel(new ImageIcon("images/loading.jpg"));
        backGroundLabel.setOpaque(true);
        backGroundLabel.setBounds(0, 0, 1366, 768);
        getContentPane().add(backGroundLabel);

        //popup
        stateLabel = new JLabel("Waiting for opponent...") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }

            @Override
            public boolean isOpaque() {
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
                        String serverAnswer = in.nextLine();
                        MessagesServer messagesServer = MessagesServer.valueOf(serverAnswer.substring(0, 17));
                        String restOfAnswer = serverAnswer.substring(17);
                        switch (messagesServer) {
                            case SET_COLOR_BLACK__:
                                untimedNotification("Your color is Black");
                                move = true;
                                Thread.sleep(3000);
                                timedNotification("Your move", 1000);
                                break;
                            case SET_COLOR_WHITE__:
                                untimedNotification("Your color is White");
                                move = false;
                                Thread.sleep(3000);
                                untimedNotification("Wait for opponent move");
                                break;
                            case WRONG_MOVE_______:
                                timedNotification("The move you tried to make is not allowed", 2000);
                                break;
                            case UPDATE_BOARD_____:
                                if(!restOfAnswer.isEmpty()) {
                                    board.update(ExtractedGrid.fromString(restOfAnswer));
                                }
                                move = !move;
                                if (move) {
                                    endNotivication();
                                    timedNotification("Your move", 1000);
                                } else {
                                    untimedNotification("Wait for opponent move");
                                }
                                break;
                            case END_GAME_________:
                                untimedNotification("The game has ended and " + restOfAnswer + " lost");
                                //TODO zakonczyc grę
                                break;
                        }
                    }
                    // TODO co zrobic gdy serwer sie nagle rozłaczy
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

    private void sendToServer(final MessagesClient message, final String additionalInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sync.await(); //wait for 'out' to be initialized
                    out.println((message.toString() + additionalInfo));
                } catch (NullPointerException | InterruptedException e) {
                    System.out.println("Didn't connect to server yet");
                }
            }
        }).start();
    }

    private synchronized void timedNotification(final String info, final int time) {
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

    private synchronized void untimedNotification(final String info) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(info);
                stateLabel.setText(info);
                stateLabel.setVisible(true);
            }
        }).start();
    }

    private void endNotivication() {
        stateLabel.setVisible(false);
    }
}
