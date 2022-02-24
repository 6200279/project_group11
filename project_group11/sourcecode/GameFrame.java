package sourcecode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {

    private Scenario scenario;
    private int scale = 10;
    private boolean isVisible;
    private ArrayList<Player> players;
    private JPanel panelWest;
    private JButton buttonPlay;
    private JButton buttonPause ;
   private JButton buttonExit;
   private  MenuFrame menuFrame ;
    private terraingenerator.Map terrain;


    public GameFrame(Scenario scenario, terraingenerator.Map terrain,MenuFrame menuFrame) {

        this.scenario = scenario;
        players = new ArrayList<Player>();
        this.terrain = terrain;
        this.menuFrame=menuFrame ;

        setSize(120 * scale, 80 * scale);
        setLayout(new BorderLayout());

        createPanelWest();

        getContentPane().add(BorderLayout.CENTER, terrain);
        setTitle("Testing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(false);

    }

    private void createPanelWest() {
        panelWest = new JPanel();
        panelWest.setBackground(new Color(36, 95, 131));
        panelWest.setLayout(new BoxLayout(panelWest,BoxLayout.PAGE_AXIS));
        buttonPlay = createButtonPlay();
        buttonPause = createButtonBreak() ;
        buttonExit=createButtonExit();
        panelWest.add(buttonPlay);
        panelWest.add(buttonPause);
        panelWest.add(buttonExit);
        add(BorderLayout.WEST, panelWest);
    }

    private JButton createButtonPlay() {

        buttonPlay = new JButton("Play");
        buttonPlay.addActionListener(e -> {terrain.changePlayToTrue();});
        return buttonPlay;

    }

    private JButton createButtonBreak(){
        buttonPause = new JButton("Pause") ;
        buttonPause.setSize(150, 18);
        buttonPause.addActionListener(e ->terrain.changePlayToFalse() );
        return  buttonPause ;
    }
    private JButton createButtonExit(){
        buttonExit = new JButton("Exit") ;

        buttonExit.setSize(150, 18);
        buttonExit.setForeground(Color.BLACK);
        buttonExit.setFont(new Font("Dialog", Font.PLAIN,15));
        buttonExit.setLocation(140,150);
        buttonExit.addActionListener(e -> {setVisible(false);
        menuFrame.setVisible(true);}) ;

        return buttonExit;
}
}




