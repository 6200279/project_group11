package sourcecode;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class GameFrame extends JFrame {





    private Scenario scenario;
    private int scale = 7;
    private boolean isVisible;
    private ArrayList<Player> players;
    private JPanel panelWest;
    private JPanel panelEast;
    private JButton buttonPlay;
    private JButton buttonPause;
    private JButton buttonExit;
    private MenuFrame menuFrame;
    public terraingenerator.Map terrain;



    public GameFrame(Scenario scenario, terraingenerator.Map terrain, MenuFrame menuFrame) {

        this.scenario = scenario;
        players = new ArrayList<Player>();
        this.terrain = terrain;
        this.menuFrame = menuFrame;
        setSize(120 * scale, 80 * scale);
        setLayout(new BorderLayout());

        createPanelWest();
        //createPanelEAST();

        getContentPane().add(BorderLayout.CENTER, this.terrain);
        setTitle("Testing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(false);



    }

   /* public GameFrame(Scenario scenario , MenuFrame menuFrame, int height, int width, double z, int scale) {
        terrain = new terraingenerator.Map(height, width, z,scale, "GREEK",scenario);

        this.scenario = scenario;
        players = new ArrayList<Player>();
        this.terrain = terrain;
        this.menuFrame = menuFrame;
        setSize(120 * scale, 80 * scale);
        setLayout(new BorderLayout());

        createPanelWest();
        // createPanelEAST();

        getContentPane().add(BorderLayout.CENTER, terrain);
        setTitle("Testing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(false);



    }*/
private void createPanelEAST(){
        panelEast = new JPanel();
        panelEast.setBackground(new Color(36, 95, 131));
        panelEast.setLayout(new BoxLayout(panelEast, BoxLayout.PAGE_AXIS));
        JTextField timer = new JTextField(10);
       /* JLabel label2 = new JLabel();
        label2.setText("starting time : " +terraingenerator.starttimer);
        JLabel label3 = new JLabel();
        label3.setText("ending time : "+ terraingenerator.endtimer);*/



        add(BorderLayout.EAST, panelEast);

    }


    private void createPanelWest() {
        panelWest = new JPanel();
        panelWest.setBackground(new Color(36, 95, 131));
        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.PAGE_AXIS));
        buttonPlay = createButtonPlay();
        buttonPause = createButtonBreak();
        buttonExit = createButtonExit();
        JLabel label1 = new JLabel();
        label1.setText("speed :" + 1000 / terraingenerator.TIME_PER_MOVE + " moves per second");
        panelWest.add(buttonPlay);
        panelWest.add(buttonPause);
        panelWest.add(buttonExit);
        panelWest.add(label1);
        add(BorderLayout.WEST, panelWest);
    }

    private JButton createButtonPlay() {

        buttonPlay = new JButton("Play");
       // long starttime = System.currentTimeMillis();
        buttonPlay.addActionListener(e -> {
            //t.setText(String.valueOf(starttime));
            this.terrain.changePlayToTrue();
        });


        return buttonPlay;

    }

    private JButton createButtonBreak() {
        buttonPause = new JButton("Pause");
        buttonPause.setSize(150, 18);
        buttonPause.addActionListener(e -> this.terrain.changePlayToFalse());
        return buttonPause;
    }

    private JButton createButtonExit() {
        buttonExit = new JButton("Exit");

        buttonExit.setSize(150, 18);
        buttonExit.setForeground(Color.BLACK);
        buttonExit.setFont(new Font("Dialog", Font.PLAIN, 15));
        buttonExit.setLocation(140, 150);
        buttonExit.addActionListener(e -> {
            setVisible(false);
            menuFrame.setVisible(true);
        });

        return buttonExit;
    }


}






