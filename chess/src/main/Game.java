package main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Game extends MouseAdapter {

    public static int tileSize = 100;
    static Board board;
    private static boolean aiMakingMove = false;

    public static boolean isWhiteTurn = true;
    public int pieceCol, pieceRow;
    public int moveCol, moveRow;

    public static int[] whiteEnpassatnt = new int[2];
    public static int[] blackEnpassant = new int[2];

    public static boolean whiteEnpassantMade = false;
    public static boolean blackEnpassantMade = false;

    public static int whiteKingMoved = 0;
    public static int blackKingMoved = 0;

    public static int[] whiteRookMoved = {0, 0};
    public static int[] blackRookMoved = {0, 0};

    public static ArrayList<Integer> validMoves = new ArrayList<>();

    public Game(Board board) {
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pieceCol = e.getX() / board.tileSize;
        pieceRow = e.getY() / board.tileSize;
        int pieceIndex = pieceRow * 8 + pieceCol;
        validMoves.clear();

        char piece = board.board[pieceIndex];
        if (piece != ' ') {
            for (int i = 0; i < 64; i++) {
                if (validateMove(pieceIndex, i, board.board) != 0) {
                    validMoves.add(i);
                }
            }
            paintValidMoves(board.getGraphics());
        }
    }

    public static void handleMove(int pieceIndex, int moveIndex) {
        makeMove(pieceIndex, moveIndex, board.board);
        if (!aiMakingMove && !isWhiteTurn) {
            aiMakingMove = true;
            char[] newBoard = trebfish.makeAiMove(board.board);
            board.board = newBoard;
            board.repaint();
            isWhiteTurn = !isWhiteTurn;
            aiMakingMove = false;
        }
    }

    public static void paintPromotionMenuWhite(Graphics g){
        // MOZE KIEDYS XD
    }

    public static void paintValidMoves(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(56, 203, 56, 100));
        for (int index : validMoves) {
            g2d.fillRect((index % 8) * tileSize, (index / 8) * tileSize, tileSize, tileSize);
        }

        int kingPosition = isWhiteTurn ? findKingPosition('K') : findKingPosition('k');
        if (isKingChecked(board.board, kingPosition)) {
            g2d.setColor(new Color(255, 0, 0, 100));
            g2d.fillRect((kingPosition % 8) * tileSize, (kingPosition / 8) * tileSize, tileSize, tileSize);
        }
    }

    public static int findKingPosition(char king) {
        for (int i = 0; i < 64; i++) {
            if (board.board[i] == king) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        moveCol = e.getX() / board.tileSize;
        moveRow = e.getY() / board.tileSize;

        int pieceIndex = pieceRow * 8 + pieceCol;
        int moveIndex = moveRow * 8 + moveCol;
        handleMove(pieceIndex, moveIndex);
    }

    public static void makeMove(int pieceIndex, int moveIndex, char[] chessBoard) {
        int tempPieceIndex = pieceIndex;
        char piece = chessBoard[pieceIndex];
        if (piece == ' ') {
            return;
        }

        if (!isKingMated(chessBoard)) {
                if (validateMove(pieceIndex, moveIndex, chessBoard) == 1) {
                    if (isWhiteTurn && moveIndex / 8 == 4 && chessBoard[pieceIndex] == 'P' && pieceIndex / 8 == 6) {
                        blackEnpassant[0] = 1;
                        blackEnpassant[1] = moveIndex;
                    } else if (!isWhiteTurn && moveIndex / 8 == 3 && chessBoard[pieceIndex] == 'p' && pieceIndex / 8 == 1) {
                        whiteEnpassatnt[0] = 1;
                        whiteEnpassatnt[1] = moveIndex;
                    }
                    if (pawnMove(chessBoard, pieceIndex, moveIndex) == 2 && whiteEnpassantMade) {
                        clearEnpassant();
                        chessBoard[moveIndex + 8] = ' ';
                        whiteEnpassantMade = false;
                    } else if (blackEnpassantMade && pawnMove(chessBoard, pieceIndex, moveIndex) == 2) {
                        clearEnpassant();
                        chessBoard[moveIndex - 8] = ' ';
                        blackEnpassantMade = false;
                    }
                    clearEnpassant();
                    chessBoard[moveIndex] = chessBoard[pieceIndex];
                    chessBoard[pieceIndex] = ' ';
                    board.repaint();
                    if (chessBoard[moveIndex] == 'K') {
                        whiteKingMoved++;
                    }else if (chessBoard[moveIndex] == 'k') {
                        blackKingMoved++;
                    }else if (piece == 'R' && tempPieceIndex == 56) {
                        whiteRookMoved[0]++;
                    }else if (piece == 'R' && tempPieceIndex == 63) {
                        whiteRookMoved[1]++;
                    }else if (piece == 'r' && tempPieceIndex == 0) {
                        blackRookMoved[0]++;
                    }else if (piece == 'r' && tempPieceIndex == 7) {
                        blackRookMoved[1]++;
                    }else if(moveIndex <= 7 && chessBoard[moveIndex] == 'P') {
                        chessBoard[moveIndex] = 'Q';
                        board.repaint();
                    }else if(moveIndex >= 56 && chessBoard[moveIndex] == 'p') {
                        chessBoard[moveIndex] = 'q';
                        board.repaint();
                    }
                } else if (validateMove(pieceIndex, moveIndex, chessBoard) == 2) {
                    clearEnpassant();
                    chessBoard[moveIndex] = chessBoard[pieceIndex];
                    chessBoard[pieceIndex + 1] = chessBoard[pieceIndex + 3];
                    chessBoard[pieceIndex + 3] = ' ';
                    chessBoard[pieceIndex] = ' ';
                    board.repaint();
                } else if (validateMove(pieceIndex, moveIndex, chessBoard) == 3) {
                    clearEnpassant();
                    chessBoard[moveIndex] = chessBoard[pieceIndex];
                    chessBoard[pieceIndex - 1] = chessBoard[pieceIndex - 4];
                    chessBoard[pieceIndex - 4] = ' ';
                    chessBoard[pieceIndex] = ' ';
                    board.repaint();
                }else {
                    return;
                }
            } else {
                if (isKingChecked(chessBoard, findKingPosition('k'))) {
                    System.out.println("White wins");
                } else if (isKingChecked(chessBoard, findKingPosition('K'))) {
                    System.out.println("Black wins");
                } else {
                    System.out.println("Stalemate");
                }
            }
            isWhiteTurn = !isWhiteTurn;
        }


    public static char[] makeTemporarMove(char[] board, int pieceIndex, int moveIndex){
        board[moveIndex] = board[pieceIndex];
        board[pieceIndex] = ' ';
        return board;
    }

    public static boolean isKingMated(char[] board){
        for(int i=0; i<64; i++){
            for(int j=0; j<64; j++){
                if(validateMove(i, j, board) !=0){
                    return false;
                }
            }
        }
        return true;
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

    public static boolean canPieceMove(int pieceIndex, int moveIndex) {
        char[] tempBoard = board.board.clone();
        tempBoard = makeTemporarMove(tempBoard, pieceIndex, moveIndex);
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

    public static boolean canPawnEnpassant(int pieceIndex, int moveIndex){
        char[] tempBoard = board.board.clone();
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
        if (isWhiteTurn) {
            if(piece == 'P' && pawnMove(board, pieceIndex, moveIndex) != 0) {
                if(pawnMove(board, pieceIndex, moveIndex) == 1 && canPieceMove(pieceIndex, moveIndex)){
                    return 1;
                }else if(pawnMove(board, pieceIndex, moveIndex) == 2 && canPawnEnpassant(pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'R' && (horizontalMove(board,pieceIndex, moveIndex) || verticalMove(board,pieceIndex, moveIndex))) {
                if(canPieceMove(pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'Q' && (horizontalMove(board,pieceIndex, moveIndex) || verticalMove(board,pieceIndex, moveIndex) || diagonalMove(board,pieceIndex, moveIndex))) {
                if(canPieceMove(pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'N' && knightMove(board,pieceIndex, moveIndex)) {
                if(canPieceMove(pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'B' && diagonalMove(board,pieceIndex, moveIndex)) {
                if(canPieceMove(pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'K' && kingMove(board,pieceIndex, moveIndex) != 0) {
                if(canPieceMove(pieceIndex, moveIndex)){
                    return kingMove(board,pieceIndex, moveIndex);
                }
            }
        }else{
            if (piece == 'p' && pawnMove(board,pieceIndex, moveIndex) != 0) {
                if(pawnMove(board, pieceIndex, moveIndex) == 1 && canPieceMove(pieceIndex, moveIndex)){
                    return 1;
                }else if(pawnMove(board, pieceIndex, moveIndex) == 2 && canPawnEnpassant(pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'r' && (horizontalMove(board,pieceIndex, moveIndex) || verticalMove(board,pieceIndex, moveIndex))) {
                if(canPieceMove(pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'q' && (horizontalMove(board,pieceIndex, moveIndex) || verticalMove(board,pieceIndex, moveIndex) || diagonalMove(board,pieceIndex, moveIndex))) {
                if(canPieceMove(pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'n' && knightMove(board,pieceIndex, moveIndex)) {
                if(canPieceMove(pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'b' && diagonalMove(board,pieceIndex, moveIndex)) {
                if(canPieceMove(pieceIndex, moveIndex)){
                    return 1;
                }
            } else if (piece == 'k' && kingMove(board,pieceIndex, moveIndex) != 0) {
                if(canPieceMove(pieceIndex, moveIndex)) {
                    return kingMove(board, pieceIndex, moveIndex);
                }
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
        }else if((whiteKingMoved == 0 && whiteRookMoved[1] == 0) && pieceIndex == 60 && moveIndex == 62 && board[61] == ' ' && board[62] == ' ' && board[63] == 'R' && (!isKingChecked(board, 60)) && !isKingChecked(board, 61) && !isKingChecked(board, 62)){
            return 2;
        }else if(whiteKingMoved == 0 && whiteRookMoved[0] == 0 && pieceIndex == 60 && moveIndex == 58 && board[59] == ' ' && board[58] == ' ' && board[57] == ' ' && board[56] == 'R' && (!isKingChecked(board, 60)) && !isKingChecked(board, 59) && !isKingChecked(board, 58)) {
            return 3;
        }else if((blackKingMoved == 0 && blackRookMoved[1] == 0) && pieceIndex == 4 && moveIndex == 6 && board[5] == ' ' && board[6] == ' ' && board[7] == 'r' && (!isKingChecked(board, 4)) && !isKingChecked(board, 5) && !isKingChecked(board, 6)) {
            return 2;
        }else if(blackKingMoved == 0 && blackRookMoved[0] == 0 && pieceIndex == 4 && moveIndex == 2 && board[3] == ' ' && board[2] == ' ' && board[1] == ' ' && board[0] == 'r' && (!isKingChecked(board, 4)) && !isKingChecked(board, 3) && !isKingChecked(board, 2)) {
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
            }else if(pieceIndex >=24 && pieceIndex <= 31 && (whiteEnpassatnt[0] == 1 && whiteEnpassatnt[1] == moveIndex+8)){
                whiteEnpassantMade = true;
                return 2;
            }
        }else if(board[pieceIndex] == 'p'){
            if(moveIndex / 8 == pieceIndex / 8 + 1 && moveIndex % 8 == pieceIndex % 8 && board[moveIndex] == ' '){
                return 1;
            }else if((pieceIndex <=15 && pieceIndex >=8) && pieceIndex+16 == moveIndex && board[moveIndex] == ' '){
                return 1;
            }else if((pieceIndex+9 == moveIndex || pieceIndex+7 == moveIndex) && Character.isUpperCase(board[moveIndex]) && moveIndex / 8 == pieceIndex / 8 + 1) {
                return 1;
            }else if(pieceIndex >= 32 && pieceIndex <= 39 && (blackEnpassant[0] == 1 && blackEnpassant[1] == moveIndex-8)){
                blackEnpassantMade = true;
                return 2;
            }
        }
        return 0;
    }

    public static void clearEnpassant(){
        if(!isWhiteTurn){
            blackEnpassant[0] = 0;
            blackEnpassant[1] = 0;
        }else{
            whiteEnpassatnt[0] = 0;
            whiteEnpassatnt[1] = 0;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
