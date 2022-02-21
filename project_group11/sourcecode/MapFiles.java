package sourcecode;

import java.sql.Array;

public class MapFiles {
    String [] maps ;

    public MapFiles(){
        maps = new String[]{"testmap.txt"};

    }
    public String[] getFiles(){
        return maps ;
    }
}
