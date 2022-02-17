package project_group11;

import java.util.Objects;

public class Point {
    private final int x, y;
    private  boolean isWall;
    private  boolean isTeleport;
    private  boolean isDoor;
    private  boolean isWindow;
    private Point targetTeleport = null; 


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setIsWall(boolean isWall){
        this.isWall = isWall;
    }

    
    public void setIsTeleport(boolean isTeleport, Point targetPoint){
        this.isTeleport = isTeleport;
        if(isTeleport){
            targetTeleport = targetPoint;
        }
    }

    public boolean getIsWall(){ return isWall;}
    public boolean getIsTeleport(){ return isTeleport;}
    public boolean getIsDoor(){ return isDoor;}
    public boolean getIsWindow(){ return isWindow;}
    public Point getTeleportTarget(){ return targetTeleport;}
    


    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }
}
