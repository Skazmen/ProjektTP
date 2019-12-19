package Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

public class MenuGui extends JFrame implements ActionListener {
    private JButton exitButton, newGameButton, settingsButton, loadGameButton;
    private JLabel backGroundLabel;
    private boolean first = true;

    public MenuGui() {
        setSize(1000, 800);
        setTitle("GO game");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //Exit button
        exitButton = new JButton("EXIT");
        exitButton.setBounds(870, 620, 100, 30);
        add(exitButton);
        exitButton.setForeground(Color.black);
        exitButton.setContentAreaFilled(false);
        exitButton.setToolTipText("Click here to exit program");
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        exitButton.addActionListener(this);

        //New Game button
        newGameButton = new JButton("NEW GAME");
        newGameButton.setBounds(620, 550, 350, 50);
        add(newGameButton);
        newGameButton.setForeground(Color.black);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setToolTipText("Click here to New Game");
        newGameButton.setFont(new Font("SansSerif", Font.BOLD, 48));
        newGameButton.addActionListener(this);

        //Load game button
        /*loadGameButton = new JButton("LOAD");
        loadGameButton.setBounds(720, 400, 250, 50);
        add(loadGameButton);
        loadGameButton.setForeground(Color.black);
        loadGameButton.setContentAreaFilled(false);
        loadGameButton.setToolTipText("Click here to Load Game");
        loadGameButton.setFont(new Font("SansSerif", Font.BOLD, 48));
        loadGameButton.addActionListener(this);*/

        //Background
        backGroundLabel = new JLabel(new ImageIcon("images/GO_BG.jpg"));
        backGroundLabel.setOpaque(true);
        backGroundLabel.setBounds(0, 0, 1000, 800);
        add(backGroundLabel);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == exitButton) {
            dispose();//exit
        }

        else if (source == newGameButton) {
            GameGui game = new GameGui();
            game.setLocation(this.getX(), this.getY());
            game.setVisible(true);
            this.setVisible(false);
        }
    }
}