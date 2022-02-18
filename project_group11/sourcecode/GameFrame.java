package sourcecode;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameFrame extends JFrame {

    private Scenario scenario ;
    private int scale = 10;
    private boolean isVisible ;


    public GameFrame(Scenario scenario,terraingenerator.Map terrain){

        this.scenario = scenario ;
        setSize(120*scale, 80*scale);
        setLayout(new BorderLayout());
        getContentPane().add(BorderLayout.CENTER, terrain);
        setTitle("Testing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        isVisible = false ;
        setVisible(isVisible);

    }

}
