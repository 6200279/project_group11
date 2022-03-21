package sourcecode;

import java.util.ArrayList;

public class MDFS_Algorithm implements Algorithm{

    Player player;


    public void execute(Player p){
        player = p;
        Point location = player.getLocation(); 
        //player.unSee();
        if(!location.getExploredMDFS()){  //if this cell is unexplored 
            location.setExploredMdfs(true);
            location.setExplorerID(player.getId());
            location.setParentMDFS(player.getLastLocation());
        }
        ArrayList<Point> unexplored = UnExploredAround(location);
        if(unexplored.size()>0) { //check for unexplored points around current pos
            Point chosenTarget = unexplored.get(rndIndex(unexplored));
            player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
            player.moveToPoint(chosenTarget);
        }
        else if(unexplored.size()==0){ // else if all neighbour cells around me are explored
            //System.out.println("agent "+player.getId()+ " has Nothing to explore");
            if(location.getExplorerID().equals(player.getId())){
                location.setVisitedMdfs(true);
                player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
                if(location.getParentMDFS().getX()!= location.getX() || location.getParentMDFS().getX()!= location.getX()) {
                    player.moveToPoint(location.getParentMDFS());
                }
                else{
                    player.moveToPoint(player.getNeighbours(location).get(rndIndex(player.getNeighbours(location))));
                }
            }
            else{ // else if this spot is marked as explored by another ID 
                ArrayList<Point> neighbours = player.getNeighbours(location);
                Point chosenTarget = neighbours.get(rndIndex(neighbours));
                //chosenTarget.setParentMDFS(location);
                player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
                player.moveToPoint(chosenTarget);

            }
        }
    }


    public ArrayList<Point> UnExploredAround(Point myLocation){
        ArrayList<Point> unExploredNeighbours = new ArrayList<>(); 
        ArrayList<Point> AdjacentPoints = player.getNeighbours(myLocation);
        for(Point neighbour: AdjacentPoints){
            if(!neighbour.getExploredMDFS() && !neighbour.getIsWall() && !player.collision(neighbour)){
                unExploredNeighbours.add(neighbour);
            }
        }
        return unExploredNeighbours;
    }

    
   public Integer rndIndex(ArrayList<Point> array){
    return  ((int)(Math.random() * array.size()));
}

    
}
