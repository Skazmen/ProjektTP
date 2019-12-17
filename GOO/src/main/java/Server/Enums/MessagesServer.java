package Server.Enums;

import Server.Server;

//wysyłane przez server do clienta
public enum MessagesServer {
	//wazne jest pierwsze 17 znakow

	SET_COLOR_BLACK__, 	//when user is connected and a geme for him is found 	 -> PO TYM USTALA SIE AKTUALNY STAN NA GRY
	SET_COLOR_WHITE__, 	//when user is connected and a geme for him is found 	 -> PO TYM USTALA SIE AKTUALNY STAN NA OCZEKIWANIA
	WRONG_MOVE_______, 		//when user tried to move to a wrong place 				 -> PO TYM NIE ZMIENIA STAN NA GRY( BO ZARAZ PO WYSŁANIU RUCHU ZMIENIŁO NA OCZEKIWANIA)
	UPDATE_BOARD_____, 		//after a successful move, drawing a board 				 -> PO TYM STAN SIE ZMIENIA NA PRZECIWNY (GRY - OCZEKIWANIA)
	END_GAME_________           //when both users give up their moves, or one surrenders -> PO TYM STAN KOŃCA
}