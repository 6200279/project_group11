package project_group11;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
public class Player {
 
    int x;
    int y;
    double angle=0;
    double time=0;
    Block location;
    int state=0; //0- standing, 1-walking , 2- sprinting, 3- dazed/climbing , 4- illegl move  
     
    public Player(int x, int y, double angle, double time){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.time = time;
    }

    public Player(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Player(Block location){
        this.location= location;
        x = location.getX();
        y = location.getY(); 
    }

    public void move(Block target) {//throws InterruptedException{

        if(target.getIsWall()){
            System.err.println("Cannot move, there is a wall there!");
            state = 4 ;
            return;
        }
        state = 1;
        location.setIsOccupied(false);
        location.setPlayerOnBlock(null);
        

        if(target.getIsPortal()){
            target.getTlpTarget().setPlayerOnBlock(this);
            //int[] newLoc = target.getTlp().getNewLocation();
        }
        else{
            target.setPlayerOnBlock(this);
            x= target.getX();
            y = target.getY();
        } 
    }

    public void walk(ArrayList<Block> path, ArrayList<ArrayList<Block>> gridMap) {
        state = 1;
        for(Block b: path){
            move(b);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    



}
//     public void setup(){
//         // we assume that the game mode is 0. That is all for which there is a
//         // minimum implementation
//         guardPositions = scenario.spawnGuards();
//         guardStates = new int[scenario.getNumGuards()]; // should be initialized to 0 by default
// }
