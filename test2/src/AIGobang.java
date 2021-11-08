public class AIGobang {
    /**
     * 五子棋
     */
    final int maxDepth=2;
    final int boardSize=5;
    final int MAXVAL=10000;
    final int MINVAL=-10000;
    int[][] board=new int[boardSize][boardSize];

    /**
     * 用来表示当前棋盘的状态以及下棋的人
     */
    static class BoardState{
        int[][]board;
        int playerTurn;
        int depth;
        int value;

        public int[][] getBoard() {
            return board;
        }

        public void setBoard(int[][] board) {
            this.board = board;
        }

        public int getPlayerTurn() {
            return playerTurn;
        }

        public void setPlayerTurn(int playerTurn) {
            this.playerTurn = playerTurn;
        }

        public int getDepth() {
            return depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public BoardState(int[][] board, int playerTurn, int depth, int value) {
            this.board = board;
            this.playerTurn = playerTurn;
            this.depth = depth;
            this.value = value;
        }
    }


}
