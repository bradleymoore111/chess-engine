package main.board;

import java.util.ArrayList;
import main.board.piece.Piece;
import main.board.piece.XY;
public class Board{
	boolean sideToMove;
	
	boolean whiteKingCastle;
	boolean whiteQueenCastle;
	boolean blackKingCastle;
	boolean blackQueenCastle;

	boolean lastMoveEnPassantable;
	int lastPawnEnPassantable;

	int movesSincePieceLost; // 50-move rule

	/* 	Each piece will be represented by a number
		0 - empty tile
		1 - king
		2 - knight
		3 - bishop
		4 - rook
		5 - queen
		Values > 0 are white, < 0 are black

		x values are horizontal, y values are vertical
		[0][0] would correspond to white queenside rook
		[3][0] would correspond to white queen
		[4][7] would correspond to black king
	 */
	int[][] board;

	public Board(){
		initialize(); //todo;
	}
	
	public void initialize(){
		board = new int[8][8];
		
		// For now being initialized with just a knight and king
		board[4][0] =  1;
		board[2][0] =  2;
		board[5][0] =  2;

		board[4][7] = -1;
		board[2][7] = -2;
		board[5][7] = -2;

		sideToMove = true;
		whiteKingCastle = true;
		whiteQueenCastle = true;
		blackKingCastle = true;
		blackQueenCastle = true;

		lastMoveEnPassantable = false;
		lastPawnEnPassantable = 0
	}

	public int getPiece(XY a){
		return board[a.x][a.y];
	}

	public ArrayList<XY> getMoves(XY a){
		int type = getPiece(a);
		ArrayList<XY> m = Piece.getMoves(type,a,board); // unfiltered list, showing all moves assuming empty board
		
		// If bishop, rook, or queen, cannot move past pieces, ever. Those options will be filtered within each piece case

		// UIniversal rule, cannot move onto friendly pieces, but can move onto enemies
		for(int i=0;i<m.size();i++){
			int x=m.get(i).x;
			int y=m.get(i).y;
			if(board[x][y]==0){
				continue;
			}else if(board[x][y]*type>0){ // same color, therefore cannot move there
				m.remove(i);
				i--;
			}
		}

		return m;
	}
}