package sourcecode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class MenuFrame extends JFrame {

    private Scenario scenario ;
    private int scale = 7;
    private final ImageIcon image ;
    private final ImageIcon image2 ;
    private JLabel bg ;
    private JCheckBox bMusic ;
    private JButton buttonStart ;
    private JRadioButton r1 ;
    private JRadioButton r2 ;
    private ButtonGroup buttonGroup ;
    private GameFrame gameFrame ;
    private double z ;
    private int height ;
    private int width ;
    public terraingenerator.Map terrain1 ;
    public terraingenerator.Map terrain2 ;
    public terraingenerator.Map terrainnull ;
    private JComboBox<String>comboBox ;


    java.util.List<Point> snow ;
    java.util.List<Point> forest ;
    java.util.List<Point> hills ;
    java.util.List<Point> Mountains ;
    java.util.List<Point> Desert  ;
    java.util.List<Point> Lake  ;
    List<Point> plains ;


    public MenuFrame(){

       // long seed = 135152123;
        long seed =100;
       // long seed =1111111;
        Random rand = new Random(seed);



        setTitle("Main Menu");
        setSize(120*scale, 80*scale);
        setLayout(new BorderLayout());


        image = new ImageIcon(new ImageIcon("project_group11/assets/img.jpeg").getImage().getScaledInstance(120*10, 80*10, Image.SCALE_DEFAULT));
        image2= new ImageIcon(new ImageIcon("/Users/stijnoverwater/Documents/GitHub/project_group11/project_group11/assets/img.jpeg").getImage().getScaledInstance(250,20,Image.SCALE_DEFAULT));

        bg= new JLabel(image);
        bg.setLayout(null);

        bMusic = new JCheckBox("Music") ;
        bMusic.setBounds(140,150,80,20);
        bMusic.setFont(new Font("Dialog", Font.PLAIN,17));
        bMusic.setLocation(10, 10);
        bMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bMusic.isSelected())
                {
                    System.out.println("Music is supposed to play");
                }
            }
        });

        MapFiles mapFiles = new MapFiles() ;

        comboBox = new JComboBox<String>(mapFiles.getFiles()) ;
        comboBox.setSize(150,22);
        comboBox.setLocation(getWidth()/2-200,getHeight()/2+170);

        buttonStart = new JButton("Start the game",image2) ;
        buttonStart.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonStart.setSize(150, 22);
        buttonStart.setForeground(Color.BLACK);
        buttonStart.setFont(new Font("Dialog", Font.PLAIN,15));
        buttonStart.setLocation(getWidth()/2,getHeight()/2+170);



       buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(r1.isSelected()) {

                    scenario = new Scenario((String) comboBox.getSelectedItem()) ;
                    height = scenario.mapHeight ;
                    System.out.println(height);
                    width = scenario.mapWidth ;
                    z = rand.nextDouble();


                    terrain1 = new terraingenerator.Map(scenario.mapHeight, scenario.mapWidth, z,7, "GREEK",scenario);


                    gameFrame = new GameFrame(scenario,terrain1,MenuFrame.this) ;
                    gameFrame.getContentPane().add(terrain1) ;
                   //
                    gameFrame.repaint();

                    setVisible(false);
                    gameFrame.setVisible(true);

                }
                if(r2.isSelected()){

                    scenario = new Scenario((String) comboBox.getSelectedItem()) ;
                    height = scenario.mapHeight ;
                    width = scenario.mapWidth ;
                    z = rand.nextDouble();

                    terrain2 = new terraingenerator.Map(scenario.mapHeight,scenario.mapWidth, z,7,"SAHARA",scenario);


                    // System.out.println("BIOME TYPE :" + biome);
                    System.out.println("Amount of snowpoints:" + snow.size());
                    System.out.println("Amount of forest points:" + forest.size());
                    System.out.println("Amount of hills points:" + hills.size());
                    System.out.println("Amount of Mountains points:" + Mountains.size());
                    System.out.println("Amount of Desert points:" + Desert.size());
                    System.out.println("Amount of lake points:" + Lake.size());
                    System.out.println("Amount of plains points:" + plains.size());

                    gameFrame = new GameFrame(scenario,terrain2,MenuFrame.this) ;
                    gameFrame.getContentPane().add(terrain2) ;
                    gameFrame.repaint();
                    setVisible(false);
                    gameFrame.setVisible(true);
                }
            }
        });

        r1=new JRadioButton("GREEK");
        r1.setSelected(true);
        r2=new JRadioButton("SAHARA");
        r1.setBounds(75,50,100,30);
        r1.setLocation(10,45);
        r2.setBounds(75,100,100,30);
        r2.setLocation(10,70);

        buttonGroup=new ButtonGroup();
        buttonGroup.add(r1);
        buttonGroup.add(r2);

        bg.add(r1);
        bg.add(r2);
        bg.add(buttonStart);
        bg.add(comboBox) ;
        bg.add(bMusic);

        add(bg) ;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
