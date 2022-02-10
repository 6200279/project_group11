import java.util.ArrayList;


// import nl.maastrichtuniversity.dke.gamecontrollersample.Area;
// import nl.maastrichtuniversity.dke.gamecontrollersample.TelePortal;

public class Grid {

    int width;
    int height;
    ArrayList<ArrayList<Block>> gridMap;

    public static void main(String[] args) {
       Grid g= new Grid(8,8);
       
       g.buildWall(3,4,7,4);
       g.buildWall(0,1,6,1);
       g.buildPortal(4,2,2,7);
       
       Player p1 = new Player(g.gridMap.get(0).get(0));
       
       g.gridMap.get(0).get(0).setPlayerOnBlock(p1);
       
       g.printGrid();

       Block start = p1.location;
       Block end = g.gridMap.get(7).get(7);
       
       ArrayList<Block> path2= new Path_Calculator(g.gridMap).BFS_Path(start,end);
       
       p1.walk(path2, g.gridMap);
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
        System.out.println();
        for(ArrayList<Block> row : gridMap){
            System.out.println();
            for(Block b: row){
                if(b.getIsOccpied() && !b.getIsPortal()){ //if guard
                    System.out.print(" g ");
                }
                else if(b.getIsWall()){
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

    public void buildWall(int x1,int y1,int x2,int y2){
        for(int i=x1; i<=x2; i++){
            for(int j=y1; j<=y2; j++){
                gridMap.get(i).get(j).setIsWall(true);
            }
        }
    }

    public void buildPortal(int x1,int y1, int x2, int y2){
        Block portal = gridMap.get(x1).get(y1);
        Block target = gridMap.get(x2).get(y2);
        portal.setIsPortal(true);
        portal.setTlpTarget(target);
    }

}

