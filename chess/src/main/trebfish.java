package main;

import java.util.Random;
import java.util.ArrayList;

public class trebfish {

    public static boolean whiteEnpassantMade = false;
    public static boolean blackEnpassantMade = false;

    public static char[] bestMove = new char[64];
    public static boolean isWhiteTurn = false;

    static int[] whitePawnBestPosition = {50, 50, 50, 50, 50, 50, 50, 50,
            50, 50, 50, 50, 50, 50, 50, 50,
            10, 10, 20, 30, 30, 20, 10, 10,
            5, 5, 10, 25, 25, 10, 5, 5,
            0, 0, 0, 25, 25, 0, 0, 0,
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
            50, 50, 50, 50, 50, 50, 50, 50};
    static int[] knightBestPosition = {
                -50, -40, -30, -30, -30, -30, -40, -50,
                -40, -20, 0, 5, 5, 0, -20, -40,
                -30, 5, 15, 20, 20, 15, 5, -30,
                -30, 10, 20, 25, 25, 20, 10, -30,
                -30, 5, 20, 25, 25, 20, 5, -30,
                -30, 10, 15, 20, 20, 15, 10, -30,
                -40, -20, 5, 10, 10, 5, -20, -40,
                -50, -40, -30, -30, -30, -30, -40, -50
    };

    static int[] bishopBestPosition = {
            -20, -10, -10, -10, -10, -10, -10, -20,
            -10, 0, 0, 5, 5, 0, 0, -10,
            -10, 0, 10, 15, 15, 10, 0, -10,
            -10, 5, 10, 20, 20, 10, 5, -10,
            -10, 0, 15, 20, 20, 15, 0, -10,
            -10, 10, 10, 15, 15, 10, 10, -10,
            -10, 0, 5, 5, 5, 5, 0, -10,
            -20, -10, -10, -10, -10, -10, -10, -20
    };

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
        return (blackAdvantage - whiteAdvantage);
    }

    public static int minimax(char[] board, int depth, int alpha, int beta,  boolean isMaximizing) {
        if (depth == 0) {
            int evaluation = evaluateBoard(board);
            return evaluation;
        }
        if (isMaximizing) {
            isWhiteTurn = false;
            int maxEvaluation = Integer.MIN_VALUE;
            for (int i = 0; i < 64; i++) {
                if (Character.isLowerCase(board[i])) {
                    for (int j = 0; j < 64; j++) {
                        if (validateMove(i, j, board) != 0) {
                            char[] newBoard = board.clone();
                            temporarMove(newBoard, i, j);
                            int eval = minimax(newBoard, depth - 1, alpha, beta,  !isMaximizing);
                            if(eval > maxEvaluation){
                                maxEvaluation = eval;
                            }
                            alpha = Math.max(alpha, eval);
                            if (beta <= alpha) {
                                break;
                            }
                            System.out.println("Current maxEvaluation: " + maxEvaluation);
                        }
                    }
                }
            }
            return maxEvaluation;
        } else {
            isWhiteTurn = true;
            int minEvaluation = Integer.MAX_VALUE;
            for (int i = 0; i < 64; i++) {
                if (Character.isUpperCase(board[i])) {
                    for (int j = 0; j < 64; j++) {
                        if (validateMove(i, j, board) != 0) {
                            char[] newBoard = board.clone();
                            temporarMove(newBoard, i, j);
                            int eval = minimax(newBoard, depth - 1, alpha, beta,  !isMaximizing);
                            if(eval < minEvaluation){
                                minEvaluation = eval;
                            }
                            beta = Math.min(beta, eval);
                            if (beta <= alpha) {
                                break;
                            }
                            System.out.println("Current minEvaluation: " + minEvaluation);
                        }
                    }
                }
            }
            return minEvaluation;
        }
    }



    public static char[] makeAiMove(char[] board) {
        char[] bestBoard = board.clone();
        int bestEvaluation = Integer.MIN_VALUE;
        int bestPieceIndex = -1;
        int bestMoveIndex = -1;

        for (int i = 0; i < 64; i++) {
            if (Character.isLowerCase(board[i])) {
                for (int j = 0; j < 64; j++) {
                    if (validateMove(i, j, board) != 0) {
                        char[] newBoard = board.clone();
                        temporarMove(newBoard, i, j);
                        int evaluation = minimax(newBoard, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                        if (evaluation > bestEvaluation) {
                            bestEvaluation = evaluation;
                            bestPieceIndex = i;
                            bestMoveIndex = j;
                            bestBoard = newBoard.clone();
                        }
                    }
                }
            }
        }

        if (bestPieceIndex != -1 && bestMoveIndex != -1) {
            setGameVariables(bestBoard, bestPieceIndex, bestMoveIndex);
        }
        System.out.println("Best move evaluation: " + bestEvaluation);
        return bestBoard;
    }


    public static char[] tempMove(char[] board, int pieceIndex, int moveIndex) {
        char[] tempBoard = board.clone();
        tempBoard[moveIndex] = tempBoard[pieceIndex];
        tempBoard[pieceIndex] = ' ';
        return tempBoard;
    }

    public static char[] temporarMove(char[] board, int pieceIndex, int moveIndex) {

        if(validateMove(pieceIndex, moveIndex, board) == 1) {
            if (pawnMove(board, pieceIndex, moveIndex) == 2 && Game.blackEnpassantMade) {
                board[moveIndex - 8] = ' ';
            }else if(isWhiteTurn && pawnMove(board, pieceIndex, moveIndex) == 2 && Game.whiteEnpassantMade){
                board[moveIndex + 8] = ' ';
            }
            board[moveIndex] = board[pieceIndex];
            board[pieceIndex] = ' ';
            if(moveIndex <= 7 && board[moveIndex] == 'P') {
                board[moveIndex] = 'Q';
            }else if(moveIndex >= 56 && board[moveIndex] == 'p') {
                board[moveIndex] = 'q';
            }
        }else if(validateMove(pieceIndex, moveIndex, board) == 2){
            board[moveIndex] = board[pieceIndex];
            board[pieceIndex + 1] = board[pieceIndex + 3];
            board[pieceIndex + 3] = ' ';
            board[pieceIndex] = ' ';
        }else if(validateMove(pieceIndex, moveIndex, board) == 3) {
            board[moveIndex] = board[pieceIndex];
            board[pieceIndex - 1] = board[pieceIndex - 4];
            board[pieceIndex - 4] = ' ';
            board[pieceIndex] = ' ';
        }
        return board;
    }

    public static void setGameVariables(char[] board, int pieceIndex, int moveIndex){
        if(validateMove(pieceIndex, moveIndex, board) == 1) {
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
    public static boolean isKingChecked(char[] board, int kingPosition) {
        if(isWhiteTurn){
            for(int j=0; j<64; j++){
                if(board[j] == 'p' && pawnMove(board, j, kingPosition) == 1){
                    return true;
                }else if(board[j] == 'r' && (horizontalMove(board, j, kingPosition) || verticalMove(board, j, kingPosition))){
                    return true;
                }else if(board[j] == 'n' && knightMove(board, j, kingPosition)){
                    return true;
                }else if(board[j] == 'b' && diagonalMove(board, j, kingPosition)){
                    return true;
                }else if(board[j] == 'q' && (horizontalMove(board, j, kingPosition) || verticalMove(board, j, kingPosition) || diagonalMove(board, j, kingPosition))){
                    return true;
                }else if(board[j] == 'k' && kingMove(board, j, kingPosition) != 0) {
                    return true;
                }
            }
        }else{
            for(int j=0; j<64; j++){
                if(board[j] == 'P' && pawnMove(board, j, kingPosition) == 1){
                    return true;
                }else if(board[j] == 'R' && (horizontalMove(board, j, kingPosition) || verticalMove(board, j, kingPosition))){
                    return true;
                }else if(board[j] == 'N' && knightMove(board, j, kingPosition)){
                    return true;
                }else if(board[j] == 'B' && diagonalMove(board, j, kingPosition)){
                    return true;
                }else if(board[j] == 'Q' && (horizontalMove(board, j, kingPosition) || verticalMove(board, j, kingPosition) || diagonalMove(board, j, kingPosition))){
                    return true;
                }else if(board[j] == 'K' && kingMove(board, j, kingPosition) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canPieceMove(char[] board, int pieceIndex, int moveIndex) {
        char[] tempBoard = board.clone();
        tempBoard = Game.makeTemporarMove(tempBoard, pieceIndex, moveIndex);
        for(int i=0; i<64; i++){
            if(isWhiteTurn && tempBoard[i] == 'K'){
                int kingPosition = i;
                if(isKingChecked(tempBoard, kingPosition)){
                    return false;
                }
            }else if(!isWhiteTurn && tempBoard[i] == 'k') {
                int kingPosition = i;
                if (isKingChecked(tempBoard, kingPosition)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean canPawnEnpassant(char[] board, int pieceIndex, int moveIndex){
        char[] tempBoard = board.clone();
        if(tempBoard[pieceIndex] == 'P') {
            tempBoard[moveIndex] = tempBoard[pieceIndex];
            tempBoard[pieceIndex] = ' ';
            tempBoard[moveIndex + 8] = ' ';
            for(int i=0; i<64; i++) {
                if (isWhiteTurn && tempBoard[i] == 'K') {
                    int kingPosition = i;
                    if (isKingChecked(tempBoard, kingPosition)) {
                        return false;
                    }
                }
            }
        }else if(tempBoard[pieceIndex] == 'p'){
            tempBoard[moveIndex] = tempBoard[pieceIndex];
            tempBoard[pieceIndex] = ' ';
            tempBoard[moveIndex - 8] = ' ';
            for(int i=0; i<64; i++) {
                if (!isWhiteTurn && tempBoard[i] == 'k') {
                    int kingPosition = i;
                    if (isKingChecked(tempBoard, kingPosition)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int validateMove(int pieceIndex, int moveIndex, char[] board) {
        char piece = board[pieceIndex];
            if(piece == 'P' && pawnMove(board, pieceIndex, moveIndex) != 0) {
                if(pawnMove(board, pieceIndex, moveIndex) == 1 && canPieceMove(board, pieceIndex, moveIndex)){
                    return 1;
                }else if(pawnMove(board, pieceIndex, moveIndex) == 2 && canPawnEnpassant(board, pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'R' && (horizontalMove(board,pieceIndex, moveIndex) || verticalMove(board,pieceIndex, moveIndex))) {
                if(canPieceMove(board, pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'Q' && (horizontalMove(board,pieceIndex, moveIndex) || verticalMove(board,pieceIndex, moveIndex) || diagonalMove(board,pieceIndex, moveIndex))) {
                if(canPieceMove(board, pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'N' && knightMove(board,pieceIndex, moveIndex)) {
                if(canPieceMove(board, pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'B' && diagonalMove(board,pieceIndex, moveIndex)) {
                if(canPieceMove(board, pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'K' && kingMove(board,pieceIndex, moveIndex) != 0) {
                if(canPieceMove(board, pieceIndex, moveIndex)){
                    return kingMove(board,pieceIndex, moveIndex);
                }
            }

            if (piece == 'p' && pawnMove(board,pieceIndex, moveIndex) != 0) {
                if(pawnMove(board, pieceIndex, moveIndex) == 1 && canPieceMove(board, pieceIndex, moveIndex)){
                    return 1;
                }else if(pawnMove(board, pieceIndex, moveIndex) == 2 && canPawnEnpassant(board, pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'r' && (horizontalMove(board,pieceIndex, moveIndex) || verticalMove(board,pieceIndex, moveIndex))) {
                if(canPieceMove(board, pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'q' && (horizontalMove(board,pieceIndex, moveIndex) || verticalMove(board,pieceIndex, moveIndex) || diagonalMove(board,pieceIndex, moveIndex))) {
                if(canPieceMove(board, pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'n' && knightMove(board,pieceIndex, moveIndex)) {
                if(canPieceMove(board, pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'b' && diagonalMove(board,pieceIndex, moveIndex)) {
                if(canPieceMove(board, pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'k' && kingMove(board,pieceIndex, moveIndex) != 0) {
                if(canPieceMove(board, pieceIndex, moveIndex)) {
                    return kingMove(board, pieceIndex, moveIndex);
                }
            }

        return 0;
    }


    public static boolean horizontalMove(char[] board, int pieceIndex, int moveIndex) {
        if (pieceIndex / 8 == moveIndex / 8) {
            if (pieceIndex < moveIndex) {
                for (int i = pieceIndex + 1; i < moveIndex; i++) {
                    if (board[i] != ' ') {
                        return false;
                    }
                }
            } else if (pieceIndex > moveIndex) {
                for (int i = pieceIndex - 1; i > moveIndex; i--) {
                    if (board[i] != ' ') {
                        return false;
                    }
                }
            }
            if (board[moveIndex] == ' ' || Character.isLowerCase(board[moveIndex]) != Character.isLowerCase(board[pieceIndex])) {
                return true;
            }
        }
        return false;
    }

    public static boolean verticalMove(char[] board, int pieceIndex, int moveIndex) {
        if (pieceIndex % 8 == moveIndex % 8) {
            if (pieceIndex < moveIndex) {
                for (int i = pieceIndex + 8; i < moveIndex; i += 8) {
                    if (board[i] != ' ') {
                        return false;
                    }
                }
            } else if (pieceIndex > moveIndex) {
                for (int i = pieceIndex - 8; i > moveIndex; i -= 8) {
                    if (board[i] != ' ') {
                        return false;
                    }
                }
            }
            if (board[moveIndex] == ' ' || Character.isLowerCase(board[moveIndex]) != Character.isLowerCase(board[pieceIndex])) {
                return true;
            }
        }
        return false;
    }

    public static boolean diagonalMove(char[] board, int pieceIndex, int moveIndex) {
        int rowDiff = Math.abs(pieceIndex / 8 - moveIndex / 8);
        int colDiff = Math.abs(pieceIndex % 8 - moveIndex % 8);

        if (rowDiff == colDiff) {
            int rowDirection = (moveIndex / 8 > pieceIndex / 8) ? 1 : -1;
            int colDirection = (moveIndex % 8 > pieceIndex % 8) ? 1 : -1;

            int currentRow = pieceIndex / 8 + rowDirection;
            int currentCol = pieceIndex % 8 + colDirection;

            while (currentRow != moveIndex / 8 || currentCol != moveIndex % 8) {
                int currentIndex = currentRow * 8 + currentCol;
                if (currentIndex < 0 || currentIndex >= 64 || board[currentIndex] != ' ') {
                    return false;
                }
                currentRow += rowDirection;
                currentCol += colDirection;
            }

            if (board[moveIndex] == ' ' || Character.isLowerCase(board[moveIndex]) != Character.isLowerCase(board[pieceIndex])) {
                return true;
            }
        }
        return false;
    }
    public static boolean knightMove(char[] board, int pieceIndex, int moveIndex) {
        if(Math.abs(pieceIndex / 8 - moveIndex / 8) == 2 && Math.abs(pieceIndex % 8 - moveIndex % 8) == 1 ||
                (Math.abs(pieceIndex / 8 - moveIndex / 8) == 1 && Math.abs(pieceIndex % 8 - moveIndex % 8) == 2)){
            if(board[moveIndex] == ' ' || Character.isLowerCase(board[moveIndex]) != Character.isLowerCase(board[pieceIndex])){
                return true;
            }
        }
        return false;
    }

    public static int kingMove(char[] board, int pieceIndex, int moveIndex) {
        if (Math.abs(pieceIndex / 8 - moveIndex / 8) <= 1 && Math.abs(pieceIndex % 8 - moveIndex % 8) <= 1) {
            if (board[moveIndex] == ' ' || Character.isLowerCase(board[moveIndex]) != Character.isLowerCase(board[pieceIndex])) {
                return 1;
            }
        }else if((Game.whiteKingMoved == 0 && Game.whiteRookMoved[1] == 0) && pieceIndex == 60 && moveIndex == 62 && board[61] == ' ' && board[62] == ' ' && board[63] == 'R' && (!isKingChecked(board, 60)) && !isKingChecked(board, 61) && !isKingChecked(board, 62)){
            return 2;
        }else if(Game.whiteKingMoved == 0 && Game.whiteRookMoved[0] == 0 && pieceIndex == 60 && moveIndex == 58 && board[59] == ' ' && board[58] == ' ' && board[57] == ' ' && board[56] == 'R' && (!isKingChecked(board, 60)) && !isKingChecked(board, 59) && !isKingChecked(board, 58)) {
            return 3;
        }else if((Game.blackKingMoved == 0 && Game.blackRookMoved[1] == 0) && pieceIndex == 4 && moveIndex == 6 && board[5] == ' ' && board[6] == ' ' && board[7] == 'r' && (!isKingChecked(board, 4)) && !isKingChecked(board, 5) && !isKingChecked(board, 6)) {
            return 2;
        }else if(Game.blackKingMoved == 0 && Game.blackRookMoved[0] == 0 && pieceIndex == 4 && moveIndex == 2 && board[3] == ' ' && board[2] == ' ' && board[1] == ' ' && board[0] == 'r' && (!isKingChecked(board, 4)) && !isKingChecked(board, 3) && !isKingChecked(board, 2)) {
            return 3;
        }
        return 0;
    }


    public static int pawnMove(char[] board, int pieceIndex, int moveIndex){
        if(board[pieceIndex] == 'P'){
            if(moveIndex / 8 == pieceIndex / 8 - 1 && moveIndex % 8 == pieceIndex % 8 && board[moveIndex] == ' '){
                return 1;
            }else if((pieceIndex <=55 && pieceIndex >=48) && pieceIndex-16 == moveIndex && board[moveIndex] == ' '){
                return 1;
            }else if((pieceIndex-9 == moveIndex || pieceIndex-7 == moveIndex) && Character.isLowerCase(board[moveIndex]) && moveIndex / 8 == pieceIndex / 8 - 1) {
                return 1;
            }else if(pieceIndex >=24 && pieceIndex <= 31 && (Game.whiteEnpassatnt[0] == 1 && Game.whiteEnpassatnt[1] == moveIndex+8)){
                whiteEnpassantMade = Game.whiteEnpassantMade;
                return 2;
            }
        }else if(board[pieceIndex] == 'p'){
            if(moveIndex / 8 == pieceIndex / 8 + 1 && moveIndex % 8 == pieceIndex % 8 && board[moveIndex] == ' '){
                return 1;
            }else if((pieceIndex <=15 && pieceIndex >=8) && pieceIndex+16 == moveIndex && board[moveIndex] == ' '){
                return 1;
            }else if((pieceIndex+9 == moveIndex || pieceIndex+7 == moveIndex) && Character.isUpperCase(board[moveIndex]) && moveIndex / 8 == pieceIndex / 8 + 1) {
                return 1;
            }else if(pieceIndex >= 32 && pieceIndex <= 39 && (Game.blackEnpassant[0] == 1 && Game.blackEnpassant[1] == moveIndex-8)){
                blackEnpassantMade = Game.blackEnpassantMade;
                return 2;
            }
        }
        return 0;
    }
}
