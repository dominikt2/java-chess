package main;

import java.util.Random;
import java.util.ArrayList;

public class trebfish {

    static int[] whitePawnBestPosition = {0,0,0,0,0,0,0,0,
                                            50,50,50,50,50,50,50,50,
                                            10,10,20,30,30,20,10,10,
                                            5,5,10,25,25,10,5,5,
                                            0,0,0,20,20,0,0,0,
                                            5,-5,-10,0,0,-10,-5,5,
                                            5,10,10,-20,-20,10,10,5,
                                            0,0,0,0,0,0,0,0};

    static int[] blackPawnBestPosition = {0,0,0,0,0,0,0,0,
                                            5,10,10,-20,-20,10,10,5,
                                            5,-5,-10,0,0,-10,-5,5,
                                            0,0,0,20,20,0,0,0,
                                            5,5,10,25,25,10,5,5,
                                            10,10,20,30,30,20,10,10,
                                            50,50,50,50,50,50,50,50,
                                            0,0,0,0,0,0,0,0};
    static int[] knightBestPosition = {-50,-40,-30,-30,-30,-30,-40,-50,
                                        -40,-20,0,0,0,0,-20,-40,
                                        -30,0,10,15,15,10,0,-30,
                                        -30,5,15,20,20,15,5,-30,
                                        -30,0,15,20,20,15,0,-30,
                                        -30,5,10,15,15,10,5,-30,
                                        -40,-20,0,5,5,0,-20,-40,
                                        -50,-40,-30,-30,-30,-30,-40,-50};

    static int[] bishopBestPosition = {-20,-10,-10,-10,-10,-10,-10,-20,
                                        -10,0,0,0,0,0,0,-10,
                                        -10,0,5,10,10,5,0,-10,
                                        -10,5,5,10,10,5,5,-10,
                                        -10,0,10,10,10,10,0,-10,
                                        -10,10,10,10,10,10,10,-10,
                                        -10,5,0,0,0,0,5,-10,
                                        -20,-10,-10,-10,-10,-10,-10,-20};

    static int[] whiteRookBestPosition = {0,0,0,0,0,0,0,0,
                                            5,10,10,10,10,10,10,5,
                                            -5,0,0,0,0,0,0,-5,
                                            -5,0,0,0,0,0,0,-5,
                                            -5,0,0,0,0,0,0,-5,
                                            -5,0,0,0,0,0,0,-5,
                                            -5,0,0,0,0,0,0,-5,
                                            0,0,0,5,5,0,0,0};

    static int[] blackRookBestPosition = {0,0,0,5,5,0,0,0,
                                            -5,0,0,0,0,0,0,-5,
                                            -5,0,0,0,0,0,0,-5,
                                            -5,0,0,0,0,0,0,-5,
                                            -5,0,0,0,0,0,0,-5,
                                            -5,0,0,0,0,0,0,-5,
                                            5,10,10,10,10,10,10,5,
                                            0,0,0,0,0,0,0,0};

    static int[] queenBestPosition = {-20,-10,-10,-5,-5,-10,-10,-20,
                                        -10,0,0,0,0,0,0,-10,
                                        -10,0,5,5,5,5,0,-10,
                                        -5,0,5,5,5,5,0,-5,
                                        -5,0,5,5,5,5,0,-5,
                                        -10,0,5,5,5,5,0,-10,
                                        -10,0,0,0,0,0,0,-10,
                                        -20,-10,-10,-5,-5,-10,-10,-20};

    static int[] whiteKingBestPosition = {-30, -40, -40, -50, -50, -40, -40, -30,
                                            -30, -40, -40, -50, -50, -40, -40, -30,
                                            -30, -40, -40, -50, -50, -40, -40, -30,
                                            -30, -40, -40, -50, -50, -40, -40, -30,
                                            -20, -30, -30, -40, -40, -30, -30, -20,
                                            -10, -20, -20, -20, -20, -20, -20, -10,
                                            20, 20, 0, 0, 0, 0, 20, 20,
                                            20, 30, 10, 0, 0, 10, 30, 20};

    static int[] blackKingBestPosition = {20, 30, 10, 0, 0, 10, 30, 20,
                                            20, 20, 0, 0, 0, 0, 20, 20,
                                            -10, -20, -20, -20, -20, -20, -20, -10,
                                            -20, -30, -30, -40, -40, -30, -30, -20,
                                            -30, -40, -40, -50, -50, -40, -40, -30,
                                            -30, -40, -40, -50, -50, -40, -40, -30,
                                            -30, -40, -40, -50, -50, -40, -40, -30,
                                            -30, -40, -40, -50, -50, -40, -40, -30};

    public static double evaluateBoard(char[] board){
        double blackAdvantage = 0;
        double whiteAdvantage = 0;
        for(int i=0; i<64; i++){
            if(board[i] == 'P'){
                whiteAdvantage += 100 + whitePawnBestPosition[i];
            }else if(board[i] == 'R'){
                whiteAdvantage += 500 + whiteRookBestPosition[i];
            }else if(board[i] == 'N'){
                whiteAdvantage += 300 + knightBestPosition[i];
            }else if(board[i] == 'B'){
                whiteAdvantage += 300 + bishopBestPosition[i];
            }else if(board[i] == 'Q'){
                whiteAdvantage += 900 + queenBestPosition[i];
            }else if(board[i] == 'K'){
                whiteAdvantage += 1000 + whiteKingBestPosition[i];
            }else if(board[i] == 'p'){
                blackAdvantage += 100 + blackPawnBestPosition[i];
            }else if(board[i] == 'r'){
                blackAdvantage += 500 + blackRookBestPosition[i];
            }else if(board[i] == 'n'){
                blackAdvantage += 300 + knightBestPosition[i];
            }else if(board[i] == 'b'){
                blackAdvantage += 300 + bishopBestPosition[i];
            }else if(board[i] == 'q'){
                blackAdvantage += 900 + queenBestPosition[i];
            }else if(board[i] == 'k'){
                blackAdvantage += 1000 + blackKingBestPosition[i];
            }
        }
        return (blackAdvantage - whiteAdvantage);
    }

    public static char[] makeRandomMove(char[] board){
        Random random = new Random();
        char[] newBoard = board.clone();
        for(int i=0; i<100; i++){
            int randomPiece = random.nextInt(64);
            for(int j=0; j<64; j++){
                if(board[randomPiece] != ' '  && Character.isLowerCase(board[randomPiece]) && Game.validateMove(randomPiece,j,board) !=0){
                    Game.makeMove(randomPiece,j,newBoard);
                }
            }
        }
        return newBoard;
    }


    public static char[] makeAiMove(char[] board) {
        char[] bestBoard = board.clone();
        double bestEvaluation = -1000000;
        double newEvalution;
        for(int i=0; i<64; i++){
            for(int j=0; j<64; j++){
                if(board[i] != ' ' && Character.isLowerCase(board[i]) && Game.validateMove(i,j,board) != 0){
                    char[] newBoard = board.clone();
                    Game.makeMove(i,j,newBoard);
                    newEvalution = evaluateBoard(newBoard);
                    if(newEvalution > bestEvaluation){
                        bestEvaluation = newEvalution;
                        bestBoard = newBoard.clone();
                    }
                }
            }
        }
      return bestBoard;
    }




}
