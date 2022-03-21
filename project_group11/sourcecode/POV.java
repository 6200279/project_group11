package sourcecode;
import java.util.ArrayList;

import java.awt.Rectangle;
import java.util.List;


public class POV {

    private final int radius;
    private ArrayList<Point> myMap;
    private terraingenerator.Map tMap;
    private ArrayList<Point> currently_watched;
    private ArrayList<Point> next_currently_watched;
    private ArrayList<List<Integer>> obstacle = new ArrayList<>();
    private Point location; 
    private String facing="D";
    private Player player ;
    private boolean firstViewSet;
   // private terraingenerator.Map map ;

    public POV(ArrayList<Point> myMap, int radius,Player player, terraingenerator.Map map){
        this.myMap = myMap;
        this.tMap = map;
        currently_watched = new ArrayList<>();
        next_currently_watched = new ArrayList<>();
        this.radius = radius;
        this.player=player ;
        location = player.getLocation() ;
        //this.map = map ;
        setFirstView();
    }

    public ArrayList<Point> getNextCurrentlyWatched(){ return next_currently_watched;}

    public ArrayList<Point> getCurrentlyWatched(){ return currently_watched;}
    public void setObstacle(ArrayList<List<Integer>> r){ obstacle = r;}


    public void seeNextView( String nextfacing) {
        location = player.getLocation();
        int distanceX = 0;
        int distanceY = 0;
        int yNew = 0;
        int xNew = 0;
       // int x = location.getX() ;
        int x = player.getLocation().getX() ;
        int y = player.getLocation().getY() ;

        if (facing == "D") {

            if (nextfacing == "D"){
                for (int i = 0; i < currently_watched.size(); i++) {
                   // if(currently_watched.get(i)== null) continue;
                    Point p = getPoint(currently_watched.get(i).getX(), currently_watched.get(i).getY());
                  //  player.isCollisionDownView(p);
                    next_currently_watched.add(p);
                }
            }
            if (nextfacing == "U") {
                for (int i = 0; i < currently_watched.size(); i++) {
                    distanceY = currently_watched.get(i).getY() - y;
                    yNew = y - distanceY;

                    Point p = getPoint(currently_watched.get(i).getX(), yNew);


                    if (p == null) {
                        addPoint2Map(currently_watched.get(i).getX(), yNew);
                        p = getPoint(currently_watched.get(i).getX(), yNew) ;
                     //   player.isCollisionUpView(p);
                    }
                    next_currently_watched.add(p);
                }

            }


            if (nextfacing == "R") {
                for (int i = 0; i < currently_watched.size(); i++) {
                    distanceY = currently_watched.get(i).getY() - y;
                    distanceX = currently_watched.get(i).getX() - x;

                    xNew = x + distanceY;
                    yNew = y - distanceX;

                    Point p = getPoint(xNew, yNew);

                    if (p == null) {
                        addPoint2Map(xNew, yNew);
                        p = getPoint(xNew,yNew) ;
                     //   player.isCollisionRightView(p);
                    }
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);



                }

            }

            if (nextfacing == "L") {

                for (int i = 0; i < currently_watched.size(); i++) {
                    distanceY = currently_watched.get(i).getY() - y;
                    distanceX = currently_watched.get(i).getX() - x;

                    xNew = x - distanceY;
                    yNew = y + distanceX;

                    Point p = getPoint(xNew, yNew);

                    if (p == null) {
                        addPoint2Map(xNew, yNew);
                        p = getPoint(xNew,yNew) ;
                     //   player.isCollisionLeftView(p);
                    }
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);

                }

            }

        }

        else if (facing == "U") {

            if (nextfacing == "U"){
                for (int i = 0; i < currently_watched.size(); i++) {
                    if(currently_watched.get(i)==null){
                        int yuuuu=0;
                    }
                    Point p = getPoint(currently_watched.get(i).getX(), currently_watched.get(i).getY());
                  //  player.isCollisionUpView(p);
                  if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);
                }
            }

            if (nextfacing == "D") {

                for (int i = 0; i < currently_watched.size(); i++) {
                    distanceY = currently_watched.get(i).getY() - y;
                    distanceX = currently_watched.get(i).getX() - x;

                    yNew = y + ((-1) * distanceY);

                    Point p = getPoint(currently_watched.get(i).getX(), yNew);

                    if (p == null) {
                        addPoint2Map(currently_watched.get(i).getX(), yNew);
                        p = getPoint(currently_watched.get(i).getX(),yNew) ;
                     //   player.isCollisionDownView(p);
                    }
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);

                }
            }
            if (nextfacing == "R") {

                for (int i = 0; i < currently_watched.size(); i++) {
                    distanceY = currently_watched.get(i).getY() - y;
                    distanceX = currently_watched.get(i).getX() - x;

                    xNew = x - distanceY;
                    yNew = y + distanceX;

                    Point p = getPoint(xNew, yNew);

                    if (p == null) {
                        addPoint2Map(xNew, yNew);
                        p = getPoint(xNew,yNew) ;
                    //    player.isCollisionRightView(p);
                    }
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);

                }
            }

            if(nextfacing =="L") {
                for (int i = 0; i < currently_watched.size(); i++) {
                    distanceY = currently_watched.get(i).getY() - y;
                    distanceX = currently_watched.get(i).getX() - x;

                    xNew = x + distanceY;
                    yNew = y - distanceX;

                    Point p = getPoint(xNew, yNew);

                    if (p == null) {
                        addPoint2Map(xNew, yNew);
                        p = getPoint(xNew,yNew) ;
                       // player.isCollisionLeftView(p);
                    }
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);
                }
            }
        }

        else if(facing=="R"){

            if (nextfacing == "R"){
                for (int i = 0; i < currently_watched.size(); i++) {
                    Point p = getPoint(currently_watched.get(i).getX(), currently_watched.get(i).getY()) ;
                  //  player.isCollisionRightView(p);
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);
                }
            }

            if(nextfacing=="L"){

                for (int i = 0; i < currently_watched.size(); i++) {
                    distanceX = currently_watched.get(i).getX() - x;

                    xNew = x - distanceX;

                    Point p = getPoint(xNew, currently_watched.get(i).getY());

                    if (p == null) {
                        addPoint2Map(xNew, currently_watched.get(i).getY());
                        p = getPoint(xNew,currently_watched.get(i).getY()) ;
                       // player.isCollisionLeftView(p);
                    }
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);
                }

            }

            if(nextfacing=="U"){

                for (int i = 0; i < currently_watched.size(); i++) {
                    distanceY = currently_watched.get(i).getY() - y;
                    distanceX = currently_watched.get(i).getX() - x;

                    xNew = x + distanceY;
                    yNew = y - distanceX;

                    Point p = getPoint(xNew, yNew);
                    if (p == null) {
                        addPoint2Map(xNew, yNew);
                        p = getPoint(xNew, yNew) ;
                     //   player.isCollisionUpView(p);
                    }
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);
                }

            }

            if(nextfacing=="D"){
                for (int i = 0; i < currently_watched.size(); i++) {
                    distanceY = currently_watched.get(i).getY() - y;
                    distanceX = currently_watched.get(i).getX() - x;

                    xNew = x - distanceY;
                    yNew = y + distanceX;

                    Point p = getPoint(xNew, yNew);

                    if (p == null) {
                        addPoint2Map(xNew, yNew);
                        p = getPoint(xNew,yNew) ;
                     //   player.isCollisionDownView(p);
                    }
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);
                }
            }
        }

        else if(facing=="L"){

            if (nextfacing == "L"){
                for (int i = 0; i < currently_watched.size(); i++) {
                    Point p = getPoint(currently_watched.get(i).getX(), currently_watched.get(i).getY());
                  //  player.isCollisionLeftView(p);
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);
                }
            }

            if(nextfacing=="R"){
                for (int i = 0; i < currently_watched.size(); i++) {
                    distanceX = currently_watched.get(i).getX() - x;
                    xNew = x - distanceX;
                    Point p = getPoint(xNew, currently_watched.get(i).getY());

                    if (p == null) {
                        addPoint2Map(xNew, currently_watched.get(i).getY());
                        p = getPoint(xNew,currently_watched.get(i).getY()) ;
                    //    player.isCollisionRightView(p);
                    }
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);
                }

            }
            if(nextfacing=="U"){
                for (int i = 0; i < currently_watched.size(); i++) {
                    distanceY = currently_watched.get(i).getY() - y;
                    distanceX = currently_watched.get(i).getX() - x;

                    xNew = x - distanceY;
                    yNew = y + distanceX;

                    Point p = getPoint(xNew, yNew);
                    if (p == null) {
                        addPoint2Map(xNew, yNew);
                        p = getPoint(xNew,yNew) ;
                     //   player.isCollisionUpView(p);
                    }
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);
                }
            }

            if(nextfacing=="D"){
                for (int i = 0; i < currently_watched.size(); i++) {
                    distanceY = currently_watched.get(i).getY() - y;
                    distanceX = currently_watched.get(i).getX() - x;

                    xNew = x + distanceY;
                    yNew = y - distanceX;

                    Point p = getPoint(xNew, yNew);

                    if (p == null) {
                        addPoint2Map(xNew, yNew);
                        p = getPoint(xNew,yNew) ;
                     //   player.isCollisionDownView(p);
                    }
                    if(player.collision(p)) p.setIsWall(true);
                    next_currently_watched.add(p);
                }
            }

        }


        currently_watched.clear();
        for (int i = 0; i < next_currently_watched.size(); i++) {
            currently_watched.add(next_currently_watched.get(i));

        }

        next_currently_watched.clear();
        facing = nextfacing;
        tMap.repaint();
    }

    public String getFacing(){
        return facing ;
    }

    public void setFirstView(){
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
        else if(facing.equals("L")){
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
        else if(facing.equals("U")){
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
        else if(facing.equals("D")){
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
            Point next = null;
            if(direction.equals("R")){
                next = getPoint(x+1, y);
                if(next == null){
                    addPoint2Map(x+1, y);
                    next = getPoint(x+1, y);
                }
                if(collision(next) || next.getIsWall()|| next.getIsDoor()){ 
                    break;}
            }
            else if(direction.equals("L")){
                next = getPoint(x-1, y);
                if(next == null){
                    addPoint2Map(x-1, y);
                    next = getPoint(x-1, y);
                }
                if(collision(next) || next.getIsWall()|| next.getIsDoor()){ 
                    break;}
            }
            else if(direction.equals("U")){
                next = getPoint(x, y-1);
                if(next == null){
                    addPoint2Map(x, y-1);
                    next = getPoint(x, y-1);
                }
                if(collision(next) || next.getIsWall()|| next.getIsDoor()){ 
                    break;}
            }
            else if(direction.equals("D")){
                next = getPoint(x, y+1);
                if(next == null){
                    addPoint2Map(x, y+1);
                    next = getPoint(x, y+1);
                }
                if(collision(next) || next.getIsWall()|| next.getIsDoor()){ 
                    break;}
            }
            straightPath.add(next);
            current = next;
            i++;
        }
        return straightPath;
    }

    
    public ArrayList<Point> getDiagonalPath(Point current, String direction,int size){
        ArrayList<Point> diagonalPath = new ArrayList<>(radius);
        int i=0;
        while(i<=size){
            if(!diagonalPath.contains(current)){
            diagonalPath.add(current);}
            int x = current.getX();
            int y = current.getY();

            if(collision(current) || current.getIsWall() || current.getIsDoor()){ 
                break;
            }
            if(direction.equals("UR")){ //Up right on the diagonal
                current = getPoint(x+1, y-1);
                if(current == null){
                    addPoint2Map(x+1, y-1);
                    current = getPoint(x+1, y-1);

                }
            }
            else if(direction.equals("DR")){ //Down right on the diagonal
                current = getPoint(x+1, y+1);
                if(current == null){
                    addPoint2Map(x+1, y+1);
                    current = getPoint(x+1, y+1);

                } 
            }
            else if(direction.equals("UL")){ //Up Left on the diagonal
                current = getPoint(x-1, y-1);
                if(current == null){
                    addPoint2Map(x-1, y-1);
                    current = getPoint(x-1, y-1);

                }
            }
            else if(direction.equals("DL")){ //Down Left on the diagonal
                current = getPoint(x-1, y+1);
                if(current == null){
                    addPoint2Map(x-1, y+1);
                    current = getPoint(x-1, y+1);
                }
            }
            i++;
        }
        return diagonalPath;
    }

    public void seeDiagonal(ArrayList<Point> diagPath){
        for(Point p: diagPath){
            if(collision(p) || p.getIsDoor()||p.getIsWall())    break;

            //  if(!currently_watched.contains(p))    
            currently_watched.add(p);
            //if(!seenByAll.contains(p))
        }
    }

   public boolean collision(Point target) {
    
    Rectangle rectangle1 = new Rectangle(location.getX()-radius/2, location.getY()-radius/2,radius,radius);
    rectangle1.setLocation(target.getX()-radius/2,target.getY()-radius/2);

    for (int i = 0;i<obstacle.size();i++){

        Rectangle obsRect= new Rectangle(obstacle.get(i).get(0),obstacle.get(i).get(1),obstacle.get(i).get(2),obstacle.get(i).get(3));
        if (obsRect.contains(target.getX(),target.getY())&&obstacle.get(i).get(4)==1) {
            target.setIsWall(true);
            //System.out.println("is a wall");
            return true;
        }
        if (obsRect.contains(target.getX(),target.getY())&&obstacle.get(i).get(4)==2) {
            target.setIsDoor(true);
            //System.out.println("is a door");
            return true;
        }
        if (obsRect.contains(target.getX(),target.getY())&&obstacle.get(i).get(4)==3) {
            target.setIsWindow(true);
            //System.out.println("is a window");
            return false;
        }
    }
        return false;
  }



//     public void addPoint2Map(int x, int y){
//         int hash = ((x+y)*(x+y+1)/2)+y;
//         if(myMap.get(hash)==null) myMap.set(hash, new Point(x,y));
//         else System.out.println("the point:" + myMap.get(hash)+ " already exists in the map");
//     }

//     public Point getPoint(int x, int y){
//         int hash = ((x+y)*(x+y+1)/2)+y;
//         return myMap.get(hash); 
//     }
// }

    
    public void addPoint2Map(int x, int y){
        int hash = ((x+y)*(x+y+1)/2)+y;
     //   if( x<0+player.getScale()+1 || x>=player.getWidth()-player.getScale()-1 || y<0+player.getScale()+1 || y>=player.getHeighth()-player.getScale()-1) return;
        if(myMap.get(hash)==null) myMap.set(hash, new Point(x,y));
        else System.out.println("the point:" + myMap.get(hash)+ " already exists in the map");
    }

    public Point getPoint(int x, int y){
        // if( x<0+player.getScale()+1 || x>=player.getWidth()-player.getScale()-1 || y<0+player.getScale()+1 || y>=player.getHeighth()-player.getScale()-1){
        //     System.out.println("point : " +x+ ","+y +" out of bounds"); 
        //     return null;
        // }
        int hash = ((x+y)*(x+y+1)/2)+y;
        return myMap.get(hash); 
    }
}


// package sourcecode;
// import java.util.ArrayList;

// import java.awt.Rectangle;
// import java.util.List;


// public class POV {

//     private final int radius;
//     private ArrayList<Point> myMap;
//     private ArrayList<Point> currently_watched;
//     private ArrayList<Point> next_currently_watched;
//     private ArrayList<List<Integer>> obstacle = new ArrayList<>();
//     private Point location; 
//     private String facing="D";
//     private Player player ;
//     private boolean firstViewSet;

//     public POV(ArrayList<Point> myMap, int radius,Player player){
//         this.myMap = myMap;
//         currently_watched = new ArrayList<>();
//         next_currently_watched = new ArrayList<>();
//         this.radius = radius;
//         this.player=player ;
//         location = player.getLocation() ;
//         setFirstView();
//     }
//     public ArrayList<Point> getNextCurrentlyWatched(){ return next_currently_watched;}

//     public ArrayList<Point> getCurrentlyWatched(){ return currently_watched;}
//     public void setObstacle(ArrayList<List<Integer>> r){ obstacle = r;}


//     public void seeNextView( String nextfacing) {

//         int distanceX = 0;
//         int distanceY = 0;
//         int yNew = 0;
//         int xNew = 0;
//        // int x = location.getX() ;
//         int x = player.getLocation().getX() ;
//         int y = player.getLocation().getY() ;




//         if (facing == "D") {

//             if (nextfacing == "D"){
//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     Point p = getPoint(currently_watched.get(i).getX(), currently_watched.get(i).getY());
//                     if(p!=null) next_currently_watched.add(p);
//                 }
//             }
//             if (nextfacing == "U") {
//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     distanceY = currently_watched.get(i).getY() - y;
//                     yNew = y - distanceY;

//                     Point p = getPoint(currently_watched.get(i).getX(), yNew);
//                     if (p == null) {
//                         addPoint2Map(currently_watched.get(i).getX(), yNew);
//                         p = getPoint(currently_watched.get(i).getX(), yNew) ;
//                     }
//                     //if the point is still null even after
//                     // adding it to the map, its bcs x or y where out of bounds
                
//                     if(p!=null) next_currently_watched.add(p); }

//             }


//             if (nextfacing == "R") {
//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     distanceY = currently_watched.get(i).getY() - y;
//                     distanceX = currently_watched.get(i).getX() - x;

//                     xNew = x + distanceY;
//                     yNew = y - distanceX;

//                     Point p = getPoint(xNew, yNew);
//                     if (p == null) {
//                         addPoint2Map(xNew, yNew);
//                         p = getPoint(xNew,yNew) ;
//                     }
//                     if(p!=null) next_currently_watched.add(p);
//                 }
//             }

//             if (nextfacing == "L") {

//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     distanceY = currently_watched.get(i).getY() - y;
//                     distanceX = currently_watched.get(i).getX() - x;

//                     xNew = x - distanceY;
//                     yNew = y + distanceX;

//                     Point p = getPoint(xNew, yNew);
//                     if (p == null) {
//                         addPoint2Map(xNew, yNew);
//                         p = getPoint(xNew,yNew) ;
//                     }
//                     if(p!=null) next_currently_watched.add(p);
//                 }
//             }
//         }

//         else if (facing == "U") {

//             if (nextfacing == "U"){
//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     Point p = getPoint(currently_watched.get(i).getX(), currently_watched.get(i).getY());
//                     if(p!=null) next_currently_watched.add(p);
//                 }
//             }

//             if (nextfacing == "D") {

//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     distanceY = currently_watched.get(i).getY() - y;
//                     distanceX = currently_watched.get(i).getX() - x;

//                     yNew = y + ((-1) * distanceY);

//                     Point p = getPoint(currently_watched.get(i).getX(), yNew);
//                     if (p == null) {
//                         addPoint2Map(currently_watched.get(i).getX(), yNew);
//                         p = getPoint(currently_watched.get(i).getX(),yNew) ;
//                     }
//                     if(p!=null) next_currently_watched.add(p);

//                 }
//             }
//             if (nextfacing == "R") {

//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     distanceY = currently_watched.get(i).getY() - y;
//                     distanceX = currently_watched.get(i).getX() - x;

//                     xNew = x - distanceY;
//                     yNew = y + distanceX;

//                     Point p = getPoint(xNew, yNew);
//                     if (p == null) {
//                         addPoint2Map(xNew, yNew);
//                         p = getPoint(xNew,yNew) ;
//                     }
//                     if(p!=null) next_currently_watched.add(p);

//                 }
//             }

//             if(nextfacing =="L") {
//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     distanceY = currently_watched.get(i).getY() - y;
//                     distanceX = currently_watched.get(i).getX() - x;

//                     xNew = x + distanceY;
//                     yNew = y - distanceX;

//                     Point p = getPoint(xNew, yNew);
//                     if (p == null) {
//                         addPoint2Map(xNew, yNew);
//                         p = getPoint(xNew,yNew) ;
//                     }
//                     if(p!=null)  next_currently_watched.add(p);
//                 }
//             }
//         }

//         else if(facing=="R"){

//             if (nextfacing == "R"){
//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     Point p = getPoint(currently_watched.get(i).getX(), currently_watched.get(i).getY());
//                     if(p!=null) next_currently_watched.add(p);
//                 }
//             }

//             if(nextfacing=="L"){

//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     distanceX = currently_watched.get(i).getX() - x;

//                     xNew = x - distanceX;

//                     Point p = getPoint(xNew, currently_watched.get(i).getY());
//                     if (p == null) {
//                         addPoint2Map(xNew, currently_watched.get(i).getY());
//                         p = getPoint(xNew,currently_watched.get(i).getY()) ;
//                     }
//                     if(p!=null) next_currently_watched.add(p);
//                 }

//             }

//             if(nextfacing=="U"){

//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     distanceY = currently_watched.get(i).getY() - y;
//                     distanceX = currently_watched.get(i).getX() - x;

//                     xNew = x + distanceY;
//                     yNew = y - distanceX;

//                     Point p = getPoint(xNew, yNew);
//                     if (p == null) {
//                         addPoint2Map(xNew, yNew);
//                         p = getPoint(xNew, yNew) ;
//                     }
//                     if(p!=null) next_currently_watched.add(p);
//                 }

//             }

//             if(nextfacing=="D"){
//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     distanceY = currently_watched.get(i).getY() - y;
//                     distanceX = currently_watched.get(i).getX() - x;

//                     xNew = x - distanceY;
//                     yNew = y + distanceX;

//                     Point p = getPoint(xNew, yNew);
//                     if (p == null) {
//                         addPoint2Map(xNew, yNew);
//                         p = getPoint(xNew,yNew) ;
//                     }
//                     if(p!=null) next_currently_watched.add(p);
//                 }
//             }
//         }

//         else if(facing=="L"){

//             if (nextfacing == "L"){
//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     Point p = getPoint(currently_watched.get(i).getX(), currently_watched.get(i).getY());
//                     if(p!=null) next_currently_watched.add(p);
//                 }
//             }

//             if(nextfacing=="R"){
//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     distanceX = currently_watched.get(i).getX() - x;
//                     xNew = x - distanceX;
//                     Point p = getPoint(xNew, currently_watched.get(i).getY());
//                     if (p == null) {
//                         addPoint2Map(xNew, currently_watched.get(i).getY());
//                         p = getPoint(xNew,currently_watched.get(i).getY()) ;
//                     }
//                     if(p!=null) next_currently_watched.add(p);
//                 }

//             }
//             if(nextfacing=="U"){
//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     distanceY = currently_watched.get(i).getY() - y;
//                     distanceX = currently_watched.get(i).getX() - x;

//                     xNew = x - distanceY;
//                     yNew = y + distanceX;

//                     Point p = getPoint(xNew, yNew);
//                     if (p == null) {
//                         addPoint2Map(xNew, yNew);
//                         p = getPoint(xNew,yNew) ;
//                     }
//                     if(p!=null) next_currently_watched.add(p);
//                 }
//             }

//             if(nextfacing=="D"){
//                 for (int i = 0; i < currently_watched.size(); i++) {
//                     if(currently_watched.get(i)==null) continue;
//                     distanceY = currently_watched.get(i).getY() - y;
//                     distanceX = currently_watched.get(i).getX() - x;

//                     xNew = x + distanceY;
//                     yNew = y - distanceX;

//                     Point p = getPoint(xNew, yNew);
//                     if (p == null) {
//                         addPoint2Map(xNew, yNew);
//                         p = getPoint(xNew,yNew) ;
//                     }
//                     if(p!=null) 
//                     next_currently_watched.add(p);
//                 }
//             }
//         }

//         // parseArray(next_currently_watched,currently_watched);
//         currently_watched.clear();
//         for (int i = 0; i < next_currently_watched.size(); i++) {
//             if(currently_watched.size()==0 || 
//             currently_watched.get(i)==null) continue;
//             currently_watched.add(next_currently_watched.get(i));
//             // System.out.print(next_currently_watched.get(i));
//         }
//         //  System.out.println(" ");
//         // if( currently_watched.isEmpty())
//         // System.out.print("bingo");
//         next_currently_watched.clear();
//         facing = nextfacing;
//         player.getTMap().repaint();
//     }

//     public void setFirstView(){
//         if(facing.equals("R")){
//             ArrayList<Point> straightP = getStraightPath(location, "R",radius);
//             int i=0;
//             for(Point p: straightP){
//                 ArrayList<Point> UR_DiagPath = getDiagonalPath(p, "UR",radius-i);
//                 ArrayList<Point> DR_DiagPath = getDiagonalPath(p, "DR",radius-i);
//                 seeDiagonal(UR_DiagPath);
//                 seeDiagonal(DR_DiagPath);
//                 i++;
//             }
//         }
//         else if(facing.equals("L")){
//             ArrayList<Point> straightP = getStraightPath(location, "L",radius);
//             int i=0;
//             for(Point p: straightP){
//                 ArrayList<Point> UL_DiagPath = getDiagonalPath(p, "UL",radius-i);
//                 ArrayList<Point> DL_DiagPath = getDiagonalPath(p, "DL",radius-i);
//                 seeDiagonal(UL_DiagPath);
//                 seeDiagonal(DL_DiagPath);
//                 i++;
//             }
//         }
//         else if(facing.equals("U")){
//             ArrayList<Point> straightP = getStraightPath(location, "U",radius);
//             int i=0;
//             for(Point p: straightP){
//                 ArrayList<Point> UL_DiagPath = getDiagonalPath(p, "UL",radius-i);
//                 ArrayList<Point> UR_DiagPath = getDiagonalPath(p, "UR",radius-i);
//                 seeDiagonal(UL_DiagPath);
//                 seeDiagonal(UR_DiagPath);
//                 i++;
//             }
//         }
//         else if(facing.equals("D")){
//             ArrayList<Point> straightP = getStraightPath(location, "D",radius);
//             int i=0;
//             for(Point p: straightP){
//                 if(p==null){
//                     int yf=9;
//                 }
//                 ArrayList<Point> DL_DiagPath = getDiagonalPath(p, "DL",radius-i);
//                 ArrayList<Point> DR_DiagPath = getDiagonalPath(p, "DR",radius-i);
//                 seeDiagonal(DL_DiagPath);
//                 seeDiagonal(DR_DiagPath);
//                 i++;
//             }
//         }
//     }
    
//     public ArrayList<Point> getStraightPath(Point current, String direction,int amount){
//         ArrayList<Point> straightPath = new ArrayList<>();
//         int i=0;
//         straightPath.add(current);
//         while(i<amount){
//             if(current==null) break; 
//             int x = current.getX();
//             int y = current.getY();
//             if(x<0 || y<0){
//                 int yyyy=9;
//             }
//             Point next = null;
//             if(direction.equals("R")){
//                 next = getPoint(x+1, y);
//                 if(next == null){
//                     addPoint2Map(x+1, y);
//                     next = getPoint(x+1, y);
//                 }
//                 if(next == null || collision(next) || next.getIsWall()|| next.getIsDoor()){ break;}
//             }
//             else if(direction.equals("L")){
//                 next = getPoint(x-1, y);
//                 if(next == null){
//                     addPoint2Map(x-1, y);
//                     next = getPoint(x-1, y);
//                 }
//                 if(next == null || collision(next) || next.getIsWall()|| next.getIsDoor()){ break;}
//             }
//             else if(direction.equals("U")){
//                 next = getPoint(x, y-1);
//                 if(next == null){
//                     addPoint2Map(x, y-1);
//                     next = getPoint(x, y-1);
//                 }
//                 if(next == null || collision(next) || next.getIsWall()|| next.getIsDoor()){ break;}
//             }
//             else if(direction.equals("D")){
//                 next = getPoint(x, y+1);
//                 if(next == null){
//                     addPoint2Map(x, y+1);
//                     next = getPoint(x, y+1);
//                 }
//                 if(next == null || collision(next) || next.getIsWall()|| next.getIsDoor()){ break;}
//             }
//             straightPath.add(next);
//             current = next;
//             i++;
//         }
//         return straightPath;
//     }

    
//     public ArrayList<Point> getDiagonalPath(Point current, String direction,int size){
//         ArrayList<Point> diagonalPath = new ArrayList<>(radius);
//         int i=0;
//         while(i<=size){
//             if(current==null) break;
//             if(!diagonalPath.contains(current)) diagonalPath.add(current);
//             int x = current.getX();
//             int y = current.getY();

//             if(current == null || collision(current) || current.getIsWall() || current.getIsDoor()){ 
//                 break;
//             }
//             if(direction.equals("UR")){ //Up right on the diagonal
//                 current = getPoint(x+1, y-1);
//                 if(current == null){
//                     addPoint2Map(x+1, y-1);
//                     current = getPoint(x+1, y-1);
//                 }
//             }
//             else if(direction.equals("DR")){ //Down right on the diagonal
//                 current = getPoint(x+1, y+1);
//                 if(current == null){
//                     addPoint2Map(x+1, y+1);
//                     current = getPoint(x+1, y+1);
//                 } 
//             }
//             else if(direction.equals("UL")){ //Up Left on the diagonal
//                 current = getPoint(x-1, y-1);
//                 if(current == null){
//                     addPoint2Map(x-1, y-1);
//                     current = getPoint(x-1, y-1);

//                 }
//             }
//             else if(direction.equals("DL")){ //Down Left on the diagonal
//                 current = getPoint(x-1, y+1);
//                 if(current == null){
//                     addPoint2Map(x-1, y+1);
//                     current = getPoint(x-1, y+1);
//                 }
//             }
//             i++;
//         }
//         return diagonalPath;
//     }

//     public void seeDiagonal(ArrayList<Point> diagPath){
//         for(Point p: diagPath){
//             if(p == null || collision(p) || p.getIsDoor()||p.getIsWall())    break;

//             //  if(!currently_watched.contains(p))    
//             if(p!=null) 
//             currently_watched.add(p);
//             //if(!seenByAll.contains(p))
//         }
//     }

//    public boolean collision(Point target) {
    
//     Rectangle rectangle1 = new Rectangle(location.getX()-radius/2, location.getY()-radius/2,radius,radius);
//     rectangle1.setLocation(target.getX()-radius/2,target.getY()-radius/2);

//     for (int i = 0;i<obstacle.size();i++){

//         Rectangle obsRect= new Rectangle(obstacle.get(i).get(0),obstacle.get(i).get(1),obstacle.get(i).get(2),obstacle.get(i).get(3));
//         if (obsRect.contains(target.getX(),target.getY())&&obstacle.get(i).get(4)==1) {
//             target.setIsWall(true);
//             //System.out.println("is a wall");
//             return true;
//         }
//         if (obsRect.contains(target.getX(),target.getY())&&obstacle.get(i).get(4)==2) {
//             target.setIsDoor(true);
//             //System.out.println("is a door");
//             return true;
//         }
//         if (obsRect.contains(target.getX(),target.getY())&&obstacle.get(i).get(4)==3) {
//             target.setIsWindow(true);
//             //System.out.println("is a window");
//             return false;
//         }
//     }
//         return false;
//   }



//     public void addPoint2Map(int x, int y){
//         int hash = ((x+y)*(x+y+1)/2)+y;
//         if( x<0 || x>=player.getWidth() || y<0 || y>=player.getHeighth()) return;
//         if(myMap.get(hash)==null) myMap.set(hash, new Point(x,y));
//         else System.out.println("the point:" + myMap.get(hash)+ " already exists in the map");
//     }

//     public Point getPoint(int x, int y){
//         if( x<0 || x>=player.getWidth() || y<0 || y>=player.getHeighth()){
//             System.out.println("point : " +x+ ","+y +" out of bounds"); 
//             return null;
//         }
//         int hash = ((x+y)*(x+y+1)/2)+y;
//         return myMap.get(hash); 
//     }

// }
