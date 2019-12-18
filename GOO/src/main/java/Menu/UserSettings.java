package Menu;

import Bot.Bot;
import Server.Enums.Boards;
import Server.Enums.Players;

public class UserSettings {
    /*public static Players players;
    public static String color;
    public static Boards boards;*/

    private int size;
    private int players;
    private String nick;
    private Bot bot;
    private boolean haveBot = false;

    void setSize(int s) {
        this.size = s;
    }

    void setPlayersCount(int p) {
        this.players = p;
    }

    void setNick(String n) {
        if (n.isEmpty()) {
            this.nick = "Player" + ((int) Math.floor(1 + Math.random() * 999));
        } else {
            this.nick = n;
        }
    }

    public Bot getBot() {
        return this.bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
        this.haveBot = true;
    }

    public boolean haveBot() {
        return this.haveBot;
    }

    public int getSize() {
        return this.size;
    }

    public int getPlayersCount() {
        return this.players;
    }

    public String getNick() {
        return this.nick;
    }
}