import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.File;
import java.util.Random;
import java.lang.*;
import java.util.*;
import java.nio.file.Paths;
//import edu.calpoly.testy.Testy;

//import testy.*;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.SpriteWindow;
import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.ImageTile;




public class WinMain // extends SpriteWindow
{

	//private final Random rand = new Random();
	private Color color;
	private Font font = new Font("Times", Font.PLAIN, 36);
	private final Size gridSize = new Size(9, 9);
	private final Random rand = new Random();
	private int side;


	private Snapshot state;
    private List<Piece> list;  //*Q

	WinMain(Snapshot state, int side) {
		this.state = state;
		this.side = side;
		//side = 0 for white;
		//side = 1 for black
	}

	private final SpriteWindow window = new SpriteWindow("Chess Nut!", gridSize);

	private Tile backgroundTile;

    public class BackgroundTile implements edu.calpoly.spritely.Tile
    {
        Color color;
        char text;
        BackgroundTile(java.awt.Color color, char text) {
            this.color = color;
            this.text = text;
        }
        @Override
        public char getText() {
            return text;
        }

        @Override
        public void paint(Graphics2D g, Size size) {
            g.setColor(color);
            g.fill3DRect(0, 0, size.width, size.height, true);
            g.setColor(Color.WHITE);
            g.drawString(Character.toString(this.text), size.width/2 , size.height/2);
            //System.out.println(Character.toString(this.text));
        }
    }

    public class HighlightedTile implements edu.calpoly.spritely.Tile
    {
        Color color;
        char text;
        HighlightedTile(java.awt.Color color, char text) {
            this.color = color;
            this.text = text;
        }
        @Override
        public char getText() {
            return text;
        }
        @Override
        public void paint(Graphics2D g, Size size) {
            g.setColor(color);
            //g.fillOval(0, 0, size.width, size.height);
            g.fill3DRect(0, 0, size.width, size.height, true);
        }
    }

	private void clearBArray( boolean[][] test) {
		for (boolean[] row : test)
			Arrays.fill(row, false);
	}

	private void clearHArray( Tile[][] test) {
		for (Tile[] row: test)
			Arrays.fill(row, null);
	}

	private Piece find_clicked(int x, int y) {
		Piece wanted = null;
		state.list = new ArrayList<Piece>(state.ChessPieces.values());
		for (Piece piece: state.list) {
			if(piece.get_x() == x && piece.get_y() == y) {
				wanted = piece;
			}
		}
		return wanted;
	}

	private void mouseClicked(int x, int y) /*throws java.io.IOException*/  {
		//accounts for 9x9 square

		HighlightedTile c;
		color = Color.yellow;

		if (side==1)   //*Q
        {
            x = (x>0)?(9-x):x;
            y = (y>0)?(9-y):y;
        }

		if(state.highlighted[x][y] != null) {
			//System.out.print("Clicked state.highlighted!");
		}

		clearHArray(state.highlighted);
		clearBArray(state.clicked);

		Piece target = find_clicked(x,y);
		if (state.promotion != null) {
			//promote(x,y);
		}
		if (state.saved != null) {

			if (target != null && target != state.saved) {
				// if piece there, remove from map piece at that spot but not self
				System.out.println(target.get_name());
				System.out.println(target.get_color());
				state.ChessPieces.remove(target.get_name());
				//state.list.remove(target);
				System.out.print(state.list.size());
			}
			if(state.saved.get_type() == "pawn") {
				state.promotion = state.saved;
				if(state.saved.get_color() == 0 && y == 1) {
					//System.out.print("White pawn state.promotion");
					//System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
					state.promotion_flag = true;
					state.promotion_flag_white = true;
				}
				if(state.saved.get_color() == 1 && y == 8) {
					//System.out.print("Black pawn state.promotion");
					state.promotion_flag_black = true;
					state.promotion_flag = true;

				}

			}
			System.out.println("About to set pos");
			state.saved.set_pos(x ,y );
			state.saved = null;
			System.out.println("set pos");
			clearHArray(state.highlighted);
		}

		else if(target != null) {
			//if state.clicked on piece
			System.out.println(target.get_name());
			System.out.println(target.get_color());
			state.saved = find_clicked(x,y);
			c = new HighlightedTile(color, 'c');
			state.highlighted[x][y] = c;
			state.clicked[x][y] = true;
			/*if(target.get_type() == "king") {
				target = (King) target;
			}
			else if(target.get_type() == "knight") {
				target = (Knight) target;
			}*/

			/*for(int i = 0; i < target.options().size(); i++) {
				System.out.println(target.options().get(i).x);
				System.out.println(target.options().get(i).y);
			}*/
			/*
			for(int i = 0; i < target.options().size(); i++) {
				c = new HighlightedTile(color, 'c');
				state.highlighted[target.options().get(i).x][target.options().get(i).y] = c;
				state.clicked[target.options().get(i).x][target.options().get(i).y] = true;
			*/
		}

		System.out.println("Mouse state.clicked:  x=" + (x ) + ", y=" + (y) + "    ");
	}

	private void keyTyped(char ch) {

		System.out.println("Key typed:  " + ch + "    ");

		if (ch == (char) 4 || ch == 'q') {

			System.out.println();

			System.out.println("Quitting program.    ");

			System.out.println();

			System.exit(0);

		} else if (ch == 'p') {

			System.out.println("Pausing animation for 5 seconds...    ");

			window.pauseAnimation(5000);

			System.out.println("Pause done.    ");

		} else if (ch == 'r') {

			System.out.println("Testing reset of animation clock...");

			try {

				Thread.sleep(5000);

			} catch (InterruptedException ex) {

				Thread.currentThread().interrupt();

			}

            window.pauseAnimation(0);

			System.out.println("Resuming.");

		}

	}

	public void setup_Piece() throws java.io.IOException {
		//sets up chess board together into initial phase
		// Black Pawns
		int counter = 0;


		//Black Pawns
		for(int i = 1; i < 9; i++) {
			Pawn Black_Pawn = new Pawn();
			//x and y reverse
			Black_Pawn.x = i;
			Black_Pawn.y = 2;
			Black_Pawn.color = 1;
			Black_Pawn.name = "BP" + i;
			Black_Pawn.type = "pawn";
			Black_Pawn.tile = new ImageTile( new File("images/black_pawn.png"), window.getTileSize(), 'p');
			state.b_tiles[i][2] = true;
			state.ChessPieces.put(Black_Pawn.name, Black_Pawn);
		}

		//Black Rooks
		Rook Black_Rook = new Rook();
		//x and y reverse
		Black_Rook.x = 1;
		Black_Rook.y = 1;
		Black_Rook.color = 1;
		Black_Rook.name = "BR" + 1;
		Black_Rook.type = "rook";
		Black_Rook.tile = new ImageTile( new File("images/black_rook.png"), window.getTileSize(), 'r');
		state.b_tiles[1][1] = true;
		state.ChessPieces.put(Black_Rook.name, Black_Rook);

		Black_Rook = new Rook();
		//x and y reverse
		Black_Rook.x = 8;
		Black_Rook.y = 1;
		Black_Rook.color = 1;
		Black_Rook.name = "BR" + 2;
		Black_Rook.type = "rook";
		Black_Rook.tile = new ImageTile( new File("images/black_rook.png"), window.getTileSize(), 'r');
		state.b_tiles[8][1] = true;
		state.ChessPieces.put(Black_Rook.name, Black_Rook);


		//Black Knights
		Knight Black_Knight = new Knight();
		//x and y reverse
		Black_Knight.x = 2;
		Black_Knight.y = 1;
		Black_Knight.color = 1;
		Black_Knight.name = "BK" + 1;
		Black_Knight.type = "knight";
		Black_Knight.tile = new ImageTile( new File("images/black_knight.png"), window.getTileSize(), 'n');
		state.b_tiles[2][1] = true;
		state.ChessPieces.put(Black_Knight.name, Black_Knight);

		Black_Knight = new Knight();
		//x and y reverse
		Black_Knight.x = 7;
		Black_Knight.y = 1;
		Black_Knight.color = 1;
		Black_Knight.name = "BK" + 2;
		Black_Knight.type = "knight";
		Black_Knight.tile = new ImageTile( new File("images/black_knight.png"), window.getTileSize(), 'n');
		state.b_tiles[7][1] = true;
		state.ChessPieces.put(Black_Knight.name, Black_Knight);

		//Black Bishop
		Bishop Black_Bishop = new Bishop();
		//x and y reverse
		Black_Bishop.x = 3;
		Black_Bishop.y = 1;
		Black_Bishop.color = 1;
		Black_Bishop.name = "BB" + 1;
		Black_Bishop.type = "bishop";
		Black_Bishop.tile = new ImageTile( new File("images/black_bishop.png"), window.getTileSize(), 'b');
		state.b_tiles[3][1] = true;
		state.ChessPieces.put(Black_Bishop.name, Black_Bishop);

		Black_Bishop = new Bishop();
		//x and y reverse
		Black_Bishop.x = 6;
		Black_Bishop.y = 1;
		Black_Bishop.color = 1;
		Black_Bishop.name = "BB" + 2;
		Black_Bishop.type = "bishop";
		Black_Bishop.tile = new ImageTile( new File("images/black_bishop.png"), window.getTileSize(), 'b');
		state.b_tiles[6][1] = true;
		state.ChessPieces.put(Black_Bishop.name, Black_Bishop);

		//Black Queen
		Queen Black_Queen = new Queen();
		//x and y reverse
		Black_Queen.x = 4;
		Black_Queen.y = 1;
		Black_Queen.color = 1;
		Black_Queen.name = "BQ" + 1;
		Black_Queen.type = "queen";
		Black_Queen.tile = new ImageTile( new File("images/black_queen.png"), window.getTileSize(), 'q');
		state.b_tiles[4][1] = true;
		state.ChessPieces.put(Black_Queen.name, Black_Queen);

		//Black King
		King Black_King = new King();
		//x and y reverse
		Black_King.x = 5;
		Black_King.y = 1;
		Black_King.color = 1;
		Black_King.name = "BKg" + 1;
		Black_King.type = "king";
		Black_King.tile = new ImageTile( new File("images/black_king.png"), window.getTileSize(), 'k');
		state.b_tiles[5][1] = true;
		state.ChessPieces.put(Black_King.name, Black_King);

		//White Pawns
		for(int i = 1; i < 9; i++) {
			Pawn White_Pawn = new Pawn();
			//x and y reverse
			White_Pawn.x = i;
			White_Pawn.y = 7;
			White_Pawn.color = 0;
			White_Pawn.name = "WP" + i;
			White_Pawn.type = "pawn";
			White_Pawn.tile = new ImageTile( new File("images/white_pawn.png"), window.getTileSize(), 'P');
			state.b_tiles[i][7] = true;
			state.ChessPieces.put(White_Pawn.name, White_Pawn);
		}

		//White Rooks
		Rook White_Rook = new Rook();
		//x and y reverse
		White_Rook.x = 8;
		White_Rook.y = 8;
		White_Rook.color = 0;
		White_Rook.name = "WR" + 1;
		White_Rook.type = "rook";
		White_Rook.tile = new ImageTile( new File("images/white_rook.png"), window.getTileSize(), 'R');
		state.b_tiles[8][8] = true;
		state.ChessPieces.put(White_Rook.name, White_Rook);

		White_Rook = new Rook();
		//x and y reverse
		White_Rook.x = 1;
		White_Rook.y = 8;
		White_Rook.color = 0;
		White_Rook.name = "WR" + 2;
		White_Rook.type = "rook";
		White_Rook.tile = new ImageTile( new File("images/white_rook.png"), window.getTileSize(), 'R');
		state.b_tiles[1][8] = true;
		state.ChessPieces.put(White_Rook.name, White_Rook);

		//Black Knights
		Knight White_Knight = new Knight();
		//x and y reverse
		White_Knight.x = 2;
		White_Knight.y = 8;
		White_Knight.color = 0;
		White_Knight.name = "WK" + 1;
		White_Knight.type = "knight";
		White_Knight.tile = new ImageTile( new File("images/white_knight.png"), window.getTileSize(), 'N');
		state.b_tiles[2][8] = true;
		state.ChessPieces.put(White_Knight.name, White_Knight);

		White_Knight = new Knight();
		//x and y reverse
		White_Knight.x = 7;
		White_Knight.y = 8;
		White_Knight.color = 0;
		White_Knight.name = "WK" + 2;
		White_Knight.type = "knight";
		White_Knight.tile = new ImageTile( new File("images/white_knight.png"), window.getTileSize(), 'N');
		state.b_tiles[7][8] = true;
		state.ChessPieces.put(White_Knight.name, White_Knight);

		//White Bishop
		Bishop White_Bishop = new Bishop();
		//x and y reverse
		White_Bishop.x = 3;
		White_Bishop.y = 8;
		White_Bishop.color = 0;
		White_Bishop.name = "WB" + 1;
		White_Bishop.type = "bishop";
		White_Bishop.tile = new ImageTile( new File("images/white_bishop.png"), window.getTileSize(), 'B');
		state.b_tiles[3][8] = true;
		state.ChessPieces.put(White_Bishop.name, White_Bishop);

		White_Bishop = new Bishop();
		//x and y reverse
		White_Bishop.x =6;
		White_Bishop.y = 8;
		White_Bishop.color = 0;
		White_Bishop.name = "WB" + 2;
		White_Bishop.type = "bishop";
		White_Bishop.tile = new ImageTile( new File("images/white_bishop.png"), window.getTileSize(), 'B');
		state.b_tiles[5][7] = true;
		state.ChessPieces.put(White_Bishop.name, White_Bishop);


		//White Queen
		Queen White_Queen = new Queen();
		White_Queen.x = 4;
		White_Queen.y = 8;
		White_Queen.color = 0;
		White_Queen.name = "WQ" + 1;
		White_Queen.type = "queen";
		White_Queen.tile = new ImageTile( new File("images/white_queen.png"), window.getTileSize(), 'Q');
		state.b_tiles[4][8] = true;
		state.ChessPieces.put(White_Queen.name, White_Queen);

		//White King
		King White_King = new King();
		//x and y reverse
		White_King.x = 5;
		White_King.y = 8;
		White_King.color = 0;
		White_King.name = "WKg" + 1;
		White_King.type = "king";
		White_King.tile = new ImageTile( new File("images/white_king.png"), window.getTileSize(), 'K');
		state.b_tiles[5][8] = true;
		state.ChessPieces.put(White_King.name, White_King);


	}

	public void setup_Board() {

		//background

		//checkboard
		for (int x = 1; x < gridSize.width; x++) {

			for (int y = 1; y < gridSize.height; y++) {
				if(side == 0) {
					if ((x + y) % 2 == 0) {
						color = new Color(128, 128, 128);
					} else {
						color = new Color(255, 255, 255);
					}
				}
				else {
					if ((x + y) % 2 != 0) {
						color = new Color(128, 128, 128);
					} else {
						color = new Color(255, 255, 255);
					}
				}

				char c = (char) ('a' + rand.nextInt(26));
				state.tiles[x][y] = new SolidColorTile(color, c);

			}

		}


		state.list = new ArrayList<Piece>(state.ChessPieces.values());

		System.out.println(state.list.size());
	}


    public void startX() throws IOException {
        window.setFps(10);
        window.setKeyTypedHandler(ch -> keyTyped(ch));
        window.setMouseClickedHandler((x, y) -> mouseClicked(x, y));
        window.start();
        print_board();
        setup_Piece();
        setup_Board();
        runOnce();
    }


    public void stopX() throws IOException {
        print_board();
        System.out.println("Stopping...    ");
        window.stop();
        System.out.println("Goodbye    ");
    }


    public void runOnce() throws IOException {

	    AnimationFrame frame = window.waitForNextFrame();

	    if (frame == null) {
	        return;
	    }

	    list = new ArrayList<Piece>(state.ChessPieces.values()); //*Q

	    char pos;
	    for (int x = 0; x < gridSize.width; x++) {

	        for (int y = 0; y < gridSize.height; y++) {
	            if(x==0 && y == 0) {
	                pos = ' ';
	            }
	            else if(x == 0 && y > 0) {
	                if(side == 0) {
						//ascii of 8 - 1
						int val = 57 - y;
						pos = (char) val;
					}
					else {
						int val = 48 + y;
						pos = (char) val;
					}
	            }
	            else if(y == 0 && x > 0) {

						//ascii of a-g
						int val = 96 + x;
						pos = (char) val;
	            }
	            else {
	                pos = '.';
	            }
	            backgroundTile = new BackgroundTile(Color.blue.darker(), pos);
	            frame.addTile( ((side==1)?((x>0)?(9-x):x):x), ((side==1)?((y>0)?(9-y):y):y), backgroundTile); //*Q
	        }
	    }

	    //pieces for state.promotion
        if(state.promotion_flag_white) {
            state.tiles[1][0] = new ImageTile( new File("images/white_queen.png"), window.getTileSize(), 'Q');
            state.tiles[2][0] = new ImageTile( new File("images/white_rook.png"), window.getTileSize(), 'R');
            state.tiles[3][0] = new ImageTile( new File("images/white_bishop.png"), window.getTileSize(), 'B');
            state.tiles[4][0] = new ImageTile( new File("images/white_knight.png"), window.getTileSize(), 'N');
        }
        if(state.promotion_flag_black) {
            state.tiles[1][0] = new ImageTile( new File("images/black_queen.png"), window.getTileSize(), 'q');
            state.tiles[2][0] = new ImageTile( new File("images/black_rook.png"), window.getTileSize(), 'r');
            state.tiles[3][0] = new ImageTile( new File("images/black_bishop.png"), window.getTileSize(), 'b');
            state.tiles[4][0] = new ImageTile( new File("images/black_knight.png"), window.getTileSize(), 'n');
        }

        if (state.promotion_flag) {

            for(int x = 1; x < 5; x++) {
                Tile t = state.tiles[x][0];
                frame.addTile(((side==1)?((x>0)?(9-x):x):x), 0, t);  //*Q
            }
        }

        for (int x = 1; x < gridSize.width; x++) {
            for (int y = 1; y < gridSize.height; y++) {
                Tile t;
                t = state.tiles[x][y];

                if (t != null) {
                    frame.addTile(((side==1)?((x>0)?(9-x):x):x), ((side==1)?((y>0)?(9-y):y):y), t);  //*Q
                    for (int j = 0; j < list.size(); j++) {
                        Piece piece = list.get(j);
                        //System.out.println(piece.get_type());
                        int px = piece.get_x();
                        int py = piece.get_y();
                        px = ((side==1)?((px>0)?(9-px):px):px);
                        py = ((side==1)?((py>0)?(9-py):py):py);
                        frame.addTile(px, py, piece.get_tile());
                    }
                    if (state.clicked[x][y]) {
                        frame.addTile(((side==1)?((x>0)?(9-x):x):x), ((side==1)?((y>0)?(9-y):y):y), state.highlighted[x][y]);
                    }
                }
            }
        }

        window.showNextFrame();
	}

	public void print_board() throws IOException {

		setup_Piece();
		setup_Board();

		char[][] board = new char[9][9];
        list = new ArrayList<Piece>(state.ChessPieces.values()); //*Q

		for (int x = 0; x < gridSize.width; x++) {
			for (int y = 0; y < gridSize.height; y++) {
				if(x==0 && y == 0) {
					board[x][y] = ' ';
				}
				else if(x == 0 && y > 0) {
					//ascii of 8 - 1
					int val = 57-y;
					board[x][y] = (char) val;
				}
				else if(y == 0 && x > 0) {
					//ascii of a-g
					int val = 96+x;
					board[x][y] = (char) val;
				}
				Tile t;
				t = state.tiles[x][y];

				if (t != null) {
					if(x+y % 2 == 0) {
						board[x][y] = ' ';
					}
					else {
						board[x][y] = '.';
					}
					for (int j = 0; j < list.size(); j++) {
						Piece piece = list.get(j);
						//System.out.println(piece.get_type());
                        int px = piece.get_x();
                        int py = piece.get_y();
                        px = ((side==1)?((px>0)?(9-px):px):px);
                        py = ((side==1)?((py>0)?(9-py):py):py);
                        board[px][py] = piece.get_tile().getText();
					}

				}
			}
		}
		for (int y = 0; y < 9; y++) {
			for(int x = 0; x < 9; x++) {
				System.out.print(board[x][y] + " ");
			}
			System.out.println("");
		}
	}

}
