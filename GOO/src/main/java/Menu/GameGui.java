package Menu;

import Server.Enums.Players;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameGui extends JFrame implements ActionListener {
    private JButton buttonBack, startButton;
    private JLabel backGroundLabel;
    private JRadioButton smallBoardRadioButton, mediumBoardRadioButton, normalBoardRadioButton;
    private JLabel newGameLabel, numberOfPlayersLabel, nickLabel;
    private ButtonGroup gamePanelButtonGroup;
    private JTextField player2TextField, player1TextField;
    private JComboBox numberOfPlayersComboBox;
    private JCheckBox nickComboBox;
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
        buttonBack = new JButton("BACK");
        buttonBack.setBounds(1180, 660, 100, 30);
        add(buttonBack);
        buttonBack.setForeground(Color.black);
        buttonBack.setContentAreaFilled(false);
        buttonBack.setToolTipText("Click here to go back");
        buttonBack.setFont(new Font("SansSerif", Font.BOLD, 20));
        buttonBack.addActionListener(this);

        //New Game button
        startButton = new JButton("START");
        startButton.setBounds(1040, 660, 120, 30);
        add(startButton);
        startButton.setForeground(Color.black);
        startButton.setContentAreaFilled(false);
        startButton.setToolTipText("Click here to go save");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        startButton.addActionListener(this);

        //New Game
        newGameLabel = new JLabel("Select type of game: ");
        newGameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        newGameLabel.setForeground(Color.white);
        newGameLabel.setBounds(220, 280, 230, 25);
        add(newGameLabel);

        gamePanelButtonGroup = new ButtonGroup();
        smallBoardRadioButton = new JRadioButton("smallBoard(9x9)");
        smallBoardRadioButton.setForeground(Color.white);
        smallBoardRadioButton.setContentAreaFilled(false);
        smallBoardRadioButton.setBounds(440, 280, 145, 25);
        smallBoardRadioButton.setSelected(true); //randommly selected

        mediumBoardRadioButton = new JRadioButton("mediumBoard(13x13)");
        mediumBoardRadioButton.setBounds(585, 280, 180, 25);
        mediumBoardRadioButton.setForeground(Color.white);
        mediumBoardRadioButton.setContentAreaFilled(false);

        normalBoardRadioButton = new JRadioButton("normalBoard(19x19)");
        normalBoardRadioButton.setBounds(760, 280, 175, 25);
        normalBoardRadioButton.setForeground(Color.white);
        normalBoardRadioButton.setContentAreaFilled(false);

        gamePanelButtonGroup.add(smallBoardRadioButton);
        gamePanelButtonGroup.add(mediumBoardRadioButton);
        gamePanelButtonGroup.add(normalBoardRadioButton);

        add(smallBoardRadioButton);
        add(mediumBoardRadioButton);
        add(normalBoardRadioButton);

        numberOfPlayersLabel = new JLabel("Select number of human players:");
        numberOfPlayersLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        numberOfPlayersLabel.setForeground(Color.white);
        numberOfPlayersLabel.setBounds(220, 315, 370, 25);
        add(numberOfPlayersLabel);

        numberOfPlayersComboBox = new JComboBox();
        numberOfPlayersComboBox.addItem("one");
        numberOfPlayersComboBox.addItem("two");
        numberOfPlayersComboBox.setBounds(590, 315, 50, 25);
        getContentPane().add(numberOfPlayersComboBox);
        numberOfPlayersComboBox.addActionListener(this);

        //nick of players
        nickComboBox = new JCheckBox("Do you want to change youre nicks set in Settings?"); //check box
        nickComboBox.setBounds(220, 350, 650, 20);
        nickComboBox.setFont(new Font("SansSerif", Font.BOLD, 18));
        nickComboBox.setForeground(Color.white);
        nickComboBox.setContentAreaFilled(false);
        nickComboBox.addActionListener(this);
        add(nickComboBox);

        nickLabel = new JLabel("Please type nick in fields: ");
        nickLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        nickLabel.setForeground(Color.white);
        nickLabel.setBounds(290, 385, 280, 25);
        nickLabel.setVisible(false);
        add(nickLabel);

        player1TextField = new JTextField("Player1");        //area to fill text
        player1TextField.setBounds(530, 425, 130, 25);
        add(player1TextField);
        player1TextField.setToolTipText("Please type nick of Player1"); //tool tip
        player1TextField.setVisible(false);

        player2TextField = new JTextField("Player2/Bot");        //area to fill text
        player2TextField.setBounds(530, 455, 130, 25);
        add(player2TextField);
        player2TextField.setToolTipText("Please type nick of Player2 or Bot"); //tool tip
        player2TextField.setVisible(false);

        //background
        backGroundLabel = new JLabel(new ImageIcon("images/GO_BG.jpg"));
        backGroundLabel.setOpaque(true);
        backGroundLabel.setBounds(0, 0, 1366, 768);
        add(backGroundLabel);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        remove(backGroundLabel);

        if (first) {
            backGroundLabel = new JLabel(new ImageIcon("images/GO_BG.jpg"));
            backGroundLabel.setOpaque(true);
            backGroundLabel.setBounds(0, 0, 1366, 768);
        } else {
            backGroundLabel = new JLabel(new ImageIcon("images/GO_BG.jpg"));
            backGroundLabel.setOpaque(true);
            backGroundLabel.setBounds(0, 0, 1366, 768);
        }

        add(backGroundLabel);
        first = !first;
        repaint();

        if (source == buttonBack) {
            MenuGui menu = new MenuGui();
            menu.setLocation(this.getX(), this.getY());
            menu.setVisible(true);
            this.setVisible(false);
        }

        if (source == nickComboBox) {
            if (nickComboBox.isSelected()) {
                nickLabel.setVisible(true);
                player2TextField.setVisible(true);
                player1TextField.setVisible(true);

            } else if (!nickComboBox.isSelected()) {
                nickLabel.setVisible(false);
                player2TextField.setVisible(false);
                player1TextField.setVisible(false);

            }
        }

        //todo trzeba zrobic dla kazdego rozmiaru
        if (source == startButton) {
            if (smallBoardRadioButton.isSelected()) {
                BoardGui playingBoard = new BoardGui();
                playingBoard.setLocation(this.getX(), this.getY());
                playingBoard.setVisible(true);
                this.setVisible(false);
            } else if (mediumBoardRadioButton.isSelected()) {
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

        if (source == numberOfPlayersComboBox) {
            String liczbaGraczy = numberOfPlayersComboBox.getSelectedItem().toString();

            if (liczbaGraczy.equals("one")) { //set players with bot and one player
                Settings.players = Players.get(0);
            } else { //set plaeyrs with second player
                Settings.players = Players.get(2);
            }
        }
    }
}