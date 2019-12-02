package Server.Enums;

import Server.Server;

//wysyłane przez server do clienta
public enum MessagesServer {
	SET_COLOR_BLACK, 	//when user is connected and a geme for him is found 	 -> PO TYM USTALA SIE AKTUALNY STAN NA GRY
	SET_COLOR_WHITE, 	//when user is connected and a geme for him is found 	 -> PO TYM USTALA SIE AKTUALNY STAN NA OCZEKIWANIA
	WRONG_MOVE, 		//when user tried to move to a wrong place 				 -> PO TYM NIE ZMIENIA STAN NA GRY( BO ZARAZ PO WYSŁANIU RUCHU ZMIENIŁO NA OCZEKIWANIA)
	UPDATE_BOARD, 		//after a successful move, drawing a board 				 -> PO TYM STAN SIE ZMIENIA NA PRZECIWNY (GRY - OCZEKIWANIA)
	END_GAME, 			//when both users give up their moves, or one surrenders -> PO TYM STAN KOŃCA
}