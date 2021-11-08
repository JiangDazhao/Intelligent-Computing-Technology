
public class AIGobangTest2 {
    public static void main(String[] args) {
        final int maxDepth=2;
        final int boardSize=5;
        final int MAXVAL=10000;
        final int MINVAL=-10000;
        int[][] board={
                {1,0,0,0,0},
                {0,1,0,0,0},
                {0,0,2,0,0},
                {0,0,0,2,0},
                {0,0,0,0,2},
        };
        AIGobang.BoardState boardState=new AIGobang.BoardState(board,1,0,0);
        int value=Utils.getCost(boardState,1);
        System.out.println(value);
        boardState.setValue(value);
        System.out.println(boardState.getValue());
    }
}
