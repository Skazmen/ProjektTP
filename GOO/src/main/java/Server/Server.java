package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) {
		int serverPort = 59898;
		try (ServerSocket listener = new ServerSocket(serverPort)) {
			System.out.println("The server is running...");
			while (true) {
				Board board = Board.getInstance();
				Socket socket = listener.accept();
				Scanner in = new  Scanner(socket.getInputStream());
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				String boardSize = in.nextLine();
				if(boardSize != null) {
					board.addClient(in, out, boardSize);
				}
			}
		} catch (IOException e) {
			System.out.println("Cannot run server on socket " + serverPort);
		}
	}
}
