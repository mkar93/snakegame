package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GameEngine extends JPanel implements ActionListener {



    private final String SCORE_STATUS = "Score:  ";
    private final String GAME_OVER_MESSAGE = "GAME OVER";

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int UNIT_SIZE;

    public static int delay = 110;
    public static int difficulty = 2;
    public static int diffLevel = 1;

    //int UNIT_SIZE_MOD;
    int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;

    boolean running = false;
    int appleTestX;
    int appleTestY;
    int appleX;
    int appleY;
    int bodySize;
    int modApple = 10;
    int modSnake = 2;
    int score;
    int[] x = new int[GAME_UNITS];
    int[] y = new int[GAME_UNITS];
    static char direction = 'R';

    Timer timer;
    Random random = new Random();

    GameEngine(int width, int height, int unitSize){

        setPreferredSize(new Dimension(SCREEN_WIDTH +UNIT_SIZE, SCREEN_HEIGHT+UNIT_SIZE*2));
        setBackground(new Color(25,25,25));
        addKeyListener(new MyKeyAdapter());
        setLayout(null);
        setFocusable(true);

        startGame();
    }

    public void startGame(){
        running = true;
        bodySize = 6;
        x[0] = 0;
        y[0] = 0;
        delay = 110;
        direction = 'R';
        timer = new Timer(delay,this);
        timer.start();
        newApple();
    }

    /*public void startRestartGame(){
        running = true;
        bodySize = 6;
        direction = 'R';
        timer.start();
        newApple();
    }*/

    public void newApple(){
        appleTestX = random.nextInt(SCREEN_WIDTH /UNIT_SIZE)*UNIT_SIZE;
        appleTestY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;

        for (int i = 0; i < bodySize; i++) {
            //checks if newApple collides with snake body
            if (appleTestX == x[i] && appleTestY == y[i]){
                newApple();
                break;
            }
            if (i == bodySize - 1) {
                appleX = appleTestX;
                appleY = appleTestY;
            }
        }
    }

    public void draw(Graphics g){

        if(running){
//            for(int i=0;i<=(SCREEN_HEIGHT/UNIT_SIZE)+1;i++){
//                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT+UNIT_SIZE);
//                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH +UNIT_SIZE, i*UNIT_SIZE);
//            }
            g.drawLine(0,SCREEN_HEIGHT+UNIT_SIZE,SCREEN_WIDTH+UNIT_SIZE,SCREEN_HEIGHT+UNIT_SIZE);

            g.setColor(new Color(200,145,75));
            g.fillOval(appleX+modApple, appleY+modApple, UNIT_SIZE-(modApple*2), UNIT_SIZE-(modApple*2));

            for(int i=0;i<bodySize;i++){
                //head
                if(i == 0) {
                    g.setColor(new Color(35,50,0));
                    g.fillOval(x[i]+modSnake, y[i]+modSnake, UNIT_SIZE-(modSnake*2),UNIT_SIZE-(modSnake*2));
                }
                //neck
                else if(i == 1) {
                    g.setColor(new Color(35,100,25));
                    g.fillRoundRect(x[i]+modSnake, y[i]+modSnake, UNIT_SIZE-(modSnake*2), UNIT_SIZE-(modSnake*2),25,25);
                }
                //tail
                else if(i == bodySize-1) {
                    g.setColor(new Color(50,60,0));
                    g.fillRoundRect(x[i]+modSnake, y[i]+modSnake, UNIT_SIZE-(modSnake*2), UNIT_SIZE-(modSnake*2),25,25);
                }
                //body
                else{
                    g.setColor(new Color(40,120,50));
                    g.fillRoundRect(x[i]+modSnake, y[i]+modSnake, UNIT_SIZE-(modSnake*2), UNIT_SIZE-(modSnake*2),25,25);
                }
            }
            g.setColor(new Color(170,170,10));
            g.setFont(new Font("Segue UI", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            //g.drawString(SCORE_STATUS + score, (SCREEN_WIDTH - metrics.stringWidth(SCORE_STATUS + score)), g.getFont().getSize());
            g.drawString(SCORE_STATUS+score,(SCREEN_WIDTH +UNIT_SIZE-5 - metrics.stringWidth(SCORE_STATUS + score)), SCREEN_HEIGHT+65);
        }
        else gameOver(g);
    }

    public void gameOver(Graphics g){
        g.setColor(new Color(170,0,0));
        g.setFont(new Font("Segue UI", Font.BOLD, 45));
        FontMetrics metrics1 = getFontMetrics(g.getFont());

        g.drawString(GAME_OVER_MESSAGE, (SCREEN_WIDTH +UNIT_SIZE - metrics1.stringWidth(GAME_OVER_MESSAGE))/2, (SCREEN_HEIGHT+UNIT_SIZE*2)/2);

        g.setColor(new Color(170,170,10));
        g.setFont(new Font("Segue UI", Font.BOLD, 20));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString(SCORE_STATUS+score,(SCREEN_WIDTH +UNIT_SIZE-5 - metrics2.stringWidth(SCORE_STATUS + score)), SCREEN_HEIGHT+65);

        timer.stop();

        g.dispose();

        //restartButton();

    }

    /*private void restartButton(){

        if(!running){
            JButton restartGame = new JButton("Play again");
            restartGame.setBounds(((SCREEN_WIDTH+UNIT_SIZE)/4)+9,450,300,100);
            restartGame.setFont(new Font("Segue UI", Font.ITALIC, 20));
            restartGame.setForeground(new Color(180,180,180));
            restartGame.setBackground(new Color(75,75,75));
            restartGame.setVisible(true);
            restartGame.addActionListener(e -> restartGame());

            add(restartGame);

        }
    }*/

    /*private void restartGame(){
        x[0] = 0;
        y[0] = 0;
        score = 0;
        delay = 110;
        startRestartGame();
    }*/

    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodySize++;
            score++;
            delay-=difficulty;
            if(delay >= 40)
                timer.setDelay(delay);
            newApple();
        }
    }

    public void checkCollisionsBodyWall(){
        for (int i=bodySize;i>0;i--){
            if( (x[0] == x[i]) && (y[0]==y[i]) ){
                running = false;
            }
        }
        if(x[0] < 0)
            running = false;
        if(x[0] > SCREEN_WIDTH)
            running = false;
        if(y[0] < 0)
            running = false;
        if(y[0] > SCREEN_HEIGHT)
            running = false;

        if(!running)
            timer.stop();
    }

    public void checkCollisionsBody(){
        for (int i=bodySize;i>0;i--){
            if( (x[0] == x[i]) && (y[0]==y[i]) ){
                running = false;
            }
        }
        if(!running)
            timer.stop();
    }

    public void move(){
        for(int i=bodySize; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                if(y[0] < 0)                //top border
                    y[0] = SCREEN_HEIGHT;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                if(y[0] > SCREEN_HEIGHT)    //bottom border
                    y[0] = 0;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                if(x[0] < 0)                //left border
                    x[0] = SCREEN_WIDTH;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                if(x[0] > SCREEN_WIDTH)     //right border
                    x[0] = 0;
                break;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(running && diffLevel == 1){
            move();
            checkApple();
            checkCollisionsBody();
        }
        else if(running && diffLevel == 2){
            move();
            checkApple();
            checkCollisionsBodyWall();
        }

        repaint();
    }

    public static class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
