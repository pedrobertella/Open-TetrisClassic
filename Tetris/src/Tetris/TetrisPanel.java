package Tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kuusisto.tinysound.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Tetris.Shape.Tetrominoes;

public class TetrisPanel extends JPanel implements Runnable {

    public Thread thread = new Thread(this);
    public static Image bg;
    public static Image go;
    public static Image ps;
    public static Image we;
    public static Image[] box;
    public static Music aTheme;
    public static Music bTheme;
    public static Music cTheme;
    public static Sound turn, move, drop, line, go1, go2, linefour, newlevel;
    public static int delay;
    public static boolean losing = false;
    public BufferedImage font;
    public Shape nextPiece;

    public boolean lose = false;
    public static boolean pause = false;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TetrisPanel() {
        TinySound.init();
        nextPiece = new Shape();
        delay = 0;
        Boolean usePrefix = true;
        String prefix = "";
        if (usePrefix) {
            String s = System.getProperty("user.dir");
            prefix = s + "/Tetris/";
        }
        bg = new ImageIcon(prefix + "graphics/gamebackgroundgamea.png").getImage();
        go = new ImageIcon(prefix + "graphics/gameover.png").getImage();
        ps = new ImageIcon(prefix + "graphics/pause.png").getImage();
        we = new ImageIcon(prefix + "graphics/welcome.png").getImage();

        box = new Image[8];

        box[1] = new ImageIcon(prefix + "graphics/pieces/1.png").getImage();
        box[2] = new ImageIcon(prefix + "graphics/pieces/2.png").getImage();
        box[3] = new ImageIcon(prefix + "graphics/pieces/3.png").getImage();
        box[4] = new ImageIcon(prefix + "graphics/pieces/4.png").getImage();
        box[5] = new ImageIcon(prefix + "graphics/pieces/5.png").getImage();
        box[6] = new ImageIcon(prefix + "graphics/pieces/6.png").getImage();
        box[7] = new ImageIcon(prefix + "graphics/pieces/7.png").getImage();

        turn = TinySound.loadSound("sounds/turn.wav");
        move = TinySound.loadSound("sounds/move.wav");
        drop = TinySound.loadSound("sounds/blockfall.wav");
        line = TinySound.loadSound("sounds/lineclear.wav");
        go1 = TinySound.loadSound("sounds/gamemover1.wav");
        go2 = TinySound.loadSound("sounds/gamemover2.wav");
        linefour = TinySound.loadSound("sounds/4lineclear.wav");
        newlevel = TinySound.loadSound("sounds/newlevel.wav");
        aTheme = TinySound.loadMusic("sounds/themeA.wav");
        bTheme = TinySound.loadMusic("sounds/themeB.wav");
        cTheme = TinySound.loadMusic("sounds/themeC.wav");
        aTheme.play(true);
        TinySound.setGlobalVolume(0.6);

        try {
            font = ImageIO.read(getClass().getResource("/graphics/font.png"));
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

        this.setBackground(new Color(100, 100, 100));

    }

    public List<BufferedImage> convert(String text) {

        List<BufferedImage> images = new ArrayList<>(25);

        for (char c : text.toCharArray()) {
            c = Character.toUpperCase(c);
            int smudge = 1;
            int offset = -1;
            if (c >= 48 && c <= 57) {
                offset = c - 48;
            } else if (c >= 65 && c <= 90) {
                offset = c - 65 + 10;
            } else if (c == 32) {
                offset = 48;
                smudge = 2;
            }

            if (offset >= 0) {
                BufferedImage sub = font.getSubimage((offset * 8) + smudge, 0, 8 - smudge, 8);
                images.add(sub);
            }
        }

        return images;

    }

    public void lose() {

        losing = true;
        delay = 500;

    }

    public void init(Frame arg0) {
        // Just Start The Thread...
        thread.start();

    }

    public void keyboardEvent(KeyEvent e) {

        String keyChar = Character.toString(e.getKeyChar());

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (!lose) {
                if (pause == false) {
                    Frame.board.timer.stop();
                } else {
                    Frame.board.timer.start();
                }
                pause = !pause;
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, 800, 720, null);
        Frame.board.paint(g);

        Graphics2D g2d = (Graphics2D) g.create();
        List<BufferedImage> text = convert(String.valueOf(Frame.board.score));
        int x = (105 + (55 - (text.size() * 8)) / 2);
        int y = (23);
        g2d.scale(5, 5);
        for (BufferedImage img : text) {

            g2d.drawImage(img, x, y, this);
            x += img.getWidth();
        }

        List<BufferedImage> text2 = convert(String.valueOf(Frame.board.level));
        int x2 = (112 + (40 - (text2.size() * 8)) / 2);
        int y2 = (56);
        for (BufferedImage img2 : text2) {

            g2d.drawImage(img2, x2, y2, this);
            x2 += img2.getWidth();
        }

        List<BufferedImage> text3 = convert(String.valueOf(Frame.board.numLinesRemoved));
        int x3 = (112 + (40 - (text3.size() * 8)) / 2);
        int y3 = (80);
        for (BufferedImage img3 : text3) {

            g2d.drawImage(img3, x3, y3, this);
            x3 += img3.getWidth();
        }

        g2d.dispose();

        if (pause) {
            g.drawImage(ps, 75, 0, 400, 720, null);
        }

        if (lose) {
            g.drawImage(go, 75, 0, 400, 720, null);
        }

        if (!lose && !pause && !Frame.board.isStarted) {
            g.drawImage(we, 75, 0, 400, 720, null);
        }

        if (!lose && nextPiece.getShape() == Tetrominoes.LineShape) {
            g.drawImage(box[1], (595 + ((170 - (box[1].getWidth(null) * 5)) / 2)),
                    (515 + ((170 - (box[1].getHeight(null) * 5)) / 2)), box[1].getWidth(null) * 5,
                    box[1].getHeight(null) * 5, null);
        }
        if (!lose && nextPiece.getShape() == Tetrominoes.MirroredLShape) {
            g.drawImage(box[2], (595 + ((170 - (box[2].getWidth(null) * 5)) / 2)),
                    (515 + ((170 - (box[2].getHeight(null) * 5)) / 2)), box[2].getWidth(null) * 5,
                    box[2].getHeight(null) * 5, null);
        }
        if (!lose && nextPiece.getShape() == Tetrominoes.LShape) {
            g.drawImage(box[3], (595 + ((170 - (box[3].getWidth(null) * 5)) / 2)),
                    (515 + ((170 - (box[3].getHeight(null) * 5)) / 2)), box[3].getWidth(null) * 5,
                    box[3].getHeight(null) * 5, null);
        }
        if (!lose && nextPiece.getShape() == Tetrominoes.SquareShape) {
            g.drawImage(box[4], (595 + ((170 - (box[4].getWidth(null) * 5)) / 2)),
                    (515 + ((170 - (box[4].getHeight(null) * 5)) / 2)), box[4].getWidth(null) * 5,
                    box[4].getHeight(null) * 5, null);
        }
        if (!lose && nextPiece.getShape() == Tetrominoes.SShape) {
            g.drawImage(box[5], (595 + ((170 - (box[5].getWidth(null) * 5)) / 2)),
                    (515 + ((170 - (box[5].getHeight(null) * 5)) / 2)), box[5].getWidth(null) * 5,
                    box[5].getHeight(null) * 5, null);
        }
        if (!lose && nextPiece.getShape() == Tetrominoes.TShape) {
            g.drawImage(box[6], (595 + ((170 - (box[6].getWidth(null) * 5)) / 2)),
                    (515 + ((170 - (box[6].getHeight(null) * 5)) / 2)), box[6].getWidth(null) * 5,
                    box[6].getHeight(null) * 5, null);
        }
        if (!lose && nextPiece.getShape() == Tetrominoes.ZShape) {
            g.drawImage(box[7], (595 + ((170 - (box[7].getWidth(null) * 5)) / 2)),
                    (515 + ((170 - (box[7].getHeight(null) * 5)) / 2)), box[7].getWidth(null) * 5,
                    box[7].getHeight(null) * 5, null);
        }

    }

    @Override
    public void run() {

        while (true) {

            if (losing) {
                if (delay > 0) {
                    delay--;
                } else {

                    go2.play();
                    lose = true;
                    losing = false;
                }
            }

            repaint();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        }

    }

}
