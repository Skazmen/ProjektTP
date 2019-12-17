package Server;

import Menu.UserSettings;
import Server.Enums.MessagesClient;
import Server.Enums.MessagesServer;
import Server.Enums.Players;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            String prevGrid = "";

            @Override
            public void run() {
                while (listen) {
                    String clientAnswer = scanner.nextLine();
                    MessagesClient messagesClient = MessagesClient.valueOf(clientAnswer.substring(0,17));
                    String restOfAnswer = clientAnswer.substring(17);
                    switch (messagesClient) {
                        case WAITING_FOR_GAME_:
                            System.out.println(player.getNickname() + " is waiting for another player");
                            checkGameCreation();
                            break;
                        case MADE_MOVE________:
                            System.out.println(player.getNickname() + " made move on position " + restOfAnswer);
                            if(game.checkMove(player, restOfAnswer)){
                                String grid = game.extractGrid().toString();
                                prevGrid = grid;
                                sendToClient(player1, MessagesServer.UPDATE_BOARD_____,grid);
                                sendToClient(player2, MessagesServer.UPDATE_BOARD_____,grid);
                            } else {
                                sendToClient(player, MessagesServer.WRONG_MOVE_______,"");
                            }
                            break;
                        case GIVE_UP_MOVE_____:
                            System.out.println(player.getNickname() + " decided not to move this time");
                            sendToClient(player1, MessagesServer.UPDATE_BOARD_____,prevGrid);
                            sendToClient(player2, MessagesServer.UPDATE_BOARD_____,prevGrid);
                            break;
                        case SURRENDER________:
                            System.out.println(player.getNickname() + " surrendered the game");
                            sendToClient(player1, MessagesServer.END_GAME_________,player.getNickname());
                            sendToClient(player2, MessagesServer.END_GAME_________,player.getNickname());

                            break;
                        case CLOSE____________:
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
                sendToClient(player1, MessagesServer.SET_COLOR_BLACK__,"");
                sendToClient(player2, MessagesServer.SET_COLOR_WHITE__,"");
                player1.setColor(Color.BLACK);
                player2.setColor(Color.WHITE);
            } else {
                sendToClient(player1, MessagesServer.SET_COLOR_WHITE__,"");
                sendToClient(player2, MessagesServer.SET_COLOR_BLACK__,"");
                player1.setColor(Color.WHITE);
                player2.setColor(Color.BLACK);
            }
            game = new Game(size);
            System.out.println("Created new game");
        }
    }


    private void sendToClient(final Player player, final MessagesServer message, final String additionalInfo) {
        if(player.getOutputStream() != null){
            String mess = message.toString() + additionalInfo;
            player.getOutputStream().println(mess);
        }
        else System.out.println("error");
    }
}