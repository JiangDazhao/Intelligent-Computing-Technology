
public class AIGobangTest2 {
    public static void main(String[] args) {
        final int MAX_DEPTH=2;
        final int BOARD_SIZE=5;
        final int MAX_VAL=10000;
        final int MIN_VAL=-10000;
        int[][] board={
                {1,0,0,0,0},
                {0,1,0,0,0},
                {0,0,2,0,0},
                {0,0,0,2,0},
                {0,0,0,0,2},
        };
        int value=Utils.getCost(board,1);
        System.out.println(value);
        Utils.printBoard(board);
    }
}
