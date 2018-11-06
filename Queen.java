import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.ImageTile;
import java.lang.*;
import java.util.*;
public class Queen extends Piece {

    public Queen() {
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

    public void remove() {

    }

    public boolean move_check( int x, int y) {
        coord newPos = new coord(x,y);
        if (options()==null) return true;
        String strList = options().toString();
        //System.out.println( ("Move to: "+newPos.toString()) );
        //System.out.println( strList);
        return ( strList.contains(newPos.toString()));
    }


    //public List<Tile> options();
}

