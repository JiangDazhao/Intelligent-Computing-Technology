import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {
    static final int BOARD_SIZE=5;
    static final int MAX_VAL=10000;
    static final int MIN_VAL=-10000;
    static final int DRAW=5000;

    /**
     * 棋盘的静态估值函数
     * @param board
     * @param playerTurn 估值的对象
     * @return
     */
    static int getCost (int [][] board, int playerTurn){
        int myScore=0;
        int yourScore=0;
        int yourPlayerTurn=changePlayerTurn(playerTurn);
        int value;
        boolean myRowWin=false;
        boolean myRowLose=false;
        boolean myColumnWin=false;
        boolean myColumnLose=false;
        boolean myDiagonalWin=false;
        boolean myDiagonalLose=false;
        /**
         * 横纵估值
         */
        for(int i=0;i<BOARD_SIZE;i++){
            boolean myRowExist=(board[i][0]==0||board[i][0]==playerTurn)&&(board[i][1]==0||board[i][1]==playerTurn)
                    &&(board[i][2]==0||board[i][2]==playerTurn)&&(board[i][3]==0||board[i][3]==playerTurn)
                    &&(board[i][4]==0||board[i][4]==playerTurn);
            if(myRowExist) {
                myScore++;
            }
            boolean yourRowExist=(board[i][0]==0||board[i][0]==yourPlayerTurn)&&(board[i][1]==0||board[i][1]==yourPlayerTurn)
                    &&(board[i][2]==0||board[i][2]==yourPlayerTurn)&&(board[i][3]==0||board[i][3]==yourPlayerTurn)
                    &&(board[i][4]==0||board[i][4]==yourPlayerTurn);
            if(yourRowExist){
                yourScore++;
            }
            boolean myColumnExist=(board[0][i]==0||board[0][i]==playerTurn)&&(board[1][i]==0||board[1][i]==playerTurn)
                    &&(board[2][i]==0||board[2][i]==playerTurn)&&(board[3][i]==0||board[3][i]==playerTurn)
                    &&(board[4][i]==0||board[4][i]==playerTurn);
            if(myColumnExist){
                myScore++;
            }
            boolean yourColumnExist=(board[0][i]==0||board[0][i]==yourPlayerTurn)&&(board[1][i]==0||board[1][i]==yourPlayerTurn)
                    &&(board[2][i]==0||board[2][i]==yourPlayerTurn)&&(board[3][i]==0||board[3][i]==yourPlayerTurn)
                    &&(board[4][i]==0||board[4][i]==yourPlayerTurn);
            if(yourColumnExist){
                yourScore++;
            }

            /**
             * 双方胜利横纵判断
             */
            myRowWin=(board[i][0]==playerTurn)&&(board[i][1]==playerTurn)&&(board[i][2]==playerTurn)
                    &&(board[i][3]==playerTurn)&&(board[i][4]==playerTurn);
            if (myRowWin){
                return MAX_VAL;
            }
            myRowLose=(board[i][0]==yourPlayerTurn)&&(board[i][1]==yourPlayerTurn)&&(board[i][2]==yourPlayerTurn)
                    &&(board[i][3]==yourPlayerTurn)&&(board[i][4]==yourPlayerTurn);
            if (myRowLose){
                return MIN_VAL;
            }
            myColumnWin=(board[0][i]==playerTurn)&&(board[1][i]==playerTurn)&&(board[2][i]==playerTurn)
                    &&(board[3][i]==playerTurn)&&(board[4][i]==playerTurn);
            if(myColumnWin){
                return MAX_VAL;
            }
            myColumnLose=(board[0][i]==yourPlayerTurn)&&(board[1][i]==yourPlayerTurn)&&(board[2][i]==yourPlayerTurn)
                    &&(board[3][i]==yourPlayerTurn)&&(board[4][i]==yourPlayerTurn);
            if(myColumnLose){
                return MIN_VAL;
            }
        }

        /**
         * 斜估值
         */
        boolean myDiagonalExist=((board[0][0]==0||board[0][0]==playerTurn)&&(board[1][1]==0||board[1][1]==playerTurn)
                &&(board[2][2]==0||board[2][2]==playerTurn)&&(board[3][3]==0||board[3][3]==playerTurn)
                &&(board[4][4]==0||board[4][4]==playerTurn))||
                ((board[0][4]==0||board[0][4]==playerTurn)&&(board[1][3]==0||board[1][3]==playerTurn)
                        &&(board[2][2]==0||board[2][2]==playerTurn)&&(board[3][1]==0||board[3][1]==playerTurn)
                        &&(board[4][0]==0||board[4][0]==playerTurn));
        if(myDiagonalExist){
            myScore++;
        }
        boolean yourDiagonalExist=((board[0][0]==0||board[0][0]==yourPlayerTurn)&&(board[1][1]==0||board[1][1]==yourPlayerTurn)
                &&(board[2][2]==0||board[2][2]==yourPlayerTurn)&&(board[3][3]==0||board[3][3]==yourPlayerTurn)
                &&(board[4][4]==0||board[4][4]==yourPlayerTurn))||
                ((board[0][4]==0||board[0][4]==yourPlayerTurn)&&(board[1][3]==0||board[1][3]==yourPlayerTurn)
                        &&(board[2][2]==0||board[2][2]==yourPlayerTurn)&&(board[3][1]==0||board[3][1]==yourPlayerTurn)
                        &&(board[4][0]==0||board[4][0]==yourPlayerTurn));
        if(yourDiagonalExist){
            yourScore++;
        }
        /**
         * 双方胜利斜判断
         */
        myDiagonalWin=(board[0][0]==playerTurn&&board[1][1]==playerTurn&&board[2][2]==playerTurn
                &&board[3][3]==playerTurn&&board[4][4]==playerTurn)||
                (board[0][4]==playerTurn&&board[1][3]==playerTurn&&board[2][2]==playerTurn
                        &&board[3][1]==playerTurn&&board[4][0]==playerTurn);
        if(myDiagonalWin){
            return MAX_VAL;
        }
        myDiagonalLose=(board[0][0]==yourPlayerTurn&&board[1][1]==yourPlayerTurn&&board[2][2]==yourPlayerTurn
                &&board[3][3]==yourPlayerTurn&&board[4][4]==yourPlayerTurn)||
                (board[0][4]==yourPlayerTurn&&board[1][3]==yourPlayerTurn&&board[2][2]==yourPlayerTurn
                        &&board[3][1]==yourPlayerTurn&&board[4][0]==yourPlayerTurn);
        if(myDiagonalLose){
            return MIN_VAL;
        }
        /**
         * 平局判断
         */
        int full=1;
        for(int i=0;i<BOARD_SIZE;i++){
            for(int j=0;j<BOARD_SIZE;j++){
                if(board[i][j]==0){
                    full=0;
                }
            }
        }
        if(full==1&&!myRowWin&&!myRowLose&&!myColumnWin&&!myColumnLose&&!myDiagonalWin&&!myDiagonalLose){
            return 5000;
        }
//        System.out.println("myscore is:"+myScore);
//        System.out.println("yourscore is:"+yourScore);
        value = myScore - yourScore;
        return value;
    }

    /**
     * 改变下棋的人
     * @param playerTurn 下棋的人 1为人 2为AI
     * @return
     */
    static int changePlayerTurn(int playerTurn){
        return (playerTurn == 1) ? 2 : 1;
    }

    /**
     * 可以下棋的位置
     * @param board
     * @return
     */
    static ArrayList<Pair<Integer,Integer>> getAvailablePos(int [][]board){
        ArrayList<Pair<Integer,Integer>> result=new ArrayList<Pair<Integer,Integer>>();
        int count=0;
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board[0].length;j++){
                if (board[i][j]==0) {
                    result.add(new Pair<>(i,j));
                }
            }
        }
        return result;
    }

    /**
     * 落子后的新棋盘
     * @param board
     * @param row
     * @param column
     * @param playerTurn
     * @return
     */
    static int[][] generateNewBoard(int[][]board,int row,int column,int playerTurn){
        int[][] newBoard;
        newBoard=Utils.cloneBoard(board);
        newBoard[row][column]=playerTurn;
        return newBoard;
    }

    /**
     * 打印棋盘
     * @param board
     */
    static void printBoard(int[][]board){
        System.out.println(" __________________________");
        for (int i=0;i<BOARD_SIZE;i++){
            for (int j=0;j<BOARD_SIZE;j++){
                System.out.print(" | " + Utils.numToChar(board[i][j])+" ");
            }
            System.out.println(" |");
            System.out.println(" __________________________");
        }
    }

    /**
     * 数组转换为字符
     * @param playerTurn
     * @return
     */
    static char numToChar(int playerTurn){
        return (playerTurn==1)? 'O':'X';
    }

    /**
     * 克隆棋盘
     * @param fromBoard
     * @return
     */
    static int[][] cloneBoard(int [][] fromBoard){
        int[][] newBoard = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i=0;i<fromBoard.length;i++){
            System.arraycopy(fromBoard[i],0,newBoard[i],0,newBoard[i].length);
        }
        return newBoard;
    }
}
