package Bot;

public class Bot {
    private String name;

    public Bot() {
        setName();
    }

    public void setName() {
        this.name = String.format("Bot #%d", System.nanoTime()/10000);
    }

    public String getName() {
        return name;
    }
}