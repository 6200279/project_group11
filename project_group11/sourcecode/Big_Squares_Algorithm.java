package sourcecode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Big_Squares_Algorithm implements Algorithm {


    Player player;
    POV pov;
    int radius; 
    Point location;
    boolean onTheMove=false;
    int counter=0;
    Queue<Point> bestPath= new LinkedList<>();
    Point chosenTarget;
    Point startPoint;


    public void execute(Player p){
            player = p;
            pov = player.getPOV();
            radius = player.getPOVRadius();
            location = player.getLocation();
            Point location = player.getLocation(); 
           // player.spinAround();

        if(onTheMove){
                if(player.getLocation().getX() == chosenTarget.getX() && player.getLocation().getY() == chosenTarget.getY() ) {
                    onTheMove = false;
                    location.setParentMDFS(startPoint);
                    return;
                }
                player.moveToPoint(bestPath.poll());
                counter++;
                return;
            }
        
        else if(!onTheMove){
            if(!location.getExploredMDFS()){  //if this cell is unexplored 
                location.setExploredMdfs(true);
                location.setExplorerID(player.getId());
            }
            ArrayList<Point> unexplored = big_UnExploredAround(location);
            if(unexplored.size()>0) { //check for unexplored points around current pos
                chosenTarget = unexplored.get(rndIndex(unexplored));
                player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
                getLineWalk(location, chosenTarget);
                System.out.println("I want to walk from " +location + " to here: "+ chosenTarget);
                onTheMove = true;
                startPoint = location;
                return; 
            }
            else if(unexplored.size()==0){ // else if all neighbour cells around me are explored
                if(location.getExplorerID().equals(player.getId())){
                    location.setVisitedMdfs(true);
                    player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
                    
                    if(location.getParentMDFS().getX()!= location.getX() || location.getParentMDFS().getX()!= location.getX()) {
                        chosenTarget = location.getParentMDFS();
                        getLineWalk(location, chosenTarget);
                        System.out.println("I want to walk from " +location + " to here: "+ chosenTarget);
                        onTheMove = true;
                        startPoint = location;
                        return;
                    }
                    else{
                            chosenTarget = get_big_square_neighbours(location).get(rndIndex(get_big_square_neighbours(location)));
                        getLineWalk(location, chosenTarget);
                        System.out.println("I want to walk from " +location + " to here: "+ chosenTarget);
                        onTheMove = true;
                        startPoint = location;
                        return;
                    }
                }
                else{ // else if this spot is marked as explored by another ID 
                    ArrayList<Point> neighbours = get_big_square_neighbours(location);
                    Point chosenTarget = neighbours.get(rndIndex(neighbours));
                    //chosenTarget.setParentMDFS(location);
                    player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
                    getLineWalk(location, chosenTarget);
                    System.out.println("I want to walk from " +location + " to here: "+ chosenTarget);
                    onTheMove = true;
                    startPoint = location;
                    return;
                }
            }
        }
    }


    public ArrayList<Point> big_UnExploredAround(Point myLocation){
        ArrayList<Point> unExploredNeighbours = new ArrayList<>(); 
        ArrayList<Point> farNeighbours = get_big_square_neighbours(location);
        for(Point neighbour: farNeighbours){
            if(neighbour!=null &&!neighbour.getExploredMDFS() && !neighbour.getIsWall() && !player.collision(neighbour)){
                unExploredNeighbours.add(neighbour);
            }
        }
        return unExploredNeighbours;
    }


    public ArrayList<Point> get_big_square_neighbours(Point location){
        ArrayList<Point> big_neighbours = new ArrayList<>();
        int x = location.getX();
        int y = location.getY();
        int distance = 2*radius;
        
        Point left,right,up,down;
        if(player.getPoint(x-distance,y)== null) player.addPoint2Map(x-distance, y);
        if(player.getPoint(x+distance,y)== null) player.addPoint2Map(x+distance, y);
        if(player.getPoint(x,y-distance)== null) player.addPoint2Map(x, y-distance);
        if(player.getPoint(x,y+distance)== null) player.addPoint2Map(x, y+distance);
        left = player.getPoint(x-distance, y);
        right = player.getPoint(x+distance, y);
        up = player.getPoint(x, y-distance);
        down = player.getPoint(x, y+distance);
        if(down==null || up==null || right==null || left==null){
            int yyy=7;
        }

        if(left != null && !player.collision(left) && !left.getIsWall() &&  !left.getIsWindow())        big_neighbours.add(left); //Point to the left
        if(right != null && !player.collision(right) && !right.getIsWall()&& !right.getIsWindow())      big_neighbours.add(right);
        if(up != null && !player.collision(up) && !up.getIsWall() && !up.getIsWindow())                 big_neighbours.add(up); //Point above
        if(down != null && !player.collision(down) && !down.getIsWall() && !down.getIsWindow())         big_neighbours.add(down);
        return big_neighbours;
    }

    public void getLineWalk(Point location, Point target){
        
        String facing = getFacingDirection(location,target);
        ArrayList<Point> pathArray = pov.getStraightPath(location, facing, 2*radius);
        for(Point p : pathArray){
            bestPath.add(p);
        }
    }

    public int getDistance(Point a, Point b ){
        int x1 = a.getX();
        int y1 = a.getY();
        int x2 = b.getX();
        int y2 = b.getY();

        return (int) Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
    }

    
    public Integer rndIndex(ArrayList<Point> array){
        return  ((int)(Math.random() * array.size()));
    }

    public String getFacingDirection(Point myPos, Point targetPos){
        
        if(myPos.getX() == targetPos.getX() && myPos.getY() == targetPos.getY()) return null;
        int x1 = myPos.getX();
        int y1 = myPos.getY();
        int x2 = targetPos.getX();
        int y2 = targetPos.getY();

        if(x1==x2){ //moving Vertically (up to down or down to up)
            if(y1 < y2) return "D";
            else return "U";
        }
        else if(y1==y2){
            if(x1 < x2) return "R";
            else return "L";
        }
        return "Nada";
    }
}



// package sourcecode;

// import java.util.ArrayList;

// public class Big_Squares_Algorithm implements Algorithm {


//     Player player;
//     POV pov;
//     int radius; 
//     Point location;

//     public void execute(Player p){
//         player = p;
//         pov = player.getPOV();
//         radius = player.getPOVRadius();
//         location = player.getLocation();
//         Point location = player.getLocation(); 
//         player.spinAround();

//         if(!location.getExploredMDFS()){  //if this cell is unexplored 
//             location.setExploredMdfs(true);
//             location.setExplorerID(player.getId());
//             location.setParentMDFS(player.getLastLocation());
//         }
//         ArrayList<Point> unexplored = big_UnExploredAround(location);
//         if(unexplored.size()>0) { //check for unexplored points around current pos
//             Point chosenTarget = unexplored.get(rndIndex(unexplored));
//             player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
//             player.moveInPath(getStraightPath(location, chosenTarget));
//         }
//         else if(unexplored.size()==0){ // else if all neighbour cells around me are explored
//             //System.out.println("agent "+player.getId()+ " has Nothing to explore");
//             if(location.getExplorerID().equals(player.getId())){
//                 location.setVisitedMdfs(true);
//                 player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
//                 if(location.getParentMDFS().getX()!= location.getX() || location.getParentMDFS().getX()!= location.getX()) {
//                     player.moveInPath(getStraightPath(location, location.getParentMDFS()));
//                 }
//                 else{
//                     player.moveInPath(getStraightPath(location, get_big_square_neighbours(location).get(rndIndex(get_big_square_neighbours(location)))) );
//                     }
//             }
//             else{ // else if this spot is marked as explored by another ID 
//                 ArrayList<Point> neighbours = get_big_square_neighbours(location);
//                 Point chosenTarget = neighbours.get(rndIndex(neighbours));
//                 //chosenTarget.setParentMDFS(location);
//                 player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
//                 player.moveInPath(getStraightPath(location,chosenTarget));

//             }
//         }
//     }


//     public ArrayList<Point> big_UnExploredAround(Point myLocation){
//         ArrayList<Point> unExploredNeighbours = new ArrayList<>(); 
//         ArrayList<Point> farNeighbours = get_big_square_neighbours(location);
//         for(Point neighbour: farNeighbours){
//             if(neighbour!=null &&!neighbour.getExploredMDFS() && !neighbour.getIsWall() && !player.collision(neighbour)){
//                 unExploredNeighbours.add(neighbour);
//             }
//         }
//         return unExploredNeighbours;
//     }


//     public ArrayList<Point> get_big_square_neighbours(Point location){
//         ArrayList<Point> big_neighbours = new ArrayList<>();
//         int x = location.getX();
//         int y = location.getY();
//         int distance = 2*radius;
        
//         Point left,right,up,down;
//         if(player.getPoint(x-distance,y)== null) player.addPoint2Map(x-distance, y);
//         if(player.getPoint(x+distance,y)== null) player.addPoint2Map(x+distance, y);
//         if(player.getPoint(x,y-distance)== null) player.addPoint2Map(x, y-distance);
//         if(player.getPoint(x,y+distance)== null) player.addPoint2Map(x, y+distance);
//         left = player.getPoint(x-distance, y);
//         right = player.getPoint(x+distance, y);
//         up = player.getPoint(x, y-distance);
//         down = player.getPoint(x, y+distance);

//         if(left != null && !player.collision(left) && !left.getIsWall() &&  !left.getIsWindow())        big_neighbours.add(left); //Point to the left
//         if(right != null && !player.collision(right) && !right.getIsWall()&& !right.getIsWindow())      big_neighbours.add(right);
//         if(up != null && !player.collision(up) && !up.getIsWall() && !up.getIsWindow())                 big_neighbours.add(up); //Point above
//         if(down != null && !player.collision(down) && !down.getIsWall() && !down.getIsWindow())         big_neighbours.add(down);
//         return big_neighbours;
//     }

//     public ArrayList<Point> getStraightPath(Point location, Point target){
//         ArrayList<Point> bestPath = new ArrayList<>();
//         String facing = getFacingDirection(location,target);
//         bestPath = pov.getStraightPath(location, facing, 2*radius);
//         return bestPath;
//     }

//     public int getDistance(Point a, Point b ){
//         int x1 = a.getX();
//         int y1 = a.getY();
//         int x2 = b.getX();
//         int y2 = b.getY();

//         return (int) Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
//     }

    
//     public Integer rndIndex(ArrayList<Point> array){
//         return  ((int)(Math.random() * array.size()));
//     }

//     public String getFacingDirection(Point myPos, Point targetPos){
        
//         if(myPos.getX() == targetPos.getX() && myPos.getY() == targetPos.getY()) return null;
//         int x1 = myPos.getX();
//         int y1 = myPos.getY();
//         int x2 = targetPos.getX();
//         int y2 = targetPos.getY();

//         if(x1==x2){ //moving Vertically (up to down or down to up)
//             if(y1 < y2) return "D";
//             else return "U";
//         }
//         else if(y1==y2){
//             if(x1 < x2) return "R";
//             else return "L";
//         }
//         return "Nada";
//     }
// }
