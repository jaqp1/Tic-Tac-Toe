package edu.lab14;

import javax.swing.*;
import java.awt.*;

public class JGamePanel extends JPanel {

    private final Font font = new Font("Verdana", Font.BOLD, 28);

    public JGamePanel() {
        super(true);
        setFocusable(true);
        requestFocus();
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void drawBoard(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,CConfig.WIDTH,CConfig.HEIGHT);
        Graphics2D g2 = (Graphics2D)g;
        g.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g.drawLine(0, CConfig.HEIGHT/3, CConfig.WIDTH, CConfig.HEIGHT/3);
        g.drawLine(0, 2*CConfig.HEIGHT/3, CConfig.WIDTH, 2*CConfig.HEIGHT/3);
        g.drawLine(CConfig.WIDTH/3, 0, CConfig.WIDTH/3, CConfig.HEIGHT);
        g.drawLine(2*CConfig.WIDTH/3, 0, 2*CConfig.WIDTH/3, CConfig.HEIGHT);
    }

    private void drawSymbol(Graphics g, char chr, int x, int y, Color col){
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(4));
        g2.setColor(col);
        switch(chr){
            case 'O':
                g2.drawOval(x-(CConfig.WIDTH/3-20)/2, y-(CConfig.HEIGHT/3-20)/2,
                        CConfig.WIDTH/3-20, CConfig.HEIGHT/3-20);
                break;
            case 'X':
                g2.drawLine(x-(CConfig.WIDTH/3-20)/2, y-(CConfig.HEIGHT/3-20)/2,
                        x+(CConfig.WIDTH/3-20)/2, y+(CConfig.HEIGHT/3-20)/2);
                g2.drawLine(x+(CConfig.WIDTH/3-20)/2, y-(CConfig.HEIGHT/3-20)/2,
                        x-(CConfig.WIDTH/3-20)/2, y+(CConfig.HEIGHT/3-20)/2);
                break;
        }
    }

    void render(Graphics g) {
        drawBoard(g);
        g.setFont(font);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (CConfig.comError) {
            g.setColor(Color.RED);
            int stringWidth = g2.getFontMetrics().stringWidth(CConfig.comErrorString);
            g.drawString(CConfig.comErrorString, CConfig.WIDTH / 2 - stringWidth / 2, CConfig.HEIGHT / 2);
            return;
        }
        if (CConfig.accepted) {
            for (int i = 0; i < CConfig.board.length; i++) {
                if (CConfig.board[i] != null) {
                    char chr = CConfig.board[i].charAt(0);
                    Color col;
                    if(chr=='O'){
                        if(CConfig.circle) col = Color.BLUE; else col = Color.RED;
                    }else{
                        if(!CConfig.circle) col = Color.BLUE; else col = Color.RED;
                    }
                    drawSymbol(g, chr, (2 * (i % 3) + 1) * CConfig.WIDTH/6,
                            (2 * (i / 3) +1) * CConfig.HEIGHT/6, col);
                }
            }
            if (CConfig.won || CConfig.enemyWon) {
                g2.setStroke(new BasicStroke(10));
                g.setColor(Color.GREEN);
                g.drawLine(
                        (2 * (CConfig.line[0] % 3) + 1) * CConfig.WIDTH/6,
                        (2 * (CConfig.line[0] / 3) +1) * CConfig.HEIGHT/6,
                        (2 * (CConfig.line[1] % 3) + 1) * CConfig.WIDTH/6,
                        (2 * (CConfig.line[1] / 3) +1) * CConfig.HEIGHT/6);
                g.setColor(Color.RED);
                if (CConfig.won) {
                    int stringWidth = g2.getFontMetrics().stringWidth(CConfig.wonString);
                    g.drawString(CConfig.wonString, CConfig.WIDTH / 2 - stringWidth / 2, CConfig.HEIGHT / 2);
                } else if (CConfig.enemyWon) {
                    int stringWidth = g2.getFontMetrics().stringWidth(CConfig.enemyWonString);
                    g.drawString(CConfig.enemyWonString, CConfig.WIDTH / 2 - stringWidth / 2, CConfig.HEIGHT / 2);
                }
            }
            if (CConfig.tie) {
                g.setColor(Color.BLACK);
                int stringWidth = g2.getFontMetrics().stringWidth(CConfig.tieString);
                g.drawString(CConfig.tieString, CConfig.WIDTH / 2 - stringWidth / 2, CConfig.HEIGHT / 2);
            }
            if(!CConfig.yourTurn && !CConfig.won && !CConfig.enemyWon && !CConfig.tie) {
                g.setColor(Color.RED);
                int stringWidth = g2.getFontMetrics().stringWidth(CConfig.waitingOpString);
                g.drawString(CConfig.waitingOpString, CConfig.WIDTH / 2 - stringWidth / 2, CConfig.HEIGHT / 2);

            }
        } else {
            if(CConfig.threadRunning) {
                g.setColor(Color.RED);
                int stringWidth = g2.getFontMetrics().stringWidth(CConfig.waitingString);
                g.drawString(CConfig.waitingString, CConfig.WIDTH / 2 - stringWidth / 2, CConfig.HEIGHT / 2);
            }
        }
    }

}
