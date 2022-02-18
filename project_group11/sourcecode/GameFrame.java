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
        buttonPlay = createButtonPlay();
        panelWest.add(buttonPlay);
        add(BorderLayout.WEST, panelWest);
    }

    private JButton createButtonPlay() {

        buttonPlay = new JButton("Play");
        buttonPlay.addActionListener(e -> {terrain.changePlay();
        terrain.actionPerformed(e);});
        return buttonPlay;

    }
}

