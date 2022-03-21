package sourcecode;

public class Move_Random_Algorithm implements Algorithm {
    Player player;

    public void execute(Player p) {
        player = p;
        int randomFace = (int) (Math.random() * 4 + 1)+1;
       if (randomFace == 5) {
           player.setFacing("U");
           player.moveInDirection("U");
       }
       if (randomFace == 2) {
            player.setFacing("D");
            player.moveInDirection("D");
       }
       if (randomFace == 3) {
            player.setFacing("L");
            player.moveInDirection("L");
        }
       if (randomFace == 4) {
            player.setFacing("R");
            player.moveInDirection("R");
       }
       player.getVisited4GUI().add(new Point(player.getLocation().getX(),player.getLocation().getY()));
    }
    
}
