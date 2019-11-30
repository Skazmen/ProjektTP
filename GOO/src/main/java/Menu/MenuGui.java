package Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.*;


public class MenuGui extends JFrame implements ActionListener {
    private JButton bExit, bNewGame, bSettings, bLoadGame;
    private JLabel bg;
    private boolean first = true;


    MenuGui() {
        setSize(1366, 768);
        setTitle("GO game");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);


        //Exit button
        bExit = new JButton("EXIT");
        bExit.setBounds(1180, 660, 100, 30);
        add(bExit);
        bExit.setForeground(Color.black);
        bExit.setContentAreaFilled(false);
        bExit.setToolTipText("Click here to exit program");
        bExit.setFont(new Font("SansSerif", Font.BOLD, 20));
        bExit.addActionListener(this);


        //New Game button
        bNewGame = new JButton("NEW GAME");
        bNewGame.setBounds(1020, 530, 350, 50);
        add(bNewGame);
        bNewGame.setForeground(Color.black);
        bNewGame.setContentAreaFilled(false);
        bNewGame.setToolTipText("Click here to New Game");
        bNewGame.setFont(new Font("SansSerif", Font.BOLD, 48));
        bNewGame.addActionListener(this);


        //Settings button
        /*bSettings = new JButton("SETTINGS");
        bSettings.setBounds(20, 600, 350, 50);
        add(bSettings);
        bSettings.setForeground(Color.black);
        bSettings.setContentAreaFilled(false);
        bSettings.setToolTipText("Click here to New Game");
        bSettings.setFont(new Font("SansSerif", Font.BOLD, 48));
        bSettings.addActionListener(this);*/

        //Settings button
        bLoadGame = new JButton("LOAD");
        bLoadGame.setBounds(1100, 600, 250, 50);
        add(bLoadGame);
        bLoadGame.setForeground(Color.black);
        bLoadGame.setContentAreaFilled(false);
        bLoadGame.setToolTipText("Click here to Load Game");
        bLoadGame.setFont(new Font("SansSerif", Font.BOLD, 48));
        bLoadGame.addActionListener(this);



        //Background
        bg = new JLabel(new ImageIcon("images/GO_BG.jpg"));
        bg.setOpaque(true);
        bg.setBounds(0, 0, 1366, 768);
        add(bg);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        remove(bg);	//tlo
        if (first)
        {
            bg = new JLabel(new ImageIcon("images/GO_BG.jpg"));
            bg.setOpaque(true);
            bg.setBounds(0, 0, 1366, 768);
        }
        else
        {
            bg = new JLabel(new ImageIcon("images/GO_BG.jpg"));
            bg.setOpaque(true);
            bg.setBounds(0, 0, 1366, 768);
        }
        add(bg);
        first=!first;
        repaint();	//to here

        if (source == bExit)
            dispose();//exit
        /*else if (source == bSettings)
        {
            SettingsGui settings = new SettingsGui();
            settings.setLocation(this.getX(), this.getY());
            settings.setVisible(true);
            this.setVisible(false);
        }*/

        else if (source == bNewGame)
        {
            GameGui game = new GameGui();
            game.setLocation(this.getX(), this.getY());
            game.setVisible(true);
            this.setVisible(false);
        }


    }

}
