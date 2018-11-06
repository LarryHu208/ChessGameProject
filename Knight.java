import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.ImageTile;
import java.lang.*;
import java.util.*;
public class Knight extends Piece {

    public Knight() {
    }
    public String get_type() {
        return type;
    }

    public String get_name() {
        return name;
    }

    public Tile get_tile() {
        return tile;
    }

    public int get_x() {
        return x;
    }

    public int get_y() {
        return y;
    }

    public int get_color() {
        return color;
    }

    public boolean move_check( int x, int y) {
        coord newPos = new coord(x,y);
        if (options()==null) return true;
        //String strList = options().toString();
        //System.out.println( ("Move to: "+newPos.toString()) );
        System.out.println( strList);
        return ( strList.contains(newPos.toString()));
    }


    public List<coord> options() {

        List<coord> list = new ArrayList<coord>();

        list.add(new coord(x+1, y-2));
        list.add(new coord(x-1, y-2));
        list.add(new coord(x+1, y+2));
        list.add(new coord(x-1, y+2));
        list.add(new coord(x+2, y+1));
        list.add(new coord(x+2, y-1));
        list.add(new coord(x-2, y+1));
        list.add(new coord(x-2, y-1));
        return filtered_options(list);
    }

    public void remove() {

    }

    //public List<Tile> options();
}


