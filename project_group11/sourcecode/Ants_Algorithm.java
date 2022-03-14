package sourcecode;

import java.util.ArrayList;

public class Ants_Algorithm {
    Player player;

    public Ants_Algorithm(Player p){
        player = p;
        execute();
    }
    

public void execute(){
    Point location = player.getLocation();
    player.unSee();
    location.steppedOn++;
    ArrayList<Point> neighbours = player.getNeighbours(location);
    Point target = getLeastChecked(neighbours);
    player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
    player.moveToPoint(target);
}

public Point getLeastChecked(ArrayList<Point> neighbours){
        Point min = neighbours.get(0);
        for(Point n: neighbours){
            if(n.getY() == min.getY() && n.getX() == min.getX()) continue;
            if(n.steppedOn < min.steppedOn)  min = n;
            else if(n.steppedOn == min.steppedOn){ // if there is a tie for least walked direction, randomize the decision
                int r = ((int)(Math.random() * 2));
                if(r==0) min = n;
            }
        }
        return min;
    }
}
