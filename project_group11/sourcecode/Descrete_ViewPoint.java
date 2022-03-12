package sourcecode;
import java.util.ArrayList;



public class Descrete_ViewPoint {

    private final int radius = 55 ;
    private String facing;
    private Point location;
    ArrayList<ArrayList<Point>> grid = new ArrayList<>();
    int counter=0;
    ArrayList<Point> lookedAt = new ArrayList<>();
    ArrayList<ArrayList<Integer>> checkedByAll = new ArrayList<>();

    // public static void main(String[] args) {
    //     Descrete_ViewPoint d = new Descrete_ViewPoint(new Point(0, 0), "U");
    //     d.createGrid(10);
    //     d.setLocation(d.grid.get(5).get(3));
    //     d.grid.get(7).get(6).setIsWall(true);
    //     d.grid.get(6).get(6).setIsWall(true);
    //     d.setFacing("R");
    //     d.see(facing);
    //     d.printGrid();
    // }

    public Descrete_ViewPoint(Point location, String facing, ArrayList<ArrayList<Point>> grid){
        this.location = location;
        this.facing = facing;
        this.grid = grid;
    }

    public Point getLocation(){ return location; } 
    public String getFacing(){ return facing; }
    public int getRadius(){ return radius; }
    public void setFacing(String facing){ this.facing= facing; }
    public void setLocation(Point location){ this.location= location; }




    public void see(String facing, Point location, ArrayList<ArrayList<Integer>> checkedByAll){
        this.checkedByAll = checkedByAll;
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
            int cX = current.getX();
            int cY = current.getY();
            Point next= null;
            if(direction.equals("R")){
                if(cX+1>=grid.size()){ break;}
                next =  grid.get(cX+1).get(cY);
                if(next.getIsWall()|| next.getIsDoor()){ break;}
                straightPath.add(next);
            }
            else if(direction.equals("L")){
                if(cX-1 < 0 ){ break;}
                next =  grid.get(cX-1).get(cY);
                if(next.getIsWall()|| next.getIsDoor()){ break;}
                straightPath.add(next);
            }
            else if(direction.equals("U")){
                if(cY-1 < 0 ){ break;}
                next =  grid.get(cX).get(cY-1);
                if(next.getIsWall()|| next.getIsDoor()){ break;}
                straightPath.add(next);
            }
            else if(direction.equals("D")){
                if(cY+1 >= grid.get(0).size() ){ break;}
                next =  grid.get(cX).get(cY+1);
                if(next.getIsWall()|| next.getIsDoor()){ break;}
                straightPath.add(next);
            }
            current = next;
            i++;
        }
        return straightPath;
    }
    public void createGrid(int size){
        for(int i=0;i<size;i++){
            ArrayList<Point> row = new ArrayList<>(size);
            for(int j=0;j<size;j++){
                row.add(new Point(i, j));
            }
            grid.add(row);
        }
    }

    public ArrayList<Point> getDiagonalPath(Point current, String direction,int size){
        ArrayList<Point> diagonalPath = new ArrayList<>(radius);
        int i=0;
        while(i<=size){
            diagonalPath.add(current);
            int cX = current.getX();
            int cY = current.getY();

            if(current.getIsWall() || current.getIsDoor()){ 
                break;
            }
            if(direction.equals("UR")){ //Up right on the diagonal
                
                if(cX+1 >= grid.size() || cY-1 <0) {break;} // prevent out of bounds
                current = grid.get(cX+1).get(cY-1); 
            }
            else if(direction.equals("DR")){ //Down right on the diagonal
                if(cX+1 >= grid.size() || cY+1 >= grid.get(0).size()) {break;} // prevent out of bounds
                current = grid.get(current.getX()+1).get(current.getY()+1); 
            }
            else if(direction.equals("UL")){ //Up Left on the diagonal
                if(cX-1 < 0 || cY-1 < 0) {break;}
                current = grid.get(current.getX()-1).get(current.getY()-1);
            }
            else if(direction.equals("DL")){ //Down Left on the diagonal
                if(cX-1 <0 || cY+1 >= grid.get(0).size()) {break;}
                current = grid.get(current.getX()-1).get(current.getY()+1);
            }
            i++;
        }
        return diagonalPath;
    }

    public void seeDiagonal(ArrayList<Point> diagPath){
        for(Point p: diagPath){
            if(p.getIsDoor()||p.getIsWall()){ break;}
            p.setIsSeen(true);
            p.setIsLookedAt(true);
            lookedAt.add(p);
            int currentValue = checkedByAll.get(p.getX()).get(p.getY());
            checkedByAll.get(p.getX()).set(p.getY(),currentValue+1);
            //p.checked++;

        }
    }

    public void SeeLine(Point startNode,Point endNode, String direction){
        if(direction.equals("R")){
            while(!endNode.getIsSeen()){
                startNode.setIsSeen(true);
                //startNode.setIsLookedAt(true);
                
                lookedAt.add(startNode);
                int currentValue = checkedByAll.get(startNode.getX()).get(startNode.getY());
                checkedByAll.get(startNode.getX()).set(startNode.getY(),currentValue+1);
               // startNode.checked++;
                if(startNode.getIsDoor()|| startNode.getIsWall()){ break;}
                if(startNode.getY()+1 >= grid.get(0).size()){break;}
                startNode = grid.get(startNode.getX()).get(startNode.getY()+1);
            }
        }
            
        else if(direction.equals("L")){
            while(!endNode.getIsSeen()){
                startNode.setIsSeen(true);
                startNode.setIsLookedAt(true);
                lookedAt.add(startNode);
                int currentValue = checkedByAll.get(startNode.getX()).get(startNode.getY());
                checkedByAll.get(startNode.getX()).set(startNode.getY(),currentValue+1);
                //startNode.checked++;
                if(startNode.getIsDoor()|| startNode.getIsWall()){ break;}
                if(startNode.getY()-1 < 0){ break;}
                startNode = grid.get(startNode.getX()).get(startNode.getY()-1);
            }
        }
            
        else if(direction.equals("U")){
            while(!endNode.getIsSeen()){
                startNode.setIsSeen(true);
                startNode.setIsLookedAt(true);
                lookedAt.add(startNode);
                int currentValue = checkedByAll.get(startNode.getX()).get(startNode.getY());
                checkedByAll.get(startNode.getX()).set(startNode.getY(),currentValue+1);
               // startNode.checked++;
                if(startNode.getIsDoor()|| startNode.getIsWall()){ break;}
                if(startNode.getX()-1 < 0){break;}
                startNode = grid.get(startNode.getX()-1).get(startNode.getY());
            }
        }
            
        else if(direction.equals("D")){
            while(!endNode.getIsSeen()){
                startNode.setIsSeen(true);
                startNode.setIsLookedAt(true);
                lookedAt.add(startNode);
                int currentValue = checkedByAll.get(startNode.getX()).get(startNode.getY());
                checkedByAll.get(startNode.getX()).set(startNode.getY(),currentValue+1);
                //startNode.checked++;
                if(startNode.getIsDoor()|| startNode.getIsWall()){ break;}
                if(startNode.getX()+1 >= grid.size()){break;}
                startNode = grid.get(startNode.getX()+1).get(startNode.getY());
            }
        }
    }

    public ArrayList<Point> getLookedAtArray() { return lookedAt;}



    public void printGrid(){
        System.out.println();
        for(int i=0;i<grid.size();i++){
            System.out.println();
            for(int j=0;j<grid.get(0).size();j++){
                Point p = grid.get(i).get(j);
                //if(p.equals(location)){ System.out.println(" l ");}
                if(p.getIsDoor()){System.out.print(" D ");}
                if(p.getIsWall()){System.out.print(" W ");}
                if(p.getIsWindow()){System.out.print(" V ");}
                if(p.getIsSeen()){ System.out.print(" S ");}
                else{ System.out.print(" * "); }
            }
        }
    }
}
