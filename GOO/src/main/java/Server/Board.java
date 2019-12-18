package Server;

import Menu.UserSettings;
import Server.Enums.MessagesClient;
import Server.Enums.MessagesServer;

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
        if (player1 == null) {
            player1 = new Player(uSet.getNick());
            player1.setInputStream(sc);
            player1.setOutputStream(pw);
            this.size = uSet.getSize();
            this.multiplayer = (uSet.getPlayersCount() == 2);
            listenForPlayer(player1);

            if (uSet.haveBot()) {
                //todo przydziel mu miejsce przy stole, musi być w oddzielnym ifie by było niezalezne od tego ilu graczy przy stole, przy obecnej strukturze
                //z tego co widzę to to coś w ten deseń moze być
                player2 = new Player(uSet.getBot().getName()); // jest tutaj dodany bot (jego nick ustawiony
                botMovements(player2);
            }
            // add second player if doesnt already existst and both chosen multiplayer mode and same board size
        } else if (multiplayer && player2 == null && uSet.getPlayersCount() == 2 && uSet.getSize() == size) {
            player2 = new Player(uSet.getNick());
            player2.setInputStream(sc);
            player2.setOutputStream(pw);
            listenForPlayer(player2);
        }
    }

    private void botMovements(final Player player, final Player bot) {
        //todo: logika bota typu: if(mozliwy ruch jest taki, to zrób to) ... etc
        //todo: skopiowałem to co jest w lsitenerze, tylko tutaj zamiast sendToClient(player2) bym zrobił metode sendToBot i tam robi jakiś możliwy ruch
        new Thread(new Runnable() {
            boolean listen = true;
            Scanner scanner = player.getInputStream();
            String prevGrid = "";

            @Override
            public void run() {
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
                            System.out.println(player.getNickname() + " made move on position " + restOfAnswer);
                            if (game.checkMove(player, restOfAnswer)) {
                                String grid = game.extractGrid().toString();
                                sendToClient(player1, MessagesServer.UPDATE_BOARD_____, grid);
                                sendToClient(player2, MessagesServer.UPDATE_BOARD_____, grid);
                                prevGrid = grid;
                            } else {
                                sendToClient(player, MessagesServer.WRONG_MOVE_______, "");
                            }
                            break;
                        case GIVE_UP_MOVE_____:
                            System.out.println(player.getNickname() + " decided not to move this time");
                            sendToClient(player1, MessagesServer.UPDATE_BOARD_____, prevGrid);
                            sendToClient(player2, MessagesServer.UPDATE_BOARD_____, prevGrid);
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
                            //TODO przypadek gdy pierwszy sie rozlaczy zanim drugi sie polaczy
                            break;
                    }
                }
            }
        }).start();
        //zrobiłbym to tak -> skopiuj zawartość
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
                    MessagesClient messagesClient = MessagesClient.valueOf(clientAnswer.substring(0, 17));
                    String restOfAnswer = clientAnswer.substring(17);
                    switch (messagesClient) {
                        case WAITING_FOR_GAME_:
                            System.out.println(player.getNickname() + " is waiting for another player");
                            checkGameCreation();
                            break;
                        case MADE_MOVE________:
                            System.out.println(player.getNickname() + " made move on position " + restOfAnswer);
                            if (game.checkMove(player, restOfAnswer)) {
                                String grid = game.extractGrid().toString();
                                sendToClient(player1, MessagesServer.UPDATE_BOARD_____, grid);
                                sendToClient(player2, MessagesServer.UPDATE_BOARD_____, grid);
                                prevGrid = grid;
                            } else {
                                sendToClient(player, MessagesServer.WRONG_MOVE_______, "");
                            }
                            break;
                        case GIVE_UP_MOVE_____:
                            System.out.println(player.getNickname() + " decided not to move this time");
                            sendToClient(player1, MessagesServer.UPDATE_BOARD_____, prevGrid);
                            sendToClient(player2, MessagesServer.UPDATE_BOARD_____, prevGrid);
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
                            //TODO przypadek gdy pierwszy sie rozlaczy zanim drugi sie polaczy
                            break;
                    }
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
        if (player.getOutputStream() != null) {
            String mess = message.toString() + additionalInfo;
            player.getOutputStream().println(mess);
        } else System.out.println("error");
    }
}