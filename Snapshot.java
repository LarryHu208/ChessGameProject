//import testy.*;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.SpriteWindow;
import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.ImageTile;
import java.lang.*;
import java.util.*;

/** An immutable snapshot of MyModel's state */

public class Snapshot {

    public boolean[][] b_tiles;
    public Tile[][] tiles;
    public Map<String, Piece> ChessPieces;
    public List<Piece> list;
    public boolean[][] clicked;
    public Tile[][] highlighted;
    public Piece saved;
    public Piece promotion;
    public boolean promotion_flag;
    public boolean promotion_flag_white;
    public boolean promotion_flag_black;
    public final Size gridSize = new Size(9, 9);

    public Snapshot() {
        b_tiles = new boolean[9][9];
        tiles = new Tile[gridSize.width][gridSize.height];
        ChessPieces = new HashMap<String, Piece>();
        list = new ArrayList<Piece>();
        clicked = new boolean[9][9];
        highlighted = new Tile[gridSize.width][gridSize.height];
        promotion_flag = false;
        promotion_flag_white = false;
        promotion_flag_black = false;
    }


}
