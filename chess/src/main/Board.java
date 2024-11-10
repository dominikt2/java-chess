package main;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Board extends JPanel{

    Game game;
    public int tileSize = 100;
    int cols = 8;
    int rows = 8;

    char[] board = {'r','n',' ',' ','k','b','n','r',
                    'p','p','P','p','p','P','P','p',
                    ' ',' ',' ',' ',' ',' ',' ',' ',
                    ' ',' ',' ',' ',' ',' ',' ',' ',
                    ' ',' ',' ',' ',' ',' ',' ',' ',
                    ' ',' ',' ',' ',' ',' ',' ',' ',
                    'P','P','p','P','P','P','P','P',
                    'R','N',' ','Q','K','B','N','R'};
    public Board(){
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseListener(game);
        this.addMouseMotionListener(game);
    }
    BufferedImage sheet;{
        try{
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("res/pieces.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    protected int sheetScale = sheet.getWidth()/6;

    Image sprite;


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Serif", Font.BOLD, tileSize / 2));

        // Draw tiles
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                g2d.setColor((r + c) % 2 == 0 ? new Color(211, 183, 151) : new Color(112, 79, 65));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);

                char piece = board[r * cols + c];
                if (piece != ' ') {
                    if(piece == 'P'){
                        sprite = sheet.getSubimage(5*sheetScale, 0, sheetScale, sheetScale);
                        g2d.drawImage(sprite, c * tileSize, r * tileSize, tileSize, tileSize, null);
                    }else if(piece == 'p'){
                        sprite = sheet.getSubimage(5*sheetScale, sheetScale, sheetScale, sheetScale);
                        g2d.drawImage(sprite, c * tileSize, r * tileSize, tileSize, tileSize, null);
                    }else if(piece == 'R'){
                        sprite = sheet.getSubimage(4*sheetScale, 0, sheetScale, sheetScale);
                        g2d.drawImage(sprite, c * tileSize, r * tileSize, tileSize, tileSize, null);
                    }else if(piece == 'r'){
                        sprite = sheet.getSubimage(4*sheetScale, sheetScale, sheetScale, sheetScale);
                        g2d.drawImage(sprite, c * tileSize, r * tileSize, tileSize, tileSize, null);
                    }else if(piece == 'N'){
                        sprite = sheet.getSubimage(3*sheetScale, 0, sheetScale, sheetScale);
                        g2d.drawImage(sprite, c * tileSize, r * tileSize, tileSize, tileSize, null);
                    }else if(piece == 'n'){
                        sprite = sheet.getSubimage(3*sheetScale, sheetScale, sheetScale, sheetScale);
                        g2d.drawImage(sprite, c * tileSize, r * tileSize, tileSize, tileSize, null);
                    }else if(piece == 'B'){
                        sprite = sheet.getSubimage(2*sheetScale, 0, sheetScale, sheetScale);
                        g2d.drawImage(sprite, c * tileSize, r * tileSize, tileSize, tileSize, null);
                    }else if(piece == 'b'){
                        sprite = sheet.getSubimage(2*sheetScale, sheetScale, sheetScale, sheetScale);
                        g2d.drawImage(sprite, c * tileSize, r * tileSize, tileSize, tileSize, null);
                    }else if(piece == 'Q'){
                        sprite = sheet.getSubimage(1*sheetScale, 0, sheetScale, sheetScale);
                        g2d.drawImage(sprite, c * tileSize, r * tileSize, tileSize, tileSize, null);
                    }else if(piece == 'q'){
                        sprite = sheet.getSubimage(1*sheetScale, sheetScale, sheetScale, sheetScale);
                        g2d.drawImage(sprite, c * tileSize, r * tileSize, tileSize, tileSize, null);
                    }else if(piece == 'K'){
                        sprite = sheet.getSubimage(0*sheetScale, 0, sheetScale, sheetScale);
                        g2d.drawImage(sprite, c * tileSize, r * tileSize, tileSize, tileSize, null);
                    }else if(piece == 'k'){
                        sprite = sheet.getSubimage(0*sheetScale, sheetScale, sheetScale, sheetScale);
                        g2d.drawImage(sprite, c * tileSize, r * tileSize, tileSize, tileSize, null);
                    }
                }

            }
        }
    }
}
