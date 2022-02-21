package sourcecode ;

import java.time.chrono.ChronoPeriod;
import java.util.ArrayList;

 public class Player {

    Point location;
    Descrete_ViewPoint d ;
    double speed;
    String facing; // either: "U" up, "D" down, "R" right, "L" left
    private final int radius = 25 ;
    ArrayList<ArrayList<Point>> grid;
    ArrayList<Point> visited;


    public Player(Point location, double speed, String direction, ArrayList<ArrayList<Point>> grid){
        this.location = location;
        this.speed = speed;
        this.facing = direction;
        this.grid = grid;
        grid = translateG(grid);
        d = new Descrete_ViewPoint(location, facing, grid);
        d.see(facing,location);
    }

    public Player(Point location, double speed, String direction, int width, int height){
        this.location = location;
        this.speed = speed;
        this.facing = direction;
        visited = new ArrayList<>();
        ArrayList<ArrayList<Point>> grid = new ArrayList<>();
        for(int i=0;i<height;i++){
            ArrayList<Point> row= new ArrayList<>();
            for(int j=0; j<width; j++){
                row.add(new Point(i,j));
            }
            grid.add(row);
        }
        this.grid = grid;
        d = new Descrete_ViewPoint(location, facing, grid);
        d.see(facing,location);

    }


    public Point getLocation(){ return location;}
    public double getSpeed(){ return speed;}
    public int getRadius(){ return radius;}
    public String getDirection(){ return facing;}
    public ArrayList<Point> getVisited(){ return visited;}

    public void setLocation(Point location){ this.location =  location;}
    public void setSpeed(double speed){ this.speed = speed;}
    public void setFacing(String new_direction){ this.facing = new_direction;}

    public void moveToPoint(Point target){
        if(target.getIsWall() || target.getIsWindow()){
        System.out.println("Ilegal");
            return;
        }
        if(target.getIsTeleport()){
            location = target.getTeleportTarget();
        }
        else{
            location = target;
        }
    }

    public void moveInDirection(String direction){
        int x = location.getX();
        int y = location.getY();
        Point target= null;
        if(direction.equals("U")){ 
            facing = "U";
            if(x-1 < 0 ){ System.out.println("Cannot move here"); return;}
            target = grid.get(x-1).get(y); 
        }
        else if(direction.equals("D")){
            facing = "D";
            if(x+1 >= grid.size() ){ System.out.println("Cannot move here"); return;}
            target = grid.get(x+1).get(y); 
        }
        else if(direction.equals("L")){
            facing = "L";
            if(y-1 <  0 ){ System.out.println("Cannot move here"); return;}
            target = grid.get(x).get(y-1); 
        }
        else if(direction.equals("R")){
            facing = "R";
            if(y+1 >= grid.get(0).size() ){ System.out.println("Cannot move here"); return;}
            target = grid.get(x).get(y+1); 
        }

        if(target.getIsWall() || target.getIsWindow()){
        System.out.println("Ilegal");
            return;
        }
        if(target.getIsTeleport()){
            location = target.getTeleportTarget();
        }
        else{
            location = target;
        }
    }


     public void moveRandom(){
        unSee();
        int randomFace = (int) (Math.random() * 4 + 1)+1;
       
        if (randomFace == 1) {
            facing = "U";
            d.see(facing,location);
            moveInDirection(facing);
        }
        if (randomFace == 2) {
            facing = "D";
            d.see(facing,location);
            moveInDirection(facing);
        }
        if (randomFace == 3) {
            facing = "L";
            d.see(facing,location);
            moveInDirection(facing);
        }
        if (randomFace == 4) {
            facing = "R";
            d.see(facing,location);
            moveInDirection(facing);
        }

        visited.add(new Point(location.getX(),location.getY()));
    }
 

    public double getAngle(Point a, Point b, Point c){
        double dx1 = a.getX()-b.getX();
        double dy1 = a.getY()-b.getY();
        double dx2 = b.getX()-c.getX();
        double dy2 = b.getY()-c.getY();
        double dot = dx1*dx2 + dy1*dy2;   // dot product of the 2 vectors
        double length = (dx1*dx1+dy1*dy1)*(dx2*dx2+dy2*dy2); // product of the squared lengths
        double angle = Math.acos(dot/Math.sqrt(length));
        return Math.toDegrees(angle);

    }

    public ArrayList<ArrayList<Point>> translateG(ArrayList<ArrayList<Point>> columnGrid){
        ArrayList<ArrayList<Point>> rowGrid = new ArrayList<>(columnGrid.get(0).size());
        for(int i =0; i < columnGrid.get(0).size(); i++){
            ArrayList<Point> row = new ArrayList<>();
            for(int j=0;j<columnGrid.size();j++){
                row.add(new Point());
            }
            rowGrid.add(row);
        }
        for(int i=0;i<columnGrid.size();i++){
            for(int j=0;j<columnGrid.get(0).size();j++){
                Point cgPoint = columnGrid.get(i).get(j);
                 
                if(cgPoint.getX()==i && cgPoint.getY()==j){
                    rowGrid.get(i).set(j,cgPoint);
                }
            }
        }
        return rowGrid;
    }


    public void unSee(){
        for(ArrayList<Point> row : grid){
            for(Point p: row){
                if(p.getIsSeen()){
                    p.setIsSeen(false);
                }
            }
        }
    }
}