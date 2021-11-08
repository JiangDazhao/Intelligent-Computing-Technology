public class Utils {
    static final int boardSize=5;
    static final int MAXVAL=10000;
    static final int MINVAL=-10000;

    /**
     * 棋盘当前状态的静态估值函数
     * @param boardState 棋盘状态
     * @param playerTurn 下棋的人 1为人 2为AI
     * @return
     */
    static int getCost (AIGobang.BoardState boardState, int playerTurn){
        int [][]board=boardState.getBoard();
        int myScore=0;
        int yourScore=0;
        int yourPlayerTurn=changePlayerTurn(playerTurn);
        int value;

        /**
         * 横纵估值
         */
        for(int i=0;i<boardSize;i++){
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
            boolean myRowWin=(board[i][0]==playerTurn)&&(board[i][1]==playerTurn)&&(board[i][2]==playerTurn)
                    &&(board[i][3]==playerTurn)&&(board[i][4]==playerTurn);
            if (myRowWin){
                return MAXVAL;
            }
            boolean myRowLose=(board[i][0]==yourPlayerTurn)&&(board[i][1]==yourPlayerTurn)&&(board[i][2]==yourPlayerTurn)
                    &&(board[i][3]==yourPlayerTurn)&&(board[i][4]==yourPlayerTurn);
            if (myRowLose){
                return MINVAL;
            }
            boolean myColumnWin=(board[0][i]==playerTurn)&&(board[1][i]==playerTurn)&&(board[2][i]==playerTurn)
                    &&(board[3][i]==playerTurn)&&(board[4][i]==playerTurn);
            if(myColumnWin){
                return MAXVAL;
            }
            boolean myColumnLose=(board[0][i]==yourPlayerTurn)&&(board[1][i]==yourPlayerTurn)&&(board[2][i]==yourPlayerTurn)
                    &&(board[3][i]==yourPlayerTurn)&&(board[4][i]==yourPlayerTurn);
            if(myColumnLose){
                return MINVAL;
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
        boolean myDiagonalWin=(board[0][0]==playerTurn&&board[1][1]==playerTurn&&board[2][2]==playerTurn
                &&board[3][3]==playerTurn&&board[4][4]==playerTurn)||
                (board[0][4]==playerTurn&&board[1][3]==playerTurn&&board[2][2]==playerTurn
                        &&board[3][1]==playerTurn&&board[4][0]==playerTurn);
        if(myDiagonalWin){
            return MAXVAL;
        }
        boolean myDiagonalLose=(board[0][0]==yourPlayerTurn&&board[1][1]==yourPlayerTurn&&board[2][2]==yourPlayerTurn
                &&board[3][3]==yourPlayerTurn&&board[4][4]==yourPlayerTurn)||
                (board[0][4]==yourPlayerTurn&&board[1][3]==yourPlayerTurn&&board[2][2]==yourPlayerTurn
                        &&board[3][1]==yourPlayerTurn&&board[4][0]==yourPlayerTurn);
        if(myDiagonalLose){
            return MINVAL;
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
}
