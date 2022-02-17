package project_group11;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;


public class GUI_TEST {
    private static final int DEFAULT_HEIGHT = 80;

    private static final int DEFAULT_WIDTH = 120;

    private Scenario scenario = new Scenario("testmap.txt") ;

    private Image backG; // background

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

        terraingenerator.Map terrain = new terraingenerator.Map(height, width, z,10,"GREEK");
        List<Point> snow = terraingenerator.Map.Terrain_mapper("SNOW");
        List<Point> forest = terraingenerator.Map.Terrain_mapper("FOREST");
        List<Point> hills = terraingenerator.Map.Terrain_mapper("HILLS");
        List<Point> Mountains = terraingenerator.Map.Terrain_mapper("MOUNTAINS");
        List<Point> Desert = terraingenerator.Map.Terrain_mapper("DESERT");
        List<Point> Lake = terraingenerator.Map.Terrain_mapper("LAKE");
        List<Point> plains = terraingenerator.Map.Terrain_mapper("PLAINS");

        JFrame frame = new JFrame();
        JFrame frame2 = new JFrame();
        frame.setSize(120*10, 80*10);
        final ImageIcon image = new ImageIcon(new ImageIcon("assets/img.jpeg").getImage().getScaledInstance(120*10, 80*10, Image.SCALE_DEFAULT));
        frame.setLayout(new BorderLayout());

        // Start Button
        JLabel bg= new JLabel(image);
        frame.add(bg);
        bg.setLayout(null);
        int fixedButtonWidth = 250;
        int fixedButtonHeight = 20;
        final ImageIcon image2= new ImageIcon(new ImageIcon("assets/img2.jpeg").getImage().getScaledInstance(fixedButtonWidth,fixedButtonHeight,Image.SCALE_DEFAULT));
        JButton b= new JButton("Start the game",image2);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        b.setSize(150, 22);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Dialog", Font.PLAIN,15));
        b.setLocation(frame.getWidth()/2-40, frame.getHeight()/2+170);
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


        frame2.setSize(120*10, 80*10);
        frame2.setTitle("Test");
        //AreaComponent ac = new AreaComponent() ;
        frame2.getContentPane().add(terrain);
        //frame2.getContentPane().add(ac) ;
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }


}
