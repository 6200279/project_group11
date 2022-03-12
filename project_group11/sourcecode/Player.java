package sourcecode ;


import java.util.ArrayList;
import java.awt.Rectangle;

 public class Player {

    private final int radius = 25 ;
    private Point location;
    private double speed;
    private String facing = "D"; // either: "U" up, "D" down, "R" right, "L" left
    private POV pov;
    private ArrayList<Point> visited;
    private ArrayList<Rectangle> rectw = new ArrayList<>();
    private Point [] sharedArr;
    private final String myId;

    public Player(Point location, double speed, int width, int height, Point [] sharedArr, String id){
        this.location = location;
        this.speed = speed;
        myId = id;
        visited = new ArrayList<>();
        this.sharedArr= sharedArr;
        pov = new POV(sharedArr);
        pov.see(facing, location);
    }

    public Point getLocation(){ return location;}
    public double getSpeed(){ return speed;}
    public int getRadius(){ return radius;}
    public String getDirection(){ return facing;}
    public String getId(){ return myId;}
    public ArrayList<Point> getVisited(){ return visited;}
    public POV getPOV(){ return pov;}

    public void setLocation(Point location){ this.location =  location;  }
    public void setSpeed(double speed){ this.speed = speed;}
    public void setFacing(String new_direction){ this.facing = new_direction;}
    public void setRectw(ArrayList<Rectangle> rectw) { this.rectw = rectw;}

    public Point getSharedData(int x, int y){
        int hash = ((x+y)*(x+y+1)/2)+y;
        return sharedArr[hash];
   }

    public void moveToPoint(Point target){
        String facing = getWhereFacing(location, target);
        moveInDirection(facing);
    }

    public void moveInDirection(String direction){
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
            location = target.getTeleportTarget();
            unSee();

        } else {
            location = target;
        }
     }


     public void MDFS_Algorithm(){
        if(!location.getExploredMDFS()){  //if this cell is unexplored 
            location.setExploredMdfs(true);
            location.setExplorerID(myId);
        }
        ArrayList<Point> unexplored = UnExploredAround(location);
        
        if(unexplored.size()>0) { //check for unexplored points around current pos
            Point chosenTarget = unexplored.get(rndIndex(unexplored));
            chosenTarget.setParentMDFS(location);
            visited.add(new Point(location.getX(),location.getY()));
            moveToPoint(chosenTarget);

        }
        else if(unexplored.size()==0){ // else if all neighbour cells around me are explored
            System.out.println("agent "+myId+ " has Nothing to explore");
            if(location.getExplorerID().equals(myId)){
                location.setVisitedMdfs(true);
                visited.add(new Point(location.getX(),location.getY()));
                moveToPoint(location.getParentMDFS());
 
            }
            else{ // else if this spot is marked as explored by another ID 
                ArrayList<Point> neighbours = getNeighbours(location);
                Point chosenTarget = neighbours.get(rndIndex(neighbours));
                chosenTarget.setParentMDFS(location);
                visited.add(new Point(location.getX(),location.getY()));
                moveToPoint(chosenTarget);
            }
        }
    }

     
    public ArrayList<Point> UnExploredAround(Point myLocation){
        ArrayList<Point> unExploredNeighbours = new ArrayList<>(); 
        ArrayList<Point> AdjacentPoints = getNeighbours(myLocation);
        for(Point neighbour: AdjacentPoints){
            if(!neighbour.getExploredMDFS() && !neighbour.getIsWall() && !collision(neighbour)){
                unExploredNeighbours.add(neighbour);
            }
        }
        return unExploredNeighbours;
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
       visited.add(new Point(location.getX(),location.getY()));
   }

   

    public void Ants_Algorithm(){
            unSee();
            location.steppedOn++;
            ArrayList<Point> neighbours = getNeighbours(location);
            Point target = getLeastChecked(neighbours);
            facing = getWhereFacing(location, target);
            visited.add(new Point(location.getX(),location.getY()));
            moveInDirection(facing);
            //moveToPoint(target);
        }

    public Point getLeastChecked(ArrayList<Point> neighbours){
                Point min = neighbours.get(0);
                for(Point n: neighbours){
                    if(n.getY() == min.getY() && n.getX() == min.getX()) continue;
                    if(n.steppedOn < min.steppedOn)  min = n;
                    else if(n.steppedOn == min.steppedOn){ // if there is a tie for least walked direction, randomize the decision
                        int r = ((int)(Math.random() * 2));
                        if(r==0) min = n;
                    }
                }
                return min;
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

   public Integer rndIndex(ArrayList<Point> array){
       return  ((int)(Math.random() * array.size()));
 
   }
}
