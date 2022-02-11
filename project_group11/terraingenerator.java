package project_group11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;


public final class terraingenerator
{
    public static final Color CITY = new Color(214,217,223);
    public static final Color DESERT = new Color(255,204,102);
    public static final Color DIRT_ROAD = new Color(153,102,0);
    public static final Color FOREST = new Color(0,102,0);
    public static final Color HILLS = new Color(51,153,0);
    public static final Color LAKE = new Color(0,153,153);
    public static final Color MOUNTAINS = new Color(102,102,255);
    public static final Color OCEAN = new Color(0,0,153);
    public static final Color PAVED_ROAD = new Color(51,51,0);
    public static final Color PLAINS = new Color(102,153,0);

    private static final int DEFAULT_HEIGHT = 80;

    private static final int DEFAULT_WIDTH = 120;


// TODO hashmap is useless ill keep it in for now, use arraylist<point>
    static class MyCoord{
        private int X;
        private int Y;

        public MyCoord() {
            this(0,0);
        }
        public MyCoord(int X, int Y) {
            this.X = X;
            this.Y = Y;
        }
        public int getX() {
            return X;
        }
        public int getY() {
            return Y;
        }
        public void setX(int X) {
            this.X = X;
        }
        public void setY(int Y) {
            this.Y = Y;
        }
    }


    public static class Map extends JPanel
    {


        private final int scale;
        Scenario scenario = new Scenario("testmap.txt");

        ArrayList<Area>walls = scenario.getWalls() ;
        ArrayList<TelePortal>telePortals = scenario.teleports ;
        ArrayList<Area>shaded = scenario.shaded ;
        HashMap<String, MyCoord> map = new HashMap<String,MyCoord>();



        private static int height;


        private static int width;

        private static double z;

        private static String BIOME;

        public  Map(int height, int width, double z,int scale, String biome)
        {
            this.height = height;
            this.width = width;
            this.z = z;
            this.BIOME = biome;
            this.scale = scale;
        }
        public static  List<Point> Terrain_mapper (String terrainTYPE){
            List<Point> listempty = new ArrayList<>();
            List<Point> FOREST_points = new ArrayList<>();
            List<Point> HILLs_points = new ArrayList<>();
            List<Point> Desert_points = new ArrayList<>();
            List<Point> LAKE_points = new ArrayList<>();
            List<Point> MOUNTAINS_points = new ArrayList<>();
            List<Point> PLAINS_points = new ArrayList<>();
            List<Point> SNOW_points = new ArrayList<>();
if (BIOME == "GREEK") {
    for (int i = 0; i < width; ++i) { // y
        for (int j = 0; j < height; ++j) { // x
            double x = (double) j / ((double) width);
            double y = (double) i / ((double) height);
            double n = noise(10 * x, 10 * y, z);


            if (n < 0.25) {
                // FOREST
                FOREST_points.add(new Point(j, i));
                // System.out.println("forestpoints:"+FOREST_points.size());


            } else if (n >= 0.25 && n < 0.30) {
                // HILLS
                HILLs_points.add(new Point(j, i));
            }
            // DESERT
            else if (n >= 0.30 && n < 0.40) {

                Desert_points.add(new Point(j, i));


            } else if (n >= 0.40 && n < 0.5) {
                //LAKE
                LAKE_points.add(new Point(j, i));
            } else if (n >= 0.5 && n < 0.70) {
                // PLAINS
                PLAINS_points.add(new Point(j, i));
            } else if (n >= 0.70 && n < 0.75) {
                // FOREST
                FOREST_points.add(new Point(j, i));
            }
            // MOUNTAINS
            else if (n >= 0.75 && n < 0.85) {

                MOUNTAINS_points.add(new Point(j, i));
            }
            // Ice (or Snow)
            else {
                SNOW_points.add(new Point(j, i));
                //System.out.println("Snowpoints:"+SNOW_points.size());
            }
        }
    }
}
if (BIOME == "SAHARA"){
    for (int i = 0; i < width; ++i) { // y
        for (int j = 0; j < height; ++j) { // x
            double x = (double) j / ((double) width);
            double y = (double) i / ((double) height);
            double n = noise(10 * x, 10 * y, z);


            if (n < 0.25) {
                // FOREST
                Desert_points.add(new Point(j, i));
                // System.out.println("forestpoints:"+FOREST_points.size());


            } else if (n >= 0.25 && n < 0.30) {
                // HILLS
                Desert_points.add(new Point(j, i));
            }
            // DESERT
            else if (n >= 0.30 && n < 0.40) {

                Desert_points.add(new Point(j, i));


            } else if (n >= 0.40 && n < 0.5) {
                //LAKE
                Desert_points.add(new Point(j, i));
            } else if (n >= 0.5 && n < 0.70) {
                // PLAINS
                Desert_points.add(new Point(j, i));
            } else if (n >= 0.70 && n < 0.75) {
                // FOREST
                Desert_points.add(new Point(j, i));
            }
            // MOUNTAINS
            else if (n >= 0.75 && n < 0.85) {

                MOUNTAINS_points.add(new Point(j, i));
            }
            // Ice (or Snow)
            else {
                SNOW_points.add(new Point(j, i));
                //System.out.println("Snowpoints:"+SNOW_points.size());
            }
        }
    }

}

    if(terrainTYPE == "SNOW")
        return SNOW_points;
    if (terrainTYPE== "PLAINS")
        return PLAINS_points;
    if (terrainTYPE== "LAKE")
        return LAKE_points;
    if(terrainTYPE == "MOUNTAINS")
        return MOUNTAINS_points;
    if (terrainTYPE == "FOREST")
        return FOREST_points;
    if (terrainTYPE== "HILLS")
        return HILLs_points;
    if (terrainTYPE == "DESERT")
        return Desert_points;
    else
       return listempty; }



        public static void main(String[] args)
        {
            List<Point> points = new ArrayList<>();
            points.add(new Point (1,2));
            points.toString();

            int scale = 10;
            int height = DEFAULT_HEIGHT;
            int width = DEFAULT_WIDTH;
            //long seed = System.currentTimeMillis();
            long seed =100;
            if (args.length == 3)
            {
                height = Integer.parseInt(args[0]);
                width = Integer.parseInt(args[1]);
                seed = Long.parseLong(args[2]);
            }

            Random rand = new Random(seed);
            double z = rand.nextDouble();

            // current biomes, GREEK, SAHARA
            String biome= "SAHARA";
            Map terrain = new Map(height, width, z,scale, biome);
            List<Point> snow = Map.Terrain_mapper("SNOW");
            List<Point> forest = Map.Terrain_mapper("FOREST");
            List<Point> hills = Map.Terrain_mapper("HILLS");
            List<Point> Mountains = Map.Terrain_mapper("MOUNTAINS");
            List<Point> Desert = Map.Terrain_mapper("DESERT");
            List<Point> Lake = Map.Terrain_mapper("LAKE");
            List<Point> plains = Map.Terrain_mapper("PLAINS");



            System.out.println("BIOME TYPE :"+ biome);
            System.out.println("Amount of snowpoints:"+snow.size());
            System.out.println("Amount of forest points:"+forest.size());
            System.out.println("Amount of hills points:"+hills.size());
            System.out.println("Amount of Mountains points:"+Mountains.size());
            System.out.println("Amount of Desert points:"+Desert.size());
            System.out.println("Amount of lake points:"+Lake.size());
            System.out.println("Amount of plains points:"+plains.size());




            JFrame window = new JFrame();
            window.setSize(width * scale, height * scale);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.getContentPane().add(terrain);
            window.setVisible(true);
            window.setTitle("Seed: " + seed);
        }




        public void paint(Graphics g)
        {
    if (BIOME =="GREEK") {
        for (int i = 0; i < width; ++i) { // y
            for (int j = 0; j < height; ++j) { // x
                double x = (double) j / ((double) width);
                double y = (double) i / ((double) height);
                double n = noise(10 * x, 10 * y, z);

                // Watter (or a Lakes)

                if (n < 0.25) {
                    g.setColor(FOREST);

                    map.put("FOREST", new MyCoord(j, i));


                } else if (n >= 0.25 && n < 0.30) {
                    g.setColor(HILLS);
                    map.put("HILLS", new MyCoord(j, i));
                }
                // Floors (or Planes)
                else if (n >= 0.30 && n < 0.40) {
                    g.setColor(DESERT);
                    map.put("DESERT", new MyCoord(j, i));

                } else if (n >= 0.40 && n < 0.5) {
                    g.setColor(LAKE);
                    map.put("LAKE", new MyCoord(j, i));
                } else if (n >= 0.5 && n < 0.70) {
                    g.setColor(PLAINS);
                    map.put("PLAINS", new MyCoord(j, i));
                } else if (n >= 0.70 && n < 0.75) {
                    g.setColor(FOREST);
                    map.put("FOREST", new MyCoord(j, i));
                }
                // Walls (or Mountains)
                else if (n >= 0.75 && n < 0.85) {
                    g.setColor(Color.GRAY);
                    map.put("MOUNTAINS", new MyCoord(j, i));
                }
                // Ice (or Snow)
                else {
                    g.setColor(Color.white);
                    map.put("SNOW", new MyCoord(j, i));
                }
                g.fillRect(i * 10, j * 10, 10, 10);
                MyCoord coord = map.get("FOREST");

                // System.out.println(coord.getX() +" : "+coord.getY());
            }
        }
    }
    if (BIOME == "SAHARA"){
        for (int i = 0; i < width; ++i) { // y
            for (int j = 0; j < height; ++j) { // x
                double x = (double) j / ((double) width);
                double y = (double) i / ((double) height);
                double n = noise(10 * x, 10 * y, z);

                // Watter (or a Lakes)

                if (n < 0.25) {
                    g.setColor(DESERT);




                } else if (n >= 0.25 && n < 0.30) {
                    g.setColor(DESERT);
                    map.put("HIlls", new MyCoord(j, i));
                }
                // Floors (or Planes)
                else if (n >= 0.30 && n < 0.40) {
                    g.setColor(DESERT);
                    map.put("DESERT", new MyCoord(j, i));

                } else if (n >= 0.40 && n < 0.5) {
                    g.setColor(DESERT);
                    map.put("LAKE", new MyCoord(j, i));
                } else if (n >= 0.5 && n < 0.70) {
                    g.setColor(DESERT);
                    map.put("PLAINS", new MyCoord(j, i));
                } else if (n >= 0.70 && n < 0.75) {
                    g.setColor(DESERT);
                    map.put("FOREST", new MyCoord(j, i));
                }
                // Walls (or Mountains)
                else if (n >= 0.75 && n < 0.85) {
                    g.setColor(Color.GRAY);
                    map.put("MOUNTAINS", new MyCoord(j, i));
                }
                // Ice (or Snow)
                else {
                    g.setColor(Color.white);
                    map.put("SNOW", new MyCoord(j, i));
                }
                g.fillRect(i * 10, j * 10, 10, 10);
                MyCoord coord = map.get("FOREST");

                // System.out.println(coord.getX() +" : "+coord.getY());
            }
        }

    }




            for (int i = 0; i < walls.size(); i++) {
                int x1 = walls.get(i).getX1()*scale ;
                int x2 = walls.get(i).getX2()*scale ;
                int y1 = walls.get(i).getY1()*scale ;
                int y2 = walls.get(i).getY2()*scale ;

                int x = Math.min(x1,x2);
                int y = Math.min(y1,y2) ;
                int width = Math.abs(x1-x2) ;
                int height = Math.abs(y1-y2) ;

                g.setColor(Color.black);
                g.drawRect(x,y,width,height);
                g.fillRect(x,y,width,height);

            }

            for (int i = 0; i < telePortals.size(); i++) {
                int x1 = telePortals.get(i).getX1()*scale ;
                int x2 = telePortals.get(i).getX2()*scale ;
                int y1 = telePortals.get(i).getY1()*scale ;
                int y2 = telePortals.get(i).getY2()*scale ;

                int x = Math.min(x1,x2);
                int y = Math.min(y1,y2) ;
                int width = Math.abs(x1-x2) ;
                int height = Math.abs(y1-y2) ;

                g.setColor(Color.cyan);
                g.drawRect(x,y,width,height);
                g.fillRect(x,y,width,height);

            }

            for (int i = 0; i < shaded.size(); i++) {
                int x1 = shaded.get(i).getX1()*scale ;
                int x2 = shaded.get(i).getX2()*scale ;
                int y1 = shaded.get(i).getY1()*scale ;
                int y2 = shaded.get(i).getY2()*scale ;

                int x = Math.min(x1,x2);
                int y = Math.min(y1,y2) ;
                int width = Math.abs(x1-x2) ;
                int height = Math.abs(y1-y2) ;

                g.setColor(Color.DARK_GRAY);
                g.drawRect(x,y,width,height);
                g.fillRect(x,y,width,height);

            }

            Area targetArea = scenario.targetArea;
            int x1 = targetArea.getX1()*scale ;
            int x2 = targetArea.getX2()*scale ;
            int y1 = targetArea.getY1()*scale ;
            int y2 = targetArea.getY2()*scale ;

            int x = Math.min(x1,x2);
            int y = Math.min(y1,y2) ;
            int width = Math.abs(x1-x2) ;
            int height = Math.abs(y1-y2) ;

            g.setColor(Color.red);
            g.drawRect(x,y,width,height);
            g.fillRect(x,y,width,height);

            Area spawnAreaIntruders = scenario.spawnAreaIntruders ;
            x1 = spawnAreaIntruders.getX1()*scale ;
            x2 = spawnAreaIntruders.getX2()*scale ;
            y1 = spawnAreaIntruders.getY1()*scale ;
            y2 = spawnAreaIntruders.getY2()*scale ;

            x = Math.min(x1,x2);
            y = Math.min(y1,y2) ;
            width = Math.abs(x1-x2) ;
            height = Math.abs(y1-y2) ;

            g.setColor(Color.blue);
            g.drawRect(x,y,width,height);
            g.fillRect(x,y,width,height);


            Area spawnAreaGuards = scenario.spawnAreaGuards;
            x1 = spawnAreaGuards.getX1()*scale ;
            x2 = spawnAreaGuards.getX2()*scale ;
            y1 = spawnAreaGuards.getY1()*scale ;
            y2 = spawnAreaGuards.getY2()*scale ;

            x = Math.min(x1,x2);
            y = Math.min(y1,y2) ;
            width = Math.abs(x1-x2) ;
            height = Math.abs(y1-y2) ;

            g.setColor(Color.green);
            g.drawRect(x,y,width,height);
            g.fillRect(x,y,width,height);

        }

        private static double noise(double x, double y, double z)
        {
            // Find the unit cube that contains the point
            int X = (int)Math.floor(x) & 255;
            int Y = (int)Math.floor(y) & 255;
            int Z = (int)Math.floor(z) & 255;

            // Find relative x, y,z of point in cube
            x -= Math.floor(x);
            y -= Math.floor(y);
            z -= Math.floor(z);

            // Compute fade curves for each of x, y, z
            double u = fading(x);
            double v = fading(y);
            double w = fading(z);

            // Hash coordinates of the 8 cube corners
            int A = p[X] + Y;
            int AA = p[A] + Z;
            int AB = p[A + 1] + Z;
            int B = p[X + 1] + Y;
            int BA = p[B] + Z;
            int BB = p[B + 1] + Z;

            // Add blended results from 8 corners of cube
            double res = lerp(
                    w,
                    lerp(v, lerp(u, gradiant(p[AA], x, y, z), gradiant(p[BA], x - 1, y, z)),
                            lerp(u, gradiant(p[AB], x, y - 1, z), gradiant(p[BB], x - 1, y - 1, z))),
                    lerp(v, lerp(u, gradiant(p[AA + 1], x, y, z - 1), gradiant(p[BA + 1], x - 1, y, z - 1)),
                            lerp(u, gradiant(p[AB + 1], x, y - 1, z - 1), gradiant(p[BB + 1], x - 1, y - 1, z - 1))));
            return (res + 1.0) / 2.0;
        }

        private static double fading(double constant)
        {
            return constant * constant * constant * (constant * (constant * 6 - 15) + 10);
        }

        private static double lerp(double t, double a, double b)
        {
            return a + t * (b - a);
        }

        private static double gradiant(int hash, double x, double y, double z)
        {
            int h = hash & 15;
            double u = h < 8 ? x : y,
                    v = h < 4 ? y : h == 12 || h == 14 ? x : z;
            return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
        }

        private static final int p[] = new int[512];

        private static final int permutation[] = { 151, 160, 137, 91, 90, 15,
                131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23,
                190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33,
                88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166,
                77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244,
                102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196,
                135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123,
                5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
                223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
                129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228,
                251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107,
                49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254,
                138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180
        };

        static
        {
            for (int i = 0; i < 256; i++)
                p[256 + i] = p[i] = permutation[i];
        }

    }







    }




