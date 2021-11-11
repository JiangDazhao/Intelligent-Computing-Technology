import javafx.util.Pair;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 用来表示当前棋盘的状态
 * @author jiangxuzhao
 * @date 2021/11/11
 */
public class BoardState{
    static final int MAX_DEPTH=2;
    static final int BOARD_SIZE=5;
    static final int MAX_VAL=10000;
    static final int MIN_VAL=-10000;
    static final int DRAW=5000;

    int[][]board;
    //生成博弈树的人
    int mainPlayerTurn;
    //要落子的人
    int playerTurn;
    int x;
    int y;

    public BoardState(int[][] board, int mainPlayerTurn, int playerTurn, int x, int y, int depth, int alpha, int beta, int value) {
        this.board = board;
        this.mainPlayerTurn = mainPlayerTurn;
        this.playerTurn = playerTurn;
        this.x = x;
        this.y = y;
        this.depth = depth;
        this.alpha = alpha;
        this.beta = beta;
        this.value = value;
    }

    int depth;
    int alpha=MIN_VAL;
    int beta=MAX_VAL;
    int value=0;


    /**
     * 进行递归搜索
     * @return
     */
    private int getScore(){

        //终结游戏
        int winnerCheck=Utils.getCost(this.board,this.mainPlayerTurn);
        if (winnerCheck==MAX_VAL){
            return MAX_VAL;
        }
        if (winnerCheck==MIN_VAL){
            return MIN_VAL;
        }
        if (winnerCheck==DRAW){
            return 0;
        }

        //限制递归深度
        if (this.depth>=MAX_DEPTH){
            return Utils.getCost(this.board,this.mainPlayerTurn);
        }

        ArrayList<Pair<Integer,Integer>> availablePos=Utils.getAvailablePos(this.board);

        //max结点，返回的是子节点的最大值
        if (this.playerTurn==mainPlayerTurn){
            int maxScore=MIN_VAL;
            int maxIndex=0;
            for (int i=0;i<availablePos.size();i++){
                Pair<Integer, Integer>pos=availablePos.get(i);
                //max结点刚刚落子
                int x=pos.getKey();
                int y=pos.getValue();
                int [][] newboard= Utils.generateNewBoard(this.board,x,y,mainPlayerTurn);
                BoardState childState = new BoardState(newboard,this.mainPlayerTurn,
                        Utils.changePlayerTurn(this.playerTurn),-1,-1,this.depth+1,this.alpha,this.beta,0);
                int childScore=childState.getScore();

                if (childScore>maxScore){
                    maxScore=childScore;
                    maxIndex=i;
                    this.x=childState.x;
                    this.y=childState.y;
                    this.alpha=maxScore;
                }

                //剪枝
                if (this.alpha>=this.beta){
                    break;
                }
            }
            return maxScore;
        }

        //min结点，返回的是子节点最小值
        if(this.playerTurn==Utils.changePlayerTurn(mainPlayerTurn)){

        }
    }

}
