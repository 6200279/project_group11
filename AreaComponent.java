package nl.maastrichtuniversity.dke.gamecontrollersample;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class AreaComponent extends JComponent {
    Scenario scenario = new Scenario("testmap.txt");
    ArrayList<Area>walls = scenario.getWalls() ;
    ArrayList<TelePortal>telePortals = scenario.teleports ;
    ArrayList<Area>shaded = scenario.shaded ;

    public void paintComponent(Graphics g) {

        for (int i = 0; i < walls.size(); i++) {
            int x1 = walls.get(i).getX1()*7 ;
            int x2 = walls.get(i).getX2()*7 ;
            int y1 = walls.get(i).getY1()*7 ;
            int y2 = walls.get(i).getY2()*7 ;

            int x = Math.min(x1,x2);
            int y = Math.min(y1,y2) ;
            int width = Math.abs(x1-x2) ;
            int height = Math.abs(y1-y2) ;


            g.drawRect(x,y,width,height);
            g.fillRect(x,y,width,height);

        }

        for (int i = 0; i < telePortals.size(); i++) {
            int x1 = telePortals.get(i).getX1()*7 ;
            int x2 = telePortals.get(i).getX2()*7 ;
            int y1 = telePortals.get(i).getY1()*7 ;
            int y2 = telePortals.get(i).getY2()*7 ;

            int x = Math.min(x1,x2);
            int y = Math.min(y1,y2) ;
            int width = Math.abs(x1-x2) ;
            int height = Math.abs(y1-y2) ;

            g.setColor(Color.cyan);
            g.drawRect(x,y,width,height);
            g.fillRect(x,y,width,height);

        }

        for (int i = 0; i < shaded.size(); i++) {
            int x1 = shaded.get(i).getX1()*7 ;
            int x2 = shaded.get(i).getX2()*7 ;
            int y1 = shaded.get(i).getY1()*7 ;
            int y2 = shaded.get(i).getY2()*7 ;

            int x = Math.min(x1,x2);
            int y = Math.min(y1,y2) ;
            int width = Math.abs(x1-x2) ;
            int height = Math.abs(y1-y2) ;

            g.setColor(Color.DARK_GRAY);
            g.drawRect(x,y,width,height);
            g.fillRect(x,y,width,height);

        }

        Area targetArea = scenario.targetArea;
        int x1 = targetArea.getX1()*7 ;
        int x2 = targetArea.getX2()*7 ;
        int y1 = targetArea.getY1()*7 ;
        int y2 = targetArea.getY2()*7 ;

        int x = Math.min(x1,x2);
        int y = Math.min(y1,y2) ;
        int width = Math.abs(x1-x2) ;
        int height = Math.abs(y1-y2) ;

        g.setColor(Color.red);
        g.drawRect(x,y,width,height);
        g.fillRect(x,y,width,height);

        Area spawnAreaIntruders = scenario.spawnAreaIntruders ;
        x1 = spawnAreaIntruders.getX1()*7 ;
        x2 = spawnAreaIntruders.getX2()*7 ;
        y1 = spawnAreaIntruders.getY1()*7 ;
        y2 = spawnAreaIntruders.getY2()*7 ;

         x = Math.min(x1,x2);
         y = Math.min(y1,y2) ;
         width = Math.abs(x1-x2) ;
         height = Math.abs(y1-y2) ;

        g.setColor(Color.blue);
        g.drawRect(x,y,width,height);
        g.fillRect(x,y,width,height);


        Area spawnAreaGuards = scenario.spawnAreaGuards;
        x1 = spawnAreaGuards.getX1()*7 ;
        x2 = spawnAreaGuards.getX2()*7 ;
        y1 = spawnAreaGuards.getY1()*7 ;
        y2 = spawnAreaGuards.getY2()*7 ;

        x = Math.min(x1,x2);
        y = Math.min(y1,y2) ;
        width = Math.abs(x1-x2) ;
        height = Math.abs(y1-y2) ;

        g.setColor(Color.green);
        g.drawRect(x,y,width,height);
        g.fillRect(x,y,width,height);







    }
}
