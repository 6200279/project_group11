package sourcecode;

import java.util.Objects;

public class Point {
    private  double x, y;
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
    private boolean BFSVisited = false;
    private Point parentBfs;
    private boolean explored_MMDFS = false;
    private boolean visited_MMDFS = false;
    private boolean checked_MMDFS = false;
    private Point parentMMDFS;


    int steppedOn=0;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(){}

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
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
    //Breadth first search
    public void setBfsVisited(boolean b){ BFSVisited = b;}
    public boolean getIsBfsVisited() {return BFSVisited;}
    public void setParentBfs(Point p){parentBfs = p;}
    public Point getParentBfs(){return parentBfs;}
    
    
    
    public boolean getSeenOnce(){ return seenOnce;}
    
    public boolean getIsWall(){ return isWall;}
    public boolean getIsTeleport(){ return isTeleport;}
    public Point getTeleportTarget(){ return targetTeleport;}
    public boolean getIsDoor(){ return isDoor;}
    public boolean getIsWindow(){ return isWindow;}
    public boolean getIsSeen(){ return seen;}
    public boolean getIsVisited(){ return visited;}
    
    //MDFS
    public boolean getVisitedMDFS() { return visited_MDFS; }
    public boolean getExploredMDFS() { return explored_MDFS; }
    public Point getParentMDFS(){ return parentMDFS;}
    public String getExplorerID() { return explorer_id; }
    
    //MMDFS
    public boolean getVisitedMMDFS() { return visited_MMDFS; }
    public boolean getExploredMMDFS() { return explored_MMDFS; }
    public boolean getCheckedMMDFS() { return checked_MMDFS; }
    public Point getParentMMDFS(){ return parentMMDFS; }

    //
    
    

    



    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }

  //  @Override
   // public int hashCode() {
  //      return Objects.hash(x, y);
 //   }

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

    public void setVisitedMMdfs(boolean visited_MMDFS){
        this.visited_MMDFS = visited_MMDFS;
    }
    public void setExploredMMdfs(boolean explored_MMDFS){
        this.explored_MMDFS = explored_MMDFS;
    }
    public void setCheckedMMdfs(boolean checked_MMDFS) { this.checked_MMDFS = checked_MMDFS; }
    public void setParentMMDFS(Point p){
        parentMMDFS = p;
    } 

    // d = sqrt[(x-x)^2+(y-y)^2]
    public double getDistance(Point other){

      double a = Math.pow((other.getX()-x),2);
      double b = Math.pow((other.getY()-y),2);
      return Math.sqrt(a+b);
      
    } 
}