package board;

import java.util.ArrayList;
public class Board{
	boolean sideToMove;
	
	boolean whiteKingCastle;
	boolean whiteQueenCastle;
	boolean blackKingCastle;
	boolean blackQueenCastle;

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
	}

	public int getPiece(XY a){
		return board[a.x][a.y];
	}

	public ArrayList<XY> getMoves(XY a){
		ArrayList<XY> moves = Piece.getMoves(getPiece(a),a); // unfiltered list, showing all moves assuming empty board
	}
}