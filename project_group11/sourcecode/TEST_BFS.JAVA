 public class TEST_BFS {

 public static void main(String[] args) {
        Player p = new Player(new Point(5,5),10.0,8,12,"33",7);

        
        p.addPoint2Map(9,5);
        p.getPoint(9, 5).setIsWall(true);
        p.addPoint2Map(9,6);
        p.getPoint(9, 6).setIsWall(true);
        p.addPoint2Map(9,7);
        p.getPoint(9, 7).setIsWall(true);
        p.addPoint2Map(9,8);
        p.getPoint(9, 8).setIsWall(true);
        p.addPoint2Map(9,9);
        p.getPoint(9, 9).setIsWall(true);
        p.addPoint2Map(9,10);
        p.getPoint(9, 10).setIsWall(true);
        p.addPoint2Map(9,11);
        p.getPoint(9, 11).setIsWall(true);
        p.addPoint2Map(9,12);
        p.getPoint(9, 12).setIsWall(true);

        p.addPoint2Map(4,5);
        p.getPoint(4, 5).setIsWall(true);
        p.addPoint2Map(4,6);
        p.getPoint(4, 6).setIsWall(true);
        p.addPoint2Map(4,7);
        p.getPoint(4, 7).setIsWall(true);
        p.addPoint2Map(4,8);
        p.getPoint(4, 8).setIsWall(true);
        p.addPoint2Map(4,9);
        p.getPoint(4, 9).setIsWall(true);
        p.addPoint2Map(4,10);
        p.getPoint(4, 10).setIsWall(true);
        p.addPoint2Map(4,11);
        p.getPoint(4, 11).setIsWall(true);
        p.addPoint2Map(4,12);
        p.getPoint(4, 12).setIsWall(true);





        p.addPoint2Map(5,13);
        p.getPoint(5, 13).setIsWall(true);
        p.addPoint2Map(6,13);
        p.getPoint(6, 13).setIsWall(true);
        p.addPoint2Map(7,13);
        p.getPoint(7, 13).setIsWall(true);
        p.addPoint2Map(8,13);
        p.getPoint(8, 13).setIsWall(true);

        p.addPoint2Map(5,4);
        p.getPoint(5, 4).setIsWall(true);
        p.addPoint2Map(6,4);
        p.getPoint(6, 4).setIsWall(true);
        p.addPoint2Map(7,4);
        p.getPoint(7, 4).setIsWall(true);
        p.addPoint2Map(8,4);
        p.getPoint(8, 4).setIsWall(true);
        
       // p.addPoint2Map(5,5);
        p.addPoint2Map(6,5);
        p.addPoint2Map(7,5);
        p.addPoint2Map(8,5);

        p.addPoint2Map(5,6);
        p.getPoint(5, 6).setIsWall(true);
        p.addPoint2Map(6,6);
        p.getPoint(6, 6).setIsWall(true);
        p.addPoint2Map(7,6);
        p.getPoint(7, 6).setIsWall(true);
        p.addPoint2Map(8,6);

        p.addPoint2Map(5,7);
        p.addPoint2Map(6,7);
        p.addPoint2Map(7,7);
        p.addPoint2Map(8,7);

        p.addPoint2Map(5,8);
        p.getPoint(5, 8).setIsWall(true);
        p.addPoint2Map(6,8);
        p.addPoint2Map(7,8);
        p.getPoint(7, 8).setIsWall(true);
        p.addPoint2Map(8,8);
        p.getPoint(8, 8).setIsWall(true);
        
        p.addPoint2Map(5,9);
        p.getPoint(5, 9).setIsWall(true);
        p.addPoint2Map(6,9);
        p.addPoint2Map(7,9);
        p.addPoint2Map(8,9);
        p.getPoint(8, 9).setIsWall(true);
        
        
        p.addPoint2Map(5,10);
        p.addPoint2Map(6,10);
        p.getPoint(6, 10).setIsWall(true);
        p.addPoint2Map(7,10);
        p.addPoint2Map(7,10);

        p.addPoint2Map(5,11);
        p.addPoint2Map(6,11);
        p.addPoint2Map(7,11);
        p.addPoint2Map(8,11);
     
        p.addPoint2Map(5,12);
        p.getPoint(5, 12).setIsWall(true);
        p.addPoint2Map(6,12);
        p.getPoint(6, 12).setIsWall(true);
        p.addPoint2Map(7,12);
        p.getPoint(7, 12).setIsWall(true);
        p.addPoint2Map(8,12);

        System.out.println(p.BFS(p.getPoint(5, 5), 8, 12));
    }
}
