package main.board.piece;

import java.util.ArrayList;
public class Pawn{
	public static ArrayList<XY> getMovesWhite(XY pos, int[][] board, boolean lastMoveEnPassantable, int lastPawnEnPassantable){
		ArrayList<XY> moves = new ArrayList<XY>();
		int x = pos.x;
		int y = pos.y;

		// Forward moving
		if(y==1){
			if(board[x][2]==0&&board[x][3]==0){
				moves.add(new XY(x,2));
				moves.add(new XY(x,3));
			}else if(board[x][2]==0){
				moves.add(new XY(x,2));
			}
		}else if(board[x][y+1]==0){
			moves.add(new XY(x,y+1));
		}

		// Diagonal attacking
		if(x!=7){
			if(board[x+1][y+1]<0){
				moves.add(new XY(x+1,y+1));
			}
		}
		if(x!=0){
			if(board[x-1][y+1]<9){
				moves.add(new XY(x-1,y+1));
			}
		}

		// En passant stuff
		// Dear god.

		if(lastMoveEnPassantable){
			if((x+1==lastPawnEnPassantable||x-1==lastPawnEnPassantable)&&y==4){ // If the file is one to the left or one to the right, and the pawn is at the appropriate row
				moves.add(new XY(lastPawnEnPassantable,5));
			}
		}
		// ...That wasn't that bad
		// Maybe the bitchy part is in the board's handling of things?

		return moves;
	}
	public static ArrayList<XY> getMovesBlack(XY pos, int[][] board, boolean lastMoveEnPassantable, int lastPawnEnPassantable){
		ArrayList<XY> moves = new ArrayList<XY>();
		int x = pos.x;
		int y = pos.y;

		// Forward
		if(y==6){
			if(board[x][5]==0&&board[x][4]==0){
				moves.add(new XY(x,5));
				moves.add(new XY(x,4));
			}else if(board[x][5]==0){
				moves.add(new XY(x,5));
			}
		}else if(board[x][y-1]==0){
			moves.add(new XY(x,y-1));
		}

		// Diagonal
		if(x!=7){
			if(board[x+1][y-1]>0){
				moves.add(new XY(x+1,y-1));
			}
		}
		if(x!=0){
			if(board[x-1][y-1]>0){
				moves.add(new XY(x-1,y-1));
			}
		}

		if(lastMoveEnPassantable){
			if((x+1==lastPawnEnPassantable||x-1==lastPawnEnPassantable)&&y==3){
				moves.add(new XY(lastPawnEnPassantable,2));
			}
		}

		return moves;
	}
}