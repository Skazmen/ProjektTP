package Menu;

import Server.MessagesClient;
import Server.MessagesServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.awt.Color;
import java.awt.Font;

public class BoardGui extends JFrame implements ActionListener{

	private Scanner in;
	private PrintWriter out;
	private CountDownLatch sync = new CountDownLatch(1); //for 'sendToServer' to wait for 'out' to be inicjalized;
	private JButton bSurrender;
	private JLabel bg;
	private boolean first = true;

	BoardGui(){
		//połączenie z serwerem
		connectToServer();

		//wysłanie wiadomości
		sendToServer(MessagesClient.WAITING_FOR_GAME);
		sendToServer(MessagesClient.MADE_MOVE);
		sendToServer(MessagesClient.GIVE_UP_MOVE);
		sendToServer(MessagesClient.SURRENDER);

		setSize(1366, 768);
		setTitle("Go game - Loading");
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);


		//Back button
		bSurrender = new JButton("leave");
		bSurrender.setBounds(1180, 660, 180, 30);
		add(bSurrender);
		bSurrender.setForeground(Color.white);
		bSurrender.setContentAreaFilled(false);
		bSurrender.setToolTipText("Click here to leave session");
		bSurrender.setFont(new Font("SansSerif", Font.BOLD, 20));
		bSurrender.addActionListener(this);
		setResizable(false);

		//background
		bg = new JLabel(new ImageIcon("images/loading.jpg"));
		bg.setOpaque(true);
		bg.setBounds(0, 0, 1366, 768);
		add(bg);




		//todo zaleznie od state odpowiednio wszytsko narysować

		//powiadomienie serwera przed zamknieciem
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				sendToServer(MessagesClient.CLOSE);
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		remove(bg);    //tlo
		if (first) {
			bg = new JLabel(new ImageIcon("images/loading.jpg"));
			bg.setOpaque(true);
			bg.setBounds(0, 0, 1366, 768);
		} else {
			bg = new JLabel(new ImageIcon("images/loading.jpg"));
			bg.setOpaque(true);
			bg.setBounds(0, 0, 1366, 768);
		}
		add(bg);
		first = !first;
		repaint();    //to here


		if (source == bSurrender) {
			sendToServer(MessagesClient.SURRENDER);
			MenuGui menu = new MenuGui();
			menu.setLocation(this.getX(), this.getY());
			menu.setVisible(true);
			this.setVisible(false);
		}

	}

	private void connectToServer() { //pokaz mi miejsce  wktórym dostjaesz info z backendu że np gracz ywkonał ruch, albo w ktorym miejscu to sie wyswie
		new Thread(new Runnable() {
			@Override
			public void run() {

				try (Socket socket = new Socket(InetAddress.getLocalHost(), 59898)) {
					in = new Scanner(socket.getInputStream());
					out = new PrintWriter(socket.getOutputStream(), true);
					sync.countDown(); //sygnalize 'out' is initialized
					while (true){
						MessagesServer serverAnswer = MessagesServer.valueOf(in.nextLine());
						switch (serverAnswer){
							case SET_COLOR_BLACK:
								System.out.println("Your color is Black");
								break;
							case SET_COLOR_WHITE:
								System.out.println("Your color is White");
								break;
							case WRONG_MOVE:
								System.out.println("The move you tride to make is not allowed");
								break;
							case UPDATE_BOARD:
								System.out.println("Your move was good, here is updated board");
								break;
							case END_GAME:
								System.out.println("The game has ended and PlayerX won");
								break;
						}
					}
				} catch (ConnectException | UnknownHostException e){
					System.out.println("Cannot connect to  server - run server first");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	private void sendToServer(final MessagesClient message) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					sync.await(); //wait for 'out' to be initialized
					out.println(message.toString());
				} catch (NullPointerException | InterruptedException e){
					System.out.println("Didn't connect to server yet");
				}
			}
		}).start();

	}
}
