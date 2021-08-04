package Tetris;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import kuusisto.tinysound.TinySound;

public class Frame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static TetrisPanel panel = new TetrisPanel();
    public static Image icon;
    public static Board board = new Board();

    public Frame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        icon = new ImageIcon("graphics/pieces/6.png").getImage();
        final ControlsWindow cont = new ControlsWindow();
        JMenuBar menuBar;
        JMenu menu, sounds, music, volume;
        JMenuItem menuItem, pauseItem, exitItem, controls;
        final JRadioButtonMenuItem aTheme;
        final JRadioButtonMenuItem bTheme;
        final JRadioButtonMenuItem cTheme;
        
        
        final JRadioButtonMenuItem volume20;
        final JRadioButtonMenuItem volume40;
        final JRadioButtonMenuItem volume60;
        final JRadioButtonMenuItem volume80;
        final JRadioButtonMenuItem volume100;
        
        JCheckBoxMenuItem snd, msc;

        menuBar = new JMenuBar();
        menu = new JMenu("Game");
        sounds = new JMenu("Options");
        music = new JMenu("Music");
        volume = new JMenu("Volume");
        menuBar.add(menu);
        menuBar.add(sounds);
        sounds.add(music);
        sounds.add(volume);

        aTheme = new JRadioButtonMenuItem("A Theme");
        aTheme.setSelected(true);

        bTheme = new JRadioButtonMenuItem("B Theme");
        cTheme = new JRadioButtonMenuItem("C Theme");
        
        volume20 = new JRadioButtonMenuItem("20%");
        volume40 = new JRadioButtonMenuItem("40%");
        volume60 = new JRadioButtonMenuItem("60%");
        volume60.setSelected(true);
        volume80 = new JRadioButtonMenuItem("80%");
        volume100 = new JRadioButtonMenuItem("100%");

        music.add(aTheme);
        music.add(bTheme);
        music.add(cTheme);
        
        volume.add(volume20);
        volume.add(volume40);
        volume.add(volume60);
        volume.add(volume80);
        volume.add(volume100);

        snd = new JCheckBoxMenuItem("Sounds");
        snd.setSelected(true);
        msc = new JCheckBoxMenuItem("Music");
        msc.setSelected(true);
        snd.setEnabled(false);
        msc.setEnabled(false);

        sounds.add(snd);
        sounds.add(msc);
        menuItem = new JMenuItem("New Game",
                KeyEvent.VK_T);
        pauseItem = new JMenuItem("Pause Game",
                KeyEvent.VK_T);
        exitItem = new JMenuItem("Exit Game",
                KeyEvent.VK_T);
        controls = new JMenuItem("Controls...",
                KeyEvent.VK_T);
        sounds.addSeparator();

        controls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.turn.play();
                cont.setVisible(true);
            }
        });

        aTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bTheme.setSelected(false);
                cTheme.setSelected(false);
                aTheme.setSelected(true);
                panel.bTheme.stop();
                panel.cTheme.stop();
                panel.aTheme.play(true);
                panel.turn.play();
            }
        });

        bTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aTheme.setSelected(false);
                cTheme.setSelected(false);
                bTheme.setSelected(true);
                panel.aTheme.stop();
                panel.cTheme.stop();
                panel.bTheme.play(true);
                panel.turn.play();
            }
        });

        cTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aTheme.setSelected(false);
                bTheme.setSelected(false);
                cTheme.setSelected(true);
                panel.aTheme.stop();
                panel.bTheme.stop();
                panel.cTheme.play(true);
                panel.turn.play();
            }
        });
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.turn.play();
                System.exit(1);
            }
        });
        
        volume20.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volume40.setSelected(false);
                volume60.setSelected(false);
                volume80.setSelected(false);
                volume100.setSelected(false);
                volume20.setSelected(true);
                TinySound.setGlobalVolume(0.2);
            }
        });
        
        volume40.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volume20.setSelected(false);
                volume60.setSelected(false);
                volume80.setSelected(false);
                volume100.setSelected(false);
                volume40.setSelected(true);
                TinySound.setGlobalVolume(0.4);
            }
        });
        
        volume60.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volume40.setSelected(false);
                volume20.setSelected(false);
                volume80.setSelected(false);
                volume100.setSelected(false);
                volume60.setSelected(true);
                TinySound.setGlobalVolume(0.6);
            }
        });
        
        volume80.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volume40.setSelected(false);
                volume60.setSelected(false);
                volume20.setSelected(false);
                volume100.setSelected(false);
                volume80.setSelected(true);
                TinySound.setGlobalVolume(0.8);
            }
        });
        
        volume100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volume40.setSelected(false);
                volume60.setSelected(false);
                volume80.setSelected(false);
                volume20.setSelected(false);
                volume100.setSelected(true);
                TinySound.setGlobalVolume(1.0);
            }
        });        

        pauseItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.turn.play();
                if (!panel.lose) {
                    if (panel.pause == false) {
                        panel.pause = true;
                        board.timer.stop();
                    } else {
                        panel.pause = false;
                        board.timer.start();
                    }
                }
            }
        });

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                panel.turn.play();

                if (aTheme.isSelected() == true) {
                    panel.aTheme.play(true);
                }
                if (bTheme.isSelected() == true) {
                    panel.bTheme.play(true);
                }
                if (cTheme.isSelected() == true) {
                    panel.cTheme.play(true);
                }
                panel.lose = false;
                board.clearBoard();
                board.start();
            }
        });

        sounds.add(controls);
        menu.add(menuItem);
        menu.add(pauseItem);
        menu.addSeparator();
        menu.add(exitItem);

        setJMenuBar(menuBar);
        setLayout(null);
        setIconImage(icon);
        setFocusable(true);
        setSize(new Dimension(800, 770));
        setTitle("Tetris");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.init(this);
        add(panel);
        panel.setBounds(0, 0, 800, 770);
        add(board);
        board.setBounds(0, 50, 400, 720);
        setLocationRelativeTo(null);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

                panel.keyboardEvent(e);
                board.keyPressed(e);

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }

    public static void main(String[] args) {

        Frame frame = new Frame();

        frame.setVisible(true);

    }

}
