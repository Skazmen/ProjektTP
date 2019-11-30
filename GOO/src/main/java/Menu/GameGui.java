package Menu;

import Server.Players;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;
import javax.swing.*;


public class GameGui extends JFrame implements ActionListener {
    private JButton bBack, bStart;
    private JLabel bg;
    private JRadioButton smallBoard, mediumBoard, normalBoard;
    private JLabel lNewGame, lNoOfPlayers, lNick;
    private ButtonGroup bgGamePanel;
    private JTextField tPlayer2, tPlayer1;
    private JComboBox cbNoOfPlayers;
    private JCheckBox cbNick;
    private boolean first = true;


    GameGui() {
        //todo wybranie nazwy gracza, romziaru planszy i czy grać z botem czy człowiekiem


        //todo After choose option in ComboBoc refresh page - sizeOf(nicklist) = N;
        setSize(1366, 768);
        setTitle("Go game - New Game");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);


        //Back button
        bBack = new JButton("BACK");
        bBack.setBounds(1180, 660, 100, 30);
        add(bBack);
        bBack.setForeground(Color.black);
        bBack.setContentAreaFilled(false);
        bBack.setToolTipText("Click here to go back");
        bBack.setFont(new Font("SansSerif", Font.BOLD, 20));
        bBack.addActionListener(this);


        //New Game button
        bStart = new JButton("START");
        bStart.setBounds(1040, 660, 120, 30);
        add(bStart);
        bStart.setForeground(Color.black);
        bStart.setContentAreaFilled(false);
        bStart.setToolTipText("Click here to go save");
        bStart.setFont(new Font("SansSerif", Font.BOLD, 20));
        bStart.addActionListener(this);


        //New Game
        lNewGame = new JLabel("Select type of game: ");
        lNewGame.setFont(new Font("SansSerif", Font.BOLD, 18));
        lNewGame.setForeground(Color.white);
        lNewGame.setBounds(220, 280, 230, 25);
        add(lNewGame);

        bgGamePanel = new ButtonGroup();
        smallBoard = new JRadioButton("smallBoard(9x9)");
        smallBoard.setForeground(Color.white);
        smallBoard.setContentAreaFilled(false);
        smallBoard.setBounds(440, 280, 145, 25);
        smallBoard.setSelected(true); //randommly selected

        mediumBoard = new JRadioButton("mediumBoard(13x13)");
        mediumBoard.setBounds(585, 280, 180, 25);
        mediumBoard.setForeground(Color.white);
        mediumBoard.setContentAreaFilled(false);

        normalBoard = new JRadioButton("normalBoard(19x19)");
        normalBoard.setBounds(760, 280, 175, 25);
        normalBoard.setForeground(Color.white);
        normalBoard.setContentAreaFilled(false);

        bgGamePanel.add(smallBoard);
        bgGamePanel.add(mediumBoard);
        bgGamePanel.add(normalBoard);

        add(smallBoard);
        add(mediumBoard);
        add(normalBoard);

        lNoOfPlayers = new JLabel("Select number of human players:");
        lNoOfPlayers.setFont(new Font("SansSerif", Font.BOLD, 18));
        lNoOfPlayers.setForeground(Color.white);
        lNoOfPlayers.setBounds(220, 315, 370, 25);
        add(lNoOfPlayers);

        JComboBox cbNoOfPlayers = new JComboBox();
        cbNoOfPlayers.addItem("one");
        cbNoOfPlayers.addItem("two");
        cbNoOfPlayers.setBounds(590, 315, 50, 25);
        getContentPane().add(cbNoOfPlayers);
        cbNoOfPlayers.addActionListener(this);


        //nick of players
        cbNick = new JCheckBox("Do you want to change youre nicks set in Settings?"); //check box
        cbNick.setBounds(220, 350, 650, 20);
        cbNick.setFont(new Font("SansSerif", Font.BOLD, 18));
        cbNick.setForeground(Color.white);
        cbNick.setContentAreaFilled(false);
        cbNick.addActionListener(this);
        add(cbNick);

        lNick = new JLabel("Please type nick in fields: ");
        lNick.setFont(new Font("SansSerif", Font.BOLD, 18));
        lNick.setForeground(Color.white);
        lNick.setBounds(290, 385, 280, 25);
        lNick.setVisible(false);
        add(lNick);

        tPlayer1 = new JTextField("Player1");        //area to fill text
        tPlayer1.setBounds(530, 425, 130, 25);
        add(tPlayer1);
        tPlayer1.setToolTipText("Please type nick of Player1"); //tool tip
        tPlayer1.setVisible(false);

        tPlayer2 = new JTextField("Player2/Bot");        //area to fill text
        tPlayer2.setBounds(530, 455, 130, 25);
        add(tPlayer2);
        tPlayer2.setToolTipText("Please type nick of Player2 or Bot"); //tool tip
        tPlayer2.setVisible(false);


        //background
        bg = new JLabel(new ImageIcon("images/GO_BG.jpg"));
        bg.setOpaque(true);
        bg.setBounds(0, 0, 1366, 768);
        add(bg);

    }


    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        remove(bg);    //tlo
        if (first) {
            bg = new JLabel(new ImageIcon("images/GO_BG.jpg"));
            bg.setOpaque(true);
            bg.setBounds(0, 0, 1366, 768);
        } else {
            bg = new JLabel(new ImageIcon("images/GO_BG.jpg"));
            bg.setOpaque(true);
            bg.setBounds(0, 0, 1366, 768);
        }

        add(bg);
        first = !first;
        repaint();    //to here


        if (source == bBack) {
            MenuGui menu = new MenuGui();
            menu.setLocation(this.getX(), this.getY());
            menu.setVisible(true);
            this.setVisible(false);
        }

        if (source == cbNick)
        {
            if (cbNick.isSelected())
            {
                lNick.setVisible(true);
                tPlayer2.setVisible(true);
                tPlayer1.setVisible(true);

            }
            else if (!cbNick.isSelected())
            {
                lNick.setVisible(false);
                tPlayer2.setVisible(false);
                tPlayer1.setVisible(false);

            }
        }
        //todo trzeba zrobic dla kazdego rozmiaru
        if (source == bStart) {
            if (smallBoard.isSelected()) {
                BoardGui playingBoard = new BoardGui();
                playingBoard.setLocation(this.getX(), this.getY());
                playingBoard.setVisible(true);
                this.setVisible(false);
            } else if (mediumBoard.isSelected()) {
                BoardGui playingBoard = new BoardGui();
                playingBoard.setLocation(this.getX(), this.getY());
                playingBoard.setVisible(true);
                this.setVisible(false);
            } else {
                BoardGui playingBoard = new BoardGui();
                playingBoard.setLocation(this.getX(), this.getY());
                playingBoard.setVisible(true);
                this.setVisible(false);
            }
        }

        if (source == cbNoOfPlayers) {

            String liczbaGraczy = cbNoOfPlayers.getSelectedItem().toString();

            if (liczbaGraczy.equals("one") ) { //set players with bot and one player
                Settings.players = Players.get(0);
            } else { //set plaeyrs with second player
                Settings.players = Players.get(2);
            }
        }
    }
}