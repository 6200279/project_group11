package sourcecode ;

import java.util.ArrayList;

 public class Player {

    Point location;
    Descrete_ViewPoint d ;
    double speed;
    String facing; // either: "U" up, "D" down, "R" right, "L" left
    private final int radius = 25 ;


    public Player(Point location, double speed, String direction){
        this.location = location;
        this.speed = speed;
        this.facing = direction;
        d = new Descrete_ViewPoint(location, facing);
    }


    public Point getLocation(){ return location;}
    public double getSpeed(){ return speed;}
    public int getRadius(){ return radius;}
    public String getDirection(){ return facing;}

    public void setLocation(Point location){ this.location =  location;}
    public void setSpeed(double speed){ this.speed = speed;}
    public void moveDirection(String new_direction){ this.facing = new_direction;}

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

    public void moveInDirection(String direction, ArrayList<ArrayList<Point>> grid){
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

  int randomX = (int) (Math.random() * 2 + 1);
  int randomY = (int) (Math.random() * 2 + 1);

  if (randomX == 1) location.setX(location.getX()+1);
  if (randomX == 2) location.setX(location.getX()-1);
  if (randomY == 1) location.setY(location.getY()+1);
  if (randomY == 2) location.setY(location.getY()-1);
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

}