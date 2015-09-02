package main.board;

import java.util.ArrayList;
import main.board.piece.*;
public class Board{
	// public boolean sideToMove; // (8,0)
	
	// public boolean whiteKingCastle; // (8,1)
	// public boolean whiteQueenCastle; // (8,2)
	// public boolean blackKingCastle; // (8,3)
	// public boolean blackQueenCastle; // (8,4)

	// public boolean lastMoveEnPassantable; // (8,5)
	// public int lastPawnEnPassantable; // (8,6) // Which file can be captured via en passant

	// public int movesSincePieceLost; // (8,7) // 50-move rule

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

	public boolean moveLock = false; // whether a side can move on their turn or not 
	public String message;

	private int lastPiece = 0; // Piece that was overtaken
	private XY lastMove = new XY(0,0); // Location piece was moved to
	private XY oldPos = new XY(0,0); // Old position originally moved from
	private boolean lastMoveWasEnPassant = false;
	private boolean lastMoveWasQueenCastle = false;
	private boolean lastMoveWasKingCastle = false;
	private boolean lastMovePawnUpgrade = false;

	private boolean lastMoveTookQueenCastling = false; // castling
	private boolean lastMoveTookKingCastling = false; // castling
	private boolean undoable = false;

	public Board(boolean init){
		if(init)
			initialize(); //todo;
	}

	public Board(){
		this(true);
	}

	public Board(int[][] board){
		this.board = board;
	}


	public Board clone(){
		return new Board(board);
	}

	public void reset(){
		initialize();

		// copy pasted from above, all variables that are modified/defined (a true reset)
		moveLock = false; // whether a side can move on their turn or not 

		lastPiece = 0; // Piece that was overtaken
		lastMove = new XY(0,0); // Location piece was moved to
		oldPos = new XY(0,0); // Old position originally moved from
		lastMoveWasEnPassant = false;
		lastMoveWasQueenCastle = false;
		lastMoveWasKingCastle = false;
		lastMovePawnUpgrade = false;

		lastMoveTookQueenCastling = false; // castling
		lastMoveTookKingCastling = false; // castling
		undoable = false;

		message = "";
	}

	public String getMessage(){
		return message;
	}
	
	public void initialize(){
		board = new int[9][8];
		
		int[][] startingPostitions = {
			{-4,-2,-3,-5,-1,-3,-2,-4},
			{-6,-6,-6,-6,-6,-6,-6,-6},
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 6, 6, 6, 6, 6, 6, 6, 6},
			{ 4, 2, 3, 5, 1, 3, 2, 4},
			
			{ 1, 1, 1, 1, 1, 0, 0, 0} // sideToMove, whiteKingCastle, whiteQueenCastle, blackKingCastle, blackQueenCastle, lastMoveEpPassantable, lastPawnEnPassantable, move Count or score not sure
		};

		// Initializing the pieces in order
		// Temporary, later will have pieces be loaded from a preset array (to allow gamemodes like horde and 960 to be more easily generated)
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				board[j][i]=startingPostitions[7-i][j];
			}
		}
		for(int i=0;i<8;i++){
			board[8][i] = startingPostitions[8][i];
		}
		// sideToMove = true;
		// whiteKingCastle = true;
		// whiteQueenCastle = true;
		// blackKingCastle = true;
		// blackQueenCastle = true;

		// lastMoveEnPassantable = false;
		// lastPawnEnPassantable = 0;
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
					moves= King.getMoves(a,board);
				}else{
					moves= King.getMoves(a,board);
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
				moves= Pawn.getMovesBlack(a,board);
				break;
			case 6: 
				moves= Pawn.getMovesWhite(a,board);
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
				message = "Checked by a diagonal1";
				return true;
			}
			if(color){
				if(board[i+1][j+1]==-6){
					message = "Checked by a diagonal2";
					return true;
				}
			}
		}
		while(i<7&&j<7){
			i++;
			j++;
			if(board[i][j]==(mod*3) || board[i][j]==(mod*5)){
				message = "Checked by a diagonal3";
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
				message = "Checked by a diagonal4";
				return true;
			}
			if(color){
				if(board[i-1][j+1]==-6){
					message = "Checked by a diagonal5";
					return true;
				}
			}
		}
		while(i>=1&&j<7){
			i--;
			j++;
			if(board[i][j]==(mod*3) || board[i][j]==(mod*5)){
				message = "Checked by a diagonal6";
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
				message = "Checked by a diagonal7";
				return true;
			}
			if(!color){
				if(board[i+1][j-1]==6){
					message = "Checked by a diagonal8";
					return true;
				}
			}
		}
		while(i>=1&&j>=1){
			i--;
			j--;
			if(board[i][j]==(mod*3) || board[i][j]==(mod*5)){
				message = "Checked by a diagonal9";
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
				message = "Checked by a diagonal10";
				return true;
			}
			if(!color){
				if(board[i-1][j-1]==6){
					message = "Checked by a diagonal11";
					return true;
				}
			}
		}
		while(i<7&&j>=1){
			i++;
			j--;
			if(board[i][j]==(mod*3) || board[i][j]==(mod*5)){
				message = "Checked by a diagonal12";
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
				message = "checked horizontally1";
				return true;
			}
		}
		while(i>=1){
			i--;
			if(board[i][y]==(mod*4) || board[i][y]==(mod*5)){
				message = "checked horizontally2";
				return true;
			}else if(board[i][y]!=0){
				break;
			}				
		}

		// Right
		i=x;
		if(i+1<8){
			if(board[i+1][y]==mod){
				message = "checked horizontally3";
				return true;
			}
		}
		while(i<7){
			i++;
			if(board[i][y]==(mod*4) || board[i][y]==(mod*5)){
				message = "checked horizontally4";
				return true;
			}else if(board[i][y]!=0){
				break;
			}			
		}

		// Down
		i=y;
		if(i-1>-1){
			if(board[x][i-1]==mod){
				message = "checked horizontally5";
				return true;
			}
		}
		while(i>=1){
			i--;
			if(board[x][i]==(mod*4) || board[x][i]==(mod*5)){
				message = "checked horizontally6";
				return true;
			}else if(board[x][i]!=0){
				break;
			}			
		}

		// Up
		i=y;
		if(i+1<8){
			if(board[x][i+1]==mod){
				message = "checked horizontally7";
				return true;
			}
		}
		while(i<7){
			i++;
			if(board[x][i]==(mod*4) || board[x][i]==(mod*5)){
				message = "checked horizontally8";
				return true;
			}else if(board[x][i]!=0){
				break;
			}			
		}

		// Check all possible knight squares.
		XY[] knightLocs = {new XY(-2,1),new XY(-1,2),new XY(1,2),new XY(2,1),new XY(2,-1),new XY(1,-2),new XY(-1,-2),new XY(-2,-1)};
		for(int n=0;n<knightLocs.length;n++){
			XY p = knightLocs[n];
			if(!(x+p.x>7||x+p.x<0)&&!(y+p.y>7||y+p.y<0)){
				if(board[x+p.x][y+p.y]==mod*2){
					message = "checked by knight";
					return true;
				}
			}
		}

		return false;
	}

	// public int[][] tempBoardAfterMove(XY a,XY b){
	// 	int oldPiece = getPiece(b);
	// 	int movingPiece = getPiece(a);
	// 	move();
	// }

	public void move(XY a,XY b){
		// message = "HURR DURR WHY AIN'T THIS WORKING";
		undoable = false;
		int type=getPiece(a);
		if(moveLock){
			if(type>0){
				if(board[8][0]==0){
					message = "not that side's turn";
					return;
				}
			}else{
				if(board[8][0]==1){
					message = "not that side's turn";
					return;
				}
			}			
		}
		/*
			For the actual engine, I probably won't be using this specific command, as it involves some unnecessary calculations such as checking if king is checked. Whereas for my type A calculation and branching, I'm probably going to just have a list of every single possible move, and want to filter each move preemptively to make sure legal. I'll still need the pawn and castle stuff, but can ignore the checking legal moves, so I'll copy pasta this function with an f in front of it for faster, but assuming legal move (so move will be carried out properly).

			Basically everything that isn't under "Make sure is a legal move" will be kept
		 */
		// Make sure is a legal move
		// 	   This is the stuff that will be omitted
		// 	   Move is within that piece's move list (getMoves of that piece)		
		ArrayList<XY> possibleMoves = getMoves(a);
		if(possibleMoves.size()==0){
			message = "Piece cannot move there1";
			return; 
		}
		for(int i=0;i<possibleMoves.size();i++){
			if(possibleMoves.get(i).equals(b)){
				break;
			}else if(i==possibleMoves.size()-1){
				message = "Piece cannot move there2";
				return;
			}
		}
		//     Doesn't result in check after move (handles both pins, and not dealing with present check)
		int oldPiece = getPiece(b);
		board[b.x][b.y] = type;
		board[a.x][a.y] = 0;
		message = "Moved piece";
		if(isChecked(type>0)){
			board[b.x][b.y] = oldPiece;
			board[a.x][a.y] = type;
			message = "Move results in check";
			return;
		}
		// Technically now the move has been made and is considered legal. There's nothing else that will make it illegal. I think.
		// If pawn move, check if en passantable needs to be toggled
		if(type==6){ // white pawn
		// 		Check if moving onto final row, if so, turn into queen
			if(b.y==7){
				message = "Upgrading passed pawn";
				board[b.x][7] = 5; // Turn it into a queen
				lastMovePawnUpgrade = true;
			}
		//  	Check if En Passant is actually happening (if enpassantable, if pawn taking on file, just check if en passant's happening)
			else if(a.y==1&&b.y==3){ // Is double jumping
				board[8][5] = 1;
				board[8][6] = a.x;
			}else if(board[8][5]==1){ // Possible for an en passant move
				if(a.x!=b.x && a.y==4 && b.x==board[8][6]){ // Still possible en passant, Pawn is taking onto en passant file
					message = "En Passant";
					board[b.x][a.y] = 0; // empty square that is being taken via en passant
					lastMoveWasEnPassant = true;
				}
			}else{
		// 		If not pawn double jump, turn off en passantable (both variables)
				board[8][5] = 0;
			}
		}else if(type==-6){ // black pawn
			if(b.y==0){
				board[b.x][0] = -5;
				lastMovePawnUpgrade = true;
			}
			else if(a.y==6&&b.y==4){
				board[8][5] = 1;
				board[8][6] = a.x;
			}else if(board[8][5]==1){ // Possible an en passant move
				if(a.x!=b.x && a.y==3 && b.x==board[8][6]){ // Still possible en passant, Pawn is taking onto en passant file
					message = "En Passant";
					board[b.x][a.y] = 0; // empty square that is being taken via en passant
					lastMoveWasEnPassant = true;
				}
			}else{
				board[8][5] = 0;
			}
		}else{ // It's not a pawn
			board[8][5] = 0;
			lastMoveWasEnPassant = false;
			lastMovePawnUpgrade = false;
		}

		// If king, check if it's the castle. If so, move rook. If not, turn off that king's castling privileges 
		if(type==1){ // white king
			if(a.x==4){
				if(b.x==6){ // castling abilities are already checked for in King.getMoves
					message = "Castled kingside";
					board[5][0] = 4;
					board[7][0] = 0;
					lastMoveWasKingCastle = true;
				}else if(b.x==2){ // queenside castling
					message = "Castled queenside";
					board[3][0] = 4;
					board[0][0] = 0;
					lastMoveWasQueenCastle = true;
				}// else{ // neither, just moving
				else{lastMoveWasQueenCastle = false;lastMoveWasKingCastle = false;}
				if(board[8][1] == 1){
					board[8][1] = 0;
					lastMoveTookKingCastling = true;
				}
				if(board[8][2] == 1){
					board[8][2] = 0;
					lastMoveTookQueenCastling = true;
				}				
				// } even after it castles, it no longer has castling rights. We can disable them		
			}
		}else if(type==-1){ // black king
			if(a.x==4){
				if(b.x==6){ // castling abilities are already checked for in King.getMoves
					message = "Castled kingside";
					board[5][7] = -4;
					board[7][7] = 0;
					lastMoveWasKingCastle = true;
				}else if(b.x==2){ // queenside castling
					message = "Castled queenside";
					board[3][7] = -4;
					board[0][7] = 0;
					lastMoveWasQueenCastle = true;
				}//else{
				else{lastMoveWasQueenCastle = false;lastMoveWasKingCastle = false;}
				if(board[8][3] == 1){
					board[8][3] = 0;
					lastMoveTookKingCastling = true;
				}
				if(board[8][4] == 1){
					board[8][4] = 0;
					lastMoveTookQueenCastling = true;
				}
				//}
			}
		}else{
			lastMoveWasQueenCastle = false;
			lastMoveWasKingCastle = false;
		}
		// If rook, turn off that side castle privileges
		if(Math.abs(type)==4){ // rook
			if(type>0){ // white
				if(board[8][1]==1){ // rook hasn't previously moved
					if(a.x==7){ // is the kingside rook
						board[8][1] = 0;
						lastMoveTookKingCastling = true;
					}
				}else{
					lastMoveTookKingCastling = false;
				}
				if(board[8][2]==1){
					if(a.x==0){
						board[8][2] = 0;
						lastMoveTookQueenCastling = true;
					}
				}else{
					lastMoveTookQueenCastling = false;
				}
			}else{ // black
				if(board[8][3]==1){
					if(a.x==7){
						board[8][3] = 0;
						lastMoveTookKingCastling = true;
					}
				}else{
					lastMoveTookKingCastling = false;
				}
				if(board[8][4]==1){
					if(a.x==0){
						board[8][4] = 0;
						lastMoveTookQueenCastling = true;
					}
				}else{
					lastMoveTookQueenCastling = false;
				}
			}
		}

		// Disabling castling if a rook is taken
		if(Math.abs(oldPiece)==4){ // Captured rook
			if(oldPiece>0){ // white
				if(board[8][1]==1){
					if(b.x==7){ // king's rook
						board[8][1] = 0;
						lastMoveTookKingCastling = true;
					}
				}
				if(board[8][2]==1){
					if(b.x==0){
						board[8][2] = 0;
						lastMoveTookQueenCastling = true;
					}
				}
			}else{
				if(board[8][3]==1){
					if(b.x==7){
						board[8][3] = 0;
						lastMoveTookKingCastling = true;
					}
				}
				if(board[8][4]==1){
					if(b.x==0){
						board[8][4] = 0;
						lastMoveTookQueenCastling = true;
					}
				}
			}
		}
		if(Math.abs(type)==2||Math.abs(type)==3||Math.abs(type)==5||Math.abs(type)==6){
			lastMoveTookQueenCastling = false;
			lastMoveTookKingCastling = false;
		}
		board[8][0]*=-1; 
		board[8][0]+=1; // toggles side to move

		lastPiece = oldPiece;
		lastMove = b;
		oldPos = a;	

		undoable = true;
	}

	public void undoMove(){
		if(!undoable){
			return;
		}
		int mult = (getPiece(lastMove)>0)?-1:1;
		// int mult = ((board[8][0]>0)?-1:1);
		if(lastMovePawnUpgrade){ // undoing queen upgrade
			board[oldPos.x][oldPos.y] = mult*6;
			board[lastMove.x][lastMove.y] = lastPiece;
		}else if(lastMoveWasEnPassant){ // undoing en Passant
			board[oldPos.x][oldPos.y] = mult*-6;
			board[lastMove.x][oldPos.y] = mult*6;
			// need to clear space that en passant pawn has moved to. is color dependent
			board[lastMove.x][lastMove.y] = 0;
			board[8][5] = 1;
			board[8][6] = lastMove.x;
		}else if(lastMoveWasQueenCastle){
			board[4][oldPos.y] = mult; // resetting king
			board[3][oldPos.y] = 0; // clearing old rook
			board[2][oldPos.y] = 0; // clearing old king
			board[0][oldPos.y] = mult*4; // resetting rook
		}else if(lastMoveWasKingCastle){
			board[4][oldPos.y] = mult; // resetting king
			board[5][oldPos.y] = 0; // clearing old rook
			board[6][oldPos.y] = 0; // clearing old king
			board[7][oldPos.y] = mult*4; // resetting rook
		}else{ // normal move
			board[oldPos.x][oldPos.y] = board[lastMove.x][lastMove.y];
			board[lastMove.x][lastMove.y] = lastPiece;
		}

		if(lastMoveTookQueenCastling){
			board[8][3-mult] = 1;
		}
		if(lastMoveTookKingCastling){
			board[8][2-mult] = 1;
		}

		board[8][0]*=-1; 
		board[8][0]+=1; // toggles side to move

		undoable = false;
	}

	public String toString(){
		// Very quick ascii representation of the board.
		String s="\n";

		s+= ((board[8][0]==1)?"White":"Black") + " to move.\n"+((moveLock)?"Moves are locked":"Moves aren't locked")+"\n\n|-----|-----|-----|-----|-----|-----|-----|-----|";
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