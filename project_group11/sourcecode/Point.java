package sourcecode;

import java.util.Objects;

public class Point {
    private  int x, y;
    private  boolean isWall;
    private  boolean isTeleport;
    private  boolean isDoor;
    private  boolean isWindow;
    private Point targetTeleport = null;
    private boolean seen = false;
    private boolean visited = false;
    private boolean explored_MDFS = false;
    private boolean visited_MDFS = false;
    private boolean seenOnce = false;
    private String explorer_id = "";
    private Point parentMDFS;
    int steppedOn=0;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(){}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setIsWall(boolean isWall){
        this.isWall = isWall;
    }
    public void setIsDoor(boolean isDoor){
        this.isDoor = isDoor;
    }
    public void setIsWindow(boolean isWindow){
        this.isWindow = isWindow;
    }

    public void setIsTeleport(boolean isTeleport, Point targetPoint){
        this.isTeleport = isTeleport;
        if(isTeleport){
            targetTeleport = targetPoint;
        }
    }

    public boolean getIsWall(){ return isWall;}
    public boolean getIsTeleport(){ return isTeleport;}
    public boolean getIsDoor(){ return isDoor;}
    public boolean getIsWindow(){ return isWindow;}
    public boolean getIsSeen(){ return seen;}
    public boolean getIsVisited(){ return visited;}
    public boolean getVisitedMDFS() { return visited_MDFS; }
    public boolean getExploredMDFS() { return explored_MDFS; }
    public boolean getSeenOnce(){ return seenOnce;}
    public String getExplorerID() { return explorer_id; }
    public Point getParentMDFS(){ return parentMDFS;}

    public Point getTeleportTarget(){ return targetTeleport;}



    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }

    public boolean equalsPoint(Point other){
        if(x == other.getX() && y == other.getY()){
            return true;
        }
        return false;
    }

    public void setX(int xx ){
        x=xx ;
    }

    public void setY(int yy){
        y=yy ;
    }

    public void setIsSeen(boolean Isseen){
        //if(Isseen){ checked++;}
        seen = Isseen;
    }

    public void setIsVisited(boolean b) {
        visited = b;
    }

    public void setExploredMdfs(boolean explored_MDFS){ 
        this.explored_MDFS = explored_MDFS; 
    }
    public void setVisitedMdfs(boolean visited_MDFS){ 
        this.visited_MDFS = visited_MDFS; 
    }
    public void setExplorerID(String id){ 
        explorer_id = id;
    }
    public void setParentMDFS(Point p){
        parentMDFS = p;
    }
    public void setSeenOnce(boolean f){
        seenOnce = f;
    }
    // d = sqrt[(x-x)^2+(y-y)^2]
    public double getDistance(Point other){

      double a = Math.pow((other.getX()-x),2);
      double b = Math.pow((other.getY()-y),2);
      return Math.sqrt(a+b);
      
    } 
}