package sourcecode;

import java.util.ArrayList;

public class B_Algorithm {

    Player player;
    POV pov;
    Point location;
    ArrayList<Point> left_seen;
    ArrayList<Point> right_seen;
    ArrayList<Point> down_seen;
    ArrayList<Point> up_seen;
    
    public B_Algorithm(Player p){
        player = p;
        location = player.getLocation();
        pov = player.getPOV();
        execute();
    }

    public void execute(){
        location = player.getLocation();
        player.unSee();
        look360();
        String least = getLeastExploredDirection();
        Point target = getDesiredLocation(least);
        
        player.moveInPath(player.BFS(location,target.getX(),target.getY()));
    }


    public void look360(){
        pov.see("R",location);
        right_seen = pov.getCurrentlyWatched();
        player.unSee();
        pov.see("L",location);
        left_seen = pov.getCurrentlyWatched();
        player.unSee();
        pov.see("U",location);
        up_seen = pov.getCurrentlyWatched();
        player.unSee();
        pov.see("D",location);
        down_seen = pov.getCurrentlyWatched();
        player.unSee();      
    }

    public String getLeastExploredDirection(){
        int ex_right = howManyExplored(right_seen);
        int ex_left = howManyExplored(left_seen);
        int ex_up = howManyExplored(up_seen);
        int ex_down = howManyExplored(down_seen);
        
        if(ex_right == ex_left && ex_right == ex_up && ex_right == ex_down) return getRndDirection();
        if(ex_right <= ex_left && ex_right <= ex_up && ex_right <= ex_down) return "R";
        if(ex_left <= ex_right && ex_left <= ex_up && ex_left <= ex_down) return "L";
        if(ex_up <= ex_left && ex_up <= ex_right && ex_up <= ex_down) return "U";
        if(ex_down <= ex_left && ex_down <= ex_right && ex_down <= ex_up) return "D";

        return "K";
    }

    public int howManyExplored(ArrayList<Point> View_field){
        int counter = 0;
        for(Point p: View_field){
            if(p.getExploredMDFS()) counter++;
            if(p.getIsWall()) return 1000;
        }    
        return counter;
    }

    public Point getDesiredLocation(String direction){
        int radius = player.getRadius();
        if(direction.equals("R")){
            int new_X = location.getX()+2*radius;
            int y = location.getY();
            if(player.getPoint(new_X, y)!=null) return player.getPoint(new_X,y);
        }
        else if(direction.equals("L")){
            int new_X = location.getX()-2*radius;
            int y = location.getY();
            if(player.getPoint(new_X, y)!=null) return player.getPoint(new_X,y);
        }
        else if(direction.equals("U")){
            int x = location.getX();
            int new_Y = location.getY()-2*radius;
            if(player.getPoint(x, new_Y)!=null) return player.getPoint(x, new_Y);
        }
        else if(direction.equals("D")){
            int x = location.getX();
            int new_Y = location.getY()+2*radius;
            if(player.getPoint(x, new_Y)!=null) return player.getPoint(x, new_Y);
        }
        return new Point(-18989,-190389);
    }

    public String getRndDirection(){
        int randomFace = (int) (Math.random() * 4 + 1)+1;
        if (randomFace == 5) return "D";
        else if (randomFace == 2) return "U";
        else if (randomFace == 3) return "L";
        else  return "R";
    }

    
}
