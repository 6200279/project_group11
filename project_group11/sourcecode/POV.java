package sourcecode;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.Rectangle;




public class POV {

    private final int radius;
    private Point [] sharedArray;
    private ArrayList<Point> currently_watched;
    private ArrayList<Rectangle> rectw = new ArrayList<>();
    private Point location; 

    public POV(Point [] sharedArray, int radius){
        this.sharedArray = sharedArray;
        currently_watched = new ArrayList<>();
        this.radius = radius;
    }

    public ArrayList<Point> getCurrentlyWatched(){ return currently_watched;}
    public void setRectw(ArrayList<Rectangle> r){ rectw = r;}

    public void see(String facing, Point location){
        this.location = location;
        if(facing.equals("R")){
            ArrayList<Point> straightP = getStraightPath(location, "R",radius);
            int i=0;
            for(Point p: straightP){
                ArrayList<Point> UR_DiagPath = getDiagonalPath(p, "UR",radius-i);
                ArrayList<Point> DR_DiagPath = getDiagonalPath(p, "DR",radius-i);
                seeDiagonal(UR_DiagPath);
                seeDiagonal(DR_DiagPath);
                i++;
            }
        }
        if(facing.equals("L")){
            ArrayList<Point> straightP = getStraightPath(location, "L",radius);
            int i=0;
            for(Point p: straightP){
                ArrayList<Point> UL_DiagPath = getDiagonalPath(p, "UL",radius-i);
                ArrayList<Point> DL_DiagPath = getDiagonalPath(p, "DL",radius-i);
                seeDiagonal(UL_DiagPath);
                seeDiagonal(DL_DiagPath);
                i++;
            }
        }
        if(facing.equals("U")){
            ArrayList<Point> straightP = getStraightPath(location, "U",radius);
            int i=0;
            for(Point p: straightP){
                ArrayList<Point> UL_DiagPath = getDiagonalPath(p, "UL",radius-i);
                ArrayList<Point> UR_DiagPath = getDiagonalPath(p, "UR",radius-i);
                seeDiagonal(UL_DiagPath);
                seeDiagonal(UR_DiagPath);
                i++;
            }
        }
        if(facing.equals("D")){
            ArrayList<Point> straightP = getStraightPath(location, "D",radius);
            int i=0;
            for(Point p: straightP){
                ArrayList<Point> UL_DiagPath = getDiagonalPath(p, "DL",radius-i);
                ArrayList<Point> DR_DiagPath = getDiagonalPath(p, "DR",radius-i);
                seeDiagonal(UL_DiagPath);
                seeDiagonal(DR_DiagPath);
                i++;
            }
        }
    }
    
    public ArrayList<Point> getStraightPath(Point current, String direction,int amount){
        ArrayList<Point> straightPath = new ArrayList<>();
        int i=0;
        straightPath.add(current);
        while(i<=amount){
            int x = current.getX();
            int y = current.getY();
            Point next= null;
            if(direction.equals("R")){
                next =  getSharedData(x+1, y);
                if(collision(next) || next.getIsWall()|| next.getIsDoor()){ break;}
                straightPath.add(next);
            }
            else if(direction.equals("L")){
                next =  getSharedData(x-1,y);
                if(collision(next) || next.getIsWall()|| next.getIsDoor()){ break;}
                straightPath.add(next);
            }
            else if(direction.equals("U")){
                next =  getSharedData(x,y-1);
                if(collision(next) || next.getIsWall()|| next.getIsDoor()){ break;}
                straightPath.add(next);
            }
            else if(direction.equals("D")){
                next =  getSharedData(x, y+1);
                if(collision(next) || next.getIsWall()|| next.getIsDoor()){ 
                    break;}
                straightPath.add(next);
            }
            current = next;
            i++;
        }
        return straightPath;
    }

    
    public ArrayList<Point> getDiagonalPath(Point current, String direction,int size){
        ArrayList<Point> diagonalPath = new ArrayList<>(radius);
        int i=0;
        while(i<=size){
            diagonalPath.add(current);
            int x = current.getX();
            int y = current.getY();

            if(collision(current) || current.getIsWall() || current.getIsDoor()){ 
                break;
            }
            if(direction.equals("UR")){ //Up right on the diagonal
                
                //if(cX+1 >= grid.size() || cY-1 <0) {break;} // prevent out of bounds
                current = getSharedData(x+1, y-1);
            }
            else if(direction.equals("DR")){ //Down right on the diagonal
                //if(cX+1 >= grid.size() || cY+1 >= grid.get(0).size()) {break;} // prevent out of bounds
                current = getSharedData(x+1, y+1); 
            }
            else if(direction.equals("UL")){ //Up Left on the diagonal
               // if(cX-1 < 0 || cY-1 < 0) {break;}
                current = getSharedData(x-1, y-1);
            }
            else if(direction.equals("DL")){ //Down Left on the diagonal
              //  if(cX-1 <0 || cY+1 >= grid.get(0).size()) {break;}
                current = getSharedData(x-1, y+1);
            }
            i++;
        }
        return diagonalPath;
    }

    public void seeDiagonal(ArrayList<Point> diagPath){
        for(Point p: diagPath){
            if(collision(p) || p.getIsDoor()||p.getIsWall()){ break;}
            currently_watched.add(p);
            p.setSeenOnce(true);
            
            //p.setIsSeen(true);
            //p.setExploredMdfs(true);
        }
    }


    public Point getSharedData(int x, int y){
        int hash = ((x+y)*(x+y+1)/2)+y;
        return sharedArray[hash];
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

}
