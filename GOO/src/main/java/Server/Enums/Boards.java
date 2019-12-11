package Server.Enums;

    public enum Boards {
        SMALL,
        MEDIUM,
        NORMAL;

        /**
         * @param board: 0 -> player1 and bot; 1 -> player 1 and player 2;
         * @return
         */
        public static Boards get(int board) {
            switch (board) {
                case 0:
                    return SMALL;
                case 1:
                    return MEDIUM;
                case 2:
                    return NORMAL;
                default:
                    return NORMAL;
            }
        }
    }