import java.util.ArrayList;

import javax.swing.SortingFocusTraversalPolicy;

// import nl.maastrichtuniversity.dke.gamecontrollersample.Area;
// import nl.maastrichtuniversity.dke.gamecontrollersample.TelePortal;

public class Grid {

    int width;
    int height;
    ArrayList<ArrayList<Block>> gridMap;

    public static void main(String[] args) {
       Grid g= new Grid(8,8);
       g.gridMap.get(3).get(4).setIsWall(true);
       g.gridMap.get(4).get(4).setIsWall(true);
       g.gridMap.get(5).get(4).setIsWall(true);
       g.gridMap.get(6).get(4).setIsWall(true);
       g.gridMap.get(3).get(6).makePortal(g.gridMap.get(7).get(7));
       g.gridMap.get(2).get(4).setPlayerOnBlock(new Player(g.gridMap.get(2).get(4)));
       
       
       g.printGrid();
       Player p1 = g.gridMap.get(2).get(4).getPlayerOnBlock();
       ArrayList<Block> path= new ArrayList<>();
       path.add(g.gridMap.get(1).get(4));
       path.add(g.gridMap.get(1).get(5));
       path.add(g.gridMap.get(1).get(6));
       path.add(g.gridMap.get(1).get(7));
       p1.walk(path, g.gridMap);
    //    p1.move(g.gridMap.get(5).get(4));
    //    System.out.println();
    //    g.printGrid();
    //    System.out.println();
    //    p1.move(g.gridMap.get(3).get(6));
       g.printGrid();
    }

    public Grid(int width, int height){
        this.width = width;
        this.height = height;
        gridMap= new ArrayList<>();
        for(int i=0; i<height; i++){
            ArrayList<Block> row = new ArrayList<>();
            for(int j=0; j<width;j++){
                row.add(new Block(i, j));
            }
            gridMap.add(row);
        }
    }



    public void printGrid(){
        for(ArrayList<Block> row : gridMap){
            System.out.println();
            for(Block b: row){
                if(b.getIsOccpied() && !b.getIsPortal()){ //if guard
                    System.out.print(" g ");
                }
                else if(b.getBlockType().equals("wall")){
                    System.out.print(" w ");
                }
                else if(b.getIsPortal()){
                    System.out.print(" p ");
                }
                else{
                    System.out.print(" * ");
                }
            }
        }
    }


    public void addWalls(ArrayList<Area> walls){
        for(Area wall : walls){
            for(int i = wall.getBottomBoundary(); i<wall.getTopBoundry(); i++){
                for(int j = wall.getLeftBoundry(); j<wall.getRightBoundry(); j++){
                    gridMap.get(i).get(j).setIsWall(true);
                }
            }
        }
    }

    public void addPortals(ArrayList<TelePortal> tlpt){
        for(TelePortal t : tlpt){
            for(int i = t.getBottomBoundary(); i<t.getTopBoundry(); i++){
                for(int j = t.getLeftBoundry(); j<t.getRightBoundry(); j++){
                    gridMap.get(i).get(j).setIsPortal(true);
                }
            }
        }
    }

    public double EuclideanDist(Block A, Block B){
        double xDist = Math.pow(A.getX() - B.getX(),2);
        double yDist = Math.pow(A.getY() - B.getY(),2);
        double d = Math.sqrt(xDist+yDist);
        return d;
    }
}

