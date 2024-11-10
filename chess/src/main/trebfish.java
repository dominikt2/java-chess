package main;

import java.util.Random;
import java.util.ArrayList;

public class trebfish {

    public static boolean isWhiteTurn = false;

    static int[] whitePawnBestPosition = {0, 0, 0, 0, 0, 0, 0, 0,
            50, 50, 50, 50, 50, 50, 50, 50,
            10, 10, 20, 30, 30, 20, 10, 10,
            5, 5, 10, 25, 25, 10, 5, 5,
            0, 0, 0, 20, 20, 0, 0, 0,
            5, -5, -10, 0, 0, -10, -5, 5,
            5, 10, 10, -20, -20, 10, 10, 5,
            0, 0, 0, 0, 0, 0, 0, 0};

    static int[] blackPawnBestPosition = {0, 0, 0, 0, 0, 0, 0, 0,
            5, 10, 10, -20, -20, 10, 10, 5,
            5, -5, -10, 0, 0, -10, -5, 5,
            0, 0, 0, 20, 20, 0, 0, 0,
            5, 5, 10, 25, 25, 10, 5, 5,
            10, 10, 20, 30, 30, 20, 10, 10,
            50, 50, 50, 50, 50, 50, 50, 50,
            0, 0, 0, 0, 0, 0, 0, 0};
    static int[] knightBestPosition = {-50, -40, -30, -30, -30, -30, -40, -50,
            -40, -20, 0, 0, 0, 0, -20, -40,
            -30, 0, 10, 15, 15, 10, 0, -30,
            -30, 5, 15, 20, 20, 15, 5, -30,
            -30, 0, 15, 20, 20, 15, 0, -30,
            -30, 5, 10, 15, 15, 10, 5, -30,
            -40, -20, 0, 5, 5, 0, -20, -40,
            -50, -40, -30, -30, -30, -30, -40, -50};

    static int[] bishopBestPosition = {-20, -10, -10, -10, -10, -10, -10, -20,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -10, 0, 5, 10, 10, 5, 0, -10,
            -10, 5, 5, 10, 10, 5, 5, -10,
            -10, 0, 10, 10, 10, 10, 0, -10,
            -10, 10, 10, 10, 10, 10, 10, -10,
            -10, 5, 0, 0, 0, 0, 5, -10,
            -20, -10, -10, -10, -10, -10, -10, -20};

    static int[] whiteRookBestPosition = {0, 0, 0, 0, 0, 0, 0, 0,
            5, 10, 10, 10, 10, 10, 10, 5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            0, 0, 0, 5, 5, 0, 0, 0};

    static int[] blackRookBestPosition = {0, 0, 0, 5, 5, 0, 0, 0,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            5, 10, 10, 10, 10, 10, 10, 5,
            0, 0, 0, 0, 0, 0, 0, 0};

    static int[] queenBestPosition = {-20, -10, -10, -5, -5, -10, -10, -20,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -10, 0, 5, 5, 5, 5, 0, -10,
            -5, 0, 5, 5, 5, 5, 0, -5,
            -5, 0, 5, 5, 5, 5, 0, -5,
            -10, 0, 5, 5, 5, 5, 0, -10,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -20, -10, -10, -5, -5, -10, -10, -20};

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

    public static int evaluateBoard(char[] board) {
        int blackAdvantage = 0;
        int whiteAdvantage = 0;
        for (int i = 0; i < 64; i++) {
            if (board[i] == ' ') {
                continue;
            }
            if (board[i] == 'P') {
                whiteAdvantage += 100 + whitePawnBestPosition[i];
            } else if (board[i] == 'R') {
                whiteAdvantage += 500 + whiteRookBestPosition[i];
            } else if (board[i] == 'N') {
                whiteAdvantage += 300 + knightBestPosition[i];
            } else if (board[i] == 'B') {
                whiteAdvantage += 300 + bishopBestPosition[i];
            } else if (board[i] == 'Q') {
                whiteAdvantage += 900 + queenBestPosition[i];
            } else if (board[i] == 'K') {
                whiteAdvantage += 1000 + whiteKingBestPosition[i];
            } else if (board[i] == 'p') {
                blackAdvantage += 100 + blackPawnBestPosition[i];
            } else if (board[i] == 'r') {
                blackAdvantage += 500 + blackRookBestPosition[i];
            } else if (board[i] == 'n') {
                blackAdvantage += 300 + knightBestPosition[i];
            } else if (board[i] == 'b') {
                blackAdvantage += 300 + bishopBestPosition[i];
            } else if (board[i] == 'q') {
                blackAdvantage += 900 + queenBestPosition[i];
            } else if (board[i] == 'k') {
                blackAdvantage += 1000 + blackKingBestPosition[i];
            }
        }
        return (whiteAdvantage - blackAdvantage);
    }

    public static char[] makeAiMove(char[] board) {
        char[] bestBoard = board.clone();
        int bestEvaluation = 10000;
        int newEvalution;
        int newWhiteEvaluation;

        int bestPieceIndex = 0;
        int bestMoveIndex = 0;

        for (int i = 0; i < 64; i++) {
            if (Character.isLowerCase(board[i])) {
                for (int j = 0; j < 64; j++) {
                    if (Game.validateMove(i, j, board) != 0) {
                        char[] newBoard = board.clone();
                        temporarMove(newBoard, i, j);
                        char[] blackBoard = newBoard.clone();
                        Game.isWhiteTurn = true;
                        isWhiteTurn = true;
                        makeWhiteAiMove(newBoard);
                        newEvalution = evaluateBoard(newBoard);
                        if (newEvalution < bestEvaluation) {
                            bestEvaluation = newEvalution;
                            bestBoard = blackBoard.clone();
                            bestPieceIndex = i;
                            bestMoveIndex = j;
                        }
                    }
                }
            }
        }
        setGameVariables(bestBoard, bestPieceIndex, bestMoveIndex);
        System.out.println("Best: " + bestEvaluation);
        return bestBoard;
    }

    public static int makeWhiteAiMove(char[] board){
        int blackAdvantage = evaluateBoard(board);
        char[] bestBoard = board.clone();
        int newEvalution;
        int bestEvaluation = -10000;
        char[] temp = board.clone();
        for(int i = 0; i < 64; i++){
            if(Character.isUpperCase(board[i])){
                for(int j = 0; j < 64; j++){
                    if(Game.validateMove(i, j, board) != 0){
                        char[] newBoard = board.clone();
                        temporarMove(newBoard, i, j);
                        newEvalution = evaluateBoard(newBoard);
                        if(newEvalution > bestEvaluation){
                            bestEvaluation = newEvalution;
                            temp = newBoard.clone();
                        }
                    }
                }
            }
        }
        for(int k = 0; k < 64; k++){
            System.out.print(temp[k]);
            if(k % 8 == 7){
                System.out.println();
            }
        }
        System.out.println("Best WHITE: " + bestEvaluation+"\n");
        Game.isWhiteTurn = false;
        isWhiteTurn = false;
        return bestEvaluation;
    }

    public static char[] temporarMove(char[] board, int pieceIndex, int moveIndex) {

        if(Game.validateMove(pieceIndex, moveIndex, board) == 1) {
            if (Game.pawnMove(board, pieceIndex, moveIndex) == 2 && Game.blackEnpassantMade) {
                board[moveIndex - 8] = ' ';
            }else if(isWhiteTurn && Game.pawnMove(board, pieceIndex, moveIndex) == 2 && Game.whiteEnpassantMade){
                board[moveIndex + 8] = ' ';
            }
            board[moveIndex] = board[pieceIndex];
            board[pieceIndex] = ' ';
        }else if(Game.validateMove(pieceIndex, moveIndex, board) == 2){
            board[moveIndex] = board[pieceIndex];
            board[pieceIndex + 1] = board[pieceIndex + 3];
            board[pieceIndex + 3] = ' ';
            board[pieceIndex] = ' ';
        }else if(Game.validateMove(pieceIndex, moveIndex, board) == 3) {
            board[moveIndex] = board[pieceIndex];
            board[pieceIndex - 1] = board[pieceIndex - 4];
            board[pieceIndex - 4] = ' ';
            board[pieceIndex] = ' ';
        }else if(Game.validateMove(pieceIndex, moveIndex, board) == 4) {
            board[moveIndex] = 'q';
            board[pieceIndex] = ' ';
        }else if(isWhiteTurn && Game.validateMove(pieceIndex, moveIndex, board) == 4) {
            board[moveIndex] = 'Q';
            board[pieceIndex] = ' ';
        }
        return board;
    }

    public static void setGameVariables(char[] board, int pieceIndex, int moveIndex){
        if(Game.validateMove(pieceIndex, moveIndex, board) == 1) {
            if (!Game.isWhiteTurn && moveIndex / 8 == 3 && board[pieceIndex] == 'p' && pieceIndex / 8 == 1) {
                Game.whiteEnpassatnt[0] = 1;
                Game.whiteEnpassatnt[1] = moveIndex;
            }
            if (Game.blackEnpassantMade && Game.pawnMove(board, pieceIndex, moveIndex) == 2) {
                Game.blackEnpassantMade = false;
            }
            if(board[moveIndex] == 'k'){
                Game.blackKingMoved++;
            }else if(board[moveIndex] == 'r'){
                if(moveIndex == 0){
                    Game.blackRookMoved[0]++;
                }else if(moveIndex == 7){
                    Game.blackRookMoved[1]++;
                }
            }
        }
    }
}

