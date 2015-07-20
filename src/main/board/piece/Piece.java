package main.board.piece;

import java.util.ArrayList;
public class Piece{
	public static ArrayList<XY> getMoves(int type,XY pos,int[][] board){
		if(type<0)
			type = (int) (-1*type);
		switch(type){
			case 0:
				return new ArrayList<XY>();
			case 1:
				return King.getMoves(pos,board);
			case 2:
				return Knight.getMoves(pos);
			case 3:
				return Bishop.getMoves(pos,board);
			case 4:
				return Rook.getMoves(pos,board);
			// case 5:
			// 	return Queen.getMoves(pos,board);
			// default:
			// 	return Pawn.getMoves(pos);
		}
		return new ArrayList<XY>();
	}
}