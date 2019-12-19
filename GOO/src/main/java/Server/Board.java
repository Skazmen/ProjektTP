package Server;

import Players.Bot;
import Menu.UserSettings;
import Players.HumanPlayer;
import Players.Player;
import Server.Enums.MessagesClient;
import Server.Enums.MessagesServer;
import Rules.Game;

import java.awt.*;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.System.exit;

class Board {
    private Player player1;
    private Player player2;
    private int size;
    private boolean multiplayer;
    private Game game;
    private boolean listen = true;
    String prevGrid = "";
    boolean prevGiveUp = false;

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

    void addClient(ServerSocket listener, Scanner sc, PrintWriter pw, UserSettings uSet) {
        // add first player
        if (player1 == null) {
            player1 = new HumanPlayer(uSet.getNick());
            player1.setInputOutputStream(listener,sc,pw);
            this.size = uSet.getSize();
            this.multiplayer = (uSet.getPlayersCount() == 2);
            listenForPlayer(player1);

            // add bot
            if(!multiplayer) {
                player2 = new Bot(size);
                player2.setInputOutputStream(listener,sc,pw);
                listenForPlayer(player2);
            }

        // add second player if doesnt already existst and both chosen multiplayer mode and same board size
        } else if (multiplayer && player2 == null && uSet.getPlayersCount() == 2 && uSet.getSize() == size) {
            player2 = new HumanPlayer(uSet.getNick());
            player2.setInputOutputStream(listener,sc,pw);
            listenForPlayer(player2);
        }
    }

    private void listenForPlayer(final Player player) {
        new Thread(new Runnable() {
            Scanner scanner = player.getInputStream();

            @Override
            public void run() {
                try {
                    while (listen) {
                        String clientAnswer = scanner.nextLine();
                        MessagesClient messagesClient = MessagesClient.valueOf(clientAnswer.substring(0, 17));
                        String restOfAnswer = clientAnswer.substring(17);
                        switch (messagesClient) {
                            case WAITING_FOR_GAME_:
                                System.out.println(player.getNickname() + " is waiting for another player");
                                checkGameCreation();
                                break;
                            case MADE_MOVE________:
                                prevGiveUp = false;
                                System.out.println(player.getNickname() + " made move on position " + restOfAnswer);
                                if (game.checkMove(player, restOfAnswer)) {
                                    game.updateBoard(player, restOfAnswer);
                                    String grid = game.extractGrid().toString();
                                    sendToClient(player1, MessagesServer.UPDATE_BOARD_____, grid);
                                    sendToClient(player2, MessagesServer.UPDATE_BOARD_____, grid);
                                    prevGrid = grid;
                                } else {
                                    sendToClient(player, MessagesServer.WRONG_MOVE_______, "");
                                }
                                if (game.impossibleToMove()) {
                                    sendToClient(player1, MessagesServer.END_GAME_________, "impossible");
                                    sendToClient(player2, MessagesServer.END_GAME_________, "impossible");
                                }
                                break;
                            case GIVE_UP_MOVE_____:
                                if (prevGiveUp) {
                                    sendToClient(player1, MessagesServer.END_GAME_________, "fin");
                                    sendToClient(player2, MessagesServer.END_GAME_________, "fin");
                                } else {
                                    System.out.println(player.getNickname() + " decided not to move this time");
                                    sendToClient(player1, MessagesServer.UPDATE_BOARD_____, prevGrid);
                                    sendToClient(player2, MessagesServer.UPDATE_BOARD_____, prevGrid);
                                    prevGiveUp = true;
                                }
                                break;
                            case SURRENDER________:
                                System.out.println(player.getNickname() + " surrendered the game");
                                sendToClient(player1, MessagesServer.END_GAME_________, player.getNickname());
                                sendToClient(player2, MessagesServer.END_GAME_________, player.getNickname());

                                break;
                            case CLOSE____________:
                                // stops listening to client when he closes his window
                                listen = false;
                                System.out.println(player.getNickname() + " disconnected");
                                sendToClient(player1, MessagesServer.END_GAME_________, player.getNickname());
                                sendToClient(player2, MessagesServer.END_GAME_________, player.getNickname());
                                player1 = null;
                                player2 = null;
                                exit(0);
                                break;
                            case CHECK_CONNECTION_:
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(new Date());
                                String time = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.HOUR_OF_DAY) + "/" + cal.get(Calendar.MINUTE) + "/" + cal.get(Calendar.SECOND);
                                sendToClient(player, MessagesServer.CONNECTION_RES___, time);
                                break;
                        }

                    }
                } catch (NoSuchElementException e){
                    sendToClient(player1, MessagesServer.END_GAME_________, "server");
                    sendToClient(player2, MessagesServer.END_GAME_________, "server");
                }
            }
        }).start();
    }

    private void checkGameCreation() {
        if (player1 != null && player2 != null) {
            if (Math.random() > 0.5) {
                sendToClient(player1, MessagesServer.SET_COLOR_BLACK__, "");
                sendToClient(player2, MessagesServer.SET_COLOR_WHITE__, "");
                player1.setColor(Color.BLACK);
                player2.setColor(Color.WHITE);
            } else {
                sendToClient(player1, MessagesServer.SET_COLOR_WHITE__, "");
                sendToClient(player2, MessagesServer.SET_COLOR_BLACK__, "");
                player1.setColor(Color.WHITE);
                player2.setColor(Color.BLACK);
            }
            game = new Game(size);
            System.out.println("Created new game");
        }
    }


    private void sendToClient(final Player player, final MessagesServer message, final String additionalInfo) {
        if (player != null && player.getOutputStream() != null) {
            String mess = message.toString() + additionalInfo;
            player.getOutputStream().println(mess);
        }
    }
}