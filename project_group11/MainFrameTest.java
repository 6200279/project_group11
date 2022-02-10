package project_group11;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainFrameTest {
    private static final int DEFAULT_HEIGHT = 80;

    private static final int DEFAULT_WIDTH = 120;

    private Scenario scenario = new Scenario("testmap.txt") ;

    public static void main(String[] args) {
        int height = DEFAULT_HEIGHT;
        int width = DEFAULT_WIDTH;
        //long seed = System.currentTimeMillis();
        long seed =13515420;
        if (args.length == 3)
        {
            height = Integer.parseInt(args[0]);
            width = Integer.parseInt(args[1]);
            seed = Long.parseLong(args[2]);
        }

        Random rand = new Random(seed);
        double z = rand.nextDouble();



        JFrame frame = new JFrame();
        frame.setSize(120*7+25, 80*7+25);
        frame.setTitle("Test");
        //AreaComponent ac = new AreaComponent() ;

        frame.getContentPane().add(new terraingenerator.Map(height, width, z));

        //frame.getContentPane().add(ac) ;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

}
