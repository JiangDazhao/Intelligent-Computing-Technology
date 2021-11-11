
public class AIGobangTest2 {
    public static void main(String[] args) {
        final int MAX_DEPTH=2;
        final int BOARD_SIZE=5;
        final int MAX_VAL=10000;
        final int MIN_VAL=-10000;
        int[][] board={
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
        };
        int value=Utils.getCost(board,1);
        Utils.printBoard(board);
    }
}
