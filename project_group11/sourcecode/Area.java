package sourcecode;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joel
 */
public class Area {
    protected int leftBoundary;
    protected int rightBoundary;
    protected int topBoundary;
    protected int bottomBoundary;
    private int  x1 ;
    private int x2 ;
    private int y1 ;
    private  int y2 ;
    private Point agentpoint;
    private Point rightview;
    private Point leftview;
    
    public Area(){
        leftBoundary=0;
        rightBoundary=1;
        topBoundary=0;
        bottomBoundary=1;
    }
    
    public Area(int x1, int y1, int x2, int y2){
        this.x1 = x1 ;
        this.x2 = x2 ;
        this.y1 = y1 ;
        this.y2 = y2 ;
        leftBoundary=Math.min(x1,x2);
        rightBoundary=Math.max(x1,x2);
        topBoundary=Math.max(y1,y2);
        bottomBoundary=Math.min(y1,y2);
    }

    public Area(Point agentpoint, Point rightview, Point leftview){

        this.agentpoint = agentpoint;
        this.rightview = rightview;
        this.leftview = leftview;
    }
    
    /*
        Check whether a point is in the target area
    */
    public boolean isHit(double x,double y){
        return (y>bottomBoundary)&(y<topBoundary)&(x>leftBoundary)&(x<rightBoundary);
    }

    /*
        Check whether something with a radius is in the target area
        STILL TO BE IMPLEMENTED
    */
    public boolean isHit(double x,double y,double radius){
        return false;
    }

    public int getX1(){return x1 ; }

    public int getX2() {
        return x2;
    }
    public int getY1(){return y1;}

    public int getY2() {return y2;}


}
