package Server.Enums;

public enum Players {
    PLAYER_ONE,
    PLAYER_TWO,
    BOTH;

    /**
     * @param player: 0 -> player1 and bot; 1 -> player 1 and player 2;
     * @return
     */
    public static Players get(int player) {
        switch (player) {
            case 0:
                return PLAYER_ONE;
            case 1:
                return PLAYER_TWO;
            case 2:
                return BOTH;
            default:
                return null;
        }
    }
}