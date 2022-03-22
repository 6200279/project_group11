package sourcecode ;


import java.util.ArrayList;
import java.awt.Rectangle;
import java.util.List;

public class Player {

    private final int radius = 10 ;
    private final int pov_radius = 20;
    private final int width;
    private final int height;
    private Point location;
    private double speed;
    private String facing = "D"; // either: "U" up, "D" down, "R" right, "L" left
    private POV pov;
    private ArrayList<Point> visited_4_GUI;
    private ArrayList<List<Integer>> tp = new ArrayList<>();
    private ArrayList<List<Integer>> obstacle = new ArrayList<>();
    private Point [] sharedArr;
    private final String myId;
    private Point lastLoc;
    private Point targetTeleport;
    private ArrayList<Point> myMap;
    private terraingenerator.Map tMap;
    private Algorithm algo;
    private int scale;

    public Player(Point location, double speed, int width, int height,String myId, int scale, terraingenerator.Map tMap,Algorithm algo ){
        this.location = location;
        this.speed = speed;
        this.myId = myId;
        this.width = width;
        this.height = height;
        this.tMap = tMap;
        this.algo = algo; 
        this.scale = scale;
        myMap = new ArrayList<>(width*scale*height*scale);
        for(int i=0; i<width*height*scale*scale; i++){
            myMap.add(null);
        }
        addPoint2Map(location.getX(), location.getY());
        visited_4_GUI = new ArrayList<>();
        pov = new POV(myMap, pov_radius,this,tMap);
        lastLoc = getNeighbours(location).get(0);
    }

    public Point getLocation(){ return location;}
    public double getSpeed(){ return speed;}
    public int getRadius(){ return radius;}
    public int getPOVRadius(){ return pov_radius;}
    public String getDirection(){ return facing;}
    public String getId(){ return myId;}
    public POV getPOV(){ return pov;}
    public Point getLastLocation(){ return lastLoc;}
    public ArrayList<Point> getVisited_4_GUI() {return visited_4_GUI;}
    public int getWidth(){ return width;}
    public int getHeight(){ return height;}
    public terraingenerator.Map getTMap(){ return tMap;}
    public Algorithm getAlgo(){ return algo;}
    public ArrayList<Point> getVisited4GUI() { return visited_4_GUI;}
    public int getScale(){ return scale;}
    public ArrayList<Point> getMyMap(){ return myMap;}

    public void setLocation(Point location){ this.location =  location;  }
    public void setSpeed(double speed){ this.speed = speed;}
    public void setFacing(String new_direction){ this.facing = new_direction;}
    public void setAlgo(Algorithm algo){ this.algo = algo;}
    
    public void setObstacle(ArrayList<List<Integer>> obstacle) {
        this.obstacle = obstacle;
        pov.setObstacle(obstacle);
    }
     public void setTp(ArrayList<List<Integer>> tp) {
         this.tp = tp;
     }

    public Point getSharedData(int x, int y){
        int hash = ((x+y)*(x+y+1)/2)+y;
        return sharedArr[hash];
   }

    public void moveToPoint(Point target){
        String facingg = getWhereFacing(location, target);
        if(!facingg.equals("NO"))
        moveInDirection(facingg);
    }

    public void moveInDirection(String direction){
       // pov.see(facing, location);
        int x = location.getX();
        int y = location.getY();
        pov.seeNextView(direction);
        getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
        if (direction.equals("U")) {
            
            Point t = getPoint(location.getX(),location.getY()-10);
            if(t==null) {
                addPoint2Map(location.getX(), location.getY()-10);
                t = getPoint(location.getX(), location.getY()-10);
            }
            if(t==null || collision(t) || t.getIsWall()) return;
             location =t;
            
            for (int i = 0; i < pov.getCurrentlyWatched().size(); i++) {
                Point p = getPoint(pov.getCurrentlyWatched().get(i).getX(), pov.getCurrentlyWatched().get(i).getY() - 10);
                if (p == null) {
                    addPoint2Map(pov.getCurrentlyWatched().get(i).getX(), pov.getCurrentlyWatched().get(i).getY() - 10);
                    p = getPoint(pov.getCurrentlyWatched().get(i).getX(), pov.getCurrentlyWatched().get(i).getY() - 10);
                }
                pov.getNextCurrentlyWatched().add(p);
            }

                pov.getCurrentlyWatched().clear();
                for (int i = 0; i < pov.getNextCurrentlyWatched().size(); i++) {
                    pov.getCurrentlyWatched().add(pov.getNextCurrentlyWatched().get(i));
                }
                pov.getNextCurrentlyWatched().clear();

        } else if (direction.equals("D")) {
            //if(y+1 >= grid.get(0).size()){ System.out.println("Ilegal move"); return;}
            Point t = getPoint(location.getX(),location.getY()+10);
            if(t==null) {
                addPoint2Map(location.getX(), location.getY()+10);
                t = getPoint(location.getX(), location.getY()+10);
            }
            if(t==null || collision(t) || t.getIsWall()) return;
             location =t;

            for (int i = 0; i < pov.getCurrentlyWatched().size(); i++) {
                Point p = getPoint(pov.getCurrentlyWatched().get(i).getX(), pov.getCurrentlyWatched().get(i).getY() + 10);
                if (p == null) {
                    addPoint2Map(pov.getCurrentlyWatched().get(i).getX(), pov.getCurrentlyWatched().get(i).getY() + 10);
                    p = getPoint(pov.getCurrentlyWatched().get(i).getX(), pov.getCurrentlyWatched().get(i).getY() + 10);
                }
                pov.getNextCurrentlyWatched().add(p);
            }
            pov.getCurrentlyWatched().clear();
            for (int i = 0; i < pov.getNextCurrentlyWatched().size(); i++) {
                pov.getCurrentlyWatched().add(pov.getNextCurrentlyWatched().get(i));
            }
            pov.getNextCurrentlyWatched().clear();
           /* if (target == null) {
                addPoint2Map(x, y+1);
                target = getPoint(x,y+1) ;
            }*/
        } else if (direction.equals("L")) {

            Point t = getPoint(location.getX()-10,location.getY());
            if(t==null) {
                addPoint2Map(location.getX()-10, location.getY());
                t = getPoint(location.getX()-10, location.getY());
            }
            if(t==null || collision(t) || t.getIsWall()) return;
             location =t;

            for (int i = 0; i < pov.getCurrentlyWatched().size(); i++) {
                Point p = getPoint(pov.getCurrentlyWatched().get(i).getX()-10, pov.getCurrentlyWatched().get(i).getY());
                if (p == null) {
                    addPoint2Map(pov.getCurrentlyWatched().get(i).getX()-10, pov.getCurrentlyWatched().get(i).getY());
                    p = getPoint(pov.getCurrentlyWatched().get(i).getX()-10, pov.getCurrentlyWatched().get(i).getY());
                }
                pov.getNextCurrentlyWatched().add(p);
            }
            pov.getCurrentlyWatched().clear();
            for (int i = 0; i < pov.getNextCurrentlyWatched().size(); i++) {
                pov.getCurrentlyWatched().add(pov.getNextCurrentlyWatched().get(i));
            }
            pov.getNextCurrentlyWatched().clear();
           /* if (target == null) {
                addPoint2Map(x-1, y);
                target = getPoint(x-1,y) ;
            }*/
        } else if (direction.equals("R")) {
            Point t = getPoint(location.getX()+10,location.getY());
            if(t==null) {
                addPoint2Map(location.getX()+10, location.getY());
                t = getPoint(location.getX()+10, location.getY());
            }
            if(t==null )
            return;
            if(collision(t)){
            System.out.println("colision");
            return; 
        }
            if(t.getIsWall()) 
            return;
             location =t;

            for (int i = 0; i < pov.getCurrentlyWatched().size(); i++) {
                Point p = getPoint(pov.getCurrentlyWatched().get(i).getX()+10, pov.getCurrentlyWatched().get(i).getY());
                if (p == null) {
                    addPoint2Map(pov.getCurrentlyWatched().get(i).getX()+10, pov.getCurrentlyWatched().get(i).getY());
                    p = getPoint(pov.getCurrentlyWatched().get(i).getX()+10, pov.getCurrentlyWatched().get(i).getY());
                }
                pov.getNextCurrentlyWatched().add(p);
            }
            pov.getCurrentlyWatched().clear();
            for (int i = 0; i < pov.getNextCurrentlyWatched().size(); i++) {
                pov.getCurrentlyWatched().add(pov.getNextCurrentlyWatched().get(i));
            }
            pov.getNextCurrentlyWatched().clear();
           /* if (target == null) {
                addPoint2Map(x+1, y);
                target = getPoint(x+1,y) ;
            }*/
        }
        
     /*   if (collision(target) || target.getIsWall() || target.getIsWindow()) {
            System.out.println("target is a wall or a window");
            return;
        }*/

      //  pov.see(facing, location);


      //  if (teleport(target)) {
         //   System.out.println("teleporting");
         //   lastLoc = location;
         //   location = targetTeleport;
         //   unSee();

       // } else {
            lastLoc = location;
            tMap.repaint();
        }
     //}

    public void moveInPath(ArrayList<Point> path){
        for(Point p: path){
            moveToPoint(p);
        }
     }
     public void spinAround(){
         pov.seeNextView("U");
         for(Point p: pov.getCurrentlyWatched()){
            visited_4_GUI.add(p);
         }
         // unSee();
         pov.seeNextView("D");
         for(Point p: pov.getCurrentlyWatched()){
            visited_4_GUI.add(p);
         }
         //unSee();
         pov.seeNextView("L");
         for(Point p: pov.getCurrentlyWatched()){
            visited_4_GUI.add(p);
         }
        // unSee();
         pov.seeNextView("R");
         for(Point p: pov.getCurrentlyWatched()){
            visited_4_GUI.add(p);
         }
        // unSee();
     }

    public ArrayList<Point> getNeighbours(Point current){
 
        ArrayList<Point> neighbours = new ArrayList<>();
        int x = current.getX();
        int y = current.getY();
        Point left,right,up,down;
        if(getPoint(x-10,y)== null) addPoint2Map(x-10, y);
        if(getPoint(x+10,y)== null) addPoint2Map(x+10, y);
        if(getPoint(x,y-10)== null) addPoint2Map(x, y-10);
        if(getPoint(x,y+10)== null) addPoint2Map(x, y+10);
        left = getPoint(x-10, y);
        right = getPoint(x+10, y);
        up = getPoint(x, y-10);
        down = getPoint(x, y+10);

        if( !collision(left) && !left.getIsWall() &&  !left.getIsWindow())      neighbours.add(left); //Point to the left
        if( !collision(right) && !right.getIsWall()&& !right.getIsWindow())     neighbours.add(right);
        if( !collision(up) && !up.getIsWall() && !up.getIsWindow())        neighbours.add(up); //Point above
        if( !collision(down) && !down.getIsWall() && !down.getIsWindow())      neighbours.add(down);
        return neighbours;
    }

    //based on your current location and the next point, get where the agent is facing
    public String getWhereFacing(Point current, Point target){
        if(target==null) return "NO";
        int xa = current.getX(); //current x
        int ya = current.getY(); //current y
        int xb = target.getX(); //new x
        int yb = target.getY(); // new y
        if(xa==xb && ya==yb) return "NO" ; 
        if(xa==xb && ya < yb) return "D";
        if(xa==xb && ya > yb) return "U";
        if(ya==yb && xa < xb) return "R";
        if(ya==yb && xa > xb) return "L";
        
        return "";
    }

    public boolean collision(Point target) {

        Rectangle rectangle1 = new Rectangle(location.getX()-radius/2, location.getY()-radius/2,radius,radius);
        rectangle1.setLocation(target.getX()-radius/2,target.getY()-radius/2);

        for (int i = 0; i < obstacle.size(); i++) {

            Rectangle obsRect= new Rectangle(obstacle.get(i).get(0),obstacle.get(i).get(1),obstacle.get(i).get(2),obstacle.get(i).get(3));
            if (rectangle1.intersects(obsRect)&&obstacle.get(i).get(4)==1) {
                target.setIsWall(true);
                //System.out.println("in a wall");
                return true;
            }
            if (rectangle1.intersects(obsRect)&&obstacle.get(i).get(4)==2) {
                target.setIsDoor(true);
                //System.out.println("in a door");
                return false;
            }
            if (rectangle1.intersects(obsRect)&&obstacle.get(i).get(4)==3) {
                target.setIsWindow(true);
                //System.out.println("in a window");
                return true;
            }
        }
        return false;
    }
    public boolean teleport(Point target) {

        Rectangle rectangle1 = new Rectangle(location.getX()-radius/2, location.getY()-radius/2,radius,radius);
        rectangle1.setLocation(target.getX()-radius/2,target.getY()-radius/2);

         for (int i = 0; i < tp.size(); i++) {


             Rectangle tpRect= new Rectangle(tp.get(i).get(0),tp.get(i).get(1),tp.get(i).get(2),tp.get(i).get(3));
             if (rectangle1.intersects(tpRect)){
                 targetTeleport = new Point(tp.get(i).get(4),tp.get(i).get(5));
                 return true;
             }
         }
        return false;
     }

     public void unSee(){
        pov.getCurrentlyWatched().clear(); 
   }

   public String OppositeDirec(String d){
       if(d.equals("U")) return "D";
       if(d.equals("L")) return "R";
       if(d.equals("R")) return "L";
       if(d.equals("D")) return "U";
       return "";
   }

    
        public void addPoint2Map(int x, int y){
        int hash = ((x+y)*(x+y+1)/2)+y;
       // if( x<0+scale+1 || x>=width-scale-1 || y<0+scale+1 || y>=height-scale-1) return;
        if(myMap.get(hash)==null) myMap.set(hash, new Point(x,y));
        else System.out.println("the point:" + myMap.get(hash)+ " already exists in the map");
    }

    public Point getPoint(int x, int y){
        if( x<0 || x>=width-scale-1 || y<0 || y>=height-scale-1){
            System.out.println("point : " +x+ ","+y +" out of bounds"); 
            return null;
        }
        int hash = ((x+y)*(x+y+1)/2)+y;
        return myMap.get(hash); 
    }



   /* public void isCollisionRightView(Point p){
        ArrayList<Area> walls = tMap.getScenario().getWalls();
        for (int i = 0; i < walls.size(); i++) {
            int y1 = walls.get(i).getY1() * 7;
            int y2 = walls.get(i).getY2() * 7;
            int ymax = Math.max(y1, y2);
            int ymin = Math.min(y1, y2);

            int x1 = walls.get(i).getX1() * 7;
            int x2 = walls.get(i).getX2() * 7;
            int xmax = Math.max(x1, x2);
            int xmin = Math.min(x1, x2);

            if (xmax > location.getX() && xmin > location.getX()) {
                if (p.getX()>=xmin&&p.getY()>=ymin&&p.getY()<=ymax)
                    p.setLegalView(false);
            }

           /* if(ymax>location.getY()&&ymin> location.getY()&&xmax> location.getX()){
                if(p.getY()>ymax)
                    p.setLegalView(false);
            }

            if(ymin< location.getY()&&ymax< location.getY()&&xmax> location.getX()){
                if (p.getY()<ymin)
                    p.setLegalView(false);

            }
        }

    }

public void isCollisionLeftView(Point p){
    ArrayList<Area> walls = map.getScenario().getWalls();
    for (int i = 0; i < walls.size(); i++) {
        int y1 = walls.get(i).getY1() * 7;
        int y2 = walls.get(i).getY2() * 7;
        int ymax = Math.max(y1, y2);
        int ymin = Math.min(y1, y2);

        int x1 = walls.get(i).getX1() * 7;
        int x2 = walls.get(i).getX2() * 7;
        int xmax = Math.max(x1, x2);
        int xmin = Math.min(x1, x2);

        if (xmax < location.getX() && xmin < location.getX()) {
            if (p.getX()<=xmax&&p.getY()>=ymin&&p.getY()<=ymax)
                p.setLegalView(false);
        }


       /* if(ymax>location.getY()&&ymin> location.getY()&&xmin< location.getX()){
            if(p.getY()>ymax)
                p.setLegalView(false);
        }

        if(ymin< location.getY()&&ymax< location.getY()&&xmin< location.getX()){
            if (p.getY()<ymin)
                p.setLegalView(false);

        }
    }

}*/
}
