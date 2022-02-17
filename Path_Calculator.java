import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
 
public class Path_Calculator {
    
    ArrayList<ArrayList<Block>> gridMap ;
    boolean [] [] visited; 
    Block [] [] prev; 
    int rows;
    int columns;

    public static void main(String[] args) {
        
    }

    public Path_Calculator(ArrayList<ArrayList<Block>> gridMap){
        this.gridMap = gridMap;
        rows = gridMap.size();
        columns = gridMap.get(0).size();
    } 

    public ArrayList<Block> BFS_Path(Block start, Block end){
        Block[][] prev = solve(start);
        return getPath(start, end, prev);
    }


    public Block [][] solve(Block position){
        Queue<Block> q= new LinkedList<>();
        q.add(position);
        visited = new boolean[rows][columns];
        visited[position.getX()][position.getY()]=true;
        prev = new Block[rows][columns];

        for(int i=0; i<rows;i++){    
            for(int j=0; j<columns; j++){
               prev[i][j]=null; 
            }
        }

        while(!q.isEmpty()){
            Block b = q.poll();
            ArrayList<Block> neighbours = b.getMyNeighbours(gridMap);
            for(Block neigh: neighbours){
                if(!visited[neigh.getX()][neigh.getY()]){
                    q.add(neigh);
                    visited[neigh.getX()][neigh.getY()]=true;  //setting the neighbour to visited
                    prev[neigh.getX()][neigh.getY()]=b;       //setting the parent of the neighbour to the current node
                }
            }
        }

        return prev;
    }


    public ArrayList<Block> getPath(Block start, Block end, Block[][]prev){
        ArrayList<Block> path= new ArrayList<>();
        Block node = end;
        while(node!=null){
            path.add(node);
            node = prev[node.getX()][node.getY()]; 
        }
        Collections.reverse(path); //reversing the order so that the start is first
        //if a path does indeed exist return it, else return empty list
        if(path.get(0).equals(start)){
            printPath(path);
            return path;
        }
        return new ArrayList<Block>();
    }

    public void printPath(ArrayList<Block> path){
        String s = "";
        for(Block b: path){
            int x = b.getX();
            int y = b.getY();
            s+="["+x+","+y+"] , ";
        }
        System.out.println(s);
    }
}
