package Server;

import java.awt.*;

public class ExtractedGrid {

    private static int size;
    private int[][] positions;

    public ExtractedGrid(int size){
        this.size = size;
        this.positions = new int[size][size];
    }

    public void setPosition(int x, int y, Color c){
        if(c == Color.BLACK) this.positions[x][y] = 1;
        else if(c == Color.WHITE) this.positions[x][y] = -1;
        else this.positions[x][y] = 0;
    }


    @Override
    public String toString() {
        String result = size + "/";
        for (int i=0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                result += positions[i][j] + "/";
            }
        }
        return result;
    }

    public static int[][] fromString(String input){
        String[] parts = input.split("/");
        int s = Integer.parseInt(parts[0]);
        int[][] temp = new int[s][s];
        for (int i=0; i<s; i++) {
            for (int j = 0; j < s; j++) {
                temp[i][j] = Integer.parseInt(parts[i*s+j+1]);
            }
        }
        return temp;
    }
}
