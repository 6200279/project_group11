package sourcecode;

public class AgentViewPoints {

    public double agentdir = 30.0;
    public double agentdirrad = (agentdir * Math.PI)/180.0;
    public double segmentspan = (45 * Math.PI)/180.0;
    public Point agentposition = new Point(25,25);
    public int pointsoncircle = 32;
    public int radius = 10;

    public AgentViewPoints(){} // may not be needed

    public Point rotate(Point currentpoint, double r){

        int rotatedpointX = (int) ((currentpoint.getX() * Math.cos(r)) - (currentpoint.getY()  * Math.sin(r)));
        int rotatedpointY = (int) ((currentpoint.getY() * Math.cos(r)) - (currentpoint.getX()  * Math.sin(r)));

        System.out.println("Rotated point is: " + rotatedpointX + " " + rotatedpointY);

        return new Point(rotatedpointX,rotatedpointY);

    }

    public Point[] ringOfPoints(){

        Point radiuspoint = new Point(0,radius);
        Point[] arrayofpoints = new Point[pointsoncircle];

        agentposition.setX(25);
        agentposition.setY(25);

        double arc = (2 * Math.PI) / pointsoncircle;

        for(int i = 0; i < arrayofpoints.length; i++){

            arrayofpoints[i] = rotate(radiuspoint, arc * i);
            arrayofpoints[i].setX(arrayofpoints[i].getX() + agentposition.getX());
            arrayofpoints[i].setY(arrayofpoints[i].getY() + agentposition.getY());

        }

        for(int i = 0; i < arrayofpoints.length; i++)
            System.out.println("Current Point is : " + arrayofpoints[i].getX() + " " + arrayofpoints[i].getY());

        return arrayofpoints;

    }

    public Point farthest(){

        agentposition.setX(25);
        agentposition.setY(25);
        int farthestX = (int) (radius * Math.cos(agentdirrad)) + agentposition.getX();
        int farthestY = (int) (radius * Math.sin(agentdirrad)) + agentposition.getY();

        //System.out.println(farthestX + "," + farthestY);

        return new Point(farthestX,farthestY);

    }

    public Point[] viewPoints(){

        agentposition.setX(25);
        agentposition.setY(25);

        Point[] arrayofpoints = new Point[2];

        Point vp1 = new Point(1,1);
        Point vp2 = new Point(1,1);

        double angle1 = agentdirrad - (0.5*segmentspan);
        double angle2 = agentdirrad + (0.5*segmentspan);

        arrayofpoints[0] = vp1;
        arrayofpoints[1] = vp2;

        System.out.println("Angle 1 : " + angle1);
        System.out.println("Angle 2 : " + angle2);

        arrayofpoints[0].setX( (int) (radius * Math.cos(angle1) + agentposition.getX()) );
        arrayofpoints[0].setY( (int) (radius * Math.sin(angle1) + agentposition.getY()) );
        arrayofpoints[1].setX( (int) (radius * Math.cos(angle2) + agentposition.getX()) );
        arrayofpoints[1].setY( (int) (radius * Math.sin(angle2) + agentposition.getY()) );

         System.out.println("The first view point is : " + "[ " +arrayofpoints[0].getX() + "," + arrayofpoints[0].getY() + "]");
         System.out.println("The second view point is : " + "[ " +arrayofpoints[1].getX() + "," + arrayofpoints[1].getY() + "]");

        return arrayofpoints;
    }



}
