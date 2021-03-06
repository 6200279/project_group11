package sourcecode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Timer;
import java.util.List;


public final class terraingenerator {
    public static final int TIME_PER_MOVE = 50; //in milli seconds (now 200 moves per second)
    public static final Color CITY = new Color(214, 217, 223);
    public static final Color DESERT = new Color(255, 204, 102);
    public static final Color DESERT2 = new Color(255, 160, 102);
    public static final Color DIRT_ROAD = new Color(153, 102, 0);
    public static final Color FOREST = new Color(0, 102, 0);
    public static final Color HILLS = new Color(51, 153, 0);
    public static final Color LAKE = new Color(0, 153, 153);
    public static final Color MOUNTAINS = new Color(102, 102, 255);
    public static final Color OCEAN = new Color(0, 0, 153);
    public static final Color PAVED_ROAD = new Color(51, 51, 0);
    public static final Color PLAINS = new Color(102, 153, 0);
    private static final int DEFAULT_HEIGHT = 80;
    private static final int DEFAULT_WIDTH = 120;
    private static final int[] p = new int[512];
    private static final int[] permutation = {151, 160, 137, 91, 90, 15,
            131, 13, 201, 95, 96, 53, 194, 233, 7,  142, 8, 99, 37, 240, 21, 10, 23,
            190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33,
            88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 16225, 140, 36, 103, 30, 69,5, 71, 134, 139, 48, 27, 166,
            77, 146, 158, 231,  211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244,
            102, 143, 54, 65, 25, 63, 161,83, 111, 229, 122, 60, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196,
            135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123,
            5, 202, 38, 147, 118, 126,  207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
            223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
            129, 22, 39, 253, 19, 98, 108, 110, 79,255, 82, 85, 212, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228,
            251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107,
            49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254,
            138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180
    };
    public static long starttimer;
    public static long endtimer;
    public static int sc;

    static {
        for (int i = 0; i < 256; i++)
            p[256 + i] = p[i] = permutation[i];
    }

    private static double pernil_noise(double x, double y, double z) {
        // Find the unit cube that contains the point
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;
        int Z = (int) Math.floor(z) & 255;

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
        return (res + 1.0) /2.0;
    }

    private static double fading(double constant) {
        //return constant * constant * constant * (constant * (constant * 6 - 15) + 10);
        return constant;
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private static double gradiant(int hash, double x, double y, double z) {
        int h = hash & 15;
        double u = h < 8 ? x : y,
                v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }


    static class MyCoord {
        private int X;
        private int Y;

        public MyCoord() {
            this(0, 0);
        }

        public MyCoord(int X, int Y) {
            this.X = X;
            this.Y = Y;
        }

        public int getX() {
            return X;
        }

        public void setX(int X) {
            this.X = X;
        }

        public int getY() {
            return Y;
        }

        public void setY(int Y) {
            this.Y = Y;
        }
    }

    public static class Map extends JPanel implements ActionListener {


        private static int height;
        private static int width;
        private static double z;
        private static String BIOME;
        public final int scale;
        private final Scenario scenario;
        private final ArrayList<Area> walls;
        private final ArrayList<List<Integer>> obstacle;
        private final ArrayList<List<Integer>> tp;
        private final ArrayList<Area> doors;
        private final ArrayList<Area> windows;
        private final ArrayList<TelePortal> telePortals;
        private final ArrayList<Area> shaded;
        private Point[] sharedArr;
        private final HashMap<String, MyCoord> map = new HashMap<String, MyCoord>();
        private final ArrayList<Player> players;
        private ArrayList<Point> seenByAll;
        public ArrayList<Point> all_map_points;
        private ArrayList<Point> locationspawn;
        private boolean tpIsSet = false;
        private boolean obstacleIsSet = false;
        private boolean play = false;
        private boolean arrinstantiated = false;
        public List<Point> FOREST_points2 = new ArrayList<>();
        public List<Point> HILLs_points2 = new ArrayList<>();
        public List<Point> Desert_points2 = new ArrayList<>();
        public List<Point> LAKE_points2 = new ArrayList<>();
        public List<Point> MOUNTAINS_points2 = new ArrayList<>();
        public List<Point> PLAINS_points2 = new ArrayList<>();
        public List<Point> SNOW_points2 = new ArrayList<>();
        public List<Point> Dirt_Road2 = new ArrayList<>();
        double percentage;
        ArrayList<Point> shared =  new ArrayList<>();





        public String getPlace(Player player) {
            Point p = new Point((int) player.getLocation().getY()/scale,(int)player.getLocation().getX()/scale);
           // System.out.println("get place poitn :"+ p);
            String land="";
            if (FOREST_points2.contains(p)){
                land="FOREST";
                player.setactualSpeed(player.getSpeed()+1);
            } else if (HILLs_points2.contains(p)){
                land="HILLS";
                player.setactualSpeed(player.getSpeed()-1);
            } else if (Desert_points2.contains(p)){
                land="DESERT";
                player.setactualSpeed(player.getSpeed());
            } else if (PLAINS_points2.contains(p)){
                land="PLAINS";
                player.setactualSpeed(player.getSpeed()+2);
            } else if (MOUNTAINS_points2.contains(p)){
                land="MOUNTAINS";
                player.setactualSpeed(player.getSpeed()-1);
            } else if (LAKE_points2.contains(p)){
                land="LAKE";
                player.setactualSpeed(player.getSpeed()-1);
            } else if(SNOW_points2.contains(p)){
                land="SNOW";
                player.setactualSpeed(player.getSpeed()-1);
            }
            /*else{
                land="empty";
                player.setSpeed(14);
            }

             */
            //System.out.println(p);
            //System.out.println(Terrain_mapper("FOREST"));
            return land;
        }

        private final Timer tm;


        public Map(int height, int width, double z, int scale, String biome, Scenario scenario) {
            this.scenario = scenario;
            tm = new Timer(0, this);

            walls = scenario.getWalls();
            doors = scenario.getDoors();
            windows = scenario.getWindows();
            telePortals = scenario.getTeleportals();
            shaded = scenario.getShaded();
            players = new ArrayList<Player>();
            tp = new ArrayList<List<Integer>>();
            obstacle = new ArrayList<List<Integer>>();


            this.height = height;
            this.width = width;
            //System.out.println(height + "height");
            this.z = z;
            BIOME = biome;
            this.scale = scale;
            sc = scale;
            createPlayers();

        }




        public void changePlayToTrue() {
            if (!play) play = true;
            starttimer = System.currentTimeMillis();
            this.repaint();
        }

        public void changePlayToFalse() {
            if (play) play = false;
            endtimer = System.currentTimeMillis();
            float elapsed_seconds = (endtimer - starttimer) / 1000F;
            System.out.println("Time elapsed (seconds): " + elapsed_seconds + " seconds");
            repaint();
        }

        public void createPlayers() {

            int x1 = scenario.spawnAreaGuards.getX1() * scale + 25 / 2;
            int x2 = scenario.spawnAreaGuards.getX2() * scale - 25 / 2;
            int y1 = scenario.spawnAreaGuards.getY1() * scale + 25 / 2;
            int y2 = scenario.spawnAreaGuards.getY2() * scale - 25 / 2;

            // ArrayList<ArrayList<Integer>> lookedAt = new ArrayList<>();
            // for(int r=0; r<1200; r++ ){
            //     ArrayList<Integer> row = new ArrayList<>();
            //     for(int c=0; c<800; c++){
            //         row.add(0);
            //     }
            //     lookedAt.add(row);
            // }

            // ArrayList<Point> sharedArray = new ArrayList<>();
            // for(int i=0; i<800; i++){
            //     for(int j=0; j<1200;j++){
            //         sharedArray.add(new Point(i,j));
            //     }
            // }

            int maxX = 800;
            int maxY = 1200;
            int maxHash = ((maxX + maxY) * (maxX + maxY + 1) / 2) + maxY;

            sharedArr = new Point[maxHash + 1];

            for (int i = 0; i < maxY; i++) {
                for (int j = 0; j < maxX; j++) {
                    int hash = ((i + j) * (i + j + 1) / 2) + j;
                    sharedArr[hash] = new Point(i, j);
                }
            }

            for (int i = 0; i < scenario.numGuards; i++) {

                int xx = (int) (Math.random() * (x2 - x1)) + x1;
                int yy = (int) (Math.random() * (y2 - y1)) + y1;

                Point location = new Point(xx, yy);

                double speed = scenario.baseSpeedGuard;

                seenByAll = new ArrayList<>();
                Player player = new Player(location, speed, width, height, String.valueOf(i), scale);
                players.add(player);
            }


        }


        public void paint(Graphics g) {
            if (BIOME == "GREEK") {
                for (int i = 0; i < width; ++i) { //y
                    for (int j = 0; j < height; ++j) { // x
                        double x = (double) j / ((double) width);
                        double y = (double) i / ((double) height);
                        double n = pernil_noise(10 * x, 10 * y, z);
                        Point p = new Point(j, i);
                        //System.out.println("paint point p "+ p);
                        // Watter (or a Lakes)

                        if (n < 0.25) {
                            g.setColor(FOREST);
                            FOREST_points2.add(p);

                            map.put("FOREST", new MyCoord(j, i));


                        } else if (n >= 0.25 && n < 0.30) {
                            g.setColor(HILLS);
                            HILLs_points2.add(p);
                            map.put("HILLS", new MyCoord(j, i));
                        }
                        // Floors (or Planes)
                        else if (n >= 0.30 && n < 0.40) {
                            Desert_points2.add(p);
                            g.setColor(DESERT);
                            map.put("DESERT", new MyCoord(j, i));

                        } else if (n >= 0.40 && n < 0.5) {
                            g.setColor(LAKE);
                            LAKE_points2.add(p);
                            map.put("LAKE", new MyCoord(j, i));
                        } else if (n >= 0.5 && n < 0.70) {
                            g.setColor(PLAINS);
                            PLAINS_points2.add(p);
                            map.put("PLAINS", new MyCoord(j, i));
                        } else if (n >= 0.70 && n < 0.75) {
                            FOREST_points2.add(p);
                            g.setColor(FOREST);
                            map.put("FOREST", new MyCoord(j, i));
                        }
                        // Walls (or Mountains)
                        else if (n >= 0.75 && n < 0.85) {
                            g.setColor(Color.GRAY);
                            MOUNTAINS_points2.add(p);
                            map.put("MOUNTAINS", new MyCoord(j, i));
                        }
                        // Ice (or Snow)
                        else {
                            g.setColor(Color.white);
                            SNOW_points2.add(p);
                            map.put("SNOW", new MyCoord(j, i));
                        }
                        g.fillRect(i*scale  , j *scale , scale, scale);
                        MyCoord coord = map.get("FOREST");


                        // System.out.println(coord.getX() +" : "+coord.getY());
                    }
                }
            }

            if (BIOME == "SAHARA") {
                for (int i = 0; i < width; ++i) { // y
                    for (int j = 0; j < height; ++j) { // x
                        double x = (double) j / ((double) width);
                        double y = (double) i / ((double) height);
                        double n = pernil_noise(10 * x, 10 * y, z);
                        Point p = new Point(j, i);
                        // Watter (or a Lakes)

                        if (n < 0.25) {
                            g.setColor(DESERT);
                            Desert_points2.add(p);

                        } else if (n >= 0.25 && n < 0.30) {
                            g.setColor(DESERT);
                            Desert_points2.add(p);

                        }
                        // Floors (or Planes)
                        else if (n >= 0.30 && n < 0.40) {
                            g.setColor(DESERT2);
                            Desert_points2.add(p);



                        } else if (n >= 0.40 && n < 0.5) {
                            g.setColor(DIRT_ROAD);
                            Dirt_Road2.add(p);

                        } else if (n >= 0.50 && n < 0.75) {
                            g.setColor(DESERT);
                            Desert_points2.add(p);
                        }


                        // Walls (or Mountains)
                        else if (n >= 0.75 && n < 0.85) {
                            g.setColor(Color.GRAY);
                            MOUNTAINS_points2.add(p);

                        }
                        // Ice (or Snow)
                        else {
                            g.setColor(Color.white);
                            SNOW_points2.add(p);

                        }
                        g.fillRect(i * scale, j * scale, scale, scale);
                        MyCoord coord = map.get("FOREST");

                        // System.out.println(coord.getX() +" : "+coord.getY());

                    }
                }
            }
            int x1, x2, y1, y2, x, y;
            for (int i = 0; i < walls.size(); i++) {
                x1 = walls.get(i).getX1() * scale;
                x2 = walls.get(i).getX2() * scale;
                y1 = walls.get(i).getY1() * scale;
                y2 = walls.get(i).getY2() * scale;


                x = Math.min(x1,x2);
               // int xB = Math.max(x1,x2);
                y = Math.min(y1,y2) ;
                //yB = Math.max(y1,y2) ;
                int width = Math.abs(x1-x2) ;
                int height = Math.abs(y1-y2) ;
                if(!arrinstantiated) {
                    List<Integer> tmp = Arrays.asList(x, y, width, height, 1);
                    obstacle.add(tmp);
                }

                g.setColor(Color.black);
                g.drawRect(x, y, width, height);
                g.fillRect(x, y, width, height);

            }
            for (int i = 0; i < doors.size(); i++) {
                x1 = doors.get(i).getX1() * scale;
                x2 = doors.get(i).getX2() * scale;
                y1 = doors.get(i).getY1() * scale;
                y2 = doors.get(i).getY2() * scale;

                x = Math.min(x1,x2);
                y = Math.min(y1,y2) ;
                int width = Math.abs(x1-x2) ;
                int height = Math.abs(y1-y2) ;
                if(!arrinstantiated) {
                    List<Integer> tmp= Arrays.asList(x,y,width,height,2);
                    obstacle.add(tmp);
                }

                g.setColor(Color.yellow);
                g.drawRect(x, y, width, height);
                g.fillRect(x, y, width, height);

            }
            for (int i = 0; i < windows.size(); i++) {
                x1 = windows.get(i).getX1() * scale;
                x2 = windows.get(i).getX2() * scale;
                y1 = windows.get(i).getY1() * scale;
                y2 = windows.get(i).getY2() * scale;


                x = Math.min(x1,x2);
                y = Math.min(y1,y2) ;
                int width = Math.abs(x1-x2) ;
                int height = Math.abs(y1-y2) ;
                if (!arrinstantiated) {
                    List<Integer> tmp = Arrays.asList(x, y, width, height, 3);
                    obstacle.add(tmp);
                }
                g.setColor(Color.blue);
                g.drawRect(x,y,width,height);
                g.fillRect(x,y,width,height);

            }

            for (Player pl : players) {
                pl.setObstacle(obstacle);
                obstacleIsSet = true;
                pl.setTp(tp);
                tpIsSet = true;
            }

            for (int i = 0; i < telePortals.size(); i++) {
                x1 = telePortals.get(i).getX1() * scale;
                x2 = telePortals.get(i).getX2() * scale;
                y1 = telePortals.get(i).getY1() * scale;
                y2 = telePortals.get(i).getY2() * scale;

                x = Math.min(x1,x2);
                y = Math.min(y1,y2) ;
                int width = Math.abs(x1-x2) ;
                int height = Math.abs(y1-y2) ;
                if(!arrinstantiated) {
                    List<Integer> teleport = Arrays.asList(x, y, width, height, telePortals.get(i).xTarget * scale, telePortals.get(i).yTarget * scale, (int) telePortals.get(i).getNewOrientation() * scale);
                    tp.add(teleport);
                }
                g.setColor(Color.cyan);
                g.drawRect(x,y,width,height);
                g.fillRect(x,y,width,height);

        }
        arrinstantiated=true;

            for (int i = 0; i < shaded.size(); i++) {
                x1 = shaded.get(i).getX1() * scale;
                x2 = shaded.get(i).getX2() * scale;
                y1 = shaded.get(i).getY1() * scale;
                y2 = shaded.get(i).getY2() * scale;

                x = Math.min(x1, x2);
                y = Math.min(y1, y2);
                int width = Math.abs(x1 - x2);
                int height = Math.abs(y1 - y2);

                g.setColor(Color.DARK_GRAY);
                g.drawRect(x, y, width, height);
                g.fillRect(x, y, width, height);

            }

            if (scenario.targetArea != null) {
                Area targetArea = scenario.targetArea;
                x1 = targetArea.getX1() * scale;
                x2 = targetArea.getX2() * scale;
                y1 = targetArea.getY1() * scale;
                y2 = targetArea.getY2() * scale;

                x = Math.min(x1, x2);
                y = Math.min(y1, y2);
                int width = Math.abs(x1 - x2);
                int height = Math.abs(y1 - y2);

                g.setColor(Color.red);
                g.drawRect(x, y, width, height);
                g.fillRect(x, y, width, height);
            }

            if (scenario.spawnAreaIntruders != null) {
                Area spawnAreaIntruders = scenario.spawnAreaIntruders;
                x1 = spawnAreaIntruders.getX1() * scale;
                x2 = spawnAreaIntruders.getX2() * scale;
                y1 = spawnAreaIntruders.getY1() * scale;
                y2 = spawnAreaIntruders.getY2() * scale;

                x = Math.min(x1, x2);
                y = Math.min(y1, y2);
                int width = Math.abs(x1 - x2);
                int height = Math.abs(y1 - y2);

                g.setColor(Color.blue);
                g.drawRect(x, y, width, height);
                g.fillRect(x, y, width, height);

            }
            if (scenario.spawnAreaGuards != null) {
                Area spawnAreaGuards = scenario.spawnAreaGuards;
                x1 = spawnAreaGuards.getX1() * scale;
                x2 = spawnAreaGuards.getX2() * scale;
                y1 = spawnAreaGuards.getY1() * scale;
                y2 = spawnAreaGuards.getY2() * scale;

                x = Math.min(x1, x2);
                y = Math.min(y1, y2);
                int width = Math.abs(x1 - x2);
                int height = Math.abs(y1 - y2);

                g.setColor(Color.green);
                g.drawRect(x, y, width, height);
                g.fillRect(x, y, width, height);
            }
            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);
                int xx = p.getLocation().getX();
                int yy = p.getLocation().getY();
                int radius = p.getRadius();

                g.setColor(Color.blue);
                g.fillOval(xx-radius/2,yy-radius/2,radius,radius);
                System.out.println("terrain: "+getPlace(p));
                getPlace(p);

                for (Point watchedP : p.getPOV().getCurrentlyWatched()) {
                    g.setColor(new Color(181, 19, 234, 48));
                    g.fillRect(watchedP.getX(), watchedP.getY(), 1, 1);
                }
            }

            g.setColor(Color.red);
            g.fillRect(this.getWidth() - 150, this.getHeight() - 150, scenario.mapWidth, scenario.mapHeight);
            g.setColor(Color.black);
            g.drawRect(this.getWidth() - 150, this.getHeight() - 150, scenario.mapWidth, scenario.mapHeight);

            for (int j = 0; j < players.size(); j++) {
                Player player = players.get(j);
                for (int k = 0; k < player.getVisited_4_GUI().size(); k++) {
                    g.setColor(Color.green);
                    int xxx = player.getVisited_4_GUI().get(k).getX() / scale;
                    int yyy = player.getVisited_4_GUI().get(k).getY() / scale;
                    g.fillOval((this.getWidth() - 150) - player.getRadius() / scale / 2 + xxx, (this.getHeight() - 150) - player.getRadius() / scale / 2 + yyy, player.getRadius() / scale, player.getRadius() / scale);
                }
            }
            //   Player player = players.get(0);
            // for (Point p: seenByAll) {
            //         g.setColor(Color.green);
            //         int xxx = p.getX()/scale ;
            //         int yyy = p.getY()/scale ;
            //         g.fillOval((this.getWidth()-150)-player.getRadius()/scale/2+xxx,(this.getHeight()-150)-player.getRadius()/scale/2+yyy,player.getRadius()/scale,player.getRadius()/scale);
            //    }
            if (play)
                tm.start();
            long start = System.currentTimeMillis();

            /*try {
                Thread.sleep(TIME_PER_MOVE);
                long end = System.currentTimeMillis();
                float sec = (end - start) / 1000F;
                System.out.println(" time per move"+ sec + " seconds");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

             */



            if (!play) {
                tm.stop();
                long end = System.currentTimeMillis();
                float sec = (end - start) / 1000F;
                //System.out.println(" time per move" + sec + " seconds");


            }

        }


        public void actionPerformed(ActionEvent e) {
            //int u = 0;
            for (int i = 0; i < scenario.numGuards; i++) {

                try {
                    long start = System.currentTimeMillis();
                    Ants_Algorithm ants = new Ants_Algorithm(players.get(i));
                    //players.get(i).moveRndom();
                    //MDFS_Algorithm mdfs = new MDFS_Algorithm(players.get(i));
                    //B_Algorithm ben = new B_Algorithm(players.get(i));
                    updateRealPercentage();
                    long tempend = System.currentTimeMillis();
                    long wait = (tempend - start);
                    Thread.sleep(Math.abs((players.get(i).getMovespersec(wait))/4));
                    long end = System.currentTimeMillis();
                    float sec = (end - start) / 1000F;
                    //System.out.println( players.get(i).getActualSpeed()+" Moves per "+ sec*players.get(i).getActualSpeed()*4);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                this.repaint();

            }
        }

        private void updatePercentage(Player p1){

            for(Point p : p1.getVisited_4_GUI())
            {
                if(!shared.contains(p)) {
                    shared.add(p);
                    System.out.println("Point in shared" + p);
                    System.out.println("Size: " + shared.size());
                    System.out.println(scenario.mapWidth);
                    System.out.println(scenario.mapHeight);
                    percentage = (shared.size() * 100.00) / (scenario.mapWidth * scenario.mapHeight * 4);
                    System.out.println("Percentage : " + percentage);
                }
            }

        }

        public void updateRealPercentage(){

            for(Player p : players)
                updatePercentage(p);
        }
    }


}




