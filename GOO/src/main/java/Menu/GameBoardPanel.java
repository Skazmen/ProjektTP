package Menu;

import Server.Enums.Colours;

import javax.swing.*;
import java.awt.*;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.BLUE;


public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = -494530433694385328L;

    //rozmiar
    private int size;
    public int numberOfTiles;
    public int tileSize;
    public int borderSize;
    public int[][] positions;

    public GameBoardPanel(int s) {
        this.size = s;
        this.numberOfTiles = size - 1;
        this.tileSize = 650 / (numberOfTiles + 2);
        this.borderSize = tileSize;

        this.setBackground(Color.LIGHT_GRAY);
    }

    // zwraca pozycje która została kliknieta
    public int[] makeMove(MouseEvent e) {
        int row = Math.round((float) (e.getY() - borderSize)
                / tileSize);
        int col = Math.round((float) (e.getX() - borderSize)
                / tileSize);

        if (row >= size || col >= size || row < 0 || col < 0) {
            return null;
        }

        int[] position = new int[2];
        position[0] = row;
        position[1] = col;
        return position;
    }

    // rysuje całą plansze od nowa
    public void update(int[][] pos) {
        this.positions = pos;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.BLACK);
        // rysowanie rzedow
        for (int i = 0; i < size; i++) {
            g2.drawLine(borderSize, i * tileSize + borderSize, tileSize
                    * numberOfTiles + borderSize, i * tileSize + borderSize);
        }
        // kolumn
        for (int i = 0; i < size; i++) {
            g2.drawLine(i * tileSize + borderSize, borderSize, i * tileSize
                    + borderSize, tileSize * numberOfTiles + borderSize);
        }

        if (positions != null) {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {

                    if (positions[row][col] == 1) {
                        g2.setColor(Color.BLACK);
                        g2.fillOval(col * tileSize + borderSize - tileSize / 2,
                                row * tileSize + borderSize - tileSize / 2,
                                tileSize, tileSize);
                    } else if (positions[row][col] == -1) {
                        g2.setColor(Color.WHITE);
                        g2.fillOval(col * tileSize + borderSize - tileSize / 2,
                                row * tileSize + borderSize - tileSize / 2,
                                tileSize, tileSize);
                    }
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(numberOfTiles * tileSize + borderSize * 2,
                numberOfTiles * tileSize + borderSize * 2);
    }
}