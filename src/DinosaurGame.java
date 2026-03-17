import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DinosaurGame extends JPanel implements ActionListener, KeyListener {

    static final int BOARD_WIDTH = 750;
    static final int BOARD_HEIGHT = 250;
    static final int GRAVITY = 2;
    static final int DINO_WIDTH = 88;
    static final int DINO_HEIGHT = 94;
    static final int DINO_X = 50;
    static final int CACTUS1_W = 34, CACTUS2_W = 69, CACTUS3_W = 103;
    static final int CACTUS_H = 70;

    private Image dinosaurRunImage;
    private Image dinosaurDeadImage;
    private Image dinosaurJumpImage;
    private Image cactus1Img;
    private Image cactus2Img;
    private Image cactus3Img;

    int dinosaurGroundY;
    Block dinosaur;

    ArrayList<Block> obstacles = new ArrayList<>();

    Timer gameLoop;
    Timer placeObstacleTimer;

    int velocityY = 0;
    int speed = 8;
    int score = 0;
    int highScore = 0;
    boolean gameOver = false;

    public DinosaurGame() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);

        dinosaurRunImage = new ImageIcon(getClass().getResource("./images/dino-run.gif")).getImage();
        dinosaurDeadImage = new ImageIcon(getClass().getResource("./images/dino-dead.png")).getImage();
        dinosaurJumpImage = new ImageIcon(getClass().getResource("./images/dino-jump.png")).getImage();
        cactus1Img = new ImageIcon(getClass().getResource("./images/cactus1.png")).getImage();
        cactus2Img = new ImageIcon(getClass().getResource("./images/cactus2.png")).getImage();
        cactus3Img = new ImageIcon(getClass().getResource("./images/cactus3.png")).getImage();

        dinosaurGroundY = BOARD_HEIGHT - DINO_HEIGHT;
        dinosaur = new Block(dinosaurRunImage, DINO_X, dinosaurGroundY, DINO_WIDTH, DINO_HEIGHT);

        gameLoop = new Timer(1000 / 60, this);
        placeObstacleTimer = new Timer(1500, e -> placeObstacle());
        gameLoop.start();
        placeObstacleTimer.start();
    }

    void placeObstacle() {
        if (gameOver) return;
        double r = Math.random();
        if (r < 0.33) {
            obstacles.add(new Block(cactus1Img, BOARD_WIDTH, BOARD_HEIGHT - CACTUS_H, CACTUS1_W, CACTUS_H));
        } else if (r < 0.66) {
            obstacles.add(new Block(cactus2Img, BOARD_WIDTH, BOARD_HEIGHT - CACTUS_H, CACTUS2_W, CACTUS_H));
        } else {
            obstacles.add(new Block(cactus3Img, BOARD_WIDTH, BOARD_HEIGHT - CACTUS_H, CACTUS3_W, CACTUS_H));
        }
    }

    static class Block {
        Image image;
        int x, y, width, height;

        Block(Image image, int x, int y, int width, int height) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    boolean collision(Block a, Block b) {
        return a.x < b.x + b.width  - 5 &&
               b.x < a.x + a.width  - 5 &&
               a.y < b.y + b.height - 5 &&
               b.y < a.y + a.height - 5;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
            placeObstacleTimer.stop();
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) return;
        if (e.getKeyCode() == KeyEvent.VK_UP && dinosaur.y == dinosaurGroundY) {
            velocityY = -17;
            dinosaur.image = dinosaurJumpImage;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    void draw(Graphics g) {
        g.drawImage(dinosaur.image, dinosaur.x, dinosaur.y, dinosaur.width, dinosaur.height, null);
        for (Block obs : obstacles) {
            g.drawImage(obs.image, obs.x, obs.y, obs.width, obs.height, null);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + score, 10, 35);
        } else {
            g.drawString(String.valueOf(score), 10, 35);
        }
    }

    void move() {
        if (gameOver) return;

        dinosaur.y += velocityY;
        if (dinosaur.y < dinosaurGroundY) {
            velocityY += GRAVITY;
        } else {
            dinosaur.y = dinosaurGroundY;
            velocityY = 0;
            dinosaur.image = dinosaurRunImage;
        }

        ArrayList<Block> toRemove = new ArrayList<>();
        for (Block obs : obstacles) {
            obs.x -= speed;
            if (collision(obs, dinosaur)) {
                dinosaur.image = dinosaurDeadImage;
                gameOver = true;
                if (score > highScore) highScore = score;
                return;
            }
            if (obs.x + obs.width < 0) toRemove.add(obs);
        }
        obstacles.removeAll(toRemove);

        score++;
    }
}
