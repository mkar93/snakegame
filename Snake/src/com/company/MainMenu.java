package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenu{

    public static void MenuButtons(){

        final int buttonSizeW = 150;
        final int buttonSizeH = 50;

        final int frameWidth = 400;
        final int frameHeight = 300;
        final int xBound = 120;

        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(25,25,25));


        JLabel gameNameLabel = new JLabel("S N A K E");
        gameNameLabel.setFont(new Font("Arial black", Font.BOLD, 20));
        //gameNameLabel.setBounds(143,-170,frameWidth,frameHeight);     //  original
        gameNameLabel.setBounds(143,-120,frameWidth,frameHeight);
        gameNameLabel.setForeground(new Color(180,180,180));

        JButton newGameButton = new JButton("New game");
        newGameButton.setBounds(xBound,60,buttonSizeW,buttonSizeH);
        newGameButton.setBackground(new Color(75,75,75));
        newGameButton.setForeground(new Color(180,180,180));
        newGameButton.addActionListener(e -> new GameFrame(GameEngine.SCREEN_WIDTH=600,GameEngine.SCREEN_HEIGHT=600, GameEngine.UNIT_SIZE=40));

        JButton optionsButton = new JButton("Options");
        optionsButton.setBounds(xBound,160,buttonSizeW,buttonSizeH);
        optionsButton.setBackground(new Color(75,75,75));
        optionsButton.setForeground(new Color(180,180,180));
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameEngine.diffLevel = 2;
            }
        });
        //does nothing atm

        JButton exitButton = new JButton("Exit game");
        //exitButton.setBounds(xBound,260,buttonSizeW,buttonSizeH);     //  original
        exitButton.setBounds(xBound,160,buttonSizeW,buttonSizeH);    //  temp
        exitButton.setBackground(new Color(75,75,75));
        exitButton.setForeground(new Color(180,180,180));
        exitButton.addActionListener(e -> System.exit(0));

        frame.add(gameNameLabel);
        frame.add(newGameButton, BorderLayout.NORTH);
        //frame.add(optionsButton, BorderLayout.CENTER);
        frame.add(exitButton, BorderLayout.CENTER);
        frame.setSize(new Dimension(frameWidth, frameHeight));
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
