package Menu;

import Server.Enums.Boards;
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
    private JTextField player1TextField;
    private JComboBox numberOfPlayersComboBox;
    private boolean first = true;

    GameGui() {
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

        numberOfPlayersLabel = new JLabel("Select opponennt:");
        numberOfPlayersLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        numberOfPlayersLabel.setForeground(Color.white);
        numberOfPlayersLabel.setBounds(220, 315, 370, 25);
        add(numberOfPlayersLabel);

        numberOfPlayersComboBox = new JComboBox();
        numberOfPlayersComboBox.addItem("another human");
        numberOfPlayersComboBox.addItem("bot");
        numberOfPlayersComboBox.setBounds(450, 315, 175, 25);
        getContentPane().add(numberOfPlayersComboBox);
        numberOfPlayersComboBox.addActionListener(this);

        //nick of players
        nickLabel = new JLabel("Set nickname"); //check box
        nickLabel.setBounds(220, 350, 200, 25);
        nickLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        nickLabel.setForeground(Color.white);
        add(nickLabel);

        player1TextField = new JTextField("Player1");        //area to fill text
        player1TextField.setBounds(530, 350, 130, 25);
        add(player1TextField);
        player1TextField.setToolTipText("Please type nick of Player1"); //tool tip
        player1TextField.setVisible(true);

        //background
        backGroundLabel = new JLabel(new ImageIcon("images/GO_BG.jpg"));
        backGroundLabel.setOpaque(true);
        backGroundLabel.setBounds(0, 0, 1366, 768);
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

        if (smallBoardRadioButton.isSelected())         uSet.setSize(9);
        else if (mediumBoardRadioButton.isSelected())   uSet.setSize(13);
        else                                            uSet.setSize(19);

        String liczbaGraczy = numberOfPlayersComboBox.getSelectedItem().toString();
        if(liczbaGraczy.equals("bot"))  uSet.setPlayersCount(1);
        else                            uSet.setPlayersCount(2);

        uSet.setNick(player1TextField.getText());

        BoardGui playingBoard = new BoardGui(uSet);
        playingBoard.setLocation(this.getX(), this.getY());
        playingBoard.setVisible(true);
        this.setVisible(false);
    }
}