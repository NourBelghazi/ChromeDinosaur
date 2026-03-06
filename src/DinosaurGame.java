import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DinosaurGame extends JPanel implements ActionListener, KeyListener{

    int boardWidth = 750;
    int boardHeight = 250;

    int gravity = 2;

    private Image cactus1;
    private Image cactus2;
    private Image cactus3;

    private Image dinosaurImage;
    private Image dinosaurDeadImage;
    private Image dinosaurJumpImage;

    int dinosaurWidth = 88;
    int dinosaurHeight = 94;
    int dinosaurX = 50;
    int dinosaurY = boardHeight - dinosaurHeight;

    Block dinosaur;

    int cactus1Width = 34;
    int cactus2Width = 69;
    int cactus3Width = 103;

    int cactusHeight = 70;
    int cactusX = 700;
    int cactusY = boardHeight-cactusHeight;

    ArrayList<Block> cactusArray= new ArrayList<>();


    Timer gameLoop;
    Timer placeCactusTimer;

    int velocityY =0;

    int cactusVelocity=20;

    boolean gameOver = false;






    public DinosaurGame(){

        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        dinosaurDeadImage = new ImageIcon(getClass().getResource("./images/dino-dead.png")).getImage();
        dinosaurImage = new ImageIcon(getClass().getResource("./images/dino-run.gif")).getImage();
        dinosaurJumpImage = new ImageIcon(getClass().getResource("./images/dino-jump.png")).getImage();

        cactus1 = new ImageIcon(getClass().getResource("./images/cactus1.png")).getImage();
        cactus2 = new ImageIcon(getClass().getResource("./images/cactus2.png")).getImage();
        cactus3 = new ImageIcon(getClass().getResource("./images/cactus3.png")).getImage();

        dinosaur = new Block(dinosaurImage,dinosaurX,dinosaurY, dinosaurHeight,dinosaurWidth);
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();


        Timer placeCactusTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
                placeCactus();
                repaint();
            }
        });
        placeCactusTimer.start();


    }
    public void placeCactus(){
        if(gameOver){
            return;
        }

        double placeCactusChance = Math.random();

        if(placeCactusChance <0.90){
            Block cactusAffiche = new Block(cactus3,cactusX,cactusY,cactusHeight,cactus3Width);
            cactusArray.add(cactusAffiche);
        }
        if(placeCactusChance >0.60){
            Block cactusAffiche = new Block(cactus2,cactusX,cactusY,cactusHeight,cactus2Width);
            cactusArray.add(cactusAffiche);
        }
        else{
            Block cactusAffiche = new Block(cactus1,cactusX,cactusY,cactusHeight,cactus1Width);
            cactusArray.add(cactusAffiche);
        }

    }


    class Block {

        Image image;
        int x;
        int y;

        int blockWidth;
        int blockHeight;


        public Block(Image image, int x, int y, int blockHeight, int blockWidth){

            this.x=x;
            this.y=y;

            this.blockHeight = blockHeight;
            this.blockWidth = blockWidth;

            this.image = image;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver){
            return;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP){
                velocityY=(dinosaur.y==dinosaurY)?-17:0;
                dinosaur.image = (dinosaur.y<=dinosaurY)?dinosaurJumpImage:dinosaurImage;
            }
        if(e.getKeyCode()==KeyEvent.VK_DOWN){}
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){}
        if(e.getKeyCode()==KeyEvent.VK_LEFT){}
    }

    @Override
    public void keyReleased(KeyEvent e) {}


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        g.drawImage(dinosaur.image,dinosaur.x,dinosaur.y,dinosaur.blockHeight, dinosaur.blockHeight,null);

        for(Block cactus:cactusArray) {
            g.drawImage(cactus.image,cactus.x,cactus.y,cactus.blockHeight, cactus.blockHeight,null);

        }


    }
    public boolean collision(Block block1, Block block2 ){
        return block1.x<block2.x+ block2.blockWidth &&
                block2.x<block1.x+ block1.blockWidth &&
                block1.y<block2.y+ block2.blockHeight -20 &&
                block2.x<block1.x+ block1.blockHeight -20;
    }


    public void move() {
        if(gameOver){
            return;
        }
        dinosaur.y += velocityY;
            if (dinosaur.y < dinosaurY) {
                velocityY += gravity;
            } else {
                dinosaur.y = dinosaurY;
                dinosaur.image = dinosaurImage;
            }
            for (Block cactus : cactusArray) {
                if(!collision(cactus,dinosaur)) {
                    cactus.x -= cactusVelocity;

                }
                else{
                    dinosaur.image=dinosaurDeadImage;
                    gameOver = true;
                }

            }

        }
    }
