package main.board.piece;

import java.util.ArrayList;
public class King{
	public static ArrayList<XY> getMoves(XY pos,int[][] board,boolean kingSide,boolean queenSide){ // kingSide and queenSide are assumed to be for this king
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
		// Gotta add can't do it if currently checked

		// pseudocode
		type = board[x][y];
		if(kingSide){
			if(checkCastleKing(board[x][y]>0,board)){
				// add castling
			}
		}
		if(queenSide){
			if(checkCastleQueen(board[x][y]>0,board)){
				// add castling
			}
		}

		return moves;
	}
	public static boolean checkCastleKing(boolean color, int[][] board){
		if(color){ // white
			if(board[5][0]==0&&board[6][0]==0){
				//if(diagonals and horizontals are clear from bishop and rook or queen){
				// Left left diagonal
				for(int i=0;i<5;i++){
					if(board[4-i][i]==-3||board[4-i][i]==-5){
						return false;
					}else if(board[4-i][i]!=0){
						break;
					}
				}
				// Right left diagonal
				for(int i=0;i<6;i++){
					if(board[5-i][i]==-3||board[5-1][i]==-5){
						return false;
					}else if(board[5-i][i]!=0){
						break;
					}
				}
				// Left right diagonal
				if(board[6][1]==0){
					if(board[7][2]==-3||board[7][2]==-5){
						lrDiag=false;
					}
				}else if(board[6][1]==-3||board[7][2]==-5){
					lrDiag=false;
				}
				// Right right diagonal
				if(board[7][1]==-3||board[7][1]==-5){
					rrDiag=false;
				}

				if(llDiag&&rlDiag&&lrDiag&&rrDiag){

					// if(knight not attacking intermittent piece (specific amounts of spots you need to check)){
					// 	add move castle
					// }
				}
			}
		}else{ // black

		}
		// later on, during the actual moving of pieces, check every time if pawn or king (cause special rules)
		// if king, check if more than one move horizontally, if so, move rook as well

	}
}