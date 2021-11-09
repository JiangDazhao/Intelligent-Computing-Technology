/**
 * 用来表示当前棋盘的状态以及下棋的人
 */
public class BoardState{
    static final int MAX_DEPTH=2;
    static final int BOARD_SIZE=5;
    static final int MAX_VAL=10000;
    static final int MIN_VAL=-10000;

    int[][]board;
    int playerTurn;
    int depth;
    BoardState chosenState;
    int alpha=MIN_VAL;
    int beta=MAX_VAL;
    int value=0;

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
