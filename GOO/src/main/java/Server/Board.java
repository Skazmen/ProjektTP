package Server;

import java.io.PrintWriter;
import java.util.Scanner;

class Board {

	private Scanner player1_in;
	private PrintWriter player1_out;
	private Scanner player2_in;
	private PrintWriter player2_out;

	private static Board instance;

	private Board(){}

	synchronized static Board getInstance(){
		//Double-checkeed looking
		Board temp = instance;
		if(temp == null){
			synchronized (Board.class){
				if(instance == null){
					instance = new Board();
				}
				temp = instance;
			}
		}
		return temp;
	}

	void addClient(Scanner sc, PrintWriter pw){
		//add as first player
		if(player1_in == null || player1_out == null) {
			this.player1_in = sc;
			this.player1_out = pw;
			listenForPlayer(Players.PLAYER_ONE, player1_in,player1_out);
		//player one exists, add as second player
		} else if(player2_in == null || player2_out == null){
			this.player2_in = sc;
			this.player2_out = pw;
			listenForPlayer(Players.PLAYER_TWO, player2_in,player2_out);
		}
	}

	private void listenForPlayer(final Players player, final Scanner scanner, final PrintWriter printWriter) {
		new Thread(new Runnable() {
			boolean listen = true;
			@Override
			public void run() {
				while (listen){
					MessagesClient clientMessage = MessagesClient.valueOf(scanner.nextLine());
					switch (clientMessage){
						case WAITING_FOR_GAME:
							/*
							-todo> wszystkie pola jakie masz na tym boardzie -> zaznaczasz żę so not editable
							todo-> zrób sobie jakąś labelkę, która ma opacity na 0 (z tego co pamiętam, ale musisz doczytać czy na 0) i wtedy jest jakby pod wszystkim, niewidoczna - ona musi być z
							z defaultu tak ustawiona
							-> i teraz w moemncie gdy jesteś wk którym kolwiek tych casów, to zmieniasz textkst tej labalki w TYM miejscu
							dodatkowo ustawiając oapcity na 1 -> żeby byąą widoczna. Tym smamym masz zablokowane akcje uzytkownika oraz nie wyświetlone labelki
							 */
							System.out.println("client " + player + " is waiting for another player");
							sendToClient(player, MessagesServer.SET_COLOR_BLACK);
							break;
						case MADE_MOVE:
							System.out.println("client " + player + " made move");
							sendToClient(player, MessagesServer.WRONG_MOVE);
							sendToClient(player, MessagesServer.UPDATE_BOARD);
							break;
						case GIVE_UP_MOVE:
							System.out.println("client " + player + " decided not to move this time");
							break;
						case SURRENDER:
							System.out.println("client " + player + " surrendered the game");
							sendToClient(Players.BOTH, MessagesServer.END_GAME);
							break;
						case CLOSE:
							// stops listening to client when he closes his window
							listen = false;
							System.out.println("Client " + player + " disconnected");
							break;
					}

				}
			}
		}).start();
	}

	private void sendToClient(final Players player, final MessagesServer message){
		switch (player){
			case PLAYER_ONE:
				if(player1_out != null)	player1_out.println(message);
				break;
			case PLAYER_TWO:
				if(player2_out != null)	player2_out.println(message);
				break;
			case BOTH:
				if(player1_out != null && player2_out != null){
					player1_out.println(message);
					player2_out.println(message);
				}
				break;
		}
	}

}
