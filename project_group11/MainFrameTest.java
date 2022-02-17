import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;



public class MainFrameTest {



    private static Scenario scenario = new Scenario("testmap.txt") ;
    private Image backG; // background
    public static void main(String[] args) {
        int height = scenario.mapHeight;
        int width = scenario.mapWidth;
        int scale = 10; // scale the map size
        //long seed = System.currentTimeMillis();
        long seed =13515420;


        Random rand = new Random(seed);
        double z = rand.nextDouble();
        // BIOME TYPES include, GREEK, SAHARA
        String biome = "GREEK";
        terraingenerator.Map terrain = new terraingenerator.Map(height, width, z,scale, biome);
        List<Point> snow = terraingenerator.Map.Terrain_mapper("SNOW");
        List<Point> forest = terraingenerator.Map.Terrain_mapper("FOREST");
        List<Point> hills = terraingenerator.Map.Terrain_mapper("HILLS");
        List<Point> Mountains = terraingenerator.Map.Terrain_mapper("MOUNTAINS");
        List<Point> Desert = terraingenerator.Map.Terrain_mapper("DESERT");
        List<Point> Lake = terraingenerator.Map.Terrain_mapper("LAKE");
        List<Point> plains = terraingenerator.Map.Terrain_mapper("PLAINS");

        System.out.println("BIOME TYPE :"+ biome);
        System.out.println("Amount of snowpoints:"+snow.size());
        System.out.println("Amount of forest points:"+forest.size());
        System.out.println("Amount of hills points:"+hills.size());
        System.out.println("Amount of Mountains points:"+Mountains.size());
        System.out.println("Amount of Desert points:"+Desert.size());
        System.out.println("Amount of lake points:"+Lake.size());
        System.out.println("Amount of plains points:"+plains.size());



        JFrame frame = new JFrame();
        JFrame frame2 = new JFrame();
        frame.setSize(120*scale, 80*scale);
        final ImageIcon image = new ImageIcon(new ImageIcon("project_group11/assets/img.jpeg").getImage().getScaledInstance(120*10, 80*10, Image.SCALE_DEFAULT));
        frame.setLayout(new BorderLayout());

        // Start Button
        JLabel bg= new JLabel(image);
        frame.add(bg);
        bg.setLayout(null);
        int fixedButtonWidth = 250;
        int fixedButtonHeight = 20;
        final ImageIcon image2= new ImageIcon(new ImageIcon("/Users/stijnoverwater/Documents/GitHub/project_group11/project_group11/assets/img.jpeg").getImage().getScaledInstance(fixedButtonWidth,fixedButtonHeight,Image.SCALE_DEFAULT));
        JButton b= new JButton("Start the game",image2);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        b.setSize(150, 22);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Dialog", Font.PLAIN,15));
        b.setLocation(frame.getWidth()/2, frame.getHeight()/2+170);

        //TODO BUTTONSS
        JRadioButton birdButton = new JRadioButton("GREEK");
        birdButton.setMnemonic(KeyEvent.VK_B);
        birdButton.setActionCommand("Greek");
        birdButton.setSelected(true);

        JRadioButton catButton = new JRadioButton("SAHARA");
        catButton.setMnemonic(KeyEvent.VK_C);
        catButton.setActionCommand("SAHARA");

        JRadioButton dogButton = new JRadioButton("something else ");
        dogButton.setMnemonic(KeyEvent.VK_D);
        dogButton.setActionCommand("SOMRTHING");

        ButtonGroup group = new ButtonGroup();
        group.add(birdButton);
        group.add(catButton);
        group.add(dogButton);



        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame2.setVisible(true);
            }
        });
        bg.add(b);



        // Music
        JCheckBox bMusic= new JCheckBox("Music");
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
        bg.add(bMusic);

        frame.setTitle("Main Menu");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame2.setSize(120*scale, 80*scale);
        frame2.setTitle("Testing");
        //AreaComponent ac = new AreaComponent() ;
        frame2.getContentPane().add(BorderLayout.CENTER,terrain);

        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

        //AgentViewPoints myAgent = new AgentViewPoints();
        //Point myPoint = new Point(25,25);
        //myAgent.rotate(myPoint, 10);

        //myAgent.farthest();
        //myAgent.viewPoints();



    }

