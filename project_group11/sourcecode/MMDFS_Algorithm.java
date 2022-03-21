package sourcecode;

import java.util.ArrayList;

public class MMDFS_Algorithm {

    Player player;

    public MMDFS_Algorithm(Player p){
        player = p;
        execute();
    }

    public void execute(){
        Point location = player.getLocation();

        if(!location.getExploredMMDFS()) {  //if the current cell is unexplored
            location.setExploredMMdfs(true);     //set to explored
            location.setParentMMDFS(player.getLastLocation());
        }
        
        ArrayList<Point> unexplored = UnExploredAround(location);
        if(unexplored.size()>0) {           //if there are unexplored cells around
            Point chosenTarget = unexplored.get(rndIndex(unexplored));  //move to one of them randomly
            player.moveToPoint(chosenTarget);

            if(!location.getVisitedMMDFS()) {      //if the current cell is not visited
                location.setExploredMMdfs(true);  //set the current cell to explored
            }
        }
        ArrayList<Point> checked = CheckedAround(location);
        if(checked.size()>0) {             //if there are checked cells around
            Point chosenTarget = checked.get(rndIndex(checked));    //move to one of the checked cells randomly
            player.moveToPoint(chosenTarget);

            if(!location.getVisitedMMDFS()) {           //if the current cell is not visited
                location.setExploredMMdfs(true);        //set the current cell to explored
            }
        }
        else{
            player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
            player.moveToPoint(location.getParentMMDFS());   //move to the parent cell
            location.setVisitedMMdfs(true);
            //location.setExplorerID(player.getId()); //set agent ID on it
        }

    }

    public ArrayList<Point> CheckedAround(Point myLocation){
        ArrayList<Point> CheckedNeighbours = new ArrayList<>();
        ArrayList<Point> AdjacentPoints = player.getNeighbours(myLocation);
        for(Point neighbour: AdjacentPoints){
            if(!neighbour.getExploredMMDFS() && !neighbour.getIsWall() && !player.collision(neighbour)){
                CheckedNeighbours.add(neighbour);
            }
        }
        return CheckedNeighbours;
    }

    public ArrayList<Point> UnExploredAround(Point myLocation){
        ArrayList<Point> unExploredNeighbours = new ArrayList<>();
        ArrayList<Point> AdjacentPoints = player.getNeighbours(myLocation);
        for(Point neighbour: AdjacentPoints){
            if(!neighbour.getExploredMMDFS()){
                unExploredNeighbours.add(neighbour);
            }
        }
        return unExploredNeighbours;
    }

    public Integer rndIndex(ArrayList<Point> array){
        return  ((int)(Math.random() * array.size()));
    }

}
