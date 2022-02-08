package project_group11;
import javax.swing.*;
import java.awt.*;

public class MainFrameTest {

    private Scenario scenario = new Scenario("testmap.txt") ;

    public static void main(String[] args) {
        buildframe();


    }

    public static void buildframe(){
        JFrame frame = new JFrame();
        frame.setSize(120*7, 80*7);
        frame.setTitle("Test");
        AreaComponent ac = new AreaComponent() ;
        frame.add(ac) ;



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

}
