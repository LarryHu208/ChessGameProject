import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.ImageTile;
import java.lang.*;
import java.util.*;

public abstract class Piece {
	public class coord {
		int x;
		int y;
		coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}


	public int color; // 0 for white. 1 for black
	public String name;
	public int x;
	public int y;
	public Tile tile;
	public String type;
	//public void getTile();
	public List<coord> moves;


	public void remove() {

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

	public void set_pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public List<coord> options() {
		System.out.println("Piece Moves");
		return moves;
	}

	public List<coord> filtered_options(List<coord> list) {
		for(coord co: list) {
			if(co.x > 7 || co.x < 0) {
				list.remove(co);
			}
			if(co.y > 7 || co.y < 0) {
				list.remove(co);
			}
		}
		return list;
	}

		public int get_color() {
		return color;
	}


}
