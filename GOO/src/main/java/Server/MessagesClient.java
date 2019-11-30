package Server;

//wysyłane przez clienta do servera
public enum MessagesClient {
	WAITING_FOR_GAME,	//when user clicked New Game and is waiting for server to find him second player
	MADE_MOVE, 			//when user makes a move
	GIVE_UP_MOVE, 		//when user decides not to do a move in this turn
	SURRENDER, 			//when user states he loses and gives a game with valcover
	CLOSE 				//when client closed his window
}
