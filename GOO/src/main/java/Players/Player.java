package Players;

import java.awt.*;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Scanner;

public interface Player {

    void setInputOutputStream(ServerSocket listener, Scanner sc, PrintWriter pw);
    void setColor(Color c);

    Scanner getInputStream();
    PrintWriter getOutputStream();
    String getNickname();
    Color getColor();

}
