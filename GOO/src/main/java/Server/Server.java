package Server;

import Menu.UserSettings;
import com.fasterxml.jackson.databind.ObjectMapper;

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
				String settings = in.nextLine();
				if(settings != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					UserSettings uSet = objectMapper.readValue(settings, UserSettings.class);
					board.addClient(listener, in, out, uSet);
				}
			}
		} catch (IOException e) {
			System.out.println("Cannot run server on socket " + serverPort);
		}
	}
}
