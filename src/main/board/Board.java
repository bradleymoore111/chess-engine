package main.board;

import java.util.ArrayList;
import main.board.piece.*;
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
		6 - pawn
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
		
		// Initializing the pieces in order
		// Temporary, later will have pieces be loaded from a preset array (to allow gamemodes like horde and 960 to be more easily generated)
		board[0][0] =  4;
		board[1][0] =  2;
		board[2][0] =  3;
		board[3][0] =  5;
		board[4][0] =  1;
		board[5][0] =  3;
		board[6][0] =  2;
		board[7][0] =  4;

		board[0][7] = -4;
		board[1][7] = -2;
		board[2][7] = -3;
		board[3][7] = -5;
		board[4][7] = -1;
		board[5][7] = -3;
		board[6][7] = -2;
		board[7][7] = -4;

		sideToMove = true;
		whiteKingCastle = true;
		whiteQueenCastle = true;
		blackKingCastle = true;
		blackQueenCastle = true;

		lastMoveEnPassantable = false;
		lastPawnEnPassantable = 0;
	}

	public int getPiece(XY a){
		return board[a.x][a.y];
	}
	public int getPiece(int x,int y){
		return board[x][y];
	}

	public ArrayList<XY> getMoves(XY a){
		int type = getPiece(a);

		ArrayList<XY> m = new ArrayList<XY>();

		if(type<0)
			type = (int) (-1*type);
		switch(type){
			case 0:
				break;
			case 1:
				if(getPiece(a)>0){
					m = King.getMoves(a,board,whiteKingCastle,whiteQueenCastle);
				}else{
					m = King.getMoves(a,board,blackKingCastle,blackQueenCastle);
				}			
				break;
			case 2:
				m = Knight.getMoves(a);
				break;
			case 3:
				m = Bishop.getMoves(a,board);
				break;
			case 4:
				m = Rook.getMoves(a,board);
				break;
			case 5:
				m = Queen.getMoves(a,board);
				break;
			// default:
			// 	m = Pawn.getMoves(a,lastMoveEnPassantable,lastPawnEnPassantable);
			//	break;
		}

		// If bishop, rook, or queen, cannot move past pieces, ever. Those options will be filtered within each piece's case

		// Universal rule, cannot move onto friendly pieces, but can move onto enemies
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

	public boolean isChecked(boolean color){
		// Check along diagonals for bishops, pawns, queens, or king of opposite color. Can stop checking if hit another piece
		// Check along horizontal for rooks, queens, or king of opposite color. Can stop checking if hit another piece
		// Check all possible knight squares.

		return true;
	}

	public void move(XY a,XY b){
		/*
			For the actual engine, I probably won't be using this specific command, as it involves some unneccessary calculations such as checking. Whereas for my type A calculation and branching, I'm probably going to just have a list of every single possible move, and want to filter each move preemptively to make sure legal. I'll still need the pawn and castle stuff, but can ignore the checking legal moves, so I'll copy pasta this function with an f in front of it for faster, but assuming legal move (so move will be carried out properly).
		 */
		// Todo: implement few checks
		// Make sure is a legal move
		// 	   Move is within that piece's move list (getMoves of that piece)
		//     Doesn't result in check after move (handles both pins, and not dealing with present check)
		// If pawn move, check if en passantable needs to be toggled
		// If not pawn move, turn off en passantable (both variables)
		// If king, check if it's the castle. If so, move rook
		// 
	}

	public String toString(){
		// Very quick ascii representation of the board.
		String s="\n";

		s+= ((sideToMove)?"White":"Black") + " to move.\n\n|-----|-----|-----|-----|-----|-----|-----|-----|";
		for(int i=7;i>=0;i--){
			s+="\n|";
			for(int j=0;j<8;j++){
				if((i+j)%2 == 0){
					s+= " " + ((board[j][i]>=0)?" ":"") + board[j][i] + "  ";
				}else{
					s+= ":" + ((board[j][i]>=0)?":":"") + board[j][i] + "::";
				}
				s+= "|";
			}
			s+="  "+i+"\n|-----|-----|-----|-----|-----|-----|-----|-----|";
		}
		s+="\n   0     1     2     3     4     5     6     7";
		return s;
	}
}