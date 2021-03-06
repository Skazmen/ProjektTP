package Menu;

import Players.Bot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGui extends JFrame implements ActionListener {
    private JButton buttonBack, startButton;
    private JLabel backGroundLabel;
    private JRadioButton smallBoardRadioButton, mediumBoardRadioButton, normalBoardRadioButton;
    private JLabel newGameLabel, numberOfPlayersLabel, nickLabel;
    private ButtonGroup gamePanelButtonGroup;
    private JTextField player1TextField;
    private JComboBox numberOfPlayersComboBox;
    private boolean first = true;

    GameGui() {
        setSize(1000, 800);
        setTitle("Go game - New Game");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //Back button
        buttonBack = new JButton("BACK");
        buttonBack.setBounds(870, 620, 100, 30);
        add(buttonBack);
        buttonBack.setForeground(Color.black);
        buttonBack.setContentAreaFilled(false);
        buttonBack.setToolTipText("Click here to go back");
        buttonBack.setFont(new Font("SansSerif", Font.BOLD, 20));
        buttonBack.addActionListener(this);

        //New Game button
        startButton = new JButton("START");
        startButton.setBounds(670, 550, 300, 50);
        add(startButton);
        startButton.setForeground(Color.black);
        startButton.setContentAreaFilled(false);
        startButton.setToolTipText("Click here to go save");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 48));
        startButton.addActionListener(this);

        //New Game
        newGameLabel = new JLabel("Select type of game: ");
        newGameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        newGameLabel.setForeground(Color.white);
        newGameLabel.setBounds(120, 280, 230, 25);
        add(newGameLabel);

        gamePanelButtonGroup = new ButtonGroup();
        smallBoardRadioButton = new JRadioButton("smallBoard(9x9)");
        smallBoardRadioButton.setForeground(Color.white);
        smallBoardRadioButton.setContentAreaFilled(false);
        smallBoardRadioButton.setBounds(340, 280, 145, 25);
        smallBoardRadioButton.setSelected(true); //randommly selected

        mediumBoardRadioButton = new JRadioButton("mediumBoard(13x13)");
        mediumBoardRadioButton.setBounds(485, 280, 180, 25);
        mediumBoardRadioButton.setForeground(Color.white);
        mediumBoardRadioButton.setContentAreaFilled(false);

        normalBoardRadioButton = new JRadioButton("normalBoard(19x19)");
        normalBoardRadioButton.setBounds(660, 280, 175, 25);
        normalBoardRadioButton.setForeground(Color.white);
        normalBoardRadioButton.setContentAreaFilled(false);

        gamePanelButtonGroup.add(smallBoardRadioButton);
        gamePanelButtonGroup.add(mediumBoardRadioButton);
        gamePanelButtonGroup.add(normalBoardRadioButton);

        add(smallBoardRadioButton);
        add(mediumBoardRadioButton);
        add(normalBoardRadioButton);

        numberOfPlayersLabel = new JLabel("Select opponennt:");
        numberOfPlayersLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        numberOfPlayersLabel.setForeground(Color.white);
        numberOfPlayersLabel.setBounds(120, 315, 370, 25);
        add(numberOfPlayersLabel);

        numberOfPlayersComboBox = new JComboBox();
        numberOfPlayersComboBox.addItem("another human");
        numberOfPlayersComboBox.addItem("bot");
        numberOfPlayersComboBox.setBounds(350, 315, 175, 25);
        getContentPane().add(numberOfPlayersComboBox);
        numberOfPlayersComboBox.addActionListener(this);

        //nick of players
        nickLabel = new JLabel("Set nickname"); //check box
        nickLabel.setBounds(120, 350, 200, 25);
        nickLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        nickLabel.setForeground(Color.white);
        add(nickLabel);

        player1TextField = new JTextField("Player_" + ((int) Math.floor(1 + Math.random() * 999)));
        player1TextField.setBounds(350, 350, 130, 25);
        add(player1TextField);
        player1TextField.setToolTipText("Please type nick of Player1"); //tool tip
        player1TextField.setVisible(true);

        //background
        backGroundLabel = new JLabel(new ImageIcon("GOO/images/GO_BG.jpg"));
        backGroundLabel.setOpaque(true);
        backGroundLabel.setBounds(0, 0, 1000, 800);
        add(backGroundLabel);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == buttonBack) {
            MenuGui menu = new MenuGui();
            menu.setLocation(this.getX(), this.getY());
            menu.setVisible(true);
            this.setVisible(false);
        }

        if (source == startButton) {
            createGame();
        }

    }

    private void createGame() {
        UserSettings uSet = new UserSettings();

        if (smallBoardRadioButton.isSelected()) {
            uSet.setSize(9);
        } else if (mediumBoardRadioButton.isSelected()) {
            uSet.setSize(13);
        } else {
            uSet.setSize(19);
        }

        if (numberOfPlayersComboBox.getSelectedItem().toString().equals("bot")) {
            uSet.setPlayersCount(1);
        } else {
            uSet.setPlayersCount(2);
        }

        uSet.setNick(player1TextField.getText());

        BoardGui playingBoard = new BoardGui(uSet);
        playingBoard.setLocation(this.getX(), this.getY());
        playingBoard.setVisible(true);
        this.setVisible(false);
    }
}