package sourcecode;

import java.sql.Array;

public class MapFiles {
    String [] maps ;

    public MapFiles(){
        maps = new String[]{"testmap.txt","Tiny.txt","tt","examinermap_phase1"};

    }
    public String[] getFiles(){
        return maps ;
    }
}
