package sourcecode ;

 public class Player {

 Point location;
 Point pov;
 double speed;
 private final int radius = 25 ;


 public Player(Point location, Point pov, double speed){
 this.location = location;
 this.pov = pov;
 this.speed = speed;
 }

 public Point getLocation(){ return location;}
 public Point getPov(){ return pov;}
 public double getSpeed(){ return speed;}
 public int getRadius(){ return radius;}

 public void setLocation(Point location){ this.location =  location;}
 public void setPov(Point POV){ this.pov = POV;}
 public void setSpeed(double speed){ this.speed = speed;}

 public void moveToPoint(Point target){
 if(target.getIsWall() || target.getIsWindow()){
 System.out.println("Ilegal");
 return;
 }
 if(target.getIsTeleport()){
 location = target.getTeleportTarget();
 }
 location =target;
 }

 public void moveRandom(){

  int randomX = (int) (Math.random() * 2 + 1);
  int randomY = (int) (Math.random() * 2 + 1);

  if (randomX == 1) location.setX(location.getX()+1);
  if (randomX == 2) location.setX(location.getX()-1);
  if (randomY == 1) location.setY(location.getY()+1);
  if (randomY == 2) location.setY(location.getY()-1);
 }

 /*public void markSeenArea(Area visionField){ //field of vision

 if(!visionField.getIsTriangle()){
 System.err.println("Gave square area as input instead of triangle one");
 return;
 }
 double angle = getAngle(location, visionField.getLeftView(), visionField.getRightView());


 }*/


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