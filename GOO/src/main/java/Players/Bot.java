package Players;

import Server.Enums.MessagesClient;
import Server.Enums.MessagesServer;
import Server.ExtractedGrid;
import Menu.GameBoardPanel;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Bot implements Player {

    private Scanner in;         // scanner na którym serwer czyta info od klienta
    private PrintWriter out;    // writer na którym serwer wysyła info do klienta
    private Scanner ClientIn;       // scanner na którym bot czyta info z serwera
    private PrintWriter ClientOut;  // writer na którym bot wysyła info na serwer
    private CountDownLatch sync = new CountDownLatch(1); //for 'sendToServer' to wait for 'ClientOut' to be inicjalized;
    private String nick;
    private Color color;
    private boolean move = false;
    private int size;
    GameBoardPanel board;

    public Bot(int size) {
        this.nick = "Bot_" + ((int) Math.floor(1 + Math.random() * 999));
        this.size = size;
        activate();
    }

    private void activate(){
        connectToServer();
        board = new GameBoardPanel(size);
        sendToServer(MessagesClient.WAITING_FOR_GAME_, "");
    }

    private void connectToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (Socket socket = new Socket(InetAddress.getLocalHost(), 59898)) {
                    ClientIn = new Scanner(socket.getInputStream());
                    ClientOut = new PrintWriter(socket.getOutputStream(), true);
                    sync.countDown(); //sygnalize 'out' is initialized

                    while (true) {
                        String serverAnswer = ClientIn.nextLine();
                        MessagesServer messagesServer = MessagesServer.valueOf(serverAnswer.substring(0, 17));
                        String restOfAnswer = serverAnswer.substring(17);
                        switch (messagesServer) {
                            case SET_COLOR_BLACK__:
                                move = true;
                                makeMove();
                                break;
                            case SET_COLOR_WHITE__:
                                move = false;
                                break;
                            case WRONG_MOVE_______:
                                makeMove();
                                break;
                            case UPDATE_BOARD_____:
                                move = !move;
                                if (move) {
                                    makeMove();
                                }
                                break;
                            default: break;
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

    private void makeMove() {
        int[] position = new int[2];
        position[0] = (int) Math.floor( Math.random() * 8 );
        position[1] = (int) Math.floor( Math.random() * 8 );
        sendToServer(MessagesClient.MADE_MOVE________, (position[0] + "/" + position[1]));
    }

    private void sendToServer(final MessagesClient message, final String additionalInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sync.await(); //wait for 'out' to be initialized
                    ClientOut.println((message.toString() + additionalInfo));
                } catch (NullPointerException | InterruptedException e) {
                    System.out.println("Didn't connect to server yet");
                }
            }
        }).start();
    }

    @Override
    public void setInputOutputStream(ServerSocket listener, Scanner sc, PrintWriter pw) {
        try {
            Socket socket = listener.accept();
            in = new  Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // poniższe podobne do HumanPlayer.class

    @Override
    public void setColor(Color c){
        this.color = c;
    }

    @Override
    public Scanner getInputStream(){
        return this.in;
    }
    @Override
    public PrintWriter getOutputStream(){
        return this.out;
    }
    @Override
    public String getNickname(){
        return this.nick;
    }
    @Override
    public Color getColor(){
        return this.color;
    }
}