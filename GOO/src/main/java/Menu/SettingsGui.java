package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SettingsGui extends JFrame implements ActionListener {
    private JButton bBack, backButton;
    private JLabel backGroundLabel;
    private JLabel newGameLabel, screenResolutionLabel, noOfPlayersLabel, nickLabel;
    private ButtonGroup gamePanelButtonGroup, screenResolutionButtonGroup;
    private JTextField player0TextField, player1TextField;
    private JComboBox noOfPlayersComboBox;
    private JCheckBox nickCheckBox, human0CheckBox, human1CheckBox;
    private JRadioButton smallBoardRadioButton, mediumBoardRadioButton, normalBoardRadioButton, smallRadioButton, mediumRadioButton;
    private boolean first = true;

    public SettingsGui() {
        //setter = new Setter(); po uploadzie tla, czyli tlo idzie pierwsze
        setSize(1366, 768);
        setTitle("GO game - Settings");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //Exit button
        bBack = new JButton("BACK");
        bBack.setBounds(1180, 660, 100, 30);
        add(bBack);
        //bBack.setBackground(Color.GREEN);
        bBack.setForeground(Color.black);
        bBack.setContentAreaFilled(false);
        //bBack.setBorderPainted(false);
        bBack.setToolTipText("Click here to go back");
        bBack.setFont(new Font("SansSerif", Font.BOLD, 20));
        bBack.addActionListener(this);

        //Save button
        backButton = new JButton("SAVE");
        backButton.setBounds(1040, 660, 100, 30);
        add(backButton);
        //bSave.setBackground(Color.GREEN);
        backButton.setForeground(Color.black);
        backButton.setContentAreaFilled(false);
        //bSave.setBorderPainted(false);
        backButton.setToolTipText("Click here to go save");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        backButton.addActionListener(this);

        //Screen Resolution panel
        screenResolutionLabel = new JLabel("Select screen resolution:");
        screenResolutionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        screenResolutionLabel.setForeground(Color.white);
        screenResolutionLabel.setBounds(220, 30, 265, 25);
        add(screenResolutionLabel);

        screenResolutionButtonGroup = new ButtonGroup();
        smallRadioButton = new JRadioButton("800 x 600");
        smallRadioButton.setForeground(Color.white);
        smallRadioButton.setContentAreaFilled(false);
        smallRadioButton.setBounds(500, 30, 90, 25);

        mediumRadioButton = new JRadioButton("1366 x 768");
        mediumRadioButton.setBounds(600, 30, 150, 25);
        mediumRadioButton.setForeground(Color.white);
        mediumRadioButton.setContentAreaFilled(false);
        mediumRadioButton.setSelected(true); //randommly selected

        screenResolutionButtonGroup.add(smallRadioButton);
        screenResolutionButtonGroup.add(mediumRadioButton);

        add(smallRadioButton);
        add(mediumRadioButton);

        //New game panel
        newGameLabel = new JLabel("Select type of game: ");
        newGameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        newGameLabel.setForeground(Color.white);
        newGameLabel.setBounds(220, 65, 250, 25);
        add(newGameLabel);

        gamePanelButtonGroup = new ButtonGroup();
        smallBoardRadioButton = new JRadioButton("9x9");
        smallBoardRadioButton.setForeground(Color.white);
        smallBoardRadioButton.setContentAreaFilled(false);
        smallBoardRadioButton.setBounds(455, 65, 90, 25);

        mediumBoardRadioButton = new JRadioButton("13x13");
        mediumBoardRadioButton.setBounds(545, 65, 90, 25);
        mediumBoardRadioButton.setForeground(Color.white);
        mediumBoardRadioButton.setContentAreaFilled(false);

        normalBoardRadioButton = new JRadioButton("19x19");
        normalBoardRadioButton.setBounds(635, 65, 90, 25);
        normalBoardRadioButton.setForeground(Color.white);
        normalBoardRadioButton.setContentAreaFilled(false);
        normalBoardRadioButton.setSelected(true); //randommly selected

        gamePanelButtonGroup.add(smallBoardRadioButton);
        gamePanelButtonGroup.add(mediumBoardRadioButton);
        gamePanelButtonGroup.add(normalBoardRadioButton);

        add(smallBoardRadioButton);
        add(mediumBoardRadioButton);
        add(normalBoardRadioButton);

        //Number of Players panel
        noOfPlayersLabel = new JLabel("Select number of human players:");
        noOfPlayersLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        noOfPlayersLabel.setForeground(Color.white);
        noOfPlayersLabel.setBounds(220, 100, 370, 25);
        add(noOfPlayersLabel);

        //How many players?
        noOfPlayersComboBox = new JComboBox();

        for (int i = 1; i <= 2; i++) {
            noOfPlayersComboBox.addItem(i);
        }

        noOfPlayersComboBox.setBounds(580, 100, 50, 25);
        getContentPane().add(noOfPlayersComboBox);

        //nick of players
        nickCheckBox = new JCheckBox("Do you want set nick of players?"); //check box
        nickCheckBox.setBounds(200, 135, 400, 20);
        nickCheckBox.setFont(new Font("SansSerif", Font.BOLD, 18));
        nickCheckBox.setForeground(Color.white);
        nickCheckBox.setContentAreaFilled(false);
        nickCheckBox.addActionListener(this);
        add(nickCheckBox);

        nickLabel = new JLabel("Please type nick in fileds: ");
        nickLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        nickLabel.setForeground(Color.white);
        nickLabel.setBounds(290, 170, 260, 25);
        nickLabel.setVisible(false);
        add(nickLabel);

        player1TextField = new JTextField("Player1");        //area to fill text
        player1TextField.setBounds(530, 205, 130, 25);
        add(player1TextField);
        player1TextField.setToolTipText("Please type nick of Player1"); //tool tip
        player1TextField.setVisible(false);

        player0TextField = new JTextField("Player2");        //area to fill text
        player0TextField.setBounds(530, 235, 130, 25);
        add(player0TextField);
        player0TextField.setToolTipText("Please type nick of Player0"); //tool tip
        player0TextField.setVisible(false);

        //human or boot
        human0CheckBox = new JCheckBox("Human?"); //check box
        human0CheckBox.setBounds(420, 205, 200, 20);
        human0CheckBox.setFont(new Font("SansSerif", Font.BOLD, 14));
        human0CheckBox.setForeground(Color.white);
        human0CheckBox.setContentAreaFilled(false);
        human0CheckBox.addActionListener(this);
        human0CheckBox.setVisible(false);
        add(human0CheckBox);

        human1CheckBox = new JCheckBox("Human?"); //check box
        human1CheckBox.setBounds(420, 235, 200, 20);
        human1CheckBox.setFont(new Font("SansSerif", Font.BOLD, 14));
        human1CheckBox.setForeground(Color.white);
        human1CheckBox.setContentAreaFilled(false);
        human1CheckBox.addActionListener(this);
        human1CheckBox.setVisible(false);
        add(human1CheckBox);

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

        if (source == bBack) {
            MenuGui menu = new MenuGui();
            menu.setLocation(this.getX(), this.getY());
            menu.setVisible(true);
            this.setVisible(false);
        }

        if (source == nickCheckBox) {
            if (nickCheckBox.isSelected()) {
                nickLabel.setVisible(true);
                player0TextField.setVisible(true);
                player1TextField.setVisible(true);

                human0CheckBox.setVisible(true);
                human1CheckBox.setVisible(true);
            } else if (!nickCheckBox.isSelected()) {
                nickLabel.setVisible(false);
                player0TextField.setVisible(false);
                player1TextField.setVisible(false);

                human0CheckBox.setVisible(false);
                human1CheckBox.setVisible(false);
            }
        }

        if (source == screenResolutionButtonGroup) {
            if (smallRadioButton.isSelected()) {
                setSize(1366, 768);
                repaint();
            } else if (mediumRadioButton.isSelected()) {
                setSize(800, 600);
                repaint();
            }
        }
    }
}