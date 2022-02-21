package sourcecode;

import javax.swing.*;
import java.awt.*;
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
    private terraingenerator.Map terrain;


    public GameFrame(Scenario scenario, terraingenerator.Map terrain) {

        this.scenario = scenario;
        players = new ArrayList<Player>();
        this.terrain = terrain;

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
        panelWest.add(buttonPlay);
        panelWest.add(buttonPause);
        add(BorderLayout.WEST, panelWest);
    }

    private JButton createButtonPlay() {

        buttonPlay = new JButton("Play");
        buttonPlay.addActionListener(e -> {terrain.changePlayToTrue();});
        return buttonPlay;

    }

    private JButton createButtonBreak(){
        buttonPause = new JButton("Pause") ;
        buttonPause.addActionListener(e ->terrain.changePlayToFalse() );
        return  buttonPause ;
    }
}

