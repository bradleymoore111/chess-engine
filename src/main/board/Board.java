package main.board;

import java.util.ArrayList;
import main.board.piece.*;
public class Board{
	public boolean sideToMove;
	
	public boolean whiteKingCastle;
	public boolean whiteQueenCastle;
	public boolean blackKingCastle;
	public boolean blackQueenCastle;

	public boolean lastMoveEnPassantable;
	public int lastPawnEnPassantable; // Which file can be captured via en passant

	public int movesSincePieceLost; // 50-move rule

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
	public int[][] board;

	public Board(){
		initialize(); //todo;
	}
	
	public void initialize(){
		board = new int[8][8];
		
		int[][] startingPostitions = {
			{-4,-2,-3,-5,-1,-3,-2,-4},
			{-6,-6,-6,-6,-6,-6,-6,-6},
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 6, 6, 6, 6, 6, 6, 6, 6},
			{ 4, 2, 3, 5, 1, 3, 2, 4}
		};

		// Initializing the pieces in order
		// Temporary, later will have pieces be loaded from a preset array (to allow gamemodes like horde and 960 to be more easily generated)
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				board[j][i]=startingPostitions[7-i][j];
			}
		}
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

		ArrayList<XY> moves= new ArrayList<XY>();

		switch(type){
			case 0:
				break;
			case -1:
			case 1:
				if(getPiece(a)>0){
					moves= King.getMoves(a,board,whiteKingCastle,whiteQueenCastle);
				}else{
					moves= King.getMoves(a,board,blackKingCastle,blackQueenCastle);
				}			
				break;
			case -2:
			case 2:
				moves= Knight.getMoves(a);
				break;
			case -3:
			case 3:
				moves= Bishop.getMoves(a,board);
				break;
			case -4:
			case 4:
				moves= Rook.getMoves(a,board);
				break;
			case -5:
			case 5:
				moves= Queen.getMoves(a,board);
				break;
			case -6:
				moves= Pawn.getMovesBlack(a,board,lastMoveEnPassantable,lastPawnEnPassantable);
				break;
			case 6:
				moves= Pawn.getMovesWhite(a,board,lastMoveEnPassantable,lastPawnEnPassantable);
				break;		
		}

		// If bishop, rook, or queen, cannot move past pieces, ever. Those options will be filtered within each piece's case

		// Universal rule, cannot move onto friendly pieces, but can move onto enemies
		for(int i=0;i<moves.size();i++){
			int x=moves.get(i).x;
			int y=moves.get(i).y;
			if(board[x][y]==0){
				continue;
			}else if(board[x][y]*type>0){ // same color, therefore cannot move there
				moves.remove(i);
				i--;
			}
		}

		return moves;
	}

	public boolean isChecked(boolean color){ // Is going through the entire board to find the king fast enough? As with this parameter I may have to.

		int x=0,y=0; // holds location of king

		int mod = ((color)?1:-1);
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(board[j][i] == mod){
					x=j;
					y=i;
					break;
				} 
			}
		}

		mod*=-1;
		// Check along diagonals for bishops, pawns, queens, or king of opposite color. Can stop checking if hits friendly piece, rook, or knight
		// 2 | 1
		// -----
		// 3 | 4

		// Quadrant 1
		int i=x; 
		int j=y; // (x,y) is now (i,j)
		if(i+1<8&&j+1<8){
			if(board[i+1][j+1]==mod){
				return true;
			}
			if(color){
				if(board[i+1][j+1]==-6){
					return true;
				}
			}
		}
		while(i<7&&j<7){
			i++;
			j++;
			if(board[i][j]==(mod*3) || board[i][j]==(mod*5)){
				return true;
			}else if(board[i][j]!=0){
				break;
			}
		}

		// Quadrant 2
		i=x; 
		j=y;
		if(i-1>-1&&j+1<8){
			if(board[i-1][j+1]==mod){
				return true;
			}
			if(color){
				if(board[i-1][j+1]==-6){
					return true;
				}
			}
		}
		while(i>=1&&j<7){
			i--;
			j++;
			if(board[i][j]==(mod*3) || board[i][j]==(mod*5)){
				return true;
			}else if(board[i][j]!=0){
				break;
			}
		}

		// Quadrant 3
		i=x; 
		j=y;
		if(i+1<8&&j-1>-1){
			if(board[i+1][j-1]==mod){
				return true;
			}
			if(!color){
				if(board[i+1][j-1]==6){
					return true;
				}
			}
		}
		while(i>=1&&j>=1){
			i--;
			j--;
			if(board[i][j]==(mod*3) || board[i][j]==(mod*5)){
				return true;
			}else if(board[i][j]!=0){
				break;
			}
		}

		// Quadrant 4
		i=x; 
		j=y;
		if(i-1>-1&&j-1>-1){
			if(board[i-1][j-1]==mod){
				return true;
			}
			if(!color){
				if(board[i-1][j-1]==6){
					return true;
				}
			}
		}
		while(i<7&&j>=1){
			i++;
			j--;
			if(board[i][j]==(mod*3) || board[i][j]==(mod*5)){
				return true;
			}else if(board[i][j]!=0){
				break;
			}
		}

		// Check along horizontal for rooks, queens, or king of opposite color. Can stop checking if hit another piece

		// Left
		i=x;
		if(i-1>-1){
			if(board[i-1][y]==mod){
				return true;
			}
		}
		while(i>=1){
			i--;
			if(board[i][y]==(mod*4) || board[i][y]==(mod*5)){
				return true;
			}else if(board[i][j]!=0){
				break;
			}				
		}

		// Right
		i=x;
		if(i+1<8){
			if(board[i+1][y]==mod){
				return true;
			}
		}
		while(i<7){
			i++;
			if(board[i][y]==(mod*4) || board[i][y]==(mod*5)){
				return true;
			}else if(board[i][j]!=0){
				break;
			}			
		}

		// Down
		i=y;
		if(i-1>-1){
			if(board[x][i-1]==mod){
				return true;
			}
		}
		while(i>=1){
			i--;
			if(board[x][i]==(mod*4) || board[x][i]==(mod*5)){
				return true;
			}else if(board[i][j]!=0){
				break;
			}			
		}

		// Up
		i=y;
		if(i+1<8){
			if(board[x][i+1]==mod){
				return true;
			}
		}
		while(i<7){
			i++;
			if(board[x][i]==(mod*4) || board[x][i]==(mod*5)){
				return true;
			}else if(board[i][j]!=0){
				break;
			}			
		}

		// Check all possible knight squares.
		XY[] knightLocs = {new XY(-2,1),new XY(-1,2),new XY(1,2),new XY(2,1),new XY(2,-1),new XY(1,-2),new XY(-1,-2),new XY(-2,-1)};
		for(int n=0;n<knightLocs.length;n++){
			XY p = knightLocs[n];
			if(!(x+p.x>7||x+p.x<0)&&!(y+p.y>7||y+p.y<0)){
				if(board[x+p.x][y+p.y]==mod*2){
					return true;
				}
			}
		}

		return false;
	}

	public void move(XY a,XY b){
		/*
			For the actual engine, I probably won't be using this specific command, as it involves some unnecessary calculations such as checking if king is checked. Whereas for my type A calculation and branching, I'm probably going to just have a list of every single possible move, and want to filter each move preemptively to make sure legal. I'll still need the pawn and castle stuff, but can ignore the checking legal moves, so I'll copy pasta this function with an f in front of it for faster, but assuming legal move (so move will be carried out properly).

			Basically everything that isn't under "Make sure is a legal move" will be kept
		 */
		// Todo: implement few checks
		// Make sure is a legal move
		// 	   Move is within that piece's move list (getMoves of that piece)
		//     Doesn't result in check after move (handles both pins, and not dealing with present check)
		// 	   This is the stuff that will be omitted
		// If pawn move, check if en passantable needs to be toggled
		// 	   Check if moving onto final row, if so, turn into queen
		//     Check if En Passant is actually happening (if enpassantable, if pawn taking on file, just check if en passant's happening)
		// If not pawn double jump, turn off en passantable (both variables)
		// If king, check if it's the castle. If so, move rook. If not, turn off that king's castling privileges 
		// If rook, turn off that side castle privileges

		board[b.x][b.y] = board[a.x][a.y]; // I'm kinda lazy, so for now
		board[a.x][a.y] = 0;
	}

	public String toString(){
		// Very quick ascii representation of the board.
		String s="\n";

		s+= ((sideToMove)?"White":"Black") + " to move.\n\n|-----|-----|-----|-----|-----|-----|-----|-----|";
		for(int i=7;i>=0;i--){
			s+="\n|";
			for(int j=0;j<8;j++){
				if((i+j)%2 == 0){
					s+= " " + ((board[j][i]>=0)?" ":"") + ((board[j][i]==0)?" ":pieceToString(board[j][i])) + "  ";
				}else{
					s+= ":" + ((board[j][i]>=0)?":":"") + ((board[j][i]==0)?":":pieceToString(board[j][i])) + "::";
				}
				s+= "|";
			}
			s+="  "+i+"\n|-----|-----|-----|-----|-----|-----|-----|-----|";
		}
		s+="\n   0     1     2     3     4     5     6     7";
		return s;
	}
	public String pieceToString(int type){
		switch(type){
			case -1:
				return "-k";
			case 1:
				return "K";
			case -2:
				return "-n";
			case 2:
				return "N";
			case -3:
				return "-b";
			case 3:
				return "B";
			case -4:
				return "-r";
			case 4:
				return "R";
			case -5:
				return "-q";
			case 5:
				return "Q";
			case -6:
				return "-p";
			case 6:
				return "P";
		}
		return "P";
	}
}