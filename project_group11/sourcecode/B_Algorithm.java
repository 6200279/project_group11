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
        player.setLocation(target);

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
        //player.unSee();      
    }

    public String getLeastExploredDirection(){
        int ex_right = howManyExplored(right_seen)/right_seen.size();
        int ex_left = howManyExplored(left_seen)/left_seen.size();
        int ex_up = howManyExplored(up_seen)/up_seen.size();
        int ex_down = howManyExplored(down_seen)/down_seen.size();
        
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
        }    
        return counter;
    }

    public Point getDesiredLocation(String direction){
        int radius = player.getRadius();
        if(direction.equals("R")){
            int new_X = location.getX()+2*radius;
            int y = location.getY();
            if(player.getSharedData(new_X, y)!=null) return player.getSharedData(new_X,y);
        }
        else if(direction.equals("L")){
            int new_X = location.getX()-2*radius;
            int y = location.getY();
            if(player.getSharedData(new_X, y)!=null) return player.getSharedData(new_X,y);
        }
        else if(direction.equals("U")){
            int x = location.getX();
            int new_Y = location.getY()-2*radius;
            if(player.getSharedData(x, new_Y)!=null) return player.getSharedData(x, new_Y);
        }
        else if(direction.equals("D")){
            int x = location.getX();
            int new_Y = location.getY()+2*radius;
            if(player.getSharedData(x, new_Y)!=null) return player.getSharedData(x, new_Y);
        }
        return new Point(-18989,-190389);
    }
 }
