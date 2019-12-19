package Server.Enums;

import Server.Server;

//wysyłane przez server do clienta
public enum MessagesServer {
	//wazne jest pierwsze 17 znakow

	SET_COLOR_BLACK__, 		//when user is connected and a geme for him is found
	SET_COLOR_WHITE__, 		//when user is connected and a geme for him is found
	WRONG_MOVE_______, 		//when user tried to move to a wrong place
	UPDATE_BOARD_____, 		//after a successful move, drawing a board
	END_GAME_________,      //when both users give up their moves, or one surrenders

	CONNECTION_RES___ 		//to check if server responds
}