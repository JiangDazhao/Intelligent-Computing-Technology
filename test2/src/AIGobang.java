import javafx.util.Pair;

import javax.rmi.CORBA.Util;
import java.util.Scanner;

public class AIGobang {
    /**
     * 五子棋
     */
    static final int BOARD_SIZE=5;
    static final int MAX_VAL=10000;
    static final int MIN_VAL=-10000;
    static final int DRAW=5000;
    static int[][] CHESS=new int[BOARD_SIZE][BOARD_SIZE];

    public static void main(String[] args) {
        int counter=0;
        System.out.println("**Human First");
        System.out.println("**Human is X");
        System.out.println("**AI is O");
        System.out.println("**Chess is as below..............");
        System.out.println("Chessboard No."+(counter++)+":"+"(The empty chessboard)");
        Utils.printBoard(CHESS);
        System.out.println("------------------Let's start!----------------------");
        BoardState wholeGame= new BoardState(CHESS,1,1,-1,-1,0,MIN_VAL,MAX_VAL);
        while (true){
            //Human
            int finishFlag;
            wholeGame.getScore();
            int nextMoveX=wholeGame.x;
            int nextMoveY=wholeGame.y;
            System.out.println("Next Human Position is "+"("+nextMoveX+","+nextMoveY+")");

            CHESS= Utils.generateNewBoard(CHESS,nextMoveX,nextMoveY,1);
            System.out.println("Chessboard No."+(counter++)+":");
            Utils.printBoard(CHESS);
            System.out.println();
            finishFlag=Utils.getCost(CHESS,1);
            if (finishFlag==MAX_VAL){
                System.out.println("The Winner Is Human!");
                return;
            }
            if (finishFlag==MIN_VAL){
                System.out.println("The Winner Is AI!");
                return;
            }
            if (finishFlag==DRAW){
                System.out.println("The Game Is Draw!");
                return;
            }

            //AI
            wholeGame= new BoardState(CHESS,2,2,-1,-1,0,MIN_VAL,MAX_VAL);
            try
            {
                Thread.sleep(1000);
            } catch (Exception e) {
            }

            wholeGame.getScore();
            nextMoveX=wholeGame.x;
            nextMoveY=wholeGame.y;
            System.out.println("Next AI Position is "+"("+nextMoveX+","+nextMoveY+")");
            CHESS= Utils.generateNewBoard(CHESS,nextMoveX,nextMoveY,2);
            System.out.println("Chessboard No."+(counter++)+":");
            Utils.printBoard(CHESS);
            System.out.println();
            if (finishFlag==MAX_VAL){
                System.out.println("The Winner Is AI!");
                return;
            }
            if (finishFlag==MIN_VAL){
                System.out.println("The Winner Is Human!");
                return;
            }
            if (finishFlag==DRAW){
                System.out.println("The Game Is Draw!");
                return;
            }

            //Human
            wholeGame= new BoardState(CHESS,1,1,-1,-1,0,MIN_VAL,MAX_VAL);
            try
            {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }



}
