import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DinosaurGame extends JPanel implements ActionListener, KeyListener{

    int boardWidth = 750;
    int boardHeight = 250;

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
    Timer gameLoop;

    int velocityX =0;
    int velocityY =0;





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
        if(e.getKeyCode()==KeyEvent.VK_UP){
            velocityY=-17;
            System.out.println("Jump");
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
    }
    public void move(){
        dinosaur.y += velocityY;
    }
}
