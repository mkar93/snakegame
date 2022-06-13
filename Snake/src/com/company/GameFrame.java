package com.company;

import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame {

    GameFrame(int width, int height, int unitSize){

        GameEngine engine = new GameEngine(width,height,unitSize);
        add(engine, BorderLayout.CENTER);
        setResizable(false);            // will work on this
        pack();
        setVisible(true);
        setLocationRelativeTo(null);

    }
}
