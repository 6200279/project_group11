package sourcecode ;


import java.util.ArrayList;
import java.awt.Rectangle;

 public class Player {

    private final int radius = 25 ;
    private final int pov_radius = 55;
    private Point location;
    private double speed;
    private String facing = "D"; // either: "U" up, "D" down, "R" right, "L" left
    private POV pov;
    private ArrayList<Point> visited_4_GUI;
    private ArrayList<Rectangle> rectw = new ArrayList<>();
    private Point [] sharedArr;
    private final String myId;
    private Point lastLoc;

    public Player(Point location, double speed, int width, int height, Point [] sharedArr, String id){
        this.location = location;
        this.speed = speed;
        myId = id;
        visited_4_GUI = new ArrayList<>();
        this.sharedArr= sharedArr;
        pov = new POV(sharedArr, pov_radius);
        lastLoc = getNeighbours(location).get(0);
    }

    public Point getLocation(){ return location;}
    public double getSpeed(){ return speed;}
    public int getRadius(){ return radius;}
    public String getDirection(){ return facing;}
    public String getId(){ return myId;}
    public POV getPOV(){ return pov;}
    public Point getLastLocation(){ return lastLoc;}
    public ArrayList<Point> getVisited_4_GUI() {return visited_4_GUI;}

    public void setLocation(Point location){ this.location =  location;  }
    public void setSpeed(double speed){ this.speed = speed;}
    public void setFacing(String new_direction){ this.facing = new_direction;}
    public void setRectw(ArrayList<Rectangle> rectw) { 
        this.rectw = rectw;
        pov.setRectw(rectw);
    }

    public Point getSharedData(int x, int y){
        int hash = ((x+y)*(x+y+1)/2)+y;
        return sharedArr[hash];
   }

    public void moveToPoint(Point target){
        String facing = getWhereFacing(location, target);
        moveInDirection(facing);
        int f=0;
    }

    public void moveInDirection(String direction){
        pov.see(facing, location);
        int x = location.getX();
        int y = location.getY();
        Point target = null;
        if (direction.equals("U")) {
            facing = "U";
            //if(y-1< 0){ System.out.println("Ilegal move"); return;}
            target = getSharedData(x, y-1);
        } else if (direction.equals("D")) {
            facing = "D";
            //if(y+1 >= grid.get(0).size()){ System.out.println("Ilegal move"); return;}
            target = getSharedData(x, y+1);
        } else if (direction.equals("L")) {
            facing = "L";
            //if(x-1< 0){ System.out.println("Ilegal move"); return;}
            target = getSharedData(x-1, y);
        } else if (direction.equals("R")) {
            facing = "R";
            //if(x+1 >= grid.size()){ System.out.println("Ilegal move"); return;}
            target  = getSharedData(x+1, y);
        }
        if (collision(target) || target.getIsWall()) {
            System.out.println("target is a wall");
            return;
        }
        pov.see(facing, location);

        if (target.getIsTeleport()) {
            lastLoc = location;
            location = target.getTeleportTarget();
            unSee();

        } else {
            lastLoc = location;
            location = target;
        }
     }

    public void moveInPath(ArrayList<Point> path){
        for(Point p: path){
            moveToPoint(p);
        }
     }

     public void spinAround(){
         pov.see("U",location);
         unSee();
         pov.see("D", location);
         unSee();
         pov.see("L",location);
         unSee();
         pov.see("R", location);
         unSee();
     }

    public ArrayList<Point> getNeighbours(Point current){
 
        ArrayList<Point> neighbours = new ArrayList<>();
        int x = current.getX();
        int y = current.getY();
        Point left = getSharedData(x-1, y);
        Point right = getSharedData(x+1, y);
        Point up = getSharedData(x, y-1);
        Point down = getSharedData(x,y+1);
        if( !collision(left) && !left.getIsWall())      neighbours.add(left); //Point to the left
        if( !collision(right) && !right.getIsWall())     neighbours.add(right);
        if( !collision(up) && !up.getIsWall())        neighbours.add(up); //Point above
        if( !collision(down) && !down.getIsWall())      neighbours.add(down);

        return neighbours;

    }
    public void setWalls() {
            for(Point target : sharedArr){
                Rectangle rectangle1 = new Rectangle(target.getX()-radius/2,target.getY()-radius/2,radius,radius);
                rectangle1.setLocation(target.getX()-radius/2,target.getY()-radius/2);
                for (int i = 0; i < rectw.size(); i++) {
                    if (rectangle1.intersects(rectw.get(i))) {
                        target.setIsWall(true);
                    }
                }
            }
        }

    //based on your current location and the next point, get where the agent is facing
    public String getWhereFacing(Point current, Point target){
        int xa = current.getX(); //current x
        int ya = current.getY(); //current y
        int xb = target.getX(); //new x
        int yb = target.getY(); // new y

        if(xa==xb && ya < yb) return "D";
        if(xa==xb && ya > yb) return "U";
        if(ya==yb && xa < xb) return "R";
        if(ya==yb && xa > xb) return "L";
        return "";
    }

    public boolean collision(Point target) {

        Rectangle rectangle1 = new Rectangle(location.getX()-radius/2, location.getY()-radius/2,radius,radius);
        rectangle1.setLocation(target.getX()-radius/2,target.getY()-radius/2);

        for (int i = 0; i < rectw.size(); i++) {

            if (rectangle1.intersects(rectw.get(i))) {
                target.setIsWall(true);
                return true;
            }
        }
        return false;
    }

    
    public void moveRndom(){
        
       unSee();

       int randomFace = (int) (Math.random() * 4 + 1)+1;
       if (randomFace == 5) {
           facing = "D";
           //d.see(facing,location,checkedByAll);
           moveInDirection(facing);
       }
       if (randomFace == 2) {
           facing = "D";
           //d.see(facing,location,checkedByAll);
           moveInDirection(facing);
       }
       if (randomFace == 3) {
           facing = "D";
           //d.see(facing,location,checkedByAll);
           moveInDirection(facing);
       }
       if (randomFace == 4) {
           facing = "D";
           //d.see(facing,location,checkedByAll);
           moveInDirection(facing);
       }
       visited_4_GUI.add(new Point(location.getX(),location.getY()));
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
}
