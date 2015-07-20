package main.board.piece;

import java.util.ArrayList;
public class King{
	public static ArrayList<XY> getMoves(XY pos,int[][] board){
		ArrayList<XY> moves = new ArrayList<XY>();
		int x = pos.x;
		int y = pos.y;

		XY[] locs = {new XY(1,1),new XY(0,1),new XY(-1,1),new XY(-1,0),new XY(-1,-1),new XY(0,-1),new XY(1,-1),new XY(1,0)};

		for(int i=0;i<locs.length;i++){
			XY p = locs[i];
			if(x+p.x>=0&&x+p.x<8&&y+p.y>=0&&y+p.y<8)
				moves.add(new XY(x+p.x,y+p.y));
		}

		// Todo: castling

		/*
		type = board[x][y];

		if(type>0){ // white
			if(castle kingside && (pieces in between are empty)){
				if(diagonals and horizontals are clear from bishop and rook or queen){
					if(knight not attacking intermittent piece (specific amounts of spots you need to check)){
						add move castle
					}
				}
			}
			if(castle queenside && pieces in between are empty){
				// same chain as above. Could compress entire thing into one long logical statement, will probably do that
			}
		}else{ // black
			// Same chains as above, except black side and black pieces
		}
		// later on, during the actual moving of pieces, check every time if pawn or king (cause special rules)
		// if king, check if more than one move horizontally, if so, move rook as well
		 */

		return moves;
	}
}