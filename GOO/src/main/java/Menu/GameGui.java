package Menu;

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
import java.util.Scanner;
import javax.swing.*;



public class GameGui extends JFrame implements ActionListener {
    private JButton bBack, bStart;
    private JLabel bg;
    private	JRadioButton smallBoard, mediumBoard, normalBoard;
    private JLabel lNewGame, lNoOfPlayers, lNick;
    private	ButtonGroup bgGamePanel;
    private JTextField tPlayer0, tPlayer1;
    private JComboBox cbNoOfPlayers;
    private JCheckBox cbNick, cbHuman0, cbHuman1;
    private boolean first = true;


    GameGui ()
    {
        //todo wybranie nazwy gracza, romziaru planszy i czy grać z botem czy człowiekiem


        //todo After choose option in ComboBoc refresh page - sizeOf(nicklist) = N;
        setSize(1366, 768);
        setTitle("Go game - Settings");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        //Back button
        bBack = new JButton ("BACK");
        bBack.setBounds(1180, 660, 100, 30);
        add(bBack);
        bBack.setForeground(Color.black);
        bBack.setContentAreaFilled(false);
        bBack.setToolTipText("Click here to go back");
        bBack.setFont(new Font("SansSerif", Font.BOLD, 20));
        bBack.addActionListener(this);


        //New Game button
        bStart = new JButton ("START");
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
        lNewGame.setBounds(220, 30, 230, 25);
        add(lNewGame);

        bgGamePanel = new ButtonGroup ();
        smallBoard = new JRadioButton("smallBoard(9x9)");
        smallBoard.setForeground(Color.white);
        smallBoard.setContentAreaFilled(false);
        smallBoard.setBounds(440, 30, 145, 25);
        smallBoard.setSelected(true); //randommly selected

        mediumBoard = new JRadioButton("mediumBoard(13x13)");
        mediumBoard.setBounds(585, 30, 175, 25);
        mediumBoard.setForeground(Color.white);
        mediumBoard.setContentAreaFilled(false);

        normalBoard = new JRadioButton("normalBoard(19x19");
        normalBoard.setBounds(760, 30, 175, 25);
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
        lNoOfPlayers.setBounds(220, 65, 295, 25);
        add(lNoOfPlayers);

        JComboBox cbNoOfPlayers = new JComboBox();
        for (int i = 1; i <= 2; i++)
            cbNoOfPlayers.addItem(i);
        cbNoOfPlayers.setBounds(525, 65, 50, 25);
        getContentPane().add(cbNoOfPlayers);


        //human or boot
        cbHuman0 = new JCheckBox("Human?"); //check box
        cbHuman0.setBounds(420, 175, 100, 20);
        cbHuman0.setFont(new Font("SansSerif", Font.BOLD, 14));
        cbHuman0.setForeground(Color.white);
        cbHuman0.setContentAreaFilled(false);
        cbHuman0.addActionListener(this);
        cbHuman0.setVisible(false);
        add(cbHuman0);

        cbHuman1 = new JCheckBox("Human?"); //check box
        cbHuman1.setBounds(420, 205, 200, 20);
        cbHuman1.setFont(new Font("SansSerif", Font.BOLD, 14));
        cbHuman1.setForeground(Color.white);
        cbHuman1.setContentAreaFilled(false);
        cbHuman1.addActionListener(this);
        cbHuman1.setVisible(false);
        add(cbHuman1);



        //nick of players
        cbNick = new JCheckBox("Do you want to change youre nicks set in Settings?"); //check box
        cbNick.setBounds(200, 100, 500, 20);
        cbNick.setFont(new Font("SansSerif", Font.BOLD, 18));
        cbNick.setForeground(Color.white);
        cbNick.setContentAreaFilled(false);
        cbNick.addActionListener(this);
        add(cbNick);

        lNick = new JLabel("Please type nick in fileds: ");
        lNick.setFont(new Font("SansSerif", Font.BOLD, 18));
        lNick.setForeground(Color.white);
        lNick.setBounds(290, 135, 260, 25);
        lNick.setVisible(false);
        add(lNick);

        tPlayer1 = new JTextField ("Player1");		//area to fill text
        tPlayer1.setBounds(530 , 175, 130, 25);
        add(tPlayer1);
        tPlayer1.setToolTipText("Please type nick of Player1"); //tool tip
        tPlayer1.setVisible(false);

        tPlayer0= new JTextField ("Player0");		//area to fill text
        tPlayer0.setBounds(530 , 205, 130, 25);
        add(tPlayer0);
        tPlayer0.setToolTipText("Please type nick of Player0"); //tool tip
        tPlayer0.setVisible(false);



        //background
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


        if (source == bBack)
        {
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
                tPlayer0.setVisible(true);
                tPlayer1.setVisible(true);

                cbHuman0.setVisible(true);
                cbHuman1.setVisible(true);

            }
            else if (!cbNick.isSelected())
            {
                lNick.setVisible(false);
                tPlayer0.setVisible(false);
                tPlayer1.setVisible(false);

                cbHuman0.setVisible(false);
                cbHuman1.setVisible(false);

            }
        }
        if(source == bStart){
            BoardGui playingBoard = new BoardGui();
            playingBoard.setLocation(this.getX(), this.getY());
            playingBoard.setVisible(true);
            this.setVisible(false);
        }

    }


}
