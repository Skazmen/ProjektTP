package Menu;

import javax.swing.*;
import java.awt.*;

public class Menu //extends JFrame
{
    public static void main(String[] args) {
        MenuGui started = new MenuGui();
        started.setVisible(true);

        //center window
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int axisX = (int) ((dimension.getWidth() - started.getWidth()) / 2);
        int axisY = (int) ((dimension.getHeight() - started.getHeight()) / 2);
        started.setLocation(axisX, axisY);
    }
}