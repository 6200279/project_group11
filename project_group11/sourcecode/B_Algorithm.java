package sourcecode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class B_Algorithm implements Algorithm {

    Player player;
    POV pov;
    Point location;
    ArrayList<Point> left_seen;
    ArrayList<Point> right_seen;
    ArrayList<Point> down_seen;
    ArrayList<Point> up_seen;
    
    public void execute(Player p){
        player = p;
        location = player.getLocation();
        pov = player.getPOV();
        location = player.getLocation();
        look360();
        ArrayList<String> usedDirections = new ArrayList<>();
        String least = getLeastExploredDirection();
        String [] Directions = {"U","D","R","L"}; 
        Point target;
        while(true){
            target = getDesiredLocation(least);
            if((target.getX()== -1234 || target.getY()== -1234 ) || target.getIsWall() || player.collision(target)){
                usedDirections.add(least);
                if(usedDirections.size()==4) {   //if all targets are unreacable break from while loop
                    player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
                    rndMove();
                    System.err.println("I moved randomly because all options to move to were shit");
                    return;
                }    
                else{  //else if we havent used all directions yet
                    for(String d : Directions ){
                        if(usedDirections.contains(d)) continue; //if this direction is one we know is bad
                        else least = d; //if we dont know about if its bad yet, check it now
                    }
                }
            }
            else break;
        }
        ArrayList<Point> BFSpath = BFS(location,target.getX(),target.getY());
        if(BFSpath==null){ 
            player.getVisited_4_GUI().add(new Point(location.getX(),location.getY()));
            rndMove();;
            System.err.println("I moved randomly because bfs gave a null path");
            return;
        }
        player.moveInPath(BFSpath);
    }


    public void look360(){
        pov.seeNextView("R");
        right_seen = pov.getCurrentlyWatched();
        pov.seeNextView("L");
        left_seen = pov.getCurrentlyWatched();
       pov.seeNextView("U");
        up_seen = pov.getCurrentlyWatched();
       pov.seeNextView("D");
        down_seen = pov.getCurrentlyWatched();
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
            int new_X = location.getX()+1*radius;
            int y = location.getY();
            if(player.getPoint(new_X, y)!=null) return player.getPoint(new_X,y);
        }
        else if(direction.equals("L")){
            int new_X = location.getX()-1*radius;
            int y = location.getY();
            if(player.getPoint(new_X, y)!=null) return player.getPoint(new_X,y);
        }
        else if(direction.equals("U")){
            int x = location.getX();
            int new_Y = location.getY()-1*radius;
            if(player.getPoint(x, new_Y)!=null) return player.getPoint(x, new_Y);
        }
        else if(direction.equals("D")){
            int x = location.getX();
            int new_Y = location.getY()+1*radius;
            if(player.getPoint(x, new_Y)!=null) return player.getPoint(x, new_Y);
        }
        return new Point(-1234,-1234);
    }

    public String getRndDirection(){
        int randomFace = (int) (Math.random() * 4 + 1)+1;
        if (randomFace == 5) return "D";
        else if (randomFace == 2) return "U";
        else if (randomFace == 3) return "L";
        else  return "R";
    }




    public ArrayList<Point> getBFSNeighbours(Point p){
        ArrayList<Point> BfsNeighbours = new ArrayList<Point>();
        ArrayList<Point> nArray = player.getNeighbours(p);
        for(Point a : nArray) {
             if(!a.getIsBfsVisited()) BfsNeighbours.add(a);
            }
        return BfsNeighbours;
        }
    
        
    
    public ArrayList<Point> BFS(Point s, int xt, int yt){
        ArrayList<Point> shortestPath = new ArrayList<>();
        Stack<Point> stack= new Stack<>();
        stack.push(s);
        s.setBfsVisited(true);
        Point target = player.getPoint(xt,yt);
        while(!stack.isEmpty()) {
            Point v = stack.pop();

            if((v.getX() == xt)&&(v.getY() == yt)){
                target = v;
                break;
            }
            for(Point neighbour : getBFSNeighbours(v)) {
                    stack.push(neighbour);
                    if(neighbour.getX()==xt && neighbour.getY()==yt){
                    }
                    neighbour.setParentBfs(v);
                    neighbour.setBfsVisited(true);
            }
        }
        
        Point n = target;
    
        while(true){
            
            if(n==null ||(n.getX()==s.getX() && n.getY() == s.getY())){
                break;
            }

            shortestPath.add(n);
            
            Point parent = n.getParentBfs(); 
            n = parent;
        }
        shortestPath.add(s);
        Collections.reverse(shortestPath);
        return shortestPath;
    }


    public void rndMove(){
        int randomFace = (int) (Math.random() * 4 + 1)+1;
       if (randomFace == 5) {
           player.setFacing("D");
           player.moveInDirection("D");
       }
       if (randomFace == 2) {
            player.setFacing("U");
            player.moveInDirection("U");
       }
       if (randomFace == 3) {
            player.setFacing("R");
            player.moveInDirection("R");
        }
       if (randomFace == 4) {
            player.setFacing("L");
            player.moveInDirection("L");
       }
    }   
}
