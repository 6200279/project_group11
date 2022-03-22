package sourcecode ;


import java.util.*;
import java.awt.Rectangle;

public class Player {

    private final int radius = 25 ;
    private final int pov_radius = 55;
    private Point location;
    private double speed;
    private String facing = "D"; // either: "U" up, "D" down, "R" right, "L" left
    private POV pov;
    private ArrayList<Point> visited_4_GUI;
    private ArrayList<List<Integer>> tp = new ArrayList<>();
    private ArrayList<List<Integer>> obstacle = new ArrayList<>();
    private Point [] sharedArr;
    private ArrayList<Point> seenByAll;
    private final String myId;
    private Point lastLoc;
    private Point targetTeleport;
    private ArrayList<Point> myMap;
    private ArrayList<Point> visitedBFS = new ArrayList<>();
    private long movespersec;
    private double actualSpeed;

    public double getActualSpeed() {
        return actualSpeed;
    }

    public void setactualSpeed(double actualSpeed) {
        this.actualSpeed = actualSpeed;
    }

    public Player(Point location, double speed, int width, int height, String myId, int scale){
        this.location = location;
        this.speed = speed;
        this.myId = myId;
        myMap = new ArrayList<>(width*10*height*10);
        for(int i=0; i<width*height*100; i++){
            myMap.add(null);
        }
        addPoint2Map(location.getX(), location.getY());
        visited_4_GUI = new ArrayList<>();
        pov = new POV(myMap, pov_radius,this);
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
    public void setActualSpeed(double speed){ this.speed = speed;}
    public void setFacing(String new_direction){ this.facing = new_direction;}
    
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
   public int movedistance(){
        return (int) (Math.random()*speed+1);
   }

    public void moveToPoint(Point target){
        String facing = getWhereFacing(location, target);
        moveInDirection(facing);
    }

    public void moveInDirection(String direction){
       // pov.see(facing, location);
        int x = location.getX();
        int y = location.getY();
        int viewSize = pov.getCurrentlyWatched().size();
        if(viewSize==0){
            pov.setFirstView();
        }
        Point target = null;
        pov.seeNextView(direction);
        getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
        if (direction.equals("U")) {
            if(location.getX()>0&&location.getY()-1>0) {
                Point t = getPoint(location.getX(), location.getY() - 1);
                if (t == null) {
                    addPoint2Map(location.getX(), location.getY() - 1);
                    t = getPoint(location.getX(), location.getY() - 1);
                }

                if (collision(t) || t.getIsWall() || t.getIsWindow()) {
                    return;
                }
                location = t;
            }
            
            for (int i = 0; i < pov.getCurrentlyWatched().size(); i++) {
                Point p = getPoint(pov.getCurrentlyWatched().get(i).getX(), pov.getCurrentlyWatched().get(i).getY() - 1);
                if (p == null) {
                    addPoint2Map(pov.getCurrentlyWatched().get(i).getX(), pov.getCurrentlyWatched().get(i).getY() - 1);
                    p = getPoint(pov.getCurrentlyWatched().get(i).getX(), pov.getCurrentlyWatched().get(i).getY() - 1);
                }
                pov.getNextCurrentlyWatched().add(p);
            }
                pov.getCurrentlyWatched().clear();
                for (int i = 0; i < pov.getNextCurrentlyWatched().size(); i++) {
                    pov.getCurrentlyWatched().add(pov.getNextCurrentlyWatched().get(i));
                }
                pov.getNextCurrentlyWatched().clear();

            if (target == null) {
                addPoint2Map(x, y-1);
                target = getPoint(x,y-1) ;
           }

        } else if (direction.equals("D")) {
            //if(y+1 >= grid.get(0).size()){ System.out.println("Ilegal move"); return;}
            if(location.getX()>0&&location.getY()+1>0) {
                Point t = getPoint(location.getX(), location.getY() + 1);
                if (t == null) {
                    addPoint2Map(location.getX(), location.getY() + 1);
                    t = getPoint(location.getX(), location.getY() + 1);
                }
                if (collision(t) || t.getIsWall() || t.getIsWindow()) {
                    return;
                }
                location = t;
                //  location.setX(location.getX());
                //  location.setY(location.getY()+1);
            }
             

            for (int i = 0; i < pov.getCurrentlyWatched().size(); i++) {
                Point p = getPoint(pov.getCurrentlyWatched().get(i).getX(), pov.getCurrentlyWatched().get(i).getY() + 1);
                if (p == null) {
                    addPoint2Map(pov.getCurrentlyWatched().get(i).getX(), pov.getCurrentlyWatched().get(i).getY() + 1);
                    p = getPoint(pov.getCurrentlyWatched().get(i).getX(), pov.getCurrentlyWatched().get(i).getY() + 1);
                }
                pov.getNextCurrentlyWatched().add(p);
            }
            pov.getCurrentlyWatched().clear();
            for (int i = 0; i < pov.getNextCurrentlyWatched().size(); i++) {
                pov.getCurrentlyWatched().add(pov.getNextCurrentlyWatched().get(i));
            }
            pov.getNextCurrentlyWatched().clear();
            if (target == null) {
                addPoint2Map(x, y+1);
                target = getPoint(x,y+1) ;
            }
        } else if (direction.equals("L")) {
            if(location.getX()-1>0&&location.getY()>0) {
                Point t = getPoint(location.getX() - 1, location.getY());
                if (t == null) {
                    addPoint2Map(location.getX() - 1, location.getY());
                    t = getPoint(location.getX() - 1, location.getY());
                }
                if (collision(t) || t.getIsWall() || t.getIsWindow()) {
                    return;
                }
                location = t;
                //      location.setX(location.getX()-1);
                //      location.setY(location.getY());
            }
             

            for (int i = 0; i < pov.getCurrentlyWatched().size(); i++) {
                Point p = getPoint(pov.getCurrentlyWatched().get(i).getX()-1, pov.getCurrentlyWatched().get(i).getY());
                if (p == null) {
                    addPoint2Map(pov.getCurrentlyWatched().get(i).getX()-1, pov.getCurrentlyWatched().get(i).getY());
                    p = getPoint(pov.getCurrentlyWatched().get(i).getX()-1, pov.getCurrentlyWatched().get(i).getY());
                }
                pov.getNextCurrentlyWatched().add(p);
            }
            pov.getCurrentlyWatched().clear();
            for (int i = 0; i < pov.getNextCurrentlyWatched().size(); i++) {
                pov.getCurrentlyWatched().add(pov.getNextCurrentlyWatched().get(i));
            }
            pov.getNextCurrentlyWatched().clear();
            if (target == null) {
                addPoint2Map(x-1, y);
                target = getPoint(x-1,y) ;
            }
        } else if (direction.equals("R")) {
            if(location.getX()+1>0&&location.getY()>0) {
                Point t = getPoint(location.getX() + 1, location.getY());
                if (t == null) {
                    addPoint2Map(location.getX() + 1, location.getY());
                    t = getPoint(location.getX() + 1, location.getY());
                }
                if (collision(t) || t.getIsWall() || t.getIsWindow()) {
                    return;
                }
                location = t;
            }

            for (int i = 0; i < pov.getCurrentlyWatched().size(); i++) {
                Point p = getPoint(pov.getCurrentlyWatched().get(i).getX()+1, pov.getCurrentlyWatched().get(i).getY());
                if (p == null) {
                    addPoint2Map(pov.getCurrentlyWatched().get(i).getX()+1, pov.getCurrentlyWatched().get(i).getY());
                    p = getPoint(pov.getCurrentlyWatched().get(i).getX()+1, pov.getCurrentlyWatched().get(i).getY());
                }
                pov.getNextCurrentlyWatched().add(p);
            }
            pov.getCurrentlyWatched().clear();
            for (int i = 0; i < pov.getNextCurrentlyWatched().size(); i++) {
                pov.getCurrentlyWatched().add(pov.getNextCurrentlyWatched().get(i));
            }
            pov.getNextCurrentlyWatched().clear();
            if (target == null) {
                addPoint2Map(x+1, y);
                target = getPoint(x+1,y) ;
            }
        }
        
     /*   if (collision(target) || target.getIsWall() || target.getIsWindow()) {
            System.out.println("target is a wall or a window");
            return;
        }*/

      //  pov.see(facing, location);


        if (teleport(target)) {
            System.out.println("teleporting");
            lastLoc = location;
            location = targetTeleport;
            unSee();

        } else {
            lastLoc = location;
        }
     }

    public void moveInPath(ArrayList<Point> path){
        for(Point p: path){
            moveToPoint(p);
        }
     }
     public void spinAround(){
        // pov.see("U",location);
        // unSee();
         //pov.see("D", location);
         //unSee();
        // pov.see("L",location);
        // unSee();
        // pov.see("R", location);
        // unSee();
     }

    public ArrayList<Point> getNeighbours(Point current){
 
        ArrayList<Point> neighbours = new ArrayList<>();
        int x = current.getX();
        int y = current.getY();
        Point left,right,up,down;
        if(getPoint(x-1,y)== null) addPoint2Map(x-1, y);
        if(getPoint(x+1,y)== null) addPoint2Map(x+1, y);
        if(getPoint(x,y-1)== null) addPoint2Map(x, y-1);
        if(getPoint(x,y+1)== null) addPoint2Map(x, y+1);
        left = getPoint(x-1, y);
        right = getPoint(x+1, y);
        up = getPoint(x, y-1);
        down = getPoint(x, y+1);

        if( !collision(left) && !left.getIsWall() &&  !left.getIsWindow())      neighbours.add(left); //Point to the left
        if( !collision(right) && !right.getIsWall()&& !right.getIsWindow())     neighbours.add(right);
        if( !collision(up) && !up.getIsWall() && !up.getIsWindow())        neighbours.add(up); //Point above
        if( !collision(down) && !down.getIsWall() && !down.getIsWindow())      neighbours.add(down);
        return neighbours;
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

        for (int i = 0; i < obstacle.size(); i++) {

            Rectangle obsRect= new Rectangle(obstacle.get(i).get(0),obstacle.get(i).get(1),obstacle.get(i).get(2),obstacle.get(i).get(3));
            if (rectangle1.intersects(obsRect)&&obstacle.get(i).get(4)==1) {
                target.setIsWall(true);
                //System.out.println("can't go through a wall");
                return true;
            }
            if (rectangle1.intersects(obsRect)&&obstacle.get(i).get(4)==2) {
                target.setIsDoor(true);
                //System.out.println("in a door");
                return false;
            }
            if (rectangle1.intersects(obsRect)&&obstacle.get(i).get(4)==3) {
                target.setIsWindow(true);
                //System.out.println("can't go through a window");
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
    public void moveRndom(){
       int randomFace = (int) (Math.random() * 4 + 1)+1;
       if (randomFace == 5) {
           facing = "D";
           moveInDirection(facing);
       }
       if (randomFace == 2) {
           facing = "L";

           //d.see(facing,location,checkedByAll);

           moveInDirection(facing);


       }
       if (randomFace == 3) {
           facing = "R";

           //d.see(facing,location,checkedByAll);

           moveInDirection(facing);


       }
       if (randomFace == 4) {
           facing="U";
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

   public ArrayList<Point> getBFSNeighbours(Point p){
    ArrayList<Point> BfsNeighbours = new ArrayList<Point>();
    ArrayList<Point> nArray = getNeighbours(p);
    for(Point a : nArray) {
         if(!a.getIsBfsVisited()) BfsNeighbours.add(a);
        }
    return BfsNeighbours;
    }

    

    public ArrayList<Point> BFS(Point s, int xt, int yt){
        ArrayList<Point> shortestPath = new ArrayList<>();
        Stack<Point> stack= new Stack<>();
        stack.push(s);
        s.setBfsVisited(true);
        Point target = getPoint(xt,yt);
        while(!stack.isEmpty()) {
            Point v = stack.pop();

            if((v.getX() == xt)&&(v.getY() == yt)){
                target = v;
                break;
            }
            for(Point neighbour : getBFSNeighbours(v)) {
                    stack.push(neighbour);
                    if(neighbour.getX()==xt && neighbour.getY()==yt){
                    }
                    neighbour.setParentBfs(v);
                    neighbour.setBfsVisited(true);
            }
        }
        
        Point n = target;
    
        while(true){
            
            if(n==null ||(n.getX()==s.getX() && n.getY() == s.getY())){
                break;
            }

            shortestPath.add(n);
            
            Point parent = n.getParentBfs(); 
            n = parent;
        }
        shortestPath.add(s);
        Collections.reverse(shortestPath);
        return shortestPath;
        }
    
   public void addPoint2Map(int x, int y){
        int hash = ((x+y)*(x+y+1)/2)+y;
       if(hash<0){
           return;
       }
        if(getPoint(x,y)==null) myMap.set(hash, new Point(x,y));
   }

   public Point getPoint(int x, int y){
        int hash = ((x+y)*(x+y+1)/2)+y;
        if(hash<0){
            return null;
        }
        return myMap.get(hash);
   
    }
    public long getMovespersec(long wait){
        movespersec=(1000-wait)/(long)actualSpeed;
        return movespersec;
    }

    // public static void main(String[] args) {
    //     Player p = new Player(new Point(5,5),10.0,8,12,"33",7);

        
    //     p.addPoint2Map(9,5);
    //     p.getPoint(9, 5).setIsWall(true);
    //     p.addPoint2Map(9,6);
    //     p.getPoint(9, 6).setIsWall(true);
    //     p.addPoint2Map(9,7);
    //     p.getPoint(9, 7).setIsWall(true);
    //     p.addPoint2Map(9,8);
    //     p.getPoint(9, 8).setIsWall(true);
    //     p.addPoint2Map(9,9);
    //     p.getPoint(9, 9).setIsWall(true);
    //     p.addPoint2Map(9,10);
    //     p.getPoint(9, 10).setIsWall(true);
    //     p.addPoint2Map(9,11);
    //     p.getPoint(9, 11).setIsWall(true);
    //     p.addPoint2Map(9,12);
    //     p.getPoint(9, 12).setIsWall(true);

    //     p.addPoint2Map(4,5);
    //     p.getPoint(4, 5).setIsWall(true);
    //     p.addPoint2Map(4,6);
    //     p.getPoint(4, 6).setIsWall(true);
    //     p.addPoint2Map(4,7);
    //     p.getPoint(4, 7).setIsWall(true);
    //     p.addPoint2Map(4,8);
    //     p.getPoint(4, 8).setIsWall(true);
    //     p.addPoint2Map(4,9);
    //     p.getPoint(4, 9).setIsWall(true);
    //     p.addPoint2Map(4,10);
    //     p.getPoint(4, 10).setIsWall(true);
    //     p.addPoint2Map(4,11);
    //     p.getPoint(4, 11).setIsWall(true);
    //     p.addPoint2Map(4,12);
    //     p.getPoint(4, 12).setIsWall(true);





    //     p.addPoint2Map(5,13);
    //     p.getPoint(5, 13).setIsWall(true);
    //     p.addPoint2Map(6,13);
    //     p.getPoint(6, 13).setIsWall(true);
    //     p.addPoint2Map(7,13);
    //     p.getPoint(7, 13).setIsWall(true);
    //     p.addPoint2Map(8,13);
    //     p.getPoint(8, 13).setIsWall(true);

    //     p.addPoint2Map(5,4);
    //     p.getPoint(5, 4).setIsWall(true);
    //     p.addPoint2Map(6,4);
    //     p.getPoint(6, 4).setIsWall(true);
    //     p.addPoint2Map(7,4);
    //     p.getPoint(7, 4).setIsWall(true);
    //     p.addPoint2Map(8,4);
    //     p.getPoint(8, 4).setIsWall(true);
        
    //    // p.addPoint2Map(5,5);
    //     p.addPoint2Map(6,5);
    //     p.addPoint2Map(7,5);
    //     p.addPoint2Map(8,5);

    //     p.addPoint2Map(5,6);
    //     p.getPoint(5, 6).setIsWall(true);
    //     p.addPoint2Map(6,6);
    //     p.getPoint(6, 6).setIsWall(true);
    //     p.addPoint2Map(7,6);
    //     p.getPoint(7, 6).setIsWall(true);
    //     p.addPoint2Map(8,6);

    //     p.addPoint2Map(5,7);
    //     p.addPoint2Map(6,7);
    //     p.addPoint2Map(7,7);
    //     p.addPoint2Map(8,7);

    //     p.addPoint2Map(5,8);
    //     p.getPoint(5, 8).setIsWall(true);
    //     p.addPoint2Map(6,8);
    //     p.addPoint2Map(7,8);
    //     p.getPoint(7, 8).setIsWall(true);
    //     p.addPoint2Map(8,8);
    //     p.getPoint(8, 8).setIsWall(true);
        
    //     p.addPoint2Map(5,9);
    //     p.getPoint(5, 9).setIsWall(true);
    //     p.addPoint2Map(6,9);
    //     p.addPoint2Map(7,9);
    //     p.addPoint2Map(8,9);
    //     p.getPoint(8, 9).setIsWall(true);
        
        
    //     p.addPoint2Map(5,10);
    //     p.addPoint2Map(6,10);
    //     p.getPoint(6, 10).setIsWall(true);
    //     p.addPoint2Map(7,10);
    //     p.addPoint2Map(7,10);

    //     p.addPoint2Map(5,11);
    //     p.addPoint2Map(6,11);
    //     p.addPoint2Map(7,11);
    //     p.addPoint2Map(8,11);
     
    //     p.addPoint2Map(5,12);
    //     p.getPoint(5, 12).setIsWall(true);
    //     p.addPoint2Map(6,12);
    //     p.getPoint(6, 12).setIsWall(true);
    //     p.addPoint2Map(7,12);
    //     p.getPoint(7, 12).setIsWall(true);
    //     p.addPoint2Map(8,12);

    //     System.out.println(p.BFS(p.getPoint(5, 5), 8, 12));
    // }

}
