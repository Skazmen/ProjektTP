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


public class SettingsGui extends JFrame implements ActionListener
{
    private JButton bBack, bSave;
    private JLabel bg;
    private JLabel lNewGame, lScreenResolution, lNoOfPlayers, lNick;
    private	ButtonGroup bgGamePanel, bgScreenResolution;
    private JTextField tPlayer0, tPlayer1;
    private JComboBox cbNoOfPlayers;
    private JCheckBox cbNick, cbHuman0, cbHuman1;
    private	JRadioButton rbSmallBoard, rbMediumBoard, rbNormalBoard, rbSmall, rbMedium;
    private boolean first = true;


    public SettingsGui()
    {
        //setter = new Setter(); po uploadzie tla, czyli tlo idzie pierwsze

        setSize(1366, 768);
        setTitle("GO game - Settings");
        setLayout(null);


        //Exit button
        bBack = new JButton ("BACK");
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
        bSave = new JButton ("SAVE");
        bSave.setBounds(1040, 660, 100, 30);
        add(bSave);
        //bSave.setBackground(Color.GREEN);
        bSave.setForeground(Color.black);
        bSave.setContentAreaFilled(false);
        //bSave.setBorderPainted(false);
        bSave.setToolTipText("Click here to go save");
        bSave.setFont(new Font("SansSerif", Font.BOLD, 20));
        bSave.addActionListener(this);


        //Screen Resolution panel
        lScreenResolution = new JLabel("Select screen resolution:");
        lScreenResolution.setFont(new Font("SansSerif", Font.BOLD, 18));
        lScreenResolution.setForeground(Color.white);
        lScreenResolution.setBounds(220, 30, 265, 25);
        add(lScreenResolution);

        bgScreenResolution = new ButtonGroup ();
        rbSmall = new JRadioButton("800 x 600");
        rbSmall.setForeground(Color.white);
        rbSmall.setContentAreaFilled(false);
        rbSmall.setBounds(500, 30, 90, 25);

        rbMedium = new JRadioButton("1366 x 768");
        rbMedium.setBounds(600, 30, 150, 25);
        rbMedium.setForeground(Color.white);
        rbMedium.setContentAreaFilled(false);
        rbMedium.setSelected(true); //randommly selected

        bgScreenResolution.add(rbSmall);
        bgScreenResolution.add(rbMedium);

        add(rbSmall);
        add(rbMedium);


        //New game panel
        lNewGame = new JLabel("Select type of game: ");
        lNewGame.setFont(new Font("SansSerif", Font.BOLD, 18));
        lNewGame.setForeground(Color.white);
        lNewGame.setBounds(220, 65, 250, 25);
        add(lNewGame);

        bgGamePanel = new ButtonGroup ();
        rbSmallBoard = new JRadioButton("9x9");
        rbSmallBoard.setForeground(Color.white);
        rbSmallBoard.setContentAreaFilled(false);
        rbSmallBoard.setBounds(455, 65, 90, 25);

        rbMediumBoard = new JRadioButton("13x13");
        rbMediumBoard.setBounds(545, 65, 90, 25);
        rbMediumBoard.setForeground(Color.white);
        rbMediumBoard.setContentAreaFilled(false);

        rbNormalBoard = new JRadioButton("19x19");
        rbNormalBoard.setBounds(635, 65, 90, 25);
        rbNormalBoard.setForeground(Color.white);
        rbNormalBoard.setContentAreaFilled(false);
        rbNormalBoard.setSelected(true); //randommly selected


        bgGamePanel.add(rbSmallBoard);
        bgGamePanel.add(rbMediumBoard);
        bgGamePanel.add(rbNormalBoard);

        add(rbSmallBoard);
        add(rbMediumBoard);
        add(rbNormalBoard);


        //Number of Players panel
        lNoOfPlayers = new JLabel("Select number of human players:");
        lNoOfPlayers.setFont(new Font("SansSerif", Font.BOLD, 18));
        lNoOfPlayers.setForeground(Color.white);
        lNoOfPlayers.setBounds(220, 100, 370, 25);
        add(lNoOfPlayers);

        //How many players?
        JComboBox cbNoOfPlayers = new JComboBox();
        for (int i = 1; i <= 2; i++)
            cbNoOfPlayers.addItem(i);
        cbNoOfPlayers.setBounds(580, 100, 50, 25);
        getContentPane().add(cbNoOfPlayers);


        //nick of players
        cbNick = new JCheckBox("Do you want set nick of players?"); //check box
        cbNick.setBounds(200, 135, 400, 20);
        cbNick.setFont(new Font("SansSerif", Font.BOLD, 18));
        cbNick.setForeground(Color.white);
        cbNick.setContentAreaFilled(false);
        cbNick.addActionListener(this);
        add(cbNick);

        lNick = new JLabel("Please type nick in fileds: ");
        lNick.setFont(new Font("SansSerif", Font.BOLD, 18));
        lNick.setForeground(Color.white);
        lNick.setBounds(290, 170, 260, 25);
        lNick.setVisible(false);
        add(lNick);

        tPlayer1 = new JTextField ("Player1");		//area to fill text
        tPlayer1.setBounds(530 , 205, 130, 25);
        add(tPlayer1);
        tPlayer1.setToolTipText("Please type nick of Player1"); //tool tip
        tPlayer1.setVisible(false);

        tPlayer0 = new JTextField ("Player2");		//area to fill text
        tPlayer0.setBounds(530 , 235, 130, 25);
        add(tPlayer0);
        tPlayer0.setToolTipText("Please type nick of Player0"); //tool tip
        tPlayer0.setVisible(false);



        //human or boot
        cbHuman0 = new JCheckBox("Human?"); //check box
        cbHuman0.setBounds(420, 205, 200, 20);
        cbHuman0.setFont(new Font("SansSerif", Font.BOLD, 14));
        cbHuman0.setForeground(Color.white);
        cbHuman0.setContentAreaFilled(false);
        cbHuman0.addActionListener(this);
        cbHuman0.setVisible(false);
        add(cbHuman0);

        cbHuman1 = new JCheckBox("Human?"); //check box
        cbHuman1.setBounds(420, 235, 200, 20);
        cbHuman1.setFont(new Font("SansSerif", Font.BOLD, 14));
        cbHuman1.setForeground(Color.white);
        cbHuman1.setContentAreaFilled(false);
        cbHuman1.addActionListener(this);
        cbHuman1.setVisible(false);
        add(cbHuman1);


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
        if(source == bgScreenResolution)
        {
            if(rbSmall.isSelected())
            {
                setSize(1366, 768);
                repaint();
            }
            else if(rbMedium.isSelected())
            {
                setSize(800, 600);
                repaint();
            };
        }

    }
}
