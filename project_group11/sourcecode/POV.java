package sourcecode;
import java.lang.reflect.Array;
import java.util.ArrayList;




public class POV {

    private final int radius = 55 ;
    Point [] sharedArray;
    ArrayList<Point> currently_watched;

    public POV( Point [] sharedArray){
        this.sharedArray = sharedArray;
        currently_watched = new ArrayList<>();
    }

    public ArrayList<Point> getCurrentlyWatched(){ return currently_watched;}

    public void see(String facing, Point location){
        if(facing.equals("R")){
            ArrayList<Point> straightP = getStraightPath(location, "R");
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
            ArrayList<Point> straightP = getStraightPath(location, "L");
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
            ArrayList<Point> straightP = getStraightPath(location, "U");
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
            ArrayList<Point> straightP = getStraightPath(location, "D");
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
    
    public ArrayList<Point> getStraightPath(Point current, String direction){
        ArrayList<Point> straightPath = new ArrayList<>();
        int i=0;
        straightPath.add(current);
        while(i<=radius){
            int x = current.getX();
            int y = current.getY();
            Point next= null;
            if(direction.equals("R")){
                next =  getSharedData(x+1, y);
                if(next.getIsWall()|| next.getIsDoor()){ break;}
                straightPath.add(next);
            }
            else if(direction.equals("L")){
                next =  getSharedData(x-1,y);
                if(next.getIsWall()|| next.getIsDoor()){ break;}
                straightPath.add(next);
            }
            else if(direction.equals("U")){
                next =  getSharedData(x,y-1);
                if(next.getIsWall()|| next.getIsDoor()){ break;}
                straightPath.add(next);
            }
            else if(direction.equals("D")){
                next =  getSharedData(x, y+1);
                if(next.getIsWall()|| next.getIsDoor()){ break;}
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

            if(current.getIsWall() || current.getIsDoor()){ 
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
            if(p.getIsDoor()||p.getIsWall()){ break;}
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

}
