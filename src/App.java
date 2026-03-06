import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args){

        int boardWidth = 750;
        int boardHeight = 250;

        JFrame frame = new JFrame("Dinosaur Game.");
        frame.setSize(new Dimension(boardWidth ,boardHeight));

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);
        DinosaurGame dinosaurGame = new DinosaurGame();

        frame.add(dinosaurGame);
        frame.pack();
        dinosaurGame.requestFocus();
        frame.setVisible(true);


    }
}
