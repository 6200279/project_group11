import java.util.ArrayList;


public class Block {
    private int x,y=-1;
    private boolean isOccupied=false;
    private boolean isWall= false;
    private boolean isPortal= false;
    private Player playerOn;
    private Block tlpTarget;
    private TelePortal tlp;

    public Block(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){ return x; } 
    public int getY(){ return y; }
    public void setX(int x){ this.x= x; }
    public void setY(int y){ this.y= y; }

    public Block getTlpTarget(){ return tlpTarget;}
    public void setTlpTarget(Block target){ this.tlpTarget = target; }

    public TelePortal getTlp(){ return tlp;}
    public void setTlp(TelePortal tlp){ this.tlp = tlp; }

    
    public boolean getIsOccpied(){ return isOccupied;}
    public boolean getIsWall(){ return isWall; } 
    public boolean getIsPortal(){ return isPortal; }
    public void setIsOccupied(boolean isOccupied){ this.isOccupied = isOccupied; }
    public void setIsWall(boolean isWall){ this.isWall= isWall; }
    public void setIsPortal(boolean isPortal){ this.isPortal= isPortal;}

    public Player getPlayerOnBlock(){ return playerOn;}
   
    public void setPlayerOnBlock(Player playerOn){ 
        this.playerOn = playerOn;
        
        if(playerOn!=null){ 
            isOccupied=true;
            playerOn.location = this;
            playerOn.x = x;
            playerOn.y = y;
        }
        else { 
            isOccupied=false; 
            
        }
    }

    public void makePortal(Block tlpTarget){
        this.tlpTarget = tlpTarget;
        this.isPortal = true;
    }

    public Block getTlpDest(ArrayList<ArrayList<Block>> gridMap){
        int x = tlp.getNewLocation()[0];
        int y = tlp.getNewLocation()[1];
        return gridMap.get(x).get(y);
    }

    public String getBlockType(){
        if(isWall) return "wall";
        else if(isPortal) return "portal";
        else return "floor";
    }

    public ArrayList<Block> getMyNeighbours(ArrayList<ArrayList<Block>> gridMap){
        ArrayList<Block> neighbours = new ArrayList<>();
        if(isPortal){
            neighbours.add(tlpTarget);
            return neighbours;
        }
        for(int i=x-1; i <=x+1; i++){
            for(int j=y-1; j<=y+1; j++){
                 if(i<0 || j<0 || i>=gridMap.size() ||j>=gridMap.get(0).size()|| (i==x&&j==y)){
                     continue;
                 }
                 Block neighbour = gridMap.get(i).get(j);
                 if(neighbour.getIsWall()){
                     continue;
                 }
                else{
                    neighbours.add(neighbour);
                }
            }
        }
        return neighbours;
    }

}
