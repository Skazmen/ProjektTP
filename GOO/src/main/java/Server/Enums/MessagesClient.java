package Server.Enums;

//wysyłane przez clienta do servera
public enum MessagesClient {
	//wazne jest pierwsze 17 znakow

	WAITING_FOR_GAME_,	//when user clicked New Game and is waiting for server to find him second player
	MADE_MOVE________, 			//when user makes a move
	GIVE_UP_MOVE_____, 		//when user decides not to do a move in this turn
	SURRENDER________, 			//when user states he loses and gives a game with valcover
	CLOSE____________//when client closed his window
}