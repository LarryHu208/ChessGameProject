import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.ImageTile;
import java.lang.*;
import java.util.*;

public class Pawn extends Piece {
    boolean moved;
    public Pawn() {
        moved = false;
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

    public List<coord> options() {
        List<coord> list = new ArrayList<coord>();
        if(color == 0) {
            //white pawn
            if(moved == false) {
                list.add(new coord(x, y-2));
            }
            list.add(new coord(x, y-1));
        }
        if(color == 1) {
            if(moved == false) {
                list.add(new coord(x, y+2));
            }
            list.add(new coord(x, y+1));
        }
        return filtered_options(list);
    }
}
