package Server;

import Menu.UserSettings;
import Server.Enums.MessagesClient;
import Server.Enums.MessagesServer;
import Server.Enums.Players;

import java.awt.*;
import java.io.PrintWriter;
import java.util.Scanner;

class Board {
    private Player player1;
    private Player player2;
    private int size;
    private boolean multiplayer;
    private Game game;

    private static Board instance;

    synchronized static Board getInstance() {
        //Double-checkeed looking
        Board temp = instance;
        if (temp == null) {
            synchronized (Board.class) {
                if (instance == null) {
                    instance = new Board();
                }
                temp = instance;
            }
        }
        return temp;
    }

    void addClient(Scanner sc, PrintWriter pw, UserSettings uSet) {
        // add first player
        if (player1 == null){
            player1 = new Player(uSet.getNick());
            player1.setInputStream(sc);
            player1.setOutputStream(pw);
            this.size = uSet.getSize();
            this.multiplayer = (uSet.getPlayersCount() == 2);
            listenForPlayer(player1);

        // add second player if doesnt already existst and both chosen multiplayer mode and same board size
        } else if (multiplayer && player2==null && uSet.getPlayersCount()==2 && uSet.getSize()==size){
            player2 = new Player(uSet.getNick());
            player2.setInputStream(sc);
            player2.setOutputStream(pw);
            listenForPlayer(player2);
        }
    }

    private void listenForPlayer(final Player player) {
        new Thread(new Runnable() {
            boolean listen = true;
            Scanner scanner = player.getInputStream();

            @Override
            public void run() {
                while (listen) {
                    MessagesClient clientMessage = MessagesClient.valueOf(scanner.nextLine());
                    switch (clientMessage) {
                        case WAITING_FOR_GAME:
                            System.out.println(player.getNickname() + " is waiting for another player");
                            checkGameCreation();
                            break;
                        case MADE_MOVE:
                            System.out.println(player.getNickname() + " made move");
                            sendToClient(player, MessagesServer.WRONG_MOVE);
                            sendToClient(player, MessagesServer.UPDATE_BOARD);

                            break;
                        case GIVE_UP_MOVE:
                            System.out.println(player.getNickname() + " decided not to move this time");
                            break;
                        case SURRENDER:
                            System.out.println(player.getNickname() + " surrendered the game");
                            sendToClient(player1, MessagesServer.END_GAME);
                            sendToClient(player2, MessagesServer.END_GAME);

                            break;
                        case CLOSE:
                            // stops listening to client when he closes his window
                            listen = false;
                            System.out.println(player.getNickname() + " disconnected");
                            //TODO przypadek gdy pierwszy sie rozlaczy zanim drugi sie polaczy
                            break;
                    }
                }
            }
        }).start();
    }

    private void checkGameCreation() {
        if(player1 != null && player2 != null){
            if(Math.random() >  0.5){
                sendToClient(player1, MessagesServer.SET_COLOR_BLACK);
                sendToClient(player2, MessagesServer.SET_COLOR_WHITE);
                player1.setColor(Color.BLACK);
                player2.setColor(Color.WHITE);
            } else {
                sendToClient(player1, MessagesServer.SET_COLOR_WHITE);
                sendToClient(player2, MessagesServer.SET_COLOR_BLACK);
                player1.setColor(Color.WHITE);
                player2.setColor(Color.BLACK);
            }
            game = new Game(size);
            System.out.println("Created new game");
        }
    }


    private void sendToClient(final Player player, final MessagesServer message) {
        if(player.getOutputStream() != null){
            player.getOutputStream().println(message);
        }
        else System.out.println("error");
    }
}