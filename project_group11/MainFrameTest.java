package project_group11;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class MainFrameTest {
    private static final int DEFAULT_HEIGHT = 80;

    private static final int DEFAULT_WIDTH = 120;

    private Scenario scenario = new Scenario("testmap.txt") ;

    public static void main(String[] args) {
        int height = DEFAULT_HEIGHT;
        int width = DEFAULT_WIDTH;
        int scale = 10; // scale the map size
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
        frame.setSize(120*scale, 80*scale);
        frame.setTitle("Test");
        //AreaComponent ac = new AreaComponent() ;

        frame.getContentPane().add(terrain);

        //frame.getContentPane().add(ac) ;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

}
